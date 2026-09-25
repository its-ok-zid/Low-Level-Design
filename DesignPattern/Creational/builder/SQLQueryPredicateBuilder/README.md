# Low-Level Design (LLD): Fluent SQL Query & Predicate Builder (Step Builder Pattern)

## 📌 Problem Overview
Writing dynamic SQL via string concatenation or telescoping constructors in database drivers and ORMs leads to syntax errors, SQL injection vulnerabilities, and clause misplacement (e.g., placing `WHERE` before `FROM`). 

While a standard Builder pattern allows flexible object creation, it cannot prevent callers from chaining methods out of order (such as calling `.limit()` before `.fromTable()`). We implement the **Step Builder Pattern** (Staged Builder) using interface segregation to enforce compile-time SQL grammar, followed by strict invariant validation at build time.

## 🏢 Company Context
**Company:** Snowflake / Oracle / CockroachDB / JOOQ  
**Domain:** Database Query Engine & AST Expression Builders  
**Scenario:** Analytical querying tools must construct dynamic queries across tables with varying filters and aggregations. The client query builder must guide developers through the required sequence (`SELECT` $\rightarrow$ `FROM` $\rightarrow$ optional clauses $\rightarrow$ `build()`), guaranteeing immutable, grammatically correct SQL strings.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Step Contracts (Interfaces):**
  * `SelectStep`: Declares `FromStep select(String... columns)`.
  * `FromStep`: Declares `OptionalSteps fromTable(String table)`.
  * `OptionalSteps`: Declares chaining methods (`join`, `where`, `groupBy`, `orderBy`, `limit`, `offset`) and `SqlQuery build()`.
* **Immutable Product (`SqlQuery`):**
  * Private/package-private constructor consuming the builder.
  * Fields: `table`, `columns`, `joinClauses`, `whereClauses`, `groupByColumns`, `orderBy`, `limit`, `offset`.
  * Defensive collection immutability via `List.copyOf(...)`.
  * `String toSql()`: Emits valid ANSI SQL formatted as:
    `SELECT <cols> FROM <table> [JOINS] [WHERE] [GROUP BY] [ORDER BY] [LIMIT] [OFFSET];`
* **Builder Implementation (`SqlQueryBuilder`):**
  * Implements `SelectStep`, `FromStep`, and `OptionalSteps`.
  * Validates table presence, non-negative limits/offsets, and enforces the rule that `OFFSET` requires `LIMIT`.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Mandatory clauses (`SELECT`, `FROM`) vs. optional clauses (`JOIN`, `WHERE`, `GROUP BY`, `ORDER BY`, `LIMIT`, `OFFSET`) $\rightarrow$ Step Builder. |
| **2. Data Holders & Containers** | *What objects store state?* | Accumulator lists inside `SqlQueryBuilder`; immutable `List.copyOf(...)` inside `SqlQuery`. |
| **3. Abstract Class Check** | *Do steps share logic?* | **No.** Step stages represent compile-time state transitions $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | Fluent caller chain initiated via `SqlQuery.builder()`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                                SqlQuery                                 |
               +-------------------------------------------------------------------------+
               | - table: String                                                         |
               | - columns: List<String>                                                 |
               | - joinClauses: List<String>                                             |
               | - whereClauses: List<String>                                            |
               | - groupByColumns: List<String>                                          |
               | - orderBy: String                                                       |
               | - limit: Integer                                                        |
               | - offset: Integer                                                       |
               +-------------------------------------------------------------------------+
               | ~ SqlQuery(builder: SqlQueryBuilder)                                    |
               | + builder(): SelectStep {static}                                        |
               | + toSql(): String                                                       |
               +-------------------------------------------------------------------------+
                                                     ▲
                                                     : <<creates>>
                                                     :
               +-------------------------------------------------------------------------+
               |                             SqlQueryBuilder                             |
               +-------------------------------------------------------------------------+
               | ~ table: String                                                         |
               | ~ columns: List<String>                                                 |
               | ~ joinClauses: List<String>                                             |
               | ~ whereClauses: List<String>                                            |
               | ~ groupByColumns: List<String>                                          |
               | ~ orderBy: String                                                       |
               | ~ limit: Integer                                                        |
               | ~ offset: Integer                                                       |
               +-------------------------------------------------------------------------+
               | + select(cols: String...): FromStep                                     |
               | + fromTable(table: String): OptionalSteps                               |
               | + join(clause: String): OptionalSteps                                   |
               | + where(condition: String): OptionalSteps                               |
               | + groupBy(cols: String...): OptionalSteps                               |
               | + orderBy(clause: String): OptionalSteps                               |
               | + limit(limit: int): OptionalSteps                                      |
               | + offset(offset: int): OptionalSteps                                    |
               | + build(): SqlQuery                                                     |
               +-------------------------------------------------------------------------+
                 |                                  |                                  |
                 | <<implements>>                   | <<implements>>                   | <<implements>>
                 v                                  v                                  v
+---------------------------------+  +-------------------------------+  +--------------------------------+
|         <<interface>>           |  |         <<interface>>         |  |         <<interface>>          |
|           SelectStep            |  |           FromStep            |  |         OptionalSteps          |
+---------------------------------+  +-------------------------------+  +--------------------------------+
| + select(cols...): FromStep     |  | + fromTable(t): OptionalSteps |  | + join(...): OptionalSteps     |
+---------------------------------+  +-------------------------------+  | + where(...): OptionalSteps    |
                                                                        | + groupBy(...): OptionalSteps  |
                                                                        | + orderBy(...): OptionalSteps  |
                                                                        | + limit(...): OptionalSteps    |
                                                                        | + offset(...): OptionalSteps   |
                                                                        | + build(): SqlQuery            |
                                                                        +--------------------------------+