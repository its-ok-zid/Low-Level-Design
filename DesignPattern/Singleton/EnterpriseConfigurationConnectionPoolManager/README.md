# Low-Level Design (LLD): Enterprise Configuration & Connection Manager (Singleton Pattern)

## 📌 Problem Overview
In distributed, multithreaded enterprise architectures, multiple background threads need concurrent access to a unified **Configuration & Connection Pool Manager**. 

Creating multiple instances leads to conflicting configurations, resource exhaustion, and memory leaks. The objective is to implement a robust, thread-safe **Singleton Design Pattern** leveraging **Double-Checked Locking** with the `volatile` modifier.

---
## 🏢 Company Context
**Company:** Google Infrastructure / AWS Core Services / Oracle Cloud  
**Domain:** Distributed Configuration & Connection Pool Orchestration  
**Scenario:** Enterprise applications require centralized management of database handles, connection limits, and runtime flags. Creating multiple configuration loaders exhausts pooled database sockets and leaves services with inconsistent runtime states.
---
## 🎯 Requirements

### 1. Functional Requirements
* **Key-Value Configuration Store:**
  * Stores in-memory key-value configuration properties (`app.env`, `db.url`, etc.).
  * Exposes thread-safe read/write operations (`setConfiguration`, `getConfiguration`, `getConfigurationOrDefault`).
* **Singleton Concurrency Guarantees:**
  * Ensures exactly one instance exists across the entire JVM lifecycle.
  * Employs lazy initialization: the instance is created only upon first request.
  * Thread-safe double-checked locking prevents race conditions under high concurrent load.
* **Verification Driver:**
  * Simulates concurrent threads accessing and mutating configuration state while proving reference equality (`instance1 == instance2`).

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands?* | None for the Singleton structure itself; key-value configurations vary dynamically inside a thread-safe map. |
| **2. Data Holders & Containers** | *What objects store state?* | `ConfigurationManager` holds the static volatile instance and the concurrent configuration map. |
| **3. Abstract Class Check** | *Is an abstract layer needed?* | **No.** Singleton controls instance lifecycle of a concrete class directly. |
| **4. Orchestrator** | *What class coordinates and tests the pattern?* | `ConfigurationTester` executes concurrent thread tasks to confirm thread safety and single-instance integrity. |

---

## 📐 Class Architecture (UML Diagram)

```text
+-------------------------------------------------------------+
|                    ConfigurationManager                     |
+-------------------------------------------------------------+
| - instance: ConfigurationManager {static, volatile}         |
| - configurations: ConcurrentHashMap<String, String>         |
+-------------------------------------------------------------+
| - ConfigurationManager()                                    |
| + getInstance(): ConfigurationManager {static}              |
| + setConfiguration(key: String, value: String): void        |
| + getConfiguration(key: String): String                     |
| + getConfiguration(key: String, defVal: String): String     |
| + displayConfiguration(): void                              |
+-------------------------------------------------------------+
                               ^
                               | (Accesses single instance)
+-------------------------------------------------------------+
|                    ConfigurationTester                      |
+-------------------------------------------------------------+
| + main(args: String[]): void                                |
+-------------------------------------------------------------+