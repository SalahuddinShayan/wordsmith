<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Message Sent</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
         
</head>
<body>

<%@ include file="nav1.jsp" %>
        
        <div  Style = "margin-top: 30px;" class = "center"><h2>Thanks You For Your Valuable feedback.</h2></div>
        
		<div  Style = "margin-top: 30px;" class = "center">
		<a href="home" class="btn btn-primary"><i class="fa-solid fa-house"></i> Home</a>
		<a>|</a>
		<a href="contactus" class="btn btn-primary">Send another Message</a>
		</div>
        
        
<%@ include file="footer1.jsp" %>
        
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>