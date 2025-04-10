<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<html lang="en">
<head>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-0D3MMVLTED');
</script>
<meta charset="UTF-8">
<title>All Comments:</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
    <link rel="stylesheet" href="stylesheet.css" >
</head>
<body onload="LocalTimeZone()">

<div class="starter-template">
   <h1>All Comments:</h1>
   <table
    class="table table-striped table-hover table-condensed table-bordered">
    <tr>
     <th> Id</th>
     <th>Entity Type</th>
     <th>Entity Id</th>
     <th>User Name</th>
     <th>Content</th>
     <th>Has Replies</th>
     <th>flagged</th>
     <th>Created At</th>
     <th>Updated At</th>
     <th>Delete</th>
     
    </tr>
    <c:forEach var="comment" items="${Comments}">
     <tr>
      <td>${comment.id}</td>
      <td>${comment.entityType}</td>
      <td>${comment.entityId}</td>
      <td>${comment.userName}</td>
      <td>${comment.content}</td>
      <td>${comment.hasReplies}</td>
      <td>${comment.flagged}</td>
      <td>${comment.createdAt}</td>
      <td>${comment.updatedAt}</td>
      <td><form method="post" action="comments/delete">
      		<input name="MessageId"  value="${comment.id}" readonly="true" style="display:none;"/>
      		<input class ="btn btn-primary" type="submit" value="Delete" />
      		</form>
      </td>
     </tr>
    </c:forEach>
   </table>
  </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>