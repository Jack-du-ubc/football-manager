package ui.GUI;

import model.League;

import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ManagerFrame extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu leagueMenu;
    JMenuItem saveItem;
    JMenuItem closeItem;
    JButton addButton;
    JButton deleteButton;
    JButton viewButton;
    JPanel choosePanel;
    JLabel leagueLabel;
    JLabel teamLabel;
    JTextField leagueTextField;
    JPasswordField teamPasswordField;
    JButton searchButton;
    Container container;

    private static final String JSON_STORE = "./data/GUI/Database.json";
    private List<League> leagueList;
    private JsonReader jsonReader;

    // constructs a frame of menus and buttons
    ManagerFrame() throws FileNotFoundException {
        set();
        init();
        buttons();
        panel();
        addListeners();
        keys();
        menuConstruct();

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    // frame settings
    public void set() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(new FlowLayout());
    }

    // initialize buttons, menus, etc.
    public void init() {
        leagueList = new ArrayList<League>();

        jsonReader = new JsonReader(JSON_STORE);

        addButton = new JButton("Add League");
        deleteButton = new JButton("Remove League");
        viewButton = new JButton("View Leagues");

        menuBar = new JMenuBar();
        // Build menus
        leagueMenu = new JMenu("Management");
        closeItem = new JMenuItem("Close");

        // Build panel
        choosePanel = new JPanel();
        leagueLabel = new JLabel("League:");
        leagueTextField = new JTextField(20);
        teamLabel = new JLabel("Team:");
        teamPasswordField = new JPasswordField(20);
        searchButton = new JButton("Search");

        container = getContentPane();

        setTitle("Soccer Manager");
        setLayout(new FlowLayout());
    }

    // set the position and size of buttons and add actionListeners
    public void buttons() {
        addButton.setBounds(500, 500, 500, 150);
        addButton.addActionListener(this);
        deleteButton.setBounds(500, 500, 500, 150);
        deleteButton.addActionListener(this);
        viewButton.setBounds(500, 500, 500, 150);
        viewButton.addActionListener(this);
    }

    // add a panel to choose teams within a league
    public void panel() {
        choosePanel.setBounds(500, 1000, 500, 500);
        leagueLabel.setBounds(600, 1150, 80, 25);
        choosePanel.add(leagueLabel);
        leagueTextField.setBounds(800, 1150, 165, 25);
        choosePanel.add(leagueTextField);
        teamLabel.setBounds(600, 1350, 80, 25);
        choosePanel.add(teamLabel);
        teamPasswordField.setBounds(800, 1350, 165, 25);
        choosePanel.add(teamPasswordField);
        searchButton.setBounds(500, 1600, 100, 25);
        choosePanel.add(searchButton);
    }

    // add menu items' actionListeners
    public void addListeners() {
        closeItem.addActionListener(this);
    }

    // add convenient keys for fast operations
    public void keys() {
        closeItem.setMnemonic(KeyEvent.VK_C);
    }

    // construct menus
    public void menuConstruct() {
        leagueMenu.add(closeItem);
        menuBar.add(leagueMenu);
        container.add(addButton);
        container.add(deleteButton);
        container.add(viewButton);
        container.add(choosePanel);
    }


    @Override
    // operations on the frame
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == closeItem) {
            System.exit(0);
        }

        if (e.getSource() == addButton) {
            try {
                new AddWindow();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == deleteButton) {
            try {
                if (leagueList != null){
                    new DeleteWindow();
                } else {
                    JOptionPane.showMessageDialog(this, "No league exists!");
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == viewButton) {
            try {
                new ViewWindow();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

}
