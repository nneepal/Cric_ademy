<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - CRICADEMY</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />

    <!-- Internal CSS for validation messages -->
    <style>
        .field-error {
            color: red;
            font-size: 0.85em;
            margin-top: 5px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="login-wrapper">
        <div class="login-left">
            <img src="${pageContext.request.contextPath}/resources/images/system/loginbanner2.png" alt="Login to Cricademy">
        </div>
        <div class="login-right">
            <h1 class="brand-title">CRICADEMY</h1>
            <div class="login-card">
                <h2>Login</h2>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" 
                           value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" required>
                    <% if (request.getAttribute("usernameError") != null) { %>
                        <div class="field-error"><%= request.getAttribute("usernameError") %></div>
                    <% } %>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                    <% if (request.getAttribute("passwordError") != null) { %>
                        <div class="field-error"><%= request.getAttribute("passwordError") %></div>
                    <% } %>

                    <button type="submit">Login</button>
                </form>

                <div class="extra-links">
                    <a href="${pageContext.request.contextPath}/forgotpassword.jsp" class="forgot-link">Forgot Password</a>
                    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register" class="register-link">Register</a></p>
                </div>
                <p class="site-footer">www.cricademy.com</p>
            </div>
        </div>
    </div>
</body>
</html>
