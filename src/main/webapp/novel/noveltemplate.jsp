<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Eastern WordSmith</title>
        <link rel="icon" type="image/x-icon" href="../images/Yin Yang Dragon.jpg">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="../css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>

<nav class="navbar navbar-light bg-az navbar-expand-md sticky-top" >
            <a href="home" class="navbar-brand" style="vertical-align:center;margin:0px 0px 0px 50px"><img src="../images/Yin Yang Dragon.jpg" width="40" height="40"  alt=""></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbar" >
                <span class="navbar-toggler-icon"></span>
            </button>

            
            <div class="navbar-collapse collapse" id="navbar" >
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="home" class="nav-link">Home</a></li>
                    <li class="nav-item"><a href="allnovels" class="nav-link">All Novels</a></li>
                    <li class="nav-item"><a href="Updates" class="nav-link">Updates</a></li>
                    <li class="nav-item"><a href="contactus" class="nav-link">Contact Us</a></li>
                </ul>          
            </div>
            
            <button id="DMS" onclick="lightSwitch()">Light Mode</button>
        </nav>
        
        <a href="https://www.vecteezy.com/free-vector/yin-yang-dragon">Yin Yang Dragon Vectors by Vecteezy</a>
        <a>test</a>
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>  
</body>
</html>