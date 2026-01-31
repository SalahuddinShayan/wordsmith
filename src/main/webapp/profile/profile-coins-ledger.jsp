<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <!DOCTYPE html>
  <html>

  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Coin Ledger: ${user.username}</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="../css/stylesheet.css">
</head>
<body>

<%@ include file="../nav2.jsp" %>

<div class="container mt-4 stm">

    <h2><span class="ew-coin" style="height: 35px; width: 35px;">
                                                <img src="/images/enso.svg" alt="EWS Coins">
                                                </span> Coin Ledger</h2>
    <p class="text-light">
        Complete history of coin credits and debits in your wallet.
    </p>

    <hr/>

    <c:if test="${empty coinLedger}">
        <div class="alert alert-info">
            No coin transactions found.
        </div>
    </c:if>

    <c:if test="${not empty coinLedger}">
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Date</th>
                        <th>Transaction</th>
                        <th>Amount</th>
                        <th>Balance After</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${coinLedger}">
                        <tr>
                            <td>
                                <c:out value="${entry.formattedCreatedAt != null 
                                    ? entry.formattedCreatedAt 
                                    : entry.createdAt}" />
                            </td>

                            <td>
                                <b>${entry.reason}</b>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${entry.delta > 0}">
                                        <span class="text-success">
                                            + <span class="ew-coin" style="height: 15px; width: 15px;">
                                                <img src="/images/enso.svg" alt="EWS Coins">
                                                </span> ${entry.delta}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger">
                                            − <span class="ew-coin" style="height: 15px; width: 15px;">
                                                <img src="/images/enso.svg" alt="EWS Coins">
                                                </span> ${entry.delta * -1}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <span class="ew-coin" style="height: 15px; width: 15px;">
                                                <img src="/images/enso.svg" alt="EWS Coins">
                                                </span> ${entry.balanceAfter}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <a href="/profile" class="btn btn-secondary mt-3">
        ← Back to Profile
    </a>

</div>

<%@ include file="../footer2.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>


</html>