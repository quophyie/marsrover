package com.test.redbadger.tests.marsrover.commandmodule;

import com.redbadger.tests.marsrover.commandmodule.CommandModule;
import com.redbadger.tests.marsrover.commandmodule.CommandModuleImpl;
import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.exception.CommandModuleException;
import com.redbadger.tests.marsrover.exception.InvalidInstructionException;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilder;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.gridbuilder.Point;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManager;
import com.redbadger.tests.marsrover.pathfinder.PathFinder;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommanModuleTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private CommandModule commandModule;

  private final int MAX_NUM_OF_NAVIGATION_INSTRUCTIONS = 99;

  @Before
  public void setUp(){
    commandModule = new CommandModuleImpl();
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenNullGridDimensionInstruction(){
    String message = "Grid uppermost top right point instruction cannot be null or empty";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions(null);
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenEmptyGridDimensionInstruction(){
    String message = "Grid uppermost top right point instruction cannot be null or empty";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions("  ");
  }



  @Test
  public void shouldThrowInvalidInstructionExceptionGivenGridDimensionInstructionWithOnlyOneCoordinate(){
    String message = "Grid uppermost top right point requires two coordinates i.e. X and Y coordinates";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions("5");
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenGridDimensionInstructionWhereXCoordinateIsANumber(){
    String dimensionInstruction = "5 3A";
    String message = String.format("Invalid coordinate instruction provided. Coordinates must be numbers only Instruction: %s", dimensionInstruction);
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions(dimensionInstruction);
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenGridDimensionInstructionWhereYCoordinateIsANumber(){
    String dimensionInstr = "5A 3";
    String message = String.format("Invalid coordinate instruction provided. Coordinates must be numbers only Instruction: %s", dimensionInstr);
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions(dimensionInstr);
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenGridDimensionInstructionWhereBothXAndYCoordinateAreNotNumber(){
    String dimensionInstruction = "5A 3B";
    String message = String.format("Invalid coordinate instruction provided. Coordinates must be numbers only Instruction: %s", dimensionInstruction);
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions(dimensionInstruction);
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenGridDimensionInstructionWhereXOrYCoordinateGreaterThan50(){
    String dimensionInstruction = "51 3";
    String message = String.format("xCoordinate or yCoordinate cannot be greater than %d", 50);
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processGridDimensionsInstructions(dimensionInstruction);
  }

  @Test
  public void shouleReturnPointGivenGridDimensionInstructionWhereBothXAndYCoordinateAreNumbers(){
    String dimensionInstruction = "25 3";
    Point result = commandModule.processGridDimensionsInstructions(dimensionInstruction);
    assertEquals(25, result.getX());
    assertEquals(3, result.getY());
  }


  @Test
  public void shouldThrowInvalidInstructionExceptionGivenNullStartingGridPositionInstruction(){
    String message = "startingPosition instruction cannot be null or empty";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processStartingGridPositionInstructions(null);
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenEmptyStartingGridPositionInstruction(){
    String message = "startingPosition instruction cannot be null or empty";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processStartingGridPositionInstructions("  ");
  }



  @Test
  public void shouldThrowInvalidInstructionExceptionGivenStartingGridPositionWhichDoesNotHaveXCoordYCoordOrOientation(){
    String message = "startingPosition instruction must have coordinates and orientation coordinates i.e. X and Y coordinates and orientation";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processStartingGridPositionInstructions("5");
  }

  @Test
  public void shouldStartingGridPositionGivenXCoordYCoordOrOientationInstruction(){
    GridPosition result = commandModule.processStartingGridPositionInstructions("5 3 N");
    assertEquals(5, result.getX());
    assertEquals(3, result.getY());
    assertEquals(Orientation.N, result.getOrientation());
  }


  @Test
  public void shouldThrowInvalidInstructionExceptionGivenNullNavigationInstructions(){
    String message = "navigationInstructions instruction cannot be null or empty";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processNavigationInstructions(null);
  }

  @Test
  public void shouldThrowInvalidInstructionExceptionGivenEmptyNavigationInstructions(){
    String message = "navigationInstructions instruction cannot be null or empty";
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processNavigationInstructions("  ");
  }

  @Test
  public void shouldReturnListOfNavigationInstructionsGivenEmptyNavigationInstructions(){
    List<Instruction> result = commandModule.processNavigationInstructions("RFRFRFRF");
    assertEquals(8,result.size());
  }

  @Test
  public void shouldThrowCommandModuleExceptionExceptionGivenNavigationInstructionsWithLengthGreaterThanMAX_NUM_OF_NAVIGATION_INSTRUCTIONS(){
    String message = String.format("navigationInstructions length must be less than or equal to %d", MAX_NUM_OF_NAVIGATION_INSTRUCTIONS);
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i <= MAX_NUM_OF_NAVIGATION_INSTRUCTIONS; i++)
      stringBuilder.append("RFRFRFRFRR");
    thrown.expectMessage(message);
    thrown.expect(InvalidInstructionException.class);
    commandModule.processNavigationInstructions(stringBuilder.toString());
  }

  @Test
  public void shouldNotThrowCommandModuleExceptionExceptionGivenNavigationInstructionsWithLengthEqualToMAX_NUM_OF_NAVIGATION_INSTRUCTIONS(){
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < 10; i++)
      stringBuilder.append("RFRFRFRFF");
    stringBuilder.append("RFRFRFRFF");

    List<Instruction> instructionList = commandModule.processNavigationInstructions(stringBuilder.toString());
    assertEquals(MAX_NUM_OF_NAVIGATION_INSTRUCTIONS, instructionList.size());
  }
}
