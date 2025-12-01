package eu.goldenkoopa.aoc.y2025.utils.grid;

import eu.goldenkoopa.aoc.y2025.utils.Grid;
import eu.goldenkoopa.aoc.y2025.utils.Location;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Bingo {

  public static <E> boolean hasBingo(Grid<E> grid, Predicate<E> isMarked) {

    // rows
    for (int i = 0; i < grid.rows(); i++) {
      final int idx = i;
      Stream<E> rowStream =
          IntStream.range(0, grid.cols()).mapToObj(j -> grid.get(new Location(idx, j)));
      if (rowStream.allMatch(isMarked)) {
        return true;
      }
    }

    // columns
    for (int i = 0; i < grid.cols(); i++) {
      final int idx = i;
      Stream<E> rowStream =
          IntStream.range(0, grid.rows()).mapToObj(j -> grid.get(new Location(j, idx)));
      if (rowStream.allMatch(isMarked)) {
        return true;
      }
    }

    return false;
  }
}
