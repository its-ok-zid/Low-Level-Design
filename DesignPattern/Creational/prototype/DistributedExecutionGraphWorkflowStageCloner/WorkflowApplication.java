package DesignPattern.Creational.prototype.DistributedExecutionGraphWorkflowStageCloner;

import java.util.HashMap;
import java.util.Map;

public class WorkflowApplication {
    public static void main(String[] args) {
        System.out.println("=== 1. Building Master Diamond DAG (A -> B, C -> D) ===");
        /*
                  [Task A: Extract]
                     /         \
                    /           \
          [Task B: Transform]  [Task C: Enrich]
                    \           /
                     \         /
                   [Task D: Load]
        */
        WorkflowTask taskA = new WorkflowTask("task-A", "EXTRACT", TaskStatus.PENDING, 0, null, null);
        WorkflowTask taskB = new WorkflowTask("task-B", "TRANSFORM", TaskStatus.PENDING, 0, null, null);
        WorkflowTask taskC = new WorkflowTask("task-C", "ENRICH", TaskStatus.PENDING, 0, null, null);
        WorkflowTask taskD = new WorkflowTask("task-D", "LOAD", TaskStatus.PENDING, 0, null, null);

        taskB.addDependency(taskA);
        taskC.addDependency(taskA);
        taskD.addDependency(taskB);
        taskD.addDependency(taskC);

        Map<String, WorkflowTask> masterTasks = new HashMap<>();
        masterTasks.put("task-A", taskA);
        masterTasks.put("task-B", taskB);
        masterTasks.put("task-C", taskC);
        masterTasks.put("task-D", taskD);

        Map<String, String> globals = new HashMap<>();
        globals.put("RUN_ENV", "PRODUCTION");

        WorkflowDAG masterDag = new WorkflowDAG("dag-etl-daily", "Daily ETL Aggregation", masterTasks, globals);

        // Register in cache
        WorkflowRegistry registry = new WorkflowRegistry();
        registry.registerWorkflow(masterDag.getDagId(), masterDag);

        System.out.println("=== 2. Spawning Independent Execution Run ===");
        WorkflowDAG executionRun1 = registry.getWorkflow("dag-etl-daily");

        WorkflowTask clonedD = executionRun1.getTasks().get("task-D");
        WorkflowTask clonedB = clonedD.getDependencies().get(0);
        WorkflowTask clonedC = clonedD.getDependencies().get(1);

        WorkflowTask clonedA_via_B = clonedB.getDependencies().get(0);
        WorkflowTask clonedA_via_C = clonedC.getDependencies().get(0);

        System.out.println("Cloned Task D depends on: " + clonedB.getTaskId() + " and " + clonedC.getTaskId());
        System.out.println("Task B's upstream Task A Memory Hash: " + System.identityHashCode(clonedA_via_B));
        System.out.println("Task C's upstream Task A Memory Hash: " + System.identityHashCode(clonedA_via_C));

        // CRITICAL CHECK: Verify Diamond Topology (Both must point to the EXACT same object in heap!)
        boolean isSameInstance = (clonedA_via_B == clonedA_via_C);
        System.out.println("Topology Preserved (clonedA_via_B == clonedA_via_C)? -> " + isSameInstance);

        // Mutate Active Execution Run
        System.out.println("\n=== 3. Mutating Execution Run & Verifying Master Template Isolation ===");
        clonedA_via_B.setTaskStatus(TaskStatus.RUNNING);
        clonedA_via_B.incrementRetryCount();

        WorkflowTask masterA = masterDag.getTasks().get("task-A");
        System.out.println("Cloned Task A Status: " + clonedA_via_B.getTaskStatus() + " | Retries: " + clonedA_via_B.getRetryCount());
        System.out.println("Master Task A Status: " + masterA.getTaskStatus() + " | Retries: " + masterA.getRetryCount());
    }
}