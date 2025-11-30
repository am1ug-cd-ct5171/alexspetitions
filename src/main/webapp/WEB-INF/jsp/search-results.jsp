<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results - Petitions System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🔍 Search Results</h1>
            <c:if test="${not empty query}">
                <p class="search-info">Search query: "<strong>${query}</strong>"</p>
            </c:if>
            <div class="nav-links">
                <a href="${pageContext.request.contextPath}/petitions/search">New Search</a>
                <a href="${pageContext.request.contextPath}/petitions/view">View All Petitions</a>
                <a href="${pageContext.request.contextPath}/petitions/create">Create Petition</a>
            </div>
        </div>

        <c:choose>
            <c:when test="${empty results}">
                <div class="no-results">
                    <h2>No Results Found</h2>
                    <p>We couldn't find any petitions matching your search criteria.</p>
                    <p>Try different keywords or browse all petitions.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="results-count">
                    <h3>Found ${results.size()} petition(s)</h3>
                </div>

                <div class="petitions-grid">
                    <c:forEach items="${results}" var="petition">
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
