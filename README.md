# EmployeeCRM
REST API to pull data from provided schema using spring boot. 
Develop following REST endpoints to fetch data with filters.  
1. Get a list of employees with filters for: - Performance score for a given review_date - Department (should support multiple departments contains filter)  - Projects (should support multiple projects contains filter) - "/api/employees/filter" POST
2. Fetch detailed employee information, including department, projects, and last 3 
performance reviews using id. - "/api/employees/{emp-id}" GET
