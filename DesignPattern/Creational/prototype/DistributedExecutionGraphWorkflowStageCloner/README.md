# Low-Level Design (LLD): Distributed Execution Graph & Workflow Stage Cloner (Prototype Pattern)

## 📌 Problem Overview
In workflow orchestrators (such as Apache Airflow, Temporal, and AWS Step Functions), pipelines are structured as Directed Acyclic Graphs (DAGs) of interdependent tasks.

Executing recurring workflow schedules or branch retries requires instantiating fresh execution graphs. Standard shallow or naive recursive cloning fails on graph topologies: in diamond dependencies ($A \rightarrow B, C \rightarrow D$), naive cloning duplicates common upstream nodes ($A$), corrupting dependencies and causing race conditions.

We implement the **Prototype Pattern with Graph Memoization**, passing a `visited` identity map during recursive cloning to preserve the DAG topology and memory bounds.

## 🏢 Company Context
**Company:** Apache Airflow / Temporal.io / AWS Step Functions  
**Domain:** Distributed Workflow Orchestration & Resilient DAG Execution  
**Scenario:** An orchestrator spawns thousands of concurrent pipeline runs from cached DAG definitions. Deep-cloned runs must isolate execution state (task status, retry attempts, environment variables) while preserving convergent upstream dependencies.

---

## 🎯 Requirements

### 1. Functional Requirements
* **`Prototype<T>` Contract:** Type-safe interface declaring `T shallowCopy()` and `T deepCopy()`.
* **Graph Node (`WorkflowTask`):**
  * Properties: `taskId`, `taskType`, `taskStatus`, `retryCount`, `environment`, `dependencies`.
  * `WorkflowTask deepCopy(Map<String, WorkflowTask> visited)`: Checks memoization map before instantiating to guarantee that shared upstream nodes are cloned exactly once.
* **Graph Container (`WorkflowDAG`):**
  * Properties: `dagId`, `dagName`, `tasks` (`Map<String, WorkflowTask>`), `globalVariables`.
  * Deep copy creates a shared `visited` map and recurses across the graph.
* **Registry (`WorkflowRegistry`):**
  * Caches golden DAG blueprints defensively and provisions isolated execution clones.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What is being cloned?* | Graph nodes (`WorkflowTask`) containing directed references (`dependencies`) to other nodes. |
| **2. Graph Traversal Strategy** | *How to handle diamond dependencies?* | Use a memoization map (`Map<String, WorkflowTask> visited`). Check if already cloned before creating. |
| **3. Execution State Isolation** | *What state mutates at runtime?* | `taskStatus`, `retryCount`, `environment` mutate per run; master template remains `PENDING`. |
| **4. Registry Management** | *How to protect golden templates?* | `WorkflowRegistry` saves `dag.deepCopy()` and serves `dag.deepCopy()`. |

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
               ▲                            ▲
               |                            |
               | <<implements>>             | <<implements>>
               |                            |
+------------------------------+  +-----------------------------------+
|         WorkflowTask         |  |            WorkflowDAG            |
+------------------------------+  +-----------------------------------+
| - taskId: String             |  | - dagId: String                   |
| - taskType: String           |  | - dagName: String                 |
| - taskStatus: TaskStatus     |  | - tasks: Map<String, WorkflowTask>|
| - retryCount: int            |  | - globalVars: Map<String, String> |
| - environment: Map           |  +-----------------------------------+
| - dependencies: List<Task>   |  | + shallowCopy(): WorkflowDAG      |
+------------------------------+  | + deepCopy(): WorkflowDAG         |
| + addDependency(task)        |  +-----------------------------------+
| + deepCopy(visitedMap): Task |                 ▲
| + shallowCopy(): Task        |                 |
| + deepCopy(): Task           |                 | manages cache
+------------------------------+                 |
               ▲                  +------------------------------+
               |                  |       WorkflowRegistry       |
               +--- 0..* depends  +------------------------------+
                                  | - registry: Map<String, DAG> |
                                  +------------------------------+
                                  | + registerWorkflow(id, dag)  |
                                  | + getWorkflow(id): DAG       |
                                  +------------------------------+