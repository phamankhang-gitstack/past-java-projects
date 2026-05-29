/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.StaffDAO;
import dto.Staff;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
public class LoginController extends HttpServlet {

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "index.jsp";
        try {
            String username = request.getParameter("txtusername");
            String password = request.getParameter("txtpassword");
            StaffDAO staffDAO = new StaffDAO();
            Staff staff = staffDAO.checkLogin(username, password);
            if (staff != null) {
                if ("Receptionist".equals(staff.getRole())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", staff);
                    session.setAttribute("ROLE", "Receptionist");
                    url = "receptionistDashboard.jsp";
                } else {
                    // Not a receptionist
                    request.setAttribute("ERROR_MESSAGE", "Access Denied: Not a Receptionist.");
                }
            } else {
                // Login failure with invalid credentials
                request.setAttribute("ERROR_MESSAGE", "Invalid username or password.");
            }
        } catch (Exception e) {
            log("Error in LoginController", e);
            request.setAttribute("ERROR_MESSAGE", "An unexpected error occurred: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Prevent direct GET access to login logic
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles staff login.";
    }
}