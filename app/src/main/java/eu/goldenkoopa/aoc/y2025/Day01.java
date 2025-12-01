package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.Day01.RotationLock.Direction;

/**
 * --- Day 1: Secret Entrance --- <br>
 * https://adventofcode.com/2025/day/1
 */
public class Day01 implements Day {

  String[] testInput =
      new String[] {"L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82"};

  @Override
  public String part1(String[] input) {
    RotationLock lock = new RotationLock(50, 100);

    int amountOfZero = 0;

    for (String line : input) {

      Direction direction = Direction.parse(line.charAt(0));
      assert direction != null;

      int turn = lock.turn(Integer.parseInt(line.substring(1)), direction);
      if (turn == 0) {
        amountOfZero++;
      }
    }

    return "" + amountOfZero;
  }

  @Override
  public String part2(String[] input) {
    RotationLock lock = new RotationLock(50, 100);

    int amountOfZero = 0;

    for (String line : input) {

      Direction direction = Direction.parse(line.charAt(0));
      assert direction != null;

      int turnAmount = Integer.parseInt(line.substring(1));
      amountOfZero += Math.floorDiv(turnAmount, lock.max);
      int oldState = lock.getCurrentState();
      int newState = lock.turn(turnAmount, direction);
      if (oldState == 0) {
        continue;
      }
      if (newState == 0
          || (direction.equals(Direction.RIGHT) && oldState > newState)
          || (direction.equals(Direction.LEFT) && newState > oldState)) {
        amountOfZero++;
      }
    }

    return "" + amountOfZero;
  }

  class RotationLock {

    private int state;
    int max;

    RotationLock(int initialState, int max) {
      this.state = initialState;
      this.max = max;
    }

    int turn(int amount, Direction direction) {

      if (direction.equals(Direction.LEFT)) {
        amount = -amount;
      }

      this.state = (this.state + amount) % this.max;
      if (this.state < 0) {
        this.state += this.max;
      }
      return this.state;
    }

    int getCurrentState() {
      return this.state;
    }

    enum Direction {
      RIGHT("R"),
      LEFT("L");

      private String inputRepresentation;

      Direction(String inputRepresentation) {
        this.inputRepresentation = inputRepresentation;
      }

      public static Direction parse(char direction) {
        return parse(Character.toString(direction));
      }

      public static Direction parse(String direction) {
        for (Direction directionType : Direction.values()) {
          if (directionType.name().equals(direction)
              || directionType.inputRepresentation.equals(direction)) {
            return directionType;
          }
        }
        return null;
      }
    }
  }
}
