package eu.goldenkoopa.aoc.y2025.utils;

import java.util.List;

public enum Direction {
  N(-1, 0),
  NE(-1, 1),
  E(0, 1),
  SE(1, 1),
  S(1, 0),
  SW(1, -1),
  W(0, -1),
  NW(-1, -1);

  public static final List<Direction> MAIN_DIRECTIONS = List.of(N, E, S, W);
  public static final List<Direction> DIAGONAL_DIRECTIONS = List.of(NE, SE, SW, NW);

  private Direction(int deltaRow, int deltaColumn) {
    this.delta = new int[] {deltaRow, deltaColumn};
  }

  private int[] delta;

  public int[] delta() {
    return delta;
  }

  public Direction turn(int eigthsClockwise) {
    return Direction.values()[(ordinal() + eigthsClockwise) % 8];
  }
}
