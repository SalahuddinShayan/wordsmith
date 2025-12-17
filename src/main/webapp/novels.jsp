<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
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
        <meta name="google-adsense-account" content="ca-pub-3020770276580291">
		<script type="text/javascript">
		(adsbygoogle = window.adsbygoogle || []).push({
		google_ad_client: "ca-pub-3020770276580291",
		enable_page_level_ads: true
		});
		</script>
        <title>Eastern WordSmith</title>
        <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>		 
</head>
<body>

<%@ include file="nav1.jsp" %>
        
        <div class="d-none d-lg-block text-center my-3 container-fluid">
            
				<!-- Responsive Horizontal -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9524461170"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
		  
          </div>
          
          <div class="d-block d-lg-none text-center my-3 container-fluid">
            
				<!-- Responsive Square -->
				<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9826879355"
     				data-ad-format="auto"
     				data-full-width-responsive="true">
     			</ins>
			
          </div>
        
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3 ">
         <div class = "sticky-ad">
              <div class="d-none d-lg-block text-center my-3" >
                
					<!-- Responsive Vertical -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9033506582"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
				
              </div>	
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
         <div class="col-1 col-lg-3 py-3 ">
         <div class="d-none d-lg-block text-center my-3 sticky-ad">
    
					<!-- Wide SkyScraper -->
					<ins class="adsbygoogle"
     				style="display:inline-block;width:160px;height:600px"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="6212425217"></ins>
				
</div>
        </div>
        </div>
        
        
        <div Style="margin-top: 30px;" class="row">
	     <div class="col-12 col-lg-6 text-center my-3">
            
				<!-- Responsive Square -->
				<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9826879355"
     				data-ad-format="auto"
     				data-full-width-responsive="true">
     			</ins>
			
          </div>
        <div class="col-12 col-lg-6 text-center my-3">
            
				<!-- Medium Rectangle -->
				<ins class="adsbygoogle"
     			style="display:inline-block;width:300px;height:250px"
     			data-ad-client="ca-pub-3020770276580291"
     			data-ad-slot="8950269194"></ins>
     			</ins>
			
          </div>
	</div>
        
<%@ include file="footer1.jsp" %>
       
        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="js/script.js"></script>  
</body>
</html>