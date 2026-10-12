package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import java.util.ArrayList;
import java.util.List;

public class HtmlDocumentFactory implements DocumentComponentFactory {
    @Override
    public HeaderComponent createHeader(String headerTitleOrFont, String marginOrTag) {
        List<String> initialStyles = new ArrayList<>();
        if (marginOrTag != null && !marginOrTag.isEmpty()) {
            initialStyles.add(marginOrTag);
        }
        return new HtmlHeader(headerTitleOrFont, initialStyles);
    }

    @Override
    public BodyComponent createBody(String fontOrQuery) {
        List<String> mediaQueries = new ArrayList<>();
        if (fontOrQuery != null && !fontOrQuery.isBlank()) {
            mediaQueries.add(fontOrQuery);
        }
        return new HtmlBody(new ArrayList<>(), mediaQueries);
    }
}
