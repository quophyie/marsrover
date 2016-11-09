package com.redbadger.tests.marsrover.gridbuilder;

import com.redbadger.tests.marsrover.enums.Orientation;
import com.redbadger.tests.marsrover.pathfinder.InstructionExecutionResult;

import java.awt.*;
import java.util.Objects;


public class GridPosition  {

  private Orientation orientation;
  private Point point;


  public GridPosition(int x, int y, Orientation orientation){
    this.orientation  = orientation;
    point = new Point(x, y);
  }

  public Orientation getOrientation() {
    return orientation;
  }

  public void setOrientation(Orientation orientation) {
    this.orientation = orientation;
  }


  public int getX() {
    return point.getX();
  }

  public void setX(int x) {
    this.point.setX(x);
  }


  public int getY() {
    return point.getY();
  }

  public void setY(int y) {
    this.point.setY(y);
  }

  public void setGridPosition(int x, int y, Orientation orientation){
    this.setX(x);
    this.setY(y);
    this.setOrientation(orientation);
  }

  public Point getPoint() {
    return point;
  }

  public void setPoint(Point point) {
    this.point = point;
  }

  @Override
  public boolean equals(Object obj){
    if ( obj!=null &&
        (this.getOrientation()!= null && ((GridPosition)obj).getOrientation()!= null)
        && this.getOrientation().equals(((GridPosition)obj).getOrientation())
        && this.getX() == ((GridPosition)obj).getX()
        && this.getY() == ((GridPosition)obj).getY())
      return true;

    return false;
  }

  @Override
  public int hashCode(){
    return (String.valueOf(this.getPoint().hashCode())
           + String.valueOf(this.orientation.hashCode())).hashCode();
  }
}
