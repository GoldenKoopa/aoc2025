package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.goldenkoopa.aoc.y2025.utils.Location;
import eu.goldenkoopa.aoc.y2025.utils.geometry.Line;
import org.junit.jupiter.api.Test;

public class LineTest {

  @Test
  public void testConstructorWithSlopeAndIntercept() {
    Line line = new Line(2, 3);
    assertEquals(2.0, line.getM());
    assertEquals(3.0, line.getB());
  }

  @Test
  public void testConstructorWithTwoLocations() {
    Location a = new Location(1, 1);
    Location b = new Location(3, 3);
    Line line = new Line(a, b); // y = x

    assertEquals(1.0, line.getM());
    assertEquals(0.0, line.getB());
  }

  @Test
  public void testConstructorWithVerticalLineThrowsException() {
    Location a = new Location(0, 1);
    Location b = new Location(5, 1);
    assertThrows(IllegalArgumentException.class, () -> new Line(a, b));
  }

  @Test
  public void testGetY() {
    Line line = new Line(2.0, 1.0); // y = 2x + 1
    assertEquals(5.0, line.getY(2.0));
    assertEquals(-1.0, line.getY(-1.0));
  }

  @Test
  public void testGetX() {
    Line line = new Line(2.0, 1.0); // y = 2x + 1
    assertEquals(2.0, line.getX(5.0));
    assertEquals(-1.0, line.getX(-1.0));
  }

  @Test
  public void testGetXThrowsForHorizontalLine() {
    Line horizontal = new Line(0.0, 3.0); // y = 3
    assertThrows(ArithmeticException.class, () -> horizontal.getX(5.0));
  }

  @Test
  public void testPerpendicularLine() {
    Line l1 = new Line(1.0, 0.0); // y = x
    Location p = new Location(2, 0);
    Line perp = l1.perpendicularThrough(p); // y = -x + 2

    assertEquals(-1.0, perp.getM());
    assertEquals(2.0, perp.getB());
  }

  @Test
  public void testPerpendicularToHorizontalThrows() {
    Line l1 = new Line(0.0, 5.0); // y = 5
    Location p = new Location(1, 1);
    assertThrows(IllegalArgumentException.class, () -> l1.perpendicularThrough(p));
  }

  @Test
  public void testIsPerpendicularTo() {
    Line l1 = new Line(2.0, 1.0); // y = 2x + 1
    Line l2 = new Line(-0.5, 3.0); // y = -0.5x + 3
    assertTrue(l1.isPerpendicularTo(l2));
    assertTrue(l2.isPerpendicularTo(l1));
  }

  @Test
  public void testIsParallelTo() {
    Line l1 = new Line(3.0, 5.0); // y = 3x + 5
    Line l2 = new Line(3.0, 1.0); // y = 3x + 1
    assertTrue(l1.isParallelTo(l2));
  }

  @Test
  public void testEquals() {
    Line l1 = new Line(1.0, 2.0); // y = x + 2
    Line l2 = new Line(1.0, 2.0); // y = x + 2
    Line l3 = new Line(2.0, 1.0); // y = 2x + 1

    assertEquals(l1, l2);
    assertNotEquals(l1, l3);
  }

  @Test
  public void testToStringFormat() {
    Line l1 = new Line(2.12345, 5.9876);
    String expected = "y = 2.123 * x + 5.988";
    assertEquals(expected, l1.toString());
  }
}
