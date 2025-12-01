package eu.goldenkoopa.aoc.y2025.utils;

public record Location(int row, int column) implements Comparable<Location> {

  public Location moved(Direction d) {
    int[] delta = d.delta();
    return movedBy(delta[0], delta[1]);
  }

  public Direction to(Location o) {
    for (Direction dir : Direction.values()) {
      if (moved(dir).equals(o)) {
        return dir;
      }
    }
    return null;
  }

  public Location movedBy(int dr, int dc) {
    return new Location(row + dr, column + dc);
  }

  public int manhattenDistance(Location o) {
    return Math.abs(row - o.row) + Math.abs(column - o.column);
  }

  @Override
  public String toString() {
    return "P(" + row + "," + column + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !(o instanceof Location)) {
      return false;
    }
    Location other = (Location) o;
    return this.column() == other.column() && this.row() == other.row();
  }

  @Override
  public int compareTo(Location o) {
    int dr = row - o.row;
    if (dr != 0) {
      return dr;
    }
    return column - o.column;
  }
}
