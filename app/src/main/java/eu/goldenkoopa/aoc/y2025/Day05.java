package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.utils.Range;
import eu.goldenkoopa.aoc.y2025.utils.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** --- Day 5: Cafeteria --- </br> https://adventofcode.com/2025/day/5 */
public class Day05 implements Day {

  String[] testInput =
      new String[] {
        "3-5", "10-14", "16-20", "12-18", "", "1", "5", "8", "11", "17", "32",
      };

  String[] testInput2 =
      new String[] {
        "3-5", "10-14", "6-9", "28-38", "", "1", "5", "8", "11", "17", "32",
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

    List<Range> ranges =
        new ArrayList<>(
            Arrays.stream(StringUtils.splitByEmptyLines(input)[0]).map(this::parseRange).toList());

    mergeAllRanges(ranges);

    long countValidIds = 0;
    for (Range range : ranges) {
      countValidIds += range.count();
    }

    return "" + countValidIds;
  }

  Range parseRange(String string) {

    String[] parts = string.split("-");

    long start = Long.parseLong(parts[0]);
    long end = Long.parseLong(parts[1]) + 1;

    return new Range(start, end);
  }

  void mergeAllRanges(List<Range> ranges) {

    int index = 0;
    while (index < ranges.size() - 1) {

      int innerIndex = index + 1;
      while (innerIndex < ranges.size()) {
        if (!ranges.get(index).canMerge(ranges.get(innerIndex))) {
          innerIndex++;
          continue;
        }
        ranges.get(index).merge(ranges.get(innerIndex));
        ranges.remove(innerIndex);
        innerIndex = index + 1;
      }

      index++;
    }
  }
}
