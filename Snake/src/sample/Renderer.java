package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Renderer {

    private final int windowSizeX;
    private final int windowSizeY;
    private final int gridSizeX;
    private final int gridSizeY;

    private Pane pane;
    private Grid grid;

    Renderer(Grid grid, Pane pane, int window_size_x, int window_size_y) {
        this.windowSizeX = window_size_x;
        this.windowSizeY = window_size_y;
        this.gridSizeX = grid.getGridSizeX();
        this.gridSizeY = grid.getGridSizeY();
        this.pane = pane;
        this.grid = grid;
    }

    public void render() {
        pane.getChildren().clear();

        int cell_x = windowSizeX / gridSizeX;
        int cell_y = windowSizeY / gridSizeY;

        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                String path = null;
                switch (grid.getCellType(i, j)) {
                    case SNAKE -> path = "cow.png";
                    case FOOD -> path = "heart.jpg";
                }
                // отрисовываем фоновый спрайт
                Image background = new Image("back.jpg", cell_x, cell_y, false, false);
                ImageView backView = new ImageView();
                backView.setImage(background);
                backView.relocate(i*cell_x, j*cell_y);
                pane.getChildren().add(backView);
                // если клетка со змеей или едой, то отрисовываем нужный спрайт
                if (path != null) {
                    Image sprite = new Image(path, cell_x, cell_y, false, false);
                    ImageView spriteView = new ImageView();
                    backView.setImage(sprite);
                    backView.relocate(i*cell_x, j*cell_y);
                    pane.getChildren().add(spriteView);
                }
            }
        }
    }
}
