# Low-Level Design (LLD): E-Commerce Dynamic Order & Discount Engine

## 📌 Problem Overview
The **Dynamic Order & Discount Engine** manages shopping cart orders and computes dynamic pricing adjustments using interchangeable promotional strategies (e.g., Percentage Discounts, Flat Discounts, No Discounts).

The primary objective is to apply the **Strategy Pattern** under SOLID principles, decoupling the checkout orchestrator and order container from specific discount algorithms.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Line Items & Order Container:**
  * `OrderItem`: Stores item name, unit price, and quantity. Exposes `getSubtotal()`.
  * `Order`: Aggregates items, maintains an assigned `DiscountStrategy`, and computes subtotals and discounted totals.
* **Discount Strategies (Axis of Variation #1):**
  * `PercentageDiscount`: Deducts a percentage (e.g., 10%) from the subtotal.
  * `FlatDiscount`: Deducts a fixed monetary amount (e.g., $20.00) from the subtotal, bounded so subtotal never drops below $0.00.
  * `NoDiscount`: Default fallback strategy ($0.00 deduction).
* **Orchestrator Service:**
  * `CheckoutService`: Processes orders and prints an itemized checkout receipt showing line items, subtotal, discount deducted, and final total payable.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands in future sprints?* | **Discount Schemes** will expand $\rightarrow$ Creates **1 Interface**: `DiscountStrategy`. |
| **2. Data Holders & Containers** | *What objects store state?* | Models: `OrderItem` (line item), `Order` (aggregate root container). |
| **3. Abstract Class Check** | *Do strategies share identical state AND logic?* | **No.** `PercentageDiscount` (percentage), `FlatDiscount` (amount), and `NoDiscount` (none) share no state $\rightarrow$ Pure Interface. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `CheckoutService` processes receipts polymorphically via `Order` and `DiscountStrategy`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +--------------------------------------+
               |            <<interface>>             |
               |           DiscountStrategy           |
               +--------------------------------------+
               | + applyDiscount(subtotal: dbl): dbl  |
               +--------------------------------------+
                                   ^
        +--------------------------+--------------------------+
        |                          |                          |
+----------------------+   +----------------------+   +----------------------+
|  PercentageDiscount  |   |     FlatDiscount     |   |      NoDiscount      |
+----------------------+   +----------------------+   +----------------------+
| - percentage: double |   | - amount: double     |   |                      |
+----------------------+   +----------------------+   +----------------------+
| + applyDiscount(...):|   | + applyDiscount(...):|   | + applyDiscount(...):|
|   double             |   |   double             |   |   double             |
+----------------------+   +----------------------+   +----------------------+

+---------------------------+               +------------------------------------+
|         OrderItem         |               |               Order                |
+---------------------------+               +------------------------------------+
| - itemName: String        |               | - orderId: String                  |
| - unitPrice: double       | 1           * | - customerName: String             |
| - quantity: int           |<--------------| - items: List<OrderItem>           |
+---------------------------+               | - discountStrategy:DiscountStrategy|
| + getSubtotal(): double   |               +------------------------------------+
+---------------------------+               | + addItem(item: OrderItem): void   |
                                            | + setDiscountStrategy(s): void     |
                                            | + calculateSubtotal(): double      |
                                            | + calculateFinalTotal(): double    |
                                            +------------------------------------+
                                                               ^
                                                               | (Processes)
                                            +------------------------------------+
                                            |          CheckoutService           |
                                            +------------------------------------+
                                            | + processCheckout(order: Order):   |
                                            |   void                             |
                                            +------------------------------------+