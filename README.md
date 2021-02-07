# FoodOrderingAppBackend
Project Structure
The project must follow a definite structure in order to help the co-developers and reviewers for easy understanding. Also, the better project structure makes your code modular and easy to implement any new features on the existing application. Follow the directory structure given in the project stub file. The main module is divided into three sub-modules FoodOrderingApp-api, FoodOrderingApp-db and FoodOrderingApp-service.

FoodOrderingApp-api

configuration - This directory must consist of all the required configuration files of the project(if any). We have already provided swagger config file in stub.

controller - This directory must consist of all the controller classes required for the project. (The list of required controllers along with the API endpoints are listed in the coming segments.)

exception - This directory must consist of the exception handlers for all the exceptions. You have to implement the code for exception handler for all the exceptions already implemented in the project.

endpoints - This directory consists of the json file which is used to generate Request and Response models.(This is already provided to you in the stub file).

test - This directory consists of tests for all the controller classes.


 

FoodOrderingApp-db

config - This directory consists of the database properties and environment properties for local development.

sql - This directory consists of all the SQL queries to create database schema tables.

 

FoodOrderingApp-service

business - This directory must consist of all the implementations of business logic.

common - This directory contains the error code implementation, any enum data type and any unexpected exception handling.

dao - This directory allows us to isolate the application/business layer from the persistence layer and must consist of the implementation of all the data access object classes.

entity - This directory must consist of all the entity classes related to the project to map these class objects with the database.

exception - This directory consists of all the exceptions related to the project. All the exceptions required for the project have been implemented in the stub file.

 
