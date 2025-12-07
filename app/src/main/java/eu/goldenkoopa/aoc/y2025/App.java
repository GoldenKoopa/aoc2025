package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.utils.ColorFormat;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

  private static final Logger LOGGER = Logger.getLogger(App.class.getTypeName());
  private static final String URL = "https://adventofcode.com/%d/day/%d";
  private static final String INPUT_URL = URL + "/input";
  private static final String SESSION_FILE_NAME = "session.txt";
  private static final String SESSION_COOKIE = loadSessionCookie();
  private static final int YEAR = 2025;

  public static void main(String[] args) {
    int day;
    if (args.length > 0) {
      day = Integer.valueOf(args[0]);
    } else {
      // default to most recent available day independent of actual timezone
      day = ZonedDateTime.now(ZoneId.of("America/New_York")).getDayOfMonth();
    }

    run(YEAR, day);
    System.out.println();
    // benchmarkParts(YEAR, day, 20);
  }

  private static void run(int year, int day) {
    String[] input = getInput(year, day);
    Day dayInstance = getDayInstance(day);

    System.out.println(
        ColorFormat.CYAN + "======== Day " + day + " (" + year + ") ========" + ColorFormat.RESET);

    runPart("Part 1", () -> dayInstance.part1(Arrays.copyOf(input, input.length)));
    System.out.println();
    runPart("Part 2", () -> dayInstance.part2(Arrays.copyOf(input, input.length)));
  }

  private static void runPart(String part, Supplier<String> partFunction) {
    System.out.println(ColorFormat.YELLOW + "----- " + part + " -----" + ColorFormat.RESET);
    try {
      long start = System.nanoTime();
      String result = partFunction.get();
      long elapsed = System.nanoTime() - start;
      System.out.println("" + ColorFormat.GREEN + result + ColorFormat.RESET);
      System.out.printf(
          ColorFormat.PURPLE + "Time: %.3f ms" + ColorFormat.RESET + "%n", elapsed / 1_000_000.0);
    } catch (Exception e) {
      System.out.println(ColorFormat.RED + "Error: " + e.getMessage() + ColorFormat.RESET);
      e.printStackTrace();
    }
  }

  public static void benchmarkParts(int year, int day, int runs) {
    String[] input = getInput(year, day);
    Day dayInstance = getDayInstance(day);

    System.out.println(
        ColorFormat.CYAN
            + "======== Benchmark Day "
            + day
            + " ("
            + year
            + ") ========"
            + ColorFormat.RESET);

    benchmarkPart("Part 1", () -> dayInstance.part1(Arrays.copyOf(input, input.length)), runs);
    System.out.println();
    benchmarkPart("Part 2", () -> dayInstance.part2(Arrays.copyOf(input, input.length)), runs);
  }

  private static void benchmarkPart(String part, Supplier<String> partFunction, int runs) {
    System.out.println(
        ColorFormat.YELLOW + "----- Benchmark " + part + " -----" + ColorFormat.RESET);

    // Warmup run (discarded)
    try {
      partFunction.get();
    } catch (Exception e) {
      System.out.println(ColorFormat.RED + "Warmup error: " + e.getMessage() + ColorFormat.RESET);
      return;
    }

    double total = 0;
    String result = null;
    for (int i = 0; i < runs; i++) {
      try {
        long start = System.nanoTime();
        result = partFunction.get();
        long elapsed = System.nanoTime() - start;
        total += elapsed / 1_000_000.0; // ms
      } catch (Exception e) {
        System.out.println(ColorFormat.RED + "Error: " + e.getMessage() + ColorFormat.RESET);
        return;
      }
    }

    double avg = total / runs;
    System.out.println(ColorFormat.GREEN + "Result: " + result + ColorFormat.RESET);
    System.out.printf(
        ColorFormat.PURPLE
            + "Average Time: %.3f ms (%d runs, 1st discarded)"
            + ColorFormat.RESET
            + "%n",
        avg,
        runs);
  }

  private static Day getDayInstance(int day) {
    String dayString = Integer.toString(day);
    if (day < 10) {
      dayString = "0" + dayString;
    }
    String dayClassName = String.format("eu.goldenkoopa.aoc.y2025.Day%s", dayString);

    try {
      Class<?> dayClass = Class.forName(dayClassName);
      if (!Day.class.isAssignableFrom(dayClass)) {
        throw new IllegalArgumentException(
            dayClassName + " does not implement eu.goldenkoopa.aoc.y2025.Day");
      }
      return (Day) dayClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      System.err.println(
          "Failed to load "
              + dayClassName
              + " class. Did you remember to create it and implement the Day interface?");
      System.exit(-1);
    }

    return null;
  }

  private static String[] getInput(int year, int day) {
    String dayString = Integer.toString(day);
    if (day < 10) {
      dayString = "0" + dayString;
    }
    Path inputPath = getResourceFolder().resolve("input/" + dayString + ".input");
    if (!Files.exists(inputPath)) {
      downloadInput(year, day);
    }

    try {
      String input = Files.readString(inputPath);
      return input.split("\n");
    } catch (IOException e) {
      throw new RuntimeException("input file read failed", e);
    }
  }

  private static void downloadInput(int year, int day) {
    URI uri;
    try {
      uri = new URI(String.format(INPUT_URL, year, day));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Failed to build URI", e);
    }

    HttpRequest req = HttpRequest.newBuilder().uri(uri).header("cookie", SESSION_COOKIE).build();

    HttpClient client = HttpClient.newHttpClient();

    HttpResponse<String> res;
    try {
      res = client.send(req, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("HTTP Request failed", e);
    }

    if (res.statusCode() != 200) {
      throw new RuntimeException(
          "Failed to fetch input: HTTP " + res.statusCode() + " - " + res.body());
    }

    String input = res.body();
    String dayString = Integer.toString(day);
    if (day < 10) {
      dayString = "0" + dayString;
    }
    try {
      Files.writeString(
          getResourceFolder().resolve("input/" + dayString + ".input"),
          input,
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static String loadSessionCookie() {
    Path sessionPath = getResourceFolder().resolve(SESSION_FILE_NAME);
    if (!Files.exists(sessionPath)) {
      try {
        Files.createFile(sessionPath);
        Files.writeString(sessionPath, "session=XXXXXX");
        LOGGER.log(Level.INFO, "session.txt created. edit cookie information accordingly");
      } catch (IOException e) {
        throw new RuntimeException("session file creation failed", e);
      }
    }

    try {
      String sessionCookie = Files.readString(sessionPath).trim();
      if (sessionCookie.endsWith("XXXX")) {
        LOGGER.log(
            Level.SEVERE,
            "session cookie not set. please edit 'session.txt' in the resources directory");
        System.exit(-1);
      }
      return sessionCookie;
    } catch (IOException e) {
      throw new RuntimeException("session file read failed", e);
    }
  }

  private static Path getResourceFolder() {
    return Path.of("src", "main", "resources");
  }
}
