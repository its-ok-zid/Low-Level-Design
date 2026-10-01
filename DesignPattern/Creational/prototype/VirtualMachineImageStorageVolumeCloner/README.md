# Low-Level Design (LLD): Virtual Machine Image & Storage Volume Cloner (Prototype Pattern)

## 📌 Problem Overview
Instantiating complex distributed objects like cloud compute instances from scratch is computationally expensive. Generating partition tables, zeroing storage blocks, configuring kernel parameters, and setting up network routing tables takes considerable time.

The **Prototype Pattern** addresses this by cloning pre-configured baseline archetypes ("Golden Master" templates) in memory. This design highlights the **Deep Copy vs. Shallow Copy boundary**, ensuring cloned instances receive isolated disk blocks and configuration maps rather than corrupted, shared memory references.

## 🏢 Company Context
**Company:** Amazon Web Services (EC2 AMI Engine) / VMware Cloud  
**Domain:** Cloud Compute Virtualization & Hypervisor Template Provisioning  
**Scenario:** An EC2 hypervisor provisions thousands of virtual machines dynamically. Instead of cold-booting from disk images, the hypervisor clones cached master VM templates in sub-milliseconds. Mutable components (virtual block storage volumes, MAC addresses, environment tags) must be deeply cloned so subsequent modifications do not corrupt the golden template.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Generic Prototype Contract (`Prototype<T>`):**
  * `T shallowCopy()`: Duplicates primitive values but preserves shared references to child objects and collections.
  * `T deepCopy()`: Duplicates primitive values and recurses into nested mutable components to guarantee complete heap memory isolation.
* **Components:**
  * `StorageVolume`: Holds volume metadata and a mutable `List<String> fileSystemBlocks`. Deep copying creates an independent list copy.
  * `NetworkInterface`: Holds immutable network descriptors (`macAddress`, `ipAddress`, `subnetId`).
* **Root Product (`VirtualMachine`):**
  * Holds hardware specifications, nested `primaryVolume`, `networkInterface`, and a mutable `Map<String, String> tags`.
  * Deep copy recursively invokes `.deepCopy()` on child prototypes and clones the tag map.
* **Prototype Registry (`VmTemplateRegistry`):**
  * Maintains an in-memory cache of baseline golden images.
  * Stores deep copies upon registration and defaults to returning deep copies to protect cached master templates from mutation.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What is being cloned and how deep?* | Primitives (int, String) vs. mutable references (`List<String>`, `Map<String, String>`, nested objects). |
| **2. Data Holders & Containers** | *How is cloning executed?* | Copy constructors parameterized by a `boolean deepCopy` flag. |
| **3. Abstract Class Check** | *Is inheritance needed?* | Generic interface `Prototype<T>` applied across all cloneable tree nodes. |
| **4. Orchestrator** | *Where are archetypes cached?* | `VmTemplateRegistry` acts as the template cache and isolation barrier. |

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
+---------------------+  +--------------------+  +-------------------------------------+
|    StorageVolume    |  |  NetworkInterface  |  |           VirtualMachine            |
+---------------------+  +--------------------+  +-------------------------------------+
| - volumeId: String  |  | - macAddress: Str  |  | - instanceId: String                |
| - sizeGb: int       |  | - ipAddress: Str   |  | - osName: String                    |
| - storageType: Str  |  | - subnetId: Str    |  | - cpuCores: int                     |
| - blocks: List<Str> |  +--------------------+  | - ramGb: int                        |
+---------------------+  | + shallowCopy()    |  | - primaryVolume: StorageVolume      |
| + shallowCopy()     |  | + deepCopy()       |  | - networkInterface: NetworkInterface|
| + deepCopy()        |  +--------------------+  | - tags: Map<String, String>         |
+---------------------+                          +-------------------------------------+
          ▲                                      | + shallowCopy(): VirtualMachine     |
          |                                      | + deepCopy(): VirtualMachine        |
          +------------------+-------------------+-------------------------------------+
                             | holds reference (1-to-1)
                             |
                   +--------------------+
                   | VmTemplateRegistry |
                   +--------------------+
                   | - registry: Map    |
                   +--------------------+
                   | + registerTemplate |
                   | + getTemplate()    |
                   +--------------------+