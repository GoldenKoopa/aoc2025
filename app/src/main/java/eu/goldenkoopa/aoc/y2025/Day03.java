package eu.goldenkoopa.aoc.y2025;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

/**
 * --- Day 3: Lobby --- <br>
 * https://adventofcode.com/2025/day/3
 */
public class Day03 implements Day {

  String[] testInput =
      new String[] {"987654321111111", "811111111111119", "234234234234278", "818181911112111"};

  @Override
  public String part1(String[] input) {

    int[][] banks =
        Arrays.stream(input)
            .map(s -> s.chars().map(Character::getNumericValue).toArray())
            .toArray(int[][]::new);

    int sum = 0;
    for (int[] bank : banks) {
      IntSummaryStatistics stat = getStats(bank, 0, bank.length - 1);
      int index = findElement(bank, stat.getMax(), 0);
      IntSummaryStatistics stat2 = getStats(bank, index + 1, bank.length);

      sum += 10 * stat.getMax() + stat2.getMax();
    }

    return "" + sum;
  }

  private IntSummaryStatistics getStats(int[] arr, int start, int end) {
    int[] subArray = Arrays.copyOfRange(arr, start, end);
    return Arrays.stream(subArray).summaryStatistics();
  }

  private int findElement(int[] arr, int t, int start) {

    int len = arr.length;

    return IntStream.range(start, len).filter(i -> t == arr[i]).findFirst().orElse(-1);
  }

  @Override
  public String part2(String[] input) {

    int[][] banks =
        Arrays.stream(input)
            .map(s -> s.chars().map(Character::getNumericValue).toArray())
            .toArray(int[][]::new);

    long sum = 0;
    for (int[] bank : banks) {
      int lastIndex = -1;
      for (int i = 0; i < 12; i++) {
        IntSummaryStatistics stats = getStats(bank, lastIndex + 1, bank.length - 12 + 1 + i);
        lastIndex = findElement(bank, stats.getMax(), lastIndex + 1);
        sum += stats.getMax() * Math.pow(10, 12d - 1 - i);
      }
    }

    return "" + sum;
  }
}
