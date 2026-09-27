package DesignPattern.Creational.builder.DistributedExecutionPlanMapReduceJobSpecBuilder;

public class DistributedJobApplication {

    public static void main(String[] args) {
        System.out.println(">>> 1. BUILDING VALID PRODUCTION JOB SPEC...\n");

        try {
            DistributedJobSpec productionSpec = new DistributedJobSpecBuilder()
                    .jobId("job-98432")
                    .jobName("ETL_Daily_Agg")
                    .inputPath("s3://analytics-lake/raw/events/")
                    .outputPath("s3://analytics-lake/curated/metrics/")
                    .numExecutors(10)
                    .executorCores(4)
                    .executorMemoryGb(16.0)
                    .shufflePartitions(400)
                    .maxRetries(3)
                    .checkpointDir("s3://checkpoints/daily/")
                    .sparkProperty("spark.sql.adaptive.enabled", "true")
                    .sparkProperty("spark.dynamicAllocation.enabled", "false")
                    .build();

            // Print the ASCII Execution Footprint
            System.out.println(productionSpec.generateExecutionSummary());

        } catch (Exception e) {
            System.err.println("Unexpected build failure: " + e.getMessage());
        }

        System.out.println("\n>>> 2. TESTING CONSTRAINT VALIDATIONS...\n");

        testFailureCase("Invalid Storage Scheme", () -> {
            new DistributedJobSpecBuilder()
                    .jobId("job-001")
                    .jobName("Invalid_Scheme")
                    .inputPath("file:///local/path/data.parquet"); // Invalid prefix
        });

        testFailureCase("Identical Input & Output Paths", () -> {
            new DistributedJobSpecBuilder()
                    .jobId("job-002")
                    .jobName("Recursive_Write")
                    .inputPath("hdfs://cluster/data/warehouse")
                    .outputPath("hdfs://cluster/data/warehouse")
                    .build();
        });

        testFailureCase("Executor Cores Exceed Max Limit (32)", () -> {
            new DistributedJobSpecBuilder()
                    .executorCores(64); // Exceeds upper limit of 32
        });

        testFailureCase("Executor Memory Below Min Limit (0.5 GB)", () -> {
            new DistributedJobSpecBuilder()
                    .executorMemoryGb(0.2); // Below 0.5 GB
        });

        testFailureCase("Max Retries Exceed Upper Bound (10)", () -> {
            new DistributedJobSpecBuilder()
                    .maxRetries(15); // Exceeds 10
        });

        testFailureCase("Missing Mandatory Required Fields", () -> {
            new DistributedJobSpecBuilder()
                    .jobId("job-003")
                    // Missing jobName, inputPath, outputPath
                    .build();
        });
    }

    private static void testFailureCase(String scenarioName, Runnable action) {
        try {
            action.run();
            System.out.println("[FAIL] Expected exception for '" + scenarioName + "', but it succeeded.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.printf("[PASS] %-42s -> Caught: %s%n", scenarioName, e.getMessage());
        } catch (Exception e) {
            System.out.printf("[WARN] %-42s -> Unexpected exception type: %s%n", scenarioName, e.getClass().getSimpleName());
        }
    }
}