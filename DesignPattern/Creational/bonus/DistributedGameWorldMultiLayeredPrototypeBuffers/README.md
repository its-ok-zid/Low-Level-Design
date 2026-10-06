# Low-Level Design (LLD): Distributed Game World Chunk & Physics Entity Spawner with Multi-Layered Prototype Buffers

## 📌 Problem Overview
In open-world spatial engines and MMOs (Unreal Engine, Riot, Roblox), game worlds are divided into 3D grid cubes called **World Chunks**. Each chunk contains massive vertex geometry meshes alongside mutable entities (NPCs, health bars, transforms, inventories).

Instantiating instances using standard `new` triggers garbage collection spikes and CPU frame drops. Conversely, naive deep cloning duplicates gigabytes of read-only polygon buffers.

We implement the **Hybrid Prototype Pattern with Flyweight Optimization**, combining the **Builder Pattern** for initial archetype declaration with **Tiered Prototype Cloning**:
1. **Shallow-shared:** Read-only heavy vertex arrays (`MeshGeometry`).
2. **Deep-isolated:** Dynamic coordinates (`Transform`), items (`Item`), active status effects, and entity lists.

## 🏢 Company Context
**Company:** Epic Games / Riot Games / Roblox Cloud Engine  
**Domain:** High-Frequency Game Engine State & Spatial Partitioning  
**Scenario:** A game server spins up isolated dungeon instances for thousands of concurrent player parties. The engine retrieves baseline chunk archetypes from `WorldChunkRegistry` and clones them without duplicating GPU geometry buffers or cross-contaminating gameplay state.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Layer 1: Shared Flyweight Buffer (`MeshGeometry`):**
  * Holds immutable vertex arrays (`byte[] polygonData`).
  * Never deeply copied; all entity clones share reference identity (`==`).
* **Layer 2: Mutable Physics & Inventory Components:**
  * `Transform`: Mutable 3D coordinates and rotations. Built via `TransformBuilder`.
  * `Item`: Mutable durability and buff maps.
* **Layer 3: Game Entity Prototype (`GameEntity`):**
  * Built using fluent `GameEntityBuilder`.
  * Deep copy clones `transform`, `inventory`, and `activeEffects`, while preserving `meshGeometry` reference.
* **Layer 4: Spatial Chunk Prototype (`WorldChunk`):**
  * Aggregates entities and environmental parameters. Deep copy clones every entity in its map.
* **Layer 5: Cache Registry (`WorldChunkRegistry`):**
  * Caches golden room/chunk archetypes and serves deep copies to active sessions.

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
          ▲                ▲               ▲               ▲
          |                |               |               |
          | <<implements>> | <<implements>>| <<implements>>| <<implements>>
          |                |               |               |
+-------------------+ +---------+ +-------------------+ +-------------------+
|     Transform     | |  Item   | |    GameEntity     | |    WorldChunk     |
+-------------------+ +---------+ +-------------------+ +-------------------+
| - x, y, z: double | | - id    | | - entityId: String| | - chunkId: String |
| - pitch, yaw, roll| | - dur   | | - health: double  | | - biomeType: Str  |
+-------------------+ | - buffs | | - meshGeometry:   | | - entities: Map   |
                      +---------+ |     MeshGeometry  | | - envParams: Map  |
                                  | - transform:      | +-------------------+
                                  |     Transform     |          ▲
                                  | - inventory: List |          |
                                  | - effects: Set    |          |
                                  +-------------------+          |
                                            |                    |
                                            | flyweight          | manages
                                            v                    |
                                  +-------------------+ +-------------------+
                                  |   MeshGeometry    | | WorldChunkRegistry|
                                  |    (Immutable)    | +-------------------+
                                  +-------------------+ | - registry: Map   |
                                  | - vertexCount     | +-------------------+
                                  | - polygonData     | | + registerChunk() |
                                  +-------------------+ | + getChunk()      |
                                                        +-------------------+