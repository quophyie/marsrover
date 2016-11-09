package com.redbadger.tests.marsrover.pathfinder;

import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;


public class InstructionDetail {

  private Instruction instruction;
  private GridPosition gridPosition;
  public InstructionDetail() {}

  public InstructionDetail(Instruction instruction, GridPosition gridPosition) {
    this.instruction = instruction;
    this.gridPosition = gridPosition;
  }

  public Instruction getInstruction() {
    return instruction;
  }

  public void setInstruction(Instruction instruction) {
    this.instruction = instruction;
  }

  public GridPosition getGridPosition() {
    return gridPosition;
  }

  public void setGridPosition(GridPosition gridPosition) {
    this.gridPosition = gridPosition;
  }


  @Override
  public boolean equals(Object obj){
    if (obj!=null &&
        this.instruction.equals(((InstructionDetail)obj).getInstruction())
        && this.gridPosition.equals(((InstructionDetail)obj).getGridPosition()))
      return true;

    return false;
  }

  @Override
  public int hashCode(){
    return (instruction.toString() + String.valueOf(gridPosition.hashCode())).hashCode();
  }
}
