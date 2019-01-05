package com.example.bernard.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0; // 0 = yellow, 1 = red
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // 2 - empty
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    boolean gameActive = true;

    int countOfMoves = 9;

    public void dropIn(View view) {

        countOfMoves--;

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // set game state to active player
        if (gameState[tappedCounter] != 2 ||!gameActive) return;

        gameState[tappedCounter] = activePlayer;

        counter.setTranslationY(-1500); // put image out of screen

        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.yellow); // set image
            activePlayer = 1;
        } else {
            counter.setImageResource(R.drawable.red); // set image
            activePlayer = 0;
        }

        counter.animate().translationYBy(1500).rotation(360).setDuration(300); // put image back on screen

        // check if the position is winning
        for (int[] winningPosition: winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                // someone has won!

                String winner = "";

                winner = (activePlayer == 1) ? "Yellow" : "Red"; // winner is opossite of active player

                gameActive = false;

                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                TextView winnerText = (TextView) findViewById(R.id.winnerText);

                winnerText.setText(winner + " has won!");

                winnerText.setVisibility(View.VISIBLE);

                playAgainButton.setVisibility(View.VISIBLE);
            }
        }

        if (countOfMoves == 0) { // no moves left, nobody wins
            gameActive = false;

            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

            TextView winnerText = (TextView) findViewById(R.id.winnerText);

            winnerText.setText("Tie!");

            winnerText.setVisibility(View.VISIBLE);

            playAgainButton.setVisibility(View.VISIBLE);
        }
    }

    public void playAgain(View view) {
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerText = (TextView) findViewById(R.id.winnerText);

        winnerText.setVisibility(View.INVISIBLE);

        playAgainButton.setVisibility(View.INVISIBLE);

        android.support.v7.widget.GridLayout grid = (android.support.v7.widget.GridLayout) findViewById(R.id.grid);

        for (int i = 0; i < grid.getChildCount(); i++) {
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        activePlayer = 0;

        gameActive = true;

        countOfMoves = 9;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
