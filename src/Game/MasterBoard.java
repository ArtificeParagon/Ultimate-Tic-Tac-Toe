package Game;

public class MasterBoard {
    private Board[] boards;

    public MasterBoard(){
        boards = new Board[9];
        for(int i = 0; i < boards.length; i++){
            boards[i] = new Board();
        }
    }

    public void playPiece(char piece, int boardX, int boardY, int pieceX, int pieceY){
        boards[translate(boardX,boardY)].playPiece(piece, translate(pieceX,pieceY));
    }

    public char getPiece(int boardX, int boardY, int pieceX, int pieceY){
        return getBoard(boardX, boardY).getPiece(translate(pieceX, pieceY));
    }

    public Board getBoard(int x, int y){
        return boards[translate(x, y)];
    }

    private int translate(int x, int y){
        return x + y * 3;
    }
}
