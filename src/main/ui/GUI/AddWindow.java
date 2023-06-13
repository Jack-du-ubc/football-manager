package ui.GUI;

import model.League;

import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class AddWindow implements ActionListener {
    private static final String JSON_STORE = "./data/GUI/Database.json";
    private List<League> leagueList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JTextField nameField;
    private JButton addButton;

    // constructs a new window for adding league
    AddWindow() throws FileNotFoundException {
        // Initiate frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new FlowLayout());

        // Initiate name field
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(165, 25));

        // Initiate add button
        addButton = new JButton("Add league");
        addButton.addActionListener(this);

        // Add components to the frame
        frame.add(addButton);
        frame.add(nameField);

        frame.pack();

        frame.setVisible(true);
    }

    public List<League> getLeagues(){
        return this.leagueList;
    }

    @Override
    // Operation for add button
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            String leagueName = nameField.getText();

            if (validateLeagueName(leagueName)) {
                int option = JOptionPane.showConfirmDialog(addButton, "Are you sure you want to add " + leagueName + "?");
                handleOption(option);

            } else {
                JOptionPane.showMessageDialog(frame, "Invalid league name. Please enter a valid name.");
            }
        }
    }

    // Check if the league names are valid
    private boolean validateLeagueName(String leagueName) {
        // Todo: add validation logic
        return leagueName != null && !leagueName.isEmpty();
    }

    // Handle add options
    private void handleOption(int option) {

        if (option == JOptionPane.YES_OPTION) {

            saveLeague();
            disableComponents();
            closeFrame();

        } else if (option == JOptionPane.NO_OPTION) {
            
            disableComponents();
            closeFrame();
        }
    }

    // Save to database
    private void saveLeague() {
        String leagueName = nameField.getText();
        League league = new League(leagueName);

        try {
            jsonReader = new JsonReader(JSON_STORE);
            jsonWriter = new JsonWriter(JSON_STORE);

            try {
                leagueList = jsonReader.readLeagues();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
            }

            leagueList.add(league);

            jsonWriter.open();
            jsonWriter.write(leagueList);
            jsonWriter.close();

            JOptionPane.showMessageDialog(frame, "Saved " + leagueName + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to write to file: " + JSON_STORE);
        }
    }

    private void disableComponents() {
        addButton.setEnabled(false);
        nameField.setEnabled(false);
    }

    private void closeFrame() {
        frame.dispose();
    }

}
