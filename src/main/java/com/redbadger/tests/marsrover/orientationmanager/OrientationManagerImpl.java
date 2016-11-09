package com.redbadger.tests.marsrover.orientationmanager;

import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.enums.Instruction;


public class OrientationManagerImpl implements OrientationManager {
  @Override
  public void changeOrientation(Instruction instruction, GridPosition gridPosition) {
    if (instruction == null)
      throw new IllegalArgumentException("instruction cannot be null");

    if (gridPosition == null)
      throw new IllegalArgumentException("gridPosition cannot be null");

    if (gridPosition.getOrientation() == null)
      throw new IllegalArgumentException("gridPosition.orientation cannot be null");

    if (instruction == Instruction.R){
      this.changeOrientationClockwise(gridPosition);
    }
    else if (instruction == Instruction.L){
      changeOrientationAntiClockwise(gridPosition);
    }
  }

  private void changeOrientationClockwise(GridPosition gridPosition){
    switch (gridPosition.getOrientation()){
      case N:{
        gridPosition.setOrientation(Orientation.E);
        break;
      }
      case E:{
        gridPosition.setOrientation(Orientation.S);
        break;
      }

      case W:{
        gridPosition.setOrientation(Orientation.N);
        break;
      }
      case S:{
        gridPosition.setOrientation(Orientation.W);
        break;
      }
    }
  }

  private void changeOrientationAntiClockwise(GridPosition gridPosition){
    switch (gridPosition.getOrientation()){
      case N:{
        gridPosition.setOrientation(Orientation.W);
        break;
      }
      case E:{
        gridPosition.setOrientation(Orientation.N);
        break;
      }
      case W:{
        gridPosition.setOrientation(Orientation.S);
        break;
      }
      case S:{
        gridPosition.setOrientation(Orientation.E);
        break;
      }
    }
  }
}
