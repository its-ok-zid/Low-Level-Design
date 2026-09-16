package DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.Creational.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
}
