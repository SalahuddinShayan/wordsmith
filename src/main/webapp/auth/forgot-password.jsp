<!-- forgot-password.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="../css/stylesheet.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body Style= "background-color: #2D3047; color: white;">

    <%@ include file="../nav2.jsp" %>


    <div class="auth-container">
        <div class="auth-box text-white">
            <h3>Forgot Password</h3>
            <form action="/auth/forgot-password" method="post">
                <label>Email Address</label>
                <input type="email" name="email" class="form-control" required>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button type="submit" class="btn btn-primary w-100 mt-3">Send OTP</button>
            </form>
        </div>
    </div>
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>