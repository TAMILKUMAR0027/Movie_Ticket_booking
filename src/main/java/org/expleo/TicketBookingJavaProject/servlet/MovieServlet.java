package org.expleo.TicketBookingJavaProject.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.expleo.TicketBookingJavaProject.model.Movie;
import org.expleo.TicketBookingJavaProject.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/api/movies/*")
public class MovieServlet extends HttpServlet {
    
    private MovieService movieService = new MovieService();
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
            if (pathInfo == null || pathInfo.equals("/")) {
                String language = request.getParameter("language");
                String search = request.getParameter("search");
                
                List<Movie> movies;
                if (search != null && !search.isEmpty()) {
                    movies = movieService.searchByTitle(search);
                } else if (language != null && !language.isEmpty()) {
                    movies = movieService.getMoviesByLanguage(language);
                } else {
                    movies = movieService.getAllMovies();
                }
                json = objectMapper.writeValueAsString(movies);
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Movie movie = movieService.getMovieById(id);
                if (movie == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    json = "{\"error\":\"Movie not found\"}";
                } else {
                    json = objectMapper.writeValueAsString(movie);
                }
            }
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
