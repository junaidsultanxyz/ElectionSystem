# Pakistan Election System

**Team:**
1. https://github.com/junaidsultanxyz
2. https://github.com/AilaArshad
3. https://github.com/asmaismail001
4. https://github.com/AYESHAJAVED29

---

## Overview

**Pakistan Election System** is a Java Swing-based desktop application developed using **FlatLaf** for modern UI styling.  
This system is designed to simulate the core functionality of an election management system in Pakistan.

It follows a **three-tier architecture**:
- **Client** – Provides the user interface and interacts with the server.
- **Server** – Processes requests, applies business logic, and communicates with the database.
- **Database** – Stores all persistent data related to voters, candidates, results, and more.

---

## Tech Stack

- **Java (Swing + FlatLaf)** – GUI
- **Socket Programming** – Client-server communication
- **MySQL** – Relational database
- **Maven** – Dependency management
- **XAMPP** – Localhost database server

---

## System Architecture

```
Client GUI ⇄ Server (Socket I/O) ⇄ MySQL Database
```

- **Client** sends request (e.g., vote, register).
- **Server** validates and processes the request.
- **Server** performs database operations and sends response back.
- **Client** displays the result or updates the UI accordingly.

---

## Features

- Voter Registration and Login
- Candidate Management
- Voting System
- Election Result Compilation
- Admin and User Roles
- Secure and Validated Communication

---

## UI Preview


---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/junaidsultanxyz/ElectionSystem.git
   ```

2. Import the project into your IDE.

3. Start your MySQL server (e.g., via XAMPP) and import the SQL database from the `Database/` folder.

4. Run the `Server` application.

5. Run the `Client` application and interact with the system.

---

## 📃 License

This project is open-source. Use it in respectable way and do not make any negative use of it
