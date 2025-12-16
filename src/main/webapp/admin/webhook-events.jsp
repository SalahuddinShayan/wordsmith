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
<script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
 <title>User Registration</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


<%@ include file="../nav2.jsp" %>
    
    <h2>Webhook Events</h2>

<table class="table table-striped table-dark">
    <thead>
        <tr>
            <th>Event ID</th>
            <th>Event Type</th>
            <th>Received At</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="e" items="${events}">
            <tr>
                <td>${e.eventId}</td>
                <td>${e.eventType}</td>
                <td>${e.receivedAt}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<c:if test="${empty events}">
    <p>No webhook events recorded.</p>
</c:if>
    
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>