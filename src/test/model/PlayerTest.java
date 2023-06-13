package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    private Player playerTest;

    @BeforeEach
    void runBefore() {
        playerTest = new Player("Jack", "team",1);
    }

    @Test
    void constructorTest() {
        assertEquals("Jack", playerTest.getPlayerName());
        assertEquals("team", playerTest.getTeamName());
        assertEquals(1, playerTest.getWages());
    }

    @Test
    public void updateWagesTest() {
        playerTest.setWages(5);
        assertEquals(5, playerTest.getWages());
    }

}