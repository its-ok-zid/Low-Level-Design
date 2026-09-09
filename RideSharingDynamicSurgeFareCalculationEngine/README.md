# Low-Level Design (LLD): Ride-Sharing Dynamic Surge & Fare Calculation Engine

## 📌 Problem Overview
The **Dynamic Surge & Fare Calculation Engine** processes trip fares for an on-demand ride-sharing platform (similar to Uber/Lyft). Fares are computed dynamically based on distance, duration, and contextual surge pricing strategies (e.g., Standard, Peak Surge Multipliers, Late-Night Flat Surcharges).

The primary objective is to apply the **Strategy Pattern** under SOLID principles, decoupling the trip model and billing orchestrator from specific tariff calculation algorithms.

---
## 🏢 Company Context
**Company:** Uber / Lyft / Grab Core Mobility Services  
**Domain:** Real-Time Dispatch & Dynamic Pricing Infrastructure  
**Scenario:** Dynamic demand, surge factors, weather alerts, and vehicle tier tiers (Economy, XL, Premier) dictate trip fares. Pricing algorithms must adapt continuously without affecting dispatch lifecycles or driver settlement engines.
---

## 🎯 Requirements

### 1. Functional Requirements
* **Trip Container:**
  * `Trip`: Stores trip ID, rider name, distance (km), duration (minutes), completion status, and assigned `PricingStrategy`.
  * Computes total fare by delegating to the assigned strategy.
  * Allows updating the strategy at runtime.
* **Pricing Strategies (Axis of Variation #1):**
  * `StandardPricing`: $3.00 base + ($1.20 / km) + ($0.25 / min).
  * `PeakSurgePricing`: (Standard Fare) $\times$ `surgeMultiplier`.
  * `LateNightPricing`: (Standard Fare) + $5.00 flat surcharge.
* **Orchestrator Service:**
  * `BillingService`: Processes trip completion and prints a formatted fare receipt showing trip specs, strategy name, status, and calculated fare.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands in future sprints?* | **Pricing Algorithms** will expand $\rightarrow$ Creates **1 Interface**: `PricingStrategy`. |
| **2. Data Holders & Containers** | *What objects store state?* | Model/Container: `Trip` (domain aggregate). |
| **3. Abstract Class Check** | *Do strategies share identical state AND logic?* | **No.** Surge multipliers, flat fees, and standard rates have differing parameters $\rightarrow$ Pure Interface. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `BillingService` prints receipts polymorphically via `Trip` and `PricingStrategy`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------+
               |                        <<interface>>                        |
               |                       PricingStrategy                       |
               +-------------------------------------------------------------+
               | + calculateFare(dist: double, time: double): double         |
               | + getStrategyName(): String                                 |
               +-------------------------------------------------------------+
                                              ^
        +-------------------------------------+-------------------------------------+
        |                                     |                                     |
+-----------------------------+ +-----------------------------+ +-----------------------------+
|       StandardPricing       | |       PeakSurgePricing      | |      LateNightPricing       |
+-----------------------------+ +-----------------------------+ +-----------------------------+
|                             | | - surgeMultiplier: double   | |                             |
+-----------------------------+ +-----------------------------+ +-----------------------------+
| + calculateFare(...): double| | + calculateFare(...): double| | + calculateFare(...): double|
| + getStrategyName(): String | | + getStrategyName(): String | | + getStrategyName(): String |
+-----------------------------+ +-----------------------------+ +-----------------------------+

                                                +-----------------------------------------------+
                                                |                     Trip                      |
                                                +-----------------------------------------------+
                                                | - tripId: String                              |
                                                | - riderName: String                           |
                                                | - distanceInKm: double                        |
                                                | - durationInMinutes: double                   |
                                                | - isCompleted: boolean                        |
                                                | - pricingStrategy: PricingStrategy            |
                                                +-----------------------------------------------+
                                                | + setPricingStrategy(s: PricingStrategy): void|
                                                | + completeTrip(): void                        |
                                                | + calculateFare(): double                     |
                                                +-----------------------------------------------+
                                                                        ^
                                                                        | (Processes)
                                                +-----------------------------------------------+
                                                |                BillingService                 |
                                                +-----------------------------------------------+
                                                | + processTripFare(trip: Trip): void           |
                                                +-----------------------------------------------+