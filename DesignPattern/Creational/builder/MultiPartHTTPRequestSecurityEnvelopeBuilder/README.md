# Low-Level Design (LLD): Multi-Part HTTP/2 Request & Security Envelope Builder (Builder Pattern)

## 📌 Problem Overview
Constructing network requests across microservices via raw strings or telescoping constructors leads to protocol errors, payload mismatches (e.g., sending bodies on `GET` requests), missing HMAC headers, and thread-safety bugs from mutable state. 

We apply the **Builder Pattern** to build an immutable `HttpRequest` representation with centralized validation in `.build()`, dynamic query parameter resolution, and HTTP/2 wire protocol serialization.

## 🏢 Company Context
**Company:** Cloudflare / Stripe / Okta Core Identity  
**Domain:** Secure API Gateway Client SDK & HTTP Wire Protocol Serialization  
**Scenario:** A financial API gateway client SDK constructs signed, multi-part HTTP/2 requests targeting payment processing microservices. Requests require validation for scheme protocols, HTTP method invariants, body restrictions, timeout ranges, and cryptographic HMAC signatures before serialization.

---

## 🎯 Requirements

### 1. Functional Requirements
* **Immutable Product (`HttpRequest`):**
  * Properties: `url`, `httpMethod`, `headers`, `queryParams`, `body`, `timeoutMs`, `isSecured`, `hmacSignature`.
  * Defensive copying for headers and query parameters via `Map.copyOf(...)`.
  * `String toWireFormat()`: Formats into HTTP/2 pseudo-header wire format.
* **Builder (`HttpRequestBuilder`):**
  * Fluent chaining for `url`, `method`, `header`, `queryParam`, `body`, `timeout`, `withSecurity`.
  * Invariant enforcement in `.build()`:
    * Must have valid URL starting with `http://` or `https://`.
    * Must specify `HttpMethod`.
    * `GET` and `DELETE` requests must not contain a request body.
    * Timeout bounded between $1$ and $60000$ ms.
    * If `isSecured` is active, `hmacSignature` must not be blank.

---

## 🧩 4-Step Mental Algorithm Breakdown

| Step | Question | Analysis & Decision |
| :--- | :--- | :--- |
| **1. Axes of Variation** | *What fields are required vs optional?* | Mandatory: `url`, `method`. Optional: `headers`, `queryParams`, `body`, `timeout`. Conditional: `hmacSignature` depends on `isSecured`. |
| **2. Data Holders & Containers** | *How is state preserved?* | Accumulators inside `HttpRequestBuilder`; immutable maps inside `HttpRequest`. |
| **3. Abstract Class Check** | *Is inheritance needed?* | **No.** Single product hierarchy with fluent accumulator methods $\rightarrow$ Standard Builder. |
| **4. Orchestrator** | *Where do invariants run?* | Terminal `.build()` enforces constraints before calling constructor. |

---

## 📐 Class Architecture (UML Diagram)

```text
+-----------------------+
|       <<enum>>        |
|      HttpMethod       |
+-----------------------+
| GET                   |
| POST                  |
| PUT                   |
| DELETE                |
| PATCH                 |
+-----------------------+
           ▲
           |
+-------------------------------------------------------------------------+
|                               HttpRequest                               |
+-------------------------------------------------------------------------+
| - url: String                                                           |
| - httpMethod: HttpMethod                                                |
| - headers: Map<String, String>                                          |
| - queryParams: Map<String, String>                                      |
| - body: String                                                          |
| - timeoutMs: int                                                        |
| - isSecured: boolean                                                    |
| - hmacSignature: String                                                 |
+-------------------------------------------------------------------------+
| ~ HttpRequest(builder: HttpRequestBuilder)                              |
| + builder(): HttpRequestBuilder {static}                                |
| + toWireFormat(): String                                                |
| + getUrl(): String                                                      |
| + getHttpMethod(): HttpMethod                                           |
| + getHeaders(): Map<String, String>                                     |
| + getQueryParams(): Map<String, String>                                 |
| + getBody(): String                                                     |
| + getTimeoutMs(): int                                                   |
| + isSecured(): boolean                                                  |
| + getHmacSignature(): String                                            |
+-------------------------------------------------------------------------+
                                    ▲
                                    : <<builds>>
                                    :
+-------------------------------------------------------------------------+
|                            HttpRequestBuilder                           |
+-------------------------------------------------------------------------+
| ~ url: String                                                           |
| ~ httpMethod: HttpMethod                                                |
| ~ headers: Map<String, String>                                          |
| ~ queryParams: Map<String, String>                                      |
| ~ body: String                                                          |
| ~ timeoutMs: int = 5000                                                 |
| ~ isSecured: boolean = false                                            |
| ~ hmacSignature: String                                                 |
+-------------------------------------------------------------------------+
| + url(url: String): HttpRequestBuilder                                  |
| + method(method: HttpMethod): HttpRequestBuilder                        |
| + header(key: String, value: String): HttpRequestBuilder                |
| + queryParam(key: String, value: String): HttpRequestBuilder            |
| + body(body: String): HttpRequestBuilder                                |
| + timeout(timeoutMs: int): HttpRequestBuilder                           |
| + isSecured(isSecured: boolean): HttpRequestBuilder                     |
| + withSecurity(signature: String): HttpRequestBuilder                   |
| + build(): HttpRequest                                                  |
+-------------------------------------------------------------------------+