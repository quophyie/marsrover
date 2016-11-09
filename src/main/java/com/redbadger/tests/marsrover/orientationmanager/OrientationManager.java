package com.redbadger.tests.marsrover.orientationmanager;

import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.enums.Instruction;

/**
 * Created by dman on 07/11/2016.
 */
public interface OrientationManager {
  /**
   * Changes the orientation of the grid position based on the given instruction
   * @param instruction
   * @param gridPosition
   * @return
   */
  void changeOrientation(Instruction instruction, GridPosition gridPosition);
}
