# Online Voting System

A secure, intuitive, and robust Online Voting System built with **Java Swing** and **MySQL**. This application features role-based access control (Admin & Voter), voter registration, candidate management, secure voting with duplicate-vote prevention, and real-time election results.

## Features

- **Role-Based Access Control:** Separate dashboards for Admins and Voters.
- **Admin Panel:** Manage elections, add candidates, and view real-time results.
- **Voter Panel:** Register securely, view candidate agendas, cast a vote, and receive confirmation.
- **Secure Voting:** MySQL triggers prevent duplicate voting automatically.
- **Engaging UI:** Modern aesthetic using rounded components and custom styling.

## Tech Stack

- **Frontend:** Java Swing, AWT
- **Backend:** Java (JDBC)
- **Database:** MySQL 8.0 (Containerized via Docker)

## Prerequisites

- **Java JDK 21+**
- **Docker & Docker Compose** (for one-click database setup)

## Quick Start Guide

### Option 1: Using Node Package Manager (NPM / Yarn)
If you have Node.js installed, you can simply run these integrated scripts from `package.json`:
```bash
# 1. Start the database
npm run db:up

# 2. Compile the application
npm run compile        # Linux/macOS
npm run compile:win    # Windows

# 3. Launch the application
npm run start          # Linux/macOS
npm run start:win      # Windows
```

### Option 2: Using Standard Commands

**1. Start the Database**
You don't need to install MySQL manually! Just run:
```bash
docker compose up -d
```
*This will spin up a MySQL container, map port 3306, and automatically create the required database, tables, triggers, and sample data using `setup_db.sql`.*

**2. Compile the Application**
Compile all Java files, including the MySQL connector in the classpath:
```bash
# On Linux/macOS
javac -cp ".:lib/mysql-connector-j-9.1.0.jar" *.java

# On Windows
javac -cp ".;lib/mysql-connector-j-9.1.0.jar" *.java
```

**3. Run the Application**
Launch the GUI application:
```bash
# On Linux/macOS
java -cp ".:lib/mysql-connector-j-9.1.0.jar" swingTrial2

# On Windows
java -cp ".;lib/mysql-connector-j-9.1.0.jar" swingTrial2
```

## Admin Access
A default admin has been seeded in the database:
- **Name:** Admin
- **Password:** admin123  *(If logging in via Voter ID, the Voter ID is `VOTER_ADMIN`)*

## Database Structure & Storage

All data relies exclusively on local storage to preserve maximum privacy and ease of use. Data is stored safely inside **local Docker volumes**:
- The MySQL database container persists all election data on your host machine inside the local Docker volume named `mysql_data` (mapped to `/var/lib/mysql` internally). This ensures any data written persists completely across restarts.
- Initial schemas and triggers execute securely on startup from `setup_db.sql`.

**Tables:**
- `voters`: Stores voter roll numbers, names, passwords, generated IDs, and admin flags.
- `candidates`: Stores candidate IDs, names, and agendas.
- `votes`: Records the votes and associates voter IDs with candidate IDs.

## Troubleshooting

- **Database Connection Failed:** Make sure Docker is running and the container is fully initialized. The `votingsys` database should be running on `localhost:3306` with user `root` and password `mysql`.
- **Image not loading:** The background image `WhatsApp Image 2025-01-31 at 14.25.24_0612e2d3.jpg` must be present in the root directory.

---
*Created as a portfolio project.*
