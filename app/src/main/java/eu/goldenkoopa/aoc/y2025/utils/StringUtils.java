package eu.goldenkoopa.aoc.y2025.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

  public static String[][] splitByEmptyLines(String[] input) {
    List<List<String>> groups = new ArrayList<>();
    List<String> currentGroup = new ArrayList<>();

    for (String line : input) {
      if (line.trim().isEmpty()) {
        if (!currentGroup.isEmpty()) {
          groups.add(new ArrayList<>(currentGroup));
          currentGroup.clear();
        }
      } else {
        currentGroup.add(line);
      }
    }

    if (!currentGroup.isEmpty()) {
      groups.add(new ArrayList<>(currentGroup));
    }

    String[][] res = new String[groups.size()][];
    for (int i = 0; i < groups.size(); i++) {
      res[i] = groups.get(i).toArray(String[]::new);
    }

    return res;
  }
}
