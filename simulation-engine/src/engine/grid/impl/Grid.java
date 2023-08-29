package engine.grid.impl;

import engine.entity.impl.EntityInstance;
import engine.grid.api.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO: handle seamlessness.

public class Grid {
    private int rows;
    private int cols;
    private final Tile[][] locationGrid;

    public Grid(int length, int width) {
        this.rows = length;
        this.cols = width;
        this.locationGrid = new Tile[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                locationGrid[i][j] = new Tile();
            }
        }
    }

    public void setGridLength(int length) {
        this.rows = length;
    }

    public void setGridWidth(int width) {
        this.cols = width;
    }

    public boolean addSackToGrid(Sack sack) {
        if (!CheckIfGridIsFull()) {
            int randomLength;
            int randomWidth;
            Random random = new Random();
            do {
                randomLength = random.nextInt(rows);
                randomWidth = random.nextInt(cols);

            } while (locationGrid[randomLength][randomWidth].getTaken());
            locationGrid[randomLength][randomWidth].setSack(sack);
            return true;
        } else {
            // TODO: message that it can't be done? exception?
            return false;
        }
    }

    public boolean CheckIfGridIsFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!locationGrid[i][j].getTaken()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void assignSacks(List<EntityInstance> instances) {
        for (EntityInstance currentEntity : instances) {
            Sack sack = new Sack(currentEntity);
            addSackToGrid(sack);
        }
    }

    public Coordinate findNewTile(int row, int col) {
        int tempRow, tempCol;
        List<Coordinate> possibleMoves = new ArrayList<>();
        //Moving up
        if (row == 0) {
            possibleMoves.add(new Coordinate(rows - 1, col));
        } else {
            possibleMoves.add(new Coordinate(row - 1, col));
        }

        //Moving down
        if (row == rows - 1) {
            possibleMoves.add(new Coordinate(0, col));
        } else {
            possibleMoves.add(new Coordinate(row + 1, col));
        }

        //Moving right
        if (col == cols - 1) {
            possibleMoves.add(new Coordinate(row, 0));
        } else {
            possibleMoves.add(new Coordinate(row, col + 1));
        }

        //Moving left
        if (col == 0) {
            possibleMoves.add(new Coordinate(row, cols - 1));
        } else {
            possibleMoves.add(new Coordinate(row, col - 1));
        }
        do {
            Random random = new Random();
            int randomIndex = random.nextInt(possibleMoves.size());
            if (!locationGrid[possibleMoves.get(randomIndex).getRow()][possibleMoves.get(randomIndex).getCol()].getTaken()) {
                return possibleMoves.get(randomIndex);
            } else {
                possibleMoves.remove(randomIndex);
            }
        } while (!possibleMoves.isEmpty());
        return new Coordinate(row, col);
    }

    public void MoveSacks() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (locationGrid[i][j].getTaken()) {
                    Coordinate newCoordinate = findNewTile(i, j);
                    locationGrid[newCoordinate.getRow()][newCoordinate.getCol()].setSack(locationGrid[i][j].getSack());
                    locationGrid[newCoordinate.getRow()][newCoordinate.getCol()].setTaken(true);
                    if (!(i == newCoordinate.getRow() && j == newCoordinate.getCol())) {
                        locationGrid[i][j].setSack(null);
                        locationGrid[i][j].setTaken(false);
                    }
                }
            }
        }
    }

    public void drawGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(locationGrid[i][j].getTaken() ? " T " : " F ");
            }
            System.out.println();
        }
    }
}
