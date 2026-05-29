 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/style.css">
        <title>Hotel Receptionist Login</title>
    </head>
    <body>
        <h1 class="title-text">Hotel Management System for Receptionist</h1>
        <form action="LoginController" method="post">
            <h2>Receptionist Login</h2>
            <c:if test="${not empty requestScope.ERROR_MESSAGE}">
                <div class="error-msg">${requestScope.ERROR_MESSAGE}</div>
            </c:if>
            <label>Username</label>
            <input type="text" name="txtusername" required>
            <label>Password</label>
            <input type="password" name="txtpassword" required>
            <div class="button-container">
                <input type="submit" value="Login">
            </div>
        </form>
    </body>
</html>