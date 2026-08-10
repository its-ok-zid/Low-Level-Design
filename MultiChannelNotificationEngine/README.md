# Low-Level Design (LLD): Multi-Channel Notification Engine

## 📌 Problem Overview
The **Multi-Channel Notification Engine** is a core infrastructure component responsible for dispatching alerts across distinct communication channels (e.g., Email, SMS, Push Notifications).

The primary objective of this exercise is to handle **varying payload requirements per channel** (e.g., Email requiring a subject line while SMS does not) without violating the **Interface Segregation Principle (ISP)** or introducing `if-else` routing inside the orchestrator service.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Notification Channels (V1):**
  * `EmailChannel`: Stores recipient email address, subject line, and body message.
  * `SmsChannel`: Stores recipient phone number and body message (no subject line).
* **Core Action:**
  * Every channel implements `send()`. Calling `send()` formats and dispatches the alert.
* **Orchestrator (`NotificationService`):**
  * Supports single notification dispatch (`sendNotification(Channel channel)`).
  * Supports multi-channel broadcasting (`broadcast(List<Channel> channels)`).
* **Extensibility:**
  * Must allow adding new channels (e.g., `PushChannel`, `WhatsAppChannel`, `SlackChannel`) without modifying `NotificationService` or existing channel classes.

### 2. Non-Functional & Quality Attributes
* **Interface Segregation Principle (ISP):** Avoid fat interfaces; do not force SMS to accept or process subject parameters.
* **Liskov Substitution Principle (LSP):** Every channel implementation can be substituted and invoked uniformly via `channel.send()`.
* **Open/Closed Principle (OCP):** Open for new channel extensions, closed for orchestrator modifications.
* **Dependency Inversion Principle (DIP):** `NotificationService` depends exclusively on the `Channel` interface abstraction.

---

## 🧩 The 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands in future sprints?* | **Notification Channels** will expand $\rightarrow$ Creates **1 Interface**: `Channel`. |
| **2. Data Holders & Containers** | *What objects store data/state?* | Concrete models: `EmailChannel` (holds email, subject, body), `SmsChannel` (holds phone, body). |
| **3. Abstract Class Check** | *Do concrete models share common fields AND code?* | **No.** Email and SMS hold fundamentally different attributes. A pure interface is cleaner. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `NotificationService` delegates execution via `Channel.send()`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +---------------------------+
               |       <<interface>>       |
               |          Channel          |
               +---------------------------+
               | + send(): void            |
               +---------------------------+
                             ^
                             | (Realization)
        +--------------------+--------------------+
        |                                         |
+-----------------------------------+   +-----------------------------------+
|           EmailChannel            |   |            SmsChannel             |
+-----------------------------------+   +-----------------------------------+
| - email: String                   |   | - phoneNumber: String             |
| - subject: String                 |   | - body: String                    |
| - body: String                    |   +-----------------------------------+
+-----------------------------------+   | + send(): void                    |
| + send(): void                    |   +-----------------------------------+
+-----------------------------------+

                             ^
                             | (Uses / Depends on via DIP)
               +---------------------------+
               |    NotificationService    |
               +---------------------------+
               | + sendNotification(       |
               |     channel: Channel):void|
               | + broadcast(              |
               |     channels: List<...>)  |
               +---------------------------+