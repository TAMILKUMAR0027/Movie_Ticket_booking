const API_BASE = 'http://localhost:9090/TicketBookingJavaProject/api';

const MOVIE_DATA = {
    movies: [
        { id: 1, title: "Avengers: Endgame", language: "English", duration: "3h 1m", rating: "8.4" },
        { id: 2, title: "Spider-Man: No Way Home", language: "English", duration: "2h 28m", rating: "8.2" },
        { id: 3, title: "Baahubali 2", language: "Telugu", duration: "2h 51m", rating: "8.5" },
        { id: 4, title: "RRR", language: "Telugu", duration: "3h 2m", rating: "8.0" },
        { id: 5, title: "Dhoom 3", language: "Hindi", duration: "2h 53m", rating: "7.3" },
        { id: 6, title: "Pushpa", language: "Telugu", duration: "2h 59m", rating: "7.6" },
        { id: 7, title: "Jawan", language: "Hindi", duration: "2h 49m", rating: "7.5" },
        { id: 8, title: "Leo", language: "Tamil", duration: "2h 44m", rating: "7.8" }
    ],
    theatres: [
        { id: 1, name: "PVR Cinemas", location: "Downtown Mall", screens: 8 },
        { id: 2, name: "INOX", location: "Central Plaza", screens: 6 },
        { id: 3, name: "Cinepolis", location: "Metro Point", screens: 5 },
        { id: 4, name: "AMC Theatres", location: "City Center", screens: 10 },
        { id: 5, name: "Regal Cinemas", location: "Express Avenue", screens: 7 }
    ],
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
        if (!response.ok) throw new Error('API not available');
        return await response.json();
    } catch (error) {
        console.log('Using offline data - API not available');
        return null;
    }
}

async function loadMovies() {
    try {
        const movies = await apiCall('/movies');
        if (movies && Array.isArray(movies) && movies.length > 0) {
            MOVIE_DATA.movies = movies;
        }
    } catch (e) {
        console.log('Using fallback movie data');
    }
}

async function loadTheatres() {
    try {
        const theatres = await apiCall('/theatres');
        if (theatres && Array.isArray(theatres) && theatres.length > 0) {
            MOVIE_DATA.theatres = theatres;
        }
    } catch (e) {
        console.log('Using fallback theatre data');
    }
}

async function loadBookings() {
    const email = MOVIE_DATA.currentUser?.email;
    if (email) {
        try {
            return await apiCall(`/bookings?email=${email}`);
        } catch (e) {
            return [];
        }
    }
    return [];
}

async function createBooking(bookingData) {
    try {
        return await apiCall('/bookings', 'POST', bookingData);
    } catch (e) {
        return { bookingId: 'BOOK-' + Date.now().toString().slice(-6), ...bookingData };
    }
}

async function processPayment(amount, method) {
    try {
        return await apiCall('/payments', 'POST', { amount, method });
    } catch (e) {
        return { paymentId: Date.now(), amount, method, status: 'SUCCESS' };
    }
}

loadMovies();
loadTheatres();