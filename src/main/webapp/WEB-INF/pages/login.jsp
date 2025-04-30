<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - CRICADEMY</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css" />
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

                <!-- Display error message if credentials are incorrect -->
                <% 
                    String loginError = (String) request.getAttribute("loginError");
                    if (loginError != null && !loginError.isEmpty()) {
                %>
                    <div class="error-message">
                        <p><%= loginError %></p>
                    </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/login" method="post">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
  
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
