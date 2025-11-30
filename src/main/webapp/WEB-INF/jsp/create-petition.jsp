<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Petition - Petitions System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container-narrow">
        <div class="header">
            <h1>📝 Create New Petition</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/petitions/view">View All Petitions</a>
                <a href="${pageContext.request.contextPath}/petitions/search">Search Petitions</a>
            </div>
        </div>

        <div class="card">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/petitions/create" method="post">
                <div class="form-group">
                    <label for="title">Petition Title *</label>
                    <input type="text" id="title" name="title" required 
                           placeholder="Enter a clear and concise title">
                </div>

                <div class="form-group">
                    <label for="description">Petition Description *</label>
                    <textarea id="description" name="description" required 
                              placeholder="Describe your petition in detail..."></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Create Petition</button>
            </form>
        </div>
    </div>
</body>
</html>
