<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>About Us - CRICADEMY</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/about.css" />
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="about-container">
        <div class="about-card">
            <img src="${pageContext.request.contextPath}/resources/images/system/about.png" alt="About Cricademy" class="card-img">
            <div class="card-text">
                <h2>About Cricademy</h2>
                <p>
                    CRICADEMY is a premier cricket academy dedicated to nurturing talent and building champions. 
                    With experienced coaches, modern facilities, and a vision to empower athletes, 
                    we’re reshaping the future of cricket training in Nepal.
                </p>
            </div>
        </div>

        <div class="about-card">
            <img src="${pageContext.request.contextPath}/resources/images/system/about2.png" alt="Achievements" class="card-img">
            <div class="card-text">
                <h2>Achievements and History</h2>
                <p>
                    Over the years, CRICADEMY has produced players who've represented the national team,
                    won major youth tournaments, and been recognized for excellence in cricket development. 
                    We started with a passion — now we’re making history.
                </p>
            </div>
        </div>

        <div class="founder-section">
            <h2 class="founder-title">Founders</h2>
            <div class="founders">
                <div class="founder-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/arpan.png" alt="Founder 1" class="founder-img">
                    <h3>Arpan Nepal</h3>
                    <p>Chief Operating Officer.</p>
                </div>
                <div class="founder-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/aashu.png" alt="Founder 2" class="founder-img">
                    <h3>Aashutosh Dhakal</h3>
                    <p>Visionary sports entrepreneur and mentor committed to youth cricket empowerment.</p>
                </div>
                <div class="founder-card">
                    <img src="${pageContext.request.contextPath}/resources/images/system/piyush.png" alt="Founder 3" class="founder-img">
                    <h3>Piyush Karn</h3>
                    <p>Experienced trainer focused on technical and mental aspects of modern cricket.</p>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp" %>
</body>
</html>
