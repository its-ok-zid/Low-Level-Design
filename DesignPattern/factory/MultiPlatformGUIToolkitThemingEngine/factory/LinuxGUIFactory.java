package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxButton;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxCheckbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.linux.LinuxTextField;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

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
