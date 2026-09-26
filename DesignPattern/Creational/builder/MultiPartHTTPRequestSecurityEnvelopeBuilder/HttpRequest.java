package DesignPattern.Creational.builder.MultiPartHTTPRequestSecurityEnvelopeBuilder;

import java.net.URI;
import java.util.Map;
import java.util.StringJoiner;

public class HttpRequest {
    private final String url;
    private final HttpMethod httpMethod;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final String body;
    private final int timeoutMs;
    private final boolean isSecured;
    private final String hmacSignature;

    HttpRequest(HttpRequestBuilder httpRequestBuilder) {
        this.url = httpRequestBuilder.url;
        this.httpMethod = httpRequestBuilder.httpMethod;
        this.headers = Map.copyOf(httpRequestBuilder.headers);
        this.queryParams = Map.copyOf(httpRequestBuilder.queryParams);
        this.body = httpRequestBuilder.body;
        this.timeoutMs = httpRequestBuilder.timeoutMs;
        this.isSecured = httpRequestBuilder.isSecured;
        this.hmacSignature = httpRequestBuilder.hmacSignature;
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public String getUrl() { return url; }
    public HttpMethod getHttpMethod() { return httpMethod; }
    public Map<String, String> getHeaders() { return headers; }
    public Map<String, String> getQueryParams() { return queryParams; }
    public String getBody() { return body; }
    public int getTimeoutMs() { return timeoutMs; }
    public boolean isSecured() { return isSecured; }
    public String getHmacSignature() { return hmacSignature; }

    public String toWireFormat() {
        StringBuilder wireFormat = new StringBuilder();
        URI uri = URI.create(this.url);

        String scheme = uri.getScheme() != null ? uri.getScheme().toLowerCase() : "http";
        String host = uri.getHost();
        String path = (uri.getPath() != null && !uri.getPath().isEmpty()) ? uri.getPath() : "/";

        StringJoiner queryJoiner = new StringJoiner("&");
        if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
            queryJoiner.add(uri.getQuery());
        }
        if (this.queryParams != null && !this.queryParams.isEmpty()) {
            this.queryParams.forEach((k, v) -> queryJoiner.add(k + "=" + v));
        }

        String fullPath = queryJoiner.length() > 0 ? path + "?" + queryJoiner : path;

        wireFormat.append(":method: ").append(this.httpMethod).append("\n");
        wireFormat.append(":scheme: ").append(scheme).append("\n");
        wireFormat.append(":path: ").append(fullPath).append("\n");

        if (host != null) {
            wireFormat.append("host: ").append(host).append("\n");
        }
        if (this.headers != null) {
            this.headers.forEach((key, value) ->
                    wireFormat.append(key.toLowerCase()).append(": ").append(value).append("\n")
            );
        }

        if (this.isSecured && this.hmacSignature != null && !this.hmacSignature.isEmpty()) {
            wireFormat.append("x-signature-hmac: ").append(this.hmacSignature).append("\n");
        }

        if (this.body != null && !this.body.isEmpty()) {
            wireFormat.append("[Body Payload]: ").append(this.body).append("\n");
        }

        return wireFormat.toString();
    }
}