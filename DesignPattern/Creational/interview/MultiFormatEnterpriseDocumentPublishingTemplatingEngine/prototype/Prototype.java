package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.prototype;

public interface Prototype<T> {
    T shallowCopy();

    T deepCopy();
}
