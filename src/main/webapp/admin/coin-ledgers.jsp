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
 <title>Coin Ledger</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


    <%@ include file="../nav2.jsp" %>

    <div class="container mt-4">
    <h3>📜 Coin Ledger</h3>
    <hr>

    <table class="table table-striped table-bordered">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>User</th>
            <th>Δ Coins</th>
            <th>Reason</th>
            <th>Reference</th>
            <th>Balance After</th>
            <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="l" items="${ledgers}">
            <tr>
                <td>${l.id}</td>
                <td>${l.username}</td>
                <td class="${l.delta < 0 ? 'text-danger' : 'text-success'}">
                    ${l.delta}
                </td>
                <td>${l.reason}</td>
                <td>
                    <c:if test="${not empty l.referenceType}">
                        ${l.referenceType} (${l.referenceId})
                    </c:if>
                </td>
                <td>${l.balanceAfter}</td>
                <td>${l.createdAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty ledgers}">
        <p class="text-muted">No ledger entries found.</p>
    </c:if>
</div>





     <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>