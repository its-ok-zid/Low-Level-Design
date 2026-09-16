package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
}
