package DesignPattern.factory.CrossPlatformDocumentRenderingExportEngine;

public class MarkdownExportFactory implements DocumentExportPlanner {
    @Override
    public DocumentExporter createExporter() {
        return new MarkdownDocumentExporter();
    }
}
