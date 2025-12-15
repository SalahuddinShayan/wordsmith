<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<meta charset="UTF-8">
<meta name="google-adsense-account" content="ca-pub-3020770276580291">
<title>Chapter List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" >
<link rel="stylesheet" href="css/stylesheet.css" >
</head>
<body>

<%@ include file="../nav2.jsp" %>

<div class="container mt-4 stm">

    <!-- 🔹 Navigation Buttons -->
    <div class="mb-3">
        <a href="novellist" class="btn btn-outline-light me-2"><i class="fa fa-list"></i> Novel List</a>
        <a href="activenovellist" class="btn btn-outline-primary"><i class="fa fa-book"></i> Active Novel List</a>
    </div>

    <h2>Manage Chapters for <span class="text-primary">${novelname}</span></h2>

    <!-- 🔹 Novel Status Change Form -->
    <form method="post" action="UpdateNovelStatus" class="my-3">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="novelName" value="${novelname}" />
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <label for="status" class="col-form-label text-light">Change Novel Status:</label>
            </div>
            <div class="col-auto">
                <select id="status" name="status" class="form-select">
                    <option value="ONGOING" ${novelStatus == 'ONGOING' ? 'selected' : ''}>Ongoing</option>
                    <option value="COMPLETED" ${novelStatus == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                    <option value="HIATUS" ${novelStatus == 'HIATUS' ? 'selected' : ''}>Hiatus</option>
                    <option value="DROPPED" ${novelStatus == 'DROPPED' ? 'selected' : ''}>Dropped</option>
                </select>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-warning">Update Status</button>
            </div>
        </div>
    </form>

    <hr>

    <!-- 🟢 Add Chapter Form -->
    <div id="add-form-section">
        <h4>Add New Chapter</h4>
        <form:form method="post" action="savechapter" modelAttribute="command">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <div class="row mb-3">
                <div class="col-md-4">
                    <form:label path="chapterNo">Chapter No:</form:label>
                    <form:input path="chapterNo" class="form-control" />
                </div>
                <div class="col-md-8">
                    <form:label path="title">Title:</form:label>
                    <form:input path="title" class="form-control" />
                </div>
            </div>

            <div class="mb-3">
                <form:label path="keywords">Keywords:</form:label>
                <form:input path="keywords" class="form-control" />
            </div>

            <div class="mb-3">
                <form:label path="content">Content:</form:label>
                <form:textarea path="content" class="form-control" rows="5" />
            </div>

            <input type="hidden" name="releaseStatus" value="STOCKPILE" />

            <input type="hidden" name="novelName" value="${novelname}" />
            <button type="submit" class="btn btn-success">Add Chapter</button>
        </form:form>
    </div>

    <!-- 🟡 Edit Chapter Form (Hidden by default) -->
    <div id="edit-form-section" style="display:none;">
        <h4>Edit Chapter</h4>
        <form id="edit-form" method="post" action="savechapter">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="edit-ChapterId" name="ChapterId" />
            <input type="hidden" name="novelName" value="${novelname}" />

            <div class="row mb-3">
                <div class="col-md-4">
                    <label>Chapter No:</label>
                    <input type="number" id="edit-chapterNo" name="chapterNo" class="form-control" required />
                </div>
                <div class="col-md-8">
                    <label>Title:</label>
                    <input type="text" id="edit-title" name="title" class="form-control" required />
                </div>
            </div>

            <div class="mb-3">
                <label>Keywords:</label>
                <input type="text" id="edit-keywords" name="keywords" class="form-control" />
            </div>

            <div class="mb-3">
                <label>Content:</label>
                <textarea id="edit-content" name="content" class="form-control" rows="5" required></textarea>
            </div>

            <button type="submit" class="btn btn-primary">Update Chapter</button>
            <button type="button" id="cancel-edit" class="btn btn-secondary">Cancel</button>
        </form>
    </div>

    <hr>

    <!-- 🔹 All Chapters Table -->
    <h4>All Chapters</h4>
    <p><b>Stock:</b> ${stock}</p>

    <div class="table-fullwidth table-responsive">
        <table class="table table-dark table-striped align-middle table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Chapter No</th>
                    <th>Title</th>
                    <th>Keywords</th>
                    <th>Content</th>
                    <th>Posted On</th>
                    <th>Release Status</th>
                    <th>Released On</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="chapter" items="${Chapters}">
                    <tr>
                        <td>${chapter.chapterId}</td>
                        <td>${chapter.chapterNo}</td>
                        <td class ="Dscroll">${chapter.title}</td>
                        <td class ="Dscroll">${chapter.keywords}</td>
                        <td>
                            <div class ="Dscroll" style="white-space:pre-wrap; word-wrap:break-word;">
                                ${chapter.content}
                            </div>
                        </td>
                        <td>${chapter.postedOn}</td>
                        <td>${chapter.releaseStatus}</td>
                        <td>${chapter.releasedOn}</td>
                        <td>
                            <button class="btn btn-sm btn-info edit-btn"
                                    data-id="${chapter.chapterId}"
                                    data-chapterno="${chapter.chapterNo}"
                                    data-title="${fn:escapeXml(chapter.title)}"
                                    data-keywords="${fn:escapeXml(chapter.keywords)}"
                                    data-content="${fn:escapeXml(fn:substring(chapter.content, 0, 500))}">
                                Edit
                            </button>

                            <form method="post" action="deletechapter" style="display:inline;">
                                <input type="hidden" name="ChapterId" value="${chapter.chapterId}" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <button type="submit" class="btn btn-sm btn-danger"
                                        onclick="return confirm('Delete this chapter?');">
                                    Delete
                                </button>
                            </form>

                            <form method="post" action="ReleaseChapter" style="display:inline;">
                                <input type="hidden" name="ChapterId" value="${chapter.chapterId}" />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <button type="submit" class="btn btn-sm btn-success">
                                    Release
                                </button>
                            </form>

                            <a href="chapter/${chapter.chapterId}" class="btn btn-sm btn-outline-light">View</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="../footer2.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="js/script.js"></script>

<!-- 🧠 Script for toggling edit form -->
<script>
document.querySelectorAll('.edit-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        document.getElementById('edit-ChapterId').value = btn.dataset.id;
        document.getElementById('edit-chapterNo').value = btn.dataset.chapterno;
        document.getElementById('edit-title').value = btn.dataset.title;
        document.getElementById('edit-keywords').value = btn.dataset.keywords;
        document.getElementById('edit-content').value = btn.dataset.content;

        document.getElementById('add-form-section').style.display = 'none';
        document.getElementById('edit-form-section').style.display = 'block';
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });
});

document.getElementById('cancel-edit').addEventListener('click', () => {
    document.getElementById('edit-form-section').style.display = 'none';
    document.getElementById('add-form-section').style.display = 'block';
});
</script>

</body>
</html>
