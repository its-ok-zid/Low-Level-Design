package DesignPattern.Creational.builder.DistributedExecutionPlanMapReduceJobSpecBuilder;


import java.util.Map;

public class DistributedJobSpec {
    private final String jobId;
    private final String jobName;
    private final String inputPath;
    private final String outputPath;
    private final int numExecutors;
    private final int executorCores;
    private final double executorMemoryGb;
    private final int shufflePartitions;
    private final String checkPointDir;
    private final int maxRetries;
    private final Map<String, String> sparkProperties;

    DistributedJobSpec(DistributedJobSpecBuilder builder) {
        this.jobId = builder.jobId;
        this.jobName = builder.jobName;
        this.inputPath = builder.inputPath;
        this.outputPath = builder.outputPath;
        this.numExecutors = builder.numExecutors;
        this.executorCores = builder.executorCores;
        this.executorMemoryGb = builder.executorMemoryGb;
        this.shufflePartitions = builder.shufflePartitions;
        this.checkPointDir = builder.checkPointDir;
        this.maxRetries = builder.maxRetries;
        this.sparkProperties = Map.copyOf(builder.sparkProperties);
    }

    public String getJobId() {
        return jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public int getNumExecutors() {
        return numExecutors;
    }

    public int getExecutorCores() {
        return executorCores;
    }

    public double getExecutorMemoryGb() {
        return executorMemoryGb;
    }

    public int getShufflePartitions() {
        return shufflePartitions;
    }

    public String getCheckPointDir() {
        return checkPointDir;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public Map<String, String> getSparkProperties() {
        return sparkProperties;
    }

    public String generateExecutionSummary() {
        int totalCores = numExecutors * executorCores;
        double totalMemory = numExecutors * executorMemoryGb;
        String checkpointDisplay = (checkPointDir != null && !checkPointDir.trim().isEmpty())
                ? checkPointDir
                : "N/A";

        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append(String.format("DISTRIBUTED JOB EXECUTION SPEC: [%s - %s]%n", jobId, jobName));
        sb.append(String.format("Input:   %s%n", inputPath));
        sb.append(String.format("Output:  %s%n", outputPath));
        sb.append(String.format("Cluster: %d executors x %d cores (%d total cores) | %.1f GB RAM per worker%n",
                numExecutors, executorCores, totalCores, executorMemoryGb));
        sb.append(String.format("Total Cluster Memory Allocated: %.1f GB%n", totalMemory));
        sb.append(String.format("Shuffle Partitions: %d | Retries: %d | Checkpoint: %s%n",
                shufflePartitions, maxRetries, checkpointDisplay));
        sb.append("========================================================");

        return sb.toString();
    }
}
