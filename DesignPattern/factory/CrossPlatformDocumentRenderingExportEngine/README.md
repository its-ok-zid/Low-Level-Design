# Low-Level Design (LLD): Cross-Platform Document Rendering & Export Engine (Factory Method Pattern)

## 📌 Problem Overview
An enterprise Content Management System (CMS) needs to compile and render dynamic digital documents into multiple standardized formats: **PDF**, **Word (DOCX)**, and **Markdown (MD)**. 

Each format requires specialized header compilation, encryption, metadata injection, and document character limits. Directly embedding format instantiation within the main document pipeline causes tight coupling, fragile dependencies, and violates the **Open/Closed Principle (OCP)**. We apply the **Factory Method Pattern** to separate compilation drivers from document authoring.

## 🏢 Company Context
**Company:** Adobe / Google Workspace / Canva Core Services  
**Domain:** Enterprise Document Infrastructure, Rich Text Rendering & Export  
**Scenario:** A cloud document workspace handles millions of export conversions daily across web, mobile, and print. Concrete compiler libraries must remain completely isolated from editing and distribution workflows so new formats (such as EPUB or HTML5) can be introduced without breaking the core publishing pipeline.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Contract (`DocumentExporter`):**
  * `void prepareHeader(String title, String author)`: Injects document metadata.
  * `boolean export(String documentId, String content)`: Compiles file format, checks limits, and outputs result.
* **Concrete Exporter Limits:**
  * `PdfDocumentExporter`: Maximum capacity = **100,000 characters**.
  * `WordDocumentExporter`: Maximum capacity = **50,000 characters**.
  * `MarkdownDocumentExporter`: Maximum capacity = **10,000 characters**.
* **Creator Contract (`DocumentExportPlanner`):**
  * `DocumentExporter createExporter()`: Factory method.
* **Concrete Creators:**
  * `PdfExportFactory`, `WordExportFactory`, `MarkdownExportFactory`.
* **Orchestrator (`DocumentExportService`):**
  * Injects `DocumentExportPlanner` abstraction, creates the exporter, prepares metadata, and executes export.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Document file types, compilation constraints, and serializers $\rightarrow$ Parallel Product and Creator hierarchies. |
| **2. Data Holders & Containers** | *What objects store state?* | Input metadata (`documentId`, `title`, `author`, `content`). |
| **3. Abstract Class Check** | *Do exporters share mutable state or identical logic?* | **No.** Character limits are static constants; header assembly differs by format $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `DocumentExportService` orchestrates execution via `DocumentExportPlanner`. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                            DocumentExporter                             |
               +-------------------------------------------------------------------------+
               | + prepareHeader(title: String, author: String): void                    |
               | + export(documentId: String, content: String): boolean                  |
               +-------------------------------------------------------------------------+
                                 △                               △
                                 :                               :
                 ................:                               :................
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|        PdfDocumentExporter        |                           |     MarkdownDocumentExporter      |
+-----------------------------------+                           +-----------------------------------+
| + prepareHeader(...): void        |                           | + prepareHeader(...): void        |
| + export(...): boolean            |                           | + export(...): boolean            |
+-----------------------------------+                           +-----------------------------------+
                 △                                                               △
                 :                                                               :
                 : <<instantiates>>                                              : <<instantiates>>
                 :                                                               :
+-----------------------------------+                           +-----------------------------------+
|          PdfExportFactory         |                           |       MarkdownExportFactory       |
+-----------------------------------+                           +-----------------------------------+
| + createExporter(): DocExporter   |                           | + createExporter(): DocExporter   |
+-----------------------------------+                           +-----------------------------------+
                 :                                                               :
                 :................                               ................:
                                 :                               :
                                 △                               △
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                          DocumentExportPlanner                          |
               +-------------------------------------------------------------------------+
               | + createExporter(): DocumentExporter                                    |
               +-------------------------------------------------------------------------+
                                                     △
                                                     :
                                                     : <<uses / depends on>>
                                                     :
               +-------------------------------------------------------------------------+
               |                          DocumentExportService                          |
               +-------------------------------------------------------------------------+
               | + executeExport(planner: DocumentExportPlanner, documentId: String,     |
               |                 title: String, author: String, content: String): void   |
               +-------------------------------------------------------------------------+