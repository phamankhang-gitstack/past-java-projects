/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.BookingDAO;
import dao.GuestDAO;
import dao.RoomDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "CreateBookingController", urlPatterns = {"/CreateBookingController"})
public class CreateBookingController extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int roomId = Integer.parseInt(request.getParameter("txtroomid"));
            String checkIn = request.getParameter("txtcheckin");
            String checkOut = request.getParameter("txtcheckout");
            GuestDAO gDAO = new GuestDAO();
            BookingDAO bDAO = new BookingDAO();
            RoomDAO rDAO = new RoomDAO();
            int guestId = gDAO.insertGuest(
                    request.getParameter("txtname"),
                    request.getParameter("txtphone"),
                    request.getParameter("txtemail"),
                    request.getParameter("txtaddress"),
                    request.getParameter("txtidnumber"),
                    request.getParameter("txtbday")
            );
            if (guestId > 0) {
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String status = checkIn.equals(today) ? "Checked-in" : "Reserved";
                if (bDAO.insertBooking(guestId, roomId, checkIn, checkOut, today, status) >= 1) {
                    rDAO.updateRoomStatus(roomId, "Occupied");
                    request.setAttribute("WARNING", "Booking successfully created.");
                }
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Booking failed: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("GetRoomController").forward(request, response);
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
