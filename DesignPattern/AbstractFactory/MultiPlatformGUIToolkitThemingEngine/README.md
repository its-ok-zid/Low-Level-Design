# Low-Level Design (LLD): Multi-Platform GUI Toolkit & Theming Engine (Abstract Factory Pattern)

## 📌 Problem Overview
Cross-platform desktop tools require consistent visual styling, input handling, and platform-specific accessibility pipelines across **Windows**, **macOS**, and **Linux (GTK)**. 

Directly mixing platform controls (such as pairing a Windows Win32 button with a macOS Aqua dropdown) introduces UI artifacts and native runtime crashes. We apply the **Abstract Factory Pattern** to guarantee that entire suites of related UI controls (`Button`, `Checkbox`, `TextField`) are instantiated consistently without client code hardcoding platform conditionals.

## 🏢 Company Context
**Company:** JetBrains (IntelliJ IDEA) / Microsoft (VS Code) / Figma Desktop Engine  
**Domain:** Cross-Platform Native Rendering & Design System Architecture  
**Scenario:** A cross-platform developer tool dynamically renders native UI controls across different host operating systems. The core application logic must assemble screens polymorphically using injected GUI factory abstractions without coupling to OS-specific widget implementations.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Product Families (Abstract Contracts):**
  * `Button`: `void render()`, `void onClick()`
  * `Checkbox`: `void render()`, `void toggle()`
  * `TextField`: `void render()`, `void setText(String text)`
* **Platform Families (Concrete Implementations):**
  * **Windows:** `WindowsButton`, `WindowsCheckbox`, `WindowsTextField`
  * **macOS:** `MacButton`, `MacCheckbox`, `MacTextField`
  * **Linux:** `LinuxButton`, `LinuxCheckbox`, `LinuxTextField`
* **Abstract Factory (`GUIFactory`):**
  * `Button createButton()`
  * `Checkbox createCheckbox()`
  * `TextField createTextField()`
* **Concrete Factories:**
  * `WindowsGUIFactory`, `MacGUIFactory`, `LinuxGUIFactory`
* **Client / Consumer (`GUIService`):**
  * Receives `GUIFactory` via constructor injection.
  * Instantiates and coordinates the entire suite of UI components.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What changes independently?* | UI widget types (`Button`, `Checkbox`, `TextField`) vary across OS families (`Windows`, `Mac`, `Linux`) $\rightarrow$ Abstract Factory. |
| **2. Data Holders & Containers** | *What objects store state?* | Concrete controls maintain platform-specific state (`toggle`, `text`). |
| **3. Abstract Class Check** | *Do controls share state or logic?* | **No.** Native rendering calls differ by platform $\rightarrow$ Pure interfaces. |
| **4. Orchestrator** | *What coordinates execution?* | `GUIService` consumes the injected `GUIFactory` abstraction. |

---

## 📐 Class Architecture (UML Diagram)

```text
               +-------------------------------------------------------------------------+
               |                              <<interface>>                              |
               |                               GUIFactory                                |
               +-------------------------------------------------------------------------+
               | + createButton(): Button                                                |
               | + createCheckbox(): Checkbox                                            |
               | + createTextField(): TextField                                          |
               +-------------------------------------------------------------------------+
                    △                               △                               △
                    |                               |                               |
    +---------------+---------------+ +-------------+-------------+ +---------------+---------------+
    |       WindowsGUIFactory       | |       MacGUIFactory       | |       LinuxGUIFactory         |
    +-------------------------------+ +---------------------------+ +-------------------------------+
    | + createButton(): Button      | | + createButton(): Button  | | + createButton(): Button      |
    | + createCheckbox(): Checkbox  | | + createCheckbox(): Chkbox| | + createCheckbox(): Checkbox  |
    | + createTextField(): TextField| | + createTextField(): TxtFld| | + createTextField(): TextField|
    +-------------------------------+ +---------------------------+ +-------------------------------+
                    :                               :                               :
   .................:...............................:...............................:
   :
   : <<instantiates respective platform product families>>
   v
+===================================================================================================+
|                                     PRODUCT FAMILIES                                              |
+===================================================================================================+
|  <<interface>> Button      |  <<interface>> Checkbox     |  <<interface>> TextField               |
|  - render(): void          |  - render(): void           |  - render(): void                      |
|  - onClick(): void         |  - toggle(): void           |  - setText(text: String): void         |
|----------------------------+-----------------------------+----------------------------------------|
|  * WindowsButton           |  * WindowsCheckbox          |  * WindowsTextField                    |
|  * MacButton               |  * MacCheckbox              |  * MacTextField                        |
|  * LinuxButton             |  * LinuxCheckbox            |  * LinuxTextField                      |
+===================================================================================================+
                                                    △
                                                    : <<holds & renders>>
                                                    :
               +-------------------------------------------------------------------------+
               |                                GUIService                               |
               +-------------------------------------------------------------------------+
               | - button: Button                                                        |
               | - checkbox: Checkbox                                                    |
               | - textField: TextField                                                  |
               +-------------------------------------------------------------------------+
               | + GUIService(factory: GUIFactory)                                       |
               | + renderUI(): void                                                      |
               | + clickButton(): void                                                   |
               | + toggleCheckbox(): void                                                |
               | + enterText(text: String): void                                         |
               +-------------------------------------------------------------------------+