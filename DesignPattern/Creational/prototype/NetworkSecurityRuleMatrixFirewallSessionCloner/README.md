# Low-Level Design (LLD): Network Security Rule Matrix & Firewall Session Cloner (Prototype Pattern)

## 📌 Problem Overview
High-throughput stateful firewalls process millions of active packet inspection sessions per second. Compiling security policies, threat signatures, and rate limiters from scratch for every network connection incurs substantial CPU and memory overhead.

We implement the **Prototype Pattern** to clone pre-compiled baseline security matrices. This design demonstrates the deep-copying of **nested lists of prototypes** (`List<SecurityRule>`), ensuring runtime modifications (e.g., incrementing packet match counters or adding dynamic threat signatures) do not mutate baseline policy templates.

## 🏢 Company Context
**Company:** Palo Alto Networks / Cisco Systems / Cloudflare Magic Firewall  
**Domain:** Deep Packet Inspection (DPI) & Stateful Firewall Session Management  
**Scenario:** A carrier-grade firewall matches incoming connection handshakes against security profiles. The policy cache provisions an isolated, deep clone of the rule matrix for each active session so dynamic counters and threat updates remain session-isolated.

---

## 🎯 Requirements

### 1. Functional Requirements
* **`Prototype<T>` Contract:** Type-safe interface declaring `T shallowCopy()` and `T deepCopy()`.
* **Leaf Prototype (`SecurityRule`):** Holds inspection attributes (`ruleId`, `protocol`, `portRange`, `action`, `matchedPacketCount`). Clones must be independent objects so packet counters do not bleed across sessions.
* **Component Prototype (`ThreatSignatureEnvelope`):** Encapsulates `Set<String> signatures`. Deep copy produces an independent `HashSet`.
* **Root Prototype (`FirewallSessionPolicy`):** Holds `List<SecurityRule>`, `ThreatSignatureEnvelope`, and `Map<String, Integer> rateLimits`. Deep copying iterates through the rule list and invokes `.deepCopy()` on each element.
* **Cache Registry (`FirewallPolicyCache`):** Stores defensive deep copies of golden security profiles.

---

## 📐 Class Architecture (UML Diagram)

```text
       +---------------------------------------------+
       |                <<interface>>                |
       |                Prototype<T>                 |
       +---------------------------------------------+
       | + shallowCopy(): T                          |
       | + deepCopy(): T                             |
       +---------------------------------------------+
          ▲                        ▲               ▲
          |                        |               |
          | <<implements>>         |               | <<implements>>
          |                        |               |
+---------------------+  +--------------------+  +---------------------------------------+
|    SecurityRule     |  | ThreatSigEnvelope  |  |         FirewallSessionPolicy         |
+---------------------+  +--------------------+  +---------------------------------------+
| - ruleId: String    |  | - signatures: Set  |  | - sessionId: String                   |
| - protocol: String  |  | - threshold: int   |  | - policyTier: String                  |
| - portRange: String |  +--------------------+  | - rules: List<SecurityRule>           |
| - action: RuleAction|  | + addSignature()   |  | - threatSignature: ThreatSigEnvelope  |
| - packetCount: long |  | + shallowCopy()    |  | - rateLimits: Map<String, Integer>    |
+---------------------+  | + deepCopy()       |  | - active: boolean                     |
| + incrementCount()  |  +--------------------+  +---------------------------------------+
| + shallowCopy()     |                          | + shallowCopy(): FirewallSessionPolicy|
| + deepCopy()        |                          | + deepCopy(): FirewallSessionPolicy   |
+---------------------+                          +---------------------------------------+
          ▲                                                          ▲
          |                                                          |
          +-------------------------+--------------------------------+
                                    | manages in-memory cache
                         +---------------------+
                         | FirewallPolicyCache |
                         +---------------------+
                         | - cache: Map        |
                         +---------------------+
                         | + registerPolicy()  |
                         | + getPolicy()       |
                         +---------------------+