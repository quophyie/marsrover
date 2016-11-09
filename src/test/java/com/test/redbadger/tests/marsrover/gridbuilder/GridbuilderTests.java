package com.test.redbadger.tests.marsrover.gridbuilder;

import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.exception.GridException;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilder;
import com.redbadger.tests.marsrover.gridbuilder.GridBuilderImpl;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by dman on 07/11/2016.
 */
public class GridbuilderTests {
  private GridBuilder gridBuilder;
  private int xCoord, yCoord;

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Before
  public void  setUp(){
    xCoord = 5;
    yCoord = 3;
    gridBuilder = new GridBuilderImpl();
  }

  @Test
  public void shouldThrowGridExceptionGivenXCoordZeroYCoordZero() {
    xCoord = 0;
    yCoord = 0;

    String message = String.format("xCoordinate and yCoordinate must be greater than 0. xCoordinate: %d, yCoordinate: %d ", xCoord, yCoord);
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(xCoord, yCoord);
  }

  @Test
  public void shouldThrowGridExceptionGivenXCoord1YCoordZero() {
    xCoord = 1;
    yCoord = 0;

    String message = String.format("xCoordinate and yCoordinate must be greater than 0. xCoordinate: %d, yCoordinate: %d ", xCoord, yCoord);
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(xCoord, yCoord);
  }

  @Test
  public void shouldThrowGridExceptionGivenXCoordZeroYCoord1() {
    xCoord = 0;
    yCoord = 1;

    String message = String.format("xCoordinate and yCoordinate must be greater than 0. xCoordinate: %d, yCoordinate: %d ", xCoord, yCoord);
    thrown.expect(GridException.class);
    thrown.expectMessage(message);

    gridBuilder.buildGrid(xCoord, yCoord);
  }

  @Test
  public void shouldReturnGridWithLengthOneGivenXCoord1YCoord1() {
    String [][] grid = gridBuilder.buildGrid(1, 1);
    assertEquals(1, grid.length);
  }

  @Test
  public void shouldReturnGridWithLength50GivenXCoord50YCoord50() {
    String [][] grid = gridBuilder.buildGrid(50, 50);
    assertEquals(50, grid.length);
  }


  @Test
  public void shouldReturnGridWithLength5GivenXCoord5YCoord3() {
    String [][] grid = gridBuilder.buildGrid(5, 3);
    assertEquals(5, grid.length);
  }

  @Test
  public void shouldReturnGridWithWidthLength3GivenXCoord5YCoord3() {
    String [][] grid = gridBuilder.buildGrid(5, 3);
    assertEquals(3, grid[0].length);
  }

  @Test
  public void shouldReturnTrueIfCoordinateExistsOnGrid() {
    String [][] grid = gridBuilder.buildGrid(5, 3);
    boolean result = gridBuilder.isValidGridPosition(grid, 2, 2);
    assertTrue(result);
  }

  @Test
  public void shouldReturnFalseIfCoordinateDoesNotExistsOnGrid() {
    String [][] grid = gridBuilder.buildGrid(5, 3);
    boolean result = gridBuilder.isValidGridPosition(grid, 7, 7);
    assertFalse(result);
  }




}
