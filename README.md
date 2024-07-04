
# Order Management System

## Overview

The Order Management System is a Java Swing-based application designed to manage orders, customers, products, and users with role-based access control. The system includes functionalities for adding, editing, deleting, and viewing records. It supports different user roles such as Admin and Agent, each with specific dashboards and permissions.


## Features

- User Login and Authentication
- Role-Based Dashboards (Admin and Agent)
- Agent Management
- Customer Management
- Product Management
- Order Management
- Database Backup
- Profile Viewing

## File Structure

### Database and Connection Management
- DbConnection.java: Manages the connection to the database.
- DatabaseBackup.java: Provides functionalities for backing up the database.

## Data Access Objects (DAOs)
- AgentDao.java: Data access object for agent-related operations.
- CustomerDao.java: Data access object for customer-related operations.
- ProductDao.java: Data access object for product-related operations.
- UserDao.java: Data access object for user-related operations.

## Data Transfer Objects (DTOs)
- AgentDto.java: Data transfer object for agent data.
- CustomerDto.java: Data transfer object for customer data.
- ProductDto.java: Data transfer object for product data.
- UserDto.java: Data transfer object for user data.

## UI Pages
- AgentPage.java: UI for managing agents.
- AgentProfilePage.java: UI for displaying agent profiles.
- CustomerPage.java: UI for managing customers.
- Dashboard.java: Main dashboard UI, with different views for Admin and Agent.
- LoginForm.java: UI for user login.
- ProductPage.java: UI for managing products.
- PurchasePage.java: UI for managing purchases.
- UserPage.java: UI for managing users.

## Backup
Use ‘DatabaseBackup.java’ for database backup

## Utilities
BuildTableModel.java: Utility class for building table models from result sets.

## SQL
agenthub.sql: SQL script for setting up the database schema and initial data.

## Database
### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Database

## Setup:
1. Clone the Repository
2. Set up the Database
- Import ‘agenthub.sql’ file into your MySQL Workbench 
3. Configure Database Connection
- Update your database connection details in ‘DbConnection.java’
private static final String URL = "jdbc:mysql://localhost:3306/agenthub";

private static final String USER = "your_username";

private static final String PASSWORD = "your_password";


4. Compile and Run the Application


