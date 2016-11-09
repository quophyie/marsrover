package com.test.redbadger.tests.marsrover.pathfinder;

import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.exception.GridPositionException;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilder;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilderImpl;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManager;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManagerImpl;
import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.pathfinder.InstructionDetail;
import com.redbadger.tests.marsrover.pathfinder.InstructionExecutionResult;
import com.redbadger.tests.marsrover.pathfinder.PathFinder;
import com.redbadger.tests.marsrover.pathfinder.PathFinderImpl;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngine;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngineImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PathFinderTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private PathFinder pathFinder;
  private GridPosition gridPosition;
  private GridBuilder gridBuilder;
  private OrientationManager orientationManager;
  private PropulsionEngine propulsionEngine;
  private int xCoord = 5, yCoord = 3;

  @Before
  public void setUp(){
    gridBuilder = new GridBuilderImpl();
    orientationManager = new OrientationManagerImpl();
    propulsionEngine = new PropulsionEngineImpl();

    int initialGridPosXCoord = 1, initialGridPosYCoord = 1;
    gridPosition = new GridPosition(initialGridPosXCoord, initialGridPosYCoord, Orientation.N);
    pathFinder = new PathFinderImpl(gridBuilder, orientationManager, propulsionEngine, xCoord, yCoord, gridPosition);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGridBuilder(){
    String message = "gridBuilder cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    new PathFinderImpl(null, orientationManager, propulsionEngine, xCoord, yCoord, gridPosition);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullOrientationManager(){
    String message = "orientationManager cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    new PathFinderImpl(gridBuilder, null, propulsionEngine, xCoord, yCoord, null);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullPropulsionEngine(){
    String message = "propulsionEngine cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    new PathFinderImpl(gridBuilder, orientationManager, null, xCoord, yCoord, null);
  }

  @Test
  public void shouldThrowGridPositionExceptionGivenInitialGridPositionWhichIsOutOfBoundsOnGrid(){
    String message = "initialGridPosition is not valid and is out of bounds on grid";
    thrown.expectMessage(message);
    thrown.expect(GridPositionException.class);
    gridPosition.setY(1000);
    gridPosition.setX(1000);
    new PathFinderImpl(gridBuilder, orientationManager, propulsionEngine, xCoord, yCoord, gridPosition);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullCurrentGridPositionOnAdvance(){
    String message = "instructionDetail.gridPosition cannot be null";
    InstructionDetail instructionDetail = new InstructionDetail(Instruction.F, null);
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    pathFinder.advance(instructionDetail);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullInstructionDetailOnAdvance(){
    String message = "instructionDetail cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    pathFinder.advance(null);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullInstructionOnInstructionDetailOnAdvance(){
    String message = "instructionDetail.instruction cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    pathFinder.advance(new InstructionDetail(null, gridPosition));
  }

  @Test
  public void shoulChangeGridPositionOrientationToEastGivenInstructionDetailWithRightTurnInstruction(){
    InstructionDetail instructionDetail = new InstructionDetail(Instruction.R, gridPosition);
    InstructionExecutionResult result = pathFinder.advance(instructionDetail);
    assertEquals(Orientation.E, result.getGridPosition().getOrientation());
    assertFalse(result.isLost());
  }

  @Test
  public void shouldChangeGridPositionOrientationToWestGivenInstructionDetailWithLeftTurnInstruction(){
    InstructionDetail instructionDetail = new InstructionDetail(Instruction.L, gridPosition);
    InstructionExecutionResult result = pathFinder.advance(instructionDetail);
    assertEquals(Orientation.W, result.getGridPosition().getOrientation());
    assertFalse(result.isLost());
  }

  @Test
  public void shouldReturnInstructionExecutionResultWithIsLostSetToTrueIfGridPositionIsAdvancedForwardOffGrid(){
    gridPosition = new GridPosition(1, 0, Orientation.S);
    InstructionDetail instructionDetail = new InstructionDetail(Instruction.F, gridPosition);
    InstructionExecutionResult result = pathFinder.advance(instructionDetail);
    assertEquals(Orientation.S, result.getGridPosition().getOrientation());
    assertTrue(result.isLost());
  }

  @Test
  public void shouldReturnInstructionExecutionResultWithIsLostSetToFalseIfGridPositionIsAdvancedForwardToPositionWhichIsMarkedAsLost(){
    gridPosition = new GridPosition(1, 0, Orientation.S);
    InstructionDetail instructionDetail = new InstructionDetail(Instruction.F, gridPosition);
    InstructionExecutionResult result = pathFinder.advance(instructionDetail);
    assertEquals(Orientation.S, result.getGridPosition().getOrientation());
    assertTrue(result.isLost());

    GridPosition duplicateGridPosition = new GridPosition(1, 0, Orientation.S);
    InstructionDetail duplicateInstructionDetail = new InstructionDetail(Instruction.F, duplicateGridPosition);
    InstructionExecutionResult newResult = pathFinder.advance(duplicateInstructionDetail);
    assertEquals(Orientation.S, newResult.getGridPosition().getOrientation());
    assertEquals(duplicateGridPosition, newResult.getGridPosition());
    assertFalse(newResult.isLost());
  }

}
