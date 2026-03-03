# Air Traffic Control System

An object-oriented Java ATC system with generic runways, priority-based flight scheduling, and custom exceptions for takeoff and landing management, built using Gradle.

---

## Academic Transparency & AI Disclosure

This repository contains a personal study project based on a university assignment.

**Status:** Developed after the academic grading period concluded. Not submitted for university credit during the active course.

**AI Usage:** This implementation was entirely AI-generated. The development process involved studying each class individually to understand the underlying logic and design patterns, treating the AI output as a learning resource to explore Java Generics, Collections, and Exception Handling in context.

**Academic Integrity:** This serves as a personal portfolio project demonstrating understanding of Java OOP principles through guided AI-assisted learning.

---

## Classes

- **Airplane (abstract):** Base class modelling flight characteristics — flight ID, model, source, destination, status, and desired/actual times.
- **WideBodyAirplane & NarrowBodyAirplane:** Subclasses extending `Airplane` to represent specific aircraft types based on dimensions.
- **Runway (generic):** Manages takeoff and landing queues using generics to ensure type safety.
- **Main:** Central class interpreting commands from the input file and managing application flow.
- **PlaneStatus (enum):** Defines possible aircraft states: `WAITING_FOR_TAKEOFF`, `DEPARTED`, `WAITING_FOR_LANDING`, and `LANDED`.
- **IncorrectRunwayException:** Thrown when an airplane is allocated to an incompatible runway type.
- **UnavailableRunwayException:** Thrown for maneuver requests on runways that are currently occupied.

---

## Features

### Data Structures

- **PriorityQueue\<T\>:** Used inside `Runway` to maintain priority order for takeoffs and landings efficiently.
- **HashMap\<String, Airplane\>:** Stores all flights for O(1) lookups during `flight_info` queries.
- **LinkedHashMap\<String, Runway\<?\>\>:** Stores runways by ID, preserving insertion order while allowing O(1) access.

### Generics & OOP

- **Generics:** `Runway<T extends Airplane>` ensures a runway only accepts the specific airplane type it was configured for.
- **Abstraction:** `Airplane` is abstract — only concrete implementations (Wide or Narrow body) are instantiated.
- **Inheritance:** Base functionality extended into specialized subclasses.
- **Polymorphism:** `toString()` overridden in subclasses to display body-type prefixes dynamically.
- **Encapsulation:** Fields are private/protected and accessed through defined methods.

### Priority Logic

- **Takeoff:** Priority is strictly chronological based on desired takeoff time
- **Landing:** Priority is determined first by urgency (if specified), then by desired landing time
- **Occupation Time:** Runway becomes unavailable for 5 minutes after a takeoff or 10 minutes after a landing

### I/O

- **Input:** Commands read line by line from `input.in`.
- **Output:** Results written to `runway_info`, `flight_info`, and `board_exceptions`.

---

## Running the Project

This project uses **Gradle** for dependency management and build automation. The Gradle Wrapper is included — no manual installation needed.

```bash
./gradlew clean test        # Linux / macOS
gradlew.bat clean test      # Windows
```

Detailed test results are available at `build/reports/tests/test/index.html`.

---

## Development Notes

This is a 2nd-year OOP course assignment, re-implemented after the grading period for self-study purposes, with entirely AI-generated code used as a structured learning resource.

---

## License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
