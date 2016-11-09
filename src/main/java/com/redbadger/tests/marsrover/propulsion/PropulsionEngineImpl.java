package com.redbadger.tests.marsrover.propulsion;


import com.redbadger.tests.marsrover.gridbuilder.GridPosition;

public class PropulsionEngineImpl implements  PropulsionEngine{

  /**
   * Moves the rover one step in its current orientation
   * @param gridPosition
   * @return
   */
  @Override
  public GridPosition applyPropulsion(GridPosition gridPosition) {

    if (gridPosition == null)
      throw new IllegalArgumentException("gridPosition cannot be null");

    if (gridPosition.getOrientation() == null)
      throw new IllegalArgumentException("gridPosition.orientation cannot be null");

    switch(gridPosition.getOrientation()){

      case N: {
        return new GridPosition(gridPosition.getX(), gridPosition.getY() + 1, gridPosition.getOrientation());
      }
      case E: {
        return new GridPosition(gridPosition.getX() + 1, gridPosition.getY(), gridPosition.getOrientation());
      }

      case S: {
        return new GridPosition(gridPosition.getX(), gridPosition.getY() - 1, gridPosition.getOrientation());
      }

      case W: {
        return new GridPosition(gridPosition.getX() - 1 , gridPosition.getY() , gridPosition.getOrientation());
      }
    }
    return gridPosition;
  }
}
