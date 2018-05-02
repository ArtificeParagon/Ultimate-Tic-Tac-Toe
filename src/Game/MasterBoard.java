package Game;

public class MasterBoard {
    private Board[] boards;

    public MasterBoard(){
        boards = new Board[9];
        for(int i = 0; i < boards.length; i++){
            boards[i] = new Board();
        }
    }

    public void playPiece(char piece, int x, int y, int ix, int iy){
        boards[translate(x,y)].playPiece(piece, translate(x,y));
    }

    private int translate(int x, int y){
        return x + y * 3;
    }
}
