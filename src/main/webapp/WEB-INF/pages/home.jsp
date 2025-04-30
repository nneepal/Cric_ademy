<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
    <title>Home</title>
</head>
<body>
<jsp:include page="header.jsp" />

    <div class="welcome-section">
        <div class="welcome-text">
            <h2>Welcome to CRICADEMY</h2>
            <p>CRICADEMY is a premier cricket academy dedicated to nurturing cricket talent at all levels. Our state-of-the-art facilities and professional coaching staff are committed to developing the next generation of cricket stars.</p>
            <p>We provide comprehensive training programs, personalized coaching, and competitive exposure to help players achieve their full potential in the sport.</p>
            <a href="${pageContext.request.contextPath}/about" class="learn-more-btn">
			    Learn More About Us
			</a>
        </div>
        <img src="${pageContext.request.contextPath}/resources/images/system/welcome.png" alt="Welcome" class="welcome-img"/>
    </div>
    
	<div class="card-wrapper">
	    <div class="card">
	    	<h3>Players</h3>
	        <img src="${pageContext.request.contextPath}/resources/images/system/player.png" alt="Players">
	        
	        <p>Our players go through a well developed training plan and disciplinary activities. Thus, we are dedicated to produce excellent cricketers.</p>
	    </div>
	    <div class="card">
	    	<h3>Coaches</h3>
	        <img src="${pageContext.request.contextPath}/resources/images/system/coach.png" alt="Coaches">
	        
	        <p>Experienced coaches with plenty of experience on and off the pitch help a player to nurture into a cricketing mind. Our coaches are experienced and helpful.</p>
	    </div>
	    <div class="card">
	    	<h3>Facilities</h3>
	        <img src="${pageContext.request.contextPath}/resources/images/system/facility.png" alt="Facilities">
	        
	        <p>State-of-the-art training facilities here at cricademy provides players with excellent access to all the modern technology and tools which is key for their improvisation.</p>
	    </div>
	    <div class="card">
	    	<h3>Exposure</h3>
	        <img src="${pageContext.request.contextPath}/resources/images/system/exposure.png" alt="Exposure">
	        
	        <p>We provide national and international level exposure to our players to turn them into a all around player. This makes them competitive from a young age.</p>
	    </div>
	</div>

    <jsp:include page="footer.jsp" />
</body>
</html>

