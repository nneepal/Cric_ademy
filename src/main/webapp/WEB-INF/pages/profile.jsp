<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile - Cricademy</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
<jsp:include page="header.jsp" />

<main class="profile-container">
    <h1 class="profile-title">User Profile</h1>
    <div class="profile-box">
        <div class="profile-left">
            <div class="profile-image-wrapper">
                <img src="${pageContext.request.contextPath}/resources/images/system/welcome.png" alt="Profile Image" class="profile-image">
                <form action="UploadImageServlet" method="post" enctype="multipart/form-data">
                    <input type="file" name="profileImage" accept="image/*" required>
                    <button type="submit" class="change-image-btn">Change Image</button>
                </form>
                <p class="profile-name">NAME</p>
            </div>
        </div>

        <div class="profile-right">
            <form action="UpdateProfileServlet" method="post">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>

                <label for="phone">Phone:</label>
                <input type="text" id="phone" name="phone" required>

                <label for="remarks">Remarks:</label>
                <input type="text" id="remarks" name="remarks">

                <button type="submit" class="update-btn">Update Information</button>
            </form>
        </div>
    </div>
</main>

    <jsp:include page="footer.jsp" />
</body>
</html>
