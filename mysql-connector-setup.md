# MySQL Connector/J Setup for Windows

## Download MySQL Connector/J

1. **Go to MySQL Connector/J download page:**
   https://dev.mysql.com/downloads/connector/j/

2. **Select Platform Independent:**
   - Choose "Platform Independent" from the dropdown
   - Download the ZIP archive (e.g., `mysql-connector-j-8.0.x.zip`)

3. **Alternative direct download links (version may vary):**
   - MySQL Connector/J 8.0: https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-8.0.33.zip
   - MySQL Connector/J 8.1: https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-8.1.0.zip

## Installation Steps for NetBeans

### Method 1: Add to Project Libraries
1. Extract the downloaded ZIP file
2. Locate the JAR file: `mysql-connector-j-8.x.x.jar`
3. In NetBeans:
   - Right-click your project → Properties
   - Go to "Libraries" category
   - Click "Add JAR/Folder"
   - Navigate to and select the JAR file
   - Click "OK"

### Method 2: Add to Global Libraries (Recommended)
1. Extract the ZIP file
2. Copy the JAR file to a permanent location (e.g., `C:\Java\mysql-connector\`)
3. In NetBeans:
   - Go to Tools → Libraries
   - Click "New Library"
   - Name it "MySQL Connector"
   - Click "Add JAR/Folder"
   - Select the JAR file
   - Click "OK"
4. Add to your project:
   - Right-click project → Properties → Libraries
   - Click "Add Library"
   - Select "MySQL Connector"
   - Click "Add Library"

## Verify Installation

After adding the connector, you can test the connection by running the `testConnection()` method in `DatabaseUtil.java` after setting up your MySQL database.

## Troubleshooting

- **ClassNotFoundException**: Ensure the connector JAR is properly added to project libraries
- **Access denied**: Verify MySQL username/password in DatabaseUtil.java
- **Connection refused**: Make sure MySQL service is running on port 3306
