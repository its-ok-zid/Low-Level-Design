package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.windows;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Button;

public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("[Windows] Rendering Win32 native flat button with Fluent Design theme.");
    }

    @Override
    public void onClick() {
        System.out.println("[Windows] WindowsButton handled WM_LBUTTONDOWN event.");
    }
}