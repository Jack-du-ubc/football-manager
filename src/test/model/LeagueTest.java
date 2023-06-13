package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class LeagueTest{
    private League leagueTest;

    @BeforeEach
    void runBefore() {
        leagueTest = new League("league");
    }

    @Test
    void constructorTest() {
        assertEquals("league", leagueTest.getLeagueName());
        assertEquals(new ArrayList<Team>(),leagueTest.getTeamList());
    }

    @Test
    public void addTeamTest() {
        List<Team> lt = leagueTest.getTeamList();
        Team t = new Team("team","league");
        lt.add(t);
        leagueTest.addTeam(t);
        assertEquals(lt,leagueTest.getTeamList());
    }

    @Test
    void addMultipleTeamTest() {
        List<Team> lt = new ArrayList<Team>();
        Team x = new Team("x","league");
        Team y = new Team("y","league");
        lt.add(x);
        lt.add(y);
        leagueTest.addTeam(x);
        leagueTest.addTeam(y);
        assertEquals(lt, leagueTest.getTeamList());
    }

    @Test
    public void removeTeamTest() {
        List<Team> lt = leagueTest.getTeamList();
        Team t = new Team("team","league");
        lt.add(t);
        leagueTest.addTeam(t);
        lt.remove(t);
        leagueTest.removeTeam(t.getTeamName());
        assertEquals(lt,leagueTest.getTeamList());
    }

    @Test
    void removeMultipleTeamTest() {
        List<Team> lt = leagueTest.getTeamList();
        Team x = new Team("x","league");
        Team y = new Team("y","league");
        lt.add(x);
        lt.add(y);
        leagueTest.addTeam(x);
        leagueTest.addTeam(y);
        lt.remove(x);
        lt.remove(y);
        leagueTest.removeTeam(x.getTeamName());
        leagueTest.removeTeam(y.getTeamName());
        assertEquals(lt,leagueTest.getTeamList());
    }

    @Test
    public void getTeamNamesTest() {
        List<String> ls = new ArrayList<String>();
        Team x = new Team("x","league");
        Team y = new Team("y","league");
        ls.add("x");
        ls.add("y");
        leagueTest.addTeam(x);
        leagueTest.addTeam(y);
        assertEquals(ls, leagueTest.getTeamNames());
    }

}