package DesignPattern.Creational.builder.DistributedExecutionPlanMapReduceJobSpecBuilder;

import java.util.HashMap;
import java.util.Map;

public class DistributedJobSpecBuilder {

     String jobId;
     String jobName;
     String inputPath;
     String outputPath;
     int numExecutors = 1;
     int executorCores = 1;
     double executorMemoryGb = 1.0;
     int shufflePartitions = 200;
     String checkPointDir;
     int maxRetries = 3;
     final Map<String, String> sparkProperties = new HashMap<>();

    public DistributedJobSpecBuilder jobId(String jobId) {
        if (jobId == null || jobId.trim().isEmpty()) {
            throw new IllegalArgumentException("Job ID cannot be null or empty.");
        }
        this.jobId = jobId;
        return this;
    }

    public DistributedJobSpecBuilder jobName(String jobName) {
        if (jobName == null || jobName.trim().isEmpty()) {
            throw new IllegalArgumentException("Job Name cannot be null or empty.");
        }
        this.jobName = jobName;
        return this;
    }

    public DistributedJobSpecBuilder inputPath(String inputPath) {
        validateStorageUri(inputPath, "Input path");
        this.inputPath = inputPath;
        return this;
    }

    public DistributedJobSpecBuilder outputPath(String outputPath) {
        validateStorageUri(outputPath, "Output path");
        this.outputPath = outputPath;
        return this;
    }

    public DistributedJobSpecBuilder checkpointDir(String checkpointDir) {
        if (checkpointDir != null) {
            validateStorageUri(checkpointDir, "Checkpoint directory");
        }
        this.checkPointDir = checkpointDir;
        return this;
    }

    public DistributedJobSpecBuilder numExecutors(int numExecutors) {
        if (numExecutors < 1) {
            throw new IllegalArgumentException("numExecutors must be >= 1. Provided: " + numExecutors);
        }
        this.numExecutors = numExecutors;
        return this;
    }

    public DistributedJobSpecBuilder executorCores(int executorCores) {
        if (executorCores < 1 || executorCores > 32) {
            throw new IllegalArgumentException("executorCores must be between 1 and 32. Provided: " + executorCores);
        }
        this.executorCores = executorCores;
        return this;
    }

    public DistributedJobSpecBuilder executorMemoryGb(double executorMemoryGb) {
        if (executorMemoryGb < 0.5 || executorMemoryGb > 128.0) {
            throw new IllegalArgumentException("executorMemoryGb must be between 0.5 and 128.0. Provided: " + executorMemoryGb);
        }
        this.executorMemoryGb = executorMemoryGb;
        return this;
    }

    public DistributedJobSpecBuilder shufflePartitions(int shufflePartitions) {
        if (shufflePartitions < 1) {
            throw new IllegalArgumentException("shufflePartitions must be >= 1. Provided: " + shufflePartitions);
        }
        this.shufflePartitions = shufflePartitions;
        return this;
    }

    public DistributedJobSpecBuilder maxRetries(int maxRetries) {
        if (maxRetries < 0 || maxRetries > 10) {
            throw new IllegalArgumentException("maxRetries must be between 0 and 10. Provided: " + maxRetries);
        }
        this.maxRetries = maxRetries;
        return this;
    }

    public DistributedJobSpecBuilder sparkProperty(String key, String value) {
        if (key != null && value != null) {
            this.sparkProperties.put(key, value);
        }
        return this;
    }

    public DistributedJobSpec build() {
        if (jobId == null) throw new IllegalStateException("jobId is required.");
        if (jobName == null) throw new IllegalStateException("jobName is required.");
        if (inputPath == null) throw new IllegalStateException("inputPath is required.");
        if (outputPath == null) throw new IllegalStateException("outputPath is required.");

        if (inputPath.equalsIgnoreCase(outputPath)) {
            throw new IllegalArgumentException("Output path cannot match input path: " + outputPath);
        }

        return new DistributedJobSpec(this);
    }

    private void validateStorageUri(String path, String fieldName) {
        if (path == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
        boolean validPrefix = path.startsWith("hdfs://")
                || path.startsWith("s3://")
                || path.startsWith("gs://");
        if (!validPrefix) {
            throw new IllegalArgumentException(fieldName + " must start with hdfs://, s3://, or gs://. Received: " + path);
        }
    }
}