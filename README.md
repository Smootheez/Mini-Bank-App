# Mini Bank App

A simple RESTful API for a banking application built with Java and Spring Boot. It provides core functionalities like user authentication, account management, and money transactions (deposit, withdraw, transfer).

## Features

-   **User Management**: User registration and login with JWT-based authentication.
-   **Secure Transactions**: Perform deposits, withdrawals, and transfers between accounts.
-   **API Security**: Endpoints are secured using Spring Security.
-   **Database Migrations**: Database schema is managed using Flyway.
-   **Containerization**: Includes a `Dockerfile` for easy containerization and deployment.
-   **Configuration**: Separate configuration profiles for `dev` and `prod` environments.

## Technologies Used

-   **Java 21**: Core programming language.
-   **Spring Boot 3.5.5**: Application framework providing:
    -   Spring Web (for RESTful APIs)
    -   Spring Data JPA (for database interaction)
    -   Spring Security (for authentication and authorization)
    -   Spring Boot Actuator (for monitoring)
    -   Validation (for data binding validation)
-   **PostgreSQL**: Relational database.
-   **Flyway**: Database migration tool.
-   **jjwt**: Java library for creating and parsing JSON Web Tokens (JWTs).
-   **Lombok**: Library to reduce boilerplate code.
-   **Gradle**: Build automation tool.
-   **Docker**: Containerization.

## Getting Started

### Prerequisites

-   JDK 21 or later
-   Gradle 8.x or later
-   Docker (optional, for containerized deployment)
-   A running PostgreSQL instance.

### Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Smootheez/Mini-Bank-App.git
    cd minibankapp
    ```

2.  **Configure the application:**
    The application is configured using environment variables. You can set them in your operating system or create a `.env` file in the project root.

    **Required Environment Variables:**
    -   `SPRING_PROFILE_ACTIVE`: Set the active Spring profile (e.g., `dev`, `prod`).
    -   `DB_URL`: The full JDBC URL for your PostgreSQL database (e.g., `localhost:5432/mini-bank`).
    -   `DB_USER`: The username for your database.
    -   `DB_PASSWORD`: The password for your database.
    -   `JWT_SECRET`: A secure secret key for signing JWTs.

    For local development, you can create a file named `src/main/resources/application-dev.properties` and override properties as needed.

3.  **Run the application:**
    You can run the application using the Gradle wrapper. Make sure your environment variables are loaded in your terminal session.

    ```bash
    ./gradlew bootRun
    ```
    The application will start on `http://localhost:8080` by default.

### Building and Running with Docker

1.  **Build the Docker image:**
    ```bash
    docker build -t minibankapp .
    ```

2.  **Run the Docker container:**
    Pass the required environment variables when running the container.
    ```bash
    docker run -p 8080:8080 \
      -e SPRING_PROFILE_ACTIVE=prod \
      -e DB_URL=<your_prod_db_url> \
      -e DB_USER=<your_prod_db_user> \
      -e DB_PASSWORD=<your_prod_db_password> \
      -e JWT_SECRET=<your_strong_jwt_secret> \
      minibankapp
    ```

## API Endpoints

The following are the primary API endpoints available:

### Authentication

-   `POST /api/v1/auth/register`: Register a new user.
-   `POST /api/v1/auth/login`: Authenticate a user and receive a JWT.

### User Management

-   `GET /api/v1/users/me`: Get current user's information.
-   `PUT /api/v1/users/update`: Update current user's information.
-   `DELETE /api/v1/users/delete`: Delete the current user's account.

### Transactions

-   `POST /api/v1/transactions/deposit`: Deposit money into an account.
-   `POST /api/v1/transactions/withdraw`: Withdraw money from an account.
-   `POST /api/v1/transactions/transfer`: Transfer money between accounts.
