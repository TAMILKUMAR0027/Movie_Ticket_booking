const API_BASE = 'http://localhost:9090/TicketBookingJavaProject/api';

const MOVIE_DATA = {
    movies: [],
    theatres: [],
    showtimes: ["10:00 AM", "01:00 PM", "04:00 PM", "07:00 PM", "10:00 PM"],
    cities: ["New York", "Los Angeles", "Chicago", "Houston", "San Francisco"],
    currentUser: null,
    selectedMovie: null,
    selectedShowtime: null,
    selectedSeats: [],
    ticketPrice: 150
};

async function apiCall(endpoint, method = 'GET', body = null) {
    const options = {
        method,
        headers: { 'Content-Type': 'application/json' }
    };
    if (body) {
        options.body = JSON.stringify(body);
    }
    try {
        const response = await fetch(`${API_BASE}${endpoint}`, options);
        return await response.json();
    } catch (error) {
        console.error('API Error:', error);
        return null;
    }
}

async function loadMovies() {
    const movies = await apiCall('/movies');
    if (movies) {
        MOVIE_DATA.movies = Array.isArray(movies) ? movies : [];
    }
}

async function loadTheatres() {
    const theatres = await apiCall('/theatres');
    if (theatres) {
        MOVIE_DATA.theatres = Array.isArray(theatres) ? theatres : [];
    }
}

async function loadBookings() {
    const email = MOVIE_DATA.currentUser?.email;
    if (email) {
        return await apiCall(`/bookings?email=${email}`);
    }
    return [];
}

async function createBooking(bookingData) {
    return await apiCall('/bookings', 'POST', bookingData);
}

async function processPayment(amount, method) {
    return await apiCall('/payments', 'POST', { amount, method });
}

loadMovies();
loadTheatres();
