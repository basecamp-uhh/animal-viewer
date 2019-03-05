# ba-service-backend

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/54ca2b461fcb48eebed8a0a6ca4cb4b1)](https://app.codacy.com/app/eugenso/ba-service-backend?utm_source=github.com&utm_medium=referral&utm_content=6hauptvo/ba-service-backend&utm_campaign=Badge_Grade_Dashboard)

This project contains a web service based on the Spring Boot framework.
It features a RESTful endpoint that can retrieve word expansions.

- Check out and run the ApplicationController
- default port is 8080
- for deployment, port can be edited in /resources/application.properties
- check with "curl localhost:8080/expansions?word=IBM&format=json"
- for textual output: "curl localhost:8080/expansions?word=IBM&format=text"
