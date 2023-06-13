package persistence;

import model.League;
import model.Player;
import model.Team;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

// represents a reader that reads team from JSON data stored in file
public class JsonReader {
    private String source;

    // constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // reads league from file and returns it;
    // throws IOException if an error occurs reading data from file
    public League readLeague() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    // reads team from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Team readTeam() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTeam(jsonObject);
    }

    // reads player from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player readPlayer() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }        

        return contentBuilder.toString();
    }

    // parses league from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        String leagueName = jsonObject.getString("leaguename");
        League l = new League(leagueName);
        addTeams(l, jsonObject);
        return l;
    }

    // parses team from JSON object and returns it
    private Team parseTeam(JSONObject jsonObject) {
        String teamName = jsonObject.getString("teamname");
        String leagueName = jsonObject.getString("leaguename");
        Team t = new Team(teamName, leagueName);
        addPlayers(t, jsonObject);
        return t;
    }

    // parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        String playerName = jsonObject.getString("playername");
        String teamName = jsonObject.getString("teamname");
        Integer wages = jsonObject.getInt("wages");
        Player p = new Player(playerName,teamName,wages);
        return p;
    }

    // parses teams from JSON object and adds them to league
    private void addTeams(League l, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeam = (JSONObject) json;
            addTeam(l, nextTeam);
        }
    }

    // parses a team from JSON object and adds it to league
    private void addTeam(League l, JSONObject jsonObject) {
        String teamName = jsonObject.getString("teamname");
        String leagueName = jsonObject.getString("leaguename");
        Team team = new Team(teamName, leagueName);
        addPlayers(team, jsonObject);
        l.addTeam(team);
    }

    // parses players from JSON object and adds them to team
    private void addPlayers(Team t, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(t, nextPlayer);
        }
    }

    // parses a player from JSON object and adds it to team
    private void addPlayer(Team t, JSONObject jsonObject) {
        String playerName = jsonObject.getString("playername");
        String teamName = jsonObject.getString("teamname");
        int wages = jsonObject.getInt("wages");
        Player player = new Player(playerName, teamName, wages);
        t.buyPlayer(player);
    }

    // read leagues from JSON file and returns it
    // throws IOException if an error occurs reading data from file
    public List<League> readLeagues() throws IOException {
        List<League> leagueList = new ArrayList<>();
        String jsonData = readFile(source);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseLeagues(leagueList, jsonArray);
    }

    // parses leagues from JSON object and adds them to list
    private List<League> parseLeagues(List<League> leagueList, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextLeague = (JSONObject) json;
            League parsedLeague = parseLeague(nextLeague);
            leagueList.add(parsedLeague);
        }

        return leagueList;
    }

}