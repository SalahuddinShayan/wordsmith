<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="../css/stylesheet.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 <title>All The User Wallets</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


    <%@ include file="../nav2.jsp" %>

    <div class="container mt-4">
    <h3>💰 User Wallets</h3>
    <hr>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Balance (Coins)</th>
            <th>Created At</th>
            <th>Updated At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="w" items="${wallets}">
            <tr>
                <td>${w.id}</td>
                <td>${w.username}</td>
                <td>${w.balance}</td>
                <td>${w.createdAt}</td>
                <td>${w.updatedAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty wallets}">
        <p class="text-muted">No wallets found.</p>
    </c:if>
</div>





     <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>