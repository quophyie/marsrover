package com.redbadger.tests.marsrover.rover;


import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.pathfinder.InstructionExecutionResult;
import com.redbadger.tests.marsrover.pathfinder.PathFinder;

import java.util.List;

public interface Rover {
  InstructionExecutionResult explore(PathFinder pathFinder, List<Instruction> instructions);
  String generateExplorationReport(List<InstructionExecutionResult> instructionExecutionResults);
}
