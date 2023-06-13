package persistence;

import model.League;
import model.Team;
import model.Player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.List;

// represents a writer that writes JSON representation to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // writes JSON representation of league to file
    public void write(League l) {
        JSONObject json = l.toJson();
        saveToFile(json.toString(TAB));
    }

    // writes JSON representation of team to file
    public void write(Team t) {
        JSONObject json = t.toJson();
        saveToFile(json.toString(TAB));
    }

    // writes JSON representation of player to file
    public void write(Player p) {
        JSONObject json = p.toJson();
        saveToFile(json.toString(TAB));
    }

    // closes writer
    public void close() {
        writer.close();
    }

    // writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // writes JSONArray representation of leagues to file
    public void write(List<League> ll) {
        JSONArray jsons = new JSONArray();
        for (League l : ll){
            JSONObject json = l.toJson();
            jsons.put(json);
        }
        saveToFile(jsons.toString(TAB));
    }

}