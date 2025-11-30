<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Petitions - Petitions System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container-narrow">
        <div class="header">
            <h1>🔍 Search Petitions</h1>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/petitions/view">View All Petitions</a>
                <a href="${pageContext.request.contextPath}/petitions/create">Create Petition</a>
            </div>
        </div>

        <div class="search-card">
            <div class="search-icon">🔎</div>
            <h2>Find a Petition</h2>
            <p>Search by title or description keywords</p>

            <form action="${pageContext.request.contextPath}/petitions/search-results" 
                  method="get" class="search-form">
                <input type="text" 
                       name="query" 
                       class="search-input" 
                       placeholder="Enter keywords to search..." 
                       required>
                <button type="submit" class="btn-search">Search</button>
            </form>
        </div>
    </div>
</body>
</html>
