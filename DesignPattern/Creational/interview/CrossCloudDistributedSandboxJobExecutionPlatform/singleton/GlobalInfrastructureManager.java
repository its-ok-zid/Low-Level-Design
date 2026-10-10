package DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.singleton;

import DesignPattern.Creational.interview.CrossCloudDistributedSandboxJobExecutionPlatform.prototype.ExecutionEnvironment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalInfrastructureManager {
    private static volatile GlobalInfrastructureManager instance;
    private final Map<String, ExecutionEnvironment> templateRegistry;
    private final AtomicInteger activeInstanceCount;
    private final int maxInstanceQuota;

    private GlobalInfrastructureManager() {
        this.templateRegistry = new ConcurrentHashMap<>();
        this.activeInstanceCount = new AtomicInteger(0);
        this.maxInstanceQuota = 10; // Default quota
    }

    public static GlobalInfrastructureManager getInstance() {
        if (instance == null) {
            synchronized (GlobalInfrastructureManager.class) {
                if (instance == null) {
                    instance = new GlobalInfrastructureManager();
                }
            }
        }
        return instance;
    }

    public Map<String, ExecutionEnvironment> getTemplateRegistry() {
        return templateRegistry;
    }

    public AtomicInteger getActiveInstanceCount() {
        return activeInstanceCount;
    }

    public int getMaxInstanceQuota() {
        return maxInstanceQuota;
    }

    public void registerGoldenTemplate(String templateId, ExecutionEnvironment template) {
        if (template == null) throw new IllegalArgumentException("Template cannot be null");
        templateRegistry.put(templateId, template.deepCopy());
    }

    public ExecutionEnvironment aquireExecutionEnvironment(String templateId) {
        if (activeInstanceCount.get() >= maxInstanceQuota) {
            throw new RuntimeException("Max instance quota reached");
        }
        ExecutionEnvironment template = templateRegistry.get(templateId);
        if (template == null) {
            throw new RuntimeException("Template not found");
        }
        activeInstanceCount.incrementAndGet();
        return template.deepCopy();
    }

    public void releaseExecutionEnvironment(ExecutionEnvironment env) {
        activeInstanceCount.decrementAndGet();
    }
}
