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
<title>Novel list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
    <link rel="stylesheet" href="stylesheet.css" >
</head>
<body onload="LocalTimeZone()">

<div class="starter-template">
   <h1>List of All The Novels:</h1>
   <table
    class="table table-striped table-hover table-condensed table-bordered">
    <tr>
     <th>Novel Id</th>
     <th>Novel Name</th>
     <th>Novel Image</th>
     <th>Author</th>
     <th>Original Language</th>
     <th>Genre</th>
     <th>Description</th>
     <th>Date Added</th>
     <th>Keywords</th>
     <th>Status</th>
     <th>View/Add Chapters</th>
     <th>Delete</th>
     
    </tr>
    <c:forEach var="novel" items="${Novels}">
     <tr>
      <td>${novel.novelId}</td>
      <td>${novel.novelName}</td>
      <td><img width="100" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" alt="images/No_image_available.svg.png" onerror="this.src='images/No_image_available.svg.png';"></td>
      <td>${novel.author}</td>
      <td>${novel.originalLanguage}</td>
      <td>${novel.genre}</td>
      <td><div class = "Dscroll">${novel.description}</div></td>
      <td><fmt:formatDate type="both" dateStyle="short" timeStyle="short"  value="${novel.addedOn}" /></td>
      <td>${novel.keywords}</td>
      <td>${novel.status}</td>
      <td><form method="post" action="chapterlist">
      		<input name="NovelName"  value="${novel.novelName}" readonly="true" style="display:none;"/>
      		<input class ="btn btn-primary" type="submit" value="View/Add Chapters" />
      		</form>
      </td> 
      <td><form method="post" action="deletenovel">
      		<input name="MemberId"  value="${novel.novelId}" readonly="true" style="display:none;"/>
      		<input class ="btn btn-primary" type="submit" value="Delete" />
      		</form>
      </td>
     </tr>
    </c:forEach>
   </table>
  </div>

<h1>Add a new Novel or Update an existing one </h1> ${timezone} ${timezone2}

<form method="post" action="savenovel" enctype="multipart/form-data">
       <input name="NovelId" placeholder= "NOVEL ID" type= "number" required>
       <input name="NovelName" placeholder= "Novel Name" type= "text" required>
       <input name="Author" placeholder= "Author of the Novel" type= "text" required>
       <lable for= "Pic">Image for the Novel</lable>
       <input name="Pic" placeholder= "Image for the Novel" type= "file" ><br>
       <lable for= "Description">Description:</lable>
       <textarea type="text" id="Description" name="Description" rows="2" class="form-control md-textarea" required></textarea>
       <input name="OriginalLanguage" placeholder= "Original Language" type= "text" required>
       <input name="Genre" placeholder= "Genre" type= "text" required>
       <input name="Keywords" placeholder= "Kewords" type= "text" required>
       <input name="Status" placeholder= "Status" type= "text" required>
       <input type="submit" value="Add Novel" />
       </form> 

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>
</body>
</html>