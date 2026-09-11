# Low-Level Design (LLD): Distributed Hardware License Key & Lease Token Allocator (Singleton Pattern)

## 📌 Problem Overview
In distributed compute and analytical cluster architectures, worker nodes must acquire time-bound license lease tokens against a hardware-locked cryptographic master license. To strictly respect legal commercial capacity and prevent cluster eviction, a centralized manager must guarantee that active lease allocations never surpass hardware seat limits.

We implement a thread-safe, reflection-resilient **Singleton Pattern** leveraging Double-Checked Locking, backing storage via `ConcurrentHashMap`, explicit thread-safe seat capacity bounds, and lazy eviction of expired lease tokens.

## 🏢 Company Context
**Company:** Snowflake / Databricks / JetBrains Licensing Infrastructure  
**Domain:** Enterprise Entitlement, Cryptographic Key Lease & Concurrency Control  
**Scenario:** An enterprise node running an on-premise compute cluster must validate and issue time-bound license lease tokens against a hardware-locked cryptographic master key. Spawning multiple license managers causes lease token collisions, exceeding customer seat limits and triggering immediate license revocation from the central authority.

---

## 🎯 Requirements

### 1. Functional Requirements
* **License Token Model (`LicenseToken`):**
  * Holds immutable properties: `tokenId` (UUID string), `allocatedTo` (worker id), `issuedAt` (epoch millis), and `durationMillis`.
  * `boolean isValid()`: Validates that system time is within `issuedAt + durationMillis`.
* **Singleton Allocator (`LicenseManager`):**
  * Implements lazy-initialized double-checked locking singleton with reflection defense.
  * Maintains an in-memory thread-safe map (`ConcurrentHashMap<String, LicenseToken>`).
  * Enforces maximum capacity: `MAX_SEATS = 5`.
  * `LicenseToken acquireToken(String workerId, long durationMillis)`: Purges expired tokens, checks capacity bounds, and allocates leases.
  * `boolean releaseToken(String tokenId)`: Explicitly revokes and returns the lease seat.
  * `int getActiveTokenCount()`: Returns active unexpired token count.
* **Verification Driver (`LicenseManagerTest`):**
  * Executes a reflection attack test bypassing constructor visibility.
  * Coordinates concurrent threads contending for finite seat leases.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes or expands?* | Lease allocation policies and duration lengths; instance lifecycle remains strictly unified. |
| **2. Data Holders & Containers** | *What objects store state?* | `LicenseToken` (immutable lease state), `LicenseManager` (singleton pool container with `ConcurrentHashMap`). |
| **3. Abstract Class Check** | *Is an abstract layer needed?* | **No.** Singleton encapsulates instance lifecycle over a concrete class. |
| **4. Orchestrator** | *What coordinates testing?* | `LicenseManagerTest` coordinates reflection tests and multithreaded contention assertions. |

---

## 📐 Class Architecture (UML Diagram)

```text
+---------------------------------------------------------------------------------+
|                                 LicenseManager                                  |
+---------------------------------------------------------------------------------+
| - MAX_SEATS: int = 5 {static, final}                                            |
| - instance: LicenseManager {static, volatile}                                   |
| - activeTokens: ConcurrentHashMap<String, LicenseToken>                         |
+---------------------------------------------------------------------------------+
| - LicenseManager()                                                              |
| + getInstance(): LicenseManager {static}                                        |
| + acquireToken(workerId: String, durationMillis: long): LicenseToken            |
| + releaseToken(tokenId: String): boolean                                        |
| + getActiveTokenCount(): int                                                    |
+---------------------------------------------------------------------------------+
                                         1
                                         ◇
                                         |
                                         | 0..*
                                         v
+---------------------------------------------------------------------------------+
|                                  LicenseToken                                   |
+---------------------------------------------------------------------------------+
| - tokenId: String                                                               |
| - allocatedTo: String                                                           |
| - issuedAt: long                                                                |
| - durationMillis: long                                                          |
+---------------------------------------------------------------------------------+
| + getTokenId(): String                                                          |
| + getAllocatedTo(): String                                                      |
| + isValid(): boolean                                                            |
| + toString(): String                                                            |
+---------------------------------------------------------------------------------+