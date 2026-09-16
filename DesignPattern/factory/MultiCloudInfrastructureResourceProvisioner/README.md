# Low-Level Design (LLD): Multi-Cloud Infrastructure Resource Provisioner (Abstract Factory Pattern)

## 📌 Problem Overview
Modern enterprise DevOps and Infrastructure-as-Code (IaC) tooling must provision interconnected cloud infrastructure suites across multiple hyperscalers: **AWS**, **Azure**, and **Google Cloud Platform (GCP)**.

Mixing resources across distinct vendors within a single logical cluster (e.g., binding an AWS EC2 instance to an Azure Blob Storage container with a GCP Cloud SQL instance) violates VPC boundaries, breaks native IAM policies, and generates substantial egress transfer costs. We implement the **Abstract Factory Pattern** to guarantee that entire resource topologies (`ComputeInstance`, `StorageBucket`, `DatabaseInstance`) belong strictly to a single cloud provider family.

## 🏢 Company Context
**Company:** HashiCorp (Terraform Engine) / Google Cloud Platform (GCP) / AWS Organizations  
**Domain:** Cloud Resource Orchestration & Infrastructure-as-Code (IaC)  
**Scenario:** A cloud orchestration platform provisions isolated cloud stacks across different cloud providers. The provisioning engine must remain completely decoupled from provider-specific SDK details and ensure that every provisioned stack consists of compatible, single-provider resources.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Family Contracts (Interfaces):**
  * `ComputeInstance`: `void provision(String instanceName, int vCpu, int ramGb)`, `void terminate()`
  * `StorageBucket`: `void createBucket(String bucketName, String region)`, `boolean upload(String key, byte[] data)`
  * `DatabaseInstance`: `void deploy(String dbName, double storageGb)`, `String getConnectionString()`
* **Provider Product Families (Concrete Classes):**
  * **AWS:** `AwsComputeInstance`, `AwsStorageBucket`, `AwsDatabaseInstance`
  * **Azure:** `AzureComputeInstance`, `AzureStorageBucket`, `AzureDatabaseInstance`
  * **GCP:** `GcpComputeInstance`, `GcpStorageBucket`, `GcpDatabaseInstance`
* **Abstract Factory (`CloudResourceFactory`):**
  * `ComputeInstance createComputeInstance()`
  * `StorageBucket createStorageBucket()`
  * `DatabaseInstance createDatabaseInstance()`
* **Concrete Factories:**
  * `AwsResourceFactory`, `AzureResourceFactory`, `GcpResourceFactory`
* **Client Orchestrator (`CloudProvisioningService`):**
  * Receives `CloudResourceFactory` via constructor injection.
  * Coordinates complete stack provisioning, teardown, data upload, and database connection retrieval.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Cloud resource types (`Compute`, `Storage`, `Database`) vary across cloud providers (`AWS`, `Azure`, `GCP`) $\rightarrow$ Abstract Factory. |
| **2. Data Holders & Containers** | *What objects store state?* | Concrete resource classes retain instance identifiers, bucket names, and database connection strings. |
| **3. Abstract Class Check** | *Do resources share common code?* | **No.** Provider API mechanics and connection string protocols differ completely $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `CloudProvisioningService` acts as the client delegating creation to `CloudResourceFactory`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                           CloudResourceFactory                          |
               +-------------------------------------------------------------------------+
               | + createComputeInstance(): ComputeInstance                              |
               | + createStorageBucket(): StorageBucket                                  |
               | + createDatabaseInstance(): DatabaseInstance                            |
               +-------------------------------------------------------------------------+
                    △                               △                               △
                    |                               |                               |
    +---------------+---------------+ +-------------+-------------+ +---------------+---------------+
    |       AwsResourceFactory      | |    AzureResourceFactory   | |       GcpResourceFactory      |
    +-------------------------------+ +---------------------------+ +-------------------------------+
    | + createComputeInstance()     | | + createComputeInstance() | | + createComputeInstance()     |
    | + createStorageBucket()       | | + createStorageBucket()   | | + createStorageBucket()       |
    | + createDatabaseInstance()    | | + createDatabaseInstance()| | + createDatabaseInstance()    |
    +-------------------------------+ +---------------------------+ +-------------------------------+
                    :                               :                               :
   .................:...............................:...............................:
   :
   : <<instantiates provider resource families>>
   v
+===================================================================================================+
|                                     PRODUCT FAMILIES                                              |
+===================================================================================================+
|  <<interface>> ComputeInstance  |  <<interface>> StorageBucket |  <<interface>> DatabaseInstance  |
|  - provision(...): void         |  - createBucket(...): void   |  - deploy(...): void             |
|  - terminate(): void            |  - upload(...): boolean      |  - getConnectionString(): String |
|---------------------------------+------------------------------+----------------------------------|
|  * AwsComputeInstance           |  * AwsStorageBucket          |  * AwsDatabaseInstance           |
|  * AzureComputeInstance         |  * AzureStorageBucket        |  * AzureDatabaseInstance         |
|  * GcpComputeInstance           |  * GcpStorageBucket          |  * GcpDatabaseInstance           |
+===================================================================================================+
                                                    △
                                                    : <<holds & coordinates>>
                                                    :
               +-------------------------------------------------------------------------+
               |                        CloudProvisioningService                         |
               +-------------------------------------------------------------------------+
               | - computeInstance: ComputeInstance                                      |
               | - storageBucket: StorageBucket                                          |
               | - databaseInstance: DatabaseInstance                                    |
               +-------------------------------------------------------------------------+
               | + CloudProvisioningService(factory: CloudResourceFactory)               |
               | + deployCompleteStack(stackName: String, region: String): void          |
               | + terminateCompleteStack(stackName: String, region: String): void        |
               | + uploadDataToStorage(key: String, data: byte[]): void                  |
               | + getDatabaseConnectionString(): void                                   |
               +-------------------------------------------------------------------------+