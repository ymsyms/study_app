#SQL Study App is a full-stack learning application built to help to study SQL topics, notes, and questions.

Backend: Spring Boot 3.5.7 +  (PostgreSQL) 14.20 
Frontend: React 19.2 + Vite
Database: PostgreSQL

The app provides a REST API for managing Topics, Notes, and Questions, and a React frontend for easy interaction.

#Architecture
React → Controller → Service → DAO → PostgreSQL

#Explanation:
Frontend: React application consuming the REST API
DTO (Data Transfer Object): Lightweight objects used to transfer data between backend and frontend
Controller: Handles HTTP requests (REST endpoints)
Service: Contains business logic and orchestrates operations
DAO (Data Access Object): Handles database operations using Spring Data JPA

    
#Database Setup
Install PostgreSQL
Create a database:
CREATE DATABASE sqlstudy;

#Configure your credentials in app_services/src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/sqlstudy
spring.datasource.username=postgres
spring.datasource.password=postgres


#When you run the backend, Spring Boot will automatically load schema.sql and data.sql.
#Backend REST API

GET	=> /api/topics	
POST	 => /api/topics	Add a new topic

#Example POST Request:
curl -X POST http://localhost:8080/api/topics \
-H "Content-Type: application/json" \
-d '{"title":"SELECT Basics","description":"Learn SELECT queries"}'

#Notes
Method	Endpoint	Description
GET	/api/notes	List all notes
POST	/api/notes	Add a new note

#Example POST Request:
curl -X POST http://localhost:8080/api/notes \
-H "Content-Type: application/json" \
-d '{"topicId":1,"content":"Use SELECT * to fetch all columns"}'

#Questions
GET	=> /api/questions
POST	 => /api/questions

#Example POST Request:
curl -X POST http://localhost:8080/api/questions \
-H "Content-Type: application/json" \
-d '{"topicId":1,"question":"How to select all rows?","answer":"SELECT * FROM table_name;"}'

#App-web
Navigate to the frontend folder
Install dependencies:
npm install
Run the frontend:
npm run dev
#Open browser at http://localhost:3000
You can view Topics, Notes, and Questions from the UI
Sample Data
The backend automatically loads sample data from data.sql:
Topics: SELECT Basics, JOINs, Indexes
Notes: Sample notes for each topic
Questions: Sample SQL questions and answers
How Layers Work Together
Frontend <--- DTO ---> Controller <--- Service ---> DAO ---> Database
Frontend requests data from the API
Controller receives requests and returns DTOs
Service processes business logic
DAO communicates with PostgreSQL
Database stores persistent data
Run Backend and Frontend Together
Backend:
cd backend
mvn spring-boot:run
Frontend:
cd frontend
npm run dev
Visit: http://localhost:5173
