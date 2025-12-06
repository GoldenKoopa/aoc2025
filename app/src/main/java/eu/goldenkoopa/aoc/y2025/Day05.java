package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.utils.Range;
import eu.goldenkoopa.aoc.y2025.utils.StringUtils;
import java.util.Arrays;

/** --- Day 5: Cafeteria --- </br> https://adventofcode.com/2025/day/5 */
public class Day05 implements Day {

  String[] testInput =
      new String[] {
        "3-5", "10-14", "16-20", "12-18", "", "1", "5", "8", "11", "17", "32",
      };

  @Override
  public String part1(String[] input) {

    String[][] parts = StringUtils.splitByEmptyLines(input);

    long[] ids = Arrays.stream(parts[1]).mapToLong(Long::parseLong).toArray();
    Range[] ranges = Arrays.stream(parts[0]).map(this::parseRange).toArray(Range[]::new);

    int countValidIds = 0;
    for (long id : ids) {
      for (Range range : ranges) {
        if (range.contains(id)) {
          countValidIds++;
          break;
        }
      }
    }

    return "" + countValidIds;
  }

  @Override
  public String part2(String[] input) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'part2'");
  }

  Range parseRange(String string) {

    String[] parts = string.split("-");

    long start = Long.parseLong(parts[0]);
    long end = Long.parseLong(parts[1]) + 1;

    return new Range(start, end);
  }
}
