package Game;

public class Game {

    private MasterBoard board;

    public Game(){
        board = new MasterBoard();
    }

    public void playPiece(int[] turn, char player){
        board.playPiece(player, turn[0], turn[1], turn[2], turn[3]);

    }

    public boolean isWon(){
        return board.wasWon();
    }

    public int getNextBoard(){
        return board.getNextBoard();
    }

    public boolean isMoveValid(int boardX, int boardY, int pieceX, int pieceY){
        return board.isValidMove(boardX, boardY, pieceX, pieceY);
    }
}
