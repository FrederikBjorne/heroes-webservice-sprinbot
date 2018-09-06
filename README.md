#Spring boot implementation of a micro-service providing information about heroes

The server is implemented using Spring Boot.

The server exposes three public JSON API data endpoints:
1. /api/heros     - returns an array containing lightweight representation of heros
2. /api/hero/:id  - returns an object containing all fields for a specific hero specified by id
3. /api/hero/name - returns an object containing all fields for a specific hero specified by name

Port 8090 is set in application.properties for developing locally and this is used by unit tests too.

All files for Hero web micro-service is located in package com.example.demo.heroes.
All reusable base classes and utilites needed are located in com.example.demo.core.
New microservices should be added in a new folder structure to keep it separated from other services.

The provided data source file dc_heroes.csv is located in src/main/resource and its content of DC comics hero information is
robbed from their repo [here](https://github.com/fivethirtyeight/data/tree/master/comic-characters).

Each line is parsed and each hero data is fed into a Hero class object by heroes/HerosCSVFileImported and the objects are
saved into memory-persistence using H2 by the hero/HeroRepositoryHandler. The Hero class (entity) itself stores short
information (name, alter ego and id) as attributes from a hashmap argument and the leftovers are kept in a details map.
In order to provide short information, typically when listing all heroes, a get method Hero.getShortInfo() is used.

The CRUD repository has been chosen for implementation by heroes/HeroRepository. The actual service hero/HeroService is
separated from the REST controller hero/HeroController. Tests are available in the normal test folder.

##Prerequisites

Java 8 JDK [Install JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Gradle 4.8.1 [Install Gradle](https://gradle.org/install/)

Spring Boot 2.04 [Install Spring boot CLI](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-installing-the-cli)

##Installation

The project is preferrably imported using the free [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) community
edition (recommended).

1. Select import project
2. Check Run | Edit Configurations and Run/Debug Configurations and if empty, click "+"-button and select Application.
3. Ensure configuring the project as the following:
    - Name: Heros
    - Main.class: com.example.demo.Application
    - Class.path: demo_main
    - JRE:        1.8
4. Now build from Build | Build Project.
5. If all goes well, hit Run | Run 'Heros'. The Spring logo should be seen in the Run window.

You may also build and run from a CLI: ./gradlew bootRun`
