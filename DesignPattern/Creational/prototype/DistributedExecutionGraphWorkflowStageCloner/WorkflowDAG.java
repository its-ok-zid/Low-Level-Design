package DesignPattern.Creational.prototype.DistributedExecutionGraphWorkflowStageCloner;

import java.util.HashMap;
import java.util.Map;

public class WorkflowDAG implements Prototype<WorkflowDAG> {
    private final String dagId;
    private final String dagName;
    private Map<String, WorkflowTask> tasks;
    private Map<String, String> globalVariables;

    public WorkflowDAG(String dagId, String dagName, Map<String, WorkflowTask> tasks, Map<String, String> globalVariables) {
        this.dagId = dagId;
        this.dagName = dagName;
        this.tasks = (tasks != null) ? new HashMap<>(tasks) : new HashMap<>();
        this.globalVariables = (globalVariables != null) ? new HashMap<>(globalVariables) : new HashMap<>();
    }

    public WorkflowDAG(WorkflowDAG source, boolean deepCopy) {
        this.dagId = source.dagId;
        this.dagName = source.dagName;
        if (deepCopy) {
            Map<String, WorkflowTask> visited = new HashMap<>();
            this.tasks = new HashMap<>();
            for (Map.Entry<String, WorkflowTask> entry : source.tasks.entrySet()) {
                this.tasks.put(entry.getKey(), entry.getValue().deepCopy(visited));
            }
            this.globalVariables = (source.globalVariables != null)
                    ? new HashMap<>(source.globalVariables)
                    : new HashMap<>();
        } else {
            this.tasks = source.tasks;
            this.globalVariables = source.globalVariables;
        }
    }

    @Override
    public WorkflowDAG shallowCopy() {
        return new WorkflowDAG(this, false);
    }

    @Override
    public WorkflowDAG deepCopy() {
        return new WorkflowDAG(this, true);
    }

    public String getDagId() { return dagId; }
    public String getDagName() { return dagName; }
    public Map<String, WorkflowTask> getTasks() { return tasks; }
    public Map<String, String> getGlobalVariables() { return globalVariables; }
}