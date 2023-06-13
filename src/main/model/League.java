package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

// represents a named league having a list of teams
public class League implements Writable{
    private String leagueName;  // league name
    private List<Team> teamList;  // the list of teams

    public League(String leagueName) {
        if (leagueName == null) {
            throw new IllegalArgumentException("Invalid argument passed to League");
        }
        this.leagueName = leagueName;
        this.teamList = new ArrayList<Team>();
    }
    

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
      }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    // get team names
    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<String>();
        for (Team t : teamList) {
            teamNames.add(t.getTeamName());
        }
        return teamNames;
    }

    // add new team to the list
    public void addTeam(Team newTeam) {
        teamList.add(newTeam);
    }

    // team member removed
    public void removeTeam(String removeTeamName) {
        Iterator<Team> iterator = teamList.iterator();
    
        while (iterator.hasNext()) {
            Team t = iterator.next();
            if (t.getTeamName().equals(removeTeamName)) {
                iterator.remove();
            }
        }
    }
    

    // convert to json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("leaguename", leagueName);
        json.put("teams", teamsToJson());
        return json;
    }

    // returns teams as a JSON array
    private JSONArray teamsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Team t : teamList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
