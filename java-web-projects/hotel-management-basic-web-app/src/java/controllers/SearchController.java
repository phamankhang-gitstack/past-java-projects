/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BookingDAO;
import dto.Booking;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name="SearchController", urlPatterns={"/SearchController"})
public class SearchController extends HttpServlet {
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "ManageBooking.jsp";
        try {
            String searchDate = request.getParameter("txtsearch");
            String searchType = request.getParameter("searchType");
            if (searchDate != null && !searchDate.trim().isEmpty()) {
                BookingDAO bDAO = new BookingDAO();
                ArrayList<Booking> result;
                if ("checkInDate".equals(searchType)) {
                    result = bDAO.findBookingByCheckInDate(searchDate.trim());
                } else {
                    result = bDAO.findBooking(searchDate.trim());
                }
                request.setAttribute("RESULT", result);
            }
        } catch (Exception e) {
            log("Search error", e);
            request.setAttribute("ERROR", "Search failed: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
