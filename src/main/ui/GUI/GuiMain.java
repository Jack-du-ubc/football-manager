package ui.GUI;

import java.io.FileNotFoundException;

public class GuiMain {
    public static void main(String[] args) {
        try {
            new ManagerFrame();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}
