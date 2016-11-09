package com.redbadger.tests.marsrover.pathfinder;

import com.redbadger.tests.marsrover.gridbuilder.GridPosition;

import java.util.Objects;

public class InstructionExecutionResult {

  private boolean lost;
  private GridPosition gridPosition;
  private String description;

  public InstructionExecutionResult(GridPosition gridPosition, boolean lost, String description){
    this.gridPosition = gridPosition;
    this.lost = lost;

    if(description != null) {
      this.description = description;
    }
    else {
      this.description = "";
    }
  }

  public InstructionExecutionResult(GridPosition gridPosition){

    this.gridPosition = gridPosition;
    this.description = "";
  }

  public boolean isLost() {
    return lost;
  }

  public void setLost(boolean lost) {
    this.lost = lost;
  }

  public GridPosition getGridPosition() {
    return gridPosition;
  }

  public void setGridPosition(GridPosition gridPosition) {
    this.gridPosition = gridPosition;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object obj){
    if (obj!=null &&
        this.isLost() == ((InstructionExecutionResult)obj).isLost()
        && this.description.equals(((InstructionExecutionResult)obj).getDescription())
        && this.getGridPosition().equals(((InstructionExecutionResult)obj).getGridPosition()))
      return true;

    return false;
  }

  @Override
  public int hashCode(){
     return (String.valueOf(this.description) + String.valueOf(lost) + String.valueOf(gridPosition.hashCode())).hashCode();
  }
}
