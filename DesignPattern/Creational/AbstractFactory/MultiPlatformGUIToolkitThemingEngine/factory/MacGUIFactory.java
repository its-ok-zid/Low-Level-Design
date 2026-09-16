package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac.MacButton;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac.MacCheckbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac.MacTextField;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public class MacGUIFactory implements GUIFactory{
    @Override
    public Button createButton() {
        return new MacButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacCheckbox();
    }

    @Override
    public TextField createTextField() {
        return new MacTextField();
    }
}
