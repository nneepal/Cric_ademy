<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.cricademy.model.Player" %>
<%@ include file="header.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/players.css">

<%
    List<Player> players = (List<Player>) request.getAttribute("players");
    String searchQuery = (String) request.getAttribute("search");
    if (searchQuery == null) {
        searchQuery = "";
    }
%>

<div class="container">
    <h1>Our Players</h1>

    <!-- Search form -->
    <form method="get" action="<%= request.getContextPath() %>/players" class="search-form">
        <input 
            type="text" 
            name="search" 
            placeholder="Search players by name..." 
            value="<%= searchQuery %>" 
            class="search-input"
        />
        <button type="submit" class="search-button">Search</button>
    </form>

    <div class="card-container">
        <% if (players != null && !players.isEmpty()) {
               for (Player player : players) { %>
            <div class="player-card">
                <div class="player-info">
                    <h2><%= player.getPlayerName() %></h2>
                    <p><strong>Email:</strong> <span><%= player.getPlayerEmail() %></span></p>
                    <p><strong>Phone:</strong> <span><%= player.getPhone() %></span></p>
                    <p><strong>Runs:</strong> <span><%= player.getPlayerRuns() %></span></p>
                </div>
            </div>
        <% } 
           } else { %>
            <p class="no-players">No players found.</p>
        <% } %>
    </div>
</div>

<%@ include file="footer.jsp" %>