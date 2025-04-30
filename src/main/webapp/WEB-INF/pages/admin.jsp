<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | CRICADEMY</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>

<%@ include file="header.jsp" %>

<div class="admin-container">
    <div class="sidebar">
        <div class="sidebar-menu">
            <a href="#" class="active">HOME</a>
            <a href="#">UPDATE INFORMATION</a>
            <a href="#">ADD PLAYER</a>
            <a href="#">REMOVE PLAYER</a>
        </div>
    </div>

    <div class="main-content">
        <h2>Players</h2>
        
        <div class="card-container">
            <div class="card">
                <h3>DESCRIPTION</h3>
                <p>Player details and statistics will be displayed here.</p>
                <button class="btn-view">View Details</button>
            </div>
            
            <div class="card">
                <h3>DESCRIPTION</h3>
                <p>Player details and statistics will be displayed here.</p>
                <button class="btn-view">View Details</button>
            </div>
            
            <div class="card">
                <h3>DESCRIPTION</h3>
                <p>Player details and statistics will be displayed here.</p>
                <button class="btn-view">View Details</button>
            </div>
        </div>
        
        <div class="action-buttons">
            <button class="btn-primary">Add New Player</button>
            <button class="btn-secondary">Generate Report</button>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>