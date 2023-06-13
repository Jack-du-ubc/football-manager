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
import java.util.Iterator;

public class DeleteWindow implements ActionListener {
    private static final String JSON_STORE = "./data/GUI/Database.json";
    private List <League> leagueList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JTextField nameField;
    private JLabel intLabel;
    private JButton deleteButton; 

    // constructs a new window for deleting league
    DeleteWindow() throws FileNotFoundException{
        // Initiate frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(new FlowLayout());

        // Initiate name field
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(165, 25));

        // Initiate label
        intLabel = new JLabel();

        // Initiate delete button
        deleteButton = new JButton("Delete league");
        deleteButton.addActionListener(this);

        // Add components to the frame
        frame.add(deleteButton);
        frame.add(nameField);
        frame.add(intLabel);

        frame.pack();

        frame.setVisible(true);
    }

    public List<League> getLeagues(){
        return this.leagueList;
    }

    @Override
    // Operation for sell button
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == deleteButton) {
            int option = JOptionPane.showConfirmDialog(deleteButton, "Are you sure you want to delete " + nameField.getText() + "?");
            
            handleOption(option);
        }
    }

    // Handle delete options
    private void handleOption(int option) {

        if (option == JOptionPane.YES_OPTION) {

            deleteLeague();
            disableComponents();
            closeFrame();

        } else if (option == JOptionPane.NO_OPTION) {

            disableComponents();
            closeFrame();
        }
    }

    // delete league from database and save
    private void deleteLeague(){

        String leagueName = nameField.getText();

        try {
            jsonReader = new JsonReader(JSON_STORE);
            jsonWriter = new JsonWriter(JSON_STORE);

            try{
                leagueList = jsonReader.readLeagues();

            } catch (IOException e){
                JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
                return;
            }

            int initialLeagueCount = leagueList.size();
            boolean leagueFound = false;
            Iterator<League> iterator = leagueList.iterator();

            while (iterator.hasNext()) {
                League l = iterator.next();
                if (l.getLeagueName().equals(leagueName)) {
                    iterator.remove();
                    leagueFound = true;
                    break;
                }
            }

            if (!leagueFound) {
                JOptionPane.showMessageDialog(frame, "Unable to find league in: " + JSON_STORE);
            }

            if (leagueList.size() == initialLeagueCount) {
                JOptionPane.showMessageDialog(frame, "No changes made to the league list.");
                return;
            }
            
            jsonWriter.open();
            jsonWriter.write(leagueList);
            jsonWriter.close();

            JOptionPane.showMessageDialog(frame, "Deleted " + leagueName + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to write to file: " + JSON_STORE);
        }
    }

    private void disableComponents() {
        deleteButton.setEnabled(false);
        nameField.setEnabled(false);
    }
    
    private void closeFrame() {
        frame.dispose();
    }

}