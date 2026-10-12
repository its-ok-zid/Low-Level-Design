package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.builder;

import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory.BodyComponent;
import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory.HeaderComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DocumentBuilder {
    private String documentId;
    private String title;
    private String author;
    private int pageWidthMm = 210;
    private int pageHeightMm = 297;
    private int marginMm = 15;
    private String watermark;
    private String orientation = "PORTRAIT";
    private HeaderComponent header;
    private BodyComponent body;
    private Map<String, String> metadata = new HashMap<>();
    private Map<String, String> customPlaceholders = new HashMap<>();

    public DocumentBuilder documentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public DocumentBuilder title(String title) {
        this.title = title;
        return this;
    }

    public DocumentBuilder author(String author) {
        this.author = author;
        return this;
    }

    public DocumentBuilder pageWidthMm(int pageWidthMm) {
        this.pageWidthMm = pageWidthMm;
        return this;
    }

    public DocumentBuilder pageHeightMm(int pageHeightMm) {
        this.pageHeightMm = pageHeightMm;
        return this;
    }

    public DocumentBuilder marginMm(int marginMm) {
        this.marginMm = marginMm;
        return this;
    }

    public DocumentBuilder watermark(String watermark) {
        this.watermark = watermark;
        return this;
    }

    public DocumentBuilder orientation(String orientation) {
        this.orientation = orientation;
        return this;
    }

    public DocumentBuilder header(HeaderComponent header) {
        this.header = header;
        return this;
    }

    public DocumentBuilder body(BodyComponent body) {
        this.body = body;
        return this;
    }

    public DocumentBuilder metadata(String key, String value) {
        this.metadata.put(key, value);
        return this;
    }

    public DocumentBuilder metadata(Map<String, String> metadata) {
        if (metadata != null) {
            this.metadata.putAll(metadata);
        }
        return this;
    }

    public DocumentBuilder placeholders(String key, String value) {
        this.customPlaceholders.put(key, value);
        return this;
    }

    public DocumentBuilder placeholders(Map<String, String> customPlaceholders) {
        if (customPlaceholders != null) {
            this.customPlaceholders.putAll(customPlaceholders);
        }
        return this;
    }

    public Document build() {
        validate();
        return new Document(documentId, title, author, pageWidthMm, pageHeightMm, marginMm,
                watermark, orientation, header, body, metadata, customPlaceholders);
    }

    private void validate() {
        if (documentId == null || documentId.isBlank()) {
            throw new IllegalArgumentException("Document ID cannot be null or empty");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if (pageWidthMm <= 0) {
            throw new IllegalArgumentException("Page width must be greater than 0");
        }
        if (pageHeightMm <= 0) {
            throw new IllegalArgumentException("Page height must be greater than 0");
        }
        if (marginMm <= 0 || 2 * marginMm >= Math.min(pageWidthMm, pageHeightMm)) {
            throw new IllegalArgumentException(
                    String.format("Invalid margin (%dmm) for page dimensions %dx%dmm", marginMm, pageWidthMm, pageHeightMm));
        }
        if (header == null) {
            throw new IllegalArgumentException("Header component is mandatory");
        }
        if (body == null) {
            throw new IllegalArgumentException("Body component is mandatory");
        }
        if (!Objects.equals(header.getFormat(), body.getFormat())) {
            throw new IllegalArgumentException(
                    String.format("Header and Body formats must match: %s vs %s", header.getFormat(), body.getFormat()));
        }
    }
}