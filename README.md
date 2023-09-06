# Product Management System with User Registration and Authentication
This is a Java Spring Boot project for a Product Management System with user registration and authentication. 
It allows users to add and view products, and administrators to edit or delete them. 
The project is designed with security in mind, including password hashing and access control.

---

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Usage](#usage)

## Features

- **User Registration and Authentication**: Users can register with their username, email, and password. Passwords are securely hashed before stored.
- **User Roles**: Users are assigned roles, including "USER" and "ADMIN." Users have the ability to create and view products, Admins can modify or delete them.
- **Product Management**: Create, Read, Update, and Delete (CRUD) operations for products.
- **Database Configuration**: Uses Spring Data JPA to interact with the database. Postgresql is used as the default database, but you can configure another database.
- **Security Configuration**: Configures Spring Security for user authentication and authorization based on roles.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17 or higher installed.
- Docker installed or a postgresql database set up.
- Maven for project management.
- Intellij Idea or another IDE of your choice for development.

## Getting Started

1. Clone this repository to your local machine:

   ```bash
   git clone git@github.com:Alexander-Gerontas/ProductManagement.git 
   ```

2. Run the supplied docker compose file by running 
    ```bash
   docker compose up -d
   ```
    or configure your own database settings in `src/main/resources/application.yml`.

3. Ensure that a database is created after running the docker file. Default database name is: db

4. Run the project using Maven:

   ```bash
   mvn spring-boot:run
   ```

5. Setup an API development platform like Postman to send http requess to the application.

## Project Structure

The project follows this structure:

```
product-management-system/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   ├── resources/
    │   │   └── ...
    │   └── test/
    ├── pom.xml
    └── ...
```

- `src/main/java`: Contains the Java source code for the application.
- `src/main/resources`: Contains configuration files for liquidbase.
- `src/test`: Contains integration tests that test how the components interact with each other.
- `pom.xml`: Maven project configuration file.

## Usage

- To create a new user send a POST request at `http://localhost:8080/api/v1/account/registration`. The Json body should have the following structure for both User and Admin roles:

```
{
   "username" : "user",
   "email" : "user@mail.com",    
   "password" : "abc123",
   "role" : "USER"
}
```
No authentication is required for the registration page. 

- To create a new product send a POST request at `http://localhost:8080/api/v1/product/add`. 
The user must submit his credentials along with the request and have a 'USER' role for the request to be authorized.
The Json body should have the following format:

```
{
    "name" : "product",
    "description" : "description",
    "price" : 10.0
}
```

- To update a product send a PATCH request at `http://localhost:8080/api/v1/product/edit/{id}`.
  The user must submit his credentials and have a 'ADMIN' role for the request to be authorized.
  The Json body can have the same format as the one used for creating the product.

- To view a product send a GET request at `http://localhost:8080/api/v1/product/get/{id}`.
  The user must submit his credentials and have a 'USER' role for the request to be authorized.

- To delete a product send a DELETE request at `http://localhost:8080/api/v1/product/delete/{id}`.
  The user must submit his credentials and have a 'ADMIN' role for the request to be authorized.
  