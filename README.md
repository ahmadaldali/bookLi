# bookLi
bookLi is a booking tool backend built with Java and the Spring Boot framework. It provides a RESTful API for user authentication, including registration and login functionalities secured with JSON Web Tokens (JWT).

## Features

-   **User Authentication**: Secure endpoints for user registration and login.
-   **Booking**: Create, and delete bookings.
-   **User**: Get the availability of a user.

## Technologies Used

-   **Backend**: Java 17, Spring Boot 3
-   **Authentication**: Spring Security, JWT (jjwt)
-   **Database**: Spring Data JPA, PostgreSQL
-   **Build Tool**: Maven
-   **Utilities**: Lombok

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

-   JDK 17 or later
-   Maven
-   PostgreSQL

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/ahmadaldali/bookli.git
    cd bookli
    ```

2.  **Configure the database:**
    Open `src/main/resources/application.properties` and update the database connection details to match your local PostgreSQL setup.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/bookli_db
    spring.datasource.username=postgres
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```
    Ensure you create a database named `bookli_db` or change the URL to an existing database.

3.  **Build and run the application:**
    You can run the application using the Maven wrapper included in the project.

    ```sh
    # On macOS/Linux
    ./mvnw spring-boot:run

    # On Windows
    ./mvnw.cmd spring-boot:run
    ```
    The application will start on `http://localhost:8000`.

## Project Structure

-   `src/main/java/com/bookli/auth`: Contains all authentication-related classes, including controllers, DTOs, JWT service, and authentication service.
-   `src/main/java/com/bookli/booking`: Contains all booking-related classes, including controllers, entities, DTOs, repositories and services.
-   `src/main/java/com/bookli/user`: Contains all user-related classes, including controllers, entities, DTOs, repositories and services.
-   `src/main/java/com/bookli/config`: Houses Spring configuration files, such as `SecurityConfig` for web security.
-   `src/main/java/com/bookli/common`: Contains shared code, such as enums and the `GlobalExceptionHandler`.
-   `src/main/resources`: Contains configuration files (`application.properties`) and static resources like custom error messages (`errors.properties`).
