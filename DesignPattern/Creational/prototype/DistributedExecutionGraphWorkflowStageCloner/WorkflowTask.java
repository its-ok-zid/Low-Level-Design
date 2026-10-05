package DesignPattern.Creational.prototype.DistributedExecutionGraphWorkflowStageCloner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowTask implements Prototype<WorkflowTask> {
    private final String taskId;
    private final String taskType;
    private TaskStatus taskStatus;
    private int retryCount;
    private Map<String, String> environment;
    private List<WorkflowTask> dependencies;

    public WorkflowTask(String taskId, String taskType, TaskStatus taskStatus, int retryCount,
                        Map<String, String> environment, List<WorkflowTask> dependencies) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.taskStatus = (taskStatus != null) ? taskStatus : TaskStatus.PENDING;
        this.retryCount = retryCount;
        this.environment = (environment != null) ? new HashMap<>(environment) : new HashMap<>();
        this.dependencies = (dependencies != null) ? new ArrayList<>(dependencies) : new ArrayList<>();
    }

    public WorkflowTask(WorkflowTask source, boolean deepCopy) {
        this.taskId = source.taskId;
        this.taskType = source.taskType;
        this.taskStatus = source.taskStatus;
        this.retryCount = source.retryCount;
        if (deepCopy) {
            this.environment = new HashMap<>(source.environment);
            this.dependencies = new ArrayList<>();
            Map<String, WorkflowTask> visited = new HashMap<>();
            visited.put(this.taskId, this);
            for (WorkflowTask dep : source.dependencies) {
                this.dependencies.add(dep.deepCopy(visited));
            }
        } else {
            this.environment = source.environment;
            this.dependencies = source.dependencies;
        }
    }

    public WorkflowTask deepCopy(Map<String, WorkflowTask> visited) {
        // 1. If this node has already been cloned during this graph traversal, reuse it
        if (visited.containsKey(this.taskId)) {
            return visited.get(this.taskId);
        }

        // 2. Instantiate clone with an empty dependency list
        WorkflowTask clonedTask = new WorkflowTask(
                this.taskId,
                this.taskType,
                this.taskStatus,
                this.retryCount,
                new HashMap<>(this.environment),
                new ArrayList<>()
        );

        // 3. Register in visited map BEFORE recursing dependencies (avoids infinite cycles)
        visited.put(this.taskId, clonedTask);

        // 4. Recursively clone dependencies passing the SAME visited context
        for (WorkflowTask dep : this.dependencies) {
            clonedTask.addDependency(dep.deepCopy(visited));
        }

        return clonedTask;
    }

    public void addDependency(WorkflowTask dependency) {
        if (dependency != null && !this.dependencies.contains(dependency)) {
            this.dependencies.add(dependency);
        }
    }

    @Override
    public WorkflowTask shallowCopy() {
        return new WorkflowTask(this, false);
    }

    @Override
    public WorkflowTask deepCopy() {
        return deepCopy(new HashMap<>());
    }

    public String getTaskId() { return taskId; }
    public String getTaskType() { return taskType; }
    public TaskStatus getTaskStatus() { return taskStatus; }
    public void setTaskStatus(TaskStatus taskStatus) { this.taskStatus = taskStatus; }
    public int getRetryCount() { return retryCount; }
    public void setRetryCount(int retryCount) { this.retryCount = retryCount; }
    public void incrementRetryCount() { this.retryCount++; }
    public Map<String, String> getEnvironment() { return environment; }
    public void setEnvironment(Map<String, String> environment) { this.environment = environment; }
    public List<WorkflowTask> getDependencies() { return dependencies; }
}