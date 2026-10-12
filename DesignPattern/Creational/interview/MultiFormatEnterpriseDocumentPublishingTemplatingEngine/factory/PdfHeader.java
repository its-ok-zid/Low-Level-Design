package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import java.util.ArrayList;
import java.util.List;

public class PdfHeader implements HeaderComponent {

    private final String fontToken;
    private final int pageNumberMarginMm;
    private List<String> vectorLogos;

    public PdfHeader(String fontToken, int pageNumberMarginMm, List<String> vectorLogos) {
        this.fontToken = fontToken;
        this.pageNumberMarginMm = pageNumberMarginMm;
        this.vectorLogos = (vectorLogos != null) ? vectorLogos : new ArrayList<>();
    }

    public PdfHeader(PdfHeader source, boolean deepCopy) {
        this.fontToken = source.fontToken;
        this.pageNumberMarginMm = source.pageNumberMarginMm;
        if (deepCopy) {
            this.vectorLogos = (source.vectorLogos != null) ? new ArrayList<>(source.vectorLogos) : new ArrayList<>();
        } else {
            this.vectorLogos = source.vectorLogos;
        }
    }

    public void addVectorLogo(String logo) {
        if (this.vectorLogos == null) {
            this.vectorLogos = new ArrayList<>();
        }
        this.vectorLogos.add(logo);
    }

    public List<String> getVectorLogos() {
        return vectorLogos;
    }

    @Override
    public HeaderComponent shallowCopy() {
        return new PdfHeader(this, false);
    }

    @Override
    public HeaderComponent deepCopy() {
        return new PdfHeader(this, true);
    }

    @Override
    public String getFormat() {
        return "PDF";
    }

    @Override
    public void renderHeaderPreview() {
        System.out.println("Rendering PDF Header Preview:");
        System.out.println("Font Token: " + fontToken);
        System.out.println("Page Number Margin (mm): " + pageNumberMarginMm);
        if (vectorLogos != null) {
            for (String logo : vectorLogos) {
                System.out.println("Vector Logo: " + logo);
            }
        }
    }
}
