package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.utils.Grid;
import eu.goldenkoopa.aoc.y2025.utils.Location;
import eu.goldenkoopa.aoc.y2025.utils.ObjectGrid;
import java.util.List;

/**
 * --- Day 4: Printing Department --- <br>
 * https://adventofcode.com/2025/day/4
 */
public class Day04 implements Day {

  String[] testInput =
      new String[] {
        "..@@.@@@@.",
        "@@@.@.@.@@",
        "@@@@@.@.@@",
        "@.@@@@..@.",
        "@@.@@@@.@@",
        ".@@@@@@@.@",
        ".@.@.@.@@@",
        "@.@@@.@@@@",
        ".@@@@@@@@.",
        "@.@.@@@.@."
      };

  @Override
  public String part1(String[] input) {

    Grid<Boolean> grid =
        ObjectGrid.parseChars(input, c -> c.equals('@') ? Boolean.TRUE : Boolean.FALSE);

    long sum =
        grid.locations()
            .filter(grid::get)
            .map(grid::allNeighbours)
            .mapToInt(neighbours -> (int) neighbours.stream().map(grid::get).filter(e -> e).count())
            .filter(count -> count < 4)
            .count();

    return "" + sum;
  }

  @Override
  public String part2(String[] input) {

    Grid<Boolean> grid =
        ObjectGrid.parseChars(input, c -> c.equals('@') ? Boolean.TRUE : Boolean.FALSE);

    int sum = 0;
    int lastCount = Integer.MAX_VALUE;
    while (lastCount > 0) {
      List<Location> locations =
          grid.locations()
              .filter(grid::get)
              .filter(l -> grid.allNeighbours(l).stream().map(grid::get).filter(e -> e).count() < 4)
              .toList();

      locations.forEach(l -> grid.put(l, Boolean.FALSE));

      lastCount = locations.size();

      sum += lastCount;
    }

    return "" + sum;
  }
}
