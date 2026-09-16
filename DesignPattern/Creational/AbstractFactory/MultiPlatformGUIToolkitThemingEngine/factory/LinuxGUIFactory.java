package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxButton;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxCheckbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxTextField;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public class LinuxGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new LinuxCheckbox();
    }

    @Override
    public TextField createTextField() {
        return new LinuxTextField();
    }
}
