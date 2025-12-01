package eu.goldenkoopa.aoc.y2025.utils;

public class IntegerGrid extends ObjectGrid<Integer> {

  public IntegerGrid(int rows, int columns, Integer value) {
    super(rows, columns, value);
  }

  public static IntegerGrid parse(String[] input) {
    ObjectGrid<Integer> grid = ObjectGrid.parseChars(input, c -> Character.getNumericValue(c));
    IntegerGrid intGrid = new IntegerGrid(0, 0, 0);
    intGrid.grid = grid.grid;
    return intGrid;
  }

  public static IntegerGrid parse(String[] input, char delimiter) {
    ObjectGrid<Integer> grid = ObjectGrid.parse(input, Integer::parseInt, delimiter);
    IntegerGrid intGrid = new IntegerGrid(0, 0, 0);
    intGrid.grid = grid.grid;
    return intGrid;
  }
}
