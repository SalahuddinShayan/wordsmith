<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <!DOCTYPE html>
  <html>

  <head>
    <script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag() { dataLayer.push(arguments); }
      gtag('js', new Date());

      gtag('config', 'G-0D3MMVLTED');
    </script>
    <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291"
      crossorigin="anonymous"></script>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="google-adsense-account" content="ca-pub-3020770276580291">
    <meta name="keywords"
      content="Eastern Word Smith, Eastern WordSmith, web novels, webnovels, novels, japanese novels, online novels, japanese webnovel">
    <title>Eastern WordSmith</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <script
          data-grow-initializer="">!(function () { window.growMe || ((window.growMe = function (e) { window.growMe._.push(e); }), (window.growMe._ = [])); var e = document.createElement("script"); (e.type = "text/javascript"), (e.src = "https://faves.grow.me/main.js"), (e.defer = !0), e.setAttribute("data-grow-faves-site-id", "U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE="); var t = document.getElementsByTagName("script")[0]; t.parentNode.insertBefore(e, t); })();</script>

  </head>

  <body>
    
    <%@ include file="nav1.jsp" %>

<div class="container mt-4">
  <h2>Announcements</h2>
  <hr/>
  <c:forEach var="announcement" items="${announcements}">
    <div class="bd-az">
        <c:if test="${not empty announcement}">
            <h4>${announcement.title}</h4>
            <p><small>${announcement.timeAgo}</small></p>
            
            <p>
                <c:choose>
                    <c:when test="${fn:length(announcement.content) > 300}">
                        ${fn:substring(announcement.content, 0, 300)}...
                    </c:when>
                    <c:otherwise>
                        ${announcement.content}
                    </c:otherwise>
                </c:choose>
            </p>

            <a href="../announcements/${announcement.id}" class="btn btn-outline-primary btn-sm">
                Read More
            </a>
        </c:if>

        <c:if test="${empty announcement}">
            <h5>Announcement:</h5>
            <p>No recent announcements available.</p>
        </c:if>
    </div>
  </c:forEach>
</div>

<%@ include file="footer1.jsp" %>




        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="js/script.js"></script>
  </body>

  </html>