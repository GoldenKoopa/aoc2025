package eu.goldenkoopa.aoc.y2025.utils.grid;

import eu.goldenkoopa.aoc.y2025.utils.Grid;
import java.util.function.Predicate;

public class GridAlgorithms {

  public static <E> boolean hasBingo(Grid<E> grid, Predicate<E> isMarked) {
    return Bingo.hasBingo(grid, isMarked);
  }
}
