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
      int[] subArray = Arrays.copyOfRange(bank, 0, bank.length - 1);
      IntSummaryStatistics stat = Arrays.stream(subArray).summaryStatistics();
      int index = findElement(bank, stat.getMax());
      int[] subArray2 = Arrays.copyOfRange(bank, index + 1, bank.length);
      IntSummaryStatistics stat2 = Arrays.stream(subArray2).summaryStatistics();

      sum += 10 * stat.getMax() + stat2.getMax();
    }

    return "" + sum;
  }

  private int findElement(int[] arr, int t) {

    int len = arr.length;

    return IntStream.range(0, len).filter(i -> t == arr[i]).findFirst().orElse(-1);
  }

  @Override
  public String part2(String[] input) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'part2'");
  }
}
