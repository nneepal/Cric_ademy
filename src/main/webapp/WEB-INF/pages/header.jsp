<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
    <div class="navbar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/home">CRICADEMY</a>
        </div>

        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/home" class="active">Home</a>
            <a href="${pageContext.request.contextPath}/about" class="active">About</a>
            <a href="${pageContext.request.contextPath}/contact" class="active">Contact</a>
            <a href="${pageContext.request.contextPath}/admin" class="active">Admin</a>
        </div>

        <div class="search-profile">
            <input type="text" placeholder="Search..." />
            <a href="${pageContext.request.contextPath}/login" class="login-link">
                <img alt="Login" src="${pageContext.request.contextPath}/resources/images/system/login.png" />
            </a>
        </div>
    </div>
</body>
</html>
