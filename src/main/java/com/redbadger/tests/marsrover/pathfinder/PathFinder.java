package com.redbadger.tests.marsrover.pathfinder;

import com.redbadger.tests.marsrover.gridbuilder.GridPosition;

/**
 * Created by dman on 07/11/2016.
 */
public interface PathFinder {
  /**
   * Moves to new position based on the given instruction
   * @param instructionDetail
   * @return
   */
  InstructionExecutionResult advance(InstructionDetail instructionDetail);

  String[][] getGrid();
  void setGrid(String[][] grid);

  GridPosition getInitialGridPosition();

  void setInitialGridPosition(GridPosition initialGridPosition);

  void clearScents();

}
