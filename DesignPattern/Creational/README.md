# Creational Design Patterns: Universal Identification & Implementation Cheat-Sheet

A rapid-lookup guide to diagnosing, choosing, and implementing Creational Design Patterns during Low-Level Design (LLD) interviews and system architecture design.

---

## ⚡ The 5-Second Pattern Identification Matrix

| Pattern | Problem Statement Clues & "Code Smells" | Primary Problem Solved | Real-World Analog |
| :--- | :--- | :--- | :--- |
| **Singleton** | • "Exactly one coordinator / pool across the application."<br>• Shared state, connection pool, rate limiter, license manager, metrics reporter.<br>• Thread-safe global access point required. | Uncontrolled duplicate instances causing race conditions, socket exhaustion, or inconsistent global state. | Global Database Connection Pool (`HikariCP`), Application Configuration Registry. |
| **Factory Method** | • "System processes different variations of an entity (e.g., payment methods, notification channels)."<br>• Creator class doesn't know the exact concrete class ahead of time.<br>• Avoids `switch/case` or `if/else` ladders that violate Open-Closed Principle (OCP). | Decouples object creation from business logic; defers instantiation to subclasses or specialized creators. | Document Parsers (`JSONParserFactory`, `XMLParserFactory`), Payment Gateway processors. |
| **Abstract Factory** | • "System must support multiple platforms/vendors/families of related objects."<br>• Products come in **suites** that must match (e.g., Mac Button + Mac Checkbox vs. Windows Button + Windows Checkbox).<br>• Prevents mixing incompatible products from different vendors. | Enforces family consistency without binding client code to concrete product implementations. | Cloud Resource Provisioner (AWS EC2 + S3 vs. Azure VM + Blob), Multi-OS UI Toolkits. |
| **Builder** | • A class has $>4$ constructor parameters, many optional, or multiple constructors (Telescoping Constructor smell).<br>• Complex invariants across fields (e.g., $A \le B$, required prefix).<br>• Immutable product creation without exposing setters.<br>• Hierarchical trees or strict step-by-step sequencing needed. | Parameter explosion, unclear constructor calls (`null, null, true, 5`), and inconsistent object construction. | Fluent SQL Query Builder (`JOOQ`), HTTP Request Specification, Kubernetes Pod Deployment Spec. |
| **Prototype** | • "Object creation via `new` is too slow / expensive (network fetch, disk read, DB query)."<br>• New objects differ only slightly from an existing baseline template.<br>• Spawning thousands of recurring instances dynamically.<br>• Graph structures requiring topology preservation. | Overhead of expensive re-computation or I/O initialization; duplicates state safely without constructor re-runs. | Game Entity Spawner (NPCs, bullets), VM / Container Image Cloner, Stateful Firewall Session Policy. |

---

## 🧠 Diagnostic Decision Tree

Ask these questions in sequence when evaluating an LLD scenario:

```text
Do I need to create a new object or manage object count?
 │
 ├── Exactly ONE instance must exist globally in the entire JVM/process?
 │    └──> SINGLETON (Double-Checked Locking or Bill Pugh Holder)
 │
 ├── Is instantiation from scratch (new) too heavy, or am I spawning clones from an existing archetype?
 │    └──> PROTOTYPE (Shallow vs. Deep Copy + Prototype Registry)
 │
 ├── Does the object have complex construction (>4 fields, step sequences, nested children, strict invariants)?
 │    └──> BUILDER
 │          ├── Mandatory sequential steps?              ──> Step Builder (Interface chaining)
 │          ├── Nested parent-child tree structure?       ──> Hierarchical Builder (Back-referencing)
 │          └── Flat object with optional parameters?    ──> Standard Fluent Builder
 │
 └── Am I decoupling creation logic so new product types can be added without modifying existing code?
      ├── Single product with polymorphic creation?      ──> FACTORY METHOD
      └── Entire family/suite of co-dependent products?  ──> ABSTRACT FACTORY
```

---

# 🧩 Structural Blueprints & Code Templates

> 🎯 **Purpose:** Quick, interview-ready implementation blueprints for the five creational patterns covered above.
>
> 💡 **Tip:** First identify the pattern using the decision tree. Then use the corresponding blueprint below as your implementation skeleton.

