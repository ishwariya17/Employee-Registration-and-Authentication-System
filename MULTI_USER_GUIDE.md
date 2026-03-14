# Multi-User Database System — How It Works

This document explains how the **Employee Registration and Authentication System** supports **multiple users** and how to work with the shared database.

---

## 1. What “Multi-User” Means Here

- **Many user accounts**: Different people can **register** (sign up) and get their own username, email, and password.
- **Shared database**: All user accounts and all employee records are stored in **one MySQL database** (`employee_system`).
- **One user per session**: Each time you run the app, one person **logs in**; the dashboard shows who is signed in and lets them **log out** so someone else can use the app.

So: **multi-user** = many users in the same database, each with their own login; the app supports this by storing users in the `users` table and identifying the current user after login.

---

## 2. Database Layout (Multi-User)

### `users` table (who can log in)

| Column     | Purpose                          |
|-----------|-----------------------------------|
| `id`      | Unique user ID (auto-increment)   |
| `username`| Login name (must be unique)      |
| `email`   | Email (must be unique)           |
| `password`| Stored password (hashed or legacy)|

- **One row per user.**  
- New users are added when they use **Register**; passwords are stored as **SHA-256 hashes** (old seed data may still be plain text for backward compatibility).

### `employees` table (shared employee records)

| Column      | Purpose                |
|------------|------------------------|
| `emp_id`   | Employee ID (primary key) |
| `name`     | Full name              |
| `department` | Department           |
| `email`    | Email (optional)       |
| `salary`   | Salary                 |

- **Shared by all users.**  
- Any logged-in user can add, update, delete, and search employees.  
- So: **multiple users**, **one shared employee list** in one database.

---

## 3. How to Work With the Multi-User System

### Adding new users (registration)

1. Run the application.
2. On the **Login** screen, click **“Create an account”**.
3. Fill in **Username**, **Email**, **Password**, **Confirm password** and click **Register**.
4. New row is inserted into `users`; password is hashed before saving.
5. That person can now log in with that email/username and password.

### Logging in (who is using the app)

1. On the **Login** screen, enter **Email or username** and **Password**.
2. The app checks the `users` table and verifies the password (hash or legacy).
3. If correct, the **Dashboard** opens and shows **“Signed in as &lt;username&gt;”** at the top right.
4. All actions (Add/Update/Delete/Search employees) are done as that user until they log out.

### Logging out (switch user)

1. On the Dashboard, click **“Log out”** (top right).
2. The app closes the dashboard and returns to the **Login** screen.
3. Another person can then log in with their own email/username and password.

### Working with employee data (shared)

- **Add / Update / Delete / Search** use the same `employees` table.
- Every user who can log in sees the **same** employee list.
- So the system is multi-user for **accounts**, and **single shared dataset** for employees.

---

## 4. Running and Configuring the Database

- **One MySQL server**, **one database**: `employee_system`.
- Create the schema (and optional sample data) with:
  ```bash
  mysql -u root -p < database.sql
  ```
- Configure connection (user/password) in:
  `src/main/java/com/employeesystem/db/DBConnection.java`
- Ensure the `users` table has `password` long enough (e.g. `VARCHAR(255)`) for hashed passwords.  
  If the table already exists with `VARCHAR(100)`, run:  
  `ALTER TABLE users MODIFY password VARCHAR(255) NOT NULL;`

---

## 5. Security Notes (Beyond the PRD)

- **Passwords**: New registrations store **SHA-256 hashed** passwords; old seed data may still use plain text for compatibility.
- **Session**: No server; “current user” is only in memory until **Log out** or app exit.
- For stronger security in the future: consider **BCrypt** and **role-based access** (e.g. Admin vs Employee) as in the PRD’s future enhancements.

---

## 6. Summary

| Concept            | Meaning in this project                                |
|--------------------|--------------------------------------------------------|
| Multi-user         | Many user accounts in `users`; each can log in.        |
| Shared database    | One `employee_system` DB; one `employees` table.      |
| Current user       | Shown as “Signed in as &lt;username&gt;”; Log out clears it. |
| Registration       | Inserts a new row into `users` (password hashed).      |
| Login              | Verifies against `users` and opens dashboard.         |

This is how the system works as a **multi-user database application** with a single shared MySQL database and a clear login/logout flow.
