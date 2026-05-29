<%-- 
    Document   : ManageBooking
    Created on : Dec 9, 2025, 7:42:00 AM
    Author     : trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
        <title>Manage Bookings</title>
    </head>
    <body>
        <h1 class="title-text">Search Booking to Manage</h1>
        <c:if test="${not empty requestScope.ERROR}">
            <div class="message-container error-msg">${requestScope.ERROR}</div>
        </c:if>
        <c:if test="${not empty requestScope.WARNING}">
            <div class="message-container warning-msg">${requestScope.WARNING}</div>
        </c:if>
        <form action="SearchController" method="get">
            <select name="searchType">
                <option value="bookingDate" ${param.searchType == 'bookingDate' ? 'selected' : ''}>By Booking Date</option>
                <option value="checkInDate" ${param.searchType == 'checkInDate' ? 'selected' : ''}>By Check-in Date</option>
            </select>
            <input type="date" name="txtsearch" value="${param.txtsearch}" required>
            <div class="button-container">
                <input type="submit" value="Search">
            </div>
        </form>
        <c:choose>
            <c:when test="${not empty requestScope.RESULT}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Guest Name</th>
                            <th>Room</th>
                            <th>Check-in</th>
                            <th>Check-out</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${requestScope.RESULT}">
                            <tr>
                                <td>${b.bookingId}</td>
                                <td>${b.guestFullName}</td>
                                <td>${b.roomNumber}</td>
                                <td>${b.checkInDate}</td>
                                <td>${b.checkOutDate}</td>
                                <td><span class="status-${b.status.toLowerCase().replace(' ', '')}">${b.status}</span></td>
                                <td>
                                    <div class="action-cell">
                                        <c:if test="${b.status eq 'Reserved'}">
                                            <a href="CheckInOutController?action=CheckIn&bookingid=${b.bookingId}&txtsearch=${param.txtsearch}" class="btn-small">Check-In</a>
                                            <a href="UpdateBookingController?bookingid=${b.bookingId}" class="btn-small">Update</a>
                                        </c:if>
                                        <c:if test="${b.status eq 'Checked-in'}">
                                            <a href="CheckInOutController?action=CheckOut&bookingid=${b.bookingId}&txtsearch=${param.txtsearch}" class="btn-small">Check-Out</a>
                                        </c:if>
                                        <c:if test="${b.status ne 'Canceled' and b.status ne 'Checked-out'}">
                                            <a href="CancelBookingController?bookingid=${b.bookingId}&txtsearch=${param.txtsearch}" 
                                               class="btn-small btn-danger" 
                                               onclick="return confirm('Cancel booking #${b.bookingId}?');">Cancel</a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:when test="${not empty param.txtsearch and empty requestScope.RESULT}">
                <div class="no-matches">
                    <p>No bookings found for the date: <strong>${param.txtsearch}</strong></p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="info-box">
                    <p>Please select a date and click "Search" to view and manage bookings.</p>
                </div>
            </c:otherwise>
        </c:choose>
        <a href="receptionistDashboard.jsp" class="back-link">Return to Dashboard</a>
    </body>
</html>