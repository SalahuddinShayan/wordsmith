<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-0D3MMVLTED');
</script>
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="google-adsense-account" content="ca-pub-3020770276580291">
        <meta name="keywords" content="Eastern Word Smith, Eastern WordSmith, web novels, webnovels, novels, japanese novels, online novels, japanese webnovel">
        <title>Eastern WordSmith</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>

</head>
<body>

<nav class="navbar navbar-light bg-az navbar-expand-md sticky-top" >
            <a href="https://easternwordsmith.com/" class="navbar-brand" style="vertical-align:center;margin:0px 0px 0px 50px"><img src="images/logo2.png" width="40" height="40"  alt=""></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbar" >
                <span class="navbar-toggler-icon"></span>
            </button>

            
            <div class="navbar-collapse collapse  pe-2" id="navbar" >
                <ul class="navbar-nav">
                    <li class="nav-item"><a href="https://easternwordsmith.com/" class="nav-link">Home</a></li>
                    <li class="nav-item"><a href="../novels" class="nav-link">All Novels</a></li>
                    <li class="nav-item"><a href="../updates" class="nav-link">Updates</a></li>
                    <li class="nav-item"><a href="../contactus" class="nav-link">Contact Us</a></li>
                </ul>
                <p class="me-auto"><script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script><script type='text/javascript'>kofiwidget2.init('Support us on Ko-fi', '#000000', 'Y8Y2163B9B');kofiwidget2.draw();</script></p>          
            </div>
        </nav>
        
        <div  Style = "margin-top: 30px;" class = "row">
        	<div class="col-1 col-lg-2 py-3 "></div>
        	<div class="col-10 col-lg-8 bg-az">
        	<h5>Popular Today</h5>
        	</div>
        	<div class="col-1 col-lg-2 py-3 "></div>
        </div>
        
        <div class = "row">
        	<div class="col-2  py-3 "></div>
        	<div class="col-8  py-3 ">
        		<div class = "row">
           			<c:forEach var="novel" items="${Novels}">
           				<div class="col-6 col-lg-3 py-3 center stm oneliner">
           					<a href ="novel/${novel.novelName}">
               					<img width="100%" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" 
               					alt="images/No-Image.png" onerror="this.src='images/No-Image.png';"> 
             				</a><br>
             				<a href ="novel/${novel.novelName}">${novel.novelName}</a><br>
             				<c:import var="data" url="latest1/${novel.novelName}" charEncoding="UTF-8"/>  
			   				<c:out value="${data}" escapeXml="false"/>
           				</div>
           			</c:forEach>
         		</div>
         	</div>
        	<div class="col-2  py-3 "></div>
        </div>
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2"></div>
         <div class="col-10 col-lg-8 bg-az">
          <h4>Latest Updates:</h4>
         </div>
         <div class="col-1 col-lg-2"></div>
        </div>
        
        <div class = "row">
         <div class="col-1 col-lg-2 py-3 "></div>
         <div class="col-10 col-lg-8 py-3 bd-az">
          <div class = "row">
           <c:forEach var="novel" items="${Novelsu}">
            <div class="col-12 col-lg-6">
             <div class = "row">
              <div class="col-3  py-lg-3 center">
               <a href ="novel/${novel.novelName}">
                 <img class= "icont" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" 
                 alt="images/No_image_available.svg.png" onerror="this.src='images/No_image_available.svg.png';"> 
               </a>
              </div>
              <div class="col-9  py-lg-3">
               <h6 class ="stm oneliner"><a href ="novel/${novel.novelName}">${novel.novelName}</a></h6>
               <c:import var="data" url="latest/${novel.novelName}" charEncoding="UTF-8"/>  
			   <c:out value="${data}" escapeXml="false"/>
              </div>
             </div>
            </div>
           </c:forEach>
          </div> 
         </div>
         <div class="col-1 col-lg-2 py-3 "></div>
        </div>
        
        
<footer class ="stm">
         <div  Style = "margin-top: 30px;" class = "row">
          <div class="col-1"></div>
          <div class="col-10 center bdt">
           <a href="imageslogo">Images&Logo</a>
           <a>|</a>
           <a href="novels">Novels</a>
           <a>|</a>
           <a href="privacypolicy">Privacy Policy</a>
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