---

## ① Singleton — Thread-Safe Double-Checked Locking

### 🔍 Structure

```text
┌──────────────────────────────────────────────┐
│          CONNECTION POOL MANAGER             │
├──────────────────────────────────────────────┤
│  static volatile instance                   │
│              │                               │
│              ▼                               │
│       ┌───────────────┐                      │
│       │ getInstance() │                      │
│       └───────┬───────┘                      │
│               │                              │
│        instance == null?                     │
│          │          │                        │
│         YES         NO                       │
│          │          │                        │
│          ▼          ▼                        │
│   synchronized    RETURN INSTANCE            │
│          │                                   │
│          ▼                                   │
│   instance == null?                          │
│          │                                   │
│         YES                                  │
│          │                                   │
│          ▼                                   │
│     new Instance                             │
└──────────────────────────────────────────────┘
```

### 💻 Java Blueprint

```java
public class ConnectionPoolManager {

    // ─────────────────────────────────────────────
    // 1️⃣ SINGLE INSTANCE
    // ─────────────────────────────────────────────
    private static volatile ConnectionPoolManager instance;


    // ─────────────────────────────────────────────
    // 2️⃣ PRIVATE CONSTRUCTOR
    // ─────────────────────────────────────────────
    private ConnectionPoolManager() {

        // Guard against reflection instantiation
        if (instance != null) {
            throw new IllegalStateException(
                "Instance already exists."
            );
        }
    }


    // ─────────────────────────────────────────────
    // 3️⃣ THREAD-SAFE ACCESS POINT
    // ─────────────────────────────────────────────
    public static ConnectionPoolManager getInstance() {

        // First check — avoids synchronization
        // once the instance already exists.
        if (instance == null) {

            synchronized (ConnectionPoolManager.class) {

                // Second check — prevents two threads
                // from creating two instances.
                if (instance == null) {
                    instance = new ConnectionPoolManager();
                }
            }
        }

        return instance;
    }


    // ─────────────────────────────────────────────
    // 4️⃣ SERIALIZATION PROTECTION
    // ─────────────────────────────────────────────
    protected Object readResolve() {
        return getInstance();
    }
}
```

### 🧠 Key Interview Point

```text
volatile
   ↓
Prevents visibility + reordering problems

double-check
   ↓
Avoids synchronization overhead after initialization

private constructor
   ↓
Prevents normal external instantiation

readResolve()
   ↓
Prevents serialization from creating another instance
```

---

## ② Factory Method

### 🔍 Structure

```text
                 ┌──────────────────────┐
                 │ NotificationCreator  │
                 │──────────────────────│
                 │ notifyUser()         │
                 │ createNotification()│
                 └──────────┬───────────┘
                            │
                  Factory Method
                            │
             ┌──────────────┴──────────────┐
             ▼                             ▼
┌────────────────────────┐    ┌────────────────────────┐
│ SlackNotification      │    │ EmailNotification      │
│ Creator                │    │ Creator                │
└───────────┬────────────┘    └───────────┬────────────┘
            │                             │
            ▼                             ▼
     SlackNotification              EmailNotification
```

### 💻 Java Blueprint

```java
// ═══════════════════════════════════════════════════════
// 🟦 PRODUCT
// ═══════════════════════════════════════════════════════

public interface Notification {

    void send(String message);
}


// ═══════════════════════════════════════════════════════
// 🟪 CREATOR
// ═══════════════════════════════════════════════════════

public abstract class NotificationCreator {

    public void notifyUser(String message) {

        // Factory Method is called here.
        Notification notification = createNotification();

        notification.send(message);
    }

    protected abstract Notification createNotification();
}


// ═══════════════════════════════════════════════════════
// 🟩 CONCRETE CREATOR
// ═══════════════════════════════════════════════════════

public class SlackNotificationCreator
        extends NotificationCreator {

    @Override
    protected Notification createNotification() {

        return new SlackNotification();
    }
}
```

### 🔑 Core Idea

```text
Business Logic
      │
      ▼
notifyUser()
      │
      ▼
createNotification()
      │
      ├──────────► SlackNotification
      │
      ├──────────► EmailNotification
      │
      └──────────► SmsNotification
```

