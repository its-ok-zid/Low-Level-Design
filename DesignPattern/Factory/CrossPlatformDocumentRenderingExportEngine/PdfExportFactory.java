package DesignPattern.Factory.CrossPlatformDocumentRenderingExportEngine;

public class PdfExportFactory implements DocumentExportPlanner {
    @Override
    public DocumentExporter createExporter() {
        return new PdfDocumentExporter();
    }
}
