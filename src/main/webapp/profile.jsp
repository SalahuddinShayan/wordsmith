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

    <div class="container mt-4 stm">
        <h2>User Profile</h2>
        <hr/>

        <!-- User Info -->
        <div class="row">
            <div class="col-md-3">
                <img width="200" 
                     src ="<c:out value='${pageContext.request.contextPath}/user-image/${user.username}'/>" 
                     alt="images/No_image_available.svg.png" 
                     onerror="this.src='images/No_image_available.svg.png';">
                <br/>
                <button type="button" class="btn btn-primary mt-2" data-bs-toggle="modal" data-bs-target="#popupFormModal">
                    Change Profile Picture
                </button>
            </div>
            <div class="col-md-9">
                <p><b>Username:</b> ${user.username}</p>
                <p><b>Email:</b> ${user.email}</p>
                <p><b>Role:</b> ${user.role}</p>
                <p><b>Joined:</b> ${user.createdAt}</p>
                <p><b>Last Login:</b> ${user.lastLoginTime}</p>
                <a href="/auth/change-password">Change Password</a>
            </div>
        </div>

        <hr/>

        <!-- 🟩 Membership Information -->
        <h3>Membership Information</h3>

        <!-- ========================================================= -->
        <!-- ACTIVE MEMBERSHIP -->
        <!-- ========================================================= -->
        <c:if test="${topMembership != null && topMembership.status == 'ACTIVE' }">
            <div class="card bg-secondary text-light mb-4">
                <div class="card-body">
                    <h5>Active Membership</h5>

                    <p><b>Status:</b> ${topMembership.status}</p>
                    <p><b>Plan:</b> ${topMembership.planId}</p>
                    <p><b>Start Date:</b> ${topMembership.startDate}</p>
                    <p><b>Next Billing Date:</b> ${topMembership.endDate}</p>

                    <p><b>Auto-Renew:</b>
                        <c:out value="${topMembership.autoRenew ? 'Enabled' : 'Disabled'}" />
                    </p>

                    <form method="post" action="/membership/cancel">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" name="subscriptionId" value="${topMembership.subscriptionId}" />
                        <button type="submit" class="btn btn-warning">Cancel Membership</button>
                    </form>
                </div>
            </div>
        </c:if>

        <!-- ========================================================= -->
        <!-- PENDING MEMBERSHIP -->
        <!-- ========================================================= -->
        <c:if test="${topMembership.status == 'PENDING'  }">
            <div class="card bg-warning text-dark mb-4">
                <div class="card-body">
                    <h5>Membership Activation In Progress</h5>
                    <p>
                        We have received your subscription request and are waiting for PayPal to confirm activation.
                        This usually takes <strong>5–30 seconds</strong>.
                    </p>
                    <p>
                        If your membership does not activate automatically within <strong>5 minutes</strong>,
                        please <a href="/contactus">contact us</a>.
                    </p>
                    <p><b>Plan:</b> ${topMembership.planId}</p>
                    <p><b>Status:</b> ${topMembership.status}</p>
                </div>
            </div>
        </c:if>

        <!-- ========================================================= -->
        <!-- NO ACTIVE MEMBERSHIP + NO PENDING MEMBERSHIP -->
        <!-- ========================================================= -->
        <c:if test="${topMembership == null && topMembership.status != 'PENDING' && topMembership.status != 'ACTIVE' }">
            <div class="card bg-info text-dark mb-4">
                <div class="card-body">
                    <h5>No Active Membership</h5>
                    <p>You currently do not have an active membership.</p>
                    <a href="/membership" class="btn btn-success">Join Membership</a>
                </div>
            </div>
        </c:if>

        <!-- ========================================================= -->
        <!-- MEMBERSHIP HISTORY -->
        <!-- ========================================================= -->
        <c:if test="${not empty membershipHistory}">
            <h4>Membership History</h4>

            <div class="card bg-dark text-light mb-4">
                <div class="card-body">
                    <c:forEach var="m" items="${membershipHistory}">
                        <div class="mb-3 p-2 border-bottom">
                            <p><b>Plan:</b> ${m.planId}</p>
                            <p><b>Status:</b> ${m.status}</p>
                            <p><b>Start:</b> ${m.startDate}</p>
                            <p><b>End:</b> ${m.endDate}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <c:if test="${empty membershipHistory}">
            <p>No past membership records.</p>
        </c:if>

        <hr/>



        <!-- Favorite Novels -->
        <h3>Favorite Novels</h3>
        <c:if test="${not empty favorites}">
            <ul>
                <c:forEach var="novel" items="${favorites}">
                    <li><a href="/novel/${novel.novelName}">${novel.novelName}</a></li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty favorites}">
            <p>No favorite novels yet.</p>
        </c:if>

        <hr/>

        <!-- Your Comments -->
        <h3>Your Comments</h3>
        <c:if test="${not empty comments}">
            <ul>
                <c:forEach var="comment" items="${comments}">
                    <li>
                        <c:if test="${not empty comment.chapter}">
                            <p><b>On:</b> <a href="/chapter/${comment.chapter.chapterId}">${comment.chapter.novelName} Chapter: ${comment.chapter.chapterNo}</a><br>
                            "${comment.content}"

                            <!-- Show replies if exist -->
                            <c:if test="${not empty comment.replies}">
                                <ul>
                                    <c:forEach var="reply" items="${comment.replies}">
                                        <li><b>Reply by <i>${reply.userName}:</i></b> ${reply.content}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            </p>
                        </c:if>

                        <c:if test="${not empty comment.novel}">
                            <p><b>On Novel:</b> <a href="/novel/${comment.novel.novelName}">${comment.novel.novelName}</a><br>
                            "${comment.content}"
                            <c:if test="${not empty comment.replies}">
                                <ul>
                                    <c:forEach var="reply" items="${comment.replies}">
                                        <li><b>Reply by <i>${reply.userName}:</i></b> ${reply.content}</li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            </p>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty comments}">
            <p>No comments made yet.</p>
        </c:if>

        <hr/>

        <!-- Your Replies -->
        <h3>Your Replies</h3>
        <c:if test="${not empty replies}">
            <ul>
                <c:forEach var="reply" items="${replies}">
                    <c:if test="${not empty reply.chapter}">
                        <li>
                            <p><b>To ${reply.parentComment.userName} On:</b> 
                            <a href="/chapter/${reply.chapter.chapterId}">${reply.chapter.novelName} Chapter: ${reply.chapter.chapterNo}</a><br>
                            "${reply.content}"</p>
                        </li>
                    </c:if>
                    <c:if test="${not empty reply.novel}">
                        <li>
                            <p><b>To ${reply.parentComment.userName} On Novel:</b> 
                            <a href="/novel/${reply.novel.novelName}">${reply.novel.novelName}</a><br>
                            "${reply.content}"</p>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty replies}">
            <p>No replies made yet.</p>
        </c:if>

        <hr/>

        <!-- Comments You Liked/Disliked -->
        <h3>Comments You Liked/Disliked</h3>
        <c:forEach var="likedComment" items="${likedComments}">
            <ul>
                <c:choose>
                    <c:when test="${not empty likedComment.parentComment}">
                        <c:choose>
                            <c:when test="${not empty likedComment.chapter}">
                                <p><b>You ${likedComment.userReaction}D Reply By User: ${likedComment.userName} On:</b> 
                                <a href="/chapter/${likedComment.chapter.chapterId}">
                                    ${likedComment.chapter.novelName} Chapter: ${likedComment.chapter.chapterNo}
                                </a><br>
                                "${likedComment.content}"</p>
                            </c:when>

                            <c:when test="${not empty likedComment.novel}">
                                <p><b>You ${likedComment.userReaction}D Reply By User: ${likedComment.userName} On Novel:</b> 
                                <a href="/novel/${likedComment.novel.novelName}">${likedComment.novel.novelName}</a><br>
                                "${likedComment.content}"</p>
                            </c:when>
                        </c:choose>
                    </c:when>

                    <c:when test="${not empty likedComment.chapter}">
                        <p><b>You ${likedComment.userReaction}D Comment By User: ${likedComment.userName} On:</b> 
                        <a href="/chapter/${likedComment.chapter.chapterId}">
                            ${likedComment.chapter.novelName} Chapter: ${likedComment.chapter.chapterNo}
                        </a><br>
                        "${likedComment.content}"</p>
                    </c:when>

                    <c:when test="${not empty likedComment.novel}">
                        <p><b>You ${likedComment.userReaction}D Comment By User: ${likedComment.userName} On Novel:</b> 
                        <a href="/novel/${likedComment.novel.novelName}">${likedComment.novel.novelName}</a><br>
                        "${likedComment.content}"</p>
                    </c:when>
                </c:choose>
            </ul>
        </c:forEach>
        <c:if test="${empty likedComments}">
            <p>You haven't liked or disliked any comments yet.</p>
        </c:if>
    </div>

    <!-- Profile Pic Modal -->
    <div class="modal fade" id="popupFormModal" tabindex="-1" aria-labelledby="popupFormLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          
          <div class="modal-header">
            <h5 class="modal-title" id="popupFormLabel">Change Profile Picture</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>

          <div class="modal-body">
            <form method="post" action="/ChangeProfilePicture" enctype="multipart/form-data" onsubmit="return validateImageSize()">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
              <input type="hidden" name="username" value="${user.username}" />

              <div class="mb-3">
                <label for="Pic">Upload the Picture</label>
                <input name="Pic" class="form-control" type="file" id="image" accept="image/*" required>
              </div>
              
              <button type="submit" class="btn btn-success">Submit</button>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </form>
          </div>
        </div>
      </div>
    </div>

    <%@ include file="footer1.jsp" %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script type="text/javascript" src="js/script.js"></script>
</body>


</html>