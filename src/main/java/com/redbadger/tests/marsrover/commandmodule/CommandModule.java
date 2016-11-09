package com.redbadger.tests.marsrover.commandmodule;

import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.gridbuilder.Point;

import java.util.List;

/**
 * Created by dman on 08/11/2016.
 */
public interface CommandModule {
  /**
   * Should return the top right uppermost coordinates of the grid
   * @param dimensions
   * @return
   */
  Point processGridDimensionsInstructions(String dimensions);


  /**
   * Should return  the robots starting position on the grid
   * @param startingPositionInstructions
   * @return
   */
  GridPosition processStartingGridPositionInstructions(String startingPositionInstructions);


  /**
   * Processes a line of navigation instructions
   * @param navigationInstructions
   * @return
   */
  List<Instruction> processNavigationInstructions(String navigationInstructions);

}
