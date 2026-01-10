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
 <title>Admin Dashboard</title>
</head>
<body Style= "background-color: #2D3047; color: white;">


<%@ include file="../nav2.jsp" %>
    
    <h2>Admin Dashboard</h2>
    <hr/>

    <div class="row">
        <div class="col">
            <p><b>Total Memberships:</b> ${totalMemberships}</p>
            <p><b>Active:</b> ${activeMemberships}</p>
            <p><b>Pending:</b> ${pendingMemberships}</p>
            <p><b>Cancelled:</b> ${cancelledMemberships}</p>
        </div>
    </div>

    <hr/>

    <h3>Webhook Activity</h3>
    <p><b>Last Webhook Received:</b>
        <c:out value="${lastWebhookTime}" default="Never"/>
    </p>

    <hr/>

    <h3>Recent Webhook Failures (last 24h)</h3>

    <c:if test="${empty recentWebhookFailures}">
        <p>No webhook failures 🎉</p>
    </c:if>

    <c:if test="${not empty recentWebhookFailures}">
        <table border="1" cellpadding="5">
            <tr>
                <th>Event ID</th>
                <th>Type</th>
                <th>Subscription</th>
                <th>Error</th>
                <th>Time</th>
            </tr>

            <c:forEach items="${recentWebhookFailures}" var="f">
                <tr>
                    <td>${f.eventId}</td>
                    <td>${f.eventType}</td>
                    <td>${f.subscriptionId}</td>
                    <td>${f.errorMessage}</td>
                    <td>${f.createdAt}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>