package DocumentEditor;

public class TextElement implements DocumentElement {

    @Override
    public void display(String type) {
        System.out.println("Entered text is:" + type);
    }
}
