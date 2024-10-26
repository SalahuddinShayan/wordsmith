<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Eastern WordSmith</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>

<nav class="navbar navbar-light bg-az navbar-expand-md sticky-top" >
            <a href="home" class="navbar-brand" style="vertical-align:center;margin:0px 0px 0px 50px"><img src="images/logo2.png" width="40" height="40"  alt=""></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbar" >
                <span class="navbar-toggler-icon"></span>
            </button>

            
            <div class="navbar-collapse collapse" id="navbar" >
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="home" class="nav-link">Home</a></li>
                    <li class="nav-item"><a href="novels" class="nav-link">All Novels</a></li>
                    <li class="nav-item"><a href="updates" class="nav-link">Updates</a></li>
                    <li class="nav-item"><a href="contactus" class="nav-link">Contact Us</a></li>
                </ul>          
            </div>
        </nav>
        
        <div  Style = "margin-top: 30px;" class = "center"><h2>Thanks You For Your Valuable feedback.</h2></div>
        
		<div  Style = "margin-top: 30px;" class = "center">
		<a href="home" class="btn btn-primary"><i class="fa-solid fa-house"></i> Home</a>
		<a>|</a>
		<a href="contactus" class="btn btn-primary">Send another Message</a>
		</div>
        
        
        <footer class ="stm">
         <div  Style = "margin-top: 30px;" class = "row">
          <div class="col-1"></div>
          <div class="col-10 center bdt">
           <a href="imageslogo">Images&Logo</a>
           <a>|</a>
           <a href="novels">Novels</a>
           <a>|</a>
           <a href="contactus">Contact Us</a>
          </div>
          <div class="col-1"></div>
         </div>
        </footer>
        
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>