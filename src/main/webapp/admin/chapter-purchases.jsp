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
 <title>Chapter Purchases</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


    <%@ include file="../nav2.jsp" %>

    <div class="container mt-4">
    <h3>📘 Chapter Purchase History</h3>
    <hr>

    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>Novel</th>
            <th>Chapter</th>
            <th>Price (Coins)</th>
            <th>Purchased At</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${purchases}">
            <tr>
                <td>${p.id}</td>
                <td>${p.username}</td>
                <td>${p.novelName}</td>
                <td>Chapter ${p.chapterNo}</td>
                <td>${p.price}</td>
                <td>${p.purchasedAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty purchases}">
        <p class="text-muted">No chapter purchases found.</p>
    </c:if>





     <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>