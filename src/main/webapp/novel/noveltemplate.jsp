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
<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
<meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="google-adsense-account" content="ca-pub-3020770276580291">
        <meta name="keywords" content="${novel.keywords}">
        <title>${novel.novelName}</title>
        <link rel="icon" type="image/x-icon" href="../images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <link rel="stylesheet" type="text/css" href="../css/stylesheet.css">
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>
</head>
<body>

<%@ include file="../nav2.jsp" %>

        
        <div class="d-none d-lg-block text-center my-3 container-fluid">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Responsive Horizontal -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9524461170"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
		  <script>
     		(adsbygoogle = window.adsbygoogle || []).push({});
		  </script>
          </div>
          
          <div class="d-block d-lg-none text-center my-3 container-fluid">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Responsive Square -->
				<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9826879355"
     				data-ad-format="auto"
     				data-full-width-responsive="true">
     			</ins>
			<script>
     			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
          </div>
        
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 py-3 ">
            <!-- ✅ Left Sidebar Ad (Load only on desktop) -->
            <div id="leftSidebarAd" class = "sticky-ad">
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291"
     crossorigin="anonymous"></script>
<!-- Wide SkyScraper -->
<ins class="adsbygoogle"
     style="display:inline-block;width:160px;height:600px"
     data-ad-client="ca-pub-3020770276580291"
     data-ad-slot="6212425217"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
         </div>
         </div>
         <div class="col-10 col-lg-8 py-3">
         <div class="center">
         <a><img width="40%" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" 
               alt="images/No_image_available.svg.png" onerror="this.src='../images/No-Image.png';"></a><br>
         <a>Views: ${totalViews}</a> <div id="favorite-section-${novel.novelId}">
                <button class="favorite-btn ${novel.favorited ? 'active' : ''}" 
            data-novel-id="${novel.novelId}">
        ⭐ Favorite <span id="favorite-count-${novel.novelId}">${novel.favoriteCount}</span>
    </button>
</div>

         </div>
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-4  py-3 right"><a>Name</a><a> : </a></div>
         <div class="col-8  py-3 "><a>${novel.novelName}</a></div>
         </div>
         <div  class = "row">
         <div class="col-4  py-3 right"><a>Original Language</a><a> : </a></div>
         <div class="col-8  py-3 "><a>${novel.originalLanguage}</a></div>
         </div>
         <div  class = "row">
         <div class="col-4  py-3 right"><a>Author</a><a> : </a></div>
         <div class="col-8  py-3 "><a>${novel.author}</a></div>
         </div>
         <div  class = "row">
         <div class="col-4  py-3 right"><a>Genre</a><a> : </a></div>
         <div class="col-8  py-3 "><a>${novel.genre}</a></div>
         </div>
         <div  class = "row">
         <div class="col-4  py-3 right"><a>Status</a><a> : </a></div>
         <div class="col-8  py-3 "><a>${novel.status}</a></div>
         </div>
         <div  class = "row">
         <div class="col-4 py-3 right"><a>Alternate Names</a><a> : </a></div>
         <div class="col-8  py-3 "><a>${novel.keywords}</a></div>
         </div>
         <div  class = "row">
         <div class="col-4  py-3 right"><a>Description</a><a> : </a></div>
         <div class="col-8  py-3 d-none d-md-block">${novel.description}</div>
         </div>
         <div  class = "row d-lg-none">
         <div class="col-1 py-3 "></div>
         <div class="col-11  py-3 ">${novel.description}</div>
         </div>
         <div  class = "row">
         <div class="col-6 py-3 text-center">
         	<a href="../chapter/${frist}" class="btn bg-az"><i class="fas fa-book-open"></i> Read First Chapter</a>
    	</div>
         <div class="col-6 py-3 text-center"><a href="../chapter/${last}" class="btn bg-az"><i class="fas fa-book-open"></i> Read Last Chapter</a></div>
         </div>
         </div>
         <div class="col-1 col-lg-2 py-3 ">
            <!-- ✅ Right Sidebar: 160x300 -->
                <div id="rightSidebarAd" class = "sticky-ad">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291"
     crossorigin="anonymous"></script>
