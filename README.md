# surepay
Sure Pay Technical

#Introduction
This was an exercise to create an application to validate recrords given either a json or cvs file and gives a report with the invalid records.
The solution was designed and implemented with a lot of detail being paid towards following solid principles and OOP principles.
I wrote a docker script just incase the project needs to be taken that direction.
The solution uses a file watcher to always be listening in on a specific directory 

#What is in the project
We have seperation of classes. 
Creation of custom exception for custom exception handling. However although they are not fully utilised the concept is shown.
We have the use of autowiring hence keeping everything loosely coupled.
Demonstration of API creation although without security I would have secured the endpoint with a jwt token.
Note the use of BigDecimal in amounts over the use of Double data type as BigDecimal is more accurate.
The project also contains profiling. To assist in seemless development process and shipping.

#What is missing
Code to move a file to a processig directory and then processed directory
Spring security for the endpoint
I would also have put in a different endpoint with filters



