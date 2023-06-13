package ui.CLI;

import exceptions.IllogicalException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new ManagerApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
            e.printStackTrace();
        } catch (IllogicalException e) {
            System.out.println("IllogicalException occurred");
            e.printStackTrace();
        }
    }
}

