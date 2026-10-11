# UML Relationships & Notation Cheat Sheet — LLD Practice

A simple, quick-reference guide for identifying UML relationships, their notation, and their Java code manifestation.

## Complete UML Relationships & Notation Cheat Sheet

| **Relationship** | **Type Concept** | **Lifecycle / Coupling** | **UML Line & Arrow Notation** | **Excalidraw / Diagram Drawing Tip** | **Java Code Pattern** |
|---|---|---|---|---|---|
| **Inheritance (Generalization)** | **“Is-A”** (Class to Class) | Permanent specialization; subclass inherits from superclass | **Solid Line** + **Hollow Closed Triangle** (`──────▷`) | Point the triangle toward the **Superclass** | `public class Dog extends Animal {}` |
| **Realization (Implementation)** | **“Is-A”** (Class to Interface) | Contractual; class implements an interface | **Dashed/Dotted Line** + **Hollow Closed Triangle** (`- - - - ▷`) | Point the triangle toward the **Interface** | `public class PdfHeader implements HeaderComponent {}` |
| **Composition** | **“Has-A”** (Strong Whole-Part) | Strong ownership; part’s lifecycle is controlled by the whole | **Solid Line** + **Filled/Black Diamond** (`◆──────`) | Place the **Black Diamond** on the **Owner / Whole** side | Often created and owned internally: `this.engine = new Engine();` |
| **Aggregation** | **“Has-A”** (Weak Whole-Part) | Part can exist independently of the container | **Solid Line** + **Hollow/White Diamond** (`◇──────`) | Place the **Hollow Diamond** on the **Container / Whole** side | Often supplied from outside: `public Dept(List<Teacher> teachers)` |
| **Association** | **“Knows-A”** (Peer-to-Peer / Structural Link) | Objects are connected but can have independent lifecycles | **Solid Line** + optional open navigability arrow (`──────>`) or simple line (`──────`) | Arrow points toward the entity that can be navigated to / referenced | `private Doctor primaryDoctor;` |
| **Dependency** | **“Uses-A”** (Temporary / Transient) | Usually exists during a method call or operation; weaker than a retained structural link | **Dashed/Dotted Line** + Open Arrow (`- - - - >`) | Point toward the utility, factory, or parameter type being used | `void log(Payment payment) {}` |

> **Quick note:** A field alone does not automatically mean composition or aggregation. Choose the relationship based on ownership, sharing, and lifecycle.

---

## Quick Visual Reference for Our Document Publishing Engine

When drawing your Excalidraw diagram for this problem, keep the relationships grouped like this.

### Realization (`- - - - ▷`)

- `PdfDocumentFactory` `- - - - ▷` `DocumentComponentFactory`
- `PdfHeader` `- - - - ▷` `HeaderComponent`
- `HeaderComponent` `- - - - ▷` `Prototype<HeaderComponent>` *(if `HeaderComponent` implements this interface)*
- `Document` `- - - - ▷` `Prototype<Document>` *(if `Document` implements this interface)*

### Aggregation (`◇──────>`)

- `Document` `◇──────>` `HeaderComponent` *(Document aggregates headers passed in via the builder)*
- `Document` `◇──────>` `BodyComponent`

### Dependency (`- - - - >`)

- `DocumentProvisioner` `- - - - >` `DocumentComponentFactory` *(selects / obtains the factory)*
- `PdfDocumentFactory` `- - - - >` `PdfHeader`, `PdfBody` *(creates products)*
- `DocumentBuilder` `- - - - >` `Document` *(builds and returns it)*

### Association (`──────>`)

- `GlobalPublishingEngine` `──────>` `Document` *(holds document instances in its `ConcurrentHashMap` registry)*

---

## Tiny Memory Aid

- **Inheritance:** solid line + hollow triangle → superclass
- **Realization:** dashed line + hollow triangle → interface
- **Composition:** black diamond at the whole / owner
- **Aggregation:** white diamond at the whole / container
- **Association:** solid line → retained structural relationship
- **Dependency:** dashed line → temporary use

**Practice rule:** Match the notation to the relationship’s meaning, and verify the relationship against the actual Java code.
