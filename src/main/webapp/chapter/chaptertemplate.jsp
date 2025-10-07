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
        <meta name="keywords" content="${chapter.keywords}">
        <title>${chapter.novelName}-Chapter ${chapter.chapterNo}</title>
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
<body onload="ChapStyle()">

<%@ include file="../nav2.jsp" %>



        
        
        <div class= "center">
        <h3>${chapter.novelName}-Chapter ${chapter.chapterNo}</h3>
        <h3>${chapter.title}</h3>
        </div>
        
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 py-3"></div>
         <div class="col-10 col-lg-8 py-3 stm"><a href ="../home">Eastern Word Smith</a><a>/</a><a  href="../novel/${chapter.novelName}">${chapter.novelName}</a><a>/</a><a  href = "${chapter.chapterId}">Chapter ${chapter.chapterNo}</a></div>
         <div class="col-1 col-lg-2 py-3"></div>
         </div>
         
        <div  Style = "margin-top: 30px;" class = "row">
        	<div class="col-1 col-lg-2 py-3 "></div>
        	<div class="col-10 col-lg-8 bd-az">
        	<h5>Update:</h5> 
        	<p>Hey guys,<br>
        	   As most of you probably know by now Disqus has stopped providing services to many sites and our site is one of them. Right now, 
        	   we are in the middle of developing our independent comment system. As of today(26 March 2025), we are releasing the comment with 
        	   Limited features. There are other features still under development and may take some time to roll out.
        	</p>
        	<p>As always, please continue to support us. You can show your support in the following ways:
        	</p> 
        	<ul> 
        	<li><strong>Bookmark and Rate Your Favorite Novel:</strong> Head over to Novel Updates, bookmark your favorite stories, rate them, and leave a review. This simple action boosts our visibility and helps us reach more readers like you.</li> 
        	<li><strong>Engage with Our Chapters:</strong> After reading a chapter, please leave a comment or reaction. Your feedback not only lets us know you’re enjoying the story, but it also keeps us motivated.</li> 
        	<li><strong>Support Us on Ko-fi:</strong> As a small operation, we rely on your support to cover our costs. If you can, please consider donating on Ko-fi. Even a small contribution makes a huge difference!</li> 
        	<li><strong>Help Us Improve the Website:</strong> Since we’re still in the early stages, we’d love your feedback on the website’s navigation, design, and user experience. What do you like? What could be better? Are there any features you'd like to see? Share your thoughts with us via the Contact Us form. Your insights are incredibly valuable to us!</li>
        	<li><strong>Do not use Ad-Blocker:</strong> Please know that ads are our only source of revenue right now.</li> 
        	</ul> 
        	
        	
        	</div>
        	<div class="col-1 col-lg-2 py-3 "></div>
        </div>
        
        
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 d-none d-md-block py-3">
         	  <div class = "sticky-ad">
         	  

			
			</div>
         </div>
         <div class="col-12 col-lg-8 py-3">
         <div class ="center">
         <a id="DMS" onclick="lightSwitch()" ><img class ="icon" alt="" src="../images/moon.png"></a>
         <a id="Minus" onclick="TextSizeDecrease()" ><img class ="icon" alt="" src="../images/minus.png"></a>
         <a id="Default" onclick="TextSizeDefault()" ><img class ="icon" alt="" src="../images/alpha.png"></a>
         <a id="Plus" onclick="TextSizeIncrease()" ><img class ="icon" alt="" src="../images/plus-sign.png"></a>
         <a href="../chapter-previous/${chapter.chapterId}"><img class ="icon" alt="" src="../images/arrow-left.png"></a>
         <a href="../novel/${chapter.novelName}"><img class ="icon" alt="" src="../images/home.png"></a>
         <a href="../chapter-next/${chapter.chapterId}"><img class ="icon" alt="" src="../images/right-arrow.png"></a>
         </div>
         <section class ="wcontainer" id="CS">
         ${chapter.content}
         </section>
         <div class ="center">
         <a id="DMS" onclick="lightSwitch()" ><img class ="icon" alt="" src="../images/moon.png"></a>
         <a id="Minus" onclick="TextSizeDecrease()" ><img class ="icon" alt="" src="../images/minus.png"></a>
         <a id="Default" onclick="TextSizeDefault()" ><img class ="icon" alt="" src="../images/alpha.png"></a>
         <a id="Plus" onclick="TextSizeIncrease()" ><img class ="icon" alt="" src="../images/plus-sign.png"></a>
         <a href="../chapter-previous/${chapter.chapterId}"><img class ="icon" alt="" src="../images/arrow-left.png"></a>
         <a href="../novel/${chapter.novelName}"><img class ="icon" alt="" src="../images/home.png"></a>
         <a href="../chapter-next/${chapter.chapterId}"><img class ="icon" alt="" src="../images/right-arrow.png"></a><br><br><br>
         <p><script type='text/javascript' src='https://storage.ko-fi.com/cdn/widget/Widget_2.js'></script><script type='text/javascript'>kofiwidget2.init('Support Us on Ko-fi', '#1F8FFF', 'Y8Y2163B9B');kofiwidget2.draw();</script></p> 
         </div>
         </div>
         <div class="col-1 col-lg-2 d-none d-md-block py-3">
			

		</div>
         </div>

	

        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3 "></div>
         <div class="col-10 col-lg-6 py-3 ">
         <div class="comments-section" id="comment">

            <div id="reaction-section-${chapter.chapterId}" class="chapter-reactions row">
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${chapter.userReaction == 'LOVE' ? 'active' : ''}" 
                        data-entity-type="CHAPTER" data-entity-id="${chapter.chapterId}" data-like-status="LOVE">
                        ❤️ <br><span id="reaction-count-LOVE-${chapter.chapterId}">${chapter.loveCount}</span>
                    </button>
                </div>
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${chapter.userReaction == 'ANGRY' ? 'active' : ''}" 
                        data-entity-type="CHAPTER" data-entity-id="${chapter.chapterId}" data-like-status="ANGRY">
                        😡 <br><span id="reaction-count-ANGRY-${chapter.chapterId}">${chapter.angryCount}</span>
                    </button>
                </div>
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${chapter.userReaction == 'LAUGH' ? 'active' : ''}" 
                        data-entity-type="CHAPTER" data-entity-id="${chapter.chapterId}" data-like-status="LAUGH">
                        😂 <br><span id="reaction-count-LAUGH-${chapter.chapterId}">${chapter.laughCount}</span>
                    </button>
                </div>    
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${chapter.userReaction == 'SAD' ? 'active' : ''}" 
                        data-entity-type="CHAPTER" data-entity-id="${chapter.chapterId}" data-like-status="SAD">
                        😢 <br><span id="reaction-count-SAD-${chapter.chapterId}">${chapter.sadCount}</span>
                    </button>
                </div>    
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${chapter.userReaction == 'WOW' ? 'active' : ''}" 
                        data-entity-type="CHAPTER" data-entity-id="${chapter.chapterId}" data-like-status="WOW">
                        😲 <br><span id="reaction-count-WOW-${chapter.chapterId}">${chapter.wowCount}</span>
                    </button>
                </div>    
                <div class="col-4 col-lg-2">
                    <button class="reaction-btn ${chapter.userReaction == 'FIRE' ? 'active' : ''}" 
                        data-entity-type="CHAPTER" data-entity-id="${chapter.chapterId}" data-like-status="FIRE">
                        🔥 <BR><span id="reaction-count-FIRE-${chapter.chapterId}">${chapter.fireCount}</span>
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
            <input type="hidden" name="entityType" value="chapter">
            <input type="hidden" name="entityId" value="${chapter.chapterId}">
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
         
        
        <%@ include file="../footer2.jsp" %>
       
     
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>
<input type="hidden" id="_csrf" 
       name="${_csrf.parameterName}" 
       value="${_csrf.token}" />  
</body>
</html>