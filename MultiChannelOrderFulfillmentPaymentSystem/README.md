# Low-Level Design (LLD): Multi-Channel Order Fulfillment & Payment System

## 📌 Problem Overview
The **Multi-Channel Order Fulfillment & Payment System** coordinates customer order transactions across two orthogonal axes of variation: **Payment Processing Strategy** (Credit Card, UPI, Crypto) and **Customer Notification Strategy** (Email, SMS, WhatsApp).

The primary objective is to demonstrate **Multi-Strategy Orchestration** under SOLID principles, ensuring neither payment mechanisms nor notification channels are tightly coupled to the fulfillment domain.

## 🏢 Real-World Company Context
* **Target Companies:** Amazon Pay / Shopify Core Checkout / Stripe Billing
* **Industry Scenario:** Enterprise e-commerce platforms must support dynamic multi-region checkout combinations (e.g., checkout using UPI in India or Crypto in Web3 portals) while simultaneously broadcasting delivery updates through distinct channels (SMS, Email, WhatsApp) without mutating the core order processing pipeline.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Order Domain Model:**
  * `Order`: Stores `orderId`, `customerName`, `email`, and `orderAmount`.
* **Axis of Variation #1: Payment Processing Strategies:**
  * `CreditCardPayment`: Applies a 2% surcharge (`amount * 1.02`).
  * `UpiPayment`: Applies 0% surcharge (`amount`).
  * `CryptoWalletPayment`: Applies a flat $5.00 network gas fee (`amount + 5.00`).
* **Axis of Variation #2: Notification Dispatch Strategies:**
  * `EmailNotification`: Outputs formatted email message to recipient address.
  * `SmsNotification`: Outputs SMS message bounded to 160 characters.
  * `WhatsAppNotification`: Outputs formatted WhatsApp message.
* **Orchestrator Service:**
  * `FulfillmentService`: Coordinates transaction processing. Dispatches success receipt on payment success (`true`), and alerts failure on payment decline (`false`).

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently in future sprints?* | **Two Independent Dimensions:** Payment methods and notification channels $\rightarrow$ **2 Interfaces**: `PaymentStrategy`, `NotificationStrategy`. |
| **2. Data Holders & Containers** | *What objects store state?* | Model: `Order`. |
| **3. Abstract Class Check** | *Do strategies share identical state AND logic?* | **No.** Surcharge rules and messaging protocols are distinct $\rightarrow$ Pure Interfaces. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `FulfillmentService` orchestrates both strategies polymorphically using DIP. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +------------------------------------+          +--------------------------------------------+
               |           <<interface>>            |          |               <<interface>>                |
               |          PaymentStrategy           |          |            NotificationStrategy            |
               +------------------------------------+          +--------------------------------------------+
               | + processPayment(amt: double): bool|          | + sendNotification(rcpt, msg: String): void|
               +------------------------------------+          +--------------------------------------------+
                                  ^                                                           ^
         +------------------------+------------------------+        +-------------------------+-------------------------+
         |                        |                        |        |                         |                         |
+------------------+     +------------------+     +------------------+ +------------------+  +------------------+  +------------------+
| CreditCardPayment|     |    UpiPayment    |     |CryptoWalletPaymnt| |EmailNotification |  | SmsNotification  |  | WhatsAppNotif... |
+------------------+     +------------------+     +------------------+ +------------------+  +------------------+  +------------------+
| + processPayment |     | + processPayment |     | + processPayment | | + sendNotif...   |  | + sendNotif...   |  | + sendNotif...   |
+------------------+     +------------------+     +------------------+ +------------------+  +------------------+  +------------------+

                                         +--------------------------------------------+
                                         |                   Order                    |
                                         +--------------------------------------------+
                                         | - orderId: String                          |
                                         | - customerName: String                     |
                                         | - email: String                            |
                                         | - orderAmount: double                      |
                                         +--------------------------------------------+
                                         | + getters...                               |
                                         +--------------------------------------------+
                                                                ^
                                                                | (Processed by)
                                         +--------------------------------------------+
                                         |             FulfillmentService             |
                                         +--------------------------------------------+
                                         | + fulfillOrder(order: Order,               |
                                         |                pStrategy: PaymentStrategy, |
                                         |                nStrategy: NotificationSt): |
                                         |   void                                     |
                                         +--------------------------------------------+