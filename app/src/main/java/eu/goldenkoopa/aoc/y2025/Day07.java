package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.utils.Grid;
import eu.goldenkoopa.aoc.y2025.utils.Location;
import eu.goldenkoopa.aoc.y2025.utils.ObjectGrid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * --- Day 7: Laboratories --- <br>
 * https://adventofcode.com/2025/day/7
 */
public class Day07 implements Day {

  String[] testInput =
      new String[] {
        ".......S.......",
        "...............",
        ".......^.......",
        "...............",
        "......^.^......",
        "...............",
        ".....^.^.^.....",
        "...............",
        "....^.^...^....",
        "...............",
        "...^.^...^.^...",
        "...............",
        "..^...^.....^..",
        "...............",
        ".^.^.^.^.^...^.",
        "...............",
      };

  @Override
  public String part1(String[] input) {

    Grid<Character> grid = ObjectGrid.parseChars(input, c -> c);

    Location start = grid.findFirst('S');
    grid.put(new Location(start.row() + 1, start.column()), '|');

    AtomicInteger timesSplit = new AtomicInteger(0);
    for (int row = start.row() + 1; row < grid.rows() - 1; row++) {
      grid.findAllRow('|', row)
          .forEach(
              loc -> {
                Location newLoc = new Location(loc.row() + 1, loc.column());
                if (!grid.isValid(newLoc)) {
                  return;
                }
                char value = grid.get(newLoc);
                if (value == '|') {
                  return;
                }
                if (value == '.') {
                  grid.put(newLoc, '|');
                  return;
                }
                Location posLeft = new Location(newLoc.row(), newLoc.column() - 1);
                if (grid.isValid(posLeft)) {
                  grid.put(posLeft, '|');
                }
                Location posRight = new Location(newLoc.row(), newLoc.column() + 1);
                if (grid.isValid(posRight)) {
                  grid.put(posRight, '|');
                }
                timesSplit.getAndIncrement();
              });
    }

    return "" + timesSplit;
  }

  @Override
  public String part2(String[] input) {

    Grid<Character> grid = ObjectGrid.parseChars(input, c -> c);

    Map<Location, Long> timelinesPerSplit = new HashMap<>();

    Location firstSplit = grid.findFirstRow('^', 2);
    timelinesPerSplit.put(firstSplit, 1L);

    for (int row = 4; row < grid.rows(); row += 2) {
      grid.findAllRow('^', row)
          .forEach(
              loc -> {
                Location upperLoc = new Location(loc.row() - 1, loc.column());
                long currentTimelines = 0;
                while (grid.isValid(upperLoc) && grid.get(upperLoc) == '.') {
                  Location leftLoc = new Location(upperLoc.row(), upperLoc.column() - 1);
                  if (grid.get(leftLoc) == '^') {
                    currentTimelines += timelinesPerSplit.get(leftLoc);
                  }
                  Location rightLoc = new Location(upperLoc.row(), upperLoc.column() + 1);
                  if (grid.get(rightLoc) == '^') {
                    currentTimelines += timelinesPerSplit.get(rightLoc);
                  }
                  upperLoc = new Location(upperLoc.row() - 1, upperLoc.column());
                }
                timelinesPerSplit.put(loc, currentTimelines);
              });
    }
    long result = timelinesPerSplit.values().stream().reduce(0L, (a, b) -> a + b);
    result += 1;

    return "" + result;
  }
}
