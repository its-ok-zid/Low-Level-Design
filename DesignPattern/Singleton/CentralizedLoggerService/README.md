# Low-Level Design (LLD): Centralized Enterprise Logger Service (Singleton Pattern)

## 📌 Problem Overview
In high-throughput distributed systems, multiple threads produce telemetry, operational, and diagnostic logs concurrently. A shared **Centralized Logger Service** prevents resource exhaustion, interleaving corruption, and thread contention.

The objective is to implement a robust, production-grade **Singleton Design Pattern** leveraging **Double-Checked Locking** with `volatile`, guarded against reflection attacks, and backed by lock-free concurrent storage.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Log Levels Support:** `INFO`, `WARN`, `ERROR` via `LogLevel` enum.
* **LogRecord Model:** Captures immutable audit metadata (timestamp, thread name, log level, message) with formatted output.
* **Logger Container (Singleton):**
  * Ensures a single instance per JVM lifecycle using lazy double-checked locking.
  * Captures entries into an in-memory `ConcurrentLinkedQueue<LogRecord>`.
  * Exposes `log(LogLevel, String)`, `getCount()`, and `displayLogHistory()`.
* **Testing Driver:**
  * Validates reference equality (`logger1 == logger2`).
  * Simulates concurrent background worker threads logging simultaneously.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands?* | Log output targets can vary, but the logger instance structure is singular. |
| **2. Data Holders & Containers** | *What objects store state?* | `LogRecord` (immutable entry), `Logger` (singleton container with concurrent queue). |
| **3. Abstract Class Check** | *Is an abstract layer needed?* | **No.** Singleton encapsulates instance lifecycle over a concrete class. |
| **4. Orchestrator** | *What coordinates testing?* | `LoggerTester` executes concurrent worker threads to verify thread-safety and shared memory identity. |

---

## 📐 Class Architecture (UML Diagram)

```text
+-------------------------------------------------------------------------+
|                                 Logger                                  |
+-------------------------------------------------------------------------+
| - instance: Logger {static, volatile}                                   |
| - logHistory: ConcurrentLinkedQueue<LogRecord>                          |
+-------------------------------------------------------------------------+
| - Logger()                                                              |
| + getInstance(): Logger {static}                                        |
| + log(logLevel: LogLevel, message: String): void                        |
| + displayLogHistory(): void                                             |
| + getCount(): int                                                       |
+-------------------------------------------------------------------------+
                                     1
                                     ◇
                                     |
                                     | 0..*
                                     v
+-------------------------------------------------------------------------+
|                               LogRecord                                 |
+-------------------------------------------------------------------------+
| - timestamp: LocalDateTime                                              |
| - threadName: String                                                    |
| - logLevel: LogLevel                                                    |
| - message: String                                                       |
+-------------------------------------------------------------------------+
| + getTimestamp(): LocalDateTime                                         |
| + getThreadName(): String                                               |
| + getLogLevel(): LogLevel                                               |
| + getMessage(): String                                                  |
| + toString(): String                                                    |
+-------------------------------------------------------------------------+
         * |
           | references
           v 1
+-------------------------------------------------------------------------+
|                              <<enumeration>>                            |
|                                 LogLevel                                |
+-------------------------------------------------------------------------+
| INFO                                                                    |
| WARN                                                                    |
| ERROR                                                                   |
+-------------------------------------------------------------------------+