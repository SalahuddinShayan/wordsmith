<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Release Policies</title>
</head>
<body>

<h2>Novel Release Policies</h2>

<a href="/admin/release-policy/new">➕ Add Policy</a>

<table border="1" style="border-collapse: collapse;">
    <style>
        table td, table th {
            padding: 6px;
        }
    </style>
    <tr>
        <th>Novel</th>
        <th>Active</th>
        <th>Chapters / Release</th>
        <th>Min Stockpile</th>
        <th>Release Hour (UTC)</th>
        <th>Last Release</th>
        <th>Actions</th>
    </tr>

<c:forEach items="${policies}" var="p">
    <tr>
        <td>${p.novelName}</td>
        <td>${p.active}</td>
        <td>${p.chaptersPerRelease}</td>
        <td>${p.minStockpileRequired}</td>
        <td>${p.releaseHour}</td>
        <td>${p.lastReleaseAt}</td>
        <td>
            <a href="/admin/release-policy/edit/${p.id}">✏️ Edit</a>
            <form action="/admin/release-policy/toggle/${p.id}" method="post" style="display:inline;">
                <button type="submit">
                    ${p.active ? "Pause" : "Enable"}
                </button>
            </form>
        </td>
    </tr>
</c:forEach>

</table>

</body>
</html>
