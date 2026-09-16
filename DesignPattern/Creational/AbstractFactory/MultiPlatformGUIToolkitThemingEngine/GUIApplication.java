package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory.GUIFactory;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory.LinuxGUIFactory;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory.MacGUIFactory;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory.WindowsGUIFactory;

public class GUIApplication {
    public static void main(String[] args) {
        System.out.println("--- Booting Windows Desktop Environment ---");
        GUIFactory winFactory = new WindowsGUIFactory();
        GUIService winService = new GUIService(winFactory);
        winService.renderUI();
        winService.enterText("Hello Windows Fluent");
        winService.toggleCheckbox();
        winService.clickButton();

        System.out.println("\n--- Booting macOS Desktop Environment ---");
        GUIFactory macFactory = new MacGUIFactory();
        GUIService macService = new GUIService(macFactory);
        macService.renderUI();
        macService.enterText("Hello macOS Aqua");
        macService.toggleCheckbox();
        macService.clickButton();

        System.out.println("\n--- Booting Linux Desktop Environment ---");
        GUIFactory linuxFactory = new LinuxGUIFactory();
        GUIService linuxService = new GUIService(linuxFactory);
        linuxService.renderUI();
        linuxService.enterText("Hello Linux GTK");
        linuxService.toggleCheckbox();
        linuxService.clickButton();
    }
}