# Low-Level Design (LLD): Multi-Format Enterprise Document Publishing & Templating Engine

## 📌 Problem Overview
Modern multi-tenant document processing suites (e.g., Google Docs Core, Adobe Document Cloud, Office 365 Core Engine) face critical challenges when dynamically assembling, styling, and rendering high-volume documents across heterogeneous layout formats (specifically **PDF** and **HTML**):
1. **Incompatible Mixing Risk:** Accidental runtime pairing of mismatched format primitives (e.g., attempting to attach an HTML semantic header to a raw PDF vector coordinate body) leads to unrenderable output, parsing crashes, or corrupted print streams.
2. **Cold-Boot & Re-Parsing Latency:** Compiling complex document layouts, vector assets, stylesheets, and typography tables from scratch for every tenant request creates severe CPU bottlenecks.
3. **Complex Construction & Document Invariants:** Documents demand numerous structural configurations (page dimensions, orientations, margins, authors, titles, dynamic placeholders) with strict geometric invariants (e.g., total margins cannot swallow page dimensions: $2 \times \text{marginMm} < \min(\text{pageWidthMm}, \text{pageHeightMm})$).
4. **Platform Coordination & Quota Exhaustion:** The rendering runtime requires a centralized, thread-safe template registry and cluster quota governor to manage bounded capacity (e.g., a 15-job cap) and prevent cluster collapse under high concurrency.

To solve these constraints, this architecture synthesizes all **five Creational Design Patterns**:
* **Abstract Factory:** Enforces strict format-family pairing between header components and body components.
* **Builder:** Provides fluent, type-safe construction with fail-fast validation of layout dimensions and component compatibility.
* **Prototype:** Enables sub-millisecond document provisioning via defensive multi-tier deep cloning with zero mutation bleed into registered master templates.
* **Singleton:** Coordinates the central template registry and atomic render quota management using double-checked locking and reflection attack defense.
* **Factory Method:** Decouples client workflows from concrete factory instantiation via dynamic provisioner dispatch.

---

## 🏢 Company Context
* **Target Companies:** Google (Google Docs / Workspace Core), Adobe (Document Cloud / Acrobat Engine), Microsoft (Office 365 Core / Word Platform Team), Atlassian (Confluence Rendering Engine)
* **Domain:** High-Throughput Multi-Tenant Document Rendering & Templating Engines
* **Role Level:** SDE-2 / Senior Backend Engineer (Low-Level Design Round)

---

## 🎯 System Requirements

### 1. Functional Requirements
* **Family Consistency:** PDF headers must strictly pair with PDF bodies; HTML headers must strictly pair with HTML bodies. Incompatible cross-format pairings must be rejected fail-fast.
* **Invariant Enforcement:** Documents must reject configurations where margins exceed page boundaries ($2 \times \text{marginMm} \ge \min(\text{pageWidthMm}, \text{pageHeightMm})$), or where mandatory fields (`documentId`, `title`, `author`, `header`, `body`) are blank or null.
* **Zero Mutation Bleed:** Pre-compiled "Golden Archetypes" must be cached in memory and duplicated via deep cloning. Dynamic alterations made by tenant jobs (e.g., appending tenant vector logos, custom CSS stylesheets, dynamic coordinate charts, or tenant placeholders) must never corrupt the registered master template.
* **Dynamic Provider Resolution:** Client code must resolve concrete document component factories dynamically via format identifiers (`"PDF"`, `"HTML"`) without coupling directly to factory classes.
* **Centralized Quota Management:** The engine must enforce an atomic maximum rendering capacity (max 15 concurrent jobs) and allow immediate capacity recovery upon document release without deleting the master template.

### 2. Non-Functional Requirements
* **Thread Safety:** The singleton engine coordinator must guarantee thread safety under heavy concurrent access using double-checked locking (`volatile`) and lock-free thread-safe structures (`ConcurrentHashMap`, `AtomicInteger`).
* **Open-Closed Principle (OCP):** Supporting a new document format (e.g., Markdown, DocX) must only require introducing new product classes and a concrete factory without altering the builder or engine coordinator.
* **Security & Encapsulation:** The singleton coordinator must block instantiation attempts through Java reflection attacks.

---

## 🧠 4-Step Mental Algorithm Breakdown

When approaching complex multi-pattern synthesis problems under high-pressure interview conditions, follow this structured mental algorithm:

