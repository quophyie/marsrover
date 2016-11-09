package com.redbadger.tests.marsrover.controlconsole;

import com.redbadger.tests.marsrover.commandmodule.CommandModule;
import com.redbadger.tests.marsrover.commandmodule.CommandModuleImpl;
import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilder;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilderImpl;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.gridbuilder.Point;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManager;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManagerImpl;
import com.redbadger.tests.marsrover.pathfinder.InstructionExecutionResult;
import com.redbadger.tests.marsrover.pathfinder.PathFinder;
import com.redbadger.tests.marsrover.pathfinder.PathFinderImpl;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngine;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngineImpl;
import com.redbadger.tests.marsrover.rover.Rover;
import com.redbadger.tests.marsrover.rover.RoverImpl;

import java.io.Console;
import java.io.IOException;
import java.util.*;


public class ControlConsole {


  public static void main(String[] args) throws IOException {


    GridBuilder gridBuilder = new GridBuilderImpl();
    OrientationManager orientationManager = new OrientationManagerImpl();
    PropulsionEngine propulsionEngine = new PropulsionEngineImpl();
    CommandModule commandModule = new CommandModuleImpl();

    System.out.println(String.format("Welcome to the Mars Rover Robot control console"));
    System.out.println(String.format("Please enter your instructions.\n" +
        "The 1st line should be the Top right upper most X and Y coordinates of the Mars grid e.g. 5 3"));

    System.out.print("\nGrid Top Right Uppermost Coordinates: ");
    Console console = System.console();
    String topRightCord = console.readLine();

    Point gridTopRightPoint = commandModule.processGridDimensionsInstructions(topRightCord);
    PathFinder pathFinder = new PathFinderImpl(gridBuilder, orientationManager, propulsionEngine, gridTopRightPoint.getX(), gridTopRightPoint.getY(), null);

    StringBuilder stringBuilder = new StringBuilder();

    while (true){


      try
      {
        if (console != null)
        {
          System.out.print("Robot start position:");
          String robotStartPos = console.readLine();

          System.out.print("Navigation Instructions: ");
          String robotNavigationInstructions = console.readLine();

          GridPosition startingPosition = commandModule.processStartingGridPositionInstructions(robotStartPos);
          Rover rover = new RoverImpl(startingPosition);

          List<Instruction> robotNavigationInstructionsList = commandModule.processNavigationInstructions(robotNavigationInstructions);

          InstructionExecutionResult instructionExecutionResult = rover.explore(pathFinder, robotNavigationInstructionsList);
          String report  = rover.generateExplorationReport(Arrays.asList(instructionExecutionResult));

          stringBuilder.append(report+"\n");

          System.out.print("Press Enter to continue or type ':exec:' to dispatch robots: ");
          String terminationCmd =  console.readLine();

          if (":exec:".equalsIgnoreCase(terminationCmd)){
             System.out.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            pathFinder.clearScents();
          }

        }
      } catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }

  }

}
