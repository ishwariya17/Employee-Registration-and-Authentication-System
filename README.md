# Employee Registration and Authentication System

A desktop-based Employee Management System developed using **Java Swing** and **MySQL** for secure employee registration, authentication, and management.
The application provides role-based access control for Admins, Employees, and Users with an interactive graphical user interface.

---

## 🚀 Features

* User Registration and Login Authentication
* Secure Database Connectivity using JDBC
* Employee Management System (CRUD Operations)
* Search Employees by ID or Name
* Role-Based Access Control
* Admin Dashboard with Full Access
* Employee Profile View
* User Dashboard with Limited Access
* Java Swing GUI Interface
* MySQL Database Integration

---

## 👤 User Roles

### 🔑 Admin

* Add Employees
* Update Employee Details
* Delete Employees
* View All Employees

### 👨‍💼 Employee

* Login using Employee Email
* View Personal Profile
* Read-Only Access

### 👥 Normal User

* Access Dashboard
* Add Employees Only

---

## 🛠️ Tech Stack

* **Frontend:** Java Swing
* **Backend:** Java
* **Database:** MySQL
* **Connectivity:** JDBC
* **Build Tool:** Maven
* **Version Control:** Git & GitHub

---

## 📂 Project Structure

```bash
src/
 └── main/
      └── java/
           └── com/employeesystem/
                ├── dao/
                ├── db/
                ├── model/
                ├── ui/
                └── App.java
```

---

## ⚙️ Database Setup

1. Open MySQL Workbench
2. Create a database named:

```sql
employee_system
```

3. Run the `database.sql` file

---

## ▶️ Run the Project

### Using Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=com.employeesystem.App
```

---

## 🔐 Default Admin Login

```text
Email: admin@email.com
Password: admin123
```

---

## 📸 Screenshots

* Login Page
* Registration Page
* Admin Dashboard
* Employee Profile
* Employee Management Table

(Add screenshots here)

---

## 📌 Future Enhancements

* Password Encryption
* Email Verification
* Export Employee Reports
* Dark Mode UI
* Attendance Management
* Role Management System

---

## 🤝 Contributing

Contributions and suggestions are welcome.

---

## 📄 License

This project is developed for educational and learning purposes.
