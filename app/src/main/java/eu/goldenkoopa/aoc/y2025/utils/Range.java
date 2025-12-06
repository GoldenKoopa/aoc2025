package eu.goldenkoopa.aoc.y2025.utils;

public class Range {

  long start;
  long end;

  public Range(long start, long endExclusive) {
    this.start = start;
    this.end = endExclusive;
  }

  public boolean contains(long num) {
    return start <= num && num < end;
  }
}
