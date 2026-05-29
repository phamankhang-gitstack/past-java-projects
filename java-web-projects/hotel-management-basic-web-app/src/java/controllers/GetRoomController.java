/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.RoomDAO;
import dto.Room;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name="GetRoomController", urlPatterns={"/GetRoomController"})
public class GetRoomController extends HttpServlet {
    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    try {
        RoomDAO rDAO = new RoomDAO();
        List<Room> availableRooms = rDAO.getAvailableRooms();
        request.setAttribute("AVAILABLE_ROOMS", availableRooms);
        request.getRequestDispatcher("CreateBooking.jsp").forward(request, response);
    } catch (Exception e) {
        log("Error in GetRoomController", e);
        request.setAttribute("ERROR", "Failed to load available rooms: " + e.getMessage());
        request.getRequestDispatcher("CreateBooking.jsp").forward(request, response);
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
