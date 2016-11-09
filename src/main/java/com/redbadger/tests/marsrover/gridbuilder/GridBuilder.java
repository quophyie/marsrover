package com.redbadger.tests.marsrover.gridbuilder;

/**
 * Created by dman on 07/11/2016.
 */
public interface GridBuilder {
  /**
   * The xCoord and yCoord are the uppermost top right and left of the grid
   * The bottom left and bottom right coordinates are assumed to 0, 0
   * @param xCoord
   * @param yCoord
   * @return
   */
  String [][] buildGrid(int xCoord, int yCoord);
  boolean isValidGridPosition(String[][] grid, int xCoord, int yCoord);
}
