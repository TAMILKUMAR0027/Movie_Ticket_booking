document.addEventListener('DOMContentLoaded', async function() {
    initNavigation();
    await renderMovies();
    renderTheatres();
    renderBookings();
    initAuth();
    initBooking();
    initPayment();
});

function initNavigation() {
    const navLinks = document.querySelectorAll('.nav-links a');
    const navMenu = document.querySelector('.nav-links');
    const hamburger = document.querySelector('.hamburger');

    navLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const pageId = this.getAttribute('data-page');
            showPage(pageId);
            
            navLinks.forEach(l => l.classList.remove('active'));
            this.classList.add('active');
            navMenu.classList.remove('active');
        });
    });

    hamburger.addEventListener('click', () => {
        navMenu.classList.toggle('active');
    });
}

function showPage(pageId) {
    const pages = document.querySelectorAll('.page');
    pages.forEach(page => page.classList.remove('active'));
    document.getElementById(pageId).classList.add('active');
    window.scrollTo(0, 0);

    if (pageId === 'movies') {
        renderMovies();
    } else if (pageId === 'theatres') {
        renderTheatres();
    } else if (pageId === 'bookings') {
        renderBookings();
    }
}

async function renderMovies(filter = 'all', searchTerm = '') {
    const movieGrid = document.getElementById('all-movies');
    const featuredGrid = document.getElementById('featured-movies');
    
    let filteredMovies = [...MOVIE_DATA.movies];
    
    if (filter !== 'all') {
        filteredMovies = filteredMovies.filter(m => 
            m.language && m.language.toLowerCase() === filter.toLowerCase()
        );
    }
    
    if (searchTerm) {
        filteredMovies = filteredMovies.filter(m => 
            m.title && m.title.toLowerCase().includes(searchTerm.toLowerCase())
        );
    }
    
    if (filteredMovies.length === 0) {
        movieGrid.innerHTML = '<p style="text-align:center; color: var(--gray);">No movies found</p>';
        featuredGrid.innerHTML = '';
        return;
    }
    
    movieGrid.innerHTML = filteredMovies.map(movie => createMovieCard(movie)).join('');
    
    const featuredMovies = filteredMovies.slice(0, 4);
    featuredGrid.innerHTML = featuredMovies.map(movie => createMovieCard(movie)).join('');
}

function createMovieCard(movie) {
    const poster = getMoviePoster(movie.title);
    return `
        <div class="movie-card" data-movie-id="${movie.id}">
            <div class="movie-poster">
                <span style="font-size: 5rem;">${poster}</span>
            </div>
            <div class="movie-info">
                <h3 class="movie-title">${movie.title || 'Unknown'}</h3>
                <p class="movie-language">${movie.language || 'N/A'}</p>
                <div class="movie-details">
                    <span><i class="far fa-clock"></i> ${movie.duration || '2h 30m'}</span>
                    <span><i class="fas fa-star" style="color: var(--accent);"></i> ${movie.rating || '7.5'}</span>
                </div>
                <button class="btn-book" onclick="openBookingModal(${movie.id})">
                    <i class="fas fa-ticket-alt"></i> Book Tickets
                </button>
            </div>
        </div>
    `;
}

function getMoviePoster(title) {
    const posters = {
        'avengers': '🦸', 'spider': '🕷️', 'baahubali': '⚔️', 'rrr': '🏍️',
        'dhoom': '💥', 'pushpa': '🌺', 'jawan': '🎯', 'leo': '🦁'
    };
    const lower = title.toLowerCase();
    for (const [key, emoji] of Object.entries(posters)) {
        if (lower.includes(key)) return emoji;
    }
    return '🎬';
}

