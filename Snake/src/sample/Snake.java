package sample;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class Snake {

    private int headIdX; private int headIdY;
    private int tailIdX; private int tailIdY;
    private int size;
    private final Grid grid;
    private Direction direction;
    private Deque<Direction> moves;
    private FoodGenerator generator;

    Snake(Grid grid, FoodGenerator generator) {
        this.grid = grid;
        direction = Direction.LEFT;
        size = 1;
        headIdX = grid.getGridSizeX() / 2;
        headIdY = grid.getGridSizeY() / 2;
        tailIdX = grid.getGridSizeX() / 2;
        tailIdY = grid.getGridSizeY() / 2;
        moves = new ArrayDeque<>();
        this.generator = generator;
    }

    // змейка ползет по полю гыг
    public boolean move() {

        // проверки на столкновение с краем поля
        if (((headIdX - 1) < 0 && direction == Direction.LEFT) || ((headIdX + 1) >= grid.getGridSizeX() && direction == Direction.RIGHT)) {
            return false;
        }
        if (((headIdY - 1) < 0 && direction == Direction.UP) || ((headIdY + 1) >= grid.getGridSizeY() && direction == Direction.DOWN)) {
            return false;
        }

        switch (direction) {
            case LEFT -> {
                if (grid.getCellType(headIdX - 1, headIdY) == Grid.CellType.SNAKE) { return false; }
                headIdX = headIdX - 1;
                moves.addFirst(Direction.LEFT);
            }
            case RIGHT -> {
                if (grid.getCellType(headIdX + 1, headIdY) == Grid.CellType.SNAKE) { return false; }
                headIdX = headIdX + 1;
                moves.addFirst(Direction.RIGHT);
            }
            case UP -> {
                if (grid.getCellType(headIdX, headIdY - 1) == Grid.CellType.SNAKE) { return false; }
                headIdY = headIdY - 1;
                moves.addFirst(Direction.UP);
            }
            case DOWN -> {
                if (grid.getCellType(headIdX, headIdY + 1) == Grid.CellType.SNAKE) { return false; }
                headIdY = headIdY + 1;
                moves.addFirst(Direction.DOWN);
            }
        }
        if (grid.getCellType(headIdX, headIdY) != Grid.CellType.FOOD) {
            taleMove(Objects.requireNonNull(moves.pollLast()));
        } else {
            generator.generateNewFood();
        }
        grid.setCellType(headIdX, headIdY, Grid.CellType.SNAKE);

        return true;
    }

    private void taleMove(Direction dir) {
        grid.setCellType(tailIdX, tailIdY, Grid.CellType.BACKGROUND);
        switch (dir) {
            case UP -> tailIdY = tailIdY - 1;
            case DOWN -> tailIdY = tailIdY + 1;
            case LEFT -> tailIdX = tailIdX - 1;
            case RIGHT -> tailIdX = tailIdX + 1;
        }
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
