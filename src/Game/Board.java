package Game;

public class Board {
    private int[] pieces; // blank = 0, X = 1, O = 2

    public Board(){
        pieces = new int[9];
        for(int i = 0; i < pieces.length; i++){
            pieces[i] = 0;
        }
    }

    public void playPiece(char piece, int square){
        switch(piece){
            case 'x':
                pieces[square] = 1;
                break;
            case 'o':
                pieces[square] = 2;
                break;
        }
    }
}
