package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import eu.goldenkoopa.aoc.y2025.utils.Direction;
import org.junit.jupiter.api.Test;

class DirectionTest {

  @Test
  void testDelta() {
    assertArrayEquals(new int[] {0, 1}, Direction.E.delta());
    assertArrayEquals(new int[] {1, -1}, Direction.SW.delta());
    assertArrayEquals(new int[] {1, 0}, Direction.S.delta());
  }

  @Test
  void testTurn() {
    assertEquals(Direction.SW, Direction.S.turn(1));
    assertEquals(Direction.NW, Direction.S.turn(3));
    assertEquals(Direction.W, Direction.N.turn(6));
    assertEquals(Direction.SW, Direction.NE.turn(4));
  }
}
