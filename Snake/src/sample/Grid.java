package sample;

public class Grid {
    private final int gridSizeX;
    private final int gridSizeY;

    private CellType [][] cells;

    Grid(int gridSizeX, int gridSizeY) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        cells = new CellType[gridSizeX][gridSizeY];
        for (int i = 0; i < gridSizeX; i++) {
            for (int j =0; j < gridSizeY; j++) {
                cells[i][j] = CellType.BACKGROUND;
            }
        }
    }

    public int getGridSizeX() {
       return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setCellType(int idX, int idY, CellType type) {
        cells[idX][idY] = type;
    }

    public CellType getCellType(int idX, int idY) {
        return cells[idX][idY];
    }

    public enum CellType {
        SNAKE,
        FOOD,
        BACKGROUND
    }
}
