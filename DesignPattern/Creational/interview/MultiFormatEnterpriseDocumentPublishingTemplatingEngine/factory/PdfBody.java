package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import java.util.ArrayList;
import java.util.List;

public class PdfBody implements BodyComponent {
    private final String embeddedFont;
    private List<String> vectorCoordinates;

    public PdfBody(String embeddedFont, List<String> vectorCoordinates) {
        this.embeddedFont = embeddedFont;
        this.vectorCoordinates = (vectorCoordinates != null) ? vectorCoordinates : new ArrayList<>();
    }

    public PdfBody(PdfBody source, boolean deepCopy) {
        this.embeddedFont = source.embeddedFont;
        if (deepCopy) {
            this.vectorCoordinates = (source.vectorCoordinates != null) ? new ArrayList<>(source.vectorCoordinates) : new ArrayList<>();
        } else {
            this.vectorCoordinates = source.vectorCoordinates;
        }
    }

    public void addVectorCoordinate(String coordinate) {
        vectorCoordinates.add(coordinate);
    }

    public List<String> getVectorCoordinates() {
        return vectorCoordinates;
    }

    @Override
    public BodyComponent shallowCopy() {
        return new PdfBody(this, false);
    }

    @Override
    public BodyComponent deepCopy() {
        return new PdfBody(this, true);
    }

    @Override
    public String getFormat() {
        return "PDF";
    }

    @Override
    public void renderBodyPreview() {
        System.out.println("Rendering PDF body preview with embedded font: " + embeddedFont);
        System.out.println("Vector coordinates: " + getVectorCoordinates());
    }
}
