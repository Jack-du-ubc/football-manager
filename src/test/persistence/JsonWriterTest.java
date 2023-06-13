package persistence;

import model.Player;
import model.Team;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    Team t;

    @Test
    void testWriterInvalidFile() {
        try {
            t = new Team("My team", "My league");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTeam() {
        try {
            Team t = new Team("My team", "My league");
            JsonWriter writer = new JsonWriter("./data/test/testWriterEmptyTeam.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/testWriterEmptyTeam.json");
            t = reader.readTeam();
            assertEquals("My team", t.getTeamName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTeam() {
        try {
            Team t = new Team("My team", "My league");
            t.buyPlayer(new Player("Bob","My team",5));
            JsonWriter writer = new JsonWriter("./data/test/testWriterGeneralTeam.json");
            writer.open();
            writer.write(t);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/testWriterGeneralTeam.json");
            t = reader.readTeam();
            assertEquals("My team", t.getTeamName());
            List<Player> players = t.getPlayerList();
            assertEquals(1, players.size());
            checkPlayer("Bob", 5, players.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
