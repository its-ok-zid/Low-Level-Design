# UML Relationships & Java Mapping --- LLD Practice Guide

> A practical cheat sheet for identifying object-oriented relationships,
> drawing their UML notation, and translating them into Java code.
> Includes a worked relationship map for a Document Publishing Engine.

## Table of Contents

-   [How to Read a Relationship](#how-to-read-a-relationship)
-   [Complete UML Relationship Cheat
    Sheet](#complete-uml-relationship-cheat-sheet)
-   [Java Examples by Relationship](#java-examples-by-relationship)
-   [Document Publishing Engine: Relationship
    Map](#document-publishing-engine-relationship-map)
-   [Excalidraw Drawing Checklist](#excalidraw-drawing-checklist)
-   [Common Interview Traps](#common-interview-traps)
-   [Practice Checklist](#practice-checklist)

------------------------------------------------------------------------

## How to Read a Relationship

When reviewing a class diagram, ask:

1.  **What is the semantic relationship?** Is it *is-a*, *has-a*,
    *knows-a*, or *uses-a*?
2.  **Who owns the reference or lifecycle?** Does one object own
    another, merely reference it, or use it temporarily?
3.  **What does the Java code actually do?** Look for `extends`,
    `implements`, fields, constructor parameters, local variables, and
    method calls.
4.  **Which UML notation communicates that relationship?** Check the
    line style, arrowhead, and diamond placement.

> **Important:** UML relationships describe design intent, not just
> syntax. A field alone does not prove composition or aggregation;
> inspect ownership, lifecycle, and how the referenced object is created
> and shared.

## Complete UML Relationship Cheat Sheet

  -----------------------------------------------------------------------------------------------------------------------------------------------------
  Relationship         Concept       Lifecycle / Coupling     UML notation            Drawing tip     Typical Java manifestation
  -------------------- ------------- ------------------------ ----------------------- --------------- -------------------------------------------------
  **Inheritance        **Is-A** ---  Specialized class        Solid line with hollow, Triangle points `class Dog extends Animal {}`
  (Generalization)**   class to      inherits behavior and    closed triangle         toward the more 
                       class         contract from its        pointing to the         general class   
                                     superclass               superclass                              

  **Realization**      **Is-A /      Class promises to        Dashed line with        Triangle points `class PdfHeader implements HeaderComponent {}`
                       Implements    implement the interface  hollow, closed triangle toward the      
                       Contract**    contract                 pointing to the         interface       
                       --- class to                           interface                               
                       interface                                                                      

  **Composition**      **Strong      Strong ownership; the    Solid line with a       Put the black   Often internally created and exclusively owned,
                       Has-A** ---   part's lifecycle is      **filled diamond at the diamond on the  e.g. `this.engine = new Engine();`
                       whole to part conceptually controlled  whole/owner**           owner side      
                                     by the whole                                                     

  **Aggregation**      **Weak        Shared or independent    Solid line with a       Put the white   Often supplied from outside,
                       Has-A** ---   part can outlive the     **hollow diamond at the diamond on the  e.g. `Department(List<Teacher> teachers)`
                       whole to part aggregate                aggregate/container**   container side  

  **Association**      **Knows-A**   Structural link; objects Solid line; optional    Arrow indicates `private Doctor primaryDoctor;`
                       --- object    can have independent     navigability arrow      navigability,   
                       references    lifecycles               toward the referenced   not necessarily 
                       another                                type                    control flow    

  **Dependency**       **Uses-A**    A change to the supplier Dashed line with        Point toward    `void log(Payment payment) { ... }`
                       --- temporary may affect the client;   open/simple arrow       the class being 
                       use           commonly                 pointing to the         used            
                                     method/local/parameter   supplier                                
                                     use                                                              
  -----------------------------------------------------------------------------------------------------------------------------------------------------

### Read the notation correctly

-   **Line style:** solid commonly indicates structural relationships;
    dashed indicates realization or dependency.
-   **Triangle:** a hollow, closed triangle indicates generalization or
    realization.
-   **Diamond:** marks the **whole/aggregate** end, not the part end.
-   **Open arrowhead:** can indicate navigability for an association or
    the direction of a dependency.
-   **Multiplicity:** labels such as `1`, `0..1`, `*`, and `1..*` show
    how many objects can participate.

## Java Examples by Relationship

These examples are schematic and focus on the relationship, not a
complete application.

### 1. Inheritance --- `extends`

``` java
class Animal {
    void eat() {}
}
class Dog extends Animal {
    void bark() {}
}
```

`Dog` **is an** `Animal`. Draw a solid line from `Dog` to `Animal` with
the hollow triangle at `Animal`.

### 2. Realization --- `implements`

``` java
interface HeaderComponent {
    void render();
}
class PdfHeader implements HeaderComponent {
    @Override
    public void render() {
        System.out.println("Rendering PDF header");
    }
}
```

`PdfHeader` realizes the `HeaderComponent` contract. Use a dashed line
with the hollow triangle at the interface.

### 3. Composition --- strong ownership

``` java
class Engine {}
class Car {
    private final Engine engine;
    Car() {
        this.engine = new Engine();
    }
}
```

This commonly illustrates composition because `Car` creates and owns its
`Engine`. In real systems, lifecycle semantics matter more than the
`new` keyword alone.

### 4. Aggregation --- weak whole-part relationship

``` java
class Teacher {}
class Department {
    private final List<Teacher> teachers;
    Department(List<Teacher> teachers) {
        this.teachers = new ArrayList<>(teachers);
    }
}
```

This models independently existing teachers grouped by a department.
Copying the list protects its structure but does not copy the `Teacher`
objects.

### 5. Association --- a structural reference

``` java
class Doctor {}
class Patient {
    private Doctor primaryDoctor;
    Patient(Doctor primaryDoctor) {
        this.primaryDoctor = primaryDoctor;
    }
}
```

`Patient` retains a reference to a `Doctor`. The domain meaning depends
on ownership and lifecycle rules.

### 6. Dependency --- temporary use

``` java
class Payment {}
class PaymentLogger {
    void log(Payment payment) {
        System.out.println("Logging payment");
    }
}
```

`PaymentLogger` uses `Payment` as a method parameter and need not retain
it as a field.

## Document Publishing Engine: Relationship Map

Use this map as a guide when drawing the Document Publishing Engine in
Excalidraw. Confirm each relationship against the actual declarations in
your code.

### Realization

Use a **dashed line + hollow triangle** pointing toward the interface:

-   `PdfDocumentFactory` → `DocumentComponentFactory`
-   `PdfHeader` → `HeaderComponent`
-   `HeaderComponent` → `Prototype<HeaderComponent>` *(only if it
    implements this interface)*
-   `Document` → `Prototype<Document>` *(only if it implements this
    interface)*

Do not draw realization solely because two types are conceptually
related; the implementation declaration must support it.

### Aggregation

Use a **solid line + hollow diamond at `Document`**:

-   `Document` ◇→ `HeaderComponent`
-   `Document` ◇→ `BodyComponent`

This is appropriate if the document groups externally supplied
components that have independent lifecycles. If it controls the
components' lifecycles, composition may better express the design.

### Dependency

Use a **dashed line + open arrow** pointing toward the used type:

-   `DocumentProvisioner` → `DocumentComponentFactory` --- if it selects
    or uses the factory without retaining a structural reference.
-   `PdfDocumentFactory` → `PdfHeader` and `PdfBody` --- if it
    instantiates these products.
-   `DocumentBuilder` → `Document` --- if it creates and returns the
    document.

If a class also stores the target in a field, the design may have an
association too. Avoid duplicating lines without a reason.

### Association

Use a **solid line**, optionally with a navigability arrow:

-   `GlobalPublishingEngine` → `Document` --- if it retains document
    references in a `ConcurrentHashMap` registry.

A registry that retains references generally indicates a structural
association, not merely temporary dependency.

## Excalidraw Drawing Checklist

1.  Put the **source/client** at the beginning of the line.
2.  Point inheritance and realization triangles toward the
    **superclass/interface**.
3.  Place composition and aggregation diamonds at the
    **whole/owner/container** end.
4.  Use dashed lines for realization and dependency; distinguish them by
    the arrowhead.
5.  Add multiplicities where useful, such as `1` document to `0..*`
    components.
6.  Label relationships when useful: `contains`, `creates`, `uses`, or
    `registers`.
7.  Check actual Java declarations and lifecycle rules before finalizing
    the UML.
8.  Keep the diagram readable and avoid redundant lines.

### Quick notation memory aid

-   `extends` → **solid + hollow triangle**
-   `implements` → **dashed + hollow triangle**
-   strong whole-part → **filled diamond at whole**
-   weak whole-part → **hollow diamond at aggregate**
-   retained reference → **solid association**
-   temporary use → **dashed dependency**

## Common Interview Traps

-   **A field does not automatically mean composition.** It establishes
    a reference; ownership and lifecycle determine whether composition
    is justified.
-   **Constructor injection does not automatically mean aggregation.**
    The injected object might still be exclusively owned by the
    receiver.
-   **A `new` expression does not prove composition by itself.**
    Consider sharing, exposure, and independent management.
-   **Association is not dependency.** Association is structural;
    dependency is commonly temporary use.
-   **Do not confuse realization and inheritance.** A class extends a
    class; a class realizes an interface.
-   **The diamond belongs at the whole end.** Putting it on the part
    reverses the meaning.
-   **Do not infer UML from class names alone.** Confirm fields, method
    signatures, ownership, and lifecycle.

## Practice Checklist

-   [ ] Identify every `extends` relationship and its generalization
    arrow.
-   [ ] Identify every `implements` relationship and its realization
    arrow.
-   [ ] Find fields that create associations.
-   [ ] Determine whether each whole-part relationship is composition or
    aggregation.
-   [ ] Identify method parameters, local variables, and factory usage
    that create dependencies.
-   [ ] Add multiplicities and navigability where useful.
-   [ ] Verify the diagram reflects the Java code and intended
    lifecycle.

**Final mental model:** *Class syntax tells you what is declared; object
ownership and lifecycle tell you which relationship the design means.*
