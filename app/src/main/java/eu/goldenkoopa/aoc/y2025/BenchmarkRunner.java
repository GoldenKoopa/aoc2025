package eu.goldenkoopa.aoc.y2025;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BenchmarkRunner {

  public static void main(String[] args) {
    int year = Integer.parseInt(args[0]);
    int day = Integer.parseInt(args[1]);
    int runs = Integer.parseInt(args[2]);

    if (day == -1) {
      day = ZonedDateTime.now(ZoneId.of("America/New_York")).getDayOfMonth();
    }
    App.benchmarkParts(year, day, runs);
  }
}
