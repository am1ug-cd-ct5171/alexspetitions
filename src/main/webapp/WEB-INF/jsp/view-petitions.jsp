<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Petitions - Petitions System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📋 All Petitions</h1>
            <div class="nav-links">
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

        <c:choose>
            <c:when test="${empty petitions}">
                <div class="empty-state">
                    <h2>No Petitions Yet</h2>
                    <p>Be the first to create a petition!</p>
                    <a href="${pageContext.request.contextPath}/petitions/create" class="nav-links">
                        <span style="color: #667eea; text-decoration: none; font-weight: 600;">Create Petition</span>
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="petitions-grid">
                    <c:forEach items="${petitions}" var="petition">
                        <div class="petition-card">
                            <h3 class="petition-title">${petition.title}</h3>
                            <c:choose>
                                <c:when test="${petition.description.length() > 200}">
                                    <p class="petition-description">
                                        ${petition.description.substring(0, 200)}...
                                    </p>
                                    <a href="${pageContext.request.contextPath}/petitions/${petition.id}" class="read-more-link">
                                        Read More →
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <p class="petition-description">${petition.description}</p>
                                </c:otherwise>
                            </c:choose>
                            <div class="petition-stats">
                                <div class="vote-count">
                                    <span>👍</span>
                                    <span>${petition.voteCount} votes</span>
                                </div>
                            </div>
                            <form action="${pageContext.request.contextPath}/petitions/vote/${petition.id}" 
                                  method="post" class="vote-form">
                                <input type="email" name="email" class="vote-input" placeholder="Your Email" required>
                                <input type="text" name="name" class="vote-input" placeholder="Your Name" required>
                                <button type="submit" class="btn-vote">Vote Now</button>
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
