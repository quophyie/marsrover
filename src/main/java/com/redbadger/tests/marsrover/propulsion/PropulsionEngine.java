package com.redbadger.tests.marsrover.propulsion;


import com.redbadger.tests.marsrover.gridbuilder.GridPosition;

public interface PropulsionEngine {
  /**
   * Applys propulsion to the mars rover
   * @param gridPosition
   * @return
   */
  GridPosition applyPropulsion(GridPosition gridPosition);
}
