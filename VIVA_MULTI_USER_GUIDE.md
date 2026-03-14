# Multi-User System — College Viva Guide (Step-by-Step)

Use this guide to **explain** and **demo** the multi-user database system in your viva. Practice once with the app open so you can do it smoothly.

---

## Part 1: What to Say (Explanation)

### Step 1 — One-line definition

**Say:**  
*"Our system is a **multi-user** Employee Registration and Authentication system. That means **many different people can create their own accounts**, log in with their own username and password, and use the same application. All user accounts and employee data are stored in **one shared MySQL database**."*

---

### Step 2 — Three main ideas

**Say (you can write these on the board):**

1. **Multiple user accounts**  
   *"We have a **users** table in MySQL. Each row is one user: username, email, and password. So we can have 10, 50, 100 users—each with their own login."*

2. **One shared database**  
   *"There is **one database** called **employee_system**. Inside it we have two main tables: **users** (who can log in) and **employees** (the employee records). Every logged-in user sees the **same** employee list—add, update, delete, search—all work on this shared data."*

3. **One user per session**  
   *"When you run the app, **one person logs in**. The dashboard shows **who is signed in**. When they click **Log out**, the app goes back to the login screen so **another person** can log in with their own account. So multi-user means: many accounts in the database, one active user at a time on this desktop app."*

---

### Step 3 — How it works technically (if they ask)

**Say:**  
*"When a new user clicks **Register**, we insert a new row into the **users** table with username, email, and a **hashed password** (we use SHA-256 for security). When someone logs in, we check the **users** table: if the email/username and password match, we open the dashboard and remember that user until they log out. The **employees** table is shared—any logged-in user can add, update, delete, or search employees. So the database is multi-user; the desktop app supports it by login and logout."*

---

## Part 2: Live Demo (Step-by-Step)

Do this **in order** so the examiner sees: two different users, one shared employee list, and proper logout.

---

### Demo Step 1 — Show the login screen

1. Run the application: `mvn exec:java` (or run from IDE).
2. **Say:** *"This is the **login** screen. Only registered users can go further."*

---

### Demo Step 2 — Create first user (Registration)

1. Click **"Create an account"**.
2. Fill in:
   - **Username:** `alice`
   - **Email:** `alice@example.com`
   - **Password:** `alice123`
   - **Confirm password:** `alice123`
3. Click **Register**.
4. **Say:** *"We just added **one new user** in the **users** table. So now the database has at least two users—for example admin and alice."*

---

### Demo Step 3 — Login as first user

1. On the login screen, enter:
   - **Email or username:** `alice@example.com` (or `alice`)
   - **Password:** `alice123`
2. Click **Sign in**.
3. **Say:** *"The dashboard opens. See at the top right it says **Signed in as alice**. So the app knows **who** is using it."*

---

### Demo Step 4 — Use shared employee data (multi-user = same data)

1. **Say:** *"The table below is the **employees** list from the database. This list is **shared**—every user sees the same data."
2. (Optional) Add one employee: fill ID, Name, Department, Email, Salary → click **Add**.
3. **Say:** *"I added an employee. This is stored in the **employees** table. When another user logs in, they will see the same list."*

---

### Demo Step 5 — Logout (switch user)

1. Click **"Log out"** (top right).
2. **Say:** *"We are back at the **login** screen. The app no longer has a ‘current user’ until someone logs in again. So we can now **switch user**."*

---

### Demo Step 6 — Login as second user

1. Enter:
   - **Email or username:** `admin@email.com` (or `admin`)
   - **Password:** `admin123`
2. Click **Sign in**.
3. **Say:** *"Now it says **Signed in as admin**. So we have **two different users**—alice and admin—using the **same** application and the **same** database."*

---

### Demo Step 7 — Show same employee list

1. **Say:** *"Look at the employee table. It’s the **same** list we saw when we were logged in as alice. That’s because **employees** is one shared table. Multi-user here means: many users in **users** table, one shared **employees** table, and the app shows who is signed in and allows logout to switch user."*

---

## Part 3: Summary Table (Quick Reference)

| Topic | What to say |
|-------|-------------|
| **Multi-user** | Many user accounts (rows in **users** table); each can log in with own email/username and password. |
| **Shared database** | One MySQL database **employee_system**; one **employees** table for all employee records. |
| **Registration** | New user → new row in **users**; password stored as hash (SHA-256). |
| **Login** | Check **users** table; if match, open dashboard and show "Signed in as &lt;username&gt;". |
| **Logout** | Clears current user; back to login so another person can sign in. |
| **Same data** | Every user sees the same employee list (add/update/delete/search on shared **employees** table). |

---

## Part 4: Likely Viva Questions & Short Answers

**Q: What is multi-user in your project?**  
*"Multiple people can have their own account (username, password). They log in one at a time on this desktop app and use the same employee database."*

**Q: Where are users stored?**  
*"In the **users** table in MySQL database **employee_system**—columns: id, username, email, password."*

**Q: Is the employee data separate for each user?**  
*"No. The **employees** table is shared. Every logged-in user sees and edits the same list."*

**Q: How do you switch from one user to another?**  
*"Click **Log out** on the dashboard. The app returns to the login screen; then the next person enters their email/username and password and signs in."*

**Q: How is password stored?**  
*"New registrations store a **hash** of the password (SHA-256), not plain text. Old seed data like admin123 may still be plain for compatibility."*

**Q: Can two users use the app at the same time?**  
*"This is a single desktop application. So only **one person** is using it at a time. But the **database** can have many users; we support multi-user by having many accounts and login/logout to switch who is using the app."*

---

## Part 5: One-Minute Script (If Time Is Short)

*"Our project is a **multi-user** employee system. We have a **users** table where each row is one account—username, email, password. When someone registers, we add a new row and hash the password. When they log in, we check the **users** table and open the dashboard and show ‘Signed in as username’. The **employees** table is shared—everyone sees the same data. To switch user, we click **Log out** and the next person logs in. So multi-user means: many accounts in the database, one shared employee list, and login/logout to change who is using the app."*

---

**File to show if asked:** You can open **MULTI_USER_GUIDE.md** in the project for the technical details. Use **VIVA_MULTI_USER_GUIDE.md** (this file) for the viva explanation and demo steps.
