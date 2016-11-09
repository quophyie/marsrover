package com.redbadger.tests.marsrover.commandmodule;

import com.redbadger.tests.marsrover.enums.Instruction;
import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.exception.InvalidInstructionException;
import com.redbadger.tests.marsrover.gridbuilder.GridPosition;
import com.redbadger.tests.marsrover.gridbuilder.Point;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandModuleImpl implements CommandModule {

  private final int MAX_NUM_OF_NAVIGATION_INSTRUCTIONS = 99;
  private final int MAX_COORD_VALUE = 50;


  @Override
  public Point processGridDimensionsInstructions(String dimensions) {
    if (dimensions == null || "".equals(dimensions.trim()))
      throw new InvalidInstructionException("Grid uppermost top right point instruction cannot be null or empty");

    long numOfDimensions = Pattern.compile(" ").splitAsStream(dimensions).count();

    if (numOfDimensions != 2)
      throw new InvalidInstructionException("Grid uppermost top right point requires two coordinates i.e. X and Y coordinates");

    List<Integer> coordinates;
    try {
      coordinates =Pattern.compile(" ")
          .splitAsStream(dimensions.trim())
          .filter(s -> !"".equals(s.trim()))
          .map(Integer::parseInt)
          .collect(Collectors.toList());
    } catch (NumberFormatException nfe){
        throw new InvalidInstructionException(String.format("Invalid coordinate instruction provided. Coordinates must be numbers only Instruction: %s", dimensions));
    }

    int xCoord = coordinates.get(0);
    int yCoord = coordinates.get(1);

    if(xCoord > MAX_COORD_VALUE || yCoord > MAX_COORD_VALUE)
      throw new InvalidInstructionException(String.format("xCoordinate or yCoordinate cannot be greater than %d", MAX_COORD_VALUE));

    Point point = new Point(xCoord, yCoord);

    return point;
  }

  @Override
  public GridPosition processStartingGridPositionInstructions(String startingPositionInstructions) {

    if (startingPositionInstructions == null || "".equals(startingPositionInstructions.trim()))
      throw new InvalidInstructionException("startingPosition instruction cannot be null or empty");

    long numOfGridPositionInstructionLength = Pattern.compile(" ").splitAsStream(startingPositionInstructions).count();

    if (numOfGridPositionInstructionLength != 3)
      throw new InvalidInstructionException("startingPosition instruction must have coordinates and orientation coordinates i.e. X and Y coordinates and orientation");

    List<Integer> coordinates;
    try {
      coordinates =Pattern.compile(" ")
          .splitAsStream(startingPositionInstructions
              .trim()
              .substring(0, startingPositionInstructions.lastIndexOf(' '))
              .trim())
          .filter(s -> !"".equals(s.trim()))
          .map(Integer::parseInt)
          .collect(Collectors.toList());
    } catch (NumberFormatException nfe){
      throw new InvalidInstructionException(String.format("Invalid coordinate instruction provided. Starting position coordinates must be numbers only. Instruction: %s", startingPositionInstructions));
    }

    String orientationInstr = startingPositionInstructions.substring(startingPositionInstructions.lastIndexOf(' ')).trim();
    Orientation orientation =  Orientation.valueOf(orientationInstr);

    int xCoord = coordinates.get(0);
    int yCoord = coordinates.get(1);

    if(xCoord > MAX_COORD_VALUE || yCoord > MAX_COORD_VALUE)
      throw new InvalidInstructionException(String.format("xCoordinate or yCoordinate cannot be greater than %d", MAX_COORD_VALUE));


    GridPosition gridPosition = new GridPosition(xCoord, yCoord, orientation);

    return gridPosition;
  }

  @Override
  public List<Instruction> processNavigationInstructions(String navigationInstructions) {
    if (navigationInstructions == null || "".equals(navigationInstructions.trim()))
      throw new InvalidInstructionException("navigationInstructions instruction cannot be null or empty");

    if (navigationInstructions.length() > this.MAX_NUM_OF_NAVIGATION_INSTRUCTIONS)
      throw new InvalidInstructionException(String.format("navigationInstructions length must be less than or equal to %d", MAX_NUM_OF_NAVIGATION_INSTRUCTIONS));


    List<Instruction> instructionList = Pattern.compile("")
          .splitAsStream(navigationInstructions.trim())
          .filter(instruction -> !"".equals(instruction.trim()) && Pattern.matches("[A-Za-z]", instruction))
          .map(Instruction::valueOf)
          .collect(Collectors.toList());

    return instructionList;
  }
}
