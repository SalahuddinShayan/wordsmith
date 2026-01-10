<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <!DOCTYPE html>
  <html>

  <head>
    <c:if test="${not hasMembership}">
    <script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
    <meta name="google-adsense-account" content="ca-pub-3020770276580291">
    <script type="text/javascript">
		(adsbygoogle = window.adsbygoogle || []).push({
		google_ad_client: "ca-pub-3020770276580291",
		enable_page_level_ads: true
		});
		</script>
    </c:if>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="keywords"
      content="Eastern Word Smith, Eastern WordSmith, web novels, webnovels, novels, japanese novels, online novels, japanese webnovel">
    <title>Eastern WordSmith</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="css/stylesheet.css">
  </head>

 <body>

  <%@ include file="nav1.jsp" %>

  <c:if test="${not hasMembership}">
  <!-- ✅ Top banner (728x90 for desktop / 320x50 for mobile) -->
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
    </c:if>

  <!-- Popular Today -->
  <div style="margin-top: 30px;" class="row">
    <div class="col-1 col-lg-2 py-3"></div>
    <div class="col-10 col-lg-8 bg-az">
      <h5>Popular Today</h5>
    </div>
    <div class="col-1 col-lg-2 py-3"></div>
  </div>

  <div class="row">
    <div class="col-2 py-3"></div>
    <div class="col-8 py-3">

      <div class="row">
        <c:forEach var="novel" items="${Novels}">
          <div class="col-6 col-lg-3 py-3 center stm oneliner">
            <a href="novel/${novel.novelName}">
              <img width="100%"
                src="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>"
                alt="images/No-Image.png"
                onerror="this.src='images/No-Image.png';">
            </a><br>
            <a href="novel/${novel.novelName}">${novel.novelName}</a><br> 
            <a href ="chapter/${novel.latestChapterId}">Chapter ${novel.latestChapterNo}</a>
          </div>
        </c:forEach>
      </div>
    </div>
    <div class="col-2 py-3"></div>
  </div>

  <c:if test="${not hasMembership}">
  <div class="container my-4">
    <div class="alert alert-light border text-center shadow-sm ad-free">
        <p class="mb-2">
            <strong>Enjoy an Ad-Free Reading Experience ❤️</strong><br>
            Go distraction-free and support Eastern WordSmith by becoming a member.
        </p>
        <a href="${pageContext.request.contextPath}/membership"
           class="btn btn-primary btn-sm">
            Go Ad-Free / Become a Member
        </a>
    </div>
  </div>
  </c:if>

  <c:if test="${not hasMembership}">    
  <div Style="margin-top: 30px;" class="row container-fluid">
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
          </div>
	</div>
  </c:if>

  <!-- Latest Updates -->
  <div style="margin-top: 30px;" class="row">
    <div class="col-1 col-lg-2"></div>
    <div class="col-10 col-lg-8 bg-az">
      <h4>Latest Updates:</h4>
    </div>
    <div class="col-1 col-lg-2"></div>
  </div>

  <div class="row">
    <!-- ✅ Left Sidebar Ad (Load only on desktop) -->
    <div class="col-1 col-lg-2 py-3 ">
            <c:if test="${not hasMembership}">
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
      </c:if>
    </div>

    <div class="col-10 col-lg-8 py-3 bd-az">
      <div class="row mt-4">
        <div class="col-12 text-center">
          <button id="btn-free" class="btn btn-sm btn-primary me-2">
            🆓 Free Chapters
          </button>
          <button id="btn-early" class="btn btn-sm btn-outline-primary">
            <span class="ew-coin" style="height: 15px; width: 15px;">
                                                <img src="/images/enso.svg" alt="EWS Coins">
                                                </span> Early Access
          </button>
        </div>
      </div>
      <br>
      <div id="free-chapters-section">
      <!-- EXISTING Latest Updates HTML (unchanged) -->
      <div class="row">
        <c:forEach var="novel" items="${Novelsu}">
          <div class="col-12 col-lg-6">
            <div class="row">
              <div class="col-3 py-lg-3 center" style="margin-top: 10px;">
                <a href="novel/${novel.novelName}">
                  <img class="icont"
                    src="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>"
                    alt="images/No_image_available.svg.png"
                    onerror="this.src='images/No_image_available.svg.png';">
                </a>
              </div>
              <div class="col-9 py-lg-3">
                <h6 class="stm oneliner"><a href="novel/${novel.novelName}">${novel.novelName}</a></h6>
                <c:forEach var="chapter" items="${novel.recentChapters}">
                <div class = "row">
                <div class="col-6 py-1 py-lg-2 stm oneliner"><a href="chapter/${chapter.chapterId}">C${chapter.chapterNo}: ${chapter.title}</a></div>
                <div class="col-6 py-1 py-lg-2 right">${chapter.timeAgo}</div>
                </div>
                </c:forEach>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
      </div>
      <div id="early-access-section" style="display:none;">
        <div class="row">
          <c:forEach var="novel" items="${Novelss}">
          <div class="col-12 col-lg-6">
            <div class="row">
              <div class="col-3 py-lg-3 center" style="margin-top: 10px;">
                <a href="novel/${novel.novelName}">
                  <img class="icont"
                    src="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>"
                    alt="images/No_image_available.svg.png"
                    onerror="this.src='images/No_image_available.svg.png';">
                </a>
              </div>
              <div class="col-9 py-lg-3">
                <h6 class="stm oneliner"><a href="novel/${novel.novelName}">${novel.novelName}</a></h6>
                <c:forEach var="chapter" items="${novel.stockpileChapters}">
                <div class = "row">
                <div class="col-6 py-1 py-lg-2 stm oneliner"><a href="chapter/${chapter.chapterId}" class = "early-access-link" data-owned="${chapter.owned}">
                  <c:choose>
                    <c:when test="${chapter.owned}">
                      🔓 C${chapter.chapterNo}: ${chapter.title}
                    </c:when>
                    <c:otherwise>
                      🔒 C${chapter.chapterNo}: ${chapter.title}
                    </c:otherwise>
                  </c:choose>
                </a></div>
                <div class="col-6 py-1 py-lg-2 right">${chapter.timeAgo}</div>
                </div>
                </c:forEach>
              </div>
            </div>
          </div>
        </c:forEach>
        </div>
      </div>
    </div>

    <!-- ✅ Right Sidebar: 160x300 -->
    <div class="col-1 col-lg-2 py-3">
      <c:if test="${not hasMembership}">
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
      </c:if>
    </div>
  </div>

  <c:if test="${not hasMembership}">
  <div class="text-center my-3">
				<!-- Responsive Horizontal -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9524461170"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
          </div>
          </c:if>

  <%@ include file="footer1.jsp" %>


  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script>
      <c:choose>
          <c:when test="${not empty loggedInUser}">
              const IS_LOGGED_IN = true;
          </c:when>
          <c:otherwise>
              const IS_LOGGED_IN = false;
          </c:otherwise>
      </c:choose>
  </script>
  <script type="text/javascript" src="js/script.js"></script>
</body>

  </html>