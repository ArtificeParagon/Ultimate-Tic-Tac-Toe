package Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {
    private static GraphicsContext gc;
    private double canvasWidth;
    private double canvasHeight;

    private static  Renderer INSTANCE;
    private Renderer(GraphicsContext gc){
        this.gc = gc;
        canvasWidth = gc.getCanvas().getWidth();
        canvasHeight = gc.getCanvas().getHeight();
    }
    public static Renderer getInstance(GraphicsContext gc) {
        if(INSTANCE == null){
            INSTANCE = new Renderer(gc);
        }
        return INSTANCE;
    }
    public static Renderer getInstance(){
        return INSTANCE;
    }

    public void drawBoard(){
        int third = (int) canvasHeight / 3;
        int halfWidth = 5;
        gc.setFill(Color.TAN);
        gc.fillRect(0, 0, canvasHeight, canvasHeight);

        // Draw main board
        gc.setFill(Color.BLACK);
        gc.fillRect(0, third-halfWidth, canvasHeight, halfWidth*2);
        gc.fillRect(0, (third * 2)-halfWidth, canvasHeight, halfWidth*2);
        gc.fillRect(third-halfWidth, 0, halfWidth*2, canvasHeight);
        gc.fillRect((third * 2)-halfWidth, 0, halfWidth*2, canvasHeight);

        // Draw smaller boards
        int smallThird = third / 3;
        halfWidth = 2;
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++){
                gc.fillRect(x*third, smallThird-halfWidth + (y*third), third, halfWidth*2);
                gc.fillRect(x*third, (smallThird * 2)-halfWidth + (y*third), third, halfWidth*2);
                gc.fillRect(smallThird-halfWidth + (x * third), y*third, halfWidth*2, third);
                gc.fillRect((smallThird * 2)-halfWidth + (x*third), y*third, halfWidth*2, third);
            }
        }
    }

    public void drawX(int boardX, int boardY, int pieceX, int pieceY){
        double smallThird = canvasHeight/9;
        smallThird -= 20;
        double tokenX = getPieceX(boardX, pieceX);
        double tokenY = getPieceY(boardY, pieceY);

        gc.setStroke(Color.RED);
        gc.setLineWidth(4);
        gc.strokeLine(tokenX, tokenY, tokenX + smallThird, tokenY + smallThird);
        gc.strokeLine(tokenX, tokenY + smallThird, tokenX + smallThird, tokenY);
    }

    public void drawO(int boardX, int boardY, int pieceX, int pieceY){
        double pieceWidth = (canvasHeight/9)-20;

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(4);
        gc.strokeOval(getPieceX(boardX, pieceX), getPieceY(boardY, pieceY), pieceWidth, pieceWidth);
    }

    private double getPieceX(int boardX, int pieceX){
        double third = canvasHeight / 3;
        double smallThird = canvasHeight / 9;
        double result = (third * boardX) + (smallThird * pieceX) + 10;
        return result;
    }
    private double getPieceY(int boardY, int pieceY){
        double third = canvasHeight / 3;
        double smallThird = canvasHeight / 9;
        double result = (third * boardY) + (smallThird * pieceY) + 10;
        return result;
    }

}
