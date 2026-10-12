package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import java.util.ArrayList;
import java.util.List;

public class HtmlHeader implements HeaderComponent {

    private final String semanticTitleTag;

    private List<String> styleSheets;

    public HtmlHeader(String semanticTitleTag, List<String> styleSheets) {
        this.semanticTitleTag = semanticTitleTag;
        this.styleSheets = (styleSheets != null) ? styleSheets : new ArrayList<>();
    }

    public HtmlHeader(HtmlHeader source, boolean deepCopy) {
        this.semanticTitleTag = source.semanticTitleTag;
        if (deepCopy) {
            this.styleSheets = (source.styleSheets != null) ? new ArrayList<>(source.styleSheets) : new ArrayList<>();
        } else {
            this.styleSheets = source.styleSheets;
        }
    }

    public void addStyleSheet(String href) {
        if (this.styleSheets == null) {
            this.styleSheets = new ArrayList<>();
        }
        this.styleSheets.add(href);
    }

    public List<String> getStyleSheets() {
        return styleSheets;
    }

    @Override
    public HeaderComponent shallowCopy() {
        return new HtmlHeader(this, false);
    }

    @Override
    public HeaderComponent deepCopy() {
        return new HtmlHeader(this, true);
    }

    @Override
    public String getFormat() {
        return "HTML";
    }

    @Override
    public void renderHeaderPreview() {
        System.out.println("Rendering HTML Header Preview:");
        System.out.println("<head>");
        System.out.println("  <title>" + semanticTitleTag + "</title>");
        if (styleSheets != null) {
            for (String styleSheet : styleSheets) {
                System.out.println("  <link rel=\"stylesheet\" href=\"" + styleSheet + "\">");
            }
        }
        System.out.println("</head>");
    }
}
