<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <!DOCTYPE html>
  <html>

  <head>
    <script type="text/javascript" src="//c.pubguru.net/pghb.easternwordsmith_com.tc.js" async></script>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Manage Announcement</title>
    <link rel="shortcut icon" type="image/x-icon" href="images/logo2.png">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="../css/stylesheet.css">
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        
  </head>

  <body>
    
<%@ include file="../nav2.jsp" %>


<div class="container mt-4 stm">
    <h2>Manage Announcements</h2>
    <hr>

    <!-- Add Announcement Form -->
    <div id="add-form-section">
        <h4>Add New Announcement</h4>
        <form method="post" action="/announcements/add">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="mb-3">
                <label>Title:</label>
                <input type="text" name="title" class="form-control" required>
            </div>

            <div class="mb-3">
                <label>Content:</label>
                <textarea name="content" class="form-control" rows="4" required></textarea>
            </div>

            <button type="submit" class="btn btn-success">Add Announcement</button>
        </form>
    </div>

    <!-- Edit Announcement Form (Hidden by default) -->
    <div id="edit-form-section" style="display: none;">
        <h4>Edit Announcement</h4>
        <form id="edit-form" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" id="edit-id" name="id">

            <div class="mb-3">
                <label>Title:</label>
                <input type="text" id="edit-title" name="title" class="form-control" required>
            </div>

            <div class="mb-3">
                <label>Content:</label>
                <textarea id="edit-content" name="content" class="form-control" rows="4" required></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Update Announcement</button>
            <button type="button" class="btn btn-secondary" id="cancel-edit">Cancel</button>
        </form>
    </div>

    <hr>

    <h4>Existing Announcements</h4>
    <table class="table table-dark table-striped">
        <thead>
            <tr>
                <th>Title</th>
                <th>Content</th>
                <th>Created</th>
                <th>Visible</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${announcements}">
                <tr>
                    <td>${a.title}</td>
                    <td><div>${a.content}</div></td>
                    <td>${a.createdAt}</td>
                    <td>${a.visible ? "Yes" : "No"}</td>
                    <td>
                        <button class="btn btn-sm btn-info edit-btn"
                                data-id="${a.id}"
                                data-title="${a.title}"
                                data-content="${a.content}">
                            Edit
                        </button>

                        <form method="post" action="/announcements/delete/${a.id}" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-sm btn-danger"
                                    onclick="return confirm('Delete this announcement?');">Delete</button>
                        </form>

                        <form method="post" action="/announcements/toggle/${a.id}" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-sm ${a.visible ? 'btn-warning' : 'btn-success'}">
                                ${a.visible ? 'Hide' : 'Show'}
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="../footer2.jsp" %>





        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="../js/script.js"></script>
  </body>

  </html>