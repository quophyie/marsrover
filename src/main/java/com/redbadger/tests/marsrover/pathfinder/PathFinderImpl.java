package com.redbadger.tests.marsrover.pathfinder;

import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.exception.GridPositionException;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilder;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManager;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngine;

import java.util.HashSet;
import java.util.Set;

public class PathFinderImpl implements PathFinder {

  private String [][] grid;
  private OrientationManager orientationManager;
  private PropulsionEngine propulsionEngine;
  private GridBuilder gridBuilder;
  private Set<InstructionDetail> fatalInstructions;
  private GridPosition initialGridPosition;

  public PathFinderImpl( GridBuilder gridBuilder,
                         OrientationManager orientationManager,
                         PropulsionEngine propulsionEngine,
                         int xCoord, int yCoord,
                         GridPosition initialGridPosition){
    if (gridBuilder == null )
      throw new IllegalArgumentException("gridBuilder cannot be null");

    if(orientationManager == null)
      throw new IllegalArgumentException("orientationManager cannot be null");

    if(propulsionEngine == null)
      throw new IllegalArgumentException("propulsionEngine cannot be null");



    this.propulsionEngine = propulsionEngine;
    this.orientationManager = orientationManager;
    this.grid = gridBuilder.buildGrid(xCoord, yCoord);
    this.gridBuilder = gridBuilder;
    this.fatalInstructions = new HashSet<>();
    this.initialGridPosition = initialGridPosition;

    if(initialGridPosition !=null && !gridBuilder.isValidGridPosition(this.grid, initialGridPosition.getX(), initialGridPosition.getY()))
      throw new GridPositionException("initialGridPosition is not valid and is out of bounds on grid");


  }

  @Override
  public InstructionExecutionResult advance(InstructionDetail instructionDetail) {

    if (instructionDetail == null)
      throw new IllegalArgumentException("instructionDetail cannot be null");

    if (instructionDetail.getInstruction() == null)
      throw new IllegalArgumentException("instructionDetail.instruction cannot be null");

    if (instructionDetail.getGridPosition() == null)
      throw new IllegalArgumentException("instructionDetail.gridPosition cannot be null");

    if (fatalInstructions.contains(instructionDetail))
      return new InstructionExecutionResult(instructionDetail.getGridPosition());

    if (instructionDetail.getInstruction() == Instruction.F){

       GridPosition newGridPosition =  propulsionEngine.applyPropulsion(instructionDetail.getGridPosition());

        if (gridBuilder.isValidGridPosition(grid, newGridPosition.getX(), newGridPosition.getY())) {
            return new InstructionExecutionResult(newGridPosition);
          } else {
            InstructionExecutionResult fatalInstructionExecutionResult =  new InstructionExecutionResult(instructionDetail.getGridPosition(), true, "LOST");
            fatalInstructions.add(instructionDetail);
            return fatalInstructionExecutionResult;
          }
      }
      else {
        orientationManager.changeOrientation(instructionDetail.getInstruction(), instructionDetail.getGridPosition());
       return new InstructionExecutionResult(instructionDetail.getGridPosition());
      }
  }

  @Override
  public String[][] getGrid() {
    return this.grid;
  }

  @Override
  public void setGrid(String[][] grid) {
    this.grid = grid;
  }

  public GridPosition getInitialGridPosition() {
    return initialGridPosition;
  }

  public void setInitialGridPosition(GridPosition initialGridPosition) {
    this.initialGridPosition = initialGridPosition;
  }

  @Override
  public void clearScents() {
    this.fatalInstructions.clear();
  }
}
