package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.prototype.Prototype;

public interface HeaderComponent extends Prototype<HeaderComponent> {

    String getFormat();

    void renderHeaderPreview();
}