package engine.grid.impl;

import engine.entity.impl.EntityInstance;
import engine.exception.XMLException;
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
        if(length < 10 || length > 100) {
            throw new XMLException("\nGrid dimensions are incorrect the length should be more then 10 and less then 100");
        }
        if(width < 10 || width > 100) {
            throw new XMLException("\nGrid dimensions are incorrect the width should be more then 10 and less then 100");
        }
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
            int finalRow = randomLength;
            int finalCol = randomWidth;
            locationGrid[randomLength][randomWidth].setSack(sack);
            sack.getEntity().setPosition(finalRow, finalCol);
            return true;
        } else {
            // TODO: message that it can't be done? exception?
            return false;
        }
    }

    public boolean addSackToGrid(Sack sack, Coordinate coordinate) {
        if(!locationGrid[coordinate.getRow()][coordinate.getCol()].getTaken()) {
            locationGrid[coordinate.getRow()][coordinate.getCol()].setSack(sack);
            locationGrid[coordinate.getRow()][coordinate.getCol()].setTaken(true);
            return true;
        } else {
            return false;
        }
    }

    public int getRows() {return this.rows;}

    public int getCols() {return this.cols;}

    public void addSackToGrid(EntityInstance entity, int row, int col) {
        Sack sack = new Sack(entity);
        Tile tile = new Tile();
        tile.setSack(sack);
        this.locationGrid[row][col] = tile;

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
            if(currentEntity.isAlive()) {
                Sack sack = new Sack(currentEntity);
                addSackToGrid(sack);
            }
        }
    }

    public Coordinate findNewTile(Coordinate currentLocation) {
        List<Coordinate> possibleMoves = new ArrayList<>();
        int upperRow = RowMinusMinus(currentLocation.getRow());
        int leftCol = ColMinusMinus(currentLocation.getCol());
        int rightCol = ColPlusPlus(currentLocation.getCol());
        int downRow = RowPlusPlus(currentLocation.getRow());

        //Upper Left
        if (!locationGrid[upperRow][leftCol].getTaken()) {
            possibleMoves.add(new Coordinate(upperRow, leftCol));
        }
        //Upper Middle
        if (!locationGrid[upperRow][currentLocation.getCol()].getTaken()) {
            possibleMoves.add(new Coordinate(upperRow, currentLocation.getCol()));
        }
        //Upper Right
        if (!locationGrid[upperRow][rightCol].getTaken()) {
            possibleMoves.add(new Coordinate(upperRow, rightCol));
        }
        //Middle left
        if (!locationGrid[currentLocation.getRow()][leftCol].getTaken()) {
            possibleMoves.add(new Coordinate(currentLocation.getRow(), leftCol));
        }
        //Middle Right
        if (!locationGrid[currentLocation.getRow()][rightCol].getTaken()) {
            possibleMoves.add(new Coordinate(currentLocation.getRow(), rightCol));
        }
        //Down left
        if (!locationGrid[downRow][leftCol].getTaken()) {
            possibleMoves.add(new Coordinate(downRow, leftCol));
        }
        //Down middle
        if (!locationGrid[downRow][currentLocation.getCol()].getTaken()) {
            possibleMoves.add(new Coordinate(downRow, currentLocation.getCol()));
        }
        //Down right
        if (!locationGrid[downRow][rightCol].getTaken()) {
            possibleMoves.add(new Coordinate(downRow, rightCol));
        }
        if (possibleMoves.isEmpty()) {
            return new Coordinate(currentLocation.getRow(), currentLocation.getCol());
        } else {
            do {
                Random random = new Random();
                int randomIndex = random.nextInt(possibleMoves.size());
                Tile currentTile = locationGrid[possibleMoves.get(randomIndex).getRow()][possibleMoves.get(randomIndex).getCol()];
                if (!currentTile.getTaken()) {
                    return possibleMoves.get(randomIndex);
                } else {
                    possibleMoves.remove(randomIndex);
                }
            } while (!possibleMoves.isEmpty());
        }
        return new Coordinate(currentLocation.getRow(), currentLocation.getCol());
    }

    public void MoveSacks() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (locationGrid[i][j].getTaken() && !locationGrid[i][j].getSack().isMoved()) {
                    Coordinate newCoordinate = findNewTile(new Coordinate(i, j));
                    locationGrid[newCoordinate.getRow()][newCoordinate.getCol()].setSack(locationGrid[i][j].getSack());
                    locationGrid[newCoordinate.getRow()][newCoordinate.getCol()].setTaken(true);
                    locationGrid[newCoordinate.getRow()][newCoordinate.getCol()].getSack().getEntity().setPosition(newCoordinate);
                    locationGrid[newCoordinate.getRow()][newCoordinate.getCol()].getSack().setMoved(true);
                    if (!(i == newCoordinate.getRow() && j == newCoordinate.getCol())) {
                        locationGrid[i][j].setSack(null);
                        locationGrid[i][j].setTaken(false);
                    }
                }
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (locationGrid[i][j].getSack() != null) {
                    locationGrid[i][j].getSack().setMoved(false);
                }
            }
        }
    }
    public void removeFromGrid(Coordinate coordinate) {
        locationGrid[coordinate.getRow()][coordinate.getCol()].setSack(null);
        locationGrid[coordinate.getRow()][coordinate.getCol()].setTaken(false);
    }

    public void drawGrid() {
        System.out.println("==================================================================");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j == 0) {
                    System.out.print(i + ".\t");
                }
                if (locationGrid[i][j].getTaken()) {
                    System.out.print(locationGrid[i][j].getSack().getEntity().getId() + "" + locationGrid[i][j].getSack().getEntity().getEntityName().charAt(0) + "   ");
                } else {
                    System.out.print("--   ");
                }
            }
            System.out.println();
        }
        System.out.println("==================================================================");
    }

    public int RowPlusPlus(int currentRow) {
        if (currentRow == rows - 1) {
            return 0;
        } else {
            return ++currentRow;
        }
    }

    public int ColPlusPlus(int currentCol) {
        if (currentCol == cols - 1) {
            return 0;
        } else {
            return ++currentCol;
        }
    }

    public int RowMinusMinus(int currentRow) {
        if (currentRow == 0) {
            return rows - 1;
        } else {
            return --currentRow;
        }
    }

    public int ColMinusMinus(int currentCol) {
        if (currentCol == 0) {
            return cols - 1;
        } else {
            return --currentCol;
        }
    }

    public void getAllInstancesAroundMe(Coordinate targetLocation, Coordinate currentLocation, int depth, Set<EntityInstance> entityInstances) {
        //TODO: change depth to expression and in case it is a string or boolean throw exception
        //Using set to avoid duplications
        //Check if the current tiles in the grid is taken and it is not the target tile that we started search from
        if (locationGrid[currentLocation.getRow()][currentLocation.getCol()].getTaken()) {
            if (targetLocation.getRow() != currentLocation.getRow() || targetLocation.getCol() != currentLocation.getCol()) {
                entityInstances.add(locationGrid[currentLocation.getRow()][currentLocation.getCol()].getEntityInSack());
            }
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
            Coordinate middleRight = new Coordinate(currentLocation.getRow(), rightCol);
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
