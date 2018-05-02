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

}
