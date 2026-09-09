# Low-Level Design (LLD): Cross-Border Payment Gateway Orchestration Engine (Factory Method Pattern)

## 📌 Problem Overview
An enterprise cross-border payment platform must integrate with disparate third-party payment providers (**PayPal**, **Stripe**, and **Razorpay**). Each gateway requires unique authentication handshakes and transaction protocols.

Directly instantiating providers violates the **Open/Closed Principle (OCP)** and tightly couples business logic to concrete SDKs. We apply the **Factory Method Pattern** to establish parallel product and creator hierarchies, delegating object instantiation to dedicated concrete factories.

---
## 🏢 Company Context
**Company:** Stripe / PayPal / Square Developer Platform 
**Domain:** Cross-Border Merchant Orchestration & Payment Gateways  
**Scenario:** A financial infrastructure engine must integrate diverse third-party payment rails (PayPal, Stripe, Razorpay) across international markets. Client checkout services must decouple from specific SDK client instantiations and provider-specific handshakes.
---
## 🎯 Requirements

### 1. Functional Requirements
* **Product Contract (`PaymentGateway`):**
  * `boolean connect()`: Authenticates and opens gateway channel.
  * `boolean processTransaction(String txnId, double amount)`: Transacts funds.
* **Concrete Gateways:**
  * `PayPalGateway`, `StripeGateway`, `RazorpayGateway`.
* **Creator Contract (`PaymentGatewayFactory`):**
  * `PaymentGateway createGateway()`: Abstract factory method.
* **Concrete Creators:**
  * `PayPalGatewayFactory`, `StripeGatewayFactory`, `RazorpayGatewayFactory`.
* **Orchestrator (`PaymentProcessingService`):**
  * Depends strictly on `PaymentGatewayFactory` and executes connection + processing workflow.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | External payment providers and their initialization procedures $\rightarrow$ Parallel Product and Creator hierarchies. |
| **2. Data Holders & Containers** | *What objects store state?* | Transaction parameters (`transactionId`, `amount`). |
| **3. Abstract Class Check** | *Do factories or gateways share state?* | **No.** Protocols and setup configurations differ across providers $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `PaymentProcessingService` depends on `PaymentGatewayFactory` abstraction. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                             PaymentGateway                              |
               +-------------------------------------------------------------------------+
               | + connect(): boolean                                                    |
               | + processTransaction(txnId: String, amount: double): boolean            |
               +-------------------------------------------------------------------------+
                                 △                               △
                                 :                               :
                 ................:                               :................
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|           PayPalGateway           |                           |          RazorpayGateway          |
+-----------------------------------+                           +-----------------------------------+
| + connect(): boolean              |                           | + connect(): boolean              |
| + processTransaction(...): boolean|                           | + processTransaction(...): boolean|
+-----------------------------------+                           +-----------------------------------+
                 △                                                               △
                 :                                                               :
                 : <<instantiates>>                                              : <<instantiates>>
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|       PayPalGatewayFactory        |                           |       RazorpayGatewayFactory      |
+-----------------------------------+                           +-----------------------------------+
| + createGateway(): PaymentGateway |                           | + createGateway(): PaymentGateway |
+-----------------------------------+                           +-----------------------------------+
                 :                                                               :
                 :................                               ................:
                                 :                               :
                                 △                               △
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                          PaymentGatewayFactory                          |
               +-------------------------------------------------------------------------+
               | + createGateway(): PaymentGateway                                       |
               +-------------------------------------------------------------------------+
                                                     △
                                                     :
                                                     : <<uses / depends on>>
                                                     :
               +-------------------------------------------------------------------------+
               |                        PaymentProcessingService                         |
               +-------------------------------------------------------------------------+
               | + processPayment(factory: PaymentGatewayFactory,                        |
               |                  txnId: String, amount: double): boolean                |
               +-------------------------------------------------------------------------+