package DesignPattern.Creational.interview.MultiFormatEnterpriseDocumentPublishingTemplatingEngine.factory;

import java.util.ArrayList;
import java.util.List;

public class HtmlBody implements BodyComponent {
    private List<String> domElements;
    private List<String> mediaQueries;

    public HtmlBody(List<String> domElements, List<String> mediaQueries) {
        this.domElements = (domElements != null) ? domElements : new ArrayList<>();
        this.mediaQueries = (mediaQueries != null) ? mediaQueries : new ArrayList<>();
    }

    public HtmlBody(HtmlBody source, boolean deepCopy) {
        if (deepCopy) {
            this.domElements = (source.domElements != null) ? new ArrayList<>(source.domElements) : new ArrayList<>();
            this.mediaQueries = (source.mediaQueries != null) ? new ArrayList<>(source.mediaQueries) : new ArrayList<>();
        } else {
            this.domElements = source.domElements;
            this.mediaQueries = source.mediaQueries;
        }
    }

    public void addDomElement(String element) {
        if (this.domElements == null) {
            this.domElements = new ArrayList<>();
        }
        domElements.add(element);
    }

    public List<String> getDomElements() {
        return domElements;
    }

    public List<String> getMediaQueries() {
        return mediaQueries;
    }

    @Override
    public BodyComponent shallowCopy() {
        return new HtmlBody(this, false);
    }

    @Override
    public BodyComponent deepCopy() {
        return new HtmlBody(this, true);
    }

    @Override
    public String getFormat() {
        return "HTML";
    }

    @Override
    public void renderBodyPreview() {
        System.out.println("Rendering HTML Body Preview:");
        System.out.println("DOM Elements: " + getDomElements());
        System.out.println("Media Queries: " + getMediaQueries());
    }
}