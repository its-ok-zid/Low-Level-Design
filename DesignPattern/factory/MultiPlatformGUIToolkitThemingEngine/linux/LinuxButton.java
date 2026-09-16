package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.linux;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Button;

public class LinuxButton implements Button {
    @Override
    public void render() {
        System.out.println("[Linux] Rendering GTK+ Adwaita theme flat button.");
    }

    @Override
    public void onClick() {
        System.out.println("[Linux] LinuxButton emitted 'clicked' GSignal.");
    }
}