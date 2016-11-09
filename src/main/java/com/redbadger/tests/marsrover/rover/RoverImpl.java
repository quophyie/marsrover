package com.redbadger.tests.marsrover.rover;

import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.pathfinder.InstructionDetail;
import com.redbadger.tests.marsrover.pathfinder.InstructionExecutionResult;
import com.redbadger.tests.marsrover.pathfinder.PathFinder;

import java.util.List;
import java.util.stream.Collectors;


public class RoverImpl implements Rover {

  private GridPosition initialPosition;
  public RoverImpl(){
  }

  public RoverImpl(GridPosition initialPosition){
    this.initialPosition = initialPosition;
  }


  @Override
  //By Recursion
  public InstructionExecutionResult explore(PathFinder pathFinder, List<Instruction> instructions) {

    GridPosition startPosition;

    if (pathFinder.getInitialGridPosition() != null) {
      startPosition = pathFinder.getInitialGridPosition();
    } else {
      startPosition = this.getInitialPosition();
    }

    GridPosition position = new GridPosition(startPosition.getX(), startPosition.getY(),startPosition.getOrientation());

    InstructionExecutionResult explorationResult = new InstructionExecutionResult(position, false, "");
    explorationResult = doExploration(pathFinder, instructions, explorationResult);

    return explorationResult;
  }

  @Override
  public String generateExplorationReport(List<InstructionExecutionResult> instructionExecutionResults) {
    if (instructionExecutionResults == null || instructionExecutionResults.isEmpty())
      return "";

    return instructionExecutionResults
          .stream()
          .map(instRes -> String.format("%d %d %s %s",
              instRes.getGridPosition().getX(),
              instRes.getGridPosition().getY(),
              instRes.getGridPosition().getOrientation().toString(),
              instRes.getDescription()).trim())
        .collect(Collectors.joining("\n"))
        .trim();
  }

  public GridPosition getInitialPosition() {
    return initialPosition;
  }

  public void setInitialPosition(GridPosition initialPosition) {
    this.initialPosition = initialPosition;
  }
/*@Override
  //By Iteration
  public InstructionExecutionResult explore(PathFinder pathFinder, List<Instruction> instructions) {

    InstructionExecutionResult explorationResult = null;
    GridPosition initialPosition = pathFinder.getInitialGridPosition();
    GridPosition position = new GridPosition(initialPosition.getX(), initialPosition.getY(),initialPosition.getOrientation());

    for (Instruction instruction : instructions) {
      InstructionDetail instrutctionDetail = new InstructionDetail(instruction,position);
      explorationResult = pathFinder.advance(instrutctionDetail);
      position = explorationResult.getGridPosition();

      if (explorationResult.isLost())
        break;
    }

    return explorationResult;
  } */


  private InstructionExecutionResult doExploration(PathFinder pathFinder, List<Instruction> instructions, InstructionExecutionResult explorationResult){

    if (instructions == null || instructions.isEmpty())
      return explorationResult;

    Instruction instruction = instructions.get(0);
    InstructionDetail instructionDetail = new InstructionDetail(instruction,explorationResult.getGridPosition());
    explorationResult = pathFinder.advance(instructionDetail);

    if (explorationResult.isLost()) {
      return explorationResult;
    }
    else {
      int instructionIdx = instructions.indexOf(instruction);
      return doExploration(pathFinder, instructions.subList(instructionIdx + 1, instructions.size()), explorationResult);
    }
  }
}
