<%-- 
    Document   : receptionistDashboard
    Created on : Dec 9, 2025, 7:41:00 AM
    Author     : trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
        <title>Receptionist Dashboard</title>
    </head>
    <body>
        <c:if test="${sessionScope.LOGIN_USER == null}"><c:redirect url="index.jsp"/></c:if>
        <h1 class="welcome-text">Welcome, ${sessionScope.LOGIN_USER.fullName}</h1>
        <div class="menu-box">
            <h2>Receptionist Menu</h2>
            <div class="menu-actions">
                <a href="GetRoomController" class="btn-box">Create New Booking</a>
                <a href="ManageBooking.jsp" class="btn-box">Manage Bookings</a>
                <a href="LogoutController" class="btn-box">Logout</a>
            </div>
        </div>
    </body>
</html>