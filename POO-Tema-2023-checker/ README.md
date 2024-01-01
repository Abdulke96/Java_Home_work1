# IMDB Clone App
===================
## Description
This is a comprehensive IMDB GUI and CLI clone app designed for managing movies, actors, and related information. The app utilizes a local database to store users, movie and actor details. It provides functionalities such as adding, deleting, updating, and listing movies and actors. Additionally, users can search for movies and actors by name, and filter them by genre and average rating. It offers both a graphical user interface (GUI) and a command-line interface (CLI) version, allowing users flexibility in their interaction.

## Packages
The app is organized into the following packages:

### org.example
#### IMDB
The main class of the app containing the main function and the `loadDatabase()` function for loading the database from a JSON file.
#### Actor, Movie, Series, Production, Rating, User, Admin, Contributor, Regular, Credentials, Episode, Staff, Request, RequestHolder
Classes defining various entities and functionalities in the application.

### org.example classes, Enums, and Interfaces
#### AddProductStrategy, AddReviewStrategy, CreateIssueStrategy, UserExperienceContext, UserFactory
Classes related to the strategy design pattern for adding products, reviews, and creating issues, as well as a factory design pattern for creating users.
#### Genre, AccountType, RequestType
Enums representing genres, account types, and request types.
#### ExperienceStrategy, Observer, Subject, RequestManager, StaffInterface
Interfaces defining the strategy design pattern for user experience, observer design pattern, and other interfaces used in the app.

### org.constants 
#### ApplicationFlow, Constants, FunctionFactory, GuiConstants, OperationFactory, OutPutConstants, ReadInput, WriteOutput
Classes containing constants, application flow, and functions for both the CLI and GUI versions.

### org.gui
A package containing a single extensive class for the GUI version of the app, designed with clear method names and descriptive variables. Javadoc's comments are provided for method documentation.

### org.helper
#### ActorDeserializer, ProductionDeserializer
Classes with custom deserializers for actors and movies/series to handle specific fields during deserialization.

## Database
The app employs a JSON database to store necessary data, loaded by the `loadDatabase()` function using the ObjectMapper class from the Jackson library. Two custom deserializers, ActorDeserializer and ProductionDeserializer, assist in deserializing specific fields.

## Design Patterns
### Strategy Design Pattern
Implemented to enhance user experience by offering different strategies for adding products, reviews, and creating issues. The UserExperienceContext class sets the strategy, and the ExperienceStrategy interface is used for strategy implementation.
### Factory Design Pattern
Used for creating users, with the UserFactory class responsible for creating Admin, Contributor, and Regular users.
### Observer Design Pattern
Utilized for sending notifications to users, where the Observer interface implements the observer and the Subject interface implements the subject.
### Singleton Design Pattern
Applied in the IMDB class to ensure only one instance of the class can be created.

## Dependencies
To achieve the functionalities and features, the IMDB Clone App relies on the following crucial dependencies:

- **[json-simple (version 1.1.1)](https://github.com/fangyidong/json-simple):**
    - A lightweight Java library for JSON processing, enabling efficient handling of JSON data within the application.

- **[lombok (version 1.18.30)](https://projectlombok.org/):**
  The app leverages the Lombok library (version 1.18.30) to eliminate repetitive calls to getter and setter methods. Lombok generates these methods at compile time, simplifying code and enhancing readability.
These dependencies play a pivotal role in the app's operations, allowing efficient JSON data management and code simplification through Lombok.
