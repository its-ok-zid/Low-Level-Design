package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.builder;

import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory.BodyComponent;
import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory.HeaderComponent;
import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.prototype.Prototype;

import java.util.HashMap;
import java.util.Map;

public class Document implements Prototype<Document> {
    private final String documentId;
    private final String title;
    private final String author;
    private final int pageWidthMm;
    private final int pageHeightMm;
    private final int marginMm;
    private final String watermark;
    private final String orientation;
    private HeaderComponent header;
    private BodyComponent body;
    private Map<String, String> metadata;
    private Map<String, String> customPlaceholders;

    public Document(String documentId, String title, String author, int pageWidthMm, int pageHeightMm,
                    int marginMm, String watermark, String orientation, HeaderComponent header,
                    BodyComponent body, Map<String, String> metadata, Map<String, String> customPlaceholders) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.pageWidthMm = pageWidthMm;
        this.pageHeightMm = pageHeightMm;
        this.marginMm = marginMm;
        this.watermark = watermark;
        this.orientation = orientation;
        this.header = header;
        this.body = body;
        this.metadata = (metadata != null) ? new HashMap<>(metadata) : new HashMap<>();
        this.customPlaceholders = (customPlaceholders != null) ? new HashMap<>(customPlaceholders) : new HashMap<>();
    }

    public Document(Document source, boolean deepCopy) {
        this.documentId = source.documentId;
        this.title = source.title;
        this.author = source.author;
        this.pageWidthMm = source.pageWidthMm;
        this.pageHeightMm = source.pageHeightMm;
        this.marginMm = source.marginMm;
        this.watermark = source.watermark;
        this.orientation = source.orientation;

        if (deepCopy) {
            this.header = (source.header != null) ? source.header.deepCopy() : null;
            this.body = (source.body != null) ? source.body.deepCopy() : null;
            this.metadata = (source.metadata != null) ? new HashMap<>(source.metadata) : new HashMap<>();
            this.customPlaceholders = (source.customPlaceholders != null) ? new HashMap<>(source.customPlaceholders) : new HashMap<>();
        } else {
            this.header = source.header;
            this.body = source.body;
            this.metadata = source.metadata;
            this.customPlaceholders = source.customPlaceholders;
        }
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageWidthMm() {
        return pageWidthMm;
    }

    public int getPageHeightMm() {
        return pageHeightMm;
    }

    public int getMarginMm() {
        return marginMm;
    }

    public String getWatermark() {
        return watermark;
    }

    public String getOrientation() {
        return orientation;
    }

    public HeaderComponent getHeader() {
        return header;
    }

    public BodyComponent getBody() {
        return body;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Map<String, String> getCustomPlaceholders() {
        return customPlaceholders;
    }

    @Override
    public Document shallowCopy() {
        return new Document(this, false);
    }

    @Override
    public Document deepCopy() {
        return new Document(this, true);
    }
}