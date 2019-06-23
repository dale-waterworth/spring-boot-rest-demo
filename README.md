# spring-boot-rest-demo
To get started download or clone the repository.

Add the project to Spring Tools suite

Create a MySQL instance currently set to:
- spring.jpa.hibernate.ddl-auto=create-drop
- spring.datasource.url=jdbc:mysql://localhost:3306/demotest
- spring.datasource.username=root
- spring.datasource.password=

Xamp on windows was used to create the DB 'demotest'

Run the tests - Right click run as JUNIT Test 
- com.dale.test TestSuite.java

Run as Spring boot app 

Open Postman and import:
- [CV Demo.postman_collection.json](https://github.com/dale-waterworth/spring-boot-rest-demo/blob/master/CV%20Demo.postman_collection.json "CV Demo.postman_collection.json")

Here one can call the api end points:
- save (post): http://localhost:8080/cv
- get all (get): http://localhost:8080/cv
- get cv (get): http://localhost:8080/cv/1
- save histories (put): http://localhost:8080/cv/companyHistory
- add history (post): http://localhost:8080/cv/1/companyHistory
- add skill (post): http://localhost:8080/cv/1/skill

Restarting the app will result in a fresh database.
