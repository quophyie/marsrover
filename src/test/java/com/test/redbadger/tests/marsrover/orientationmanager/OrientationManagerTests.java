package com.test.redbadger.tests.marsrover.orientationmanager;

import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManager;
import com.redbadger.tests.marsrover.orientationmanager.OrientationManagerImpl;
import com.redbadger.tests.marsrover.enums.Instruction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class OrientationManagerTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private OrientationManager orientationManager;
  private GridPosition gridPosition;

  @Before
  public void setUp(){
    orientationManager = new OrientationManagerImpl();
    gridPosition = new GridPosition(1, 1, Orientation.N);

  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGridPositionOnChangeOrientation(){
    String message = "gridPosition cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    orientationManager.changeOrientation(Instruction.R, null);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGridPositionOrientationOnChangeOrientation(){
    String message = "gridPosition.orientation cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    gridPosition.setOrientation(null);
    orientationManager.changeOrientation(Instruction.F, gridPosition);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullInstructionOnChangeOrientation(){
    String message = "instruction cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    orientationManager.changeOrientation(null, gridPosition);
  }

  @Test
  public void shouldReturnEastGivenOrientationNorthOnRightTurn() {

    orientationManager.changeOrientation(Instruction.R, gridPosition);
    assertEquals(Orientation.E, gridPosition.getOrientation());
  }

  @Test
  public void shouldReturnSouthGivenOrientationEastOnRightTurn() {
    gridPosition.setOrientation(Orientation.E);
    orientationManager.changeOrientation(Instruction.R, gridPosition);
    assertEquals(Orientation.S, gridPosition.getOrientation());
  }

  @Test
  public void shouldReturnWestGivenOrientationSouthOnRightTurn() {
    gridPosition.setOrientation(Orientation.S);
    orientationManager.changeOrientation(Instruction.R, gridPosition);
    assertEquals(Orientation.W, gridPosition.getOrientation());
  }

  @Test
  public void shouldReturnNorthGivenOrientationWestOnRightTurn() {
    gridPosition.setOrientation(Orientation.W);
    orientationManager.changeOrientation(Instruction.R, gridPosition);
    assertEquals(Orientation.N, gridPosition.getOrientation());
  }


  @Test
  public void shouldReturnWestGivenOrientationNorthOnLeftTurn() {

    orientationManager.changeOrientation(Instruction.L, gridPosition);
    assertEquals(Orientation.W, gridPosition.getOrientation());
  }

  @Test
  public void shouldReturnNorthGivenOrientationEastOnLeftTurn() {
    gridPosition.setOrientation(Orientation.E);
    orientationManager.changeOrientation(Instruction.L, gridPosition);
    assertEquals(Orientation.N, gridPosition.getOrientation());
  }

  @Test
  public void shouldReturnEastGivenOrientationSouthOnLeftTurn() {
    gridPosition.setOrientation(Orientation.S);
    orientationManager.changeOrientation(Instruction.L, gridPosition);
    assertEquals(Orientation.E, gridPosition.getOrientation());
  }

  @Test
  public void shouldReturnSouthGivenOrientationWestOnLeftTurn() {
    gridPosition.setOrientation(Orientation.W);
    orientationManager.changeOrientation(Instruction.L, gridPosition);
    assertEquals(Orientation.S, gridPosition.getOrientation());
  }
}
