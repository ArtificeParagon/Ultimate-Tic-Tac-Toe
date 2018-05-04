package Game;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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

    public void renderInfo(){
        double offset = canvasHeight;
        double infoWidth = canvasWidth - canvasHeight;

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        changeFontSize(gc, 40);
        gc.fillText("ULTIMATE", (infoWidth /2 ) + offset, 50);
        gc.fillText("TIC-TAC-TOE", (infoWidth /2 ) + offset, 100);

        double smallThird = canvasHeight/9;
        smallThird -= 20;

        gc.setStroke(Color.RED);
        gc.setLineWidth(4);
        double xx = offset + (infoWidth / 2) - smallThird - 20;
        double xy = 200;
        gc.strokeLine(xx, xy, xx + smallThird, xy + smallThird);
        gc.strokeLine(xx, xy+ smallThird, xx + smallThird, xy);

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(4);
        double ox = offset + (infoWidth / 2) + 20;
        double oy = 200;
        gc.strokeOval(ox, oy, smallThird, smallThird);
    }

    public void underline(char icon){
        double offset = canvasHeight;
        double infoWidth = canvasWidth - canvasHeight;
        double smallThird = canvasHeight/9;
        double xx = offset + (infoWidth / 2) - smallThird - 20;
        double ox = offset + (infoWidth / 2) + 20;
        double y = 200 + smallThird + 20;

        gc.setLineWidth(4);
        gc.setStroke(Color.WHITE);
        gc.strokeLine(xx-10, y, ox + smallThird + 10, y);
        gc.setStroke(Color.BLACK);
        if(icon == 'o'){
            gc.strokeLine(xx - 10, y, xx + 10 + smallThird, y);
        } else {
            gc.strokeLine(ox - 10, y, ox + 10 + smallThird, y);
        }
    }

    public void renderMessage(String message){
        double offset = canvasHeight;
        double infoWidth = canvasWidth - canvasHeight;

        gc.setFill(Color.WHITE);
        gc.fillRect(offset, canvasHeight-50, infoWidth, 50);

        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        changeFontSize(gc, 22);
        gc.fillText(message, (infoWidth / 2) + offset, canvasHeight - 40);
    }

    public void renderPlayableSquare(int square){
        unRenderPlayableSquare();
        double offset = canvasHeight;
        double infoWidth = canvasWidth - canvasHeight;
        renderSmallBoard(offset, infoWidth);

        double topLeftX = offset + (infoWidth/2) - 75;
        double topLeftY = canvasHeight-300;
        if(square < 0) {
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    double squareX = topLeftX + (x*50) + 4;
                    double squareY = topLeftY + (y*50) + 4;
                    gc.setFill(Color.RED);
                    gc.fillRect(squareX, squareY, 42, 42);
                }
            }
        } else {
            int x = square % 3;
            int y = square / 3;
            double squareX = topLeftX + (x*50) + 4;
            double squareY = topLeftY + (y*50) + 4;
            gc.setFill(Color.RED);
            gc.fillRect(squareX, squareY, 42, 42);
        }
    }
    private void renderSmallBoard(double offset, double infoWidth){
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(4);
        gc.strokeLine(offset + (infoWidth / 2) - 75, canvasHeight-200, offset + (infoWidth / 2) + 75, canvasHeight-200);
        gc.strokeLine(offset + (infoWidth / 2) - 75, canvasHeight-250, offset + (infoWidth / 2) + 75, canvasHeight-250);
        gc.strokeLine(offset + (infoWidth / 2) - 25, canvasHeight-300, offset + (infoWidth / 2) - 25, canvasHeight-150);
        gc.strokeLine(offset + (infoWidth / 2) + 25, canvasHeight-300, offset + (infoWidth / 2) + 25, canvasHeight-150);
    }
    private void unRenderPlayableSquare(){
        double offset = canvasHeight;
        double infoWidth = canvasWidth - canvasHeight;
        double topLeftX = offset + (infoWidth/2) - 75;
        double topLeftY = canvasHeight-300;

        gc.setFill(Color.WHITE);
        gc.fillRect(topLeftX-2, topLeftY-2, 154, 154);
    }

    private void changeFontSize(GraphicsContext gc, int size){
        gc.setFont(Font.font(gc.getFont().toString(), size));
    }

}
