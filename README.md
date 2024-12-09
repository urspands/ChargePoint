# ChargePoint Scheduler

## Overview

This project is an Android application that helps schedule charging sessions for electric vehicles.
It lists a schedule showing which trucks should be charged on each charger using the "First Come,
First Serve" scheduling algorithm.
The SchedulerService interface is injected into the repository which can be easily replaced with
other scheduling algorithms or network service. The SchedulerService implementation sorts the list
for truck based on their arrival time and assigns the charger based on total time needed to charge
it with a charger and the time remaining in that charger.

## Limitations

The app doesn't provide an user interface to add the list of chargers and vehicles. The list of
chargers and vehicles are hardcoded in the HomeViewModel for demo purpose.

## Architecture

The project follows the Model-View-ViewModel (MVVM) architectural pattern, with a focus on clean
code principles and testability.

* **Data Layer:** Responsible for handling data persistence and network communication. It includes
  repositories, data sources, and data models.
* **Domain Layer:** Contains the business logic of the application, including use cases and domain
  models.
* **ViewModel Layer:** Responsible for holding the state of the UI(Activities, Fragments or
  Composable) and handling user interactions through a interface object.
* **UI layer: ** Responsible for displaying(with Jetpack Compose) the data collected from the
  ViewModel.

## Technologies Used

* **Kotlin:** The primary programming language for the project.
* **Jetpack Compose:** Used for building the user interface.
* **Coroutines:** Used for handling asynchronous operations and managing background tasks.
* **Flow:** Used for reactive data streams and handling UI updates.
* **Hilt:** Used for dependency injection.
* **MockK:** Used for mocking dependencies in unit tests.