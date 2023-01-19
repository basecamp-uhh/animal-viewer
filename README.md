# base.camp Animal Viewer
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fbasecamp-uhh%2Fanimal-viewer.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fbasecamp-uhh%2Fanimal-viewer?ref=badge_shield)



## Description

### German
Projekt: Automatische Tiererkennung auf Bauernhöfen zur Erhöhung der Kundentransparenz

In dem Projekt soll mit Hilfe von Bildverarbeitung und Texterkennung ermöglicht werden, dass die Ohr­marken von Kühen automatisch in Bildern erkannt werden. Dabei sollen dann einer Kuh alle Bilder, auf denen diese Kuh mit entsprechender Ohrmarke er­kannt wurde, zugeordnet werden. Schließlich wäre es dann möglich anhand eines Codes, alle Bilder zu ei­ner Kuh zu sehen. Zudem soll dann noch im nächsten Schritt eine App gestaltet werden. Diese App soll zum Beispiel im Supermarkt zum Einsatz kommen, um ei­nen QR-Code auf einem Produkt zu scannen. Über den QR-Code sind dann Bilder von den Haltungsbedingun­gen der Kuh einsehbar, von der das Fleisch stammt.

### English

This project displays images of animals, which can be accessed using scannable QR-codes.

## Live Demo
A live demo is available on our web server: [animal-viewer](http://basecamp-demos.informatik.uni-hamburg.de/animal-viewer/)

## Running instructions

This project contains a web service based on the Spring Boot framework.

- Check out and run the ApplicationController
- use the maven command **spring-boot:run**
- server is started at port **8080**
- for deployment, port can be edited in /src/main/resources/application.properties



## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fbasecamp-uhh%2Fanimal-viewer.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fbasecamp-uhh%2Fanimal-viewer?ref=badge_large)