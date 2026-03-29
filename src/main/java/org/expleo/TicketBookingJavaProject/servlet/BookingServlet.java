package org.expleo.TicketBookingJavaProject.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.expleo.TicketBookingJavaProject.model.Booking;
import org.expleo.TicketBookingJavaProject.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/bookings/*")
public class BookingServlet extends HttpServlet {
    
    private BookingService bookingService = new BookingService();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        String pathInfo = request.getPathInfo();
        String json;
        
        try {
            String email = request.getParameter("email");
            
            if (email != null && !email.isEmpty()) {
                List<Booking> bookings = bookingService.getBookingsByUser(email);
                json = objectMapper.writeValueAsString(bookings);
            } else if (pathInfo != null && !pathInfo.equals("/")) {
                int id = Integer.parseInt(pathInfo.substring(1));
                Booking booking = bookingService.getBookingById(id);
                if (booking == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    json = "{\"error\":\"Booking not found\"}";
                } else {
                    json = objectMapper.writeValueAsString(booking);
                }
            } else {
                List<Booking> bookings = bookingService.getAllBookings();
                json = objectMapper.writeValueAsString(bookings);
            }
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            
            Booking booking = objectMapper.readValue(sb.toString(), Booking.class);
            Booking created = bookingService.createBooking(booking);
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(objectMapper.writeValueAsString(created));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
