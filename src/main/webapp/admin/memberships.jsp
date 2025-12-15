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
    
    <h2>Memberships</h2>
<hr/>

<form method="get" action="/admin/memberships" class="mb-3">
    <label for="status">Filter by Status:</label>

    <select name="status" id="status" onchange="this.form.submit()">
        <option value="">All</option>

        <option value="ACTIVE"
            <c:if test="${selectedStatus eq 'ACTIVE'}">selected</c:if>>
            ACTIVE
        </option>

        <option value="PENDING"
            <c:if test="${selectedStatus eq 'PENDING'}">selected</c:if>>
            PENDING
        </option>

        <option value="EXPIRED"
            <c:if test="${selectedStatus eq 'EXPIRED'}">selected</c:if>>
            EXPIRED
        </option>
    </select>
</form>


<table class="table table-dark table-striped">
    <thead>
        <tr>
            <th>User</th>
            <th>Subscription ID</th>
            <th>Plan</th>
            <th>Status</th>
            <th>Start</th>
            <th>End</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="m" items="${memberships}">
            <tr>
                <td>${m.username}</td>
                <td>${m.subscriptionId}</td>
                <td>${m.planId}</td>
                <td>
                    <c:choose>
                        <c:when test="${m.status eq 'ACTIVE'}">
                            <span class="badge bg-success">ACTIVE</span>
                        </c:when>
                        <c:when test="${m.status eq 'PENDING'}">
                            <span class="badge bg-warning text-dark">PENDING</span>
                        </c:when>
                        <c:when test="${m.status eq 'CANCELLED'}">
                            <span class="badge bg-danger">CANCELLED</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">EXPIRED</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${m.startDate}</td>
                <td>${m.endDate}</td>
                <td>
                    <!-- ✅ ONLY PENDING memberships can be resolved -->
                    <c:choose>

                        
                        <c:when test="${m.status eq 'PENDING'}">
                            <form method="post"
                                action="/admin/membership/resolve"
                                onsubmit="return confirm('Resolve membership from PayPal?');">

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="hidden" name="subscriptionId" value="${m.subscriptionId}" />

                                <button type="submit" class="btn btn-sm btn-warning">
                                    Resolve
                                </button>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <button class="btn btn-sm btn-secondary" disabled>
                                Resolve
                            </button>
                        </c:otherwise>

                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
    
    
    <%@ include file="../footer2.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>
</html>