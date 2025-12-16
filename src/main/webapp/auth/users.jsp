<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<html lang="en">
<head>
  <script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-0D3MMVLTED');
</script>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>All Users:</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
    <link rel="stylesheet" href="stylesheet.css" >
</head>
<body onload="LocalTimeZone()">

<div class="starter-template">
   <h1>All Users:</h1>
   <table
    class="table table-striped table-hover table-condensed table-bordered">
    <tr>
     <th>User Id</th>
     <th>User Name</th>
     <th>User's Email</th>
     <th>password</th>
     <th>Role</th>
     <th>User since</th>
     <th>Last Login</th>
     <th>Delete</th>
     
    </tr>
    <c:forEach var="user" items="${Users}">
     <tr>
      <td>${user.id}</td>
      <td>${user.username}</td>
      <td>${user.email}</td>
      <td>${user.password}</td>
      <td>${user.role}</td>
      <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${user.createdAt}" /></td>
      <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${user.lastLoginTime}" /></td>
      <td><form method="post" action="deletemessage">
      		<input name="MessageId"  value="${user.id}" readonly="true" style="display:none;"/>
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