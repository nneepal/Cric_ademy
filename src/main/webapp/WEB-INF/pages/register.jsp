<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - CRICADEMY</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
<div class="register-container">
    <form class="register-box" action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data">
        <h2>Create an Account</h2>

        <!-- Username -->
        <label>Username</label>
        <input type="text" name="username" value="${username != null ? username : ''}" />
        <div style="color:red;">${usernameError}</div>

        <!-- Email -->
        <label>Email</label>
        <input type="text" name="email" value="${email != null ? email : ''}" />
        <div style="color:red;">${emailError}</div>

        <!-- Password -->
        <label>Password</label>
        <input type="password" name="password" />
        <div style="color:red;">${passwordError}</div>

        <!-- Confirm Password -->
        <label>Confirm Password</label>
        <input type="password" name="confirmPassword" />
        <div style="color:red;">${confirmPasswordError}</div>

        <!-- Phone -->
        <label>Phone Number</label>
        <input type="text" name="phone" value="${phone != null ? phone : ''}" />
        <div style="color:red;">${phoneError}</div>

        <!-- Profile Image -->
        <label>Upload Profile Image</label>
        <input type="file" name="profileImage" accept="image/*" />
        <div style="color:red;">${imageError}</div>

        <!-- General Error -->
        <div style="color:red;">${error}</div>

        <button type="submit">Create Account</button>
        <p class="footer-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
        <p class="copyright">www.cricademy.com</p>
    </form>
</div>
</body>
</html>
