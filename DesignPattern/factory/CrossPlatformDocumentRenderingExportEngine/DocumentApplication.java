package DesignPattern.factory.CrossPlatformDocumentRenderingExportEngine;

public class DocumentApplication {
    public static void main(String[] args) {
        DocumentExportService exportService = new DocumentExportService();

        System.out.println("Starting document export process...");
        System.out.println("--------------------------------------------------");

        // Document export for PDF format
        DocumentExportPlanner pdfExportPlanner = new PdfExportFactory();
        exportService.executeExport(pdfExportPlanner, "doc123", "Sample PDF Document", "John Doe", "This is the content of the PDF document.");
        System.out.println("--------------------------------------------------");

        //Document export for Word format
        DocumentExportPlanner wordExportPlanner = new WordExportFactory();
        exportService.executeExport(wordExportPlanner, "doc456", "Sample Word Document", "Jane Smith", "This is the content of the Word document.");
        System.out.println("--------------------------------------------------");

        //Document export for MarkDown format
        DocumentExportPlanner markDownExportPlanner = new MarkdownExportFactory();
        exportService.executeExport(markDownExportPlanner, "doc789", "Sample Markdown Document", "Alice Johnson", "This is the content of the Markdown document.");
    }

}
