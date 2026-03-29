document.addEventListener('DOMContentLoaded', function() {
    initNavigation();
    renderMovies();
    renderTheatres();
    renderBookings();
    initAuth();
    initBooking();
    initPayment();
});

function initNavigation() {
    const navLinks = document.querySelectorAll('.nav-links a');
    const pages = document.querySelectorAll('.page');
    const hamburger = document.querySelector('.hamburger');
    const navMenu = document.querySelector('.nav-links');

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
}

function renderMovies(filter = 'all', searchTerm = '') {
    const movieGrid = document.getElementById('all-movies');
    const featuredGrid = document.getElementById('featured-movies');
    
    let filteredMovies = MOVIE_DATA.movies;
    
    if (filter !== 'all') {
        filteredMovies = filteredMovies.filter(m => 
            m.language.toLowerCase() === filter.toLowerCase()
        );
    }
    
    if (searchTerm) {
        filteredMovies = filteredMovies.filter(m => 
            m.title.toLowerCase().includes(searchTerm.toLowerCase())
        );
    }
    
    movieGrid.innerHTML = filteredMovies.map(movie => createMovieCard(movie)).join('');
    
    const featuredMovies = MOVIE_DATA.movies.slice(0, 4);
    featuredGrid.innerHTML = featuredMovies.map(movie => createMovieCard(movie)).join('');
}

function createMovieCard(movie) {
    return `
        <div class="movie-card" data-movie-id="${movie.id}">
            <div class="movie-poster">
                <span style="font-size: 5rem;">${movie.poster}</span>
            </div>
            <div class="movie-info">
                <h3 class="movie-title">${movie.title}</h3>
                <p class="movie-language">${movie.language}</p>
                <div class="movie-details">
                    <span><i class="far fa-clock"></i> ${movie.duration}</span>
                    <span><i class="fas fa-star" style="color: var(--accent);"></i> ${movie.rating}</span>
                </div>
                <button class="btn-book" onclick="openBookingModal(${movie.id})">
                    <i class="fas fa-ticket-alt"></i> Book Tickets
                </button>
            </div>
        </div>
    `;
}

function renderTheatres() {
    const theatreList = document.getElementById('theatre-list');
    theatreList.innerHTML = MOVIE_DATA.theatres.map(theatre => `
        <div class="theatre-card">
            <i class="fas fa-film"></i>
            <h3>${theatre.name}</h3>
            <p>${theatre.location}</p>
            <p style="color: var(--primary); margin-top: 10px;">${theatre.screens} Screens</p>
        </div>
    `).join('');
}

function renderBookings() {
    const bookingsList = document.getElementById('bookings-list');
    
    if (MOVIE_DATA.bookings.length === 0) {
        bookingsList.innerHTML = `
            <div class="empty-state">
                <i class="fas fa-ticket-alt"></i>
                <p>No bookings yet. Start booking movies!</p>
                <button class="btn-primary" onclick="showPage('movies')">Browse Movies</button>
            </div>
        `;
        return;
    }
    
    bookingsList.innerHTML = MOVIE_DATA.bookings.map(booking => `
        <div class="booking-card" style="background: var(--dark-light); padding: 20px; border-radius: 15px; margin-bottom: 20px;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                <h3 style="color: var(--primary);">${booking.movieTitle}</h3>
                <span style="background: var(--success); padding: 5px 15px; border-radius: 20px; font-size: 0.85rem;">CONFIRMED</span>
            </div>
            <div style="display: grid; grid-template-columns: repeat(2, 1fr); gap: 15px;">
                <p><strong>Booking ID:</strong> ${booking.bookingId}</p>
                <p><strong>Seats:</strong> ${booking.seats.join(', ')}</p>
                <p><strong>Showtime:</strong> ${booking.showtime}</p>
                <p><strong>Amount:</strong> Rs. ${booking.amount}</p>
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
        
        const user = MOVIE_DATA.users.find(u => u.email === email);
        if (user) {
            MOVIE_DATA.currentUser = user;
            localStorage.setItem('currentUser', JSON.stringify(user));
            alert('Login successful!');
            showPage('home');
            updateLoginUI();
        } else {
            alert('Invalid credentials. Please try again.');
        }
    });

    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const name = document.getElementById('register-name').value;
        const email = document.getElementById('register-email').value;
        const phone = document.getElementById('register-phone').value;
        const password = document.getElementById('register-password').value;
        const role = document.getElementById('register-role').value;
        
        if (MOVIE_DATA.users.find(u => u.email === email)) {
            alert('Email already registered!');
            return;
        }
        
        const newUser = {
            id: MOVIE_DATA.users.length + 1,
            name,
            email,
            phone,
            role
        };
        
        MOVIE_DATA.users.push(newUser);
        localStorage.setItem('ticketBookingUsers', JSON.stringify(MOVIE_DATA.users));
        
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
                localStorage.removeItem('currentUser');
                loginBtn.textContent = 'Login';
                loginBtn.onclick = null;
                loginBtn.setAttribute('data-page', 'login');
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
        const activeFilter = document.querySelector('.filter-btn.active').dataset.filter;
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
        bookingModal.classList.remove('active');
        openPaymentModal();
    });
}

function openBookingModal(movieId) {
    const movie = MOVIE_DATA.movies.find(m => m.id === movieId);
    if (!movie) return;
    
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

    confirmPayment.addEventListener('click', processPayment);

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

function processPayment() {
    const selectedMethod = document.querySelector('input[name="payment"]:checked').value;
    
    document.getElementById('payment-modal').classList.remove('active');
    
    setTimeout(() => {
        const bookingId = 'BOOK-' + Date.now().toString().slice(-6);
        const booking = {
            bookingId,
            movieTitle: MOVIE_DATA.selectedMovie.title,
            showtime: MOVIE_DATA.selectedShowtime,
            seats: [...MOVIE_DATA.selectedSeats],
            amount: MOVIE_DATA.selectedSeats.length * MOVIE_DATA.ticketPrice,
            paymentMethod: selectedMethod,
            date: new Date().toLocaleDateString()
        };
        
        MOVIE_DATA.bookings.push(booking);
        localStorage.setItem('ticketBookingBookings', JSON.stringify(MOVIE_DATA.bookings));
        
        document.getElementById('success-booking-id').textContent = bookingId;
        document.getElementById('success-movie').textContent = booking.movieTitle;
        document.getElementById('success-seats').textContent = booking.seats.join(', ');
        document.getElementById('success-amount').textContent = `Rs. ${booking.amount}`;
        
        document.getElementById('success-modal').classList.add('active');
        
        renderBookings();
        resetBookingSelection();
    }, 500);
}

window.showPage = showPage;
window.openBookingModal = openBookingModal;
window.selectShowtime = selectShowtime;
window.toggleSeat = toggleSeat;
