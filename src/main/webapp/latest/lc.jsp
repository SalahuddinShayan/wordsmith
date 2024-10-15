<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach var="chapter" items="${Chapters}">
<div class = "row">
<div class="col-6 py-1 py-lg-2 stm oneliner"><a href="chapter/${chapter.chapterId}">Chapter ${chapter.chapterNo}: ${chapter.title}</a></div>
<div class="col-6 py-1 py-lg-2 right"><fmt:formatDate pattern="dd MMM"  value="${chapter.postedOn}" /></div>
</div>
</c:forEach>