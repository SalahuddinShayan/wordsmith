<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
    <title>Release Policy</title>
</head>
<body>

<h2>Release Policy</h2>

<form action="/admin/release-policy/save" method="post">

    <input type="hidden" name="id" value="${policy.id}" />

    <label>Novel Name</label><br>
    <select name="novelName" required>
        <c:forEach var="name" items="${novelnames}">
            <option value="${name}"
                <c:if test="${name == policy.novelName}">selected</c:if>>
                ${name}
            </option>
        </c:forEach>

    <label>Active</label>
    <input type="checkbox" name="active"
           ${policy.active ? "checked" : ""} /><br><br>

    <label>Chapters per Release</label><br>
    <input type="number" name="chaptersPerRelease"
           value="${policy.chaptersPerRelease}" min="1" /><br><br>

    <label>Minimum Stockpile</label><br>
    <input type="number" name="minStockpileRequired"
           value="${policy.minStockpileRequired}" min="1" /><br><br>

    <label>Release Hour (UTC)</label><br>
    <input type="number" name="releaseHour"
           value="${policy.releaseHour}" min="0" max="23" /><br><br>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <label>Release Type</label><br>
    <select name="releaseType" required>
        <option value="DAILY" ${policy.releaseType == 'DAILY' ? 'selected' : ''}>Daily</option>
        <option value="WEEKLY" ${policy.releaseType == 'WEEKLY' ? 'selected' : ''}>Weekly</option>
        <option value="CUSTOM" ${policy.releaseType == 'CUSTOM' ? 'selected' : ''}>Custom</option>
    </select><br><br>

    <label>Custom Interval (Days)</label><br>
    <input type="number" name="customIntervalDays"
           value="${policy.customIntervalDays}" min="1" /><br><br>

    <button type="submit">💾 Save</button>
    <a href="/admin/release-policy">Cancel</a>

</form>

</body>
</html>
