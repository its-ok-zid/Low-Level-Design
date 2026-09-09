# Low-Level Design (LLD): Payment Gateway Module

## 📌 Problem Overview
The **Payment Gateway Module** is a core component of an e-commerce checkout engine responsible for processing customer payments across multiple payment methods. 

The primary objective of this exercise is to design an object-oriented payment module strictly adhering to **SOLID principles**, focusing on **polymorphism** to eliminate conditional branching (`if-else` / `switch`) when handling different payment types.

---
## 🏢 Company Context
**Company:** PayPal / Adyen / Checkout.com Merchant Services  
**Domain:** Global Payment Aggregation & Merchant Orchestration  
**Scenario:** A merchant platform must ingest new regional payment rails (Credit Card, UPI, Klarna, Crypto) on a weekly basis. Core checkout orchestration code cannot be opened, modified, or re-tested for every new payment method rollout without risking production regressions on existing payment streams.
## 🎯 Requirements

### 1. Functional Requirements
* **Payment Methods (V1):**
  * `CreditCardPayment`: Stores cardholder name, card number, and CVV.
  * `PayPalPayment`: Stores PayPal email address and account password/token.
* **Core Operation:**
  * Every payment method must implement a `pay(double amount)` action.
  * Executing `pay` formats and processes the payment transaction.
* **Extensibility:**
  * The system must allow adding new payment types (e.g., `UpiPayment`, `CryptoPayment`, `ApplePay`) without altering existing payment classes or the orchestrator.
* **Orchestrator:**
  * A central `PaymentProcessor` class receives payment requests and executes transactions polymorphically.

### 2. Non-Functional & Quality Attributes
* **SOLID Principles Compliance:**
  * **Single Responsibility Principle (SRP):** Decouple payment data, payment execution, and orchestration.
  * **Open/Closed Principle (OCP):** Open for new payment methods, closed for modification.
  * **Liskov Substitution Principle (LSP):** Any `Payment` subtype can replace another seamlessly.
  * **Interface Segregation Principle (ISP):** Keep payment interfaces thin and focused.
  * **Dependency Inversion Principle (DIP):** Orchestrators depend on the `Payment` abstraction, not concrete implementations.

---

## 🧩 The 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands in future sprints?* | **Payment Methods** will expand $\rightarrow$ Creates **1 Interface**: `Payment`. |
| **2. Data Holders & Containers** | *What objects store data/state?* | Concrete models: `CreditCardPayment` (holds card details), `PayPalPayment` (holds credentials). |
| **3. Abstract Class Check** | *Do concrete models share common fields AND code?* | **No.** Credit Card and PayPal have completely different attributes. No abstract class needed. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `PaymentProcessor` delegates execution via `Payment.pay(amount)`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +---------------------------+
               |       <<interface>>       |
               |          Payment          |
               +---------------------------+
               | + pay(amount: double):void|
               +---------------------------+
                             ^
                             | (Realization)
        +--------------------+--------------------+
        |                                         |
+-----------------------------------+   +-----------------------------------+
|         CreditCardPayment         |   |           PayPalPayment           |
+-----------------------------------+   +-----------------------------------+
| - cardHolderName: String          |   | - email: String                   |
| - cardNumber: String              |   | - password: String                |
| - cvv: int                        |   +-----------------------------------+
+-----------------------------------+   | + pay(amount: double): void       |
| + pay(amount: double): void       |   +-----------------------------------+
+-----------------------------------+

                             ^
                             | (Uses / Depends on via DIP)
               +---------------------------+
               |     PaymentProcessor      |
               +---------------------------+
               | + processTransaction(     |
               |     payment: Payment,     |
               |     amount: double): void |
               +---------------------------+