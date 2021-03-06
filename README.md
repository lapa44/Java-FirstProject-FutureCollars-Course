# Accounting system
> Created by team P-16-irek-jonasz-magda

### Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Contact](#contact)

### General info
This API allows to generate and manage invoices.

### Technologies
* Java 11
* Spring 2.2.2
* Maven 4.0.0
* REST
* Junit 5
* AssertJ 3.15
* Swagger 2.9.2
* Hibernate 5.4.10 + H2 1.4.200 
* MapStruct 1.3.1
* Jaccoco 0.8.5
* iTextpdf 5.5.13.1
* JavaFaker 1.0.2
* SOAP

### Features
* Managing invoices in several databases:
	* In memory
	* In file
	* H2 Database with Hibernate
* Saving single invoice as PDF.
* Saving all invoices as PDF's in ZIP.
* Sending mail to admin, including every change in database.

### Setup

To run tests on your local machine you can run them directly from your IDE, or type in terminal:

``` mvn clean test ```

or second way which will also check checkstyle rules:

```mvn clean verify```

Now if you want to run application, you can use your IDE again to execute `main` method in the `com.futurecollars.accounting.Application` class.
The other method is to type in terminal:

```mvn clean spring-boot:run```

And the last thing to finish setup, while in terminal go to directory `angularClient` and type:

```ng serve --open```

It will launch basic angular front end, where you can try this app.

### Status
* Currently finished, there are some things to add here, but I want to focus on other projects now.

### Contact
If you have any questions or you want to offer us a job feel free to contact us on our github accounts. ;)

[Jonasz](https://github.com/lapa44)

[Irek](https://github.com/ireneuo)

[Magda](https://github.com/magdalena-git-hub)