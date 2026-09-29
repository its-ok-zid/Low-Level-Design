# Low-Level Design (LLD): Hierarchical Kubernetes Pod & Multi-Container Deployment Descriptor Builder

## 📌 Problem Overview
Declarative infrastructure models like Kubernetes Pod specs represent complex, deeply nested hierarchical trees. A Pod contains container configurations (images, ports, resource limits, environment variables) alongside attached volumes and metadata labels.

Standard flat builders cannot cleanly manage child sub-trees without parameter explosion or rigid sequencing. We apply the **Hierarchical Fluent Builder Pattern with Parent Back-Referencing** to allow child builders (`ContainerSpecBuilder`, `VolumeSpecBuilder`) to configure child objects and hand control back to the root `PodDeploymentSpecBuilder`.

## 🏢 Company Context
**Company:** Google Cloud (Borg / GKE) / AWS (EKS) / Red Hat OpenShift  
**Domain:** Container Orchestration & Declarative Infrastructure Engine  
**Scenario:** A cloud deployment orchestrator validates and compiles Pod deployment specs. The builder must support dynamic, arbitrary container counts and optional storage mounts, while enforcing tree-level invariants (such as container port collision avoidance and CPU/Memory request-versus-limit bounds).

---

## 🎯 Requirements

### 1. Functional Requirements
* **Child Models & Builders:**
  * `ContainerSpec`: `name`, `image`, `containerPort`, `cpuRequest`, `cpuLimit`, `memoryRequestMb`, `memoryLimitMb`, `envVars`.
  * `VolumeSpec`: `volumeName`, `mountPath`, `readOnly`.
  * `ContainerSpecBuilder`: Chains `.name()`, `.image()`, `.port()`, `.resources()`, `.env()`, and ends with `.endContainer()` which appends to parent.
  * `VolumeSpecBuilder`: Chains `.name()`, `.mountPath()`, `.readOnly()`, and ends with `.endVolume()`.
* **Root Model & Builder (`PodDeploymentSpec` & `PodDeploymentSpecBuilder`):**
  * Properties: `podName`, `namespace`, `restartPolicy`, `containers`, `volumes`, `labels`.
  * Spawner methods: `container()` and `volume()`.
  * `String toYaml()`: Serializes the full tree into valid Kubernetes YAML.
* **Validation Invariants:**
  1. `podName` must not be blank.
  2. At least 1 container must be configured.
  3. `cpuLimit >= cpuRequest` and `memoryLimitMb >= memoryRequestMb`.
  4. `mountPath` must start with `"/"`.
  5. Port collision check: No two containers may declare identical `containerPort` values.
  6. Name collision check: Container names must be unique within the Pod.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What is the structural relationship?* | 1-to-many tree structure with optional nested lists $\rightarrow$ Hierarchical Back-Referencing Builder. |
| **2. Data Holders & Containers** | *How is state maintained?* | Child builders hold independent attributes and a reference to the parent builder. |
| **3. Abstract Class Check** | *Is inheritance needed?* | **No.** Distinct domain entities with dedicated builder workflows. |
| **4. Orchestrator** | *Where is the tree validated?* | Child invariants validate on `.end*()`; tree cross-cutting invariants (port collisions) validate on parent `.build()`. |

---

## 📐 Class Architecture (UML Diagram)

```text
+-------------------------------------------------------------------------------+
|                               PodDeploymentSpec                               |
+-------------------------------------------------------------------------------+
| - podName: String                                                             |
| - namespace: String                                                           |
| - restartPolicy: RestartPolicy                                                |
| - containers: List<ContainerSpec>                                             |
| - volumes: List<VolumeSpec>                                                   |
| - labels: Map<String, String>                                                 |
+-------------------------------------------------------------------------------+
| ~ PodDeploymentSpec(builder: PodDeploymentSpecBuilder)                        |
| + builder(): PodDeploymentSpecBuilder {static}                                |
| + toYaml(): String                                                            |
+-------------------------------------------------------------------------------+
                                    ▲
                                    : <<builds>>
                                    :
+-------------------------------------------------------------------------------+
|                           PodDeploymentSpecBuilder                            |
+-------------------------------------------------------------------------------+
| ~ podName: String                                                             |
| ~ namespace: String = "default"                                               |
| ~ restartPolicy: RestartPolicy = ALWAYS                                       |
| ~ containers: List<ContainerSpec>                                             |
| ~ volumes: List<VolumeSpec>                                                   |
| ~ labels: Map<String, String>                                                 |
+-------------------------------------------------------------------------------+
| + name(name: String): PodDeploymentSpecBuilder                                |
| + namespace(ns: String): PodDeploymentSpecBuilder                             |
| + restartPolicy(p: RestartPolicy): PodDeploymentSpecBuilder                   |
| + label(k: String, v: String): PodDeploymentSpecBuilder                       |
| + container(): ContainerSpecBuilder --------------------------+               |
| + volume(): VolumeSpecBuilder ------------------------------+ |               |
| + build(): PodDeploymentSpec                                | |               |
+-------------------------------------------------------------|-|---------------+
                                                              | |
           +--------------------------------------------------+ |
           |                                                    |
           v                                                    v
+---------------------------------------+  +------------------------------------+
|         ContainerSpecBuilder          |  |         VolumeSpecBuilder          |
+---------------------------------------+  +------------------------------------+
| - parent: PodDeploymentSpecBuilder    |  | - parent: PodDeploymentSpecBuilder |
| ~ name, image: String                 |  | ~ volumeName, mountPath: String    |
| ~ containerPort: int                  |  | ~ readOnly: boolean                |
| ~ cpuReq, cpuLimit: double            |  +------------------------------------+
| ~ memReq, memLimit: int               |  | + name(n): VolumeSpecBuilder       |
| ~ envVars: Map<String, String>        |  | + mountPath(p): VolumeSpecBuilder  |
+---------------------------------------+  | + readOnly(ro): VolumeSpecBuilder  |
| + name(n): ContainerSpecBuilder       |  | + endVolume():                     |
| + image(i): ContainerSpecBuilder      |  |     PodDeploymentSpecBuilder       |
| + port(p): ContainerSpecBuilder       |  +------------------------------------+
| + resources(...): ContainerSpecBuilder|
| + env(k, v): ContainerSpecBuilder     |
| + endContainer():                     |
|     PodDeploymentSpecBuilder          |
+---------------------------------------+