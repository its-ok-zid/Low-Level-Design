# Low-Level Design (LLD): Cross-Cloud Distributed Sandbox & Job Execution Platform

## 📌 Problem Overview
Modern multi-tenant cloud runtimes (e.g., AWS Batch, Google Cloud Run, distributed CI/CD workers) face critical challenges when dynamically spinning up isolated compute jobs across heterogeneous cloud platforms (AWS, GCP):
1. **Incompatible Mixing Risk:** Accidental runtime attachment of mismatched provider primitives (e.g., attempting to attach an AWS EBS storage volume to a GCP Compute Engine instance) leads to disk corruption or unrecoverable boot failures.
2. **Cold-Boot Latency:** Cold-booting environments and baseline kernel images from scratch takes seconds to minutes, making high-throughput batch testing unviable.
3. **Complex Construction & Hardware Invariants:** Compute instances require numerous mandatory and optional configuration parameters (CPU, RAM, subnet CIDRs, tags, telemetry flags) with strict hardware invariants (e.g., $\text{RAM} \ge 2 \times \text{CPU}$).
4. **Platform Coordination & Quota Exhaustion:** The execution runtime requires thread-safe, centralized quota tracking and golden blueprint caching to prevent noisy-neighbor cluster exhaustion.

To solve these constraints, this architecture synthesizes all **five Creational Design Patterns**:
* **Abstract Factory:** Enforces strict provider-family pairing between compute instances and storage volumes.
* **Builder (with CRTP):** Provides fluent, type-safe construction with compile-time inheritance and runtime hardware invariant enforcement.
* **Prototype:** Enables sub-millisecond environment provisioning via defensive multi-tier deep cloning with zero mutation bleed into registered master templates.
* **Singleton:** Coordinates centralized template caching and atomic cluster quota management using double-checked locking.
* **Factory Method:** Decouples client workflows from provider-specific instantiation via dynamic provisioner dispatch.

---

## 🏢 Company Context
* **Target Companies:** Amazon Web Services (AWS EC2 / Batch), Google Cloud Platform (Cloud Run Core), Salesforce (Hyperforce Compute Team)
* **Domain:** High-Frequency Cloud Infrastructure Orchestration & Isolated Execution Sandboxes
* **Role Level:** SDE-2 / Senior Backend Engineer (Low-Level Design Round)

---

## 🎯 System Requirements

### 1. Functional Requirements
* **Family Consistency:** AWS compute instances must only accept AWS EBS storage volumes; GCP instances must only accept GCP persistent disks. Incompatible volume attachments must be rejected fail-fast.
* **Invariant Enforcement:** Compute instances must reject configuration where $\text{RAM} < 2 \times \text{CPU}$, missing instance IDs, non-positive core counts, or unassigned subnet CIDRs.
* **Sub-Millisecond Provisioning:** Pre-warmed "Golden Master" environments must be cached in memory and duplicated via deep cloning so runtime test modifications (e.g., writing log blocks to disk, altering environment variables) do not mutate the registered baseline archetype.
* **Dynamic Provider Resolution:** Client code must resolve concrete cloud factories dynamically using a string or enum identifier without hardcoding concrete factory types.
* **Centralized Quota Tracking:** The engine must enforce an atomic maximum instance cap (e.g., max 10 active instances) across all cloud providers and allow capacity recovery upon sandbox release.

### 2. Non-Functional Requirements
* **Thread Safety:** The singleton manager must guarantee thread-safe access under high-concurrency multi-threaded CI/CD workers using double-checked locking (`volatile`) and thread-safe data structures (`ConcurrentHashMap`, `AtomicInteger`).
* **Open-Closed Principle (OCP):** Introducing a new cloud provider (e.g., Azure, OCI) must only require adding new concrete products and factories without modifying the client provisioning engine or coordinator.
* **Zero Mutation Bleed:** Deep cloning must isolate nested mutable collections (disk block lists, tag maps, environment variable dictionaries) across all layers.

---

## 🧠 4-Step Mental Algorithm Breakdown

When approaching complex multi-pattern synthesis problems under high-pressure interview conditions, follow this structured mental algorithm:

### Step 1: Core Problem & Invariant Decomposition
* Break down the problem statement into distinct operational boundaries:
    * **Family Compatibility Boundary:** Are there co-dependent object suites from different vendors that must never be mixed? Identify product pairings (`ComputeInstance` + `StorageVolume`).
    * **Construction Complexity Boundary:** Are there multiple optional/mandatory parameters, telescoping constructors, or strict validation equations ($\text{RAM} \ge 2 \times \text{CPU}$)? Identify builder requirements.
    * **Latency & Lifecycle Boundary:** Is object instantiation from scratch too heavy, or is there a need to stamp out clones from a pre-warmed archetype? Identify prototype boundaries.
    * **Global Coordination Boundary:** Is there a single point of truth required for quotas, caches, or connections across all worker threads? Identify singleton boundaries.

