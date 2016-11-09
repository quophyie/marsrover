package com.redbadger.tests.marsrover.gridbuilder;


import com.redbadger.tests.marsrover.exception.GridException;

/**
 * Created by dman on 07/11/2016.
 */
public class GridBuilderImpl implements GridBuilder {

  @Override
  public String[][] buildGrid(int xCoord, int yCoord) {
    if (xCoord == 0 || yCoord == 0)
     throw new GridException(String.format("xCoordinate and yCoordinate must be greater than 0. xCoordinate: %d, yCoordinate: %d ", xCoord, yCoord));

    String [][] grid  = new String [xCoord][yCoord];
    for (int col = 0; col < xCoord; col++) {
      for (int row = 0; row < yCoord; row++) {
        grid[col][row] =  "X";
      }
    }
    return grid;
  }

  @Override
  public boolean isValidGridPosition(String[][] grid, int xCoord, int yCoord) {
    if (xCoord >= 0 && xCoord <= grid.length
        && yCoord >= 0 && yCoord <= grid[0].length)
      return true;
    return false;
  }
}
