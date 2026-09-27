# Low-Level Design (LLD): Distributed Execution Plan & MapReduce Job Spec Builder (Builder Pattern)

## 📌 Problem Overview
Distributed compute engines (such as Apache Spark, Apache Flink, and Databricks Runtime) execute multi-stage DAG analytics across thousands of worker nodes. 

Configuring these jobs via standard constructors or mutable bean objects leads to resource allocation mismatches, invalid URI targets, and recursive write errors. We apply the **Builder Pattern** to encapsulate complex compute allocation logic, enforce storage protocol invariants, and assemble an immutable `DistributedJobSpec`.

## 🏢 Company Context
**Company:** Databricks / Apache Spark / Apache Flink Engine  
**Domain:** Distributed DAG Execution Engines & Big Data Compute Specs  
**Scenario:** A distributed compute orchestrator schedules batch and streaming analytics jobs. Setting an invalid resource envelope (e.g., zero partitions, exceeding node core limits, or identical input/output paths) halts clusters mid-computation. The framework requires a fluent, immutable Job Spec Builder that strictly validates resources and storage paths prior to job submission.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Immutable Product (`DistributedJobSpec`):**
  * Properties: `jobId`, `jobName`, `inputPath`, `outputPath`, `numExecutors`, `executorCores`, `executorMemoryGb`, `shufflePartitions`, `checkPointDir`, `maxRetries`, `sparkProperties`.
  * Defensive copying for fine-grained property tuning via `Map.copyOf(...)`.
  * `String generateExecutionSummary()`: Produces an ASCII visualization detailing total allocated cluster cores and aggregate memory footprint.
* **Builder (`DistributedJobSpecBuilder`):**
  * Sensible defaults: `numExecutors = 1`, `executorCores = 1`, `executorMemoryGb = 1.0`, `shufflePartitions = 200`, `maxRetries = 3`.
  * Invariant enforcement in `.build()`:
    * Mandatory strings (`jobId`, `jobName`, `inputPath`, `outputPath`) must be non-null and non-blank.
    * Storage URIs (`inputPath`, `outputPath`, `checkpointDir`) must use supported schemes (`hdfs://`, `s3://`, `gs://`).
    * `outputPath` cannot match `inputPath` (prevent recursive write collision).
    * Compute resource boundaries: $1 \le \text{numExecutors}$, $1 \le \text{executorCores} \le 32$, $0.5 \le \text{executorMemoryGb} \le 128.0$, $1 \le \text{shufflePartitions}$, $0 \le \text{maxRetries} \le 10$.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What fields are required vs optional?* | Mandatory: `jobId`, `jobName`, `inputPath`, `outputPath`. Optional with defaults: `numExecutors`, `executorCores`, `executorMemoryGb`, `shufflePartitions`, `maxRetries`, `checkpointDir`, `sparkProperties`. |
| **2. Data Holders & Containers** | *How is state preserved?* | Accumulators inside `DistributedJobSpecBuilder`; immutable collections inside `DistributedJobSpec`. |
| **3. Abstract Class Check** | *Is inheritance needed?* | **No.** Single product hierarchy with fluent chaining $\rightarrow$ Standard Fluent Builder. |
| **4. Orchestrator** | *Where do invariants run?* | Method-level boundary checks during chaining, combined with relational path validation in `.build()`. |

---

## 📐 Class Architecture (UML Diagram)

```text
+-------------------------------------------------------------------------+
|                           DistributedJobSpec                            |
+-------------------------------------------------------------------------+
| - jobId: String                                                         |
| - jobName: String                                                       |
| - inputPath: String                                                     |
| - outputPath: String                                                    |
| - numExecutors: int                                                     |
| - executorCores: int                                                    |
| - executorMemoryGb: double                                              |
| - shufflePartitions: int                                                |
| - checkPointDir: String                                                 |
| - maxRetries: int                                                       |
| - sparkProperties: Map<String, String>                                  |
+-------------------------------------------------------------------------+
| ~ DistributedJobSpec(builder: DistributedJobSpecBuilder)                |
| + getJobId(): String                                                    |
| + getJobName(): String                                                  |
| + getInputPath(): String                                                |
| + getOutputPath(): String                                               |
| + getNumExecutors(): int                                                |
| + getExecutorCores(): int                                               |
| + getExecutorMemoryGb(): double                                         |
| + getShufflePartitions(): int                                           |
| + getCheckPointDir(): String                                            |
| + getMaxRetries(): int                                                  |
| + getSparkProperties(): Map<String, String>                             |
| + generateExecutionSummary(): String                                    |
+-------------------------------------------------------------------------+
                                    ▲
                                    : <<builds>>
                                    :
+-------------------------------------------------------------------------+
|                        DistributedJobSpecBuilder                        |
+-------------------------------------------------------------------------+
| ~ jobId: String                                                         |
| ~ jobName: String                                                       |
| ~ inputPath: String                                                     |
| ~ outputPath: String                                                    |
| ~ numExecutors: int = 1                                                 |
| ~ executorCores: int = 1                                                |
| ~ executorMemoryGb: double = 1.0                                        |
| ~ shufflePartitions: int = 200                                          |
| ~ checkPointDir: String                                                 |
| ~ maxRetries: int = 3                                                   |
| ~ sparkProperties: Map<String, String>                                  |
+-------------------------------------------------------------------------+
| + jobId(jobId: String): DistributedJobSpecBuilder                       |
| + jobName(jobName: String): DistributedJobSpecBuilder                   |
| + inputPath(inputPath: String): DistributedJobSpecBuilder               |
| + outputPath(outputPath: String): DistributedJobSpecBuilder             |
| + checkpointDir(checkpointDir: String): DistributedJobSpecBuilder       |
| + numExecutors(numExecutors: int): DistributedJobSpecBuilder           |
| + executorCores(executorCores: int): DistributedJobSpecBuilder         |
| + executorMemoryGb(memoryGb: double): DistributedJobSpecBuilder         |
| + shufflePartitions(partitions: int): DistributedJobSpecBuilder         |
| + maxRetries(retries: int): DistributedJobSpecBuilder                   |
| + sparkProperty(k: String, v: String): DistributedJobSpecBuilder        |
| + build(): DistributedJobSpec                                           |
| - validateStorageUri(path: String, field: String): void                 |
+-------------------------------------------------------------------------+