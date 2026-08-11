# Low-Level Design (LLD): Vehicle Rental System

## 📌 Problem Overview
The **Vehicle Rental System** manages rental transactions for multiple vehicle types (e.g., Cars, Bikes) with shared state operations (renting, returning) and distinct pricing calculations.

The primary objective of this exercise is to combine an **Interface** with an **Abstract Base Class** to reuse common state/logic while allowing polymorphic extensions for vehicle-specific rental pricing.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Vehicles (V1):**
  * `Car`: Stores license plate, base daily rate, rental availability status, and seating capacity.
  * `Bike`: Stores license plate, base daily rate, rental availability status, and helmet inclusion flag.
* **Core Operations:**
  * `rent()`: Marks a vehicle as rented if available.
  * `returnVehicle()`: Marks a rented vehicle as available.
  * `calculateRentalCost(int days)`: Calculates cost based on base rate and vehicle-specific surcharges.
* **Orchestrator:**
  * `RentalService`: Processes rentals polymorphically without branching logic based on vehicle types.

---

## 🧩 The 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands in future sprints?* | **Vehicle Types** will expand $\rightarrow$ Creates **1 Interface**: `Vehicle`. |
| **2. Data Holders & Containers** | *What objects store data/state?* | Concrete models: `Car` (seating capacity), `Bike` (helmet option). |
| **3. Abstract Class Check** | *Do models share common fields AND code?* | **Yes.** All vehicles share `licensePlate`, `baseDailyRate`, `isRented`, and identical `rent()` / `returnVehicle()` state transitions $\rightarrow$ Creates `AbstractVehicle`. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `RentalService` manages vehicle collections and delegates processing via `Vehicle`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-----------------------------------+
               |           <<interface>>           |
               |              Vehicle              |
               +-----------------------------------+
               | + calculateRentalCost(days):double|
               | + rent(): boolean                 |
               | + returnVehicle(): boolean        |
               +-----------------------------------+
                                 ^
                                 | (Realization)
               +-----------------------------------+
               |         AbstractVehicle           |
               +-----------------------------------+
               | - licensePlate: String            |
               | - baseDailyRate: double           |
               | - isRented: boolean               |
               +-----------------------------------+
               | + rent(): boolean                 |
               | + returnVehicle(): boolean        |
               +-----------------------------------+
                                 ^
                                 | (Inheritance)
        +------------------------+------------------------+
        |                                                 |
+-----------------------------------+   +-----------------------------------+
|                Car                |   |               Bike                |
+-----------------------------------+   +-----------------------------------+
| - seatingCapacity: int            |   | - includesHelmet: boolean         |
+-----------------------------------+   +-----------------------------------+
| + calculateRentalCost(...): double|   | + calculateRentalCost(...): double|
+-----------------------------------+   +-----------------------------------+

                                 ^
                                 | (Uses / Depends on via DIP)
               +-----------------------------------+
               |           RentalService           |
               +-----------------------------------+
               | + processRental(vehicle: Vehicle, |
               |                  days: int): void |
               +-----------------------------------+