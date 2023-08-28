package engine.grid.impl;

import java.util.Random;

public class Grid {
    private int lengthSize;
    private int widthSize;
    private final Tile[][] locationGrid;
    public Grid(int length, int width) {
        this.lengthSize = length;
        this.widthSize = width;
        this.locationGrid = new Tile[lengthSize][widthSize];
        for(int i = 0; i < lengthSize; i++) {
            for (int j = 0; j < widthSize; j++) {
                locationGrid[i][j] = new Tile();
            }
        }
    }

    public void setGridLength(int length) {this.lengthSize = length;}
    public void setGridWidth(int width) {this.widthSize = width;}

    public boolean addSackToGrid(Sack sack) {
        if (!CheckIfGridIsFull()) {
            int randomLength;
            int randomWidth;
            Random random = new Random();
            do {
                randomLength = random.nextInt(lengthSize);
                randomWidth = random.nextInt(widthSize);

            } while (locationGrid[randomLength][randomWidth].getTaken());
            locationGrid[randomLength][randomWidth].setSack(sack);
            return true;
        }
        else {
            // TODO: message that it can't be done? exception?
            return false;
        }
    }

    public boolean CheckIfGridIsFull() {
        for (int i = 0; i < lengthSize; i++) {
            for (int j = 0; j < widthSize; j++) {
                if (!locationGrid[i][j].getTaken()) {
                    return false;
                }
            }
        }
        return true;
    }
}
