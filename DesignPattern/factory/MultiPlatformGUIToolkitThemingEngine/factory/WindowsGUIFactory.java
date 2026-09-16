package DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.factory;

import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.product.TextField;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsButton;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsCheckbox;
import DesignPattern.factory.MultiPlatformGUIToolkitThemingEngine.windows.WindowsTextField;

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
