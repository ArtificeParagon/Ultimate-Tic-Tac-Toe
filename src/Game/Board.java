package Game;

public class Board {
    private char[] pieces; // blank = 0, X = 1, O = 2
    private boolean boardWon;
    private char boardPiece;

    public Board(){
        pieces = new char[9];
        for(int i = 0; i < pieces.length; i++){
            pieces[i] = '-';
        }
        boardWon = false;
        boardPiece = '-';
    }

    private void checkForWin(){
        char one,two,three;
        for(int i = 0; i < 9; i+=3){
            one = pieces[i];
            two = pieces[i+1];
            three = pieces[i+2];
            if(one == two && two == three && one != '-'){
                boardWon = true;
                boardPiece = one;
            }
        }
        for(int i = 0; i < 3; i++){
            one = pieces[i];
            two = pieces[i+3];
            three = pieces[i+6];
            if(one == two && two == three && one != '-'){
                boardWon = true;
                boardPiece = one;
            }
        }
        if(pieces[0] == pieces[4] && pieces[4] == pieces[8] && pieces[4] != '-') {
            boardWon = true;
            boardPiece = pieces[4];
        }
        if(pieces[2] == pieces[4] && pieces[4] == pieces[6] && pieces[4] != '-') {
            boardWon = true;
            boardPiece = pieces[4];
        }
    }

    public boolean isValidMove(int square){
        if(boardWon) return false;
        if(pieces[square] == '-') return true;
        return false;
    }

    public void playPiece(char piece, int square){
        pieces[square] = piece;
        checkForWin();
    }

    public boolean isBoardWon() {return boardWon;}

    public char getPiece(int piece){
        return pieces[piece];
    }
    public char getBoardPiece() {
        return boardPiece;
    }
}
