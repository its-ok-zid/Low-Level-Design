# Low-Level Design (LLD): Cross-Platform Gaming Engine Physics & Audio Suite (Abstract Factory Pattern)

## 📌 Problem Overview
Modern AAA rendering and interactive compute platforms target heterogeneous hardware ecosystems: **DirectX (Windows/Xbox)**, **Metal (macOS/iOS)**, and **Vulkan (Linux/Android)**.

Each graphics/compute API requires closely coupled hardware abstraction layers for rigid body dynamics, hardware sound synthesis, and shader byte-compilation. Mixing low-level pipeline handles—such as passing a DirectX compute context to a Metal audio bus—causes unmapped memory faults, hardware resets, and graphics driver crashes. We utilize the **Abstract Factory Pattern** to ensure that when a game level initializes, all core subsystem engines (`PhysicsEngine`, `AudioDevice`, `PipelineCompiler`) belong strictly to the identical, cohesive graphics hardware family.

## 🏢 Company Context
**Company:** Epic Games (Unreal Engine Core) / Unity Technologies / Electronic Arts (Frostbite)  
**Domain:** Game Engine Subsystem Architecture & Multi-API Hardware Abstraction  
**Scenario:** A game engine core boots dynamically across runtime platforms. The client `GameWorld` must instantiate, link, and tick the entire subsystem pipeline without hardcoding native driver instantiation logic inside high-level game logic loops.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Family 1: Physics Engine (`PhysicsEngine`):**
  * `void initializeSimulation(float gravity, int maxSubSteps)`: Configures world physics bounds.
  * `void simulateStep(float deltaTime)`: Advances simulation frame ticks.
* **Product Family 2: Audio Device (`AudioDevice`):**
  * `void initAudioStream(int sampleRate, int channels)`: Initializes spatial hardware sound bus.
  * `void playSpatialSound(String soundClip, float x, float y, float z)`: Emits 3D positional audio.
* **Product Family 3: Pipeline Compiler (`PipelineCompiler`):**
  * `void compileShaders(String vertexSrc, String fragmentSrc)`: Compiles backend shader sources.
  * `String getBinaryFormat()`: Returns binary output format identifier.
* **Hardware Families (Concrete Products):**
  * **DirectX Family:** `DirectXPhysicsEngine`, `DirectXAudioDevice`, `DirectXPipelineCompiler` (`DXBC/DXIL`)
  * **Metal Family:** `MetalPhysicsEngine`, `MetalAudioDevice`, `MetalPipelineCompiler` (`AIR/MSL`)
  * **Vulkan Family:** `VulkanPhysicsEngine`, `VulkanAudioDevice`, `VulkanPipelineCompiler` (`SPIR-V`)
* **Abstract Factory (`GameEngineBackendFactory`):**
  * `PhysicsEngine createPhysicsEngine()`
  * `AudioDevice createAudioDevice()`
  * `PipelineCompiler createPipelineCompiler()`
* **Concrete Factories:**
  * `DirectXBackendFactory`, `MetalBackendFactory`, `VulkanBackendFactory`
* **Client Orchestrator (`GameWorld`):**
  * Receives `GameEngineBackendFactory` via constructor injection.
  * `loadScene(String sceneName, float gravity)`
  * `simulateEngineStep()`
  * `playDeviceSpatialSound()`
  * `getPipelineBinaryFormat()`

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Subsystem engines (`Physics`, `Audio`, `Pipeline`) vary across platform families (`DirectX`, `Metal`, `Vulkan`) $\rightarrow$ Abstract Factory. |
| **2. Data Holders & Containers** | *What objects store state?* | Simulation settings, device stream metadata, and compiled binary blobs. |
| **3. Abstract Class Check** | *Do subsystems share shared code?* | **No.** Low-level driver and native memory APIs are strictly incompatible $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `GameWorld` injects the abstract `GameEngineBackendFactory` to initialize and tick subsystems. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                        GameEngineBackendFactory                         |
               +-------------------------------------------------------------------------+
               | + createPhysicsEngine(): PhysicsEngine                                  |
               | + createAudioDevice(): AudioDevice                                      |
               | + createPipelineCompiler(): PipelineCompiler                            |
               +-------------------------------------------------------------------------+
                    △                               △                               △
                    |                               |                               |
    +---------------+---------------+ +-------------+-------------+ +---------------+---------------+
    |     DirectXBackendFactory     | |    MetalBackendFactory    | |     VulkanBackendFactory      |
    +-------------------------------+ +---------------------------+ +-------------------------------+
    | + createPhysicsEngine()       | | + createPhysicsEngine()   | | + createPhysicsEngine()       |
    | + createAudioDevice()         | | + createAudioDevice()     | | + createAudioDevice()         |
    | + createPipelineCompiler()    | | + createPipelineCompiler()| | + createPipelineCompiler()    |
    +-------------------------------+ +---------------------------+ +-------------------------------+
                    :                               :                               :
   .................:...............................:...............................:
   :
   : <<instantiates cohesive driver-level engine families>>
   v
+===================================================================================================+
|                                     PRODUCT FAMILIES                                              |
+===================================================================================================+
|  <<interface>> PhysicsEngine    |  <<interface>> AudioDevice   |  <<interface>> PipelineCompiler  |
|  - initializeSimulation(...)    |  - initAudioStream(...)      |  - compileShaders(...)           |
|  - simulateStep(dt)             |  - playSpatialSound(...)     |  - getBinaryFormat(): String     |
|---------------------------------+------------------------------+----------------------------------|
|  * DirectXPhysicsEngine         |  * DirectXAudioDevice        |  * DirectXPipelineCompiler       |
|  * MetalPhysicsEngine           |  * MetalAudioDevice          |  * MetalPipelineCompiler         |
|  * VulkanPhysicsEngine          |  * VulkanAudioDevice         |  * VulkanPipelineCompiler        |
+===================================================================================================+
                                                    △
                                                    : <<holds & orchestrates>>
                                                    :
               +-------------------------------------------------------------------------+
               |                                GameWorld                                |
               +-------------------------------------------------------------------------+
               | - physicsEngine: PhysicsEngine                                          |
               | - audioDevice: AudioDevice                                              |
               | - pipelineCompiler: PipelineCompiler                                    |
               +-------------------------------------------------------------------------+
               | + GameWorld(factory: GameEngineBackendFactory)                          |
               | + loadScene(sceneName: String, gravity: float): void                    |
               | + simulateEngineStep(): void                                            |
               | + playDeviceSpatialSound(): void                                        |
               | + getPipelineBinaryFormat(): void                                       |
               +-------------------------------------------------------------------------+