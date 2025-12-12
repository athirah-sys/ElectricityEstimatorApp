Electricity Estimator App
ICT602 — Mobile Technology & Development
Individual Assignment — Athirah Izzati Binti Rosli (2023637546)

Overview
Electricity Estimator is an Android application developed as part of the ICT602 Individual Assignment.
The app allows users to calculate electricity usage costs based on monthly consumption (kWh), apply rebates, and store the results in a local SQLite database.

This project demonstrates:
•	Android UI development
•	Activity navigation
•	Form handling & input validation
•	SQLite CRUD operations
•	Custom app icons
•	GitHub version control

Features
1. Add Electricity Usage
Users can:
•	Enter month
•	Enter total units (kWh)
•	Auto-calculate total charges
•	Auto-calculate rebate
•	Store results in SQLite database

2. View Records
• Main screen shows a list of all saved months and their total charges.

3. Detailed View
DetailActivity displays:
•	Month
•	Units
•	Total Charges
•	Rebate %
•	Final Cost
Includes a Delete button.

4. About Page
Shows:
•	Student Name
•	Student ID
•	Course
•	GitHub Repository Link

5. Custom App Icon
A custom icon created and added using Asset Studio.

6. Database Schema (SQLite)
   
| Column       | Type                |
| ------------ | ------------------- |
| id           | INTEGER PRIMARY KEY |
| month        | TEXT                |
| units        | INTEGER             |
| totalCharges | REAL                |
| rebate       | REAL                |
| finalCost    | REAL                |

