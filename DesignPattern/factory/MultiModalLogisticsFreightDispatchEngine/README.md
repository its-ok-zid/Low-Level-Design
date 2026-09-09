# Low-Level Design (LLD): Multi-Modal Logistics & Freight Dispatch Engine (Factory Method Pattern)

## 📌 Problem Overview
Global logistics and freight forwarding networks orchestrate shipments across varying transportation modalities: **Road Truck**, **Air Cargo**, and **Sea Container**. Each transportation carrier enforces independent navigation constraints, customs checks, fuel burn rates, and payload weight limits.

Direct instantiation of transport vehicles inside routing or dispatch orchestrators hardcodes dependencies and prevents onboarding new carriers (such as Rail Freight or Drone Hubs). We apply the **Factory Method Pattern** to establish parallel product and creator hierarchies.

## 🏢 Company Context
**Company:** Amazon Transportation / Flexport / FedEx Core Freight Infrastructure  
**Domain:** Global Multi-Modal Supply Chain & Carrier Orchestration  
**Scenario:** A centralized freight manifest orchestrator routes millions of consignments worldwide across land, sea, and air carriers. Concrete carrier SDKs and vehicle-specific initialization logic must remain decoupled from the routing pipeline to allow dynamic onboarding of regional carriers without code rewrites.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Contract (`Transport`):**
  * `void planRoute(String origin, String destination)`: Resolves logistical transit corridor.
  * `boolean dispatch(String shipmentId, double weightInKg)`: Validates payload and confirms departure.
* **Concrete Carriers:**
  * `RoadTruckTransport`: Maximum weight capacity = **20,000 kg**.
  * `AirCargoTransport`: Maximum weight capacity = **50,000 kg**.
  * `SeaContainerTransport`: Maximum weight capacity = **500,000 kg**.
* **Creator Contract (`LogisticPlanner`):**
  * `Transport createTransport()`: Abstract factory method.
* **Concrete Planners:**
  * `RoadLogisticPlanner`, `AirLogisticPlanner`, `SeaLogisticPlanner`.
* **Orchestrator (`LogisticDispatchService`):**
  * Depends strictly on `LogisticPlanner`, instantiates `Transport`, plans the corridor route, and validates dispatch.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Transportation vehicles and their dispatch mechanics $\rightarrow$ Parallel Product and Creator hierarchies. |
| **2. Data Holders & Containers** | *What objects store state?* | Manifest payload attributes (`shipmentId`, `origin`, `destination`, `weightInKg`). |
| **3. Abstract Class Check** | *Do factories or products share mutable state?* | **No.** Route calculation formulas and vehicle types differ entirely $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `LogisticDispatchService` orchestrates execution via the abstract `LogisticPlanner`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                                Transport                                |
               +-------------------------------------------------------------------------+
               | + planRoute(origin: String, destination: String): void                  |
               | + dispatch(shipmentId: String, weightInKg: double): boolean             |
               +-------------------------------------------------------------------------+
                                 △                               △
                                 :                               :
                 ................:                               :................
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|        RoadTruckTransport         |                           |       SeaContainerTransport       |
+-----------------------------------+                           +-----------------------------------+
| + planRoute(...): void            |                           | + planRoute(...): void            |
| + dispatch(...): boolean          |                           | + dispatch(...): boolean          |
+-----------------------------------+                           +-----------------------------------+
                 △                                                               △
                 :                                                               :
                 : <<instantiates>>                                              : <<instantiates>>
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|        RoadLogisticPlanner        |                           |        SeaLogisticPlanner         |
+-----------------------------------+                           +-----------------------------------+
| + createTransport(): Transport    |                           | + createTransport(): Transport    |
+-----------------------------------+                           +-----------------------------------+
                 :                                                               :
                 :................                               ................:
                                 :                               :
                                 △                               △
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                             LogisticPlanner                             |
               +-------------------------------------------------------------------------+
               | + createTransport(): Transport                                          |
               +-------------------------------------------------------------------------+
                                                     △
                                                     :
                                                     : <<uses / depends on>>
                                                     :
               +-------------------------------------------------------------------------+
               |                         LogisticDispatchService                         |
               +-------------------------------------------------------------------------+
               | + executeRoute(planner: LogisticPlanner, shipmentId: String,            |
               |                origin: String, destination: String,                     |
               |                weightInKg: double): void                                |
               +-------------------------------------------------------------------------+