package DesignPattern.factory.CrossPlatformDocumentRenderingExportEngine;

public class DocumentExportService {
    public void executeExport(DocumentExportPlanner planner, String documentId, String title, String author, String content) {
        if (planner == null || documentId == null || title == null || author == null || content == null) {
            throw new IllegalArgumentException("Invalid export parameters provided.");
        }

        DocumentExporter exporter = planner.createExporter();
        exporter.prepareHeader(title, author);
        boolean success = exporter.export(documentId, content);

        if (success) {
            System.out.println("Document exported successfully: " + documentId + "\n");
        } else {
            System.out.println("Document export failed: " + documentId + "\n");
        }
    }
}