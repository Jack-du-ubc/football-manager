package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a player having his or her name and statistics(wages for now)
public class Player implements Writable {
    private String playerName; // player name
    private String teamName; // name of the team the player plays for
    private int wages; // player wages

    public Player(String playerName, String teamName, int wages) {
        if (playerName == null || teamName == null || wages < 0) {
            throw new IllegalArgumentException("Invalid argument passed to Player");
        }
        this.playerName = playerName;
        this.teamName = teamName;
        this.wages = wages;
    }
    

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public int getWages() {
        return wages;
    }

    public void setWages(int wages) {
        this.wages = wages;
    }

    // convert to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playername", playerName);
        json.put("teamname", teamName);
        json.put("wages", wages);
        return json;
    }
}