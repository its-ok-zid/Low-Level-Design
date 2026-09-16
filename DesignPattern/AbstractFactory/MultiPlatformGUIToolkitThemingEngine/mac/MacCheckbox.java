package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;

public class MacCheckbox implements Checkbox {
    private boolean checked = false;

    @Override
    public void render() {
        System.out.println("[macOS] Rendering Cocoa rounded checkbox. Checked: " + checked);
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
        System.out.println("[macOS] MacCheckbox toggled. Current state: " + checked);
    }
}