package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.linux;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;

public class LinuxCheckbox implements Checkbox {
    private boolean checked = false;

    @Override
    public void render() {
        System.out.println("[Linux] Rendering GTK+ check-button widget. Checked: " + checked);
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
        System.out.println("[Linux] LinuxCheckbox toggled. Current state: " + checked);
    }
}