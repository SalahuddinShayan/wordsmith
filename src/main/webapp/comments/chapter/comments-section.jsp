<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="comments-section">
    <h3>Comments</h3>

    <!-- 🔹 Show login prompt if the user is not logged in -->
    <c:if test="${empty loggedInUser}">
        <p>You must <a href="/auth/loginpage">log in</a> to post a comment.</p>
    </c:if>

    <!-- 🔹 Show comment form if the user is logged in -->
    <c:if test="${not empty loggedInUser}">
        <form action="/comments/add" method="post">
            <input type="hidden" name="entityType" value="${entityType}">
            <input type="hidden" name="entityId" value="${entityId}">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <textarea name="content" required class="form-control" placeholder="Write a comment..."></textarea>
            <button type="submit" class="btn btn-primary mt-2">Submit</button>
        </form>
    </c:if>

    <hr>

    <!-- 🔹 Display Comments and Nested Replies -->
    <c:forEach var="comment" items="${comments}">
        <div class="comment">
            <strong>${comment.userName}</strong>
            <p>${comment.content}</p>
            <small>Posted on: ${comment.createdAt}</small>

            <!-- 🔹 Delete button (only for comment owner or admin) -->
            <c:if test="${loggedInUser.username == comment.userName || loggedInUser.role == 'ADMIN'}">
                <form action="/comments/delete" method="post">
                    <input type="hidden" name="commentId" value="${comment.id}">
                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
            </c:if>

            <!-- 🔹 Flag button (any user can flag a comment) -->
            <form action="/comments/flag" method="post">
                <input type="hidden" name="commentId" value="${comment.id}">
                <button type="submit" class="btn btn-warning btn-sm">Flag</button>
            </form>

            <!-- 🔹 Reply button (only logged-in users can reply) -->
            <c:if test="${not empty loggedInUser}">
                <button class="btn btn-link btn-sm" onclick="toggleReplyForm('${comment.id}')">Reply</button>
                <div id="reply-form-${comment.id}" style="display: none; margin-left: 20px;">
                    <form action="/comments/add" method="post">
                        <input type="hidden" name="entityType" value="COMMENT">
                        <input type="hidden" name="entityId" value="${comment.id}">
                        <textarea name="content" required class="form-control" placeholder="Write a reply..."></textarea>
                        <button type="submit" class="btn btn-primary mt-2">Submit</button>
                    </form>
                </div>
            </c:if>

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
        <hr>
    </c:forEach>
</div>