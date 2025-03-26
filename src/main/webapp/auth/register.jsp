<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="../css/stylesheet.css"%></style>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<meta charset="UTF-8">
 <title>User Registration</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


<%@ include file="../nav2.jsp" %>
    
    <div class="auth-container">
    <div class="auth-box text-white">
    <h3>Register</h3>

<%-- Show error messages if any --%>
 <c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

    <form action="/auth/register" method="post">
     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
     <div class="mb-3">
    <label>Email:</label>
    <input type="email" name="email" required />
    </div>
    <div class="mb-3">
    <label>Username:</label>
    <input type="text" name="username" required />
    </div>
    <div class="mb-3">
    <label>Password:</label>
    <input type="password" name="password" required />
    </div>
    <button type="submit" class="btn btn-primary w-100">Register</button>
</form>
<p class="text-center mt-3">
            Already have an account? <a href="/auth/loginpage" class="text-info">Login Here</a>
    </p>
    </div>
    </div>

    <p>${message}</p>
    
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>