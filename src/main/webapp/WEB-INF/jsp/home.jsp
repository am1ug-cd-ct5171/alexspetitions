<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome - Petitions System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <div class="hero">
            <div class="hero-icon">📢</div>
            <h1>Welcome to Petitions System</h1>
            <p>Make your voice heard! Create, sign, and discover petitions that matter to you.</p>
        </div>

        <div class="action-cards">
            <a href="${pageContext.request.contextPath}/petitions/view" class="action-card">
                <div class="action-card-icon">📋</div>
                <h2>View All Petitions</h2>
                <p>Browse all active petitions and support the causes you believe in by voting.</p>
            </a>

            <a href="${pageContext.request.contextPath}/petitions/create" class="action-card">
                <div class="action-card-icon">📝</div>
                <h2>Create a Petition</h2>
                <p>Start a new petition and gather support for your cause from the community.</p>
            </a>

            <a href="${pageContext.request.contextPath}/petitions/search" class="action-card">
                <div class="action-card-icon">🔍</div>
                <h2>Search Petitions</h2>
                <p>Find specific petitions by searching through titles and descriptions.</p>
            </a>
        </div>
    </div>
</body>
</html>