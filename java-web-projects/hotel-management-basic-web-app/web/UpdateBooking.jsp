<%-- 
    Document   : UpdateBooking
    Created on : Dec 9, 2025, 3:27:42 PM
    Author     : trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
        <title>Update Booking</title>
    </head>
    <body>
        <h1 class="title-text">Update Booking</h1>
        <c:if test="${not empty requestScope.BOOKING_DATA}">
            <form action="UpdateBookingController" method="post">
                <c:if test="${not empty requestScope.ERROR}"><div class="error-msg">${requestScope.ERROR}</div></c:if>
                <c:if test="${not empty requestScope.WARNING}"><div class="warning-msg">${requestScope.WARNING}</div></c:if>
                <input type="hidden" name="bookingid" value="${BOOKING_DATA.bookingId}">
                <input type="hidden" name="action" value="update">
                <h3>Update Booking #${BOOKING_DATA.bookingId}</h3>
                <label>Check-in Date</label>
                <input type="date" name="txtcheckin" value="${BOOKING_DATA.checkInDate}" required>
                <label>Check-out Date</label>
                <input type="date" name="txtcheckout" value="${BOOKING_DATA.checkOutDate}" required>
                <label>Room Selection</label>
                <select name="txtroomid" required>
                    <option value="${BOOKING_DATA.roomId}">Current: Room ${not empty BOOKING_DATA.roomNumber ? BOOKING_DATA.roomNumber : 'Unassigned'}</option>
                    <c:forEach var="room" items="${AVAILABLE_ROOMS}">
                        <c:if test="${room.roomId != BOOKING_DATA.roomId}">
                            <option value="${room.roomId}">New: Room ${room.roomNumber} [${room.roomTypeName}]</option>
                        </c:if>
                    </c:forEach>
                </select>
                <div class="button-container">
                    <input type="submit" value="Save Updates">
                </div>
            </form>
        </c:if>
        <a href="ManageBooking.jsp" class="back-link">Return to Management</a>
    </body>
</html>