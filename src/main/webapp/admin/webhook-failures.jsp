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
    
    <h2>Webhook Failures</h2>
<hr/>

<c:if test="${empty failures}">
    <p>No webhook failures recorded.</p>
</c:if>

<c:if test="${not empty failures}">
    <table border="1" cellpadding="5">
        <tr>
            <th>Event ID</th>
            <th>Type</th>
            <th>Subscription</th>
            <th>Error</th>
            <th>Time</th>
            <th>Action</th>
        </tr>

        <c:forEach items="${failures}" var="f">
            <tr>
                <td>${f.eventId}</td>
                <td>${f.eventType}</td>
                <td>${f.subscriptionId}</td>
                <td>${f.errorMessage}</td>
                <td>${f.createdAt}</td>
                <td>
                    <c:if test="${not empty f.subscriptionId}">
                        <form method="post" action="/admin/transactions/recover" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <input type="hidden" name="subscriptionId" value="${f.subscriptionId}" />

                            <button class="btn btn-sm btn-warning"
                                    onclick="return confirm('Recover transaction from PayPal?');">
                                Recover Transaction
                            </button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
    
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>