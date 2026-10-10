package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.prototype;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.factory.ComputeInstance;

import java.util.HashMap;
import java.util.Map;

public class ExecutionEnvironment implements Prototype<ExecutionEnvironment> {

    private final String environmentId;
    private String cloudProvider;

    private ComputeInstance computeInstance;
    private Map<String, String> environmentVariables;

    public ExecutionEnvironment(String environmentId, String cloudProvider, ComputeInstance computeInstance, Map<String, String> environmentVariables) {
        this.environmentId = environmentId;
        this.cloudProvider = cloudProvider;
        this.computeInstance = computeInstance;
        this.environmentVariables = (environmentVariables != null) ? new HashMap<>(environmentVariables) : new HashMap<>();
    }

    public ExecutionEnvironment(ExecutionEnvironment source, boolean deepCopy) {
        this.environmentId = source.environmentId;
        this.cloudProvider = source.cloudProvider;
        if (deepCopy) {
            this.computeInstance = (source.computeInstance != null) ? source.computeInstance.deepCopy() : null;
            this.environmentVariables = (source.environmentVariables != null) ? new HashMap<>(source.environmentVariables) : new HashMap<>();
        } else {
            this.computeInstance = source.computeInstance;
            this.environmentVariables = source.environmentVariables;
        }
    }

    public String getEnvironmentId() {
        return environmentId;
    }

    public String getCloudProvider() {
        return cloudProvider;
    }

    public ComputeInstance getComputeInstance() {
        return computeInstance;
    }

    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    @Override
    public ExecutionEnvironment shallowCopy() {
        return new ExecutionEnvironment(this, false);
    }

    @Override
    public ExecutionEnvironment deepCopy() {
        return new ExecutionEnvironment(this, true);
    }
}
