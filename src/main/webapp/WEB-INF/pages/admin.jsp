<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.cricademy.model.Player" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel | CRICADEMY</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>

<%@ include file="header.jsp" %>

<div class="admin-container">
    <div class="sidebar">
        <div class="sidebar-menu">
            <a href="#dashboard" class="tab-link active">DASHBOARD</a>
            <a href="#add" class="tab-link">ADD PLAYER</a>
            <a href="#update" class="tab-link">UPDATE PLAYER</a>
            <a href="#delete" class="tab-link">REMOVE PLAYER</a>
        </div>
    </div>

    <div class="main-content">
        <h2>Admin Dashboard</h2>

        <!-- Display error/success messages -->
        <%
            String error = (String) request.getAttribute("error");
            String success = (String) request.getAttribute("success");
            if (error != null) {
        %>
            <div class="message error"><%= error %></div>
        <% } else if (success != null) { %>
            <div class="message success"><%= success %></div>
        <% } %>

        <!-- Dashboard Tab -->
        <div class="tab-content" id="dashboard">
            <h3>All Players</h3>
            <table class="player-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Runs</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Player> players = (List<Player>) request.getAttribute("players");
                        if (players != null) {
                            for (Player player : players) {
                    %>
                        <tr>
                            <td><%= player.getPlayerName() %></td>
                            <td><%= player.getPlayerEmail() %></td>
                            <td><%= player.getPhone() %></td>
                            <td><%= player.getPlayerRuns() %></td>
                        </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!-- Add Player Tab -->
        <div class="tab-content" id="add">
            <form method="post" action="admin">
                <input type="hidden" name="action" value="add">
                <h3>Add New Player</h3>
                <input type="text" name="name" placeholder="Full Name" required>
                <input type="email" name="email" placeholder="Email Address" required>
                <input type="text" name="phone" placeholder="Phone (98XXXXXXXX)" required>
                <input type="number" name="runs" placeholder="Total Runs" required>
                <button type="submit" class="btn-primary">Add Player</button>
            </form>
        </div>

        <!-- Update Player Tab -->
        <div class="tab-content" id="update">
            <h3>Update Existing Player</h3>
            <table class="player-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Runs</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (players != null) {
                            for (Player player : players) {
                    %>
                        <tr>
                            <td>
                                <form method="post" action="admin">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="originalEmail" value="<%= player.getPlayerEmail() %>">
                                    <input type="text" name="name" value="<%= player.getPlayerName() %>" required>
                            </td>
                            <td>
                                    <input type="email" name="email" value="<%= player.getPlayerEmail() %>" required>
                            </td>
                            <td>
                                    <input type="text" name="phone" value="<%= player.getPhone() %>" required>
                            </td>
                            <td>
                                    <input type="number" name="runs" value="<%= player.getPlayerRuns() %>" required>
                            </td>
                            <td>
                                    <div class="action-buttons">
                                        <button type="submit" class="btn-primary">Update</button>
                                </form>

                                <form method="post" action="admin" onsubmit="return confirm('Are you sure you want to delete this player?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="email" value="<%= player.getPlayerEmail() %>">
                                </form>
                                    </div>
                            </td>
                        </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </div>

        <!-- Delete Player Tab -->
        <div class="tab-content" id="delete">
    <h3>Delete Player</h3>
    <table class="player-table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Runs</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (players != null) {
                    for (Player player : players) {
            %>
                <tr>
                    <td><%= player.getPlayerName() %></td>
                    <td><%= player.getPlayerEmail() %></td>
                    <td><%= player.getPhone() %></td>
                    <td><%= player.getPlayerRuns() %></td>
                    <td>
                        <form method="post" action="admin" onsubmit="return confirm('Are you sure you want to delete this player?');">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="email" value="<%= player.getPlayerEmail() %>">
                            <button type="submit" class="btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</div>

    </div>
</div>

<script>
    const links = document.querySelectorAll('.tab-link');
    const tabs = document.querySelectorAll('.tab-content');

    links.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            links.forEach(l => l.classList.remove('active'));
            tabs.forEach(tab => tab.style.display = 'none');

            this.classList.add('active');
            const activeTab = document.querySelector(this.getAttribute('href'));
            activeTab.style.display = 'block';
        });
    });

    // Set default active tab
    document.querySelectorAll('.tab-content').forEach(tab => tab.style.display = 'none');
    document.querySelector('#dashboard').style.display = 'block';
</script>

<%@ include file="footer.jsp" %>
</body>
</html>