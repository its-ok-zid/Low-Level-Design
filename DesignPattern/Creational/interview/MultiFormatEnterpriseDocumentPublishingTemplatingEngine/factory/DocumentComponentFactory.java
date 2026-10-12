package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

public interface DocumentComponentFactory {
    HeaderComponent createHeader(String headerTitleOrFont, String marginOrTag);

    BodyComponent createBody(String fontOrQuery);
}
