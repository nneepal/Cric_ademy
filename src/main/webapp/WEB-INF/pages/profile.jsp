<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cricademy.model.PlayerModel" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Profile - Cricademy</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/profile.css">
</head>
<body>
<jsp:include page="header.jsp" />

<main class="profile-container">
    <h1 class="profile-title">User Profile</h1>

    <!-- Display success or error messages using scriptlet -->
    <%
        String successMessage = (String) request.getAttribute("successMessage");
        String errorMessage = (String) request.getAttribute("errorMessage");
        PlayerModel user = (PlayerModel) request.getAttribute("user");
    %>
    <% if (successMessage != null) { %>
        <div class="success-message"><%= successMessage %></div>
    <% } %>
    <% if (errorMessage != null) { %>
        <div class="error-message"><%= errorMessage %></div>
    <% } %>

    <div class="profile-box">
        <div class="profile-left">
            <div class="profile-image-wrapper">
                <img src="<%= request.getContextPath() + "/" + user.getProfileImage() %>" alt="Profile Image" class="profile-image">
                <p class="profile-name"><%= user.getUsername() %></p>
            </div>

            <!-- Logout Form -->
            <form action="<%= request.getContextPath() %>/logout" method="post">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
        </div>

        <div class="profile-right">
            <!-- Profile Update Form -->
            <form action="<%= request.getContextPath() %>/profile" method="post" enctype="multipart/form-data">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" placeholder="Enter new password" required>

                <label for="phone">Phone:</label>
                <input type="text" id="phone" name="phone" value="<%= user.getPhone() %>" required>

                <label for="profileImage">Change Profile Image:</label>
                <input type="file" id="profileImage" name="profileImage" accept="image/*">

                <button type="submit" class="update-btn">Update Information</button>
            </form>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp" />
</body>
</html>
