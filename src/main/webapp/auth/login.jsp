<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/stylesheet.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body Style= "background-color: #2D3047; color: white;">
    
    
    <%@ include file="../nav2.jsp" %>
    
    <div class="auth-container">
    <div class="auth-box text-white">
    <h3>Login</h3>
    
    <%-- Show success message if registration was successful --%>
<c:if test="${not empty success}">
    <p style="color: green;">${success}</p>
</c:if>

<%-- Show error message if login failed --%>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>
    
    
    <form action="/auth/login" method="post">
    <div class="mb-3">
        <label>Username or Email:</label>
        <input type="text" name="identifier" class="form-control" required />
        </div>
        <div class="mb-3">
        <label>Password:</label>
        <input type="password" name="password" required />
        </div>
        
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button type="submit" class="btn btn-primary w-100">Login</button>
    </form>
    <p class="text-center mt-3">
            Don't have an account? <a href="/auth/register" class="text-info">Register Here</a><br>
            Forgot Password? <a href="/auth/forgot-password" class="text-info">Click Here</a>            
    </p>
    </div>
    </div>

    <!-- 🔥 Display error message if login fails -->
    <% if (request.getParameter("error") != null) { %>
        <p style="color: red;">Invalid username/email or password!</p>
    <% } %>
    <p>${error}</p>
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>