<!-- Wide SkyScraper -->
<ins class="adsbygoogle"
     style="display:inline-block;width:160px;height:600px"
     data-ad-client="ca-pub-3020770276580291"
     data-ad-slot="6212425217"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>
         </div>
         </div>
         
         
        
         <div Style="margin-top: 30px;" class="row container-fluid">
	     <div class="col-12 col-lg-6 text-center my-3">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Responsive Square -->
				<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9826879355"
     				data-ad-format="auto"
     				data-full-width-responsive="true">
     			</ins>
			<script>
     			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
          </div>
        <div class="col-12 col-lg-6 text-center my-3">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Medium Rectangle -->
				<ins class="adsbygoogle"
     			style="display:inline-block;width:300px;height:250px"
     			data-ad-client="ca-pub-3020770276580291"
     			data-ad-slot="8950269194"></ins>
     			</ins>
			<script>
     			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
          </div>
	</div>
         
         
         
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3">
         <!-- ✅ Left Sidebar Ad (Load only on desktop) -->
            <div id="leftSidebarAd2" class = "sticky-ad">
          <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
					<!-- Responsive Vertical -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9033506582"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
				<script>
     				(adsbygoogle = window.adsbygoogle || []).push({});
				</script>
            </div>
         </div>
         <div class="col-10 col-lg-6 py-3 ">
         <h4 class="center">Table Of Content</h4>
         <c:forEach var="chapter" items="${Chapters}">
  <div class="row align-items-center py-2">
    <!-- Chapter title and number -->
    <div class="col-7 col-lg-6 stm oneliner">
      <a href="../chapter/${chapter.chapterId}">
        Chapter ${chapter.chapterNo} : ${chapter.title}
      </a>
    </div>

    <!-- Time ago and views -->
    <div class="col-5 col-lg-3 text-end">
      <small>
        <i class="fa-regular fa-clock"></i> ${chapter.timeAgo}
      </small>
    </div>
    <div class="col-12 col-lg-3 text-start">
      <small>
        <i class="fa-solid fa-eye"></i> ${chapter.views}
      </small>
    </div>
  </div>
