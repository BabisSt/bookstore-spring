# 📚 Bookstore Spring Backend

A RESTful backend for a bookstore app built with **Spring Boot**. Includes user authentication, book management, and reviews. Dockerized for deployment.

---

## 🚀 Features

- **📚 Book Management**
  - Add, update, delete, and retrieve books.
  - Each book is linked to one or more authors (`Author.java`).
  - Book reviews are supported through the `BookReviews.java` class.

- **✍️ Author Support**
  - Manage author data (`Author.java`) and associate them with books.

- **👥 User System**
  - Includes `User.java` and `Person.java` for managing personal and account-related info.
  - JWT-based authentication implemented for secure access to protected endpoints.

- **🛒 Order Handling**
  - Refactored order model (`Order.java`, `BookAmountPair.java`) to support:
    - Multiple books per order
    - Custom amount per book
  - Includes update endpoints for modifying orders post-creation.

- **📝 DTO Usage**
  - Clear separation between internal models and exposed data.
  - Enhances maintainability and reduces tight coupling between API and domain logic.

- **🔍 Search Capability**
  - Powerful `/search` endpoint with optional filters (e.g., genre, title, author) to query books.

- **🔐 Authentication & Security**
  - JWT-based authentication implemented.
  - Initially used in-memory authentication (Spring Security), then migrated to database-based authentication for persistence.

- **🗃️ Database Integration**
  - **Initially** used in-memory H2 database with Hibernate for quick prototyping.
  - **Migrated** to a persistent database setup for long-term data storage.
  - JPA/Hibernate used for ORM.

- **📦 Dockerized Deployment**
  - Contains `Dockerfile` and `.dockerignore`.
  - Ready to run in any containerized environment with:
    ```bash
    docker build -t bookstore-backend .
    docker run -p 8080:8080 bookstore-backend
    ```

---

## ⚙️ Tech Stack

- Java 23  
- Spring Boot  
- JWT  
- Docker  

---


🔧 Endpoints (Examples)

- POST /api/auth/login – login with credentials
- GET /api/books/search – filter by title, genre, author
- POST /api/reviews – submit a book review
