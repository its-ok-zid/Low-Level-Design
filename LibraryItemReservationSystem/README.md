# Low-Level Design (LLD): Library Item Reservation System

## 📌 Problem Overview
The **Library Item Reservation System** manages media item availability, reservations, returns, and overdue penalty fee calculations across multiple catalog formats (e.g., Physical Books, Audiobooks).

The objective is to combine an **Interface** with an **Abstract Base Class** to reuse shared reservation state lifecycles (`reserve`, `returnItem`) while allowing polymorphic late fee calculations.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Library Media Items (V1):**
  * `Book`: Stores item ID, title, reservation status, and page count.
  * `AudioBook`: Stores item ID, title, reservation status, and duration in minutes.
* **Core Operations:**
  * `reserve()`: Marks an available item as reserved.
  * `returnItem()`: Marks a reserved item as available.
  * `calculateLateFee(int daysOverdue)`: Calculates overdue fees plus media-specific surcharges.
* **Orchestrator:**
  * `LibraryService`: Manages catalog collections and processes reservations/returns polymorphically.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes in future sprints?* | **Media Types** expand $\rightarrow$ **1 Interface**: `MediaItem`. |
| **2. Data Holders & Containers** | *What objects store state?* | Models: `Book`, `AudioBook`. |
| **3. Abstract Class Check** | *Do models share state AND logic?* | **Yes.** Shared `itemId`, `title`, `isReserved`, and identical reservation lifecycle $\rightarrow$ `AbstractBook`. |
| **4. Orchestrator** | *What glues components together?* | `LibraryService` processes reservations and returns polymorphically. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-----------------------------------+
               |           <<interface>>           |
               |             MediaItem             |
               +-----------------------------------+
               | + reserve(): boolean              |
               | + returnItem(): boolean           |
               | + calculateLateFee(days): double  |
               +-----------------------------------+
                                 ^
                                 | (Realization)
               +-----------------------------------+
               |           AbstractBook            |
               +-----------------------------------+
               | - itemId: String                  |
               | - title: String                   |
               | - isReserved: boolean             |
               +-----------------------------------+
               | + reserve(): boolean              |
               | + returnItem(): boolean           |
               +-----------------------------------+
                                 ^
                                 | (Inheritance)
        +------------------------+------------------------+
        |                                                 |
+-----------------------------------+   +-----------------------------------+
|               Book                |   |             AudioBook             |
+-----------------------------------+   +-----------------------------------+
| - pageCount: int                  |   | - durationInMinutes: double       |
+-----------------------------------+   +-----------------------------------+
| + calculateLateFee(...): double   |   | + calculateLateFee(...): double   |
+-----------------------------------+   +-----------------------------------+

                                 ^
                                 | (Uses / Depends on via DIP)
               +-----------------------------------+
               |          LibraryService           |
               +-----------------------------------+
               | + processReservation(item: ...)   |
               | + processReturn(item, days): void |
               +-----------------------------------+