package persistence;

import model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String playerName, int wages, Player player) {
        assertEquals(playerName, player.getPlayerName());
        assertEquals(wages, player.getWages());
    }
}