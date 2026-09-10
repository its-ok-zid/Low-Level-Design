package DesignPattern.factory.CrossPlatformDocumentRenderingExportEngine;

public class MarkdownDocumentExporter implements DocumentExporter {
    private static final int MAX_CHAR_LIMIT = 10000;

    @Override
    public void prepareHeader(String title, String author) {
        if (title == null || title.isEmpty() || author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Title and author cannot be null or empty");
        }
        System.out.println("[Markdown Engine] Inlining YAML front-matter header. Title: " + title + ", Author: " + author);
    }

    @Override
    public boolean export(String documentId, String content) {
        if (documentId == null || documentId.isEmpty() || content == null || content.isEmpty()) {
            System.out.println("[Markdown Engine] Error: Document ID and content cannot be null or empty.");
            return false;
        }
        if (content.length() > MAX_CHAR_LIMIT) {
            System.out.println("[Markdown Engine] Error: Content exceeds max limit of " + MAX_CHAR_LIMIT + " characters.");
            return false;
        }
        System.out.println("[Markdown Engine] Writing plain UTF-8 text for Doc: " + documentId);
        return true;
    }
}