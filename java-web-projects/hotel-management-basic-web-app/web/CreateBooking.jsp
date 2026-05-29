<%-- 
    Document   : CreateBooking
    Created on : Dec 3, 2025, 9:06:28 AM
    Author     : trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
        <title>New Booking</title>
    </head>
    <body>
        <h1 class="title-text">Create New Booking</h1>
        <form action="CreateBookingController" method="post">
            <c:if test="${not empty requestScope.ERROR}"><div class="error-msg">${requestScope.ERROR}</div></c:if>
            <c:if test="${not empty requestScope.WARNING}"><div class="warning-msg">${requestScope.WARNING}</div></c:if>
            <h3>Guest Details</h3>
            <input type="text" name="txtname" placeholder="Full Name" required>
            <input type="text" name="txtphone" placeholder="Phone Number" required>
            <input type="email" name="txtemail" placeholder="Email Address" required>
            <input type="text" name="txtaddress" placeholder="Address" required>
            <input type="text" name="txtidnumber" placeholder="ID Number" required>
            <label>Date of Birth</label>
            <input type="date" name="txtbday" required>
            <h3>Stay Information</h3>
            <label>Check-in Date</label>
            <input type="date" name="txtcheckin" required>
            <label>Check-out Date</label>
            <input type="date" name="txtcheckout" required>
            <label>Room Assignment</label>
            <select name="txtroomid" required>
                <c:forEach var="room" items="${requestScope.AVAILABLE_ROOMS}">
                    <option value="${room.roomId}">Room ${room.roomNumber} [${room.roomTypeName}] - $${room.pricePerNight}</option>
                </c:forEach>
            </select>
            <div class="button-container">
                <input type="submit" value="Create Booking">
            </div>
        </form>
        <a href="receptionistDashboard.jsp" class="back-link">Return to Dashboard</a>
    </body>
</html>