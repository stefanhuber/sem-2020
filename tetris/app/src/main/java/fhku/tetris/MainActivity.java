package fhku.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected int gameSpeed = 800;
    protected TetrisGameView gameView;
    protected Tetris game = new Tetris();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        gameView = findViewById(R.id.tetris);
        findViewById(R.id.btn_left).setOnClickListener(this);
        findViewById(R.id.btn_right).setOnClickListener(this);

        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!game.isInitialized()) {
                    game.init(gameView.getLines());
                }
                game.next();
                gameView.draw(game.getBoard());

                h.postDelayed(this, gameSpeed);
            }
        }, gameSpeed);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_left) {
            game.left();
        } else if (v.getId() == R.id.btn_right) {
            game.right();
        }

        gameView.draw(game.getBoard());
    }
}