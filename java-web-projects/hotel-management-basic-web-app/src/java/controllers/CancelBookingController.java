/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BookingDAO;
import dao.RoomDAO;
import dto.Booking;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name="CancelBookingController", urlPatterns={"/CancelBookingController"})
public class CancelBookingController extends HttpServlet {
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingid"));
            BookingDAO bDAO = new BookingDAO();
            RoomDAO rDAO = new RoomDAO();
            Booking b = bDAO.getBookingById(bookingId);
            if (bDAO.cancelBooking(bookingId) >= 1) {
                rDAO.updateRoomStatus(b.getRoomId(), "Available");
                request.setAttribute("WARNING", "Booking successfully canceled.");
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Cancellation failed: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("SearchController").forward(request, response);
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
