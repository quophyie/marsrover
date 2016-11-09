package com.test.redbadger.tests.marsrover.rover;

import com.redbadger.tests.marsrover.commandmodule.CommandModule;
import com.redbadger.tests.marsrover.commandmodule.CommandModuleImpl;
import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.enums.Orientation;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoverTests {

  private Rover rover;
  private CommandModule commandModule;
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private PathFinder pathFinder;
  private GridBuilder gridBuilder;
  private OrientationManager orientationManager;
  private PropulsionEngine propulsionEngine;

  private String gridUpperTopRightCoordStr;


  @Before
  public void setUp(){

    gridUpperTopRightCoordStr = "5 3";

    gridBuilder = new GridBuilderImpl();
    orientationManager = new OrientationManagerImpl();
    propulsionEngine = new PropulsionEngineImpl();
    commandModule = new CommandModuleImpl();

    Point gridTopRightPoint = commandModule.processGridDimensionsInstructions(gridUpperTopRightCoordStr);

    pathFinder = new PathFinderImpl(gridBuilder, orientationManager, propulsionEngine, gridTopRightPoint.getX(), gridTopRightPoint.getY(), null);

    rover = new RoverImpl();
  }



  @Test
  public void shouldReturnSuccessGivenInstructionsWhereSingleRoverSuccessfullyNavigatesGridWithoutGettingLots() {

    GridPosition initPos = commandModule.processStartingGridPositionInstructions("1 1 E");
    String navigationInstructionsStr  = "RFRFRFRF";
    List<Instruction> roverNavigationInstructions = commandModule.processNavigationInstructions(navigationInstructionsStr);

    pathFinder.setInitialGridPosition(initPos);
    InstructionExecutionResult result = rover.explore(pathFinder,roverNavigationInstructions);
    assertEquals(initPos, result.getGridPosition());

    //1 1 E
    assertEquals(1, result.getGridPosition().getX());
    assertEquals(1, result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("E"), result.getGridPosition().getOrientation());
    assertFalse(result.isLost());
  }

  @Test
  public void shouldReturnGridPositionIndicatingALossAfterExploration() {
    GridPosition initPos = commandModule.processStartingGridPositionInstructions("3 2 N");

    List<Instruction> roverNavigationInstructions = commandModule.processNavigationInstructions("FRRFLLFFRRFLL");

    pathFinder.setInitialGridPosition(initPos);
    InstructionExecutionResult result = rover.explore(pathFinder,roverNavigationInstructions);


    //3 3 N LOST
    assertEquals(3, result.getGridPosition().getX());
    assertEquals(3, result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("N"), result.getGridPosition().getOrientation());
    assertTrue("LOST".equals(result.getDescription()));
  }

  @Test
  public void shouldReturnGridPositionIndicatingASuccessGivenTwoRoversWhereRover1GetsLostAndRover2ndAvoidsRover1FatalInstruction() {

    GridPosition rover1InitialPos  = commandModule.processStartingGridPositionInstructions("3 2 N");

    List<Instruction> rover1NavigationInstructions = commandModule.processNavigationInstructions("FRRFLLFFRRFLL");

    GridPosition rover2InitialPos = commandModule.processStartingGridPositionInstructions("0 3 W");

    List<Instruction> rover2NavigationInstructions = commandModule.processNavigationInstructions("LLFFFLFLFL");

    Rover rover1 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover1InitialPos);
    InstructionExecutionResult rover1Result = rover1.explore(pathFinder,rover1NavigationInstructions);


    Rover rover2 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover2InitialPos);
    InstructionExecutionResult rover2Result = rover2.explore(pathFinder,rover2NavigationInstructions);

    //3 3 N LOST
    assertEquals(3, rover1Result.getGridPosition().getX());
    assertEquals(3, rover1Result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("N"), rover1Result.getGridPosition().getOrientation());
    assertTrue("LOST".equals(rover1Result.getDescription()));

    //2 3 S
    assertEquals(2, rover2Result.getGridPosition().getX());
    assertEquals(3, rover2Result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("S"), rover2Result.getGridPosition().getOrientation());
  }

  @Test
  public void shouldReturnGridPositionIndicatingASuccessGivenThreeRoversWhereRover1FinishesSuccessfullyRover2GetsLostAndTheRover3AvoidsRover2FatalInstrcution() {

    GridPosition rover0InitialPos  = commandModule.processStartingGridPositionInstructions("1 1 E");

    List<Instruction> rover0NavigationInstructions = commandModule.processNavigationInstructions("RFRFRFRF");

    GridPosition rover1InitialPos  = commandModule.processStartingGridPositionInstructions("3 2 N");

    List<Instruction> rover1NavigationInstructions = commandModule.processNavigationInstructions("FRRFLLFFRRFLL");

    GridPosition rover2InitialPos = commandModule.processStartingGridPositionInstructions("0 3 W");

    List<Instruction> rover2NavigationInstructions = commandModule.processNavigationInstructions("LLFFFLFLFL");

    Rover rover0 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover0InitialPos);
    InstructionExecutionResult rover0Result = rover0.explore(pathFinder,rover0NavigationInstructions);

    Rover rover1 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover1InitialPos);
    InstructionExecutionResult rover1Result = rover1.explore(pathFinder,rover1NavigationInstructions);


    Rover rover2 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover2InitialPos);
    InstructionExecutionResult rover2Result = rover2.explore(pathFinder,rover2NavigationInstructions);

    //1 1 E
    assertEquals(1, rover0Result.getGridPosition().getX());
    assertEquals(1, rover0Result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("E"), rover0Result.getGridPosition().getOrientation());
    assertFalse(rover0Result.isLost());

    //3 3 N LOST
    assertEquals(3, rover1Result.getGridPosition().getX());
    assertEquals(3, rover1Result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("N"), rover1Result.getGridPosition().getOrientation());
    assertTrue("LOST".equals(rover1Result.getDescription()));

    //2 3 S
    assertEquals(2, rover2Result.getGridPosition().getX());
    assertEquals(3, rover2Result.getGridPosition().getY());
    assertEquals(Orientation.valueOf("S"), rover2Result.getGridPosition().getOrientation());
  }

  @Test
  public void shouldReturnGenerateExplorationReportForSingleRoverExplorations() {

    GridPosition rover0InitialPos  = commandModule.processStartingGridPositionInstructions("1 1 E");

    List<Instruction> rover0NavigationInstructions = commandModule.processNavigationInstructions("RFRFRFRF");


    Rover rover0 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover0InitialPos);
    InstructionExecutionResult rover0Result = rover0.explore(pathFinder,rover0NavigationInstructions);

    String result = rover0.generateExplorationReport(Arrays.asList(rover0Result));
    String expectedReport = "1 1 E";
    assertEquals(expectedReport, result);
  }

  @Test
  public void shouldReturnGenerateExplorationReportForThreeRoversExplorations() {

    GridPosition rover0InitialPos  = commandModule.processStartingGridPositionInstructions("1 1 E");

    List<Instruction> rover0NavigationInstructions = commandModule.processNavigationInstructions("RFRFRFRF");

    GridPosition rover1InitialPos  = commandModule.processStartingGridPositionInstructions("3 2 N");

    List<Instruction> rover1NavigationInstructions = commandModule.processNavigationInstructions("FRRFLLFFRRFLL");

    GridPosition rover2InitialPos = commandModule.processStartingGridPositionInstructions("0 3 W");

    List<Instruction> rover2NavigationInstructions = commandModule.processNavigationInstructions("LLFFFLFLFL");

    Rover rover0 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover0InitialPos);
    InstructionExecutionResult rover0Result = rover0.explore(pathFinder,rover0NavigationInstructions);

    Rover rover1 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover1InitialPos);
    InstructionExecutionResult rover1Result = rover1.explore(pathFinder,rover1NavigationInstructions);


    Rover rover2 = new RoverImpl();
    pathFinder.setInitialGridPosition(rover2InitialPos);
    InstructionExecutionResult rover2Result = rover2.explore(pathFinder,rover2NavigationInstructions);

    String result = rover0.generateExplorationReport(Arrays.asList(rover0Result, rover1Result, rover2Result));
    String expectedReport =
        "1 1 E\n"+
        "3 3 N LOST\n"
        +"2 3 S";

    assertEquals(expectedReport, result);
  }

  @Test
  public void shouldReturnAnEmptyExplorationReportGivenAnEmptyListOfInstructionExecutionResult(){
    Rover rover0 = new RoverImpl();
    String result = rover0.generateExplorationReport(new ArrayList<>());
    String expectedReport = "";

    assertEquals(expectedReport, result);
  }

  @Test
  public void shouldReturnAnEmptyExplorationReportGivenANullListOfInstructionExecutionResult(){
    Rover rover0 = new RoverImpl();
    String result = rover0.generateExplorationReport(null);
    String expectedReport = "";
    assertEquals(expectedReport, result);
  }


}
