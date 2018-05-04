package Game;

public class MasterBoard {
    private Board[] boards;

    private int nextBoard = -1;

    public MasterBoard(){
        boards = new Board[9];
        for(int i = 0; i < boards.length; i++){
            boards[i] = new Board();
        }
    }

    public boolean wasWon(){
        char one,two,three;
        for(int i = 0; i < 9; i+=3){
            one = boards[i].getBoardPiece();
            two = boards[i+1].getBoardPiece();
            three = boards[i+2].getBoardPiece();
            if(one == two && two == three && one != '-'){
                return true;
            }
        }
        for(int i = 0; i < 3; i++){
            one = boards[i].getBoardPiece();
            two = boards[i+3].getBoardPiece();
            three = boards[i+6].getBoardPiece();
            if(one == two && two == three && one != '-'){
                return true;
            }
        }
        if(boards[0].getBoardPiece() == boards[4].getBoardPiece() &&
                boards[4].getBoardPiece() == boards[8].getBoardPiece() && boards[4].getBoardPiece() != '-') {
            return true;
        }
        if(boards[2].getBoardPiece() == boards[4].getBoardPiece() &&
                boards[4].getBoardPiece() == boards[6].getBoardPiece() && boards[4].getBoardPiece() != '-') {
            return true;
        }
        return false;
    }

    public void playPiece(char piece, int boardX, int boardY, int pieceX, int pieceY){
        boards[translate(boardX,boardY)].playPiece(piece, translate(pieceX,pieceY));
        nextBoard = translate(pieceX, pieceY);
        if(boards[nextBoard].isBoardWon()){
            nextBoard = -1;
        }
    }

    public char getPiece(int boardX, int boardY, int pieceX, int pieceY){
        return getBoard(boardX, boardY).getPiece(translate(pieceX, pieceY));
    }

    public boolean isValidMove(int boardX, int boardY, int pieceX, int pieceY){
        if(nextBoard != -1 && nextBoard != translate(boardX, boardY)) return false;
        return boards[translate(boardX, boardY)].isValidMove(translate(pieceX, pieceY));
    }

    public int getNextBoard(){
        return nextBoard;
    }

    public Board getBoard(int x, int y){
        return boards[translate(x, y)];
    }

    private int translate(int x, int y){
        return x + y * 3;
    }
}
