package eu.goldenkoopa.aoc.y2025.utils.geometry;

import eu.goldenkoopa.aoc.y2025.utils.Location;

public class Line {

  public double m = 0;
  public double b = 0;

  public Line(double m, double b) {
    this.m = m;
    this.b = b;
  }

  public Line(Location loc1, Location loc2) {
    int dx = loc2.column() - loc1.column();
    if (dx == 0) {
      throw new IllegalArgumentException("Cannot create a vertical line with undefined slope");
    }
    int dy = loc2.row() - loc1.row();
    this.m = dy / (double) dx;
    this.b = loc1.row() - (this.m * loc1.column());
  }

  public double getY(double x) {
    return m * x + b;
  }

  public double getX(double y) {
    if (m == 0) {
      throw new ArithmeticException("Line is horizontal; undefined x for given y");
    }
    return (y - b) / m;
  }

  public double getM() {
    return this.m;
  }

  public double getB() {
    return this.b;
  }

  public Line perpendicularThrough(Location point) {
    if (this.m == 0) {
      throw new IllegalArgumentException(
          "Perpendicular line would be vertical and undefined in this model");
    }
    double perpM = -1 / this.m;
    double newB = point.row() - perpM * point.column();
    return new Line(perpM, newB);
  }

  public boolean isParallelTo(Line other) {
    return this.m == other.m;
  }

  public boolean isPerpendicularTo(Line other) {
    return this.m * other.m == -1;
  }

  @Override
  public String toString() {
    return String.format("y = %.3f * x + %.3f", m, b);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (!(o instanceof Line)) {
      return false;
    }
    Line other = (Line) o;

    return this.m == other.m && this.b == other.b;
  }
}
