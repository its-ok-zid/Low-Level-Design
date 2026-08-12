# Low-Level Design (LLD): Employee Payroll & Attendance System

## 📌 Problem Overview
The **Employee Payroll System** manages employee attendance (`clockIn`, `clockOut`) and calculates payouts across different worker types (e.g., Full-Time, Contractors).

The objective is to combine an **Interface** with an **Abstract Base Class** to reuse shared attendance logic and encapsulation while isolating employee-specific payment algorithms.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Employee Types (V1):**
  * `FullTimeEmployee`: Stores ID, name, base monthly salary, and performance bonus percentage.
  * `ContractorEmployee`: Stores ID, name, hourly rate, and total hours worked.
* **Core Operations:**
  * `clockIn()` / `clockOut()`: Toggles attendance state safely.
  * `calculatePay()`:
    * `FullTimeEmployee`: `baseSalary + (baseSalary * bonus / 100)`
    * `ContractorEmployee`: `160 * rate + (overtimeHours * rate * 1.5)`
* **Orchestrator:**
  * `PayrollService`: Manages employee lists and executes payouts polymorphically.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes in future sprints?* | **Employee Types** expand $\rightarrow$ **1 Interface**: `Employee`. |
| **2. Data Holders & Containers** | *What objects store state?* | Models: `FullTimeEmployee`, `ContractorEmployee`. |
| **3. Abstract Class Check** | *Do models share state AND logic?* | **Yes.** Shared `employeeId`, `name`, `isClockedIn`, and identical clocking logic $\rightarrow$ `AbstractEmployee`. |
| **4. Orchestrator** | *What glues components together?* | `PayrollService` processes payouts polymorphically. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-----------------------------------+
               |           <<interface>>           |
               |             Employee              |
               +-----------------------------------+
               | + clockIn(): boolean              |
               | + clockOut(): boolean             |
               | + calculatePay(): double          |
               +-----------------------------------+
                                 ^
                                 | (Realization)
               +-----------------------------------+
               |         AbstractEmployee          |
               +-----------------------------------+
               | - employeeId: String              |
               | - name: String                    |
               | - isClockedIn: boolean            |
               +-----------------------------------+
               | + clockIn(): boolean              |
               | + clockOut(): boolean             |
               +-----------------------------------+
                                 ^
                                 | (Inheritance)
        +------------------------+------------------------+
        |                                                 |
+-----------------------------------+   +-----------------------------------+
|         FullTimeEmployee          |   |        ContractorEmployee         |
+-----------------------------------+   +-----------------------------------+
| - monthlyBaseSalary: double       |   | - hourlyRate: double              |
| - performanceBonusPercentage: dbl |   | - hoursWorked: double             |
+-----------------------------------+   +-----------------------------------+
| + calculatePay(): double          |   | + calculatePay(): double          |
+-----------------------------------+   +-----------------------------------+

                                 ^
                                 | (Uses / Depends on via DIP)
               +-----------------------------------+
               |          PayrollService           |
               +-----------------------------------+
               | + processPayout(emp: Employee)    |
               +-----------------------------------+