### Step 2: Interface & Generics Boundary Definition
* Define the contracts cleanly before writing any business logic:
    * Establish the generic prototype contract `Prototype<T>` with explicit `shallowCopy()` and `deepCopy()`.
    * Establish the abstract products (`ComputeInstance`, `StorageVolume`) extending `Prototype<T>`.
    * Decouple the abstract factory interface (`CloudInfrastructureFactory`) strictly to return product interfaces, keeping product state outside of the factory classes.
    * Apply the **Curiously Recurring Template Pattern (CRTP)** (`<T extends ComputeInstanceBuilder<T>>`) in the base builder with protected `self()` to ensure fluent method chaining persists across inheritance layers without dropping child identity.

### Step 3: Layered Implementation & Defensive Invariant Enforcement
* Implement concrete classes with strict encapsulation and fail-fast invariants:
    * In the builders, run `.validate()` inside `.build()` to verify hardware invariants, positive numerical constraints, and required subnets.
    * In concrete compute instances, enforce runtime type assertions in `.attachVolume()` (e.g., `!(volume instanceof AwsStorageVolume)` throws `IllegalArgumentException`) to guard against cross-cloud contamination.
    * In prototype implementations, implement copy constructors that deeply copy all nested mutable references (e.g., `diskBlocks`, `tags`, `environmentVariables`) while keeping immutable Flyweight references (like OS image buffers) shared.
    * In the singleton manager, use double-checked locking with `volatile`, defensively deep-copy prototypes on both insertion (`registerGoldenTemplate`) and retrieval (`aquireExecutionEnvironment`), and manage active capacity atomically with `AtomicInteger`.

### Step 4: Trace & Validate with an Automated 4-Stage Test Harness
* Write an outside-in client driver using the **4-Stage Verification Formula**:
    * **Stage 1 (Negative Validation):** Prove that builders fail on hardware ratio breaches or missing fields, and prove that cross-cloud volume attachment throws expected exceptions.
    * **Stage 2 (Positive Assembly):** Wire the leaf storage products, pass them into concrete compute builders, wrap them in aggregate execution environments, and register them into the singleton cache.
    * **Stage 3 (Mutation Isolation):** Acquire a clone, mutate its disk blocks and environment variables, and assert that the master template in cache remains unmodified.
    * **Stage 4 (Quota & Lifecycle):** Exhaust the singleton's active instance quota to verify rejection, release an active sandbox, and verify subsequent acquisitions succeed.

---

## 📐 System Architecture & Complete UML Diagram

Below is the complete architectural layout reflecting the design:

