<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <!-- Google tag (gtag.js) -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=G-0D3MMVLTED"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag() { dataLayer.push(arguments); }
      gtag('js', new Date());

      gtag('config', 'G-0D3MMVLTED');
    </script>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Purchases: ${user.username}</title>
    <link rel="shortcut icon" type="image/x-icon" href="../images/logo2.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="../css/stylesheet.css">
</head>
<body>

<%@ include file="../nav2.jsp" %>

<div class="container mt-4 stm">

    <h2>📘 Chapter Purchase History</h2>
    <p class="text-light-muted">
        Showing all chapters you have purchased.
    </p>

    <hr/>

    <c:if test="${empty purchases}">
        <div class="alert alert-info">
            You haven’t purchased any chapters yet.
        </div>
    </c:if>

    <c:if test="${not empty purchases}">
        <div class="table-responsive">
            <table class="table table-striped table-hover align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Novel</th>
                        <th>Chapter</th>
                        <th>Coins Spent</th>
                        <th>Purchased On</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${purchases}">
                        <tr>
                            <td><b>${p.novelName}</b></td>
                            <td>Chapter ${p.chapterNo}</td>
                            <td><span class="ew-coin" style="height: 15px; width: 15px;">
                                                <img src="/images/enso.svg" alt="EWS Coins">
                                                </span> ${p.price}</td>
                            <td>${p.purchasedAt}</td>
                            <td>
                                <a href="/chapter/${p.chapterId}" class="btn btn-sm btn-primary">
                                    Read
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <a href="/profile" class="btn btn-secondary mt-3">
        ← Back to Profile
    </a>

</div>

<%@ include file="../footer2.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="../js/script.js"></script>
</body>


</html>
