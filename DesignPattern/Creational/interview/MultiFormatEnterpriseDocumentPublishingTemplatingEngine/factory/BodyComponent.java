package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.prototype.Prototype;

public interface BodyComponent extends Prototype<BodyComponent> {
    String getFormat();

    void renderBodyPreview();
}