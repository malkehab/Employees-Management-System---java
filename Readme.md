# Employees Management System

## Overview
The Employees Management System is a Java-based desktop application that provides a comprehensive solution for managing employee data, attendance, and payroll. Built with Java Swing for the GUI and MySQL for data persistence, this system offers both administrative and Employees interfaces for efficient workforce management.

## Dependencies & Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server 8.0 or higher
- External Libraries:
  - MySQL Connector/J (JDBC driver) version 8.0.x
  - `mysql-connector-j-8.0.33.jar` or newer

## Features

### User Interfaces
- **Home Interface**: Central navigation hub with options for Admin and Employee access
- **Admin Portal**: 
  - Delete employee records
  - Update employee information
  - Manage employee data
- **Employee Portal**:
  - Login/Registration system
  - Hours logging
  - Salary calculation
  - Personal details view

### Core Functionalities
1. **User Authentication**
   - Secure login system
   - New employee registration
   - Password protection
   - Session management

2. **Employee Management**
   - Add new employees
   - Update employee information
   - Delete employee records
   - View employee details

3. **Attendance & Payroll**
   - Work hours logging
   - Automatic salary calculation (rate: $40/hour)
   - Real-time updates

## Setup Instructions

### 1. Database Setup
1. Download and install MySQL Server from the official website
2. Start MySQL Server on default port (3306)
3. Create a new database:
   ```sql
   CREATE DATABASE employee;
   ```
4. Create the employee table:
   ```sql
   CREATE TABLE employee_table (
       id INT PRIMARY KEY,
       name VARCHAR(255),
       department VARCHAR(255),
       phone BIGINT,
       password VARCHAR(255),
       hours INT,
       salary DOUBLE
   );
   ```

### 2. External Libraries Setup
1. Download MySQL Connector/J:
   - Visit: https://dev.mysql.com/downloads/connector/j/
   - Download Platform Independent version
   - Extract the .jar file

2. Add MySQL Connector to Project:
   - If using an IDE (Eclipse/IntelliJ):
     1. Right-click on project
     2. Select 'Build Path' → 'Configure Build Path'
     3. Under 'Libraries' tab, click 'Add External JARs'
     4. Select the downloaded mysql-connector-j-8.0.xx.jar
     5. Apply and close
   
   - If using command line:
     1. Create a 'lib' folder in your project root
     2. Copy mysql-connector-j-8.0.xx.jar to the 'lib' folder
     3. Add to classpath when compiling/running:
     ```bash
     javac -cp "lib/*" src/*.java
     java -cp "lib/*:." Main
     ```

### 3. Configuration
1. Update database connection parameters in source code if needed:
   ```java
   String url = "jdbc:mysql://127.0.0.1:3306/employee";
   String username = "root";
   String password = "root";
   ```

### 4. Building and Running
1. Using IDE:
   - Import project
   - Ensure dependencies are properly configured
   - Run Main.java

2. Using Command Line:
   ```bash
   # Navigate to project directory
   cd employee-management-system

   # Compile
   javac -cp "lib/*" src/*.java -d out/

   # Run
   java -cp "out:lib/*" Main
   ```

## Technical Architecture

### Frontend (Java Swing)
- **Schema Class**: Base class defining common window properties
  - Standardized window dimensions
  - Consistent positioning
  - Visibility control

- **Custom Components**
  - ButtonCustomizer: Unified button styling
  - Consistent color scheme (Primary: RGB(72, 61, 139))
  - Responsive layouts

### Backend (MySQL)
- **Database**: 'employee'
- **Table**: 'employee_table'
- **Connection Details**:
  - URL: jdbc:mysql://127.0.0.1:3306/employee
  - Username: root
  - Password: root

## Implementation Details

### Key Classes

1. **Data Management**
   - `data`: Static class for session data storage
   - `Schema`: Base class for UI consistency

2. **Authentication Frames**
   - `Log_in_frame`/`Log_in_panel`: User authentication
   - `sign_up_frame`/`sign_up_panel`: New user registration
   - `Check_frame`/`Check_panel`: Invalid login handling

3. **Administrative Frames**
   - `control_frame`/`control_panel`: Admin dashboard
   - `update_frame`/`update_panel`: Employee data modification
   - `delete_frame`/`delete_panel`: Employee record deletion

4. **Employee Frames**
   - `hours_frame`/`hours_panel`: Work hours input
   - `details_frame`/`details_panel`: Employee information display

### Security Features
- Input validation for numeric fields
- Password protection
- Session management
- SQL injection prevention using PreparedStatement

## Design Patterns & Best Practices

1. **MVC-like Structure**
   - Models: Data class and database interactions
   - Views: *_frame and *_panel classes
   - Controllers: Action listeners and event handlers

2. **Code Organization**
   - Consistent naming conventions
   - Modular class structure
   - Separated concerns for UI and logic

3. **Error Handling**
   - Input validation
   - Database connection error handling
   - User feedback through JOptionPane

## Contributors & Acknowledgments

This project was developed as a collaborative effort by our team:
- Tarek Mahmoud - [Github](https://github.com/Tarek-Mahm0ud)
- Roaa Mohamed - [Github](https://github.com/roaa46)
- Hana Shaker - [Github](https://github.com/hanaz12)
- Seif Hatem - [Github](https://github.com/seif-138)
- Eman Diab - [Github](https://github.com/emandeyab)
- Malk Ehab - [Github](https://github.com/malkehab)

## License
This project is licensed under the MIT License - see the LICENSE file for details.
