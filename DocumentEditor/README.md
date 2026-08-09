# Low-Level Design (LLD): Google Docs / Extensible Document Editor

## 📌 Problem Overview
The **Extensible Document Editor** is a core engine designed to manage structured, linear document elements (such as text, images, and tables) and export or persist the document in multiple formats. 

The primary objective of this exercise is to build an object-oriented document engine strictly adhering to **SOLID principles**, ensuring that adding new document element types or new export/saving formats requires **zero modifications** to existing core classes.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Document Structure (V1):**
  * A `Document` acts as a container holding a sequence of document elements.
  * `TextElement`: Stores raw text content.
  * `ImageElement`: Stores image file metadata (e.g., image path/URL).
* **Core Action:**
  * Support a save/export trigger on the document (`DocumentSaver.save(Document doc)`).
  * V1 must support exporting/saving the document as formatted **Plain Text output**.
* **Extensibility Requirements:**
  * Must allow adding new element types (e.g., `TableElement`, `VideoElement`, `CodeBlockElement`) without touching existing element logic or container classes.
  * Must allow adding new export formats (e.g., `JsonSaver`, `PdfSaver`, `HtmlSaver`) without altering element models or the `Document` container.

### 2. Non-Functional & Quality Attributes
* **SOLID Principles Compliance:**
  * **Single Responsibility Principle (SRP):** Separate data representation (elements), document collection management (`Document`), and persistence formatting (`DocumentSaver`).
  * **Open/Closed Principle (OCP):** Open for extending new elements and savers; closed for modifying existing source code.
  * **Liskov Substitution Principle (LSP):** Any `DocumentElement` can be rendered uniformly without runtime type checking (`instanceof`).
  * **Interface Segregation Principle (ISP):** Keep interfaces thin, focused, and purpose-driven.
  * **Dependency Inversion Principle (DIP):** High-level modules depend on abstractions (`DocumentElement`, `DocumentSaver`), not concrete storage or element details.

---

## 🧩 The 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What features will expand or change in future sprints?* | **1. Element Types** (`DocumentElement`)<br>**2. Export Formats** (`DocumentSaver`) $\rightarrow$ Creates **2 Interfaces**. |
| **2. Data Holders & Containers** | *What objects hold data vs. what groups them?* | **Data Models:** `TextElement` (holds text), `ImageElement` (holds path).<br>**Container:** `Document` (holds `List<DocumentElement>`). |
| **3. Abstract Class Check** | *Do concrete models share common fields AND code?* | **No.** `TextElement` and `ImageElement` share no attributes or code. A clean interface (`DocumentElement`) is preferred. |
| **4. Orchestrator** | *What class glues models and interfaces together?* | `Main` / Driver program wires dependencies and executes operations via abstractions. |

---

## 📐 Class Architecture (UML Diagram)

```text
       +-----------------------+
       |   <<interface>>       |
       |    DocumentSaver      |
       +-----------------------+
       | + save(doc: Document) |
       +-----------------------+
                   ^
                   | (Realization)
       +-----------+-----------+
       |     PlainTextSaver    |
       +-----------------------+
       | + save(doc: Document) |
       +-----------------------+
                   |
                   | (Uses / Depends on)
                   v
+------------------------------------+          +---------------------------+
|              Document              |          |       <<interface>>       |
+------------------------------------+          |      DocumentElement      |
| - elements: List<DocumentElement>  |1        *+---------------------------+
+------------------------------------+--------->| + render(): String        |
| + addElement(e: DocumentElement)   | (Comp)   +---------------------------+
| + removeElement(e: DocumentElement)|                        ^
| + getElements(): List<...>         |                        | (Realization)
+------------------------------------+            +-----------+-----------+
                                                  |                       |
                                      +-----------------------+ +-----------------------+
                                      |      TextElement      | |     ImageElement      |
                                      +-----------------------+ +-----------------------+
                                      | - text: String        | | - imagePath: String   |
                                      +-----------------------+ +-----------------------+
                                      | + render(): String    | | + render(): String    |
                                      +-----------------------+ +-----------------------+