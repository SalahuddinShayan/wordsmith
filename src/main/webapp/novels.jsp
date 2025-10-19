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
        <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>
		 
</head>
<body>

<%@ include file="nav1.jsp" %>
        
        <!-- ✅ Top banner (728x90 for desktop / 320x50 for mobile) -->
  <div class="container text-center mt-3">
    <div class="d-none d-md-block">
      <script type="text/javascript">
        atOptions = {
          'key': '909738982dd00f426ae61300a03dc401',
          'format': 'iframe',
          'height': 90,
          'width': 728,
          'params': {}
        };
      </script>
      <script type="text/javascript" src="//www.highperformanceformat.com/909738982dd00f426ae61300a03dc401/invoke.js"></script>
    </div>
    <div class="d-block d-md-none">
      <script type="text/javascript">
        atOptions = {
          'key': 'faa56f41f1310cace7c00437b456859e',
          'format': 'iframe',
          'height': 50,
          'width': 320,
          'params': {}
        };
      </script>
      <script type="text/javascript" src="//www.highperformanceformat.com/faa56f41f1310cace7c00437b456859e/invoke.js"></script>
    </div>
  </div>
        
        
        <div  Style = "margin-top: 30px;" class = "row">
          <!-- ✅ Left Sidebar Ad (Load only on desktop) -->
         <div class="col-1 col-lg-3 py-3 ">
         <div id="leftSidebarAd" class = "sticky-ad">
          <script type="text/javascript">
	          atOptions = {
		          'key' : 'b9f5f7efab2763887c1ac6f86dcca5c8',
		          'format' : 'iframe',
		          'height' : 600,
		          'width' : 160,
		          'params' : {}
	          };
          </script>
          <script type="text/javascript" src="//www.highperformanceformat.com/b9f5f7efab2763887c1ac6f86dcca5c8/invoke.js"></script>

			</div>
         </div>
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
         <!-- ✅ Right Sidebar: 160x300 -->
         <div class="col-1 col-lg-3 py-3 ">
          <div id="rightSidebarAd" class = "sticky-ad">
            <script type="text/javascript">
	            atOptions = {
		            'key' : 'edd83a3d2aa632833a12fe13dff9971b',
		            'format' : 'iframe',
		            'height' : 300,
		            'width' : 160,
		            'params' : {}
	            };
            </script>
            <script type="text/javascript" src="//www.highperformanceformat.com/edd83a3d2aa632833a12fe13dff9971b/invoke.js"></script>
         </div>
         </div>
        </div>


        <!-- ✅ In-content 300x250 mid-section ad -->
      <div class="text-center mt-4">
        <script type="text/javascript">
          atOptions = {
            'key': 'e51cc6ba4468ff9ed2d28d4172eb88eb',
            'format': 'iframe',
            'height': 250,
            'width': 300,
            'params': {}
          };
        </script>
        <script type="text/javascript" src="//www.highperformanceformat.com/e51cc6ba4468ff9ed2d28d4172eb88eb/invoke.js"></script>
      </div>


        <!-- ✅ Bottom Ad (468x60 for desktop / 320x50 for mobile) -->
  <div class="container text-center mt-3">
    <div class="d-none d-md-block">
      <script type="text/javascript">
      atOptions = {
        'key': 'c137bf5820b877dfb9f4df89a80f0236',
        'format': 'iframe',
        'height': 60,
        'width': 468,
        'params': {}
      };
    </script>
    <script type="text/javascript" src="//www.highperformanceformat.com/c137bf5820b877dfb9f4df89a80f0236/invoke.js"></script>
    </div>
    <div class="d-block d-md-none">
      <script type="text/javascript">
        atOptions = {
          'key': 'faa56f41f1310cace7c00437b456859e',
          'format': 'iframe',
          'height': 50,
          'width': 320,
          'params': {}
        };
      </script>
      <script type="text/javascript" src="//www.highperformanceformat.com/faa56f41f1310cace7c00437b456859e/invoke.js"></script>
    </div>
  </div>
        
        
<%@ include file="footer1.jsp" %>
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>