function renderTheatres() {
    const theatreList = document.getElementById('theatre-list');
    if (MOVIE_DATA.theatres.length === 0) {
        theatreList.innerHTML = '<p style="text-align:center; color: var(--gray);">No theatres available</p>';
        return;
    }
    theatreList.innerHTML = MOVIE_DATA.theatres.map(theatre => `
        <div class="theatre-card">
            <i class="fas fa-film"></i>
            <h3>${theatre.name || 'Unknown Theatre'}</h3>
            <p>${theatre.location || 'Location TBD'}</p>
            <p style="color: var(--primary); margin-top: 10px;">${theatre.screens || 5} Screens</p>
        </div>
    `).join('');
}

async function renderBookings() {
    const bookingsList = document.getElementById('bookings-list');
    
    if (!MOVIE_DATA.currentUser) {
        bookingsList.innerHTML = `
            <div class="empty-state">
                <i class="fas fa-ticket-alt"></i>
                <p>Please login to view your bookings</p>
                <button class="btn-primary" onclick="showPage('login')">Login</button>
            </div>
        `;
        return;
    }
    
    const bookings = await loadBookings();
    
    if (!bookings || bookings.length === 0) {
        bookingsList.innerHTML = `
            <div class="empty-state">
                <i class="fas fa-ticket-alt"></i>
                <p>No bookings yet. Start booking movies!</p>
                <button class="btn-primary" onclick="showPage('movies')">Browse Movies</button>
            </div>
        `;
        return;
    }
    
    bookingsList.innerHTML = bookings.map(booking => `
        <div class="booking-card" style="background: var(--dark-light); padding: 20px; border-radius: 15px; margin-bottom: 20px;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                <h3 style="color: var(--primary);">${booking.movieTitle || 'Movie'}</h3>
                <span style="background: var(--success); padding: 5px 15px; border-radius: 20px; font-size: 0.85rem;">${booking.status || 'CONFIRMED'}</span>
            </div>
            <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 15px;">
                <p><strong>Booking ID:</strong> ${booking.bookingId || 'N/A'}</p>
                <p><strong>Seats:</strong> ${booking.seats || 'N/A'}</p>
                <p><strong>Showtime:</strong> ${booking.showtime || 'N/A'}</p>
                <p><strong>Amount:</strong> Rs. ${booking.totalAmount || 0}</p>
            </div>
        </div>
    `).join('');
}

function initAuth() {
    const authTabs = document.querySelectorAll('.auth-tab');
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');

    authTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            authTabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
            
            if (this.dataset.tab === 'login') {
                loginForm.classList.add('active');
                registerForm.classList.remove('active');
            } else {
                loginForm.classList.remove('active');
                registerForm.classList.add('active');
            }
        });
    });

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const email = document.getElementById('login-email').value;
        const password = document.getElementById('login-password').value;
        const role = document.getElementById('login-role').value;
        
        // Try admin login first
        if (handleAdminLogin(email, password)) {
            return;
        }
        
        // Regular customer login
        MOVIE_DATA.currentUser = { email, name: email.split('@')[0], role: 'CUSTOMER' };
        alert('Login successful!');
        showPage('home');
        updateLoginUI();
    });

    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const name = document.getElementById('register-name').value;
        const email = document.getElementById('register-email').value;
        
        alert('Registration successful! Please login.');
        document.querySelector('.auth-tab[data-tab="login"]').click();
    });
}

function updateLoginUI() {
    const loginBtn = document.querySelector('.btn-login');
    if (MOVIE_DATA.currentUser) {
        loginBtn.textContent = MOVIE_DATA.currentUser.name;
        loginBtn.onclick = () => {
            if (confirm('Logout?')) {
                MOVIE_DATA.currentUser = null;
                loginBtn.textContent = 'Login';
                loginBtn.onclick = null;
            }
        };
    }
}

