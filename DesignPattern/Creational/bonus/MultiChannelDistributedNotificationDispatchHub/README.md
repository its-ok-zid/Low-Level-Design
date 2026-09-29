# Low-Level Design (LLD): Multi-Channel Distributed Notification Dispatch Hub (Singleton + Factory Method)

## 📌 Problem Overview
An enterprise notification infrastructure ingests and delivers high-volume notifications across multiple client protocols: **SMS (Twilio)**, **Email (SendGrid)**, and **Push (Firebase Cloud Messaging)**. 

To prevent cascading vendor costs and spam floods, the dispatch layer must enforce system-wide idempotency, sliding-window rate limits, and reflection-resilient single-point coordination. Dispatch mechanisms must also remain decoupled from core ingestion using the **Factory Method Pattern**, orchestrated through a **Double-Checked Locking Singleton Hub**.

## 🏢 Company Context
**Company:** Amazon (AWS SNS / Pinpoint) / Uber Communication Platform / Twilio  
**Domain:** Distributed Multi-Channel Notification Ingestion & Delivery  
**Scenario:** Downstream services (Order Fulfillment, Driver Tracking, Fraud Alerting) broadcast messages concurrently. The notification engine must enforce global rate limits and deduplicate requests to prevent duplicate user notifications and vendor quota exhaustion, while remaining decoupled from individual delivery rail SDKs.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Contract (`NotificationChannel`):**
  * `boolean send(String recipient, String message)`: Dispatches message over external provider rails.
* **Concrete Channels:**
  * `SmsNotificationChannel`: Validates international phone format and enforces max 160 characters.
  * `EmailNotificationChannel`: Validates recipient contains valid `@` format.
  * `PushNotificationChannel`: Validates device FCM token length ($\ge 10$ characters).
* **Creator Contract (`NotificationChannelFactory`):**
  * `NotificationChannel createChannel()`: Abstract factory method.
* **Concrete Creators:**
  * `SmsChannelFactory`, `EmailChannelFactory`, `PushChannelFactory`.
* **Singleton Orchestrator (`NotificationHub`):**
  * Thread-safe double-checked locking singleton with defensive reflection protection.
  * `MAX_GLOBAL_LIMIT = 10`.
  * `boolean dispatchNotification(NotificationChannelFactory factory, String recipient, String message, String idempotencyKey)`: Enforces deduplication and global rate limit checks.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Dispatch communication channels vs. central rate/idempotency policy $\rightarrow$ Factory Method for channels, Singleton for Hub. |
| **2. Data Holders & Containers** | *What objects store state?* | `ConcurrentHashMap.newKeySet()` for idempotency keys, `AtomicInteger` for dispatch count. |
| **3. Abstract Class Check** | *Do channels share state or logic?* | **No.** Validation requirements and protocols differ entirely $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | Singleton `NotificationHub` validates idempotency/rate limits, invokes the factory, and delegates dispatch. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                           NotificationChannel                           |
               +-------------------------------------------------------------------------+
               | + send(recipient: String, message: String): boolean                     |
               +-------------------------------------------------------------------------+
                                 △                               △
                                 :                               :
                 ................:                               :................
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|      SmsNotificationChannel       |                           |      PushNotificationChannel      |
+-----------------------------------+                           +-----------------------------------+
| + send(recipient, msg): boolean   |                           | + send(recipient, msg): boolean   |
+-----------------------------------+                           +-----------------------------------+
                 △                                                               △
                 :                                                               :
                 : <<instantiates>>                                              : <<instantiates>>
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|         SmsChannelFactory         |                           |        PushChannelFactory         |
+-----------------------------------+                           +-----------------------------------+
| + createChannel(): NotifChannel   |                           | + createChannel(): NotifChannel   |
+-----------------------------------+                           +-----------------------------------+
                 :                                                               :
                 :................                               ................:
                                 :                               :
                                 △                               △
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                        NotificationChannelFactory                       |
               +-------------------------------------------------------------------------+
               | + createChannel(): NotificationChannel                                  |
               +-------------------------------------------------------------------------+
                                                     △
                                                     : <<uses / invokes>>
                                                     :
               +-------------------------------------------------------------------------+
               |                             <<Singleton>>                               |
               |                            NotificationHub                              |
               +-------------------------------------------------------------------------+
               | - instance: NotificationHub {static, volatile}                          |
               | - MAX_GLOBAL_LIMIT: int = 10 {static, final}                            |
               | - processedKey: Set<String>                                             |
               | - totalDispatches: AtomicInteger                                        |
               +-------------------------------------------------------------------------+
               | - NotificationHub()                                                     |
               | + getInstance(): NotificationHub {static}                               |
               | + dispatchNotification(factory: NotificationChannelFactory,             |
               |                        recipient: String, msg: String,                  |
               |                        idempotencyKey: String): boolean                 |
               | + getTotalDispatches(): int                                             |
               +-------------------------------------------------------------------------+