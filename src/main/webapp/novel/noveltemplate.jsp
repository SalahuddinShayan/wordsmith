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
        <meta name="keywords" content="${novel.keywords}">
        <title>${novel.novelName}</title>
        <link rel="icon" type="image/x-icon" href="../images/logo2.png">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" >
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
        <style><%@include file="../css/stylesheet.css"%></style>
         <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
		 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
		 <script data-grow-initializer="">!(function(){window.growMe||((window.growMe=function(e){window.growMe._.push(e);}),(window.growMe._=[]));var e=document.createElement("script");(e.type="text/javascript"),(e.src="https://faves.grow.me/main.js"),(e.defer=!0),e.setAttribute("data-grow-faves-site-id","U2l0ZTpjMzAxMTE4Mi1jZDdlLTRiYTMtOTkxNy1lMDZhMThiOGFiMjE=");var t=document.getElementsByTagName("script")[0];t.parentNode.insertBefore(e,t);})();</script>
</head>
<body>

<%@ include file="../nav2.jsp" %>

        
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-2 py-3 "></div>
         <div class="col-10 col-lg-8 py-3">
         <div class="center">
         <a><img width="40%" src ="<c:out value='${pageContext.request.contextPath}/novel-image/${novel.novelId}'/>" 
               alt="images/No_image_available.svg.png" onerror="this.src='../images/No-Image.png';"></a><br>
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
         <div class="col-1 py-3 "><a></a></div>
         <div class="col-11  py-3 ">${novel.description}</div>
         </div>
         <div  class = "row">
         <div class="col-6 py-3 text-center">
         	<a href="../chapter/${frist}" class="btn bg-az"><i class="fas fa-book-open"></i> Read First Chapter</a>
    	</div>
         <div class="col-6 py-3 text-center"><a href="../chapter/${last}" class="btn bg-az"><i class="fas fa-book-open"></i> Read Last Chapter</a></div>
         </div>
         </div>
         <div class="col-1 col-lg-2 py-3 "></div>
         </div>
         
         
         
         
         <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3"></div>
         <div class="col-10 col-lg-6 py-3 ">
         <h4 class="center">Table Of Content</h4>
         <c:forEach var="chapter" items="${Chapters}">
         <div  class = "row">
         <div class="col-8  py-3 stm"><a href="../chapter/${chapter.chapterId}">Chapter ${chapter.chapterNo} : ${chapter.title}</a></div>
         <div class="col-4  py-3 right"><a>${chapter.timeAgo}</a></div>
         </div>
         </c:forEach>
         </div>
         <div class="col-1 col-lg-3 py-3"></div>
         </div>
         
         
         
        
        <div  Style = "margin-top: 30px;" class = "row">
         <div class="col-1 col-lg-3 py-3 "></div>
         <div class="col-10 col-lg-6 py-3 ">
         <div class="comments-section" id="comment">
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
            <strong>${comment.userName}</strong>
            <small>${comment.timeAgo}</small>
            <p>${comment.content}</p>
            
            

            
            
            <!-- 🔹 Buttons for Delete, Flag, and Reply (Now in the same row) -->
            <div class="comment-actions">
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

                <!--<c:if test="${not empty loggedInUser}">
                    <button class="icon-btn reply-btn" onclick="toggleReplyForm('${comment.id}')">
                        <i class="fa-solid fa-reply"></i>
                    </button>
                </c:if>-->
            </div>

            <!-- 🔹 Display Replies (Nested Comments) -->
            <!--<c:if test="${comment.hasReplies}">
                <div class="replies" style="margin-left: 30px;">
                    <c:forEach var="reply" items="${comment.replies}">
                        <div class="comment">
                            <strong>${reply.user.username}</strong>
                            <p>${reply.content}</p>
                            <small>Posted on: ${reply.createdAt}</small>

                            <c:if test="${loggedInUser.id == reply.user.id || loggedInUser.role == 'ADMIN'}">
                                <form action="/comments/delete" method="post">
                                    <input type="hidden" name="commentId" value="${reply.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                            </c:if>

                            <form action="/comments/flag" method="post">
                                <input type="hidden" name="commentId" value="${reply.id}">
                                <button type="submit" class="btn btn-warning btn-sm">Flag</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:if>-->
        </div>
        <br>
    </c:forEach>
</div>
</div>
         </div>
         <div class="col-1 col-lg-3 py-3 "></div>
         </div>
        
        <%@ include file="../footer2.jsp" %>
       

<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>        
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" ></script>
<script type="text/javascript" src="../js/script.js"></script>  
</body>
</html>