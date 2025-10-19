<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
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
        <title>${announcement.title}</title>
        <link rel="icon" type="image/x-icon" href="../images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <link rel="stylesheet" type="text/css" href="../css/stylesheet.css">
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>
</head>
<body>

<%@ include file="../nav2.jsp" %>

<div class="center">
    <h3>Announcement</h3>
    <h3>${announcement.title}</h3>
    <p><small>Posted by <strong>${announcement.postedBy}</strong> on ${announcement.createdAt}</small></p>
</div>

<div style="margin-top: 30px;" class="row">
    <div class="col-1 col-lg-2 py-3"></div>
    <div class="col-10 col-lg-8 py-3 stm">
        <a href="../home">Eastern Word Smith</a>
        <a>/</a>
        <a href="../announcements">Announcements</a>
        <a>/</a>
        <a href="${announcement.id}">${announcement.title}</a>
    </div>
    <div class="col-1 col-lg-2 py-3"></div>
</div>

<div style="margin-top: 30px;" class="row">
    <div class="col-1 col-lg-2 py-3"></div>
    <div class="col-10 col-lg-8">
        <section class="wcontainer" id="CS">
            ${announcement.content}
        </section>
    </div>
    <div class="col-1 col-lg-2 py-3"></div>
</div>

<div style="margin-top: 30px;" class="row">
    <div class="col-1 col-lg-3 py-3"></div>
    <div class="col-10 col-lg-6 py-3">
        <!-- Reactions Section -->
        <div id="reaction-section-${announcement.id}" class="chapter-reactions row">
            <div class="col-4 col-lg-2">
                <button class="reaction-btn ${announcement.userReaction == 'LOVE' ? 'active' : ''}" 
                        data-entity-type="ANNOUNCEMENT" data-entity-id="${announcement.id}" data-like-status="LOVE">
                    ❤️ <br><span id="reaction-count-LOVE-${announcement.id}">${announcement.loveCount}</span>
                </button>
            </div>
            <div class="col-4 col-lg-2">
                <button class="reaction-btn ${announcement.userReaction == 'ANGRY' ? 'active' : ''}" 
                        data-entity-type="ANNOUNCEMENT" data-entity-id="${announcement.id}" data-like-status="ANGRY">
                    😡 <br><span id="reaction-count-ANGRY-${announcement.id}">${announcement.angryCount}</span>
                </button>
            </div>
            <div class="col-4 col-lg-2">
                <button class="reaction-btn ${announcement.userReaction == 'LAUGH' ? 'active' : ''}" 
                        data-entity-type="ANNOUNCEMENT" data-entity-id="${announcement.id}" data-like-status="LAUGH">
                    😂 <br><span id="reaction-count-LAUGH-${announcement.id}">${announcement.laughCount}</span>
                </button>
            </div>
            <div class="col-4 col-lg-2">
                <button class="reaction-btn ${announcement.userReaction == 'SAD' ? 'active' : ''}" 
                        data-entity-type="ANNOUNCEMENT" data-entity-id="${announcement.id}" data-like-status="SAD">
                    😢 <br><span id="reaction-count-SAD-${announcement.id}">${announcement.sadCount}</span>
                </button>
            </div>
            <div class="col-4 col-lg-2">
                <button class="reaction-btn ${announcement.userReaction == 'WOW' ? 'active' : ''}" 
                        data-entity-type="ANNOUNCEMENT" data-entity-id="${announcement.id}" data-like-status="WOW">
                    😲 <br><span id="reaction-count-WOW-${announcement.id}">${announcement.wowCount}</span>
                </button>
            </div>
            <div class="col-4 col-lg-2">
                <button class="reaction-btn ${announcement.userReaction == 'FIRE' ? 'active' : ''}" 
                        data-entity-type="ANNOUNCEMENT" data-entity-id="${announcement.id}" data-like-status="FIRE">
                    🔥 <br><span id="reaction-count-FIRE-${announcement.id}">${announcement.fireCount}</span>
                </button>
            </div>
        </div>

        <hr>

        <!-- Comments Section -->
        <h3>Comments</h3>

        <div class="cbox">
            <c:if test="${empty loggedInUser}">
                <p>You must <a href="/auth/loginpage">log in</a> to post a comment.</p>
            </c:if>

            <c:if test="${not empty loggedInUser}">
                <form action="/comments/add" method="post">
                    <input type="hidden" name="entityType" value="announcement">
                    <input type="hidden" name="entityId" value="${announcement.id}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <textarea name="content" required class="form-control" placeholder="Write a comment..."></textarea>
                    <button type="submit" class="btn btn-primary mt-2">Comment</button>
                </form>
            </c:if>
            <br>

            <c:forEach var="comment" items="${comments}">
                <div class="comment">
                    <img class="profile-pic" src ="<c:out value='${pageContext.request.contextPath}/user-image/${comment.userName}'/>" onerror="this.src='../images/No-Image.png';">
                    <strong>${comment.userName}</strong>
                    <small>${comment.timeAgo}</small>
                    <p>${comment.content}</p>

                    <div class="comment-actions">
                        <div id="like-section-${comment.id}">
                            <button class="like-btn ${comment.userReaction == 'LIKE' ? 'active' : ''}" 
                                    data-entity-type="COMMENT" data-entity-id="${comment.id}" data-like-status="LIKE">
                                👍 <span id="like-count-${comment.id}">${comment.likeCount}</span>
                            </button>
                            <button class="dislike-btn ${comment.userReaction == 'DISLIKE' ? 'active' : ''}" 
                                    data-entity-type="COMMENT" data-entity-id="${comment.id}" data-like-status="DISLIKE">
                                👎 <span id="dislike-count-${comment.id}">${comment.dislikeCount}</span>
                            </button>
                        </div>

                        <c:if test="${loggedInUser.username == comment.userName || loggedInUser.role == 'ADMIN'}">
                            <form action="/comments/delete" method="post" class="comment-action-form">
                                <input type="hidden" name="commentId" value="${comment.id}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <button type="submit" class="icon-btn"><i class="fa-solid fa-trash"></i></button>
                            </form>
                        </c:if>

                        <form action="/comments/flag" method="post" class="comment-action-form">
                            <input type="hidden" name="commentId" value="${comment.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <button type="submit" class="icon-btn"><i class="fa-solid fa-flag"></i></button>
                        </form>

                        <c:if test="${not empty loggedInUser}">
                            <button class="icon-btn reply-btn" onclick="toggleReplyForm('${comment.id}')">
                                <i class="fa-solid fa-reply"></i>
                            </button>
                        </c:if>
                    </div>

                    <!-- Reply Form -->
                    <div id="reply-form-${comment.id}" class="reply-form" style="display:none; margin-top:10px;">
                        <form action="/comments/add" method="post">
                            <input type="hidden" name="entityType" value="comment">
                            <input type="hidden" name="entityId" value="${comment.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <textarea name="content" required class="form-control" placeholder="Write a reply..."></textarea>
                            <button type="submit" class="btn btn-secondary mt-2">Reply</button>
                            <button type="button" class="btn btn-link mt-2" onclick="toggleReplyForm('${comment.id}')">Cancel</button>
                        </form>
                    </div>

                    <!-- Display Replies -->
                    <c:if test="${comment.hasReplies}">
                        <div class="replies" style="margin-left: 30px;">
                            <c:forEach var="reply" items="${comment.replies}">
                                <div class="replied-comment">
                                    <img class="profile-pic" src ="<c:out value='${pageContext.request.contextPath}/user-image/${reply.userName}'/>" onerror="this.src='../images/No-Image.png';">
                                    <strong>${reply.userName}</strong>
                                    <small>${reply.timeAgo}</small>
                                    <p>${reply.content}</p>

                                    <div class="comment-actions">
                                        <div id="like-section-${reply.id}">
                                            <button class="like-btn ${reply.userReaction == 'LIKE' ? 'active' : ''}" 
                                                    data-entity-type="COMMENT" data-entity-id="${reply.id}" data-like-status="LIKE">
                                                👍 <span id="like-count-${reply.id}">${reply.likeCount}</span>
                                            </button>
                                            <button class="dislike-btn ${reply.userReaction == 'DISLIKE' ? 'active' : ''}" 
                                                    data-entity-type="COMMENT" data-entity-id="${reply.id}" data-like-status="DISLIKE">
                                                👎 <span id="dislike-count-${reply.id}">${reply.dislikeCount}</span>
                                            </button>
                                        </div>

                                        <c:if test="${loggedInUser.username == reply.userName || loggedInUser.role == 'ADMIN'}">
                                            <form action="/comments/delete" method="post" class="comment-action-form">
                                                <input type="hidden" name="commentId" value="${reply.id}">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                <button type="submit" class="icon-btn"><i class="fa-solid fa-trash"></i></button>
                                            </form>
                                        </c:if>

                                        <form action="/comments/flag" method="post" class="comment-action-form">
                                            <input type="hidden" name="commentId" value="${comment.id}">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            <button type="submit" class="icon-btn"><i class="fa-solid fa-flag"></i></button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
                <br>
            </c:forEach>
        </div>
    </div>
    <div class="col-1 col-lg-3 py-3"></div>
</div>

<%@ include file="../footer2.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="../js/script.js"></script>

<input type="hidden" id="_csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />  
</body>

</html>