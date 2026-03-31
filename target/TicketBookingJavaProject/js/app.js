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
    await loadMovies();
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
        
        MOVIE_DATA.currentUser = { email, name: email.split('@')[0] };
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
    await loadMovies();
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
