package fhku.testingtictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameUnitTest {

    protected Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.setGame(new String[] {" ", "X", " ", " ", "O", "X", " ", " ", "O"});
    }

    @Test
    public void turnNumberShouldEqualTo5After4Turns() {
        int turnNumber = game.getTurnNumber();
        Assert.assertEquals(5, turnNumber);
    }

    @Test
    public void initShouldResetGame() {
        game.init();
        String[] board = game.getGame();
        Assert.assertArrayEquals(new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " "}, board);
    }

    @Test
    public void position0ShouldSetXCorrectly() {
        boolean result = game.turn(0);
        String[] board = game.getGame();
        Assert.assertEquals(true, result);
        Assert.assertEquals("X", board[0]);
    }

}