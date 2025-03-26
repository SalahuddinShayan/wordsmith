<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    String role = (String) session.getAttribute("userRole");
    if (role == null || (!role.equals("ADMIN") && !role.equals("TRANSLATOR") && !role.equals("EDITOR"))) {
        response.sendRedirect("home.jsp");
    }
%>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Welcome, ${sessionScope.loggedInUser.username}!</h2>
    <p>Your Role: ${sessionScope.userRole}</p>
    <a href="/auth/logout">Logout</a>
</body>
</html>