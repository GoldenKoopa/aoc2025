package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.goldenkoopa.aoc.y2025.utils.Direction;
import eu.goldenkoopa.aoc.y2025.utils.IntegerGrid;
import eu.goldenkoopa.aoc.y2025.utils.Location;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GridTest {

  private static final String[] DIGIT_INPUT = new String[] {"0345", "8975", "2345"};
  private static final String[] NUMBER_INPUT = new String[] {"0 3 45", "89 7 5", "23 4 5"};
  private IntegerGrid DIGIT_GRID;
  private IntegerGrid NUMBER_GRID;

  @BeforeEach
  void setUpGrids() {
    DIGIT_GRID = IntegerGrid.parse(DIGIT_INPUT);
    NUMBER_GRID = IntegerGrid.parse(NUMBER_INPUT, ' ');
  }

  @Test
  void testMainNeighbourDirections() {
    assertEquals(
        Set.of(Direction.S, Direction.E), DIGIT_GRID.mainNeighbourDirections(new Location(0, 0)));
    assertEquals(
        new HashSet<>(Direction.MAIN_DIRECTIONS),
        DIGIT_GRID.mainNeighbourDirections(new Location(1, 1)));
  }

  @Test
  void testAllNeighbourDirections() {
    assertEquals(
        Set.of(Direction.S, Direction.SE, Direction.E),
        DIGIT_GRID.allNeighbourDirections(new Location(0, 0)));
    assertEquals(
        new HashSet<>(Arrays.asList(Direction.values())),
        DIGIT_GRID.allNeighbourDirections(new Location(1, 1)));
  }

  @Test
  void testRowsColumns() {
    assertEquals(3, DIGIT_GRID.rows(), "digit grid should have 3 rows");
    assertEquals(4, DIGIT_GRID.cols(), "digit grid should have 4 columns");

    assertEquals(3, NUMBER_GRID.rows(), "number grid should have 3 rows");
    assertEquals(3, NUMBER_GRID.cols(), "number grid should have 3 columns");
  }

  @Test
  void testLocationValidity() {
    assertTrue(DIGIT_GRID.isValid(new Location(2, 2)), "location should be valid");
    assertTrue(DIGIT_GRID.isValid(new Location(0, 0)));
    assertFalse(DIGIT_GRID.isValid(new Location(5, 2)), "location should not be valid");
    assertFalse(DIGIT_GRID.isValid(new Location(3, 4)));
    assertFalse(DIGIT_GRID.isValid(new Location(-1, -1)));

    assertTrue(NUMBER_GRID.isValid(new Location(2, 1)));
    assertTrue(NUMBER_GRID.isValid(new Location(0, 0)));
    assertFalse(NUMBER_GRID.isValid(new Location(5, 2)));
    assertFalse(NUMBER_GRID.isValid(new Location(3, 3)));
    assertFalse(NUMBER_GRID.isValid(new Location(-1, -1)));
  }

  @Test
  void testFindFirst() {
    assertEquals(null, DIGIT_GRID.findFirst(32));
    assertEquals(new Location(0, 1), DIGIT_GRID.findFirst(3));
    assertEquals(new Location(1, 0), NUMBER_GRID.findFirst(89));
  }

  @Test
  void testFindAll() {
    assertEquals(Stream.empty().toList(), DIGIT_GRID.findAll(23).toList());
    assertEquals(
        List.of(new Location(0, 3), new Location(1, 3), new Location(2, 3)),
        DIGIT_GRID.findAll(5).toList());
    assertEquals(List.of(new Location(1, 2), new Location(2, 2)), NUMBER_GRID.findAll(5).toList());
  }

  @Test
  void testLocationCount() {
    assertEquals(12, DIGIT_GRID.locations().count(), "should have 12 lcoations");

    assertEquals(9, NUMBER_GRID.locations().count());
  }

  @Test
  void testGetElement() {
    assertEquals(0, DIGIT_GRID.get(new Location(0, 0)));
    assertEquals(4, DIGIT_GRID.get(new Location(0, 2)));
    assertEquals(9, DIGIT_GRID.get(new Location(1, 1)));
    assertEquals(5, DIGIT_GRID.get(new Location(2, 3)));

    assertEquals(0, NUMBER_GRID.get(new Location(0, 0)));
    assertEquals(45, NUMBER_GRID.get(new Location(0, 2)));
    assertEquals(7, NUMBER_GRID.get(new Location(1, 1)));
    assertEquals(5, NUMBER_GRID.get(new Location(2, 2)));
  }

  @Test
  void testPutElement() {
    assertEquals(0, DIGIT_GRID.put(new Location(0, 0), 6));
    assertEquals(6, DIGIT_GRID.get(new Location(0, 0)));

    assertEquals(45, NUMBER_GRID.put(new Location(0, 2), 64));
    assertEquals(64, NUMBER_GRID.get(new Location(0, 2)));
  }

  @Test
  void testMainNeighbours() {
    assertEquals(
        Set.of(new Location(0, 1), new Location(1, 0)),
        DIGIT_GRID.mainNeighbours(new Location(0, 0)));
    assertEquals(
        Set.of(new Location(0, 1), new Location(1, 0), new Location(1, 2), new Location(2, 1)),
        DIGIT_GRID.mainNeighbours(new Location(1, 1)));
  }

  @Test
  void testSameNeighbours() {
    assertEquals(
        Set.of(new Location(0, 3), new Location(2, 3)),
        DIGIT_GRID.sameNeighbours(new Location(1, 3)));
  }

  @Test
  void testAllNeighbours() {
    assertEquals(
        Set.of(new Location(0, 1), new Location(1, 0), new Location(1, 1)),
        DIGIT_GRID.allNeighbours(new Location(0, 0)));
    assertEquals(
        Set.of(
            new Location(0, 1),
            new Location(1, 0),
            new Location(0, 0),
            new Location(0, 2),
            new Location(2, 0),
            new Location(2, 2),
            new Location(2, 1),
            new Location(1, 2)),
        DIGIT_GRID.allNeighbours(new Location(1, 1)));
  }

  @Test
  void testManhattanDistance() {
    assertEquals(
        Set.of(new Location(0, 0), new Location(0, 1), new Location(1, 0)),
        DIGIT_GRID.manhattanDistance(new Location(0, 0), 1));
    assertEquals(
        Set.of(
            new Location(1, 1),
            new Location(0, 1),
            new Location(1, 0),
            new Location(1, 2),
            new Location(2, 1)),
        DIGIT_GRID.manhattanDistance(new Location(1, 1), 1));
  }
}
