package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;

public class WindowsCheckbox implements Checkbox {
    private boolean checked = false;

    @Override
    public void render() {
        System.out.println("[Windows] Rendering Win32 square checkbox. Checked: " + checked);
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
        System.out.println("[Windows] WindowsCheckbox toggled. Current state: " + checked);
    }
}