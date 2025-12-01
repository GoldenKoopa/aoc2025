package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.goldenkoopa.aoc.y2025.utils.Location;
import eu.goldenkoopa.aoc.y2025.utils.geometry.LineSegment;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class LineSegmentTest {

  @Test
  public void testGetters() {
    Location a = new Location(2, 3);
    Location b = new Location(4, 7);
    LineSegment segment = new LineSegment(a, b);

    assertEquals(a, segment.getStart());
    assertEquals(b, segment.getEnd());
  }

  @Test
  public void testLength() {
    LineSegment segment = new LineSegment(new Location(0, 0), new Location(3, 4));
    assertEquals(5.0, segment.length(), 1e-9); // 3-4-5 triangle
  }

  @Test
  public void testMidpoint() {
    LineSegment segment = new LineSegment(new Location(0, 0), new Location(4, 6));
    Location midpoint = segment.midpoint();
    assertEquals(new Location(2, 3), midpoint);
  }

  @Test
  public void testSlope() {
    LineSegment segment = new LineSegment(new Location(2, 2), new Location(4, 4));
    assertEquals(1.0, segment.slope(), 1e-9);
  }

  @Test
  public void testSlope_ThrowsForVertical() {
    LineSegment segment = new LineSegment(new Location(1, 5), new Location(8, 5));
    assertThrows(ArithmeticException.class, segment::slope);
  }

  @Test
  public void testIsHorizontal() {
    LineSegment segment = new LineSegment(new Location(5, 2), new Location(5, 10));
    assertTrue(segment.isHorizontal());
  }

  @Test
  public void testIsVertical() {
    LineSegment segment = new LineSegment(new Location(1, 3), new Location(10, 3));
    assertTrue(segment.isVertical());
  }

  @Test
  public void testAngleDegrees_45() {
    LineSegment segment = new LineSegment(new Location(0, 0), new Location(1, 1));
    assertEquals(45.0, segment.angleDegrees(), 1e-9);
  }

  @Test
  public void testAngleDegrees_0_Horizontal() {
    LineSegment segment = new LineSegment(new Location(1, 1), new Location(1, 5));
    assertEquals(0, segment.angleDegrees(), 1e-9);
  }

  @Test
  public void testAngleDegrees_90_Vertical() {
    LineSegment segment = new LineSegment(new Location(5, 5), new Location(2, 5));
    assertEquals(-90.0, segment.angleDegrees(), 1e-9); // Negative Y direction
  }

  @Test
  public void testAllTouchedLocations_Horizontal() {
    LineSegment segment = new LineSegment(new Location(2, 1), new Location(2, 4));

    Set<Location> expected =
        Set.of(new Location(2, 1), new Location(2, 2), new Location(2, 3), new Location(2, 4));

    assertEquals(expected, segment.allTouchedLocations());
  }

  @Test
  public void testAllTouchedLocations_Vertical() {
    LineSegment segment = new LineSegment(new Location(1, 3), new Location(4, 3));

    Set<Location> expected =
        Set.of(new Location(1, 3), new Location(2, 3), new Location(3, 3), new Location(4, 3));

    assertEquals(expected, segment.allTouchedLocations());
  }

  @Test
  public void testAllTouchedLocations_Diagonal() {
    LineSegment segment = new LineSegment(new Location(0, 0), new Location(3, 3));

    Set<Location> expected =
        Set.of(new Location(0, 0), new Location(1, 1), new Location(2, 2), new Location(3, 3));

    assertEquals(expected, segment.allTouchedLocations());
    LineSegment segment2 = new LineSegment(new Location(2, 2), new Location(4, 5));

    Set<Location> expected2 =
        Set.of(new Location(2, 2), new Location(3, 3), new Location(3, 4), new Location(4, 5));

    assertEquals(expected2, segment2.allTouchedLocations());
  }

  @Test
  public void testEquals_SameDirection() {
    LineSegment a = new LineSegment(new Location(0, 0), new Location(1, 1));
    LineSegment b = new LineSegment(new Location(0, 0), new Location(1, 1));
    assertEquals(a, b);
  }

  @Test
  public void testEquals_ReversedDirection() {
    LineSegment a = new LineSegment(new Location(0, 0), new Location(1, 1));
    LineSegment b = new LineSegment(new Location(1, 1), new Location(0, 0));
    assertEquals(a, b);
  }

  @Test
  public void testEquals_NotEqual() {
    LineSegment a = new LineSegment(new Location(0, 0), new Location(1, 1));
    LineSegment b = new LineSegment(new Location(1, 1), new Location(2, 2));
    assertNotEquals(a, b);
  }

  @Test
  public void testToString() {
    LineSegment segment = new LineSegment(new Location(3, 4), new Location(7, 9));
    assertEquals("Segment[(3,4) -> (7,9)]", segment.toString());
  }
}
