/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dao.BookingDAO;
import dao.RoomDAO;
import dto.Booking;
import dto.Room;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author trang
 */
@WebServlet(name = "UpdateBookingController", urlPatterns = {"/UpdateBookingController"})
public class UpdateBookingController extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "UpdateBooking.jsp";
        try {
            BookingDAO bDao = new BookingDAO();
            RoomDAO rDao = new RoomDAO();
            String action = request.getParameter("action");
            int bookingId = Integer.parseInt(request.getParameter("bookingid"));
            if (action == null || !action.equals("update")) {
                // Load data for the form
                Booking booking = bDao.getBookingById(bookingId);
                List<Room> availableRooms = rDao.getAvailableRooms();
                request.setAttribute("BOOKING_DATA", booking);
                request.setAttribute("AVAILABLE_ROOMS", availableRooms);
            } else {
                // Process the update
                int newRoomId = Integer.parseInt(request.getParameter("txtroomid"));
                String checkIn = request.getParameter("txtcheckin");
                String checkOut = request.getParameter("txtcheckout");
                Booking original = bDao.getBookingById(bookingId);
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String newStatus = checkIn.equals(today) ? "Checked-in" : "Reserved";
                if (original.getRoomId() != newRoomId) {
                    rDao.updateRoomStatus(original.getRoomId(), "Available");
                    rDao.updateRoomStatus(newRoomId, "Occupied");
                }
                int result = bDao.updateBooking(bookingId, newRoomId, checkIn, checkOut, newStatus);
                if (result >= 1) {
                    request.setAttribute("WARNING", "Booking successfully updated.");
                    String lastSearch = request.getParameter("lastSearch");
                    if (lastSearch != null && !lastSearch.isEmpty()) {
                        url = "SearchController?txtsearch=" + lastSearch;
                    } else {
                        url = "ManageBooking.jsp"; // Go back to empty state if no previous search
                    }
                }
            }
        } catch (Exception e) {
            log("Update Error: " + e.getMessage());
            request.setAttribute("ERROR", "Update failed: " + e.getMessage());
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
