<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<c:if test="${not hasMembership}">
<script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
<meta name="google-adsense-account" content="ca-pub-3020770276580291">
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
</c:if>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-0D3MMVLTED');
</script>

<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>All Novels</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <link rel="stylesheet" type="text/css" href="css/stylesheet.css">		 
</head>
<body>

<%@ include file="nav1.jsp" %>
        
		<c:if test="${not hasMembership}">
        <div class="d-none d-lg-block text-center my-3 container-fluid">
            
			<%@ include file="jspf/ad-horizontal.jspf" %>
		  
          </div>
          
          <div class="d-block d-lg-none text-center my-3 container-fluid">
            
			<%@ include file="jspf/ad-square.jspf" %>
			
          </div>
        </c:if>
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3 ">
			<c:if test="${not hasMembership}">
         <div class = "sticky-ad">
              <div class="d-none d-lg-block text-center my-3" >
                
					<%@ include file="jspf/ad-vertical.jspf" %>
				
              </div>	
			</div>
			</c:if>
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
         <div class="col-1 col-lg-3 py-3 ">
			<c:if test="${not hasMembership}">
         <div class="d-none d-lg-block text-center my-3 sticky-ad">
					<%@ include file="jspf/ad-vertical.jspf" %>
				
</div>
</c:if>
        </div>
        </div>
        
        <c:if test="${not hasMembership}">
        <div Style="margin-top: 30px;" class="row">
	     <div class="col-12 col-lg-6 text-center my-3">
            
				<%@ include file="jspf/ad-square.jspf" %>
			
          </div>
        <div class="col-12 col-lg-6 text-center my-3">
            
				<%@ include file="jspf/ad-square.jspf" %>
			
          </div>
	</div>
	</c:if>
        
<%@ include file="footer1.jsp" %>
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>