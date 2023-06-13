package ui.CLI;

import exceptions.IllogicalException;
import model.League;
import model.Player;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// football management application
public class ManagerApp {
    private Team team;
    private Player player;
    private League league;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // run the manager application
    public ManagerApp() throws FileNotFoundException, IllogicalException {
        runManager();
    }


    // process user input
    private void runManager() throws IllogicalException {
        boolean running = true;
        String command = null;
        init();
        while (running) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                running = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nWish you become the best football manager");
    }

    // initializes teams, players, leagues
    private void init() {
        league = new League("l");
        team = new Team("t", "l");
        player = new Player("p", "t", 0);
        input = new Scanner(System.in);
    }

    // displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Team");
        System.out.println("\tr -> Remove Team");
        System.out.println("\tb -> Buy Player");
        System.out.println("\ts -> Sell Player");
        System.out.println("\tu -> Update Stat");
        System.out.println("\tv -> View");
        System.out.println("\tq -> Quit");
    }

    // processes user command
    private void processCommand(String command) throws IllogicalException {
        if (command.equals("a")) {
            doAddTeam();
        } else if (command.equals("r")) {
            doRemoveTeam();
        } else if (command.equals("b")) {
            doBuyPlayer();
        } else if (command.equals("s")) {
            doSellPlayer();
        } else if (command.equals("u")) {
            updateStat();
        } else if (command.equals("v")) {
            viewLinedUp();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // conducts add team operation
    private void doAddTeam() {
        System.out.print("Type existing league or create:\n");
        String leagueName = input.next();
        File directory0 = new File("./data/CLI/" + leagueName);
        if (!directory0.exists()) {
            league = makeLeague(leagueName, directory0);
        } else {
            league = loadLeague(leagueName);
        }

        System.out.print("Create team:\n");
        String teamName = input.next();
        File directory1 = new File("./data/CLI/" + league.getLeagueName() + "/" + teamName);
        try {
            directory1.mkdir();
            team = new Team(teamName, league.getLeagueName());
            saveTeam(team);
            league.addTeam(team);
            saveLeague(league);
        } catch (SecurityException e) {
            System.out.println("Unable to create directory for team: " + teamName);
        }
    }

    // conducts remove team operation
    private void doRemoveTeam() {
        System.out.print("Type existing league:\n");
        String leagueName = input.next();
        File directory0 = new File("./data/CLI/" + leagueName);
        if (!directory0.exists()) {
            System.out.println("League does not exist");
        } else {
            league = loadLeague(leagueName);
            System.out.print("Remove existing team:\n");
            String teamName = input.next();
            if (!league.getTeamNames().contains(teamName)) {
                System.out.println("Team does not exist");
            } else {
                league.removeTeam(teamName);
                File file = new File("./data/CLI/" + leagueName + "/" + teamName + ".json");
                if (file.exists()) {
                    if (!file.delete()) {
                        System.out.println("Unable to delete file: " + file.getPath());
                    } else {
                        file.delete();
                        File directory1 = new File("./data/CLI/" + league.getLeagueName() + "/" + teamName);
                        if (!directory1.exists()) {
                            System.out.println("Unable to delete directory: " + directory1.getPath());
                        } else {
                            if (directory1.listFiles() != null) {
                                String[] fileStrings = directory1.list();
                                for (String s : fileStrings) {
                                    File currentFile = new File(directory1.getPath(), s);
                                    currentFile.delete();
                                }
                            }
                            directory1.delete();
                        }
                    }
                }
            }

            saveLeague(league);
        }
    }

    

    // conducts buy player operation
    private void doBuyPlayer() {
        System.out.print("Type existing league:\n");
        String leagueName = input.next();
        File directory0 = new File("./data/CLI/" + leagueName);
        if (!directory0.exists()) {
            System.out.println("League does not exist");
        } else {
            league = loadLeague(leagueName);
            System.out.print("Type existing team:\n");
            String teamName = input.next();
            File directory1 = new File("./data/CLI/" + league.getLeagueName() + "/" + teamName);
            if (!directory1.exists()) {
                System.out.println("Team does not exist");
            } else {
                System.out.print("Buy player:\n");
                String playerName = input.next();
                System.out.print("Input wages:\n");
                int initialWages = input.nextInt();
                player = new Player(playerName, teamName, initialWages);
                File file = new File("./data/CLI/" + leagueName + "/" + teamName + ".json");
                if (file.exists()) {
                    try {
                        savePlayer(player, league);
                        team = loadTeam(teamName, league);
                        team.buyPlayer(player);
                        saveTeam(team);
                    } catch (SecurityException e) {
                        System.out.println("Unable to create file for player: " + playerName);
                    }
                } else {
                    System.out.println("Unable to find file for team: " + teamName);
                }
                updateLeague(team, league);
                saveLeague(league);
            }
        }

    }

    // conducts sell player operation
    private void doSellPlayer() {
        System.out.print("Type existing league:\n");
        String leagueName = input.next();
        File directory0 = new File("./data/CLI/" + leagueName);
        if (!directory0.exists()) {
            System.out.println("League does not exist");
        } else {
            league = loadLeague(leagueName);
            System.out.print("Type existing team:\n");
            String teamName = input.next();
            File directory1 = new File("./data/CLI/" + league.getLeagueName() + "/" + teamName);
            if (!directory1.exists()) {
                System.out.println("Team does not exist");
            } else {
                team = loadTeam(teamName, league);
                System.out.print("Sell player:\n");
                String playerName = input.next();

                if (!team.getPlayerNames().contains(playerName)) {
                    System.out.println("Player does not exist");
                } else {
                    team.sellPlayer(playerName);
                    File file = new File("./data/CLI/" + leagueName + "/" + teamName + "/" + playerName + ".json");
                    if (file.exists()) {
                        if (!file.delete()) {
                            System.out.println("Unable to sell player: " + file.getPath());
                        } else {
                            file.delete();
                            System.out.println("Sold player: " + file.getPath());
                        }
                    }
                    updateTeam(player, team);
                    saveTeam(team);
                }
            }
            updateLeague(team, league);
            saveLeague(league);
        }
    }

    // update stats (wages)
    private void updateStat() {
        System.out.print("Type existing league:\n");
        String leagueName = input.next();
        File directory0 = new File("./data/CLI/" + leagueName);
        if (!directory0.exists()) {
            System.out.println("League does not exist");
        } else {
            league = loadLeague(leagueName);
            System.out.print("Type existing team:\n");
            String teamName = input.next();
            File directory1 = new File("./data/CLI/" + league.getLeagueName() + "/" + teamName);
            if (!directory1.exists()) {
                System.out.println("Team does not exist");
            } else {
                team = loadTeam(teamName, league);
                System.out.print("Update player:\n");
                String playerName = input.next();

                if (!team.getPlayerNames().contains(playerName)) {
                    System.out.println("Player does not exist");
                } else {
                    player = loadPlayer(playerName, team);
                    System.out.print("Update wages:\n");
                    int newWages = input.nextInt();
                    player.setWages(newWages);
                    savePlayer(player, league);
                }
                updateTeam(player, team);
                saveTeam(team);
            }
            updateLeague(team, league);
            saveLeague(league);
        }  
    }

    // view info
    private void viewLinedUp() {
        System.out.print("View:\n");
        System.out.print("Type existing league:\n");
        String leagueName = input.next();
        File directory0 = new File("./data/CLI/" + leagueName);
        if (!directory0.exists()) {
            System.out.println("League does not exist");
        } else {
            league = loadLeague(leagueName);
            for (Team t : league.getTeamList()) {
                System.out.println(t.getTeamName());
            }
            System.out.print("Type existing team:\n");
            String teamName = input.next();
            File directory1 = new File("./data/CLI/" + league.getLeagueName() + "/" + teamName);
            if (!directory1.exists()) {
                System.out.println("Team does not exist");
            } else {
                team = loadTeam(teamName, league);
                for (Player p : team.getPlayerList()) {
                    System.out.println(p.getPlayerName());
                }
                System.out.print("Select player:\n");
                String playerName = input.next();

                if (!team.getPlayerNames().contains(playerName)) {
                    System.out.println("Player does not exist");
                } else {
                    player = loadPlayer(playerName, team);
                    System.out.println(player.getPlayerName());
                    System.out.println(player.getWages());
                }
            }
        }

    }

    // saves the player to file
    private void savePlayer(Player player, League league) {
        String playerFilePath = "./data/CLI/" + league.getLeagueName() + "/" + player.getTeamName() + "/"
                + player.getPlayerName() + ".json";
        try {
            jsonWriter = new JsonWriter(playerFilePath);
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Saved " + player.getPlayerName() + " to " + playerFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + playerFilePath);
        }
    }

    // loads player from file
    private Player loadPlayer(String playerName, Team playerTeam) {
        Player player = null;
        Team team = null;
        String leagueFilePath = "./data/CLI/" + playerTeam.getLeagueName() + ".json";
        String playerFilePath = "./data/CLI/" + playerTeam.getLeagueName() + "/" + playerName + ".json";
        try {
            jsonReader = new JsonReader(leagueFilePath);
            List<Team> teamList = jsonReader.readLeague().getTeamList();
            Iterator<Team> iterator = teamList.iterator();

            while (iterator.hasNext()) {
                Team t = iterator.next();
                if (t.getTeamName().equals(playerTeam.getTeamName())) {
                    team = t;
                }
            }

            List<Player> playerList = team.getPlayerList();
            Iterator<Player> iterator1 = playerList.iterator();

            while (iterator1.hasNext()) {
                Player p = iterator1.next();
                if (p.getPlayerName().equals(playerName)) {
                    player = p;
                }
            }
            System.out.println("Loaded " + playerName + " from " + playerFilePath);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + playerFilePath);
        }

        return player;
    }

    // saves the team to file
    private void saveTeam(Team team) {
        String teamFilePath = "./data/CLI/" + team.getLeagueName() + "/" + team.getTeamName() + ".json";
        try {
            jsonWriter = new JsonWriter(teamFilePath);
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            System.out.println("Saved " + team.getTeamName() + " to " + teamFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + teamFilePath);
        }
    }

    // loads team from file
    private Team loadTeam(String teamName, League league) {
        team = null;
        String teamFilePath = "./data/CLI/" + league.getLeagueName() + "/" + teamName + ".json";
        try {
            String leagueFilePath = "./data/CLI/" + league.getLeagueName() + ".json";
            jsonReader = new JsonReader(leagueFilePath);
            List<Team> teamList = jsonReader.readLeague().getTeamList();
            Iterator<Team> iterator = teamList.iterator();

            while (iterator.hasNext()) {
                Team t = iterator.next();
                if (t.getTeamName().equals(teamName)) {
                    team = t;
                }
            }

            System.out.println("Loaded " + teamName + " from " + teamFilePath);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + teamFilePath);
        }

        return team;
    }

    // saves the league to file
    private void saveLeague(League league) {
        String leagueFilePath = "./data/CLI/" + league.getLeagueName() + ".json";
        try {
            jsonWriter = new JsonWriter(leagueFilePath);
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            System.out.println("Saved " + league.getLeagueName() + " to " + leagueFilePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + leagueFilePath);
        }
    }

    // loads league from file
    private League loadLeague(String leagueName) {
        String leagueFilePath = "./data/CLI/" + leagueName + ".json";

        try {
            jsonReader = new JsonReader(leagueFilePath);
            league = jsonReader.readLeague();
            System.out.println("Loaded " + leagueName + " from " + leagueFilePath);
        } catch (IOException e) {
            System.out.println("Adding or unable to read from file: " + leagueFilePath);
        }

        return league;
    }

    // create directory for the league if it doesn't exist, and return the league
    private League makeLeague(String typeLeagueName, File directory) {
        if (!directory.exists()) {
            try {
                directory.mkdir();
                league = new League(typeLeagueName);
                saveLeague(league);
            } catch (SecurityException e) {
                System.out.println("Unable to create directory for league: " + typeLeagueName);
            }
        }

        return league;

    }

    // update league with new info on team
    private void updateLeague(Team team, League league) {
        List<Team> teamList = league.getTeamList();
        Iterator<Team> iterator = teamList.iterator();

        while (iterator.hasNext()) {
            Team t = iterator.next();
            if (t.getTeamName().equals(team.getTeamName())) {
                teamList.remove(t);
                teamList.add(team);
            }
        }

        league.setTeamList(teamList);
    }

    // update team with new info on player
    private void updateTeam(Player player, Team team) {
        List<Player> playerList = team.getPlayerList();
        Iterator<Player> iterator = playerList.iterator();

        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getPlayerName().equals(player.getPlayerName())) {
                playerList.remove(p);
                playerList.add(player);
            }
        }

        team.setPlayerList(playerList);
    }

}
