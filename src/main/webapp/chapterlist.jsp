<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<meta name="google-adsense-account" content="ca-pub-3020770276580291">
<title>Chapter list</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
    <link rel="stylesheet" href="css/stylesheet.css" >
</head>
<body style= "background-color:white; color: black">

<div class="starter-template">
   <h1>List of All The Chapter in the Novel:</h1>
   <a class="btn btn-primary" href="novellist">Novel List</a>   <a class="btn btn-primary" href="activenovellist">Active Novel List</a> 
   <br>
   <a><b>Stock:</b>${stock}</a><br>
   <a><b>Change the novel Status:</b></a><br>
   <form method="post" action="novelstatus">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input readonly="true" style="display:none;" name="novelname" value="${novelname}" />
    <select name="status">
      <option value="ONGOING">Ongoing</option>
      <option value="COMPLETED">Completed</option>
      <option value="HIATUS">Hiatus</option>
      <option value="Dropped">Dropped</option>
    </select>
    <input type="submit" value="Change Status" class="btn btn-primary"/> 
    </form>
   <table class="table table-striped table-hover table-condensed table-bordered">
    <tr>
     <th>Chapter Id</th>
     <th>Novel Name</th>
     <th>Chapter Number</th>
     <th>Chapter Title</th>
     <th>Posted On</th>
     <th>Keywords</th>
     <th>Release Status</th>
     <th>Released On</th>
     <th class="center">Chapter Content</th>
     <th>Preview</th>
     <th>Delete</th>
     
    </tr>
    <c:forEach var="chapter" items="${Chapters}">
     <tr>
      <td>${chapter.chapterId}</td>
      <td class ="Dscroll">${chapter.novelName}</td>
      <td>${chapter.chapterNo}</td>
      <td>${chapter.title}</td>
      <td>${chapter.postedOn}</td>
      <td class ="Dscroll">${chapter.keywords}</td>
      <td>${chapter.releaseStatus}</td>
      <td>${chapter.releasedOn}</td>
      <td class ="Dscroll">${chapter.content}</td>
      <td><a href="chapter/${chapter.chapterId}" class="btn btn-primary"> View</a></td> 
      <td><form method="post" action="deletechapter">
      		<input name="ChapterId"  value="${chapter.chapterId}" readonly="true" style="display:none;"/>
      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      		<input class ="btn btn-primary" type="submit" value="Delete" />
      		</form>
      </td> 
     </tr>
    </c:forEach>
   </table>
  </div>
  <h3>Upload a New Chapter/Edit an old Chapter</h3>
  <h4>Click Here to Switch:</h4><button id="UES" onclick="EditSwitch()">Upload</button>
  
  <form:form method="post" action="savechapter">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  <form:label path="ChapterId" style="display:none;" id="IdLable">Chapter Id</form:label>
  <form:input path="ChapterId" disabled="true" style="display:none;" id="Id"/>
  <form:label path="novelName">Name Of The Novel</form:label>
  <form:input path="novelName" value="${novelname}" readonly="true"/>
  <form:label path="chapterNo">Chapter No:</form:label>
  <form:input path="chapterNo" />
  <form:label path="title">Title Of The Chapter</form:label>
  <form:input path="title"/>
  <form:label path="keywords">Keywords</form:label>
  <form:input path="keywords"/>
  <form:label path="releaseStatus">Release Status</form:label>
  <form:select path="releaseStatus">
    <form:option value="RELEASED">Released</form:option>
    <form:option value="STOCKPILE">Stockpile</form:option>
  </form:select>
  <form:label path="content">Content</form:label>
  <form:textarea path="content" rows = "5" cols = "180"/>
  <input type="submit" value="post Chapter" />
  </form:form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script> 
</body>
</html>