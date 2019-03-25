package com.rvcodes.tictactoe;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons = new Button[3][3];
    Button btn_reset;
    TextView player1, player2;
    Boolean player1turn = true;
    int player1score, player2score;
    int turnCount;
    Drawable x_background, o_background, empty_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x_background = getResources().getDrawable(R.drawable.button_x_bg);
        o_background = getResources().getDrawable(R.drawable.button_o_bg);
        empty_background = getResources().getDrawable(R.drawable.button_empty);

        player1 = findViewById(R.id.player1Score);
        player2 = findViewById(R.id.player2Score);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btnId = "btn_" + i + j;
//                System.out.println(btnId);
                int resId = getResources().getIdentifier(btnId, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                player1.setText("0");
                player2.setText("0");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
           return;
        }

        if (player1turn) {
            ((Button) v).setText("X");
            ((Button) v).setBackgroundDrawable(x_background);
        }
        else {
            ((Button) v).setText("O");
            ((Button) v).setBackgroundDrawable(o_background);
        }
        turnCount++;
        if (checkForWin()) {
            if (player1turn) {
//                Toast.makeText(this, "player1", Toast.LENGTH_SHORT).show();
                player1wins();
            } else {
//                Toast.makeText(this, "player2", Toast.LENGTH_SHORT).show();
                player2wins();
            }
        } else if (turnCount == 9) {
            gameDraw();
        } else {
            player1turn = !player1turn;
        }
    }

    public Boolean checkForWin() {
        String[][] values = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                values[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if(values[i][0].equals(values[i][1]) && values[i][0].equals(values[i][2]) && !values[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if(values[0][i].equals(values[1][i]) && values[0][i].equals(values[2][i]) && !values[0][i].equals("")) {
                return true;
            }
        }
        if(values[0][0].equals(values[1][1]) && values[0][0].equals(values[2][2]) && !values[0][0].equals("")) {
            return true;
        }
        if(values[0][2].equals(values[1][1]) && values[0][2].equals(values[2][0]) && !values[0][2].equals("")) {
            return true;
        }
        return false;
    }

    public void player1wins() {
        Toast.makeText(this, "PLAYER 1 WINS", Toast.LENGTH_SHORT).show();
        player1score++;
        updatePoints();
        resetBoard();
    }

    public void player2wins() {
        Toast.makeText(this, "PLAYER 2 WINS", Toast.LENGTH_SHORT).show();
        player2score++;
        updatePoints();
        resetBoard();
    }

    private void gameDraw() {
        Toast.makeText(this, "MATCH DRAW", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePoints() {
        player1.setText(player1score+"");
        player2.setText(player2score+"");
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackgroundDrawable(empty_background);
            }
        }
        turnCount = 0;
        player1turn = true;
    }
}