function initBooking() {
    const filterBtns = document.querySelectorAll('.filter-btn');
    const searchInput = document.getElementById('movie-search');
    const bookingModal = document.getElementById('booking-modal');
    const closeModal = document.querySelectorAll('.close-modal');
    const cancelBooking = document.getElementById('cancel-booking');
    const proceedPayment = document.getElementById('proceed-payment');

    filterBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            filterBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            renderMovies(this.dataset.filter, searchInput.value);
        });
    });

    searchInput.addEventListener('input', function() {
        const activeFilter = document.querySelector('.filter-btn.active')?.dataset.filter || 'all';
        renderMovies(activeFilter, this.value);
    });

    closeModal.forEach(btn => {
        btn.addEventListener('click', function() {
            bookingModal.classList.remove('active');
            document.getElementById('payment-modal').classList.remove('active');
            document.getElementById('success-modal').classList.remove('active');
        });
    });

    cancelBooking.addEventListener('click', () => {
        bookingModal.classList.remove('active');
        resetBookingSelection();
    });

    proceedPayment.addEventListener('click', () => {
        if (MOVIE_DATA.selectedSeats.length === 0) {
            alert('Please select at least one seat.');
            return;
        }
        if (!MOVIE_DATA.selectedShowtime) {
            alert('Please select a showtime.');
            return;
        }
        bookingModal.classList.remove('active');
        openPaymentModal();
    });
}

async function openBookingModal(movieId) {
    const movie = MOVIE_DATA.movies.find(m => m.id === movieId);
    if (!movie) {
        alert('Movie not found');
        return;
    }
    
    MOVIE_DATA.selectedMovie = movie;
    MOVIE_DATA.selectedSeats = [];
    MOVIE_DATA.selectedShowtime = null;
    
    document.getElementById('modal-movie-title').textContent = movie.title;
    document.getElementById('summary-movie').textContent = movie.title;
    document.getElementById('summary-showtime').textContent = '-';
    document.getElementById('summary-seats').textContent = '-';
    document.getElementById('summary-total').textContent = 'Rs. 0';
    
    renderShowtimes();
    renderSeats();
    
    document.getElementById('booking-modal').classList.add('active');
}

function renderShowtimes() {
    const showtimeOptions = document.getElementById('showtime-options');
    showtimeOptions.innerHTML = MOVIE_DATA.showtimes.map(time => `
        <button class="showtime-btn" onclick="selectShowtime('${time}')">${time}</button>
    `).join('');
}

function selectShowtime(time) {
    MOVIE_DATA.selectedShowtime = time;
    document.getElementById('summary-showtime').textContent = time;
    
    document.querySelectorAll('.showtime-btn').forEach(btn => {
        btn.classList.remove('active');
        if (btn.textContent === time) {
            btn.classList.add('active');
        }
    });
    
    updateBookingSummary();
}

function renderSeats() {
    const seatGrid = document.getElementById('seat-grid');
    const rows = ['A', 'B', 'C', 'D', 'E'];
    let html = '';
    
    rows.forEach(row => {
        for (let i = 1; i <= 10; i++) {
            const seatId = `${row}${i}`;
            const isBooked = Math.random() < 0.2;
            html += `
                <button class="seat-btn ${isBooked ? 'booked' : ''}" 
                        data-seat="${seatId}"
                        ${isBooked ? 'disabled' : ''}
                        onclick="toggleSeat('${seatId}', this)">
                    ${seatId}
                </button>
            `;
        }
    });
    
    seatGrid.innerHTML = html;
}

function toggleSeat(seatId, element) {
    const index = MOVIE_DATA.selectedSeats.indexOf(seatId);
    
    if (index > -1) {
        MOVIE_DATA.selectedSeats.splice(index, 1);
        element.classList.remove('selected');
    } else {
        MOVIE_DATA.selectedSeats.push(seatId);
        element.classList.add('selected');
    }
    
    updateBookingSummary();
}

function updateBookingSummary() {
    document.getElementById('summary-seats').textContent = 
        MOVIE_DATA.selectedSeats.length > 0 
            ? MOVIE_DATA.selectedSeats.join(', ') 
            : '-';
    
    const total = MOVIE_DATA.selectedSeats.length * MOVIE_DATA.ticketPrice;
    document.getElementById('summary-total').textContent = `Rs. ${total}`;
}

