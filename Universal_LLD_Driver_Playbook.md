# Universal LLD Driver Playbook

> **A repeatable framework for wiring, executing, and validating
> Low-Level Design (LLD) patterns from `main()`.**

A practical checklist for turning a set of designed classes into a
working demo. Use it to identify the entry point, create dependencies in
the right order, assemble the root object, exercise the pattern, and
verify its invariants.

> **Naming note:** The requested title was "Universal LMD Driver
> Paybook." This README uses **Universal LLD Driver Playbook**, the
> conventional term for Low-Level Design, and corrects "Paybook" to
> "Playbook." Rename the title if you intentionally meant something
> else.

------------------------------------------------------------------------

## Table of Contents

-   [The Universal 3-Question Mental
    Framework](#the-universal-3-question-mental-framework)
-   [Trace the Dependency Chain](#1-trace-the-dependency-chain)
-   [Pattern-by-Pattern `main()` Cheat
    Sheet](#pattern-by-pattern-main-cheat-sheet)
-   [The Universal 4-Block `main()`
    Template](#the-universal-4-block-main-template)
-   [Example: Cloud Infrastructure
    Provisioning](#example-cloud-infrastructure-provisioning)
-   [Validation Checklist](#validation-checklist)
-   [Common Mistakes](#common-mistakes)
-   [Final Rule of Thumb](#final-rule-of-thumb)

------------------------------------------------------------------------

## The Universal 3-Question Mental Framework

Before touching the keyboard, ask these three questions **in order**:

1.  **Who is the top-level orchestrator or manager?**\
    This is your entry point---the object that coordinates the workflow
    or owns the registry/state.

2.  **What domain object does the orchestrator need?**\
    This is the payload that will be registered, executed, cloned, or
    managed.

3.  **How was that domain object meant to be created?**\
    Identify the creation pattern: Builder, Factory Method, Abstract
    Factory, Prototype, or another mechanism.

These questions help you move from a class diagram to an executable
`main()` without guessing the construction sequence.

------------------------------------------------------------------------

## 1. Trace the Dependency Chain

Start with the classes you designed and ask:

**"What is the ultimate class holding everything together?"**

For example, in a cloud infrastructure provisioning system:

-   `GlobalInfrastructureManager` holds or registers an
    `ExecutionEnvironment`.
-   `ExecutionEnvironment` holds a `ComputeInstance` and may hold other
    environment metadata.
-   `ComputeInstance` is constructed using a **Builder**.
-   `StorageVolume` is created by an **Abstract Factory**.
-   The appropriate cloud factory is selected by
    `InfrastructureProvisioner`.

Reverse the dependency chain into a sequence of object-creation steps:

1.  Obtain the factory or creator.
2.  Create the required leaf products.
3.  Build the parent/domain object.
4.  Assemble the root/container object.
5.  Register the completed object with the manager or registry.
6.  Retrieve it and test the behavior that matters.

### Let constructors reveal the order

-   If `ExecutionEnvironment` requires a `ComputeInstance`, create the
    instance first.
-   If `ComputeInstance` requires a `StorageVolume`, create the volume
    first.
-   If a builder requires a cloud-specific volume type, ensure the
    factory produces a compatible type.
-   Only register the root object after all required dependencies have
    been created and validated.

**Rule:** Build from the leaves upward; reason from the orchestrator
downward.

------------------------------------------------------------------------

## Pattern-by-Pattern `main()` Cheat Sheet

  ----------------------------------------------------------------------------------------------------
  Pattern           Start by calling                        What to provide or do    What to verify
  ----------------- --------------------------------------- ------------------------ -----------------
  **Builder**       `Target.builder()` or                   Supply all mandatory,    A valid object is
                    `new TargetBuilder()`                   valid fields and call    created; invalid
                                                            `build()`                input fails with
                                                                                     the documented
                                                                                     exception

  **Factory         A concrete creator,                     Call                     The returned
  Method**          e.g. `new SlackNotificationCreator()`   `createNotification()`   product uses the
                                                                                     concrete
                                                                                     creator's
                                                                                     intended
                                                                                     polymorphic
                                                                                     behavior

  **Abstract        A concrete factory,                     Create related Product A Products are
  Factory**         e.g. `new AwsInfrastructureFactory()`   and Product B through    compatible;
                                                            the same factory         cross-cloud
                                                                                     combinations are
                                                                                     rejected or
                                                                                     prevented by the
                                                                                     design

  **Prototype**     The registry or prototype cache         Register a baseline      Mutating the
                                                            template, then call      clone does not
                                                            `deepCopy()` or retrieve mutate the
                                                            a clone                  master; nested
                                                                                     mutable state is
                                                                                     isolated

  **Singleton**     `TargetManager.getInstance()`           Call manager methods,    Calls return the
                                                            optionally from multiple same instance
                                                            threads                  reference and
                                                                                     shared state
                                                                                     behaves as
                                                                                     specified
  ----------------------------------------------------------------------------------------------------

### Builder testing

Test both:

-   **Happy path:** provide mandatory valid values and confirm `build()`
    returns a usable object.
-   **Negative path:** deliberately omit a required field or provide an
    invalid value, then confirm validation rejects it with the
    documented exception (for example, `IllegalArgumentException` or
    `IllegalStateException`).

### Prototype testing

Test the copy contract, not just object identity:

-   Confirm the clone is a different object from the master.
-   Mutate a mutable field or nested child on the clone.
-   Retrieve or inspect the master and confirm its state remains
    unchanged.
-   Ensure the copy is deep enough for every mutable object that must be
    independent.

------------------------------------------------------------------------

## The Universal 4-Block `main()` Template

Organize a pattern demo into four blocks. Not every LLD problem needs
all four; adapt the blocks to the behavior your design actually
promises.

``` java
public class ApplicationTest {

    public static void main(String[] args) {

        // ================================================================
        // BLOCK 1: NEGATIVE TESTS — validation and invariants
        // ================================================================
        System.out.println("--- 1. Testing Validation & Invariants ---");

        try {
            // Replace with a genuinely invalid value for your design.
            new MyBuilder()
                    .setInvalidParam()
                    .build();

            System.err.println("FAILED: Invalid input was accepted");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(
                    "PASSED: Expected validation error -> " + e.getMessage()
            );
        }

        // ================================================================
        // BLOCK 2: HAPPY PATH — construct and register valid entities
        // ================================================================
        System.out.println("\\n--- 2. Building & Registering Valid Entities ---");

        // Create leaf dependencies first.
        ChildProduct child = factory.createChild("id-01", /* valid arguments */);

        // Build the parent/domain object.
        ParentEntity root = new ParentBuilder()
                .validField(/* valid value */)
                .attach(child)
                .build();

        // Register the completed root object.
        Manager.getInstance().register("MASTER_BLUEPRINT", root);
        System.out.println("PASSED: Blueprint registered.");

        // ================================================================
        // BLOCK 3: BEHAVIOR & ISOLATION — exercise the core contract
        // ================================================================
        System.out.println("\\n--- 3. Testing Behavior & Memory Isolation ---");

        ParentEntity clone =
                Manager.getInstance().get("MASTER_BLUEPRINT");

        // Use the clone API appropriate to your design. If get() returns
        // the master directly, this is not a valid prototype-isolation test.
        clone.getChild().modifyData("NEW_DATA");

        ParentEntity master =
                Manager.getInstance().peekMaster("MASTER_BLUEPRINT");

        boolean isolated = !master.getChild().hasData("NEW_DATA");
        System.out.println("Clone mutation isolated from master? -> " + isolated);

        // In a real test, prefer an assertion so a failed invariant fails
        // the program rather than merely printing a result.
        if (!isolated) {
            throw new AssertionError("Prototype isolation failed");
        }

        // ================================================================
        // BLOCK 4: BOUNDARIES & CONCURRENCY — test declared limits
        // ================================================================
        System.out.println("\\n--- 4. Testing Limits & Edge Boundaries ---");

        // Example only: replace with the actual capacity/limit API.
        Manager.getInstance().acquireUntilLimit();

        try {
            Manager.getInstance().acquire(); // Expected to fail at capacity.
            System.err.println("FAILED: Capacity limit was exceeded");
        } catch (IllegalStateException e) {
            System.out.println(
                    "PASSED: Boundary guarded -> " + e.getMessage()
            );
        }
    }
}
```

### Adapt the template to your design

-   Replace placeholder types and methods with the APIs that actually
    exist.
-   Catch the specific exception promised by the implementation; avoid
    catching `Exception` unless the test intentionally covers a broad
    failure contract.
-   If the design has no capacity limit or concurrency behavior, replace
    Block 4 with a relevant boundary test---or omit it.
-   Use assertions or a test framework for automated verification.
    Console output alone does not make a test fail when an invariant is
    broken.

------------------------------------------------------------------------

## Example: Cloud Infrastructure Provisioning

The following example illustrates the **creation order** for an
environment containing a cloud-specific compute instance and storage
volume.

``` java
// 1. Obtain the cloud factory through the provisioner.
InfrastructureProvisioner provisioner = new InfrastructureProvisioner();
CloudInfrastructureFactory factory = provisioner.getFactory("AWS");

// 2. Create the required leaf product.
StorageVolume volume = factory.createStorageVolume("vol-1", 100);

// 3. Build the compute instance using its builder.
ComputeInstance instance = new AwsComputeInstanceBuilder()
        .instanceId("id-1")
        .cpuCores(4)
        .ramGb(16)
        .subnetCidr("10.0.0.0/24")
        .attachedVolume((AwsStorageVolume) volume)
        .build();

// 4. Assemble the root/domain container.
ExecutionEnvironment env =
        new ExecutionEnvironment("env-1", "AWS", instance, Map.of());

// 5. Register the completed environment with the manager.
GlobalInfrastructureManager.getInstance()
        .registerGoldenTemplate("AWS_BASELINE", env);
```

### Important type-safety note

The cast to `AwsStorageVolume` is safe only if the selected factory is
guaranteed to return that concrete type. Prefer APIs that preserve
compatibility without unchecked assumptions---for example, a
cloud-specific builder that accepts the abstraction it genuinely
supports, or a factory family whose product types are enforced by the
design.

Also check the real constructors and method signatures in your codebase.
This example is a wiring illustration, not a claim that these exact APIs
compile in every implementation.

------------------------------------------------------------------------

## Validation Checklist

Before considering your `main()` complete, verify the following.

### Construction and wiring

-   [ ] I identified the top-level orchestrator or manager.
-   [ ] I identified the root/domain object the orchestrator needs.
-   [ ] I traced every constructor and builder dependency.
-   [ ] I created leaf dependencies before the objects that depend on
    them.
-   [ ] I used the intended factory/creator for each product.
-   [ ] I assembled the root object only after its dependencies were
    ready.
-   [ ] I registered the completed object with the manager or registry.

### Behavioral tests

-   [ ] I tested a valid construction path.
-   [ ] I tested invalid input where validation is part of the contract.
-   [ ] I verified the pattern's actual behavior, not only that an
    object was created.
-   [ ] For Abstract Factory, I verified that related products are
    compatible.
-   [ ] For Prototype, I verified deep-copy isolation of mutable nested
    state.
-   [ ] For Singleton, I verified reference identity and the expected
    state behavior.
-   [ ] I tested capacity, boundaries, or concurrency only when relevant
    to the design.
-   [ ] Automated assertions fail when an invariant is violated.

------------------------------------------------------------------------

## Common Mistakes

1.  **Starting in the middle of the dependency chain.**\
    Read constructors and builder requirements before writing the wiring
    code.

2.  **Creating the root before its dependencies.**\
    Work bottom-up: leaves, parents, root, then manager/registry.

3.  **Calling a manager without registering a valid object.**\
    Check what the manager expects and whether it owns, stores, clones,
    or executes the object.

4.  **Assuming every registry lookup returns a clone.**\
    A registry may return the original reference. Confirm the contract
    before running mutation-isolation tests.

5.  **Testing only object creation.**\
    A successful `build()` does not prove validation, polymorphism,
    compatibility, deep copying, or thread safety works.

6.  **Using casts to hide incompatible product types.**\
    Prefer type-safe factory/builder APIs and make product-family
    compatibility explicit.

7.  **Printing "PASSED" without asserting the condition.**\
    Make a failed invariant throw an assertion error or fail a unit
    test.

8.  **Forcing all four blocks into every problem.**\
    Keep the structure, but test only the invariants and behaviors
    relevant to the design.

------------------------------------------------------------------------

## Final Rule of Thumb

When writing `main()`:

1.  **Do not start from the middle.**
2.  Identify the orchestrator, then identify the domain object it needs.
3.  Trace constructor and builder dependencies.
4.  Create the smallest required child dependency first.
5.  Pass that dependency into the parent's factory or builder.
6.  Assemble the root object and register it with the manager.
7.  Exercise the pattern's contract---validation, polymorphism,
    compatibility, isolation, identity, limits, or concurrency.
8.  Assert the expected result instead of relying only on printed
    output.

**Mental model:** *Orchestrator → Payload → Creation Pattern →
Dependencies → Root Assembly → Registration → Behavioral Verification.*
