<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Contact Us - Cricademy</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contact.css">
</head>
<body>

<jsp:include page="header.jsp" />

<main class="contact-container">
    <section class="contact-box">
        <h1 class="contact-heading">Contact <span>Us</span></h1>
        <p class="contact-subtitle">Feel free to reach out for any help, query, or feedback.</p>

        <form action="ContactServlet" method="post" class="contact-form">
            <div class="form-group">
                <label for="name">Your Name</label>
                <input type="text" id="name" name="name" required placeholder="Enter your name">
            </div>

            <div class="form-group">
                <label for="email">Your Email</label>
                <input type="email" id="email" name="email" required placeholder="Enter your email">
            </div>

            <div class="form-group">
                <label for="message">How can we help?</label>
                <textarea id="message" name="message" rows="5" required placeholder="Write your message here..."></textarea>
            </div>

            <button type="submit" class="send-button">SEND</button>
        </form>
    </section>
</main>

<jsp:include page="footer.jsp" />

</body>
</html>
