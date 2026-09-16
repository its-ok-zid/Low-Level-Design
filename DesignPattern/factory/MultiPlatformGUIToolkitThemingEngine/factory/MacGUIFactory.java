package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.mac.MacButton;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.mac.MacCheckbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.mac.MacTextField;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

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
