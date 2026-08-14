# Low-Level Design (LLD): Hotel Room Booking System

## 📌 Problem Overview
The **Hotel Room Booking System** handles room availability, reservations, and billing for various room types (e.g., Standard Rooms, Suite Rooms).

The objective is to combine an **Interface** with an **Abstract Base Class** to reuse shared booking state transitions (`bookRoom`, `cancelBooking`) while enabling polymorphic cost calculations.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Room Types (V1):**
  * `StandardRoom`: Stores room number, base nightly rate, booking status, and bed count.
  * `SuiteRoom`: Stores room number, base nightly rate, booking status, and jacuzzi flag.
* **Core Operations:**
  * `bookRoom()`: Marks an available room as booked.
  * `cancelBooking()`: Marks a booked room as available.
  * `calculateTotalBill(int nights)`: Calculates base rate plus room-specific surcharges.
* **Orchestrator:**
  * `BookingService`: Manages room inventories and processes reservations polymorphically.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes in future sprints?* | **Room Types** expand $\rightarrow$ **1 Interface**: `Room`. |
| **2. Data Holders & Containers** | *What objects store state?* | Models: `StandardRoom`, `SuiteRoom`. |
| **3. Abstract Class Check** | *Do models share state AND logic?* | **Yes.** Shared `roomNumber`, `baseNightlyRate`, `isBooked`, and identical reservation logic $\rightarrow$ `AbstractRoom`. |
| **4. Orchestrator** | *What glues components together?* | `BookingService` processes bookings polymorphically. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-----------------------------------+
               |           <<interface>>           |
               |               Room                |
               +-----------------------------------+
               | + bookRoom(): boolean             |
               | + cancelBooking(): boolean        |
               | + calculateTotalBill(...): double |
               +-----------------------------------+
                                 ^
                                 | (Realization)
               +-----------------------------------+
               |           AbstractRoom            |
               +-----------------------------------+
               | - roomNumber: String              |
               | - baseNightlyRate: double         |
               | - isBooked: boolean               |
               +-----------------------------------+
               | + bookRoom(): boolean             |
               | + cancelBooking(): boolean        |
               +-----------------------------------+
                                 ^
                                 | (Inheritance)
        +------------------------+------------------------+
        |                                                 |
+-----------------------------------+   +-----------------------------------+
|           StandardRoom            |   |             SuiteRoom             |
+-----------------------------------+   +-----------------------------------+
| - numberOfBeds: int               |   | - hasJacuzzi: boolean             |
+-----------------------------------+   +-----------------------------------+
| + calculateTotalBill(...): double |   | + calculateTotalBill(...): double |
+-----------------------------------+   +-----------------------------------+

                                 ^
                                 | (Uses / Depends on via DIP)
               +-----------------------------------+
               |          BookingService           |
               +-----------------------------------+
               | + processBooking(room: Room,      |
               |                  nights: int)     |
               +-----------------------------------+