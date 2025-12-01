package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.goldenkoopa.aoc.y2025.utils.ObjectGrid;
import eu.goldenkoopa.aoc.y2025.utils.grid.GridAlgorithms;
import org.junit.jupiter.api.Test;

class BingoTest {

  static final String[] INPUT1 = new String[] {"0 -1 0 -1 0", "-1 0 0 -1 0", "-1 0 -1 -1 0"};
  static final String[] INPUT2 = new String[] {"0 -1 0 0 0", "-1 0 0 -1 0", "-1 0 -1 -1 0"};
  static final String[] INPUT3 = new String[] {"-1 -1 -1 -1 -1", "-1 0 0 0 0", "-1 0 -1 -1 0"};

  @Test
  void testBingo() {

    assertTrue(
        GridAlgorithms.hasBingo(
            ObjectGrid.parse(INPUT1, TestClass::from, ' '), TestClass::isMarked),
        "test column");
    assertFalse(
        GridAlgorithms.hasBingo(
            ObjectGrid.parse(INPUT2, TestClass::from, ' '), TestClass::isMarked),
        "test none");
    assertTrue(
        GridAlgorithms.hasBingo(
            ObjectGrid.parse(INPUT3, TestClass::from, ' '), TestClass::isMarked),
        "test row");
  }

  private static class TestClass {

    private int integer;

    public TestClass(int integer) {
      this.integer = integer;
    }

    public boolean isMarked() {
      return integer < 0;
    }

    public static TestClass from(String string) {
      return new TestClass(Integer.parseInt(string));
    }
  }
}