function resetBookingSelection() {
    MOVIE_DATA.selectedMovie = null;
    MOVIE_DATA.selectedShowtime = null;
    MOVIE_DATA.selectedSeats = [];
}

function initPayment() {
    const paymentModal = document.getElementById('payment-modal');
    const cancelPayment = document.getElementById('cancel-payment');
    const confirmPayment = document.getElementById('confirm-payment');
    const viewBookings = document.getElementById('view-bookings');

    cancelPayment.addEventListener('click', () => {
        paymentModal.classList.remove('active');
    });

    confirmPayment.addEventListener('click', confirmBooking);

    viewBookings.addEventListener('click', () => {
        document.getElementById('success-modal').classList.remove('active');
        showPage('bookings');
    });
}

function openPaymentModal() {
    const total = MOVIE_DATA.selectedSeats.length * MOVIE_DATA.ticketPrice;
    document.getElementById('payment-amount').textContent = `Rs. ${total}`;
    document.getElementById('payment-modal').classList.add('active');
}

async function confirmBooking() {
    const selectedMethod = document.querySelector('input[name="payment"]:checked').value;
    const total = MOVIE_DATA.selectedSeats.length * MOVIE_DATA.ticketPrice;
    
    document.getElementById('payment-modal').classList.remove('active');
    
    try {
        const bookingData = {
            movieId: MOVIE_DATA.selectedMovie.id,
            movieTitle: MOVIE_DATA.selectedMovie.title,
            showtime: MOVIE_DATA.selectedShowtime,
            seats: MOVIE_DATA.selectedSeats.join(','),
            totalAmount: total,
            paymentMethod: selectedMethod,
            userEmail: MOVIE_DATA.currentUser?.email || 'guest@example.com',
            status: 'CONFIRMED'
        };
        
        const booking = await createBooking(bookingData);
        
        setTimeout(() => {
            document.getElementById('success-booking-id').textContent = booking?.bookingId || 'BOOK-' + Date.now().toString().slice(-6);
            document.getElementById('success-movie').textContent = MOVIE_DATA.selectedMovie.title;
            document.getElementById('success-seats').textContent = MOVIE_DATA.selectedSeats.join(', ');
            document.getElementById('success-amount').textContent = `Rs. ${total}`;
            
            document.getElementById('success-modal').classList.add('active');
            
            resetBookingSelection();
        }, 500);
    } catch (error) {
        alert('Payment failed. Please try again.');
        console.error(error);
    }
}

window.showPage = showPage;
window.openBookingModal = openBookingModal;
window.selectShowtime = selectShowtime;
window.toggleSeat = toggleSeat;
window.searchMoviesFromHome = searchMoviesFromHome;

async function searchMoviesFromHome() {
    const searchTerm = document.getElementById('home-search').value.trim();
    if (!searchTerm) {
        alert('Please enter a movie name to search');
        return;
    }
    
    const results = MOVIE_DATA.movies.filter(m => 
        m.title && m.title.toLowerCase().includes(searchTerm.toLowerCase())
    );
    
    const searchResultsSection = document.getElementById('search-results');
    const searchMoviesGrid = document.getElementById('search-movies');
    const featuredSection = document.querySelector('.featured');
    
    if (results.length > 0) {
        searchMoviesGrid.innerHTML = results.map(movie => createMovieCard(movie)).join('');
        searchResultsSection.style.display = 'block';
        featuredSection.style.display = 'none';
    } else {
        searchMoviesGrid.innerHTML = '<p style="text-align:center; color: var(--gray);">No movies found matching "' + searchTerm + '"</p>';
        searchResultsSection.style.display = 'block';
        featuredSection.style.display = 'none';
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const homeSearch = document.getElementById('home-search');
    if (homeSearch) {
        homeSearch.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                searchMoviesFromHome();
            }
        });
    }
});

// ==================== ADMIN FUNCTIONALITY ====================
let currentAdminRole = null;
let adminUser = null;

