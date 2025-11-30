# Petitions System

A Spring Boot web application for creating, viewing, searching, and voting on petitions.

## Features

- ✅ Create new petitions with title and description
- ✅ View all petitions with vote counts
- ✅ Search petitions by title or description
- ✅ Vote on petitions (requires email and name)
- ✅ Duplicate vote prevention (by email and session)
- ✅ Detailed petition view for long descriptions
- ✅ Pre-populated sample data on startup

## Technology Stack

- **Backend**: Spring Boot 3.5.8, Spring MVC, Spring Data JPA
- **Frontend**: JSP, CSS3
- **Database**: H2 (in-memory)
- **Build Tool**: Maven
- **Java Version**: 17
- **Server**: Apache Tomcat 10.1

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker (optional, for containerized deployment)

### Running Locally
mvn clean package spring-boot:run