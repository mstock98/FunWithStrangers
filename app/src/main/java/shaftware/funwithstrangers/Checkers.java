package shaftware.funwithstrangers;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import shaftware.funwithstrangers.CheckersLogic.Move;

public class Checkers extends AppCompatActivity {

    //Fuckin hell dudes look at all of these
    ImageButton c00, c01, c02, c03, c04, c05, c06, c07,
            c10, c11, c12, c13, c14, c15, c16, c17,
            c20, c21, c22, c23, c24, c25, c26, c27,
            c30, c31, c32, c33, c34, c35, c36, c37,
            c40, c41, c42, c43, c44, c45, c46, c47,
            c50, c51, c52, c53, c54, c55, c56, c57,
            c60, c61, c62, c63, c64, c65, c66, c67,
            c70, c71, c72, c73, c74, c75, c76, c77;
    ImageButton[] buttons = {c00, c01, c02, c03, c04, c05, c06, c07,
            c10, c11, c12, c13, c14, c15, c16, c17,
            c20, c21, c22, c23, c24, c25, c26, c27,
            c30, c31, c32, c33, c34, c35, c36, c37,
            c40, c41, c42, c43, c44, c45, c46, c47,
            c50, c51, c52, c53, c54, c55, c56, c57,
            c60, c61, c62, c63, c64, c65, c66, c67,
            c70, c71, c72, c73, c74, c75, c76, c77};
    String[] buttonsID = {"c00", "c01", "c02", "c03", "c04", "c05", "c06", "c07",
            "c10", "c11", "c12", "c13", "c14", "c15", "c16", "c17",
            "c20", "c21", "c22", "c23", "c24", "c25", "c26", "c27",
            "c30", "c31", "c32", "c33", "c34", "c35", "c36", "c37",
            "c40", "c41", "c42", "c43", "c44", "c45", "c46", "c47",
            "c50", "c51", "c52", "c53", "c54", "c55", "c56", "c57",
            "c60", "c61", "c62", "c63", "c64", "c65", "c66", "c67",
            "c70", "c71", "c72", "c73", "c74", "c75", "c76", "c77"};
    CheckersLogic.square[] board;

    CheckersLogic game;
    Move selectedMove, destinationMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);
        //TODO
        //Configure
        game = new CheckersLogic(CheckersLogic.square.WHITE, true);


        createBoard();
        initializeButtons();
        initializeBoard();

        syncBoards();
        updateGameView();


    }

    private void createBoard() {
        board = new CheckersLogic.square[64];
        for (int i = 0; i < board.length; i++) {
            board[i] = CheckersLogic.square.OPEN;

        }
    }

    private ImageButton getButton(int i){
        int resID = getResources().getIdentifier(buttonsID[i], "id", getPackageName());
        return findViewById(resID);
    }

    private void initializeButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = getButton(i);
            buttons[i].setBackgroundColor(Color.TRANSPARENT);
            buttons[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onImageButtonPressed(v);
                }
            });
        }
    }

    //TODO
    //Syncs UIs single dimension board with logic's multidimensional board
    private void syncBoards() {
        for (int i = 0; i < board.length; i++) {
            board[i] = game.getBoard()[i / 8][i % 8];
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 == j % 2) {
                    buttons[i * 8 + j].setImageResource(R.drawable.boardlight);
                }
                else {
                    buttons[i * 8 + j].setImageResource(R.drawable.boarddark);
                }
            }
        }
    }

    //TODO
    //Set pieces to their correct color
    private void updateGameView() {
        for (int i = 0; i < board.length; i++) {
            CheckersLogic.square square = board[i];
            buttons[i].setColorFilter(Color.TRANSPARENT);
            if (square == CheckersLogic.square.BLACK)
                buttons[i].setImageResource(R.drawable.black);
            else if (square == CheckersLogic.square.WHITE)
                buttons[i].setImageResource(R.drawable.white);
            else if (square == CheckersLogic.square.BKING)
                buttons[i].setImageResource(R.drawable.blackk);
            else if (square == CheckersLogic.square.WKING)
                buttons[i].setImageResource(R.drawable.whitek);
            else if (square == CheckersLogic.square.OPEN) {
                //TODO
                //Need to pick the correct light or dark
                if ((i / 8) % 2 == (i % 8) % 2)
                    buttons[i].setImageResource(R.drawable.boardlight);
                else
                    buttons[i].setImageResource(R.drawable.boarddark);
            }
        }
    }

    private void onImageButtonPressed(View v) {
        //do nothing if it isn't your turn
        if (!game.getTurn())
            return;

        CheckersLogic.square piece = null;
        int id = -1;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getId() == v.getId()) {
                piece = board[i];
                id = i;
            }
        }

        //if buttons ins't found return
        if (piece == null || id == -1)
            return;

        if (selectedMove == null) {
            //if the selected piece is an empty square return
            if (piece == CheckersLogic.square.OPEN)
                return;
            Move move = new Move(id / 8, id % 8);
            selectedMove = move;
            highlightSquare(move);
            return;
        }
        else if (destinationMove == null) {
            Move move = new Move(id / 8, id % 8);
            destinationMove = move;
            highlightSquare(move);
        }


        //TODO
        //if valid move...
        if (selectedMove != null && destinationMove != null && game.validMove(selectedMove, destinationMove, true)) {

            //if turn can end... e.i. no more available moves that have to be made
            if (game.checkEndTurn(destinationMove)) {
                //game.setTurn(false);
                //TODO
                game.swapPiece();
            }

            //checks and handles winning situations
            checkWinner();

            syncBoards();
            updateGameView();
        }


        if (selectedMove != null && destinationMove != null){
            //reset variables after valid move
            selectedMove = null;
            destinationMove = null;
            updateGameView();
        }

    }

    private void highlightSquare(Move move){
        ImageButton b = getButton(move.getRow() * 8 + move.getCol());
        b.setColorFilter(Color.argb(100, 255, 255, 255));
    }

    private void checkWinner() {
        //TODO
        //Do something if someone has won
        CheckersLogic.outcome outcome = game.checkWinner();
        if (outcome == CheckersLogic.outcome.IN_PROGRESS)
            return;

        if (outcome == outcome.BLACK) {
            Toast.makeText(getApplicationContext(), "Black Won!", Toast.LENGTH_LONG).show();
        } else if (outcome == outcome.WHITE) {
            Toast.makeText(getApplicationContext(), "White Won!", Toast.LENGTH_LONG).show();
        } else if (outcome == outcome.TIE){
            Toast.makeText(getApplicationContext(), "Tie!", Toast.LENGTH_LONG).show();
        }

    }
}
