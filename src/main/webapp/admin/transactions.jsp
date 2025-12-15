<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="../css/stylesheet.css">
<meta charset="UTF-8">
 <title>User Registration</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


<%@ include file="../nav2.jsp" %>

    <h2>Payment Transactions</h2>
    <hr/>

    <c:if test="${empty transactions}">
        <p>No transactions found.</p>
    </c:if>

    <c:if test="${not empty transactions}">
        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Subscription</th>
                <th>Amount</th>
                <th>Currency</th>
                <th>Status</th>
                <th>Created</th>
            </tr>

            <c:forEach items="${transactions}" var="t">
                <tr>
                    <td>${t.id}</td>
                    <td>${t.username}</td>
                    <td>${t.subscriptionId}</td>
                    <td>${t.amount}</td>
                    <td>${t.currency}</td>
                    <td>${t.status}</td>
                    <td>${t.createdAt}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>