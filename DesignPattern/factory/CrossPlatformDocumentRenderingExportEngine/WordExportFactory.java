package DesignPattern.factory.CrossPlatformDocumentRenderingExportEngine;

public class WordExportFactory implements DocumentExportPlanner {
    @Override
    public DocumentExporter createExporter() {
        return new WordDocumentExporter();
    }
}