const ADMIN_USERS = {
    'admin@system.com': { password: 'admin123', role: 'SUPER_ADMIN', name: 'Super Admin' },
    'theatre@pvr.com': { password: 'theatre123', role: 'THEATRE_ADMIN', name: 'Theatre Admin' },
    'officer1@pvr.com': { password: 'officer123', role: 'OFFICER', name: 'Officer One' },
    'john@example.com': { password: 'password123', role: 'CUSTOMER', name: 'John Doe' }
};

function handleAdminLogin(email, password) {
    const user = ADMIN_USERS[email];
    if (user && user.password === password) {
        adminUser = { email, ...user };
        currentAdminRole = user.role;
        
        document.getElementById('login-modal').classList.remove('active');
        
        updateNavForAdmin(user.role, user.name);
        
        if (user.role === 'SUPER_ADMIN') {
            showPage('super-admin');
        } else if (user.role === 'THEATRE_ADMIN') {
            showPage('theatre-admin');
        } else if (user.role === 'OFFICER') {
            showPage('officer-dashboard');
        } else {
            showPage('home');
        }
        
        alert('Welcome ' + user.name + '!');
        return true;
    }
    return false;
}

function updateNavForAdmin(role, name) {
    const loginBtn = document.getElementById('login-btn');
    loginBtn.textContent = name;
    loginBtn.onclick = logout;
}

function logout() {
    adminUser = null;
    currentAdminRole = null;
    
    const loginBtn = document.getElementById('login-btn');
    loginBtn.textContent = 'Login';
    loginBtn.onclick = null;
    loginBtn.setAttribute('data-page', 'login');
    
    showPage('home');
    alert('Logged out successfully!');
}