### Step 1: Core Problem & Invariant Decomposition
* Break down the problem statement into distinct operational boundaries:
    * **Family Compatibility Boundary:** Are there co-dependent object suites that must never be mixed? Identify product pairings (`HeaderComponent` + `BodyComponent` belonging strictly to PDF or HTML).
    * **Construction Complexity Boundary:** Are there multiple optional/mandatory parameters, telescoping constructors, or strict validation equations ($2 \times \text{margin} < \min(W, H)$)? Identify builder requirements.
    * **Latency & Lifecycle Boundary:** Is object instantiation from scratch too heavy, or is there a need to stamp out clones from a pre-warmed archetype? Identify prototype boundaries.
    * **Global Coordination Boundary:** Is there a single point of truth required for quotas, caches, or connections across all worker threads? Identify singleton boundaries.

### Step 2: Interface & Generics Boundary Definition
* Define the contracts cleanly before writing any business logic:
    * Establish the generic prototype contract `Prototype<T>` with explicit `shallowCopy()` and `deepCopy()`.
    * Establish the abstract products (`HeaderComponent`, `BodyComponent`) extending `Prototype<T>` to allow polymorphic deep copying through interfaces without concrete downcasting.
    * Decouple the abstract factory interface (`DocumentComponentFactory`) strictly to return product interfaces, keeping internal rendering data encapsulated within concrete products.
    * Implement `Document` as the aggregate root implementing `Prototype<Document>`, containing references to `HeaderComponent` and `BodyComponent`.

### Step 3: Layered Implementation & Defensive Invariant Enforcement
* Implement concrete classes with strict encapsulation and fail-fast invariants:
    * In `DocumentBuilder`, run `.validate()` inside `.build()` to verify geometric bounds, non-blank metadata, and matching header/body formats.
    * In prototype implementations, implement copy constructors that deeply copy all nested mutable references (e.g., `vectorLogos`, `vectorCoordinates`, `styleSheets`, `domElements`, `customPlaceholders`).
    * In `GlobalPublishingEngine`, apply double-checked locking with `volatile`, guard the private constructor against reflection, defensively clone documents on registration and acquisition, and manage active capacity atomically using `AtomicInteger`.

### Step 4: Trace & Validate with an Automated 5-Stage Test Harness
* Write an outside-in client driver using the **5-Stage Verification Formula**:
    * **Stage 1 (Negative Validation):** Prove that the builder rejects invalid margin geometry, blank titles/authors, cross-format mixing (PDF header + HTML body), and unknown factory providers.
    * **Stage 2 (Positive Assembly):** Assemble valid PDF and HTML master templates and register them into the engine.
    * **Stage 3 (Mutation Isolation):** Acquire a cloned document, mutate its logos, coordinate elements, and placeholders, and assert that the master template remains pristine.
    * **Stage 4 (Security & Concurrency):** Verify that reflection attacks are blocked and that multiple concurrent threads safely access the singleton instance without race conditions.
    * **Stage 5 (Quota & Recovery):** Saturate the 15-job render quota, assert that the 16th job fails-fast, release an active job, and verify subsequent acquisitions succeed.

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
|             HeaderComponent              | |        BodyComponent        | │
+------------------------------------------+ +-----------------------------+ │
| + getFormat() : String                   | | + getFormat() : String      | │
| + renderHeaderPreview() : void           | | + renderBodyPreview() : void| │
+------------------------------------------+ +-----------------------------+ │
       ▲                            ▲                 ▲               ▲      │
       │                            │                 │               │      │
+──────────────────────+    +──────────────────────+  │               │      │
|      PdfHeader       |    |      HtmlHeader      |  │               │      │
+──────────────────────+    +──────────────────────+  │               │      │
| - fontToken: String  |    | - semanticTitle: Str |  │               │      │
| - pageNumMarginMm:int|    | - styleSheets: List  |  │               │      │
| - vectorLogos: List  |    +──────────────────────+  │               │      │
+──────────────────────+    | + addStyleSheet()    |  │               │      │
| + addVectorLogo()    |    | + getStyleSheets()   |  │               │      │
| + getVectorLogos()   |    | + deepCopy()         |  │               │      │
| + deepCopy()         |    +──────────────────────+  │               │      │
+──────────────────────+                              │               │      │
                                                      │               │      │
                      +───────────────────────────────┴───────────────┤      │
                      │                                               │      │
             +──────────────────────+                        +──────────────────────+
             |       PdfBody        |                        |       HtmlBody       |
             +──────────────────────+                        +──────────────────────+
             | - embeddedFont: Str  |                        | - domElements: List  |
             | - vectorCoords: List |                        | - mediaQueries: List |
             +──────────────────────+                        +──────────────────────+
             | + addVectorCoord()   |                        | + addDomElement()    |
             | + getVectorCoords()  |                        | + getDomElements()   |
             | + deepCopy()         |                        | + deepCopy()         |
             +──────────────────────+                        +──────────────────────+
                                                                             ▲
                                                                             │
