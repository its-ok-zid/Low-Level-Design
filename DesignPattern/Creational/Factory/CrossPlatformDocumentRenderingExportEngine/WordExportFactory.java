package DesignPattern.Creational.Factory.CrossPlatformDocumentRenderingExportEngine;

public class WordExportFactory implements DocumentExportPlanner {
    @Override
    public DocumentExporter createExporter() {
        return new WordDocumentExporter();
    }
}
