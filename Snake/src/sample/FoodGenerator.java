package sample;

import java.util.Random;

public class FoodGenerator {

    private final Grid grid;

    FoodGenerator(Grid grid) {
        this.grid = grid;
    }

    public void generateNewFood() {
        Random random = new Random();
        int x = random.nextInt(grid.getGridSizeX());
        int y = random.nextInt(grid.getGridSizeY());
        grid.setCellType(x, y, Grid.CellType.FOOD);
    }
}
