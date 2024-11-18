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
        <title>Eastern WordSmith</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
		 
<script type="text/javascript" data-cfasync="false">
/*<![CDATA[/* */
(function(){var o=window,f="e0c1aec43ba85c167e1b35ed321dc0d1",a=[["siteId",264+960-319-978+5148148],["minBid",0],["popundersPerIP","0"],["delayBetween",0],["default",false],["defaultPerDay",0],["topmostLayer","auto"]],l=["d3d3LmRpc3BsYXl2ZXJ0aXNpbmcuY29tL2dwb3N0YWwubWluLmNzcw==","ZDNtem9rdHk5NTFjNXcuY2xvdWRmcm9udC5uZXQvb2JweS90aW5kZXgubWluLmpz","d3d3LnFvdXN1anVoLmNvbS9kcG9zdGFsLm1pbi5jc3M=","d3d3LmRjYndrbW55ZW5ocS5jb20vei95aW5kZXgubWluLmpz"],d=-1,h,w,q=function(){clearTimeout(w);d++;if(l[d]&&!(1757414113000<(new Date).getTime()&&1<d)){h=o.document.createElement("script");h.type="text/javascript";h.async=!0;var y=o.document.getElementsByTagName("script")[0];h.src="https://"+atob(l[d]);h.crossOrigin="anonymous";h.onerror=q;h.onload=function(){clearTimeout(w);o[f.slice(0,16)+f.slice(0,16)]||q()};w=setTimeout(q,5E3);y.parentNode.insertBefore(h,y)}};if(!o[f]){try{Object.freeze(o[f]=a)}catch(e){}q()}})();
/*]]>/* */
</script>




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
         <div class="col-1 col-lg-3 py-3 "></div>
         <div class="col-10 col-lg-6 py-3 bd">
          <c:forEach var="novel" items="${Novels}">
           <div class = "row bdt">
            <div class="col-4 py-3 d-lg-none"></div>
            <div class="col-4 col-lg-3 py-3 center">
             <a href ="novel/${novel.novelName}">
               <img width="100%" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" 
               alt="images/No_image_available.svg.png" onerror="this.src='images/No_image_available.svg.png';"> 
             </a> 
             <a> ${novel.originalLanguage}  </a> <BR>
             <a>${novel.genre}  </a>
            </div>
            <div class="col-4 py-3 d-lg-none"></div>
            <div class="col-12 col-lg-9 py-3">
             <h6 class ="stm"><a href ="novel/${novel.novelName}">${novel.novelName}</a></h6>
             <a>${novel.status}  </a><br><br>
             <div class="">${novel.description}</div>
            </div>
           </div>
          </c:forEach>
         </div>
         <div class="col-1 col-lg-3 py-3 "></div>
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