function showAdminSection(section) {
    const contentDiv = document.getElementById('super-admin-content');
    if (currentAdminRole === 'THEATRE_ADMIN') {
        contentDiv = document.getElementById('theatre-admin-content');
    }
    
    let html = '';
    
    switch(section) {
        case 'create-theatre':
            html = `
                <h3>Create New Theatre</h3>
                <div class="form-group">
                    <label>Theatre Name</label>
                    <input type="text" id="theatre-name" placeholder="Enter theatre name">
                </div>
                <div class="form-group">
                    <label>City</label>
                    <select id="theatre-city">
                        <option value="Chennai">Chennai</option>
                        <option value="Coimbatore">Coimbatore</option>
                        <option value="Madurai">Madurai</option>
                        <option value="Trichy">Trichy</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Location</label>
                    <input type="text" id="theatre-location" placeholder="Enter location">
                </div>
                <div class="form-group">
                    <label>Number of Screens</label>
                    <input type="number" id="theatre-screens" placeholder="Enter number of screens">
                </div>
                <button class="btn-primary" onclick="createTheatre()">Create Theatre</button>
            `;
            break;
            
        case 'create-admin':
            html = `
                <h3>Create Theatre Admin</h3>
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" id="admin-name" placeholder="Enter name">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="admin-email" placeholder="Enter email">
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <input type="tel" id="admin-phone" placeholder="Enter phone">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" id="admin-password" placeholder="Enter password">
                </div>
                <div class="form-group">
                    <label>Theatre</label>
                    <select id="admin-theatre">
                        <option value="PVR Cinemas">PVR Cinemas</option>
                        <option value="INOX">INOX</option>
                        <option value="Cinepolis">Cinepolis</option>
                    </select>
                </div>
                <button class="btn-primary" onclick="createTheatreAdmin()">Create Admin</button>
            `;
            break;
            
        case 'remove-theatre':
            html = `
                <h3>Remove Theatre</h3>
                <table>
                    <thead><tr><th>ID</th><th>Name</th><th>City</th><th>Action</th></tr></thead>
                    <tbody>
                        <tr><td>1</td><td>PVR Cinemas</td><td>Chennai</td><td><button class="btn-secondary" onclick="removeTheatre(1)">Remove</button></td></tr>
                        <tr><td>2</td><td>INOX</td><td>Chennai</td><td><button class="btn-secondary" onclick="removeTheatre(2)">Remove</button></td></tr>
                        <tr><td>3</td><td>Cinepolis</td><td>Coimbatore</td><td><button class="btn-secondary" onclick="removeTheatre(3)">Remove</button></td></tr>
                    </tbody>
                </table>
            `;
            break;
            
        case 'remove-admin':
            html = `
                <h3>Remove Theatre Admin</h3>
                <table>
                    <thead><tr><th>Name</th><th>Email</th><th>Theatre</th><th>Action</th></tr></thead>
                    <tbody>
                        <tr><td>Theatre Admin</td><td>theatre@pvr.com</td><td>PVR Cinemas</td><td><button class="btn-secondary" onclick="removeTheatreAdmin('theatre@pvr.com')">Remove</button></td></tr>
                    </tbody>
                </table>
            `;
            break;
            
        case 'add-movie':
            html = `
                <h3>Add New Movie</h3>
                <div class="form-group">
                    <label>Movie Title</label>
                    <input type="text" id="movie-title" placeholder="Enter movie title">
                </div>
                <div class="form-group">
                    <label>Language</label>
                    <select id="movie-language">
                        <option value="English">English</option>
                        <option value="Hindi">Hindi</option>
                        <option value="Tamil">Tamil</option>
                        <option value="Telugu">Telugu</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Duration</label>
                    <input type="text" id="movie-duration" placeholder="e.g., 2h 30m">
                </div>
                <div class="form-group">
                    <label>Rating</label>
                    <input type="text" id="movie-rating" placeholder="e.g., 8.5">
                </div>
                <button class="btn-primary" onclick="addMovie()">Add Movie</button>
            `;
            break;
            
        case 'update-movie':
            html = `
                <h3>Update Movie</h3>
                <table>
                    <thead><tr><th>ID</th><th>Title</th><th>Language</th><th>Duration</th><th>Action</th></tr></thead>
                    <tbody>
                        ${MOVIE_DATA.movies.map(m => `<tr><td>${m.id}</td><td>${m.title}</td><td>${m.language}</td><td>${m.duration || 'N/A'}</td><td><button class="btn-primary" onclick="editMovie(${m.id})">Edit</button></td></tr>`).join('')}
                    </tbody>
                </table>
            `;
            break;
            
        case 'delete-movie':
            html = `
                <h3>Delete Movie</h3>
                <table>
                    <thead><tr><th>ID</th><th>Title</th><th>Language</th><th>Action</th></tr></thead>
                    <tbody>
                        ${MOVIE_DATA.movies.map(m => `<tr><td>${m.id}</td><td>${m.title}</td><td>${m.language}</td><td><button class="btn-secondary" onclick="deleteMovie(${m.id})">Delete</button></td></tr>`).join('')}
                    </tbody>
                </table>
            `;
            break;
            
        case 'create-officer':
            html = `
                <h3>Create Officer</h3>
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" id="officer-name" placeholder="Enter name">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="officer-email" placeholder="Enter email">
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <input type="tel" id="officer-phone" placeholder="Enter phone">
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" id="officer-password" placeholder="Enter password">
                </div>
                <div class="form-group">
                    <label>Shift</label>
                    <select id="officer-shift">
                        <option value="MORNING">Morning</option>
                        <option value="EVENING">Evening</option>
                    </select>
                </div>
                <button class="btn-primary" onclick="createOfficer()">Create Officer</button>
            `;
            break;
            
        case 'view-movies':
            html = `
                <h3>Movie List</h3>
                <table>
                    <thead><tr><th>ID</th><th>Title</th><th>Language</th><th>Duration</th><th>Rating</th></tr></thead>
                    <tbody>
                        ${MOVIE_DATA.movies.map(m => `<tr><td>${m.id}</td><td>${m.title}</td><td>${m.language}</td><td>${m.duration || 'N/A'}</td><td>${m.rating || 'N/A'}</td></tr>`).join('')}
                    </tbody>
                </table>
            `;
            break;
            
        case 'view-profile':
            html = `
                <h3>My Profile</h3>
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" value="${adminUser?.name || ''}" disabled>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" value="${adminUser?.email || ''}" disabled>
                </div>
                <div class="form-group">
                    <label>Role</label>
                    <input type="text" value="${adminUser?.role || ''}" disabled>
                </div>
            `;
            break;
            
        case 'update-profile':
            html = `
                <h3>Update Profile</h3>
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" id="update-name" value="${adminUser?.name || ''}">
                </div>
                <div class="form-group">
                    <label>Phone</label>
                    <input type="tel" id="update-phone" placeholder="Enter new phone">
                </div>
                <button class="btn-primary" onclick="updateProfile()">Update Profile</button>
            `;
            break;
            
        default:
            html = '<p>Select an option from the menu</p>';
    }
    
    contentDiv.innerHTML = html;
}

