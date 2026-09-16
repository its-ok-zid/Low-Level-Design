package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public class LinuxTextField implements TextField {
    private String content = "";

    @Override
    public void render() {
        System.out.println("[Linux] Rendering GtkEntry with default cursor. Text: '" + content + "'");
    }

    @Override
    public void setText(String text) {
        this.content = (text != null) ? text : "";
        System.out.println("[Linux] LinuxTextField updated to: '" + this.content + "'");
    }
}