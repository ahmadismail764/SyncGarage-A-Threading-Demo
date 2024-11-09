# Multithreaded Parking System Simulation

This project simulates a parking system using multithreading and semaphores to control access to a limited number of parking spots. Cars arrive at different gates with varying arrival times and parking durations, managed through synchronized threads.

## Project Overview

The parking lot in this simulation has **4 spots** and **3 gates**. Cars arrive at specific times, attempt to park, stay for a defined duration, and then exit. The program coordinates the parking spots among arriving cars, ensuring proper synchronization and accurate status reporting of the lot's occupancy and car activity.

### Objectives

- **Thread Synchronization**: Use semaphores to manage parking spot access.
- **Concurrency Management**: Handle concurrent arrivals and departures efficiently.
- **Realistic Simulation**: Simulate car arrivals, parking, and departure timing.
- **Status Reporting**: Track the number of cars currently parked and report the total served at each gate.

### System Specifications

- **Parking Spots**: Total of 4 spots available.
- **Gates**: 3 gates (Gate 1, Gate 2, Gate 3).
- **Car Arrival**: Cars arrive at each gate per a predefined schedule.

### Implementation Details

- **Arrival Times**: Use `Thread.sleep()` to simulate car arrival times.
- **Parking Duration**: Each car parks for a specified time using `Thread.sleep()`.
- **Concurrency Control**: Manage spot allocation with a semaphore to prevent race conditions.
- **Input Format**: Read car data from a text file with entries specifying the gate, car ID, arrival time, and parking duration.

## Classes and Methods

### `Gate` Class
Represents each gate in the parking lot. Each gate manages its car queue and parking spot allocation.
- **Attributes**:
  - `gateName`: Identifier for the gate (e.g., "Gate 1").
  - `cars`: List of `Car` objects queued at the gate.
  - `parkingSlots`: Semaphore controlling parking spot access.
- **Methods**:
  - `startCarThreads()`: Initializes car threads for the gate.
  - `joinAllThread()`: Joins threads after completion.

### `Car` Class
Each car is represented by a thread that simulates arrival, waiting, parking, and departure.
- **Attributes**:
  - `carId`, `arrivalTime`, `parkDuration`: Car details for simulation.
  - `parkingSlots`: Semaphore to manage parking access.
- **Method**:
  - `run()`: Main logic for car activity, including waiting for parking spots, parking, and leaving.

### `Main` Class
Entry point for the simulation. It:
- Reads input from a file to initialize cars and gates.
- Calls methods to start and manage car threads.
