package com.test.redbadger.tests.marsrover.propulsion;

import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngine;
import com.redbadger.tests.marsrover.propulsion.PropulsionEngineImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertEquals;

public class PropulsionEngineTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private PropulsionEngine propulsionEngine;
  int xCoord, yCoord ;
  private GridPosition gridPosition;

  @Before
  public void setUp(){
    propulsionEngine = new PropulsionEngineImpl();
    xCoord = 3;
    yCoord = 3;
    gridPosition = new GridPosition(xCoord, yCoord, Orientation.N);
  }

  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGridPositionOnApplyPropulsion(){
    String message = "gridPosition cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    propulsionEngine.applyPropulsion(null);
  }


  @Test
  public void shouldThrowIllegalArgumentExceptionGivenNullGridPositionOrientationOnApplyPropulsion(){
    String message = "gridPosition.orientation cannot be null";
    thrown.expectMessage(message);
    thrown.expect(IllegalArgumentException.class);
    gridPosition.setOrientation(null);
    propulsionEngine.applyPropulsion(gridPosition);
  }

  @Test
  public void shouldMoveGridPositionNorthByOneWhenPropulsionAppliedToGridPositionOrientedNorth(){
    GridPosition result = propulsionEngine.applyPropulsion(gridPosition);
    assertEquals(Orientation.N, result.getOrientation());
    assertEquals(yCoord +1, result.getY());
  }

  @Test
  public void shouldMoveGridPositionEastByOneWhenPropulsionAppliedToGridPositionOrientedEast(){
    gridPosition.setOrientation(Orientation.E);
    GridPosition result = propulsionEngine.applyPropulsion(gridPosition);
    assertEquals(Orientation.E, result.getOrientation());
    assertEquals(xCoord +1, result.getX());
  }

  @Test
  public void shouldMoveGridPositionSouthByOneWhenPropulsionAppliedToGridPositionOrientedSouth(){
    gridPosition.setOrientation(Orientation.S);
    GridPosition result = propulsionEngine.applyPropulsion(gridPosition);
    assertEquals(Orientation.S, result.getOrientation());
    assertEquals(yCoord  - 1, result.getY());
  }

  @Test
  public void shouldMoveGridPositionWestByOneWhenPropulsionAppliedToGridPositionOrientedWest(){
    gridPosition.setOrientation(Orientation.W);
    GridPosition result = propulsionEngine.applyPropulsion(gridPosition);
    assertEquals(Orientation.W, result.getOrientation());
    assertEquals(xCoord  - 1, result.getX());
  }

}
