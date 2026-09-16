package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;

public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("[macOS] Rendering Cocoa rounded Aqua glass button with drop shadow.");
    }

    @Override
    public void onClick() {
        System.out.println("[macOS] MacButton triggered NSButton action target.");
    }
}