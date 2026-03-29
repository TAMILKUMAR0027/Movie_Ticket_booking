const MOVIE_DATA = {
    movies: [
        { id: 1, title: "Avengers: Endgame", language: "English", duration: "3h 1m", rating: "8.4", poster: "🎬" },
        { id: 2, title: "Spider-Man: No Way Home", language: "English", duration: "2h 28m", rating: "8.2", poster: "🕷️" },
        { id: 3, title: "Baahubali 2", language: "Telugu", duration: "2h 51m", rating: "8.5", poster: "⚔️" },
        { id: 4, title: "RRR", language: "Telugu", duration: "3h 2m", rating: "8.0", poster: "🏍️" },
        { id: 5, title: "Dhoom 3", language: "Hindi", duration: "2h 53m", rating: "7.3", poster: "💥" },
        { id: 6, title: "Pushpa", language: "Telugu", duration: "2h 59m", rating: "7.6", poster: "🌺" },
        { id: 7, title: "Jawan", language: "Hindi", duration: "2h 49m", rating: "7.5", poster: "🎯" },
        { id: 8, title: "Leo", language: "Tamil", duration: "2h 44m", rating: "7.8", poster: "🦁" }
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
    users: [],
    bookings: [],
    currentUser: null,
    selectedMovie: null,
    selectedShowtime: null,
    selectedSeats: [],
    ticketPrice: 150
};

if (localStorage.getItem('ticketBookingUsers')) {
    MOVIE_DATA.users = JSON.parse(localStorage.getItem('ticketBookingUsers'));
}
if (localStorage.getItem('ticketBookingBookings')) {
    MOVIE_DATA.bookings = JSON.parse(localStorage.getItem('ticketBookingBookings'));
}
