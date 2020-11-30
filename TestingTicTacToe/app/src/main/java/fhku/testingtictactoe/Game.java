package fhku.testingtictactoe;

public class Game {

    public static final String[] players = new String[] {"O", "X"};
    protected String[] game = new String[9];

    public void init() {
        game = new String[] {" ", " ", " ", " ", " ", " ", " ", " ", " "};
    }

    public boolean turn(int position) {
        if (position < 0 || position > 8) {
            return false;
        } else if (!game[position].equals(" ")) {
            return false;
        }

        game[position] = getCurrentPlayer();
        return true;
    }

    public String checkWinner() {
        for (int i = 0; i < 3; i++)
            if (!game[i].equals(" ") &&
                    game[i*3].equals(game[i*3+1]) &&
                    game[i*3].equals(game[i*3+2])) {
                return game[i];
            }

        for (int i = 0; i < 3; i++)
            if (!game[i].equals(" ") &&
                    game[i].equals(game[i+3]) &&
                    game[i].equals(game[i+6])) {
                return game[i];
            }

        if (!game[0].equals(" ") &&
                game[0].equals(game[4]) &&
                game[0].equals(game[8])) {
            return game[4];
        }

        if (!game[2].equals(" ") &&
                game[2].equals(game[4]) &&
                game[2].equals(game[6])) {
            return game[4];
        }

        return " ";
    }

    public String getCurrentPlayer() {
        return players[getTurnNumber() % 2];
    }

    public int getTurnNumber() {
        int emptyFields = 0;

        for (String field : game) {
            if (field.equals(" ")) {
                emptyFields++;
            }
        }

        return 10 - emptyFields;
    }

    public String[] getGame() {
        return game.clone();
    }

    public void setGame(String[] game) {
        this.game = game;
    }

    public String getPlayer(int position) {
        return this.game[position];
    }

    public boolean isGameOver() {
        return !checkWinner().equals(" ") || getTurnNumber() == 10;
    }

}
