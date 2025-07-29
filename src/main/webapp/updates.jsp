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
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>
		 
</head>
<body>

<%@ include file="nav1.jsp" %>
        
        
        <!-- Conversant Media CODE for Eastern Wordsmith (Placement 953273c1) -->
          <div class="d-block d-lg-none text-center my-3" id="pubCodeContainer-id-245979-953273c1">
            <script type="text/javascript"
              src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
            <script>
              conversant.pubcode.loadOneAd({
                sid: 245979,
                id: "953273c1",
                location: "pubCodeContainer-id-245979-953273c1",
                format: [{ w: 320, h: 100 }]
              })
            </script>
          </div>
          <!-- Conversant Media CODE for Eastern Wordsmith (Placement 953273c1) -->


	<!-- Conversant Media CODE for Eastern Wordsmith (Placement fdb866a2) -->
	<div class="d-none d-lg-block text-center my-3" id="pubCodeContainer-id-245979-fdb866a2">
		<script type="text/javascript"
			src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
		<script>
        conversant.pubcode.loadOneAd({
            sid: 245979,
            id: "fdb866a2",
            location: "pubCodeContainer-id-245979-fdb866a2",
            format: [{w:970,h:250}]
        })
    </script>
	</div>
	<!-- Conversant Media CODE for Eastern Wordsmith (Placement fdb866a2) -->
	
		
		<!-- Conversant Media CODE for Eastern Wordsmith (Placement efe28409) -->
          <div class="text-center my-3" id="pubCodeContainer-id-245979-efe28409">
            <script type="text/javascript"
              src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
            <script>
              conversant.pubcode.loadOneAd({
                sid: 245979,
                id: "efe28409",
                location: "pubCodeContainer-id-245979-efe28409",
                format: [{ w: 728, h: 90 }]
              })
            </script>
          </div>
          <!-- Conversant Media CODE for Eastern Wordsmith (Placement efe28409) -->
        
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 py-3 ">
         <div class = "sticky-ad">
         	  <!-- Conversant Media CODE for Eastern Wordsmith (Placement 2964d05d) -->
              <div class="d-none d-lg-block text-center my-3" id="pubCodeContainer-id-245979-2964d05d">
                <script type="text/javascript"
                  src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
                <script>
                  conversant.pubcode.loadOneAd({
                    sid: 245979,
                    id: "2964d05d",
                    location: "pubCodeContainer-id-245979-2964d05d",
                    format: [{ w: 160, h: 600 }]
                  })
                </script>
              </div>
              <!-- Conversant Media CODE for Eastern Wordsmith (Placement 2964d05d) -->

			<!-- Conversant Media CODE for Eastern Wordsmith (Placement 210e6523) -->
			<div class="d-none d-lg-block text-center my-3" id="pubCodeContainer-id-245979-210e6523">
				<script type="text/javascript"
					src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
				<script>
        conversant.pubcode.loadOneAd({
            sid: 245979,
            id: "210e6523",
            location: "pubCodeContainer-id-245979-210e6523",
            format: [{w:300,h:600}]
        })
    </script>
			</div>
			<!-- Conversant Media CODE for Eastern Wordsmith (Placement 210e6523) -->
			</div>
         </div>
         <div class="col-10 col-lg-8 py-3 bd">
          <h2>Latest Updates:</h2>
          <div class = "row">
           <c:forEach var="novel" items="${Novels}">
            <div class="col-12 col-lg-6">
             <div class = "row">
              <div class="col-3  py-3 center">
               <a href ="novel/${novel.novelName}">
                 <img class= "icont" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" 
                 alt="images/No_image_available.svg.png" onerror="this.src='images/No_image_available.svg.png';"> 
               </a>
              </div>
              <div class="col-9  py-3">
               <h6 class ="stm oneliner"><a href ="novel/${novel.novelName}">${novel.novelName}</a></h6>
               <c:import var="data" url="latest/${novel.novelName}" charEncoding="UTF-8"/>  
			   <c:out value="${data}" escapeXml="false"/>
              </div>
             </div>
            </div>
           </c:forEach>
          </div> 
         </div>
         <div class="col-1 col-lg-2 py-3 ">
         <!-- Conversant Media CODE for Eastern Wordsmith (Placement 1d89bbaf) -->
			<div class="d-none d-lg-block text-center my-3 sticky-ad" id="pubCodeContainer-id-245979-1d89bbaf">
				<script type="text/javascript"
					src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
				<script>
        conversant.pubcode.loadOneAd({
            sid: 245979,
            id: "1d89bbaf",
            location: "pubCodeContainer-id-245979-1d89bbaf",
            format: [{w:300,h:1050}]
        })
    </script>
			</div>
			<!-- Conversant Media CODE for Eastern Wordsmith (Placement 1d89bbaf) -->
         </div>
        </div>
        
        
        <!-- Conversant Media CODE for Eastern Wordsmith (Placement ce1af472) -->
	<div class="text-center my-4" id="pubCodeContainer-id-245979-ce1af472">
		<script type="text/javascript"
			src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
		<script>
        conversant.pubcode.loadOneAd({
            sid: 245979,
            id: "ce1af472",
            location: "pubCodeContainer-id-245979-ce1af472",
            format: [{w:336,h:280}]
        })
    </script>
	</div>
	<!-- Conversant Media CODE for Eastern Wordsmith (Placement ce1af472) -->
        
        <!-- Conversant Media CODE for Eastern Wordsmith (Placement e1f0d2e7) -->
      <div class="d-block text-center my-4" id="pubCodeContainer-id-245979-e1f0d2e7">
        <script type="text/javascript"
          src="https://secure.cdn.fastclick.net/js/cnvr-pubcode/latest/pubcode.min.js"></script>
        <script>
          conversant.pubcode.loadOneAd({
            sid: 245979,
            id: "e1f0d2e7",
            location: "pubCodeContainer-id-245979-e1f0d2e7",
            format: [{ w: 320, h: 50 }]
          })
        </script>
      </div>
      <!-- Conversant Media CODE for Eastern Wordsmith (Placement e1f0d2e7) -->
        
        
<%@ include file="footer1.jsp" %>
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>