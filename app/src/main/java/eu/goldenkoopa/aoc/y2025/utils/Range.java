package eu.goldenkoopa.aoc.y2025.utils;

public class Range {

  private long start;
  private long end;

  public Range(long start, long endExclusive) {
    this.start = start;
    this.end = endExclusive;
  }

  public long getStart() {
    return this.start;
  }

  public long getEnd() {
    return this.end;
  }

  public boolean contains(long num) {
    return start <= num && num < end;
  }

  public long count() {
    return this.end - this.start;
  }

  public boolean canMerge(Range otherRange) {
    return this.contains(otherRange.start)
        || this.contains(otherRange.end)
        || (this.end <= otherRange.end && this.start > otherRange.start);
  }

  public void merge(Range otherRange) {
    this.start = Math.min(this.start, otherRange.start);
    this.end = Math.max(this.end, otherRange.end);
  }

  public String toString() {
    return "Range[" + this.start + "," + this.end + "]";
  }
}
