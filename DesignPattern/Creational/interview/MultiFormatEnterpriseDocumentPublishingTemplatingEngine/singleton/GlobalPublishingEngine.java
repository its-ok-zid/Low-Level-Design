package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.singleton;

import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.builder.Document;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalPublishingEngine {
    private static volatile GlobalPublishingEngine instance;
    private final Map<String, Document> templateRegistry;
    private final AtomicInteger activeRenderJobs;
    private final int maxRenderQuota;

    private GlobalPublishingEngine(int maxRenderQuota) {
        if (instance != null) {
            throw new IllegalStateException("GlobalPublishingEngine instance already exists. Use getInstance() to access it.");
        }
        this.templateRegistry = new ConcurrentHashMap<>();
        this.activeRenderJobs = new AtomicInteger(0);
        this.maxRenderQuota = maxRenderQuota;
    }

    public static GlobalPublishingEngine getInstance() {
        if (instance == null) {
            synchronized (GlobalPublishingEngine.class) {
                if (instance == null) {
                    instance = new GlobalPublishingEngine(15);
                }
            }
        }
        return instance;
    }

    public void registerTemplate(String templateId, Document template) {
        if (templateId == null || template == null) {
            throw new IllegalArgumentException("Template ID and template cannot be null.");
        }
        templateRegistry.put(templateId, template.deepCopy());
    }

    public Document getRegisteredTemplate(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Template ID cannot be null.");
        }
        return templateRegistry.get(key);
    }

    public Document acquireDocument(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Template ID cannot be null.");
        }
        if (activeRenderJobs.get() >= maxRenderQuota) {
            throw new IllegalStateException("Max render quota reached (" + maxRenderQuota + "). Please wait for capacity to free up.");
        }
        Document template = templateRegistry.get(key);
        if (template == null) {
            throw new IllegalArgumentException("Template not found: " + key);
        }
        activeRenderJobs.incrementAndGet();
        return template.deepCopy();
    }

    public void releaseDocument(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Template ID cannot be null.");
        }
        if (!templateRegistry.containsKey(key)) {
            throw new IllegalArgumentException("Template not found: " + key);
        }
        activeRenderJobs.updateAndGet(current -> Math.max(0, current - 1));
    }

    public AtomicInteger getActiveRenderJobs() {
        return activeRenderJobs;
    }

    public int getMaxRenderQuota() {
        return maxRenderQuota;
    }
}