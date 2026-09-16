package DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine;

import java.util.Objects;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.factory.GUIFactory;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Button;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.Checkbox;
import DesignPattern.AbstractFactory.MultiPlatformGUIToolkitThemingEngine.product.TextField;

public class GUIService {
    private final Button button;
    private final Checkbox checkbox;
    private final TextField textField;

    public GUIService(GUIFactory factory) {
        Objects.requireNonNull(factory, "GUIFactory must not be null.");
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
        this.textField = factory.createTextField();
    }

    public void renderUI() {
        button.render();
        checkbox.render();
        textField.render();
    }

    public void clickButton() {
        button.onClick();
    }

    public void toggleCheckbox() {
        checkbox.toggle();
    }

    public void enterText(String text) {
        textField.setText(text);
    }
}