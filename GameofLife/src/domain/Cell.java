
package domain;

public class Cell {
    private int width;
    private int height;
    private int x;
    private int y;
    private int numberOfNeighbours;
    
    public Cell(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.numberOfNeighbours = 0;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
}