+-------------------------------------------------------------+              │
|                          Document                           |──────────────┘
+-------------------------------------------------------------+
| - documentId: String                                        |
| - title: String                                             |
| - author: String                                            |
| - pageWidthMm: int                                          |
| - pageHeightMm: int                                         |
| - marginMm: int                                             |
| - watermark: String                                         |
| - orientation: String                                       |
| - header: HeaderComponent                                   |
| - body: BodyComponent                                       |
| - metadata: Map<String, String>                             |
| - customPlaceholders: Map<String, String>                   |
+-------------------------------------------------------------+
| + shallowCopy() : Document                                  |
| + deepCopy() : Document                                     |
+-------------------------------------------------------------+
                               ▲
                               │ clones & manages
                               │
+-------------------------------------------------------------+
|                    GlobalPublishingEngine                   |
|                        <<Singleton>>                        |
+-------------------------------------------------------------+
| - instance: GlobalPublishingEngine                          |
| - templateRegistry: ConcurrentHashMap<String, Document>     |
| - activeRenderJobs: AtomicInteger                           |
| - maxRenderQuota: int = 15                                  |
+-------------------------------------------------------------+
| + getInstance() : GlobalPublishingEngine                    |
| + registerTemplate(key: String, doc: Document) : void       |
| + getRegisteredTemplate(key: String) : Document             |
| + acquireDocument(key: String) : Document                   |
| + releaseDocument(key: String) : void                       |
| + getActiveRenderJobs() : AtomicInteger                     |
| + getMaxRenderQuota() : int                                 |
+-------------------------------------------------------------+


===================================================================================
                                BUILDER HIERARCHY
===================================================================================

+-------------------------------------------------------------+
|                       DocumentBuilder                       |
+-------------------------------------------------------------+
| - documentId: String                                        |
| - title: String                                             |
| - author: String                                            |
| - pageWidthMm: int = 210                                     |
| - pageHeightMm: int = 297                                    |
| - marginMm: int = 15                                        |
| - watermark: String                                         |
| - orientation: String = "PORTRAIT"                          |
| - header: HeaderComponent                                   |
| - body: BodyComponent                                       |
| - metadata: Map<String, String>                             |
| - customPlaceholders: Map<String, String>                   |
+-------------------------------------------------------------+
| + documentId(id: String) : DocumentBuilder                  |
| + title(t: String) : DocumentBuilder                        |
| + author(a: String) : DocumentBuilder                       |
| + pageWidthMm(w: int) : DocumentBuilder                     |
| + pageHeightMm(h: int) : DocumentBuilder                    |
| + marginMm(m: int) : DocumentBuilder                        |
| + watermark(w: String) : DocumentBuilder                    |
| + orientation(o: String) : DocumentBuilder                  |
| + header(h: HeaderComponent) : DocumentBuilder              |
| + body(b: BodyComponent) : DocumentBuilder                  |
| + metadata(k: String, v: String) : DocumentBuilder          |
| + metadata(m: Map) : DocumentBuilder                        |
| + placeholders(k: String, v: String) : DocumentBuilder      |
| + placeholders(m: Map) : DocumentBuilder                    |
| - validate() : void                                         |
| + build() : Document                                        |
+-------------------------------------------------------------+
                               │ builds
                               ▼
                          [Document]


===================================================================================
                   ABSTRACT FACTORY & PROVISIONER HIERARCHY
===================================================================================

+-------------------------------------------------------------+
|                     DocumentProvisioner                     |
+-------------------------------------------------------------+
| + getFactory(format: String) : DocumentComponentFactory     |
+-------------------------------------------------------------+
                               │ creates
                               ▼
+-------------------------------------------------------------+
|                        <<interface>>                        |
|                  DocumentComponentFactory                   |
+-------------------------------------------------------------+
| + createHeader(titleOrFont: String, marginOrTag: Str) : Hdr |
| + createBody(fontOrQuery: String) : BodyComponent           |
+-------------------------------------------------------------+
                ▲                                ▲
                │                                │
+───────────────────────────────+ +───────────────────────────────+
|      PdfDocumentFactory       | |      HtmlDocumentFactory      |
+───────────────────────────────+ +───────────────────────────────+
| + createHeader() : PdfHeader  | | + createHeader() : HtmlHeader |
| + createBody()   : PdfBody    | | + createBody()   : HtmlBody   |
+───────────────────────────────+ +───────────────────────────────+