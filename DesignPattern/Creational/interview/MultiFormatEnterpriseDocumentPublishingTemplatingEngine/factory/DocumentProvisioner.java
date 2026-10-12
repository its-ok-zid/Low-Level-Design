package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

public class DocumentProvisioner {
    public DocumentComponentFactory getFactory(String format) {
        if (format.equalsIgnoreCase("HTML")) {
            return new HtmlDocumentFactory();
        } else if (format.equalsIgnoreCase("PDF")) {
            return new PdfDocumentFactory();
        } else {
            throw new IllegalArgumentException("Unsupported document format: " + format);
        }
    }
}
