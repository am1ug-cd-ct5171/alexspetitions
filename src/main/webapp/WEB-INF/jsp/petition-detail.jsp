<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${petition.title} - Petitions System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📋 Petition Details</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/petitions/view">Back to All Petitions</a>
                <a href="${pageContext.request.contextPath}/petitions/create">Create New Petition</a>
                <a href="${pageContext.request.contextPath}/petitions/search">Search Petitions</a>
            </div>
        </div>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">${errorMessage}</div>
        </c:if>

        <div class="petition-detail-card">
            <h2 class="petition-detail-title">${petition.title}</h2>

            <div class="petition-detail-stats">
                <div class="vote-count-large">
                    <span>👍</span>
                    <span>${petition.voteCount} votes</span>
                </div>
            </div>

            <div class="petition-detail-description">
                <h3>Description</h3>
                <p>${petition.description}</p>
            </div>

            <div class="petition-detail-vote-section">
                <h3>Support This Petition</h3>
                <p class="vote-section-info">Add your voice to this petition by voting below.</p>
                <form action="${pageContext.request.contextPath}/petitions/vote/${petition.id}" 
                      method="post" class="vote-form-detail">
                    <div class="vote-inputs-row">
                        <input type="email" name="email" class="vote-input-detail" placeholder="Your Email" required>
                        <input type="text" name="name" class="vote-input-detail" placeholder="Your Name" required>
                    </div>
                    <button type="submit" class="btn-vote-detail">Vote for This Petition</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>