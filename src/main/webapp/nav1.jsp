<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<nav class="navbar navbar-light bg-az navbar-expand-md sticky-top container-fluid">
    <div class="container-fluid">
        <a href="https://easternwordsmith.com/" class="navbar-brand d-flex align-items-center">
            <img src="images/logo2.png" width="40" height="40" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbar">
            <ul class="navbar-nav">
                <li class="nav-item"><a href="https://easternwordsmith.com/" class="nav-link">Home</a></li>
                <li class="nav-item"><a href="../novels" class="nav-link">All Novels</a></li>
                <li class="nav-item"><a href="../updates" class="nav-link">Updates</a></li>
                <li class="nav-item"><a href="../contactus" class="nav-link">Contact Us</a></li>
            </ul>

            <!-- Right-side buttons for desktop -->
            <div class="ms-auto d-none d-md-flex align-items-center flex-wrap gap-2">
                <script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script>
                <script type='text/javascript'>kofiwidget2.init('Support us on Ko-fi', '#000000', 'Y8Y2163B9B');kofiwidget2.draw();</script>

                <%
                    com.wordsmith.Entity.User user = (com.wordsmith.Entity.User) session.getAttribute("loggedInUser");
                    String userRole = (String) session.getAttribute("userRole");
                    if (user != null) {
                %>
                    <div class="dropdown">
                        <button class="btn btn-primary dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                            <%= user.getUsername() %>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end bg-az" aria-labelledby="userDropdown">
                            <% if ("ADMIN".equals(userRole) || "TRANSLATOR".equals(userRole) || "EDITOR".equals(userRole)) { %>
                                <li><a class="dropdown-item" href="/dashboard">Dashboard</a></li>
                            <% } else { %>
                                <li><a class="dropdown-item" href="/profile">Profile</a></li>
                            <% } %>    
                            <li><a class="dropdown-item" href="/auth/logout">Logout</a></li>
                        </ul>
                    </div>
                <%
                    } else {
                %>
                    <a href="/auth/loginpage" class="btn btn-primary">Login / Register</a>
                <%
                    }
                %>
            </div>

            <!-- Mobile View -->
            <div class="d-md-none w-100 mt-3">
                <ul class="navbar-nav">
                    <li class="nav-item text-center mb-2">
                        <script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script>
                        <script type='text/javascript'>kofiwidget2.init('Support us on Ko-fi', '#000000', 'Y8Y2163B9B');kofiwidget2.draw();</script>
                    </li>
                    <li class="nav-item text-center">
                        <% if (user != null) { %>
                            <a class="btn btn-primary w-100 mt-2" href="/profile"><%= user.getUsername() %></a>
                            <% if ("ADMIN".equals(userRole) || "TRANSLATOR".equals(userRole) || "EDITOR".equals(userRole)) { %>
                                <a class="btn btn-secondary w-100 mt-2" href="/dashboard">Dashboard</a>
                            <% } %>
                            <a class="btn btn-danger w-100 mt-2" href="/auth/logout">Logout</a>
                        <% } else { %>
                            <a class="btn btn-primary w-100 mt-2" href="/auth/loginpage">Login / Register</a>
                        <% } %>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</nav>