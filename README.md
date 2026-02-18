# Java Swing Banking System

A complete Java Swing banking application with MySQL database integration featuring beautiful UI and real database operations.

## Features

- **Role-based Authentication**: ADMIN, TELLER, and CUSTOMER roles with real database authentication
- **Beautiful UI**: Modern, professional interface with styled components
- **Account Management**: Create and manage bank accounts
- **Transaction Processing**: Deposit, withdrawal, and fund transfer operations
- **User Management**: Admin panel for managing users and accounts
- **Database Integration**: MySQL database connectivity with proper error handling

## Prerequisites

1. **Java Development Kit (JDK)**: JDK 8 or higher
2. **XAMPP**: For MySQL database (recommended) or standalone MySQL
3. **MySQL Connector/J**: JDBC driver for MySQL

## Quick Setup with MySQL Server

### 1. Install MySQL Server
1. Download MySQL Community Server from https://dev.mysql.com/downloads/mysql/
2. Install and start MySQL service
3. MySQL will run on `localhost:3306` with your configured username and password

### 2. Database Setup
1. Open MySQL Command Line Client or MySQL Workbench
2. Create a new database named `banking_system`
3. Run the following SQL to create tables and demo data:

```sql
-- Create database
CREATE DATABASE banking_system;
USE banking_system;

-- Users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'TELLER', 'CUSTOMER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert demo users
INSERT INTO users (username, password_hash, role) VALUES
('admin', 'admin123', 'ADMIN'),
('teller1', 'teller123', 'TELLER'),
('customer1', 'customer123', 'CUSTOMER');

-- Customers table
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Accounts table
CREATE TABLE accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type ENUM('SAVINGS', 'CURRENT', 'FIXED_DEPOSIT') NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    minimum_balance DECIMAL(10, 2) DEFAULT 500.00,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- Transactions table
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    transaction_type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER') NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    description TEXT,
    related_account_id INT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);
```

## How to Run the Application

### Method 1: Command Line
```bash
# Compile all Java files
javac -cp .;mysql-connector-java-8.0.33.jar src/*.java src/model/*.java src/ui/*.java src/util/*.java src/service/*.java

# Run the application
java -cp .;mysql-connector-java-8.0.33.jar src/ui/LoginFrame
```

### Method 2: IDE (NetBeans/Eclipse/IntelliJ)
1. Create a new Java project
2. Copy all source files to your project
3. Add MySQL Connector/J to project libraries
4. Run `LoginFrame.java` as the main class

## Login Credentials

Use these demo credentials to login:

| Role | Username | Password | Access Level |
|------|----------|----------|-------------|
| **Admin** | `admin` | `admin123` | Full system access, user management |
| **Teller** | `teller1` | `teller123` | Customer account operations |
| **Customer** | `customer1` | `customer123` | Personal account access |

## Database Configuration

The application uses the following default MySQL configuration (XAMPP):
- **URL**: `jdbc:mysql://localhost:3306/banking_system`
- **Username**: `root`
- **Password**: `""` (empty password for XAMPP)

If using different MySQL setup, update `DatabaseUtil.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

## Project Structure

```
src/
├── model/          # Data models
│   ├── User.java
│   ├── Customer.java
│   └── Account.java
├── ui/            # Swing GUI components
│   ├── LoginFrame.java          # Enhanced login screen
│   ├── DashboardFrame.java      # Main dashboard with role-based tabs
│   ├── TransactionPanel.java    # Transaction processing
│   ├── TransferPanel.java       # Fund transfers
│   ├── AccountManagementPanel.java
│   ├── AdminUserManagementPanel.java    # Admin user management
│   └── AdminAccountOverviewPanel.java   # Admin account overview
├── service/        # Business logic
│   └── UserService.java         # User authentication & management
└── util/          # Utility classes
    └── DatabaseUtil.java        # Database connection management
```

## Features by Role

### Admin Dashboard
- User management (view, create, edit, delete users)
- Account overview and management
- System reports and analytics

### Teller Dashboard  
- Customer account management
- Transaction processing (deposits/withdrawals)
- Account opening and maintenance

### Customer Dashboard
- View personal accounts
- Transfer funds between accounts
- Transaction history

## Troubleshooting

### Common Issues:

1. **Database Connection Failed**
   - Ensure MySQL service is running
   - Check database name is `banking_system`
   - Verify MySQL credentials in `DatabaseUtil.java` match your MySQL installation

2. **ClassNotFoundException: com.mysql.cj.jdbc.Driver**
   - MySQL Connector/J not in classpath
   - Download from https://dev.mysql.com/downloads/connector/j/
   - Add to project libraries

3. **Access Denied for User**
   - Check MySQL username and password in `DatabaseUtil.java`
   - Verify your MySQL user has proper permissions

4. **Unknown Database 'banking_system'**
   - Create database: `CREATE DATABASE banking_system;`

### Getting Help:
1. Check that all database tables are created using the provided SQL
2. Verify demo user accounts exist in the database
3. Ensure MySQL service is running on port 3306
4. Check application console for specific error messages
5. Update `DatabaseUtil.java` with your actual MySQL credentials

## Next Steps for Development

1. Implement password hashing (bcrypt)
2. Add more transaction types and business logic
3. Implement account statements and reports
4. Add email notifications
5. Enhance security features
6. Add data validation and error handling