```text
                               +---------------------------------------------+
                               |                <<interface>>                |
                               |                Prototype<T>                 |
                               +---------------------------------------------+
                               | + shallowCopy() : T                         |
                               | + deepCopy() : T                            |
                               +---------------------------------------------+
                                      ▲                      ▲               ▲
                                      │                      │               │
                                      │                      │               │
      +───────────────────────────────┴──────────────+       │               │
      │                                              │       │               │
+------------------------------------------+ +-----------------------------+ │
|              <<interface>>               | |        <<interface>>        | │
|             ComputeInstance              | |        StorageVolume        | │
+------------------------------------------+ +-----------------------------+ │
| + getInstanceId() : String               | | + getVolumeId() : String    | │
| + getCpuCores() : int                    | | + getCapacityGb() : int     | │
| + getRamGb() : int                       | | + getStorageType() : String | │
| + getSubnetCidr() : String               | | + getDiskBlocks() : List    | │
| + getTags() : Map<String, String>        | | + writeData(block: String)  | │
| + isMonitoringEnabled() : boolean        | +-----------------------------+ │
| + getAttachedVolume() : StorageVolume    |                 ▲               │
| + attachVolume(vol: StorageVolume) : void|                 │               │
| + executeTask(script: String) : void     |         +───────┴───────+       │
+------------------------------------------+         │               │       │
       ▲                            ▲                │               │       │
       │                            │                │               │       │
+──────────────────────+    +──────────────────────+ │               │       │
|  AwsComputeInstance  |    |  GcpComputeInstance  | │               │       │
+──────────────────────+    +──────────────────────+ │               │       │
| - instanceId: String |    | - instanceId: String | │               │       │
| - cpuCores: int      |    | - cpuCores: int      | │               │       │
| - ramGb: int         |    | - ramGb: int         | │               │       │
| - subnetCidr: String |    | - subnetCidr: String | │               │       │
| - tags: Map          |    | - tags: Map          | │               │       │
| - monitoringEnabled  |    | - monitoringEnabled  | │               │       │
| - attachedVolume:    |    | - attachedVolume:    | │               │       │
|   AwsStorageVolume   |    |   GcpStorageVolume   | │               │       │
+──────────────────────+    +──────────────────────+ │               │       │
| + attachVolume()     |    | + attachVolume()     | │               │       │
| + deepCopy()         |    | + deepCopy()         | │               │       │
+──────────────────────+    +──────────────────────+ │               │       │
       │                                   │         │               │       │
       │ manages                           │ manages │               │       │
       ▼                                   ▼         │               │       │
+──────────────────────+    +──────────────────────+ │               │       │
|   AwsStorageVolume   |    |   GcpStorageVolume   | │               │       │
+──────────────────────+    +──────────────────────+ │               │       │
| - volumeId: String   |    | - volumeId: String   |─┘               │       │
| - capacityGb: int    |    | - capacityGb: int    |                 │       │
| - volumeType: String |    | - diskType: String   |─────────────────┘       │
| - diskBlocks: List   |    | - diskBlocks: List   |                         │
+──────────────────────+    +──────────────────────+                         │
| + writeData()        |    | + writeData()        |                         │
| + deepCopy()         |    | + deepCopy()         |                         │
+──────────────────────+    +──────────────────────+                         │
                                                                             │
                                                                             │
                                                                             │
+-------------------------------------------------------------+              │
|                    ExecutionEnvironment                     |──────────────┘
+-------------------------------------------------------------+
| - environmentId: String                                     |
| - cloudProvider: String                                     |
| - computeInstance: ComputeInstance                          |
| - environmentVariables: Map<String, String>                 |
+-------------------------------------------------------------+
| + shallowCopy() : ExecutionEnvironment                      |
| + deepCopy() : ExecutionEnvironment                         |
+-------------------------------------------------------------+
                               ▲
                               │ clones & manages
                               │
+-------------------------------------------------------------+
|                  GlobalInfrastructureManager                |
+-------------------------------------------------------------+
| - instance: GlobalInfrastructureManager                     |
| - templateRegistry: ConcurrentHashMap<String, ExecutionEnv> |
| - activeInstanceCount: AtomicInteger                        |
| - maxInstanceQuota: int                                     |
+-------------------------------------------------------------+
| + getInstance() : GlobalInfrastructureManager               |
| + registerGoldenTemplate(key: String, env: ExecutionEnv)    |
| + aquireExecutionEnvironment(key: String) : ExecutionEnv    |
| + releaseExecutionEnvironment(env: ExecutionEnv)            |
+-------------------------------------------------------------+


===================================================================================
                        BUILDER HIERARCHY (CRTP)
===================================================================================

                +------------------------------------------------+
                |                  <<abstract>>                  |
                |          ComputeInstanceBuilder<T>             |
                +------------------------------------------------+
                | # instanceId: String                           |
                | # cpuCores: int                                |
                | # ramGb: int                                   |
                | # tags: Map<String, String>                    |
                | # monitoringEnabled: boolean                   |
                | # attachedVolume: StorageVolume                |
                +------------------------------------------------+
                | # abstract self() : T                          |
                | + instanceId(id: String) : T                   |
                | + cpuCores(cores: int) : T                     |
                | + ramGb(ram: int) : T                          |
                | + tags(tags: Map) : T                          |
                | + tag(k: String, v: String) : T                |
                | + monitoringEnabled(flag: boolean) : T         |
                | + attachedVolume(vol: StorageVolume) : T       |
                | # validate() : void                            |
                | + abstract build() : ComputeInstance           |
                +------------------------------------------------+
                        ▲                                ▲
                        │                                │
+───────────────────────────────────────+ +───────────────────────────────────────+
|      AwsComputeInstanceBuilder        | |      GcpComputeInstanceBuilder        |
+───────────────────────────────────────+ +───────────────────────────────────────+
| - subnetCidr: String                  | | - subnetCidr: String                  |
+───────────────────────────────────────+ +───────────────────────────────────────+
| # self() : AwsComputeInstanceBuilder  | | # self() : GcpComputeInstanceBuilder  |
| + subnetCidr(cidr: String) : Builder  | | + subnetCidr(cidr: String) : Builder  |
| + attachedVolume(vol: AwsVol) : Bldr  | | + build() : ComputeInstance           |
| + build() : AwsComputeInstance        | +───────────────────────────────────────+
+───────────────────────────────────────+


===================================================================================
                   ABSTRACT FACTORY & PROVISIONER HIERARCHY
===================================================================================

+-------------------------------------------------------------+
|                  InfrastructureProvisioner                  |
+-------------------------------------------------------------+
| + getFactory(providerType: String) : CloudInfrastructureFac |
+-------------------------------------------------------------+
                               │ creates
                               ▼
+-------------------------------------------------------------+
|                        <<interface>>                        |
|                  CloudInfrastructureFactory                 |
+-------------------------------------------------------------+
| + createComputeInstance(bldr: ComputeInstanceBuilder) : CI  |
| + createStorageVolume(id: String, cap: int) : StorageVolume |
+-------------------------------------------------------------+
                ▲                                ▲
                │                                │
+───────────────────────────────+ +───────────────────────────────+
|    AwsInfrastructureFactory   | |    GcpInfrastructureFactory   |
+───────────────────────────────+ +───────────────────────────────+
| + createComputeInstance()     | | + createComputeInstance()     |
| + createStorageVolume()       | | + createStorageVolume()       |
+───────────────────────────────+ +───────────────────────────────+