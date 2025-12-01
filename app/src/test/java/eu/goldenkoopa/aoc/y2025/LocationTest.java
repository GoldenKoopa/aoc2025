package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import eu.goldenkoopa.aoc.y2025.utils.Direction;
import eu.goldenkoopa.aoc.y2025.utils.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationTest {

  Location testLocation1;
  Location testLocation2;

  @BeforeEach
  void setUp() {
    testLocation1 = new Location(0, 0);
    testLocation2 = new Location(0, 1);
  }

  @Test
  void testMovedBy() {
    assertEquals(new Location(4, -3), testLocation1.movedBy(4, -3));
    assertEquals(new Location(5, 2), testLocation2.movedBy(5, 1));
    assertEquals(new Location(-1, -1), testLocation1.movedBy(-1, -1));
  }

  @Test
  void testMoved() {
    assertEquals(new Location(1, 1), testLocation1.moved(Direction.SE));
    assertEquals(new Location(-1, 0), testLocation1.moved(Direction.N));
    assertEquals(new Location(0, 2), testLocation2.moved(Direction.E));
  }

  @Test
  void testTo() {
    assertEquals(Direction.S, testLocation1.to(new Location(1, 0)));
    assertEquals(Direction.NW, testLocation2.to(new Location(-1, 0)));
    assertEquals(Direction.E, testLocation1.to(testLocation2));

    assertEquals(Direction.W, testLocation1.to(testLocation1.moved(Direction.W)));

    assertNull(testLocation2.to(new Location(10, 5)));
  }

  @Test
  void testManhattenDistance() {
    assertEquals(1, testLocation1.manhattenDistance(testLocation2));
    assertEquals(6, testLocation1.manhattenDistance(new Location(4, 2)));
    assertEquals(345, testLocation2.manhattenDistance(new Location(264, 82)));
  }

  @Test
  void testCompareTo() {
    assertEquals(-5, testLocation1.compareTo(new Location(5, 0)));
    assertEquals(-5, testLocation1.compareTo(new Location(0, 5)));
    assertEquals(-5, testLocation1.compareTo(new Location(5, 2)));
    assertEquals(-2, testLocation1.compareTo(new Location(2, 5)));
  }

  @Test
  void testToString() {
    assertEquals("P(0,0)", testLocation1.toString());
    assertEquals("P(0,1)", testLocation2.toString());
  }
}
