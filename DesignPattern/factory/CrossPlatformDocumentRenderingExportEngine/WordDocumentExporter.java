package DesignPattern.factory.CrossPlatformDocumentRenderingExportEngine;

public class WordDocumentExporter implements DocumentExporter {
    private static final int MAX_CHAR_LIMIT = 50000;

    @Override
    public void prepareHeader(String title, String author) {
        if (title == null || title.isEmpty() || author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Title and author cannot be null or empty");
        }
        System.out.println("[Word Engine] Injecting OpenXML header markup. Title: " + title + ", Author: " + author);
    }

    @Override
    public boolean export(String documentId, String content) {
        if (documentId == null || documentId.isEmpty() || content == null || content.isEmpty()) {
            System.out.println("[Word Engine] Error: Document ID and content cannot be null or empty.");
            return false;
        }
        if (content.length() > MAX_CHAR_LIMIT) {
            System.out.println("[Word Engine] Error: Content exceeds max limit of " + MAX_CHAR_LIMIT + " characters.");
            return false;
        }
        System.out.println("[Word Engine] Packaging DOCX OpenXML document for Doc: " + documentId);
        return true;
    }
}