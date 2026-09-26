package DesignPattern.Creational.builder.MultiPartHTTPRequestSecurityEnvelopeBuilder;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilder {
    String url;
    HttpMethod httpMethod;
    final Map<String, String> headers = new HashMap<>();
    final Map<String, String> queryParams = new HashMap<>();
    String body;
    int timeoutMs = 5000;
    boolean isSecured = false;
    String hmacSignature;

    public HttpRequestBuilder url(String url) {
        if (url == null || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            throw new IllegalArgumentException("Invalid URL: must start with http:// or https://");
        }
        this.url = url;
        return this;
    }

    public HttpRequestBuilder method(HttpMethod httpMethod) {
        if (httpMethod == null) {
            throw new IllegalArgumentException("HttpMethod cannot be null.");
        }
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpRequestBuilder header(String key, String value) {
        if (key != null && value != null) {
            this.headers.put(key.trim(), value.trim());
        }
        return this;
    }

    public HttpRequestBuilder queryParam(String key, String value) {
        if (key != null && value != null) {
            this.queryParams.put(key.trim(), value.trim());
        }
        return this;
    }

    public HttpRequestBuilder body(String body) {
        this.body = body;
        return this;
    }

    public HttpRequestBuilder timeout(int timeoutMs) {
        if (timeoutMs <= 0 || timeoutMs > 60000) {
            throw new IllegalArgumentException("Timeout must be between 1 and 60,000 ms.");
        }
        this.timeoutMs = timeoutMs;
        return this;
    }

    public HttpRequestBuilder isSecured(boolean isSecured) {
        this.isSecured = isSecured;
        return this;
    }

    public HttpRequestBuilder withSecurity(String hmacSignature) {
        this.isSecured = true;
        this.hmacSignature = hmacSignature;
        return this;
    }

    public HttpRequest build() {
        if (this.url == null || this.url.isBlank()) {
            throw new IllegalStateException("URL must be set before building HttpRequest.");
        }
        if (this.httpMethod == null) {
            throw new IllegalStateException("HTTP method must be set before building HttpRequest.");
        }
        if ((this.httpMethod == HttpMethod.GET || this.httpMethod == HttpMethod.DELETE)
                && this.body != null && !this.body.isBlank()) {
            throw new IllegalStateException("HTTP method " + this.httpMethod + " must not have a body payload.");
        }
        if (this.isSecured && (this.hmacSignature == null || this.hmacSignature.isBlank())) {
            throw new IllegalStateException("HMAC signature cannot be null or empty when isSecured is true.");
        }

        return new HttpRequest(this);
    }
}