package DesignPattern.Creational.builder.MultiPartHTTPRequestSecurityEnvelopeBuilder;

public class HttpRequestApplication {
    public static void main(String[] args) {
        System.out.println("=== 1. Valid Secured Payment POST Request ===");
        HttpRequest postRequest = HttpRequest.builder()
                .url("[https://api.stripe.com/v1/payments](https://api.stripe.com/v1/payments)")
                .method(HttpMethod.POST)
                .queryParam("currency", "USD")
                .header("Content-Type", "application/json")
                .body("{\"amount\": 2500}")
                .timeout(5000)
                .withSecurity("9b71d224b1a37c568f9a...")
                .build();
        System.out.println(postRequest.toWireFormat());

        System.out.println("=== 2. Valid GET Request with Query Parameters ===");
        HttpRequest getRequest = HttpRequest.builder()
                .url("[https://api.stripe.com/v1/charges](https://api.stripe.com/v1/charges)")
                .method(HttpMethod.GET)
                .queryParam("limit", "10")
                .header("Accept", "application/json")
                .build();
        System.out.println(getRequest.toWireFormat());

        System.out.println("=== 3. Testing Semantic Violation: GET with Body ===");
        try {
            HttpRequest.builder()
                    .url("[https://api.stripe.com/v1/users](https://api.stripe.com/v1/users)")
                    .method(HttpMethod.GET)
                    .body("{\"filter\": \"active\"}")
                    .build();
            System.err.println("FAILED: Validation did not catch body on GET request.");
        } catch (IllegalStateException e) {
            System.out.println("SUCCESS: Caught invalid request -> " + e.getMessage());
        }
    }
}