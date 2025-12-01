package eu.goldenkoopa.aoc.y2025;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DayAssembler {

  private static final String PACKAGE_NAME = "eu.goldenkoopa.aoc.y%d";
  private static final String DIRECTORY = "src/main/java/eu/goldenkoopa/aoc/y%d/";
  private static final String URL = "https://adventofcode.com/%d/day/%d";

  public static void main(String[] args) {
    assembleDay(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
  }

  private static void assembleDay(int year, int day) {
    if (day == -1) {
      day = ZonedDateTime.now(ZoneId.of("America/New_York")).getDayOfMonth();
    }

    DecimalFormat df = new DecimalFormat("00");
    String dayStr = df.format(day);
    String className = "Day" + dayStr;
    String fileName = className + ".java";
    Path filePath = Paths.get(String.format(DIRECTORY, year) + fileName);

    if (Files.exists(filePath)) {
      System.out.println("File already exists: " + filePath);
      return;
    }

    String classContent;
    try {
      String template;
      try (InputStream templateStream = DayAssembler.class.getResourceAsStream("/day-template.java")) {
        if (templateStream == null) {
          throw new IOException("Template file not found in resources");
        }

        template = new String(templateStream.readAllBytes(), StandardCharsets.UTF_8);
      }

      classContent = template
          .replace("${packageName}", String.format(PACKAGE_NAME, year))
          .replace("${url}", String.format(URL, year, day))
          .replace("${className}", className);

    } catch (IOException e) {
      System.err.println("Failed to read template: " + e.getMessage());
      return;
    }

    try {
      Files.createDirectories(Paths.get(String.format(DIRECTORY, year)));
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));
      writer.write(classContent);
      writer.close();
      System.out.println("Created: " + filePath);
    } catch (IOException e) {
      System.err.println("Failed to create file: " + e.getMessage());
    }
  }
}
