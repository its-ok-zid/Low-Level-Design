package DesignPattern.Factory.CrossPlatformDocumentRenderingExportEngine;

public interface DocumentExporter {
    void prepareHeader(String title, String author);

    boolean export(String documentId, String content);
}