</c:forEach>
         </div>
         <div class="col-1 col-lg-3 py-3">
         <!-- ✅ Right Sidebar: 160x300 -->
                <div id="rightSidebarAd2" class = "sticky-ad">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
					<!-- Responsive Vertical -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9033506582"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
				<script>
     				(adsbygoogle = window.adsbygoogle || []).push({});
				</script>
         </div>
         </div>
         </div>
         
         
        <div Style="margin-top: 30px;" class="row container-fluid">
	     <div class="col-12 col-lg-6 text-center my-3">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Responsive Square -->
				<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9826879355"
     				data-ad-format="auto"
     				data-full-width-responsive="true">
     			</ins>
			<script>
     			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
          </div>
        <div class="col-12 col-lg-6 text-center my-3">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Medium Rectangle -->
				<ins class="adsbygoogle"
     			style="display:inline-block;width:300px;height:250px"
     			data-ad-client="ca-pub-3020770276580291"
     			data-ad-slot="8950269194"></ins>
     			</ins>
			<script>
     			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
          </div>
	</div>
         
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3 "></div>
         <div class="col-10 col-lg-6 py-3 ">
         <div class="comments-section" id="comment">

            <div id="reaction-section-${novel.novelId}" class="chapter-reactions row">
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${novel.userReaction == 'LOVE' ? 'active' : ''}" 
                        data-entity-type="NOVEL" data-entity-id="${novel.novelId}" data-like-status="LOVE">
                        ❤️ <br><span id="reaction-count-LOVE-${novel.novelId}">${novel.loveCount}</span>
                    </button>
                </div>
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${novel.userReaction == 'ANGRY' ? 'active' : ''}" 
                        data-entity-type="NOVEL" data-entity-id="${novel.novelId}" data-like-status="ANGRY">
                        😡 <br><span id="reaction-count-ANGRY-${novel.novelId}">${novel.angryCount}</span>
                    </button>
                </div>
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${novel.userReaction == 'LAUGH' ? 'active' : ''}" 
                        data-entity-type="NOVEL" data-entity-id="${novel.novelId}" data-like-status="LAUGH">
                        😂 <br><span id="reaction-count-LAUGH-${novel.novelId}">${novel.laughCount}</span>
                    </button>
                </div>    
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${novel.userReaction == 'SAD' ? 'active' : ''}" 
                        data-entity-type="NOVEL" data-entity-id="${novel.novelId}" data-like-status="SAD">
                        😢 <br><span id="reaction-count-SAD-${novel.novelId}">${novel.sadCount}</span>
                    </button>
                </div>    
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${novel.userReaction == 'WOW' ? 'active' : ''}" 
                        data-entity-type="NOVEL" data-entity-id="${novel.novelId}" data-like-status="WOW">
                        😲 <br><span id="reaction-count-WOW-${novel.novelId}">${novel.wowCount}</span>
                    </button>
                </div>    
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${novel.userReaction == 'FIRE' ? 'active' : ''}" 
                        data-entity-type="NOVEL" data-entity-id="${novel.novelId}" data-like-status="FIRE">
                        🔥 <BR><span id="reaction-count-FIRE-${novel.novelId}">${novel.fireCount}</span>
                    </button>
                </div>    
    
            </div>
    <h3>Comments</h3>

    <div class= "cbox">
    <div class="">
    <!-- 🔹 Show login prompt if the user is not logged in -->
    <c:if test="${empty loggedInUser}">
        <p>You must <a href="/auth/loginpage">log in</a> to post a comment.</p>
    </c:if>

    <!-- 🔹 Show comment form if the user is logged in -->
    <c:if test="${not empty loggedInUser}">
        <form action="/comments/add" method="post">
            <input type="hidden" name="entityType" value="novel">
            <input type="hidden" name="entityId" value="${novel.novelId}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <textarea name="content" required class="form-control" placeholder="Write a comment..."></textarea>
            <button type="submit" class="btn btn-primary mt-2">Comment</button>
        </form>
    </c:if>
    </div>
    
    <br>


    <!-- 🔹 Display Comments and Nested Replies -->
    <c:forEach var="comment" items="${comments}">
        <div class="comment">
            <img class="profile-pic" src ="<c:out value='${pageContext.request.contextPath}/user-image/${comment.userName}'/>" onerror="this.src='../images/No-Image.png';">
            <strong>${comment.userName}</strong>
            <small>${comment.timeAgo}</small>
            <p>${comment.content}</p>
            
            

            
            
            <!-- 🔹 Buttons for Delete, Flag, and Reply (Now in the same row) -->
            <div class="comment-actions">
                <div id="like-section-${comment.id}">
                <button class="like-btn ${comment.userReaction == 'LIKE' ? 'active' : ''}" data-entity-type="COMMENT" data-entity-id="${comment.id}" data-like-status="LIKE">
                👍 <span id="like-count-${comment.id}">${comment.likeCount}</span>
                </button>
                <button class="dislike-btn ${comment.userReaction == 'DISLIKE' ? 'active' : ''}" data-entity-type="COMMENT" data-entity-id="${comment.id}" data-like-status="DISLIKE">
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
            <!-- 🔹 Reply Form -->
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

            <!-- 🔹 Display Replies (Nested Comments) -->
            <c:if test="${comment.hasReplies}">
                <div class="replies" style="margin-left: 30px;">
                    <c:forEach var="reply" items="${comment.replies}">
                        <div class="replied-comment">
                            <img class="profile-pic" src ="<c:out value='${pageContext.request.contextPath}/user-image/${reply.userName}'/>" onerror="this.src='../images/No-Image.png';">
                            <strong>${reply.userName}</strong>
                            <small>${reply.timeAgo}</small>
                            <p>${reply.content}</p>

                            <!-- 🔹 Buttons for Delete, Flag, and Like/Dislike (Now in the same row) -->
                            <div class="comment-actions">
                              <div id="like-section-${reply.id}">
                                <button class="like-btn ${reply.userReaction == 'LIKE' ? 'active' : ''}" data-entity-type="COMMENT" data-entity-id="${reply.id}" data-like-status="LIKE">
                                  👍 <span id="like-count-${reply.id}">${reply.likeCount}</span>
                                </button>
                                <button class="dislike-btn ${reply.userReaction == 'DISLIKE' ? 'active' : ''}" data-entity-type="COMMENT" data-entity-id="${reply.id}" data-like-status="DISLIKE">
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
         </div>
         <div class="col-1 col-lg-3 py-3 "></div>
         </div>
        
         
        <!-- ✅ In-content 300x250 mid-section ad -->
      <div class="text-center mt-4">
        <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291"
     crossorigin="anonymous"></script>
<!-- Medium Rectangle -->
<ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:250px"
     data-ad-client="ca-pub-3020770276580291"
     data-ad-slot="8950269194"></ins>
<script>
     (adsbygoogle = window.adsbygoogle || []).push({});
</script>

  <div class="d-none d-lg-block text-center my-3 container-fluid">
            <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-3020770276580291" crossorigin="anonymous"></script>
				<!-- Responsive Horizontal -->
					<ins class="adsbygoogle"
     				style="display:block"
     				data-ad-client="ca-pub-3020770276580291"
     				data-ad-slot="9524461170"
     				data-ad-format="auto"
     				data-full-width-responsive="true"></ins>
		  <script>
     		(adsbygoogle = window.adsbygoogle || []).push({});
		  </script>
          </div>
        
        <%@ include file="../footer2.jsp" %>
       

       
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
<input type="hidden" id="_csrf" 
       name="${_csrf.parameterName}" 
       value="${_csrf.token}" />  
</body>
</html>