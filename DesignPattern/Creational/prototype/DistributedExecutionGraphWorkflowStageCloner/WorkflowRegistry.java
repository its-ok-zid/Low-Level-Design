package DesignPattern.Creational.prototype.DistributedExecutionGraphWorkflowStageCloner;

import java.util.HashMap;
import java.util.Map;

public class WorkflowRegistry {
    private final Map<String, WorkflowDAG> workflowRegistry = new HashMap<>();

    public void registerWorkflow(String workflowId, WorkflowDAG workflowDAG) {
        if (workflowDAG == null) {
            throw new IllegalArgumentException("WorkflowDAG cannot be null.");
        }
        // Protect master DAG blueprint with defensive deep copy
        workflowRegistry.put(workflowId, workflowDAG.deepCopy());
    }

    public WorkflowDAG getWorkflow(String workflowId) {
        WorkflowDAG workflowDAG = workflowRegistry.get(workflowId);
        if (workflowDAG == null) {
            throw new IllegalArgumentException("No workflow found with ID: " + workflowId);
        }
        return workflowDAG.deepCopy();
    }
}