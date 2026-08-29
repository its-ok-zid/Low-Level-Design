# Low-Level Design (LLD): Logistics & Dynamic Shipping Fee Engine

## 📌 Problem Overview
The **Dynamic Shipping Fee Engine** manages physical package shipments and computes delivery fees based on dynamic transport and handling strategies (e.g., Standard Ground, Express Air, High-Value Insured).

The objective is to implement the **Strategy Pattern** under SOLID principles, decoupling the shipment container and dispatch service from transport fee calculation formulas.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Line Items & Shipment Container:**
  * `Package`: Stores description, weight in kg, and declared monetary value.
  * `Shipment`: Aggregates packages, tracks destination and tracking code, maintains an assigned `ShippingStrategy`, and calculates total weight, value, and fee.
* **Shipping Fee Strategies (Axis of Variation #1):**
  * `StandardGroundShipping`: $5.00 base + $2.50 / kg.
  * `ExpressAirShipping`: $15.00 base + $4.00 / kg + $10.00 flat fee.
  * `HighValueInsuredShipping`: $20.00 base + $3.00 / kg + 1% declared value.
* **Orchestrator Service:**
  * `DispatchService`: Processes shipments and prints a complete dispatch manifest polymorphically.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands in future sprints?* | **Shipping Fee Schemes** will expand $\rightarrow$ Creates **1 Interface**: `ShippingStrategy`. |
| **2. Data Holders & Containers** | *What objects store state?* | Models: `Package` (line item), `Shipment` (aggregate root container). |
| **3. Abstract Class Check** | *Do strategies share identical state AND logic?* | **No.** Fee formulas and parameters differ without shared state $\rightarrow$ Pure Interface. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `DispatchService` outputs manifests polymorphically via `Shipment` and `ShippingStrategy`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------+
               |                        <<interface>>                        |
               |                       ShippingStrategy                      |
               +-------------------------------------------------------------+
               | + calculateShippingFee(weight: double, value: double): double|
               | + getStrategyName(): String                                 |
               +-------------------------------------------------------------+
                                              ^
        +-------------------------------------+-------------------------------------+
        |                                     |                                     |
+-----------------------------+ +-----------------------------+ +-----------------------------+
|    StandardGroundShipping   | |      ExpressAirShipping     | |  HighValueInsuredShipping   |
+-----------------------------+ +-----------------------------+ +-----------------------------+
| + calculateShippingFee(...):| | + calculateShippingFee(...):| | + calculateShippingFee(...):|
|   double                    | |   double                    | |   double                    |
| + getStrategyName(): String | | + getStrategyName(): String | | + getStrategyName(): String |
+-----------------------------+ +-----------------------------+ +-----------------------------+

+-----------------------------+               +-----------------------------------------------+
|           Package           |               |                   Shipment                    |
+-----------------------------+               +-----------------------------------------------+
| - description: String       |               | - trackingCode: String                        |
| - weightInKg: double        | 1           * | - destinationAddress: String                  |
| - declaredValue: double     |<--------------| - packages: List<Package>                     |
+-----------------------------+               | - shippingStrategy: ShippingStrategy          |
| + getWeightInKg(): double   |               +-----------------------------------------------+
| + getDeclaredValue(): double|               | + addPackage(pkg: Package): void              |
| + getDescription(): String  |               | + setShippingStrategy(s: ShippingStrategy)    |
+-----------------------------+               | + calculateTotalWeight(): double              |
                                              | + calculateTotalValue(): double               |
                                              | + calculateShippingFee(): double              |
                                              +-----------------------------------------------+
                                                                      ^
                                                                      | (Processes)
                                              +-----------------------------------------------+
                                              |                DispatchService                |
                                              +-----------------------------------------------+
                                              | + processDispatch(shipment: Shipment): void   |
                                              +-----------------------------------------------+