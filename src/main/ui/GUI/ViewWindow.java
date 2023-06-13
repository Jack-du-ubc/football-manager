package ui.GUI;

import model.League;

import persistence.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewWindow implements ActionListener {
    private static final String JSON_STORE = "./data/GUI/Database.json";
    private List<League> leagueList;
    private JList<String> nameList;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JButton closeButton;

    ViewWindow() throws FileNotFoundException {
        initData();

        // Initiate frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initiate panel
        panel = new JPanel(new BorderLayout());

        // Initiate scrollpane
        scrollPane = new JScrollPane(nameList);

        // Initiate button
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);

        // Add components to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        frame.pack();

        frame.setVisible(true);
    }

    // initialize data of leagues from jsonreader
    private void initData() {
        try {
            jsonReader = new JsonReader(JSON_STORE);
            leagueList = jsonReader.readLeagues();

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (League league : leagueList) {
                listModel.addElement(league.getLeagueName());
            }

            nameList = new JList<>(listModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to read from file: " + JSON_STORE);
        
            leagueList = new ArrayList<>();
            nameList = new JList<>();
            return;
        }
    }

    @Override
    // Operation for close button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            disableComponents();
            closeFrame();
        }
    }

    private void disableComponents() {
        closeButton.setEnabled(false);
        nameList.setEnabled(false);
    }

    private void closeFrame() {
        frame.dispose();
    }
    
}