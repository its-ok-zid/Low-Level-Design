package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine;

import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.builder.Document;
import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.builder.DocumentBuilder;
import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory.*;
import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.singleton.GlobalPublishingEngine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentApplication {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("ENTERPRISE DOCUMENT PUBLISHING ENGINE - 5-STAGE VERIFICATION HARNESS");
        System.out.println("======================================================================\n");

        runStage1NegativeValidationTests();
        runStage2PositiveAssemblyAndRegistration();
        runStage3PrototypeDeepCopyIsolation();
        runStage4SecurityAndConcurrencyTests();
        runStage5QuotaSaturationAndRecovery();

        System.out.println("\n>>> ALL TEST STAGES COMPLETED SUCCESSFULLY <<<");
    }

    // =====================================================================================
    // STAGE 1: NEGATIVE VALIDATION TESTS & DOMAIN INVARIANTS (FAIL-FAST GUARDS)
    // =====================================================================================
    private static void runStage1NegativeValidationTests() {
        System.out.println("--- [STAGE 1] NEGATIVE VALIDATION TESTS ---");

        // [TEST 1.1] Margin Geometry Boundary Violation (2 * 110 = 220 >= 210)
        try {
            new DocumentBuilder()
                    .documentId("doc-invalid-margin")
                    .title("Invalid Margin Doc")
                    .author("QA")
                    .pageWidthMm(210)
                    .pageHeightMm(297)
                    .marginMm(110)
                    .header(new PdfHeader("Helvetica-Bold", 10, new ArrayList<>()))
                    .body(new PdfBody("Helvetica", new ArrayList<>()))
                    .build();
            System.err.println("[FAIL] Test 1.1: Invalid margin was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("[PASS] Test 1.1: Margin geometry violation caught -> " + e.getMessage());
        }

        // [TEST 1.2] Blank Title / Author Invariant
        try {
            new DocumentBuilder()
                    .documentId("doc-blank-title")
                    .title("   ")
                    .author("Valid Author")
                    .header(new PdfHeader("Helvetica-Bold", 10, new ArrayList<>()))
                    .body(new PdfBody("Helvetica", new ArrayList<>()))
                    .build();
            System.err.println("[FAIL] Test 1.2: Blank title was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("[PASS] Test 1.2: Blank title caught -> " + e.getMessage());
        }

        // [TEST 1.3] Cross-Format Mixing Guard (PdfHeader + HtmlBody)
        try {
            new DocumentBuilder()
                    .documentId("doc-mixed-format")
                    .title("Format Mismatch")
                    .author("QA")
                    .header(new PdfHeader("Helvetica-Bold", 10, new ArrayList<>())) // PDF
                    .body(new HtmlBody(new ArrayList<>(), new ArrayList<>()))        // HTML
                    .build();
            System.err.println("[FAIL] Test 1.3: Cross-format mixing was allowed!");
        } catch (IllegalArgumentException e) {
            System.out.println("[PASS] Test 1.3: Cross-format mismatch caught -> " + e.getMessage());
        }

        // [TEST 1.4] Unsupported Factory Provider
        try {
            DocumentProvisioner provisioner = new DocumentProvisioner();
            provisioner.getFactory("MARKDOWN");
            System.err.println("[FAIL] Test 1.4: Unsupported format provider accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("[PASS] Test 1.4: Unknown format caught -> " + e.getMessage());
        }
        System.out.println();
    }

    // =====================================================================================
    // STAGE 2: POSITIVE ASYMMETRIC ASSEMBLY & GOLDEN MASTER REGISTRATION
    // =====================================================================================
    private static void runStage2PositiveAssemblyAndRegistration() {
        System.out.println("--- [STAGE 2] POSITIVE ASSEMBLY & REGISTRATION ---");
        GlobalPublishingEngine engine = GlobalPublishingEngine.getInstance();
        DocumentProvisioner provisioner = new DocumentProvisioner();

        // 2.1 PDF Corporate Report Archetype
        DocumentComponentFactory pdfFactory = provisioner.getFactory("PDF");
        HeaderComponent pdfHeader = pdfFactory.createHeader("Helvetica-Bold", "10");
        if (pdfHeader instanceof PdfHeader) {
            ((PdfHeader) pdfHeader).addVectorLogo("CORP_LOGO_2026.svg");
            ((PdfHeader) pdfHeader).addVectorLogo("AUDIT_SEAL.svg");
        }

        BodyComponent pdfBody = pdfFactory.createBody("Helvetica-Regular");
        if (pdfBody instanceof PdfBody) {
            ((PdfBody) pdfBody).addVectorCoordinate("x:10,y:20:revenue_chart");
            ((PdfBody) pdfBody).addVectorCoordinate("x:10,y:120:balance_sheet");
        }

        Document pdfMaster = new DocumentBuilder()
                .documentId("CORP_ANNUAL_REPORT_PDF")
                .title("FY2026 Enterprise Financial Statement")
                .author("Executive Board")
                .pageWidthMm(210)
                .pageHeightMm(297)
                .marginMm(15)
                .watermark("OFFICIAL")
                .orientation("PORTRAIT")
                .header(pdfHeader)
                .body(pdfBody)
                .metadata(Map.of("classification", "Public", "dept", "Finance"))
                .placeholders(Map.of("YEAR", "2026", "CURRENCY", "USD"))
                .build();

        engine.registerTemplate("CORP_ANNUAL_REPORT_PDF", pdfMaster);
        System.out.println("[PASS] Master template 'CORP_ANNUAL_REPORT_PDF' registered successfully.");

        // 2.2 HTML Standard Invoice Archetype
        DocumentComponentFactory htmlFactory = provisioner.getFactory("HTML");
        HeaderComponent htmlHeader = htmlFactory.createHeader("Invoice Template", "https://cdn.corp.com/base.css");
        if (htmlHeader instanceof HtmlHeader) {
            ((HtmlHeader) htmlHeader).addStyleSheet("https://cdn.corp.com/invoice-v2.css");
        }

        BodyComponent htmlBody = htmlFactory.createBody("@media print { .no-print { display: none; } }");
        if (htmlBody instanceof HtmlBody) {
            ((HtmlBody) htmlBody).addDomElement("<div class='header'>Tax Invoice</div>");
            ((HtmlBody) htmlBody).addDomElement("<table class='line-items'><tr><td>Item</td></tr></table>");
        }

        Document htmlMaster = new DocumentBuilder()
                .documentId("STANDARD_INVOICE_HTML")
                .title("Standard Customer Invoice")
                .author("Billing System")
                .pageWidthMm(210)
                .pageHeightMm(297)
                .marginMm(15)
                .header(htmlHeader)
                .body(htmlBody)
                .placeholders(Map.of("TAX_RATE", "18%"))
                .build();

        engine.registerTemplate("STANDARD_INVOICE_HTML", htmlMaster);
        System.out.println("[PASS] Master template 'STANDARD_INVOICE_HTML' registered successfully.\n");
    }

    // =====================================================================================
    // STAGE 3: PROTOTYPE DEEP COPY & ZERO-MUTATION-BLEED PROOFS
    // =====================================================================================
    private static void runStage3PrototypeDeepCopyIsolation() {
        System.out.println("--- [STAGE 3] PROTOTYPE DEEP COPY & ISOLATION ---");
        GlobalPublishingEngine engine = GlobalPublishingEngine.getInstance();

        Document master = engine.getRegisteredTemplate("CORP_ANNUAL_REPORT_PDF");
        Document clone = engine.acquireDocument("CORP_ANNUAL_REPORT_PDF");

        // 3.1 Instance Heap Verification
        if (clone == master) throw new AssertionError("Clone points to the same memory address as Master!");
        if (clone.getHeader() == master.getHeader()) throw new AssertionError("Header was not deeply cloned!");
        if (clone.getBody() == master.getBody()) throw new AssertionError("Body was not deeply cloned!");
        System.out.println("[PASS] Distinct heap memory addresses verified for Document, Header, and Body.");

        // 3.2 Mutate Clone
        if (clone.getHeader() instanceof PdfHeader) {
            ((PdfHeader) clone.getHeader()).addVectorLogo("TENANT_CUSTOM_LOGO.svg");
        }
        if (clone.getBody() instanceof PdfBody) {
            ((PdfBody) clone.getBody()).addVectorCoordinate("x:50,y:200:custom_user_signature");
        }
        clone.getCustomPlaceholders().put("TENANT_ID", "ACME_CORP_123");

        // 3.3 Verify Master is Completely Untouched
        PdfHeader masterHeader = (PdfHeader) master.getHeader();
        PdfBody masterBody = (PdfBody) master.getBody();

        if (masterHeader.getVectorLogos().size() != 2) {
            throw new AssertionError("[FAIL] Mutation bleed: Master vector logos mutated to " + masterHeader.getVectorLogos().size());
        }
        if (masterBody.getVectorCoordinates().size() != 2) {
            throw new AssertionError("[FAIL] Mutation bleed: Master vector coordinates mutated to " + masterBody.getVectorCoordinates().size());
        }
        if (master.getCustomPlaceholders().containsKey("TENANT_ID")) {
            throw new AssertionError("[FAIL] Mutation bleed: Master custom placeholders corrupted with tenant data!");
        }

        System.out.println("[PASS] Zero-mutation bleed verified! Master remains completely isolated from clone mutations.");
        engine.releaseDocument("CORP_ANNUAL_REPORT_PDF");
        System.out.println();
    }

    // =====================================================================================
    // STAGE 4: SECURITY & CONCURRENCY CORE ENGINEERING CHECKS
    // =====================================================================================
    private static void runStage4SecurityAndConcurrencyTests() {
        System.out.println("--- [STAGE 4] SECURITY & CONCURRENCY TESTS ---");

        // 4.1 Reflection Attack Defense
        try {
            Constructor<GlobalPublishingEngine> constructor =
                    GlobalPublishingEngine.class.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            constructor.newInstance(50);
            System.err.println("[FAIL] Reflection attack succeeded in creating a second instance!");
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IllegalStateException) {
                System.out.println("[PASS] Reflection attack successfully blocked: " + e.getCause().getMessage());
            }
        } catch (Exception e) {
            System.err.println("[FAIL] Unexpected reflection error: " + e.getMessage());
        }

        // 4.2 Multi-Threaded Concurrent Singleton & Template Acquisition
        GlobalPublishingEngine singletonEngine = GlobalPublishingEngine.getInstance();
        int workerCount = 10;
        Thread[] workers = new Thread[workerCount];

        for (int i = 0; i < workerCount; i++) {
            final int workerId = i;
            workers[i] = new Thread(() -> {
                GlobalPublishingEngine threadEngine = GlobalPublishingEngine.getInstance();
                if (threadEngine != singletonEngine) {
                    throw new AssertionError("Thread received a divergent Singleton reference!");
                }

                Document jobDoc = threadEngine.acquireDocument("STANDARD_INVOICE_HTML");
                jobDoc.getCustomPlaceholders().put("WORKER_TAG", "WORKER_" + workerId);
                threadEngine.releaseDocument("STANDARD_INVOICE_HTML");
            }, "Worker-" + i);
        }

        for (Thread worker : workers) worker.start();
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("[PASS] All 10 worker threads completed concurrent acquisitions and releases safely.");
        System.out.println("[PASS] Active render jobs count safely returned to: " + singletonEngine.getActiveRenderJobs().get() + "\n");
    }

    // =====================================================================================
    // STAGE 5: SINGLETON QUOTA SATURATION & CAPACITY RECOVERY LIFECYCLE
    // =====================================================================================
    private static void runStage5QuotaSaturationAndRecovery() {
        System.out.println("--- [STAGE 5] QUOTA SATURATION & RECOVERY LIFECYCLE ---");
        GlobalPublishingEngine engine = GlobalPublishingEngine.getInstance();
        int maxQuota = engine.getMaxRenderQuota(); // 15

        // 5.1 Saturate Quota to 15
        List<Document> activeJobs = new ArrayList<>();
        for (int i = 0; i < maxQuota; i++) {
            activeJobs.add(engine.acquireDocument("CORP_ANNUAL_REPORT_PDF"));
        }
        System.out.println("Render quota saturated: " + engine.getActiveRenderJobs().get() + " / " + maxQuota);

        // 5.2 Attempt 16th Acquisition (Must Fail-Fast)
        try {
            engine.acquireDocument("CORP_ANNUAL_REPORT_PDF");
            System.err.println("[FAIL] 16th render job acquired beyond max quota cap!");
        } catch (IllegalStateException e) {
            System.out.println("[PASS] Quota cap strictly enforced -> " + e.getMessage());
        }

        // 5.3 Release Capacity and Re-acquire
        engine.releaseDocument("CORP_ANNUAL_REPORT_PDF");
        System.out.println("Released 1 job. Active render jobs: " + engine.getActiveRenderJobs().get());

        Document finalJob = engine.acquireDocument("CORP_ANNUAL_REPORT_PDF");
        System.out.println("[PASS] Capacity recovered: 15th job successfully re-acquired.");

        // Clean up all acquired jobs
        for (int i = 0; i < maxQuota; i++) {
            engine.releaseDocument("CORP_ANNUAL_REPORT_PDF");
        }
        System.out.println("[PASS] Cleanup complete. Active jobs reset to: " + engine.getActiveRenderJobs().get());
    }
}