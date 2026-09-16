package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;

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