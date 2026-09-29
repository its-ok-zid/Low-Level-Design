package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

public class PodApplication {
    public static void main(String[] args) {
        System.out.println("=== 1. Building Multi-Container Pod with Volume Mount ===");
        PodDeploymentSpec pod = PodDeploymentSpec.builder()
                .name("payment-service-pod")
                .namespace("production")
                .restartPolicy(RestartPolicy.ALWAYS)
                .label("app", "payment-service")
                .label("tier", "backend")
                // Container 1: Core API
                .container()
                .name("payment-app")
                .image("internal-registry.ecr/payment:v2.1")
                .port(8080)
                .resources(0.5, 2.0, 512, 2048)
                .env("ENV", "PROD")
                .env("DB_HOST", "rds.internal")
                .endContainer()
                // Container 2: Envoy Proxy Sidecar
                .container()
                .name("envoy-sidecar")
                .image("envoyproxy/envoy:v1.28")
                .port(9901)
                .resources(0.25, 0.5, 256, 512)
                .endContainer()
                // Volume Spec
                .volume()
                .name("app-secrets")
                .mountPath("/etc/secrets")
                .readOnly(true)
                .endVolume()
                .build();

        System.out.println(pod.toYaml());

        System.out.println("=== 2. Testing Invariant Violation: Port Collision ===");
        try {
            PodDeploymentSpec.builder()
                    .name("colliding-pod")
                    .container()
                    .name("web-1")
                    .image("nginx:latest")
                    .port(80)
                    .endContainer()
                    .container()
                    .name("web-2")
                    .image("apache:latest")
                    .port(80) // Duplicate port 80!
                    .endContainer()
                    .build();
            System.err.println("FAILED: Validation allowed port collision!");
        } catch (IllegalStateException e) {
            System.out.println("SUCCESS: Blocked port collision -> " + e.getMessage());
        }
    }
}