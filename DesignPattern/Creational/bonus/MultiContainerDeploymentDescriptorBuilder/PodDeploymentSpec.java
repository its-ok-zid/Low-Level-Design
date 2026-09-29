package DesignPattern.Creational.bonus.MultiContainerDeploymentDescriptorBuilder;

import java.util.List;
import java.util.Map;

public class PodDeploymentSpec {
    private final String podName;
    private final String namespace;
    private final RestartPolicy restartPolicy;
    private final List<ContainerSpec> containers;
    private final List<VolumeSpec> volumes;
    private final Map<String, String> labels;

    PodDeploymentSpec(PodDeploymentSpecBuilder b) {
        this.podName = b.podName;
        this.namespace = b.namespace;
        this.restartPolicy = b.restartPolicy;
        this.containers = List.copyOf(b.containers);
        this.volumes = List.copyOf(b.volumes);
        this.labels = Map.copyOf(b.labels);
    }

    public static PodDeploymentSpecBuilder builder() {
        return new PodDeploymentSpecBuilder();
    }

    public String getPodName() { return podName; }
    public String getNamespace() { return namespace; }
    public RestartPolicy getRestartPolicy() { return restartPolicy; }
    public List<ContainerSpec> getContainers() { return containers; }
    public List<VolumeSpec> getVolumes() { return volumes; }
    public Map<String, String> getLabels() { return labels; }

    public String toYaml() {
        StringBuilder yml = new StringBuilder();
        yml.append("apiVersion: v1\n");
        yml.append("kind: Pod\n");
        yml.append("metadata:\n");
        yml.append("  name: ").append(podName).append("\n");
        yml.append("  namespace: ").append(namespace).append("\n");
        if (!labels.isEmpty()) {
            yml.append("  labels:\n");
            for (Map.Entry<String, String> entry : labels.entrySet()) {
                yml.append("    ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        }
        yml.append("spec:\n");
        yml.append("  restartPolicy: ").append(restartPolicy.name()).append("\n");

        yml.append("  containers:\n");
        for (ContainerSpec c : containers) {
            yml.append("    - name: ").append(c.getName()).append("\n");
            yml.append("      image: ").append(c.getImage()).append("\n");
            if (c.getContainerPort() > 0) {
                yml.append("      ports:\n");
                yml.append("        - containerPort: ").append(c.getContainerPort()).append("\n");
            }
            if (c.getCpuRequest() > 0 || c.getMemoryRequestMb() > 0) {
                yml.append("      resources:\n");
                yml.append("        requests:\n");
                yml.append("          cpu: \"").append(c.getCpuRequest()).append("\"\n");
                yml.append("          memory: \"").append(c.getMemoryRequestMb()).append("Mi\"\n");
                yml.append("        limits:\n");
                yml.append("          cpu: \"").append(c.getCpuLimit()).append("\"\n");
                yml.append("          memory: \"").append(c.getMemoryLimitMb()).append("Mi\"\n");
            }
            if (!c.getEnvVars().isEmpty()) {
                yml.append("      env:\n");
                for (Map.Entry<String, String> envEntry : c.getEnvVars().entrySet()) {
                    yml.append("        - name: ").append(envEntry.getKey()).append("\n");
                    yml.append("          value: \"").append(envEntry.getValue()).append("\"\n");
                }
            }
        }

        if (!volumes.isEmpty()) {
            yml.append("  volumes:\n");
            for (VolumeSpec v : volumes) {
                yml.append("    - name: ").append(v.getVolumeName()).append("\n");
                yml.append("      mountPath: ").append(v.getMountPath()).append("\n");
                yml.append("      readOnly: ").append(v.isReadOnly()).append("\n");
            }
        }
        return yml.toString();
    }
}