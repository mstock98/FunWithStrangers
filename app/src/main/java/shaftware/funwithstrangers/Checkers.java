package shaftware.funwithstrangers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


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
        game = new CheckersLogic(CheckersLogic.square.OPEN, false);

        createPiecesArray();
        initializeButtons();

    }

    private void createPiecesArray() {
        board = new CheckersLogic.square[64];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i + j] = CheckersLogic.square.OPEN;
            }
        }
    }

    private void initializeButtons() {
        for (int i = 0; i < buttons.length; i++) {
            int resID = getResources().getIdentifier(buttonsID[i], "id", getPackageName());
            buttons[i] = findViewById(resID);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    onImageButtonPressed(v);
                }
            });
        }
    }

    //TODO
    //Syncs UIs single dimension board with logic's multidimensional board
    private void syncBoards(){
        for (int i = 0; i < board.length; i++){
            board[i] = game.getBoard()[i / 8][i % 8];
        }
    }

    private void updateGameView() {
        for (int i = 0; i < board.length; i++) {
            CheckersLogic.square square = board[i];
            if (square == CheckersLogic.square.BLACK)
                buttons[i].setImageResource(R.drawable.black);
            else if (square == CheckersLogic.square.WHITE)
                buttons[i].setImageResource(R.drawable.white);
            else if (square == CheckersLogic.square.OPEN)
                buttons[i].setImageResource(R.drawable.b);
        }
    }

    private void onImageButtonPressed(View v) {
        ImageButton b = null;
        CheckersLogic.square piece = CheckersLogic.square.OPEN;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getId() == v.getId()) {
                b = buttons[i];
                piece = board[i];
                if (selectedMove == null)
                    selectedMove = new Move(i / 8, i % 8);
                else if (selectedMove != null && destinationMove == null) {
                    destinationMove = new Move(i / 8, i % 8);
                }
            }
        }
        //Exits if button isn't found or piece hasn't been configured. Should never get here
        if (b == null || piece == CheckersLogic.square.OPEN) {
            return;
        }

        //TODO
        //if valid move...
        if (game.validMove(selectedMove, destinationMove)) {

            //reset variables after valid move
            selectedMove = null;
            destinationMove = null;

            //if turn can end... e.i. no more available moves that have to be made
            if (game.checkEndTurn()) {
                game.setTurn(false);
            }

            //checks and handles winning situations
            checkWinner();

        }

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
            Toast.makeText(getApplicationContext(), "Red Won!", Toast.LENGTH_LONG).show();
        }

    }

    class Move {
        private int row, col;
        private int id;

        public Move(int row, int col) {
            setLocation(row, col);
            id = (row * 8) + col;
        }

        public void setLocation(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public int getId() {
            return id;
        }
    }
}