> **Factory Method = one product hierarchy + polymorphic creation.**

---

## ③ Abstract Factory

### 🔍 Structure

```text
                    ┌─────────────────────────┐
                    │ CloudProviderFactory    │
                    ├─────────────────────────┤
                    │ createCompute()         │
                    │ createStorage()         │
                    └────────────┬────────────┘
                                 │
              ┌──────────────────┴──────────────────┐
              ▼                                     ▼
    ┌───────────────────┐                 ┌───────────────────┐
    │ AwsCloudFactory   │                 │ GcpCloudFactory   │
    └─────────┬─────────┘                 └─────────┬─────────┘
              │                                     │
       ┌──────┴──────┐                       ┌──────┴──────┐
       ▼             ▼                       ▼             ▼
     EC2             S3               Compute Engine    Cloud Storage
```

### 💻 Java Blueprint

```java
// ═══════════════════════════════════════════════════════
// 🟦 ABSTRACT FACTORY
// ═══════════════════════════════════════════════════════

public interface CloudProviderFactory {

    ComputeInstance createCompute();

    StorageBucket createStorage();
}


// ═══════════════════════════════════════════════════════
// 🟨 AWS PRODUCT FAMILY
// ═══════════════════════════════════════════════════════

public class AwsCloudFactory
        implements CloudProviderFactory {

    @Override
    public ComputeInstance createCompute() {
        return new Ec2Instance();
    }

    @Override
    public StorageBucket createStorage() {
        return new S3Bucket();
    }
}


// ═══════════════════════════════════════════════════════
// 🟥 GCP PRODUCT FAMILY
// ═══════════════════════════════════════════════════════

public class GcpCloudFactory
        implements CloudProviderFactory {

    @Override
    public ComputeInstance createCompute() {
        return new ComputeEngineInstance();
    }

    @Override
    public StorageBucket createStorage() {
        return new CloudStorageBucket();
    }
}
```

### 🧠 Family Consistency

```text
AWS FACTORY
    │
    ├── Compute → EC2
    └── Storage → S3

              VS

GCP FACTORY
    │
    ├── Compute → Compute Engine
    └── Storage → Cloud Storage
```

> **Abstract Factory = a factory of related products that must work together.**

---

## ④ Builder Pattern

### Variant A — Standard Fluent Builder

### 🔍 Structure

```text
                 ┌─────────────────────┐
                 │   DatabaseConfig    │
                 ├─────────────────────┤
                 │ url                 │
                 │ poolSize            │
                 └──────────▲──────────┘
                            │
                          build()
                            │
                 ┌──────────┴──────────┐
                 │       Builder       │
                 ├─────────────────────┤
                 │ url(...)            │
                 │ poolSize(...)       │
                 │ build()             │
                 └─────────────────────┘
```

### 💻 Java Blueprint

```java
// ═══════════════════════════════════════════════════════
// 🟦 PRODUCT
// ═══════════════════════════════════════════════════════

public class DatabaseConfig {

    private final String url;
    private final int poolSize;


    // ─────────────────────────────────────────────
    // PRIVATE CONSTRUCTOR
    // ─────────────────────────────────────────────

    private DatabaseConfig(Builder builder) {

        this.url = builder.url;
        this.poolSize = builder.poolSize;
    }


    // ═════════════════════════════════════════════════
    // 🟪 BUILDER
    // ═════════════════════════════════════════════════

    public static class Builder {

        private String url;

        // Default value
        private int poolSize = 10;


        public Builder url(String url) {

            this.url = url;
            return this;
        }


        public Builder poolSize(int size) {

            this.poolSize = size;
            return this;
        }


        // ─────────────────────────────────────────
        // VALIDATION + OBJECT CREATION
        // ─────────────────────────────────────────

        public DatabaseConfig build() {

            if (url == null || url.isBlank()) {
                throw new IllegalStateException(
                    "URL required"
                );
            }

            if (poolSize <= 0) {
                throw new IllegalArgumentException(
                    "poolSize > 0 required"
                );
            }

            return new DatabaseConfig(this);
        }
    }
}
```

### ✨ Usage

