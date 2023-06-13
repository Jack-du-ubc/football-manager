package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {
    private Team teamTest;

    @BeforeEach
    void runBefore() {
        teamTest = new Team("team","league");
    }

    @Test
    void constructorTest() {
        assertEquals("league", teamTest.getLeagueName());
        assertEquals("team", teamTest.getTeamName());
        assertEquals(new ArrayList<Player>(), teamTest.getPlayerList());
    }

    @Test
    public void buyPlayerTest() {
        List<Player> lp = new ArrayList<Player>();
        Player p = new Player("Alan", "team", 1);
        lp.add(p);
        teamTest.buyPlayer(p);
        assertEquals(lp, teamTest.getPlayerList());
    }

    @Test
    public void buyMultiplePlayerTest() {
        List<Player> lp = new ArrayList<Player>();
        Player p1 = new Player("Alan", "team", 1);
        Player p2 = new Player("Black", "team", 1);
        lp.add(p1);
        lp.add(p2);
        teamTest.buyPlayer(p1);
        teamTest.buyPlayer(p2);
        assertEquals(lp, teamTest.getPlayerList());
    }

    @Test
    public void sellPlayerTest() {
        List<Player> lp = new ArrayList<Player>();
        Player p1 = new Player("Jack","team",2);
        Player p2 = new Player("Ben","team",3);
        teamTest.buyPlayer(p1);
        teamTest.buyPlayer(p2);
        teamTest.sellPlayer(p1.getPlayerName());
        lp.add(p1);
        lp.add(p2);
        lp.remove(p1);
        assertTrue(teamTest.getPlayerList().equals(lp));
        Player p3 = new Player("Cindy","team",4);
        teamTest.sellPlayer(p3.getPlayerName());
        assertTrue(teamTest.getPlayerList().equals(lp));
    }

    @Test
    void sellMultiplePlayersTest() {
        List<Player> lp = new ArrayList<Player>();
        Player l = new Player("L.Messi","team", 100);
        Player c = new Player("C.Ronaldo","team", 100);
        lp.add(l);
        lp.add(c);
        teamTest.buyPlayer(l);
        teamTest.buyPlayer(c);
        teamTest.sellPlayer(l.getPlayerName());
        teamTest.sellPlayer(c.getPlayerName());
        lp.remove(l);
        lp.remove(c);
        assertEquals(lp, teamTest.getPlayerList());
    }

    @Test
    public void playerNamesTest() {
        List<String> ls = new ArrayList<String>();
        teamTest.buyPlayer(new Player("a", "team", 5));
        teamTest.buyPlayer(new Player("b","team",7));
        ls.add("a");
        ls.add("b");
        assertEquals(ls,teamTest.getPlayerNames());
    }

}
