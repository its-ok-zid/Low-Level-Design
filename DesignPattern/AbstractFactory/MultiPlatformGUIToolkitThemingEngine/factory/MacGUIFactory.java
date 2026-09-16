package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac.MacButton;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac.MacCheckbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.mac.MacTextField;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

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
