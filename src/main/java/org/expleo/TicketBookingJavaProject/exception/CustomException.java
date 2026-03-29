package org.expleo.TicketBookingJavaProject.exception;

public class CustomException extends Exception {

    private String errorCode;

    public CustomException(String message) {
        super(message);
        this.errorCode = "ERR_DEFAULT";
    }

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public static class SeatNotAvailableException extends CustomException {
        public SeatNotAvailableException(String seatLabel) {
            super("Seat " + seatLabel + " is not available.", "SEAT_UNAVAILABLE");
        }
    }

    public static class PaymentFailedException extends CustomException {
        public PaymentFailedException(String reason) {
            super("Payment failed: " + reason, "PAYMENT_FAILED");
        }
    }

    public static class InvalidUserException extends CustomException {
        public InvalidUserException(String message) {
            super(message, "INVALID_USER");
        }
    }

    public static class BookingNotFoundException extends CustomException {
        public BookingNotFoundException(int bookingId) {
            super("Booking with ID " + bookingId + " not found.", "BOOKING_NOT_FOUND");
        }
    }
}
