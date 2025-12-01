package eu.goldenkoopa.aoc.y2025.utils.geometry;

import eu.goldenkoopa.aoc.y2025.utils.Location;
import java.util.HashSet;
import java.util.Set;

public class LineSegment {

  private Location start;
  private Location end;

  public LineSegment(Location start, Location end) {
    this.start = start;
    this.end = end;
  }

  public Location getStart() {
    return start;
  }

  public Location getEnd() {
    return end;
  }

  public double length() {
    int dx = end.column() - start.column();
    int dy = end.row() - start.row();
    return Math.sqrt(dx * dx + dy * dy);
  }

  public Location midpoint() {
    int midRow = (start.row() + end.row()) / 2;
    int midCol = (start.column() + end.column()) / 2;
    return new Location(midRow, midCol);
  }

  public double slope() {
    int dx = end.column() - start.column();
    if (dx == 0) {
      throw new ArithmeticException("Vertical segment has undefined slope");
    }
    int dy = end.row() - start.row();
    return dy / (double) dx;
  }

  public boolean isVertical() {
    return start.column() == end.column();
  }

  public boolean isHorizontal() {
    return start.row() == end.row();
  }

  public double angleDegrees() {
    int dx = end.column() - start.column();
    int dy = end.row() - start.row();
    return Math.toDegrees(Math.atan2(dy, dx));
  }

  public Set<Location> allTouchedLocations() {
    Set<Location> points = new HashSet<>();

    int x0 = start.column();
    int y0 = start.row();
    int x1 = end.column();
    int y1 = end.row();

    int dx = Math.abs(x1 - x0);
    int dy = Math.abs(y1 - y0);

    int sx = Integer.compare(x1, x0);
    int sy = Integer.compare(y1, y0);

    int err = dx - dy;

    while (true) {
      points.add(new Location(y0, x0));
      if (x0 == x1 && y0 == y1) {
        break;
      }
      int e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x0 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y0 += sy;
      }
    }

    return points;
  }

  @Override
  public String toString() {
    return String.format(
        "Segment[(%d,%d) -> (%d,%d)]", start.row(), start.column(), end.row(), end.column());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LineSegment other)) {
      return false;
    }

    return (start.equals(other.start) && end.equals(other.end))
        || (start.equals(other.end) && end.equals(other.start));
  }
}
