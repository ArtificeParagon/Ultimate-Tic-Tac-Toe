package Game;

public class Board {
    private char[] pieces; // blank = 0, X = 1, O = 2
    private boolean boardWon;
    private char boardPiece;

    public Board(){
        pieces = new char[9];
        for(int i = 0; i < pieces.length; i++){
            pieces[i] = 0;
        }
        boardWon = false;

    }

    private void checkForWin(){
        char one,two,three;
        for(int i = 0; i < 9; i+=3){
            one = pieces[i];
            two = pieces[i+1];
            three = pieces[i+2];
            if(one == two && two == three){
                boardWon = true;
                boardPiece = one;
            }
        }
        for(int i = 0; i < 3; i++){
            one = pieces[i];
            two = pieces[i+3];
            three = pieces[i+6];
            if(one == two && two == three){
                boardWon = true;
                boardPiece = one;
            }
        }
        
    }

    private boolean isValidMove(char piece, int square){
        return true;
    }

    public void playPiece(char piece, int square){
        if(boardWon){

        } else if (isValidMove(piece, square)){

        } else {

        }
        checkForWin();
    }

    public char getPiece(int piece){
        return pieces[piece];
    }
}
