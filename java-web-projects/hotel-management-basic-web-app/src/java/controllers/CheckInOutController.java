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
 * @author trang
 */
@WebServlet(name = "CheckInOutController", urlPatterns = {"/CheckInOutController"})
public class CheckInOutController extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "SearchController";
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingid"));
            String action = request.getParameter("action");
            BookingDAO bDAO = new BookingDAO();
            RoomDAO rDAO = new RoomDAO();
            Booking booking = bDAO.getBookingById(bookingId);
            if ("CheckIn".equalsIgnoreCase(action)) {
                if (bDAO.checkInBooking(bookingId) >= 1) {
                    rDAO.updateRoomStatus(booking.getRoomId(), "Occupied");
                    request.setAttribute("WARNING", "Check-in successful for Room " + booking.getRoomNumber());
                }
            } else if ("CheckOut".equalsIgnoreCase(action)) {
                if (bDAO.checkOutBooking(bookingId) >= 1) {
                    rDAO.updateRoomStatus(booking.getRoomId(), "Available");
                    request.setAttribute("WARNING", "Check-out successful for Room " + booking.getRoomNumber());
                }
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Transaction failed: " + e.getMessage());
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
