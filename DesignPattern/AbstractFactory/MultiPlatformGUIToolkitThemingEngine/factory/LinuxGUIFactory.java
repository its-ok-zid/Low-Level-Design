package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxButton;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxCheckbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxTextField;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

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
