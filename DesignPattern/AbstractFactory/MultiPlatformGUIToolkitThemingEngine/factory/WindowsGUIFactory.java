package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsButton;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsCheckbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsTextField;

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
