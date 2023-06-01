# SimpleNetdisk

SimpleNetdisk is a simple practice project for a file hosting service. It provides the following interfaces:

- User registration, login, and logout
- File upload, download, create folder, delete folder, delete file, and paginated file listing

This project is developed in Java and uses the SSM (Spring+SpringMVC+MyBatis) framework and MySQL to provide the interfaces.

## Features

- User Management
  - User Registration: Users can register by providing their username, password, and other necessary information.
  - User Login: Registered users can log in to the system using their username and password.
  - User Logout: Logged-in users can actively log out of the system.
- File Management
  - File Upload: Logged-in users can upload files to the file hosting service.
  - File Download: Logged-in users can download files from the file hosting service.
  - Create Folder: Logged-in users can create new folders in the file hosting service.
  - Delete Folder: Logged-in users can delete folders and all files within them from the file hosting service.
  - Delete File: Logged-in users can delete files from the file hosting service.
  - Paginated File Listing: Logged-in users can view a paginated list of files in the file hosting service.

## Technology Stack

- Java: The project is developed using the Java programming language.
- SSM Framework: The project is built using the Spring, SpringMVC, and MyBatis framework.
- RESTful APIs: The project provides a set of RESTful APIs for frontend integration.
- Database: The project utilizes a relational database to store user and file information.

## Getting Started

1. Prerequisites

   - Java 8 or higher
   - Maven
   - MySQL database

2. Clone the project

   ```
   git clone https://github.com/yourusername/SimpleNetdisk.git
   ```

3. Configure the database

   - Create a new database in MySQL.
   - Modify the database connection configuration in `src/main/resources/application.properties` to match your own configuration.

4. Import the database schema

   - Open a command prompt and navigate to the project's root directory.

   - Run the following command to import the database schema:

     ```
     mysql -u username -p database_name < src/main/resources/database.sql
     ```

5. Build and run

   - Open a command prompt and navigate to the project's root directory.

   - Run the following command to build the project:

     ```
     mvn clean package
     ```

   - Once the build is successful, run the following command to start the project:

     ```
     mvn jetty:run
     ```

   - After the project starts, you can access the file hosting application by opening `http://localhost:8080` in your browser.


## Notes

- This project is intended for learning and reference purposes only. It is a practice project and should not be used in production environments.
