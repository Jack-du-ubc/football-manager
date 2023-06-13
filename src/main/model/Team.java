package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

// represents a team having a team name, its belonged league name and a list of its players
public class Team  implements Writable {
    private String teamName;  // team name
    private String leagueName; // league name team belongs to
    private List<Player> playerList;  // players in the team

    public Team(String teamName, String leagueName) {
        if (teamName == null || leagueName == null) {
            throw new IllegalArgumentException("Invalid argument passed to Team");
        }
        this.teamName = teamName;
        this.leagueName = leagueName;
        this.playerList = new ArrayList<Player>();
    }
    

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName){
        this.teamName = teamName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName){
        this.leagueName = leagueName;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList){
        this.playerList = playerList;
    }

    // buy a player
    // add the list with the new member
    public void buyPlayer(Player newMember) {
        playerList.add(newMember);
    }

    // sell a player
    // delete the member gone
    public void sellPlayer(String soldMember) {
        Iterator<Player> iterator = playerList.iterator();
    
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getPlayerName().equals(soldMember)) {
                iterator.remove();
            }
        }
    }

    // returns player names in the list
    public List<String> getPlayerNames() {
        List<String> playerNames = new ArrayList<>();
        for (Player p : playerList) {
            playerNames.add(p.getPlayerName());
        }
        return playerNames;
    }

    // convert to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamname", teamName);
        json.put("leaguename", leagueName);
        json.put("players", playersToJson());
        return json;
    }

    // returns players as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : playerList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}