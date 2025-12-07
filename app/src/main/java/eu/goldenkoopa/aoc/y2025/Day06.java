package eu.goldenkoopa.aoc.y2025;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * --- Day 6: Trash Compactor --- <br>
 * https://adventofcode.com/2025/day/6
 */
public class Day06 implements Day {

  String[] testInput =
      new String[] {
        "123 328  51 64 ", " 45 64  387 23 ", "  6 98  215 314", "*   +   *   +  ",
      };

  @Override
  public String part1(String[] input) {

    String[][] splitInput =
        Arrays.stream(input).map(l -> l.trim().split("\\s+")).toArray(String[][]::new);

    long sum = 0;
    for (int i = 0; i < splitInput[0].length; i++) {
      String operator = splitInput[splitInput.length - 1][i];

      long result = operator.equals("*") ? 1 : 0;
      for (int j = 0; j < splitInput.length - 1; j++) {
        if (operator.equals("*")) {
          result *= Integer.parseInt(splitInput[j][i]);
          continue;
        }
        result += Integer.parseInt(splitInput[j][i]);
      }

      sum += result;
    }

    return "" + sum;
  }

  @Override
  public String part2(String[] input) {

    long sum = 0;

    int col = input[0].length() - 1;
    Deque<Integer> stack = new ArrayDeque<>();
    while (col >= 0) {

      StringBuilder sb = new StringBuilder();
      for (int row = 0; row < input.length; row++) {
        char charAt = input[row].charAt(col);
        if (!Character.isDigit(charAt)) {
          continue;
        }
        sb.append(charAt);
      }

      stack.add(Integer.parseInt(sb.toString()));

      char operator = input[input.length - 1].charAt(col);
      if (Character.isWhitespace(operator)) {
        col--;
        continue;
      }

      sum += doOperation(stack, operator);
      col--;
      col--;
    }

    return "" + sum;
  }

  long doOperation(Deque<Integer> stack, char operator) {
    long result = operator == '*' ? 1 : 0;

    while (!stack.isEmpty()) {
      int num = stack.pop();
      if (operator == '*') {
        result *= num;
        continue;
      }
      result += num;
    }

    return result;
  }
}
