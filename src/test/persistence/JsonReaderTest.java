package persistence;

import model.Player;
import model.League;
import model.Team;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    League l;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/test/noSuchFile.json");
        try {
            l = reader.readLeague();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTeam() {
        try {
            JsonReader reader = new JsonReader("./data/test/testReaderEmptyTeam.json");
            Team t = reader.readTeam();
            assertEquals(0, t.getPlayerList().size());
        } catch (
                IOException e) {
            fail("Couldn't read from file");
        }

    }

    @Test
    void testReaderGeneralTeam() {
        try {
            JsonReader reader = new JsonReader("./data/testReaderGeneralTeam.json");
            Team t = reader.readTeam();
            assertEquals("My team", t.getTeamName());
            assertEquals( "My league", t.getLeagueName());
            List<Player> players = t.getPlayerList();
            assertEquals(2, players.size());
            checkPlayer("Ann", 1, players.get(0));
            checkPlayer("Ben", 2, players.get(1));
        } catch (
                IOException e) {
            fail("Couldn't read from file");
        }
    }
}