```java
DatabaseConfig config =
        new DatabaseConfig.Builder()
                .url("jdbc:postgresql://localhost:5432/app")
                .poolSize(20)
                .build();
```

---

### Variant B — Hierarchical Back-Referencing Builder

### 🔍 Structure

```text
┌───────────────────────┐
│     ParentBuilder     │
│───────────────────────│
│ addChild()            │
│ build()               │
└───────────┬───────────┘
            │
            │ creates
            ▼
┌───────────────────────┐
│      ChildBuilder     │
│───────────────────────│
│ name(...)             │
│ endChild()            │
└───────────┬───────────┘
            │
            │ returns parent
            ▼
       ParentBuilder
```

### 💻 Java Blueprint

```java
// ═══════════════════════════════════════════════════════
// 🟦 PARENT BUILDER
// ═══════════════════════════════════════════════════════

public class ParentBuilder {

    final List<Child> children = new ArrayList<>();


    public ChildBuilder addChild() {

        // Pass the parent into the child builder.
        return new ChildBuilder(this);
    }


    public Parent build() {

        return new Parent(this);
    }
}


// ═══════════════════════════════════════════════════════
// 🟩 CHILD BUILDER
// ═══════════════════════════════════════════════════════

public class ChildBuilder {

    private final ParentBuilder parent;

    private String name;


    public ChildBuilder(ParentBuilder parent) {

        this.parent = parent;
    }


    public ChildBuilder name(String name) {

        this.name = name;
        return this;
    }


    public ParentBuilder endChild() {

        // Create child and attach it to parent.
        this.parent.children.add(
            new Child(this.name)
        );

        // Give control back to parent.
        return this.parent;
    }
}
```

### ✨ Usage

```java
Parent parent =
        new ParentBuilder()

            .addChild()
                .name("Child-A")
            .endChild()

            .addChild()
                .name("Child-B")
            .endChild()

            .build();
```

> **Best fit:** Kubernetes specifications, workflow trees, UI hierarchies, request structures, and other nested object graphs.

---

## ⑤ Prototype — Deep Copy + Registry

### 🔍 Structure

```text
                     ┌─────────────────────┐
                     │ Prototype Registry  │
                     ├─────────────────────┤
                     │ "orc" → archetype   │
                     │ "goblin" → archetype│
                     └──────────┬──────────┘
                                │
                           deepCopy()
                                │
               ┌────────────────┼────────────────┐
               ▼                ▼                ▼
           Instance 1       Instance 2       Instance 3
```

### 💻 Java Blueprint

```java
// ═══════════════════════════════════════════════════════
// 🟦 PROTOTYPE CONTRACT
// ═══════════════════════════════════════════════════════

public interface Prototype<T> {

    T shallowCopy();

    T deepCopy();
}


// ═══════════════════════════════════════════════════════
// 🟪 PROTOTYPE REGISTRY
// ═══════════════════════════════════════════════════════

public class PrototypeRegistry<T extends Prototype<T>> {

    private final Map<String, T> cache =
            new HashMap<>();


    // ─────────────────────────────────────────────
    // REGISTER ARCHETYPE
    // ─────────────────────────────────────────────

    public void register(String key, T archetype) {

        // Defensive copy:
        // registry does not keep the caller's object.
        cache.put(
            key,
            archetype.deepCopy()
        );
    }


    // ─────────────────────────────────────────────
    // CREATE NEW INSTANCE
    // ─────────────────────────────────────────────

    public T get(String key) {

        T archetype = cache.get(key);

        if (archetype == null) {

            throw new IllegalArgumentException(
                "Key not found: " + key
            );
        }

        // Return a fresh independent copy.
        return archetype.deepCopy();
    }
}
```

### 🧠 Copy Strategy

```text
             PROTOTYPE
                 │
        ┌────────┴────────┐
        ▼                 ▼
   shallowCopy()      deepCopy()
        │                 │
        ▼                 ▼
 Shares references    Copies mutable
 to nested objects    nested objects
```

> **Prototype = create new objects by copying an existing archetype instead of reconstructing them from scratch.**

---

# 🤝 When Patterns Fuse — Real-World Hybrids

> 🚀 In production LLD, patterns rarely exist completely in isolation. The strongest designs often combine two or more patterns.

