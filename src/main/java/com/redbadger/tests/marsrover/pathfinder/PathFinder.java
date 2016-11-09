package com.redbadger.tests.marsrover.pathfinder;

import com.redbadger.tests.marsrover.gridbuilder.GridPosition;


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
