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
    <h2>${novelName} — Chapter ${chapterNo}</h2>
    <p>${chapterTitle}</p>

    <hr>

    <p>Price: <b>${price}</b> coins</p>
    <p>Your balance: <b>${walletBalance}</b> coins</p>

    <c:choose>
    <c:when test="${!canAfford}">
        <p class="text-danger">Insufficient balance</p>
        <a href="/coin" class="btn btn-primary">Buy Coins</a>
        <a href="/novel/${novelName}" class="btn btn-secondary">Cancel</a>
    </c:when>

    <c:otherwise>
        <form method="post" action="/chapter/${chapterId}/purchase">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <p>Are you sure you want to purchase this chapter?</p>
        <button type="submit" class="btn btn-success">
            Confirm Purchase
        </button>
        <a href="/novel/${novelName}" class="btn btn-secondary">Cancel</a>
        </form>
    </c:otherwise>
    </c:choose>
    </div>
    </div>

    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>