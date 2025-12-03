package eu.goldenkoopa.aoc.y2025;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 2: Gift Shop --- <br>
 * https://adventofcode.com/2025/day/2
 */
public class Day02 implements Day {

  String[] testInput =
      new String[] {
        "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
      };

  @Override
  public String part1(String[] input) {

    String[] actualInput = input[0].split(",");

    long sum = 0;
    for (String range : actualInput) {

      String[] startEnd = range.split("-");
      long start = Long.parseLong(startEnd[0]);
      long end = Long.parseLong(startEnd[1]);

      for (long i = start; i < end + 1; i++) {

        String number = Long.toString(i);

        if (number.length() % 2 != 0) {
          continue;
        }

        String firstHalf = number.substring(0, number.length() / 2);
        String secondHalf = number.substring(number.length() / 2);

        if (Integer.parseInt(firstHalf) == Integer.parseInt(secondHalf)) {
          sum += i;
        }
      }
    }

    return "" + sum;
  }

  Pattern pattern = Pattern.compile("^(\\d+)\\1+$");

  @Override
  public String part2(String[] input) {

    String[] actualInput = input[0].split(",");

    long sum = 0;
    for (String range : actualInput) {

      String[] startEnd = range.split("-");
      long start = Long.parseLong(startEnd[0]);
      long end = Long.parseLong(startEnd[1]);

      for (long i = start; i < end + 1; i++) {

        Matcher matcher = pattern.matcher(Long.toString(i));

        if (matcher.find()) {
          sum += i;
        }
      }
    }

    return "" + sum;
  }
}
