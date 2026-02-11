# Air Traffic Control System

> [!IMPORTANT]
> This is the second project assignment for the Object-Oriented Programming (OOP) course (2nd year, 1st semester).
- This implementation was developed as a **personal re-implementation** for self-educational purposes after the official academic evaluation period.
- The project was developed entirely with **AI-generated code** to explore advanced Java patterns and optimization.

## ⚠️ Academic Transparency & AI Disclosure

This repository contains a personal study project based on a university assignment.

- **Status:** This code was developed **after the academic grading period** ended. It was not submitted for university credit during the active course.
- **AI Usage:** This implementation was **entirely AI-generated**. The development process involved:
  - Using AI to generate complete implementations for each class
  - Studying each class individually to understand the underlying logic and design patterns
  - Learning Java concepts (Generics, Collections, Exception Handling) through the generated code
  - Iteratively refining the implementation with AI assistance to fix bugs and improve structure
- **Learning Approach:** While the code itself is AI-generated, the educational value came from analyzing and understanding each component class by class, treating the AI output as a learning resource rather than original work.
- **Academic Integrity:** This serves as a personal portfolio project demonstrating understanding of Java OOP principles through guided AI-assisted learning.

---

## Classes

The following classes were implemented for this project:

- **Airplane (abstract):** The base class modeling flight characteristics such as flight ID, model, source, destination, status, and desired/actual times.
- **WideBodyAirplane & NarrowBodyAirplane:** Subclasses extending `Airplane` to represent specific aircraft types based on dimensions.
- **Runway (generic):** Manages takeoff and landing queues using generics to ensure type safety.
- **Main:** The central class that interprets commands from the input file and manages the application flow.
- **PlaneStatus (enum):** Defines possible aircraft states: `WAITING_FOR_TAKEOFF`, `DEPARTED`, `WAITING_FOR_LANDING`, and `LANDED`.
- **IncorrectRunwayException:** A custom exception thrown when an airplane is allocated to an incompatible runway type (e.g., landing plane on a takeoff runway).
- **UnavailableRunwayException:** A custom exception thrown for maneuver requests on runways that are currently occupied.

---

## Features

### Data Structures

- **PriorityQueue\<T\>:** Utilized inside the `Runway` class to maintain the priority order for takeoffs and landings efficiently.
- **HashMap\<String, Airplane\>:** Used to store all flights (`allFlights`) for fast O(1) lookups during `flight_info` queries.
- **LinkedHashMap\<String, Runway\<?\>\>:** Stores runways by ID, maintaining the order in which they were added while allowing O(1) access.

### Classes and Generics

- **Generics:** Implemented in the `Runway<T extends Airplane>` class to ensure that a runway only accepts the specific airplane type (WideBody or NarrowBody) it was configured for.
- **Abstraction:** `Airplane` is an **abstract** class because generic airplane instances are not created directly; only concrete implementations (Wide or Narrow body) are used.

### Priority Logic

- **Takeoff:** Priority is strictly chronological based on the desired takeoff time.
- **Landing:** Priority is determined first by **urgency** (if specified) and then by the desired landing time.
- **Occupation Time:** After a maneuver, the runway becomes unavailable for **5 minutes** for takeoffs or **10 minutes** for landings.

### I/O and Windows Compatibility

- **Reading:** Commands are read line by line from the `input.in` file.
- **Writing:** Results are written to specific output files: `runway_info`, `flight_info`, and `board_exceptions`.
- **Compatibility:** Implemented character replacement (replacing `:` with `-`) in `runway_info` filenames to ensure compatibility with Windows systems.

### OOP Principles

- **Encapsulation:** Class fields are protected or private and accessed through specific methods to maintain data integrity.
- **Abstraction:** Implemented via the abstract `Airplane` class to define a common template for all aircraft.
- **Inheritance:** Extends base functionalities into specialized airplane subclasses (Wide/Narrow body).
- **Polymorphism:** Overrode the `toString()` method in subclasses to display specific aircraft body-type prefixes dynamically.

---

## Running the Project

This project uses **Gradle** for dependency management and build automation.

> [!NOTE]
> You do not need to install Gradle manually, as the project includes the Gradle Wrapper (`gradlew`).

The project includes a `settings.gradle` file for build configuration.

### Running the Tests

> [!TIP]
> **To run the tests:**
- Open a terminal in the project root directory.
- Execute: `./gradlew clean test` (Windows users: `gradlew.bat clean test`)
- Detailed results can be viewed at `build/reports/tests/test/index.html`

---

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
