package DocumentEditor;

import java.util.Scanner;

public class ImageElement implements DocumentElement {

    @Override
    public void display(String type) {
        System.out.println("Image path is :" + type);
    }
}
