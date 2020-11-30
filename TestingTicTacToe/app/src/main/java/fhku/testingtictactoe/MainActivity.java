package fhku.testingtictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected int[] buttonIds = new int[9];
    protected Game game = new Game();
    protected TextView gameState;
    protected static final String TAG = "TESTING_TTT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = findViewById(R.id.gameState);
        initButtonIds();

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(this);
        }

        resetGame();
        gameState.setText("Zug Nummer: " + game.getTurnNumber());
    }

    public void initButtonIds() {
        buttonIds[0] = R.id.button_1;
        buttonIds[1] = R.id.button_2;
        buttonIds[2] = R.id.button_3;
        buttonIds[3] = R.id.button_4;
        buttonIds[4] = R.id.button_5;
        buttonIds[5] = R.id.button_6;
        buttonIds[6] = R.id.button_7;
        buttonIds[7] = R.id.button_8;
        buttonIds[8] = R.id.button_9;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        int position = -1;

        for (int i = 0; i < buttonIds.length; i++) {
            if (b.getId() == buttonIds[i]) {
                position = i;
                break;
            }
        }

        Log.i(TAG, "Position: " + position);

        if (game.turn(position)) {
            b.setText(game.getPlayer(position));
            gameState.setText("Zug Nummer: " + game.getTurnNumber());
        }

        /*
        if (service.isGameOver()) {
            String winner = service.checkWinner();
            String drawText = "Game Over: Draw";
            String winnerText = "Game Over: Winner " + winner;
            gameState.setText(winner.equals(" ") ? drawText : winnerText);
            resetGame();
        }
        */
    }

    public void resetGame() {
        game.init();
    }
}