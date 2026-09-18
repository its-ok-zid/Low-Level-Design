# Low-Level Design (LLD): Multi-Tenant Adaptive Media Pipeline & DRM Engine (Abstract Factory Pattern)

## 📌 Problem Overview
Enterprise digital streaming platforms (e.g., Netflix, Spotify, Prime Video) deliver secure 4K HDR media across heterogeneous client runtime environments: **Apple (iOS/macOS/tvOS)**, **Google (Android/Chromecast)**, and **Microsoft (Windows/Xbox)**.

Each host platform imposes tightly coupled, hardware-accelerated DRM subsystems, media decoders, and adaptive bitrate transports. Incompatible pairings—such as passing an Apple FairPlay session token to an Android MediaCodec hardware buffer—lead to cryptographic faults and security watchdog crashes. We apply the **Abstract Factory Pattern** to ensure that every provisioned playback pipeline (`DrmSessionManager`, `VideoDecoder`, `NetworkTransport`) is instantiated cohesively from a single platform ecosystem.

## 🏢 Company Context
**Company:** Netflix / Spotify / Apple Media Platform  
**Domain:** Cross-Ecosystem Digital Rights Management (DRM) & Video Processing Pipeline  
**Scenario:** A client playback engine streams protected media. The orchestrator must initialize network sessions, acquire DRM licenses, and decode frame segments polymorphically using injected factory abstractions without coupling high-level playback logic to vendor-specific SDKs.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Family Contracts (Interfaces):**
  * `DrmSessionManager`: `boolean acquireLicense(String contentId, String keyServerUrl)`, `byte[] decryptPayload(byte[] encryptedBytes)`
  * `VideoDecoder`: `void initializeDecoder(int width, int height, String colorSpace)`, `void decodeFrame(byte[] packetData)`
  * `NetworkTransport`: `void openStream(String cdnEndpointUrl)`, `byte[] pullChunk(int chunkSize)`
* **Ecosystem Product Suites:**
  * **Apple:** `FairPlayDrmSessionManager`, `VideoToolboxDecoder`, `HlsTransport`
  * **Google:** `WidevineDrmSessionManager`, `MediaCodecDecoder`, `DashQuicTransport`
  * **Microsoft:** `PlayReadyDrmSessionManager`, `MediaFoundationDecoder`, `SmoothStreamingTransport`
* **Abstract Factory (`MediaPipelineFactory`):**
  * `DrmSessionManager createDrmSessionManager()`
  * `VideoDecoder createVideoDecoder()`
  * `NetworkTransport createNetworkTransport()`
* **Concrete Factories:**
  * `AppleMediaPipelineFactory`, `GoogleMediaPipelineFactory`, `MicrosoftMediaPipelineFactory`
* **Client Orchestrator (`StreamingPlaybackEngine`):**
  * Injects `MediaPipelineFactory` abstraction.
  * `preparePlayback(String contentId, String keyServer, String cdnUrl)`: Opens stream, acquires DRM license, initializes decoder.
  * `playSegment(int segmentSize)`: Pulls chunk $\rightarrow$ decrypts payload $\rightarrow$ decodes frame.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | Media subsystem contracts (`DRM`, `Decoder`, `Transport`) vary across OS vendor platforms (`Apple`, `Google`, `Microsoft`) $\rightarrow$ Abstract Factory. |
| **2. Data Holders & Containers** | *What objects store state?* | Stream buffers, cryptographic tokens, resolution profiles, and transport endpoints. |
| **3. Abstract Class Check** | *Do subsystems share shared code?* | **No.** Apple AVFoundation, Google NDK MediaCodec, and Microsoft Media Foundation use mutually incompatible native calls $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `StreamingPlaybackEngine` coordinates network fetch $\rightarrow$ decrypt $\rightarrow$ decode data flow. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                           MediaPipelineFactory                          |
               +-------------------------------------------------------------------------+
               | + createDrmSessionManager(): DrmSessionManager                          |
               | + createVideoDecoder(): VideoDecoder                                    |
               | + createNetworkTransport(): NetworkTransport                            |
               +-------------------------------------------------------------------------+
                    △                               △                               △
                    |                               |                               |
    +---------------+---------------+ +-------------+-------------+ +---------------+---------------+
    |   AppleMediaPipelineFactory   | |  GoogleMediaPipelineFact. | |  MicrosoftMediaPipelineFact.  |
    +-------------------------------+ +---------------------------+ +-------------------------------+
    | + createDrmSessionManager()   | | + createDrmSessionManager() | | + createDrmSessionManager()   |
    | + createVideoDecoder()        | | + createVideoDecoder()      | | + createVideoDecoder()        |
    | + createNetworkTransport()    | | + createNetworkTransport()  | | + createNetworkTransport()    |
    +-------------------------------+ +---------------------------+ +-------------------------------+
                    :                               :                               :
   .................:...............................:...............................:
   :
   : <<instantiates ecosystem-locked playback suites>>
   v
+===================================================================================================+
|                                     PRODUCT FAMILIES                                              |
+===================================================================================================+
|  <<interface>> DrmSessionManager|  <<interface>> VideoDecoder    |  <<interface>> NetworkTransport|
|  - acquireLicense(...)          |  - initializeDecoder(...)      |  - openStream(cdnUrl)          |
|  - decryptPayload(...)          |  - decodeFrame(packet)         |  - pullChunk(size): byte[]     |
|---------------------------------+--------------------------------+--------------------------------|
|  * FairPlayDrmSessionManager    |  * VideoToolboxDecoder         |  * HlsTransport                |
|  * WidevineDrmSessionManager    |  * MediaCodecDecoder           |  * DashQuicTransport           |
|  * PlayReadyDrmSessionManager   |  * MediaFoundationDecoder      |  * SmoothStreamingTransport    |
+===================================================================================================+
                                                    △
                                                    : <<orchestrates playback pipeline>>
                                                    :
               +-------------------------------------------------------------------------+
               |                         StreamingPlaybackEngine                         |
               +-------------------------------------------------------------------------+
               | - drmSessionManager: DrmSessionManager                                  |
               | - videoDecoder: VideoDecoder                                            |
               | - networkTransport: NetworkTransport                                    |
               +-------------------------------------------------------------------------+
               | + StreamingPlaybackEngine(factory: MediaPipelineFactory)                |
               | + preparePlayback(contentId: String, keyServer: String, cdn: String)    |
               | + playSegment(segmentSize: int): void                                   |
               +-------------------------------------------------------------------------+