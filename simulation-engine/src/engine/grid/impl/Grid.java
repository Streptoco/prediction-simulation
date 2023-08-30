package engine.grid.impl;

import engine.entity.impl.EntityInstance;
import engine.grid.api.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public int rowUp(int currentRow, int depth) {
        int result;
        if (currentRow == 0) {
            return rows - depth;
        } else {
            result = currentRow - depth;
        }
        if (result < 0) {
            result = rows + result;
        }
        return result;
    }

    public int rowDown(int currentRow, int depth) {
        int result;
        if (currentRow == rows - 1) {
            return depth - 1;
        } else {
            result = currentRow + depth;
        }
        if (result >= rows) {
            result = result - rows;
        }
        return result;
    }

    public int colLeft(int currentCol, int depth) {
        int result;
        if (currentCol == 0) {
            return cols - depth;
        } else {
            result = currentCol - depth;
        }
        if (result < 0) {
            result = cols + result;
        }
        return result;
    }

    public int colRight(int currentCol, int depth) {
        int result;
        if (currentCol == cols - 1) {
            return depth - 1;
        } else {
            result = currentCol + depth;
        }
        if (result >= cols) {
            result = result - cols;
        }
        return result;
    }

    public int RowPlusPlus(int currentRow) {
        if (currentRow == rows - 1) {
            return 0;
        } else {
            return currentRow++;
        }
    }

    public int ColPlusPlus(int currentCol) {
        if (currentCol == cols - 1) {
            return 0;
        } else {
            return currentCol++;
        }
    }

    public int RowMinusMinus(int currentRow) {
        if (currentRow == 0) {
            return rows - 1;
        } else {
            return currentRow--;
        }
    }

    public int ColMinusMinus(int currentCol) {
        if (currentCol == 0) {
            return cols - 1;
        } else {
            return currentCol--;
        }
    }

    public void getAllInstancesAroundMe(Coordinate targetLocation,Coordinate currentLocation, int depth, Set<EntityInstance> entityInstances) {
        //Using set to avoid duplications
        //Check if the current tiles in the grid is taken and it is not the target tile that we started search from
        if (locationGrid[currentLocation.getRow()][currentLocation.getCol()].getTaken() && !(targetLocation.getRow() == currentLocation.getRow() && targetLocation.getCol() == currentLocation.getCol())) {
            entityInstances.add(locationGrid[currentLocation.getRow()][currentLocation.getCol()].getEntityInSack());
        }
        if (depth != 0) {
            // calculate the indexes of the rows around me
            int upperRow = RowMinusMinus(currentLocation.getRow());
            int leftCol = ColMinusMinus(currentLocation.getCol());
            int rightCol = ColPlusPlus(currentLocation.getCol());
            int downRow = RowPlusPlus(currentLocation.getRow());

            // checking the upper row
            Coordinate upperLeft = new Coordinate(upperRow, leftCol);
            getAllInstancesAroundMe(targetLocation, upperLeft, depth - 1, entityInstances);
            Coordinate upperMiddle = new Coordinate(upperRow, currentLocation.getCol());
            getAllInstancesAroundMe(targetLocation, upperMiddle, depth - 1, entityInstances);
            Coordinate upperRight = new Coordinate(upperRow, rightCol);
            getAllInstancesAroundMe(targetLocation, upperRight, depth - 1, entityInstances);

            // checking the middle row
            Coordinate middleLeft = new Coordinate(currentLocation.getRow(), leftCol);
            getAllInstancesAroundMe(targetLocation, middleLeft, depth - 1, entityInstances);
            Coordinate middleRight = new Coordinate(currentLocation.getRow(), leftCol);
            getAllInstancesAroundMe(targetLocation, middleRight, depth - 1, entityInstances);

            //checking the down row
            Coordinate downLeft = new Coordinate(downRow, leftCol);
            getAllInstancesAroundMe(targetLocation, downLeft, depth - 1, entityInstances);
            Coordinate downMiddle = new Coordinate(downRow, currentLocation.getCol());
            getAllInstancesAroundMe(targetLocation, downMiddle, depth - 1, entityInstances);
            Coordinate downRight = new Coordinate(downRow, rightCol);
            getAllInstancesAroundMe(targetLocation, downRight, depth - 1, entityInstances);



        }
    }


}
