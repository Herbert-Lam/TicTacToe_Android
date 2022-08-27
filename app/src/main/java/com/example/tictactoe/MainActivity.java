package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn11, btn12, btn13, btn21, btn22, btn23, btn31, btn32, btn33;

    ArrayList<Button> btnAL = new ArrayList<>();

    TextView textview;
    char[][] statusArray = {{'-','-','-'},{'-','-','-'},{'-','-','-'}};
    char turn = 'X';
    int numberOfTurn = 0;
    boolean win = false;
    String outputStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String title = "Tic-Tac-Toe";
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + title + "</font>"));
        getSupportActionBar().setIcon(R.drawable.tic_tac_toe_icon);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn21 = findViewById(R.id.btn21);
        btn22 = findViewById(R.id.btn22);
        btn23 = findViewById(R.id.btn23);
        btn31 = findViewById(R.id.btn31);
        btn32 = findViewById(R.id.btn32);
        btn33 = findViewById(R.id.btn33);

        btnAL.add(btn11);
        btnAL.add(btn12);
        btnAL.add(btn13);
        btnAL.add(btn21);
        btnAL.add(btn22);
        btnAL.add(btn23);
        btnAL.add(btn31);
        btnAL.add(btn32);
        btnAL.add(btn33);

        for(int i = 0; i < btnAL.size(); i++){
            btnAL.get(i).setOnClickListener(this);
        }

        textview = findViewById(R.id.textView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                String keyStr = "key" + i + j;
                outState.putChar(keyStr, statusArray[i][j]);
            }
        }

        outState.putString("textViewStr", textview.getText().toString());
        outState.putChar("turn", turn);
        outState.putInt("numberOfTurn", numberOfTurn);
        outState.putBoolean("win", win);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    String keyStr = "key" + i + j;
                    statusArray[i][j] = savedInstanceState.getChar(keyStr);
                    if(statusArray[i][j] != '-'){
                        if(i == 0){
                            btnAL.get(j).setText(String.valueOf(statusArray[0][j]));
                            btnAL.get(j).setTextSize(24);
                        } else if (i == 1){
                            btnAL.get(3 + j).setText(String.valueOf(statusArray[1][j]));
                            btnAL.get(3 + j).setTextSize(24);
                        } else {
                            btnAL.get(6 + j).setText(String.valueOf(statusArray[2][j]));
                            btnAL.get(6 + j).setTextSize(24);
                        }
                    }
                }
            }

            textview.setText(savedInstanceState.getString("textViewStr"));
            turn = savedInstanceState.getChar("turn");
            numberOfTurn = savedInstanceState.getInt("numberOfTurn");
            win = savedInstanceState.getBoolean("win");

            if(win){
                endGame(true);
            } else if (numberOfTurn == 9){
                endGame(false);
            }
        }
    }

    @Override
    public void onClick(View view){
        Button btnClicked = (Button) view;
        String idStr = btnClicked.getResources().getResourceName(view.getId());
        int row = Character.getNumericValue(idStr.charAt(idStr.length() - 2)) - 1;
        int col = Character.getNumericValue(idStr.charAt(idStr.length() - 1)) - 1;
        numberOfTurn++;
        if(turn == 'X'){
            btnClicked.setText("X");
            btnClicked.setTextSize(24);
            statusArray[row][col] = 'X';
            win = checkWin();
            if(!win && numberOfTurn < 9){
                turn = 'O';
                outputStr = "Player " + turn + "'s turn";
                textview.setText(outputStr);
            } else {
                endGame(win);
            }
        } else {
            btnClicked.setText("O");
            btnClicked.setTextSize(24);
            statusArray[row][col] = 'O';
            win = checkWin();
            if(!win){
                turn = 'X';
                outputStr = "Player " + turn + "'s turn";
                textview.setText(outputStr);
            } else {
                endGame(true);
            }
        }
        btnClicked.setClickable(false);
    }

    public boolean checkWin(){
        boolean winFlag = false;
        for(int i = 0; i < 3; i++){
            if (statusArray[i][0] != '-' && statusArray[i][0] == statusArray[i][1] &&
                    statusArray[i][0] == statusArray[i][2]) {
                winFlag = true;
                break;
            }
        }
        for(int j = 0; j < 3; j++){
            if(statusArray[0][j] != '-' && statusArray[0][j] == statusArray[1][j] &&
                    statusArray[0][j] == statusArray[2][j]){
                winFlag = true;
                break;
            }
        }
        if(statusArray[1][1] != '-' && ((statusArray[1][1] == statusArray[0][0] &&
                statusArray[1][1] == statusArray[2][2]) || (statusArray[1][1] == statusArray[0][2] &&
                statusArray[1][1] == statusArray[2][0]))) {
            winFlag = true;
        }

        return winFlag;
    }

    public void newGame(View view) {
        win = false;
        turn = 'X';
        outputStr = "Player " + turn + "'s turn";
        textview.setText(outputStr);
        numberOfTurn = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                statusArray[i][j] = '-';
            }
        }
        for(int i = 0; i < btnAL.size(); i++){
            btnAL.get(i).setText("");
            btnAL.get(i).setClickable(true);
        }
    }

    public void endGame(boolean win){
        for(int i = 0; i < btnAL.size(); i++){
            btnAL.get(i).setClickable(false);
        }
        if(win){
            outputStr = turn + " wins!";
        } else {
            outputStr = "Draw!";
        }
        textview.setText(outputStr);
    }
}