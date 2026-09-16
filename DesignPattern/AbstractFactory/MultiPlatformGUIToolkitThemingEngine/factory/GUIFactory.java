package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
}
