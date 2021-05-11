package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        int WINDOW_HEIGHT = 1000;
        int WINDOW_WIDTH = 1000;

        int GRID_SIZE_X = 20;
        int GRID_SIZE_Y = 20;

        long FRAME_DURATION = 60000000L;

        Pane pane = new Pane();
        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);

        // создаем сущностей
        Grid grid = new Grid(GRID_SIZE_X, GRID_SIZE_Y);
        
        FoodGenerator generator = new FoodGenerator(grid);
        generator.generateNewFood();

        Snake hyperSnake = new Snake(grid, generator);
        Renderer renderer = new Renderer(grid, pane, WINDOW_HEIGHT, WINDOW_WIDTH);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case W -> hyperSnake.setDirection(Snake.Direction.UP);
                case S -> hyperSnake.setDirection(Snake.Direction.DOWN);
                case D -> hyperSnake.setDirection(Snake.Direction.RIGHT);
                case A -> hyperSnake.setDirection(Snake.Direction.LEFT);
            }
        });

        new AnimationTimer(){
            long duration;
            @Override
            public void handle(long arg0) {
                if(arg0 - duration > FRAME_DURATION){
                    duration = arg0;

                    if(!hyperSnake.move()){
                        Platform.exit();
                    }

                    renderer.render();
                }

            }
        }.start();

        primaryStage.setTitle("Minecraft Snake");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
