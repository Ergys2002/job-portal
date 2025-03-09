# Job Seeker API

## Overview
This Spring Boot application provides a complete backend API for a job seeking platform. The system supports multiple user roles (Job Seekers, Employers, and Admins) with different functionalities:

- **Job Seekers** can create profiles, upload CVs, search for jobs, and apply to positions
- **Employers** can create company profiles, post job openings, and manage applications
- **Admins** can manage users and system data

The application uses JWT-based authentication with access and refresh tokens for security.

## Prerequisites
* Java 17 or higher
* Maven 3.6+
* MySQL 8.0+ database
* SMTP server access for email functionality
* Git (for cloning the repository)
* Postman (for API testing)

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/yourusername/job-seeker.git
cd job-seeker
```

### Configure Database
1. Create a database in MySQL:
   ```sql
   CREATE DATABASE job_seeker;
   ```

2. Setup environment variables in your IDE in the Run/Debug configuration settings:

   | Variable | Description | Example Value |
      |----------|-------------|---------------|
   | `DB_PORT` | Database port | `3306` |
   | `DB_NAME` | Database name | `job_seeker` |
   | `DB_USER` | Database username | `root` |
   | `DB_PASSWORD` | Database password | `password` |
   | `ACCESS_TOKEN_SECRET` | Secret key for JWT access tokens | `your_secure_random_string` |
   | `REFRESH_TOKEN_SECRET` | Secret key for JWT refresh tokens | `your_secure_random_string` |
   | `MAIL_USER` | Email address for sending notifications | `your_email@example.com` |
   | `MAIL_PASSWORD` | Email password | `your_email_password` |

3. Run the application. The database tables will be created automatically.  
   An admin user will be created with the following credentials:
    ```
    - Email: admin@admin.com
    - Password: admin
    ```

The application will start on http://localhost:8080/api/v1

## Project Structure
```
src
├── main
    ├── java
    │   └── com
    │       └── exh
    │           └── jobseeker
    │               ├── JobSeekerApplication.java
    │               ├── config
    │               │   ├── security          # Security configuration classes
    │               │   └── jwt               # JWT handling classes
    │               ├── controller            # REST API controllers
    │               │   ├── AuthController.java
    │               │   ├── EmployerController.java
    │               │   ├── JobApplicationController.java
    │               │   ├── JobOpeningController.java
    │               │   ├── JobSeekerController.java
    │               │   └── UserController.java
    │               ├── model
    │               │   ├── dto               # Data Transfer Objects
    │               │   │   ├── request       # Request DTOs
    │               │   │   └── response      # Response DTOs
    │               │   └── entity            # JPA entities
    │               ├── repository            # Spring Data repositories
    │               ├── service               # Service layer
    │               ├── exception             # Custom exceptions
    │               └── util                  # Utility classes
    └── resources
        ├── application.properties
        ├── static
        │   └── cv                 # Upload directory for CVs
        └── templates
```

### API Endpoints

#### Authentication
* `POST /api/v1/auth/register` - Register a new user
* `POST /api/v1/auth/login` - Authenticate a user
* `POST /api/v1/auth/refresh-token` - Refresh JWT token
* `POST /api/v1/auth/logout` - Logout user (invalidate refresh token)
* `POST /api/v1/auth/logout-all` - Logout from all devices

#### User Management (Admin Only)
* `GET /api/v1/users` - Get all users with pagination and filtering
* `DELETE /api/v1/users/{id}` - Delete a user

#### Employer Profile Management
* `POST /api/v1/employers` - Create employer profile
* `PUT /api/v1/employers` - Update employer profile

#### Job Seeker Profile Management
* `POST /api/v1/job-seekers` - Create job seeker profile with CV upload
* `PUT /api/v1/job-seekers` - Update job seeker profile

#### Job Management
* `POST /api/v1/jobs` - Create a new job posting
* `GET /api/v1/jobs/my-listings` - Get employer's job postings
* `POST /api/v1/jobs/search` - Search for jobs with filters
* `PUT /api/v1/jobs/{id}` - Update job status

#### Job Applications
* `POST /api/v1/job-applications/{jobOpeningId}` - Apply for a job
* `PUT /api/v1/job-applications/{id}` - Update application status
* `POST /api/v1/job-applications/search` - Search job applications with filters

## Manual Testing with Postman
A Postman collection is available for testing the API endpoints. Import the collection into Postman and follow these steps:

1. First set your environment variable for the base URL:
    - Create an environment in Postman
    - Add a variable named `base-url` with value `http://localhost:8080/api/v1`

2. Test the authentication flow:
    - Register a new user using the `auth/register` endpoint
    - Login with the created user using the `auth/login` endpoint
    - Copy the access token from the response
    - Use the token in subsequent requests by adding it to the Authorization header

3. Test different user roles:
    - For Job Seeker functionality, register with a personal email
    - For Employer functionality, register and then use the employer endpoints
    - For Admin functionality, you'll need to manually set a user as admin in the database

The Postman collection includes sample requests for all major API endpoints.

## System Features

### User Roles
The application supports three user roles:
- **JOB_SEEKER**: Can create profiles, search jobs, and submit applications
- **EMPLOYER**: Can create company profiles and post job openings
- **ADMIN**: Has system administration capabilities

### Authentication Flow
1. Users register with email, password, and personal details
2. Authentication uses JWT with both access and refresh tokens
3. Access tokens expire after 15 minutes
4. Refresh tokens can be used to obtain new access tokens
5. Users can logout from current or all devices

### File Upload
The application handles CV file uploads with a maximum size of 10MB. Files are stored in the `src/main/resources/static/cv` directory.

### Email Notifications
The system can send email notifications for application status changes.

### JWT Token Configuration
The default JWT token expiration settings are:
- Access token: 15 minutes (900000 ms)
- Refresh token: 7 days (604800000 ms)