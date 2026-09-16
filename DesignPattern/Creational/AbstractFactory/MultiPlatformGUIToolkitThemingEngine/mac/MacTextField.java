package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public class MacTextField implements TextField {
    private String content = "";

    @Override
    public void render() {
        System.out.println("[macOS] Rendering NSTextField with subtle inner border. Text: '" + content + "'");
    }

    @Override
    public void setText(String text) {
        this.content = (text != null) ? text : "";
        System.out.println("[macOS] MacTextField updated to: '" + this.content + "'");
    }
}