---

## 1️⃣ Prototype + Builder

```text
        ┌───────────────────┐
        │      Builder      │
        │ Complex Archetype │
        └─────────┬─────────┘
                  │
                build()
                  │
                  ▼
        ┌───────────────────┐
        │     Archetype     │
        └─────────┬─────────┘
                  │
               register
                  │
                  ▼
        ┌───────────────────┐
        │ PrototypeRegistry │
        └─────────┬─────────┘
                  │
              deepCopy()
                  │
        ┌─────────┼─────────┐
        ▼         ▼         ▼
      Clone 1   Clone 2   Clone 3
```

### Why combine them?

- **Builder** constructs the complex initial archetype.
- Builder handles readable configuration and validation.
- The completed object is registered as a prototype.
- **Prototype** creates many runtime copies efficiently.
- Useful for game entities, workflows, templates, and recurring configurations.

---

## 2️⃣ Abstract Factory + Singleton

```text
             ┌─────────────────────┐
             │  Application Code   │
             └──────────┬──────────┘
                        │
                        ▼
             ┌─────────────────────┐
             │   Factory Instance  │
             │     Singleton       │
             └──────────┬──────────┘
                        │
              ┌─────────┴─────────┐
              ▼                   ▼
         createCompute()     createStorage()
              │                   │
              ▼                   ▼
             EC2                  S3
```

Concrete factories such as `AwsCloudFactory` or `MacUiFactory` often have no internal mutable state. In such cases, they can be implemented as Singletons to avoid unnecessary factory instances.

---

## 3️⃣ Builder + Factory

```text
             ┌────────────────────┐
             │       Factory      │
             └─────────┬──────────┘
                       │
             createHttpRequestBuilder()
                       │
                       ▼
             ┌────────────────────┐
             │      Builder       │
             ├────────────────────┤
             │ url(...)           │
             │ header(...)        │
             │ timeout(...)       │
             └─────────┬──────────┘
                       │
                     build()
                       │
                       ▼
             ┌────────────────────┐
             │    HttpRequest     │
             └────────────────────┘
```

A Factory can return a pre-configured Builder, allowing the client to customize only the optional parameters before calling `build()`.

---

## 4️⃣ Prototype + Flyweight

### 🧠 Memory Optimization Model

```text
                  PROTOTYPE CLONE
                         │
             ┌───────────┴───────────┐
             │                       │
             ▼                       ▼
       MUTABLE STATE           IMMUTABLE STATE
             │                       │
             ▼                       ▼
        DEEP COPY              SHARED REFERENCE
             │                       │
     ┌───────┼───────┐        ┌──────┴─────────┐
     ▼       ▼       ▼        ▼                ▼
  Health  Inventory Transform  Mesh         Static Data
                              │
                              ▼
                         Flyweight
```

### What gets copied?

```text
DEEP COPY
─────────
✓ Transforms
✓ Health
✓ Inventories
✓ Counters
✓ Other mutable runtime state


SHARED / FLYWEIGHT
──────────────────
✓ 3D geometry meshes
✓ Static bytecode
✓ Compiled regex tables
✓ Other heavy immutable buffers
```

> **Hybrid principle:** Deep-copy what changes per instance; share what is immutable and expensive.

---

# 🏁 Quick Pattern + Hybrid Cheat Sheet

```text
┌──────────────────────┬──────────────────────────────────────┐
│ Pattern              │ Think                                │
├──────────────────────┼──────────────────────────────────────┤
│ Singleton            │ "Only ONE instance"                  │
│ Factory Method       │ "Which ONE product?"                 │
│ Abstract Factory     │ "Which PRODUCT FAMILY?"              │
│ Builder              │ "How do I BUILD this complex object?"│
│ Prototype            │ "Can I COPY an existing object?"     │
├──────────────────────┼──────────────────────────────────────┤
│ Builder + Prototype  │ Build once → clone many              │
│ Factory + Builder    │ Choose builder → customize → build   │
│ Factory + Singleton  │ One reusable stateless factory       │
│ Prototype + Flyweight│ Copy mutable → share immutable       │
└──────────────────────┴──────────────────────────────────────┘
```
