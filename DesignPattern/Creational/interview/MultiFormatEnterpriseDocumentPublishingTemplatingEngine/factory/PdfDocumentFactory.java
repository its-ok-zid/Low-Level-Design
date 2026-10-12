package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import java.util.ArrayList;

public class PdfDocumentFactory implements DocumentComponentFactory {
    @Override
    public HeaderComponent createHeader(String headerTitleOrFont, String marginOrTag) {
        int marginMm = 10;
        try {
            if (marginOrTag != null) marginMm = Integer.parseInt(marginOrTag);
        } catch (NumberFormatException ignored) {}
        return new PdfHeader(headerTitleOrFont, marginMm, new ArrayList<>());
    }

    @Override
    public BodyComponent createBody(String fontOrQuery) {
        return new PdfBody(fontOrQuery, new ArrayList<>());
    }
}