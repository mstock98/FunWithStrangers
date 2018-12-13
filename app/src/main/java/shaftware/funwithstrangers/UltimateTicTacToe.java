package shaftware.funwithstrangers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import static shaftware.funwithstrangers.TttLogicBase.Piece;
import static shaftware.funwithstrangers.TttLogicBase.Winner;

public class UltimateTicTacToe extends AppCompatActivity {

    UltimateTTTLogic UTTTGame;

    ImageButton c000, c001, c002,
            c010, c011, c012,
            c020, c021, c022,
            c100, c101, c102,
            c110, c111, c112,
            c120, c121, c122,
            c200, c201, c202,
            c210, c211, c212,
            c220, c221, c222,
            c300, c301, c302,
            c310, c311, c312,
            c320, c321, c322,
            c400, c401, c402,
            c410, c411, c412,
            c420, c421, c422,
            c500, c501, c502,
            c510, c511, c512,
            c520, c521, c522,
            c600, c601, c602,
            c610, c611, c612,
            c620, c621, c622,
            c700, c701, c702,
            c710, c711, c712,
            c720, c721, c722,
            c800, c801, c802,
            c810, c811, c812,
            c820, c821, c822;
    ImageButton[][] buttons = {{c000, c001, c002, c100, c101, c102, c200, c201, c202},
            {c010, c011, c012, c110, c111, c112, c210, c211, c212},
            {c020, c021, c022, c120, c121, c122, c220, c221, c222},
            {c300, c301, c302, c400, c401, c402, c500, c501, c502},
            {c310, c311, c312, c410, c411, c412, c510, c511, c512},
            {c320, c321, c322, c420, c421, c422, c520, c521, c522},
            {c600, c601, c602, c700, c701, c702, c800, c801, c802},
            {c610, c611, c612, c710, c711, c712, c810, c811, c812},
            {c620, c621, c622, c720, c721, c722, c820, c821, c822}};
    String[][] buttonsID = {{"c000", "c001", "c002", "c100", "c101", "c102", "c200", "c201", "c202"},
            {"c010", "c011", "c012", "c110", "c111", "c112", "c210", "c211", "c212"},
            {"c020", "c021", "c022", "c120", "c121", "c122", "c220", "c221", "c222"},
            {"c300", "c301", "c302", "c400", "c401", "c402", "c500", "c501", "c502"},
            {"c310", "c311", "c312", "c410", "c411", "c412", "c510", "c511", "c512"},
            {"c320", "c321", "c322", "c420", "c421", "c422", "c520", "c521", "c522"},
            {"c600", "c601", "c602", "c700", "c701", "c702", "c800", "c801", "c802"},
            {"c610", "c611", "c612", "c710", "c711", "c712", "c810", "c811", "c812"},
            {"c620", "c621", "c622", "c720", "c721", "c722", "c820", "c821", "c822"}};

