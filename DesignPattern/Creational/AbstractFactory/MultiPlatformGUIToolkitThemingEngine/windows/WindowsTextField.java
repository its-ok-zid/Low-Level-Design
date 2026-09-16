package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public class WindowsTextField implements TextField {
    private String content = "";

    @Override
    public void render() {
        System.out.println("[Windows] Rendering Win32 input box with placeholder. Text: '" + content + "'");
    }

    @Override
    public void setText(String text) {
        this.content = (text != null) ? text : "";
        System.out.println("[Windows] WindowsTextField updated to: '" + this.content + "'");
    }
}