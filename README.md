# phone-numbers-validator
a single page application that uses the provided 
database (SQLite 3) to list and categorize country phone numbers.
Phone numbers are categorized by country, state (valid or not valid), country 
code and number.It's possible to filter by country and state.

# Steps to run project
mvn clean install <br >
docker build -t phone-validator-image . <br >
docker run -d -p 3333:3333 --name phone-validator-container phone-validator-image:latest <br >
navigate to http://localhost:3333/ <br >

# Application on Swagger UI 
http://localhost:3333/swagger-ui.html
<br >
this link is for the json docs.<br >
http://localhost:3333/v2/api-docs 