    boolean playerFirst; // who goes first, true is me, false is the other person
    boolean begin; // can we start playing
    Piece playerPIECE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_tic_tac_toe);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UTTTGame = new UltimateTTTLogic(Piece.X);

        //Creates on click listeners and everything for the TTT grid buttons
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int resID = getResources().getIdentifier(buttonsID[i][j], "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        tttPressed(v);
                    }
                });
            }
        }

        updateGameView(UTTTGame);
    }

    //Interface between button pressed and TttLogic
    private void tttPressed(View v) {


        int row = -1, col = -1;

        boolean validSpot;


            if (true /*UTTTGame.isTurn()*/) {
                //Button locations
                switch (v.getId()) {
                    case R.id.c000:
                        row = 0;
                        col = 0;
                        break;
                    case R.id.c001:
                        row = 0;
                        col = 1;
                        break;
                    case R.id.c002:
                        row = 0;
                        col = 2;
                        break;
                    case R.id.c010:
                        row = 1;
                        col = 0;
                        break;
                    case R.id.c011:
                        row = 1;
                        col = 1;
                        break;
                    case R.id.c012:
                        row = 1;
                        col = 2;
                        break;
                    case R.id.c020:
                        row = 2;
                        col = 0;
                        break;
                    case R.id.c021:
                        row = 2;
                        col = 1;
                        break;
                    case R.id.c022:
                        row = 2;
                        col = 2;
                        break;
                    case R.id.c100:
                        row = 0;
                        col = 3;
                        break;
                    case R.id.c101:
                        row = 0;
                        col = 4;
                        break;
                    case R.id.c102:
                        row = 0;
                        col = 5;
                        break;
                    case R.id.c110:
                        row = 1;
                        col = 3;
                        break;
                    case R.id.c111:
                        row = 1;
                        col = 4;
                        break;
                    case R.id.c112:
                        row = 1;
                        col = 5;
                        break;
                    case R.id.c120:
                        row = 2;
                        col = 3;
                        break;
                    case R.id.c121:
                        row = 2;
                        col = 4;
                        break;
                    case R.id.c122:
                        row = 2;
                        col = 5;
                        break;
                    case R.id.c200:
                        row = 0;
                        col = 6;
                        break;
                    case R.id.c201:
                        row = 0;
                        col = 7;
                        break;
                    case R.id.c202:
                        row = 0;
                        col = 8;
                        break;
                    case R.id.c210:
                        row = 1;
                        col = 6;
                        break;
                    case R.id.c211:
                        row = 1;
                        col = 7;
                        break;
                    case R.id.c212:
                        row = 1;
                        col = 8;
                        break;
                    case R.id.c220:
                        row = 2;
                        col = 6;
                        break;
                    case R.id.c221:
                        row = 2;
                        col = 7;
                        break;
                    case R.id.c222:
                        row = 2;
                        col = 8;
                        break;
                    case R.id.c300:
                        row = 3;
                        col = 0;
                        break;
                    case R.id.c301:
                        row = 3;
                        col = 1;
                        break;
                    case R.id.c302:
                        row = 3;
                        col = 2;
                        break;
                    case R.id.c310:
                        row = 4;
                        col = 0;
                        break;
                    case R.id.c311:
                        row = 4;
                        col = 1;
                        break;
                    case R.id.c312:
                        row = 4;
                        col = 2;
                        break;
                    case R.id.c320:
                        row = 5;
                        col = 0;
                        break;
                    case R.id.c321:
                        row = 5;
                        col = 1;
                        break;
                    case R.id.c322:
                        row = 5;
                        col = 2;
                        break;
                    case R.id.c400:
                        row = 3;
                        col = 3;
                        break;
                    case R.id.c401:
                        row = 3;
                        col = 4;
                        break;
                    case R.id.c402:
                        row = 3;
                        col = 5;
                        break;
                    case R.id.c410:
                        row = 4;
                        col = 3;
                        break;
                    case R.id.c411:
                        row = 4;
                        col = 4;
                        break;
                    case R.id.c412:
                        row = 4;
                        col = 5;
                        break;
                    case R.id.c420:
                        row = 5;
                        col = 3;
                        break;
                    case R.id.c421:
                        row = 5;
                        col = 4;
                        break;
                    case R.id.c422:
                        row = 5;
                        col = 5;
                        break;
                    case R.id.c500:
                        row = 3;
                        col = 6;
                        break;
                    case R.id.c501:
                        row = 3;
                        col = 7;
                        break;
                    case R.id.c502:
                        row = 3;
                        col = 8;
                        break;
                    case R.id.c510:
                        row = 4;
                        col = 6;
                        break;
                    case R.id.c511:
                        row = 4;
                        col = 7;
                        break;
                    case R.id.c512:
                        row = 4;
                        col = 8;
                        break;
                    case R.id.c520:
                        row = 5;
                        col = 6;
                        break;
                    case R.id.c521:
                        row = 5;
                        col = 7;
                        break;
                    case R.id.c522:
                        row = 5;
                        col = 8;
                        break;
                    case R.id.c600:
                        row = 6;
                        col = 0;
                        break;
                    case R.id.c601:
                        row = 6;
                        col = 1;
                        break;
                    case R.id.c602:
                        row = 6;
                        col = 2;
                        break;
                    case R.id.c610:
                        row = 7;
                        col = 0;
                        break;
                    case R.id.c611:
                        row = 7;
                        col = 1;
                        break;
                    case R.id.c612:
                        row = 7;
                        col = 2;
                        break;
                    case R.id.c620:
                        row = 8;
                        col = 0;
                        break;
                    case R.id.c621:
                        row = 8;
                        col = 1;
                        break;
                    case R.id.c622:
                        row = 8;
                        col = 2;
                        break;
                    case R.id.c700:
                        row = 6;
                        col = 3;
                        break;
                    case R.id.c701:
                        row = 6;
                        col = 4;
                        break;
                    case R.id.c702:
                        row = 6;
                        col = 5;
                        break;
                    case R.id.c710:
                        row = 7;
                        col = 3;
                        break;
                    case R.id.c711:
                        row = 7;
                        col = 4;
                        break;
                    case R.id.c712:
                        row = 7;
                        col = 5;
                        break;
                    case R.id.c720:
                        row = 8;
                        col = 3;
                        break;
                    case R.id.c721:
                        row = 8;
                        col = 4;
                        break;
                    case R.id.c722:
                        row = 8;
                        col = 5;
                        break;
                    case R.id.c800:
                        row = 6;
                        col = 6;
                        break;
                    case R.id.c801:
                        row = 6;
                        col = 7;
                        break;
                    case R.id.c802:
                        row = 6;
                        col = 8;
                        break;
                    case R.id.c810:
                        row = 7;
                        col = 6;
                        break;
                    case R.id.c811:
                        row = 7;
                        col = 7;
                        break;
                    case R.id.c812:
                        row = 7;
                        col = 8;
                        break;
                    case R.id.c820:
                        row = 8;
                        col = 6;
                        break;
                    case R.id.c821:
                        row = 8;
                        col = 7;
                        break;
                    case R.id.c822:
                        row = 8;
                        col = 8;
                        break;
                }
                validSpot = UTTTGame.pickSpot(row, col);

                if (validSpot) {
                    UTTTGame.swapPiece();
                    updateGameView(UTTTGame);
                }

                // Dumb AI
                if (!checkWinner()) {
                    do {
                        row = (int) (Math.random() * 9);
                        col = (int) (Math.random() * 9);
                    } while (!UTTTGame.pickSpot(row, col));

                    UTTTGame.swapPiece();
                    updateGameView(UTTTGame);
                } else {
                    updateGameView(UTTTGame);
                }
            }



            /*if(begin){

                //Game does not accept an invalid input and will wait for player to enter in something that is valid
                boolean validSpot = TttGame.pickSpot(row, col);// choose a location
                if(Globals.getConnected()){
                    if (validSpot) {
                        Toast toast = Toast.makeText(getApplicationContext(), "boop", Toast.LENGTH_SHORT);
                        toast.show();
                        TttGame.swapTurn();
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                        updateGameView(TttGame);
                        checkWinner();
                        sendBoard();
                    }
                }else{
                    if (validSpot) {
                        TttGame.swapTurn();
                        ai.game.receiveBoard(TttGame.getBoard());
                        updateGameView(TttGame);

                        //If someone has won stop the AI from playing
                        if (!checkWinner()) {
                            ai.TttAiTurn();
                            TttGame.receiveBoard(ai.game.getBoard());
                            updateGameView(TttGame);
                        }

                        //Disable player from doing anything if someone has won
                        if (!checkWinner())
                            TttGame.swapTurn();
                    }
                }
            }*/
        }

    //Refreshes every button's image to correspond with the gameboard
    private void updateGameView(UltimateTTTLogic game) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = game.getBoardPiece(i, j);
                if (piece == Piece.X) {
                    buttons[i][j].setImageResource(R.drawable.x);
                }
                if (piece == Piece.O) {
                    buttons[i][j].setImageResource(R.drawable.o);
                }
                if (piece == Piece.OPEN) {
                    buttons[i][j].setImageResource(R.drawable.highlighted_translucent);
                }
                if (piece == Piece.DISABLED) {
                    buttons[i][j].setImageResource(R.drawable.blank);
                }
            }
        }
    }

    private boolean checkWinner() {
        Winner winner = UTTTGame.checkWinner();
        if (winner == Winner.O) {
            Toast.makeText(getApplicationContext(), "O Won!", Toast.LENGTH_LONG).show();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    UTTTGame.setBoardPiece(Piece.O, i, j);
                }
            }
            return true;
        } else if (winner == Winner.X) {
            Toast.makeText(getApplicationContext(), "X Won!", Toast.LENGTH_LONG).show();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    UTTTGame.setBoardPiece(Piece.X, i, j);
                }
            }
            return true;
        } else if (winner == Winner.TIE) {
            Toast.makeText(getApplicationContext(), "Tie!", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}