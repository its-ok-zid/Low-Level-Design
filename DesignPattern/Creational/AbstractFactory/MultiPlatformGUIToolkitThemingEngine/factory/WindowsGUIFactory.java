package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsButton;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsCheckbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsTextField;

public class WindowsGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }

    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }
}
