# Order Management System  

This project is a **Java-based desktop application** with a **PostgreSQL database backend**, designed to manage clients, products, and orders through an intuitive graphical interface.  
It demonstrates layered software architecture, database integration, and Swing-based GUI design.  

---

## Functionality  

- **Graphical Interface:**  
  - The `MainGUI` window provides buttons to manage clients, products, and orders.  
  - Each section (`ClientManagementWindow`, `ProductManagementWindow`, `OrderManagementWindow`) offers create, update, delete, and view operations.  

- **Database Integration:**  
  - The `ConnectionFactory` class handles PostgreSQL connectivity.  
  - DAO classes (`ClientDAO`, `ProductDAO`, `OrderDAO`, `BillDAO`, `LogDAO`) manage CRUD operations for each table.  

- **Business Logic Layer:**  
  - Classes such as `ClientBLL`, `ProductBLL`, and `OrderBLL` handle validations and intermediate logic between GUI and database.  

- **Validation:**  
  - Input data is verified using the `ClientAgeValidator` and `EmailValidator` before being saved to the database.  

---

## How It Works  

The application starts with the `MainGUI`, which serves as the central dashboard.  
Each management window allows users to insert, modify, delete, or view database entries.  
All database interactions go through the DAO layer, which uses JDBC to communicate with PostgreSQL.  
Validation is applied before executing any operation to maintain data consistency and prevent invalid inputs.  

---

## Technologies Used  

- **Language:** Java  
- **Interface:** Swing GUI  
- **Database:** PostgreSQL (via JDBC)  
- **Architecture:** Layered structure  
  - `presentation/` – GUI windows and main controller.  
  - `bll/` – Business logic and data validation.  
  - `dao/` – Data access objects for database operations.  
  - `model/` – Data structures for entities (`Client`, `Product`, `Order`, `Bill`, `Log`).  
  - `connection/` – Database connection management.  

---

## Future Improvements  

- Add authentication and user roles.  
- Implement search and filtering options in tables.  
- Generate printable reports or export data to CSV/PDF.  
- Add error dialogs for validation feedback in the GUI.  

---