function createTheatre() {
    const name = document.getElementById('theatre-name').value;
    const city = document.getElementById('theatre-city').value;
    const location = document.getElementById('theatre-location').value;
    const screens = document.getElementById('theatre-screens').value;
    
    if (name && city && location && screens) {
        alert('Theatre "' + name + '" created successfully!');
        document.getElementById('super-admin-content').innerHTML = '';
    } else {
        alert('Please fill all fields!');
    }
}

function createTheatreAdmin() {
    const name = document.getElementById('admin-name').value;
    const email = document.getElementById('admin-email').value;
    
    if (name && email) {
        alert('Theatre Admin "' + name + '" created successfully!');
        document.getElementById('super-admin-content').innerHTML = '';
    } else {
        alert('Please fill all fields!');
    }
}

function removeTheatre(id) {
    if (confirm('Are you sure you want to remove this theatre?')) {
        alert('Theatre removed successfully!');
    }
}

function removeTheatreAdmin(email) {
    if (confirm('Are you sure you want to remove this admin?')) {
        alert('Theatre Admin removed successfully!');
    }
}

function addMovie() {
    const title = document.getElementById('movie-title').value;
    const language = document.getElementById('movie-language').value;
    
    if (title && language) {
        const newId = MOVIE_DATA.movies.length + 1;
        MOVIE_DATA.movies.push({
            id: newId,
            title: title,
            language: language,
            duration: document.getElementById('movie-duration').value || '2h 30m',
            rating: document.getElementById('movie-rating').value || '7.5'
        });
        alert('Movie "' + title + '" added successfully!');
        document.getElementById('theatre-admin-content').innerHTML = '';
    } else {
        alert('Please fill required fields!');
    }
}

function editMovie(id) {
    alert('Edit movie with ID: ' + id);
}

function deleteMovie(id) {
    if (confirm('Are you sure you want to delete this movie?')) {
        MOVIE_DATA.movies = MOVIE_DATA.movies.filter(m => m.id !== id);
        alert('Movie deleted successfully!');
        showAdminSection('delete-movie');
    }
}

function createOfficer() {
    const name = document.getElementById('officer-name').value;
    const email = document.getElementById('officer-email').value;
    
    if (name && email) {
        alert('Officer "' + name + '" created successfully!');
        document.getElementById('theatre-admin-content').innerHTML = '';
    } else {
        alert('Please fill all fields!');
    }
}

function updateProfile() {
    alert('Profile updated successfully!');
    document.getElementById('theatre-admin-content').innerHTML = '';
}

// Make functions available globally
window.handleAdminLogin = handleAdminLogin;
window.logout = logout;
window.showAdminSection = showAdminSection;
window.createTheatre = createTheatre;
window.createTheatreAdmin = createTheatreAdmin;
window.removeTheatre = removeTheatre;
window.removeTheatreAdmin = removeTheatreAdmin;
window.addMovie = addMovie;
window.editMovie = editMovie;
window.deleteMovie = deleteMovie;
window.createOfficer = createOfficer;
window.updateProfile = updateProfile;
