package eu.goldenkoopa.aoc.y2025;

import eu.goldenkoopa.aoc.y2025.utils.geometry.Point3D;
import eu.goldenkoopa.aoc.y2025.utils.graph.Graph;
import eu.goldenkoopa.aoc.y2025.utils.graph.Vertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * --- Day 8: Playground --- <br>
 * https://adventofcode.com/2025/day/8
 */
public class Day08 implements Day {

  String[] testInput =
      new String[] {
        "162,817,812",
        "57,618,57",
        "906,360,560",
        "592,479,940",
        "352,342,300",
        "466,668,158",
        "542,29,236",
        "431,825,988",
        "739,650,466",
        "52,470,668",
        "216,146,977",
        "819,987,18",
        "117,168,530",
        "805,96,715",
        "346,949,466",
        "970,615,88",
        "941,993,340",
        "862,61,35",
        "984,92,344",
        "425,690,689",
      };

  @Override
  public String part1(String[] input) {

    PriorityQueue<Map.Entry<Point3D[], Integer>> sorted = getSorted(input);

    Graph<Point3D> graph = new Graph<>();

    for (int i = 0; i < 1000; i++) {
      Map.Entry<Point3D[], Integer> x = sorted.poll();
      graph.addEdge(new Vertex<>(x.getKey()[0]), new Vertex<>(x.getKey()[1]));
    }

    List<Set<Vertex<Point3D>>> groups = graph.getGroups();

    int sum =
        groups.stream()
            .map(Set::size)
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .reduce(1, (a, b) -> a * b);

    return "" + sum;
  }

  @Override
  public String part2(String[] input) {

    int lengthGoal = input.length;

    PriorityQueue<Map.Entry<Point3D[], Integer>> sorted = getSorted(input);

    Map<Point3D, Set<Point3D>> map = new HashMap<>();

    List<Set<Point3D>> groups = new ArrayList<>();

    Point3D point1 = null;
    Point3D point2 = null;
    int i = 0;
    while (!(groups.size() == 1 && groups.get(0).size() == lengthGoal) && i < sorted.size()) {
      Map.Entry<Point3D[], Integer> x = sorted.poll();
      point1 = x.getKey()[0];
      point2 = x.getKey()[1];
      i++;
      Set<Point3D> group1 = map.get(point1);
      Set<Point3D> group2 = map.get(point2);
      if (group1 == null && group2 == null) {
        Set<Point3D> newGroup = new HashSet<>();
        newGroup.add(point1);
        newGroup.add(point2);
        groups.add(newGroup);
        map.put(point1, newGroup);
        map.put(point2, newGroup);
        continue;
      }
      if (group1 == null) {
        group2.add(point1);
        map.put(point1, group2);
        continue;
      }
      if (group2 == null) {
        group1.add(point2);
        map.put(point2, group1);
        continue;
      }
      if (group1.equals(group2)) {
        continue;
      }
      group2.forEach(p -> map.put(p, group1));
      group1.addAll(group2);
      groups.remove(group2);
    }

    return "" + point1.getX() * point2.getX();
  }

  private PriorityQueue<Map.Entry<Point3D[], Integer>> getSorted(String[] input) {
    Point3D[] junctionBoxes =
        Arrays.stream(input)
            .map(
                l -> {
                  String[] parts = l.split(",");
                  return new Point3D(
                      Integer.parseInt(parts[0]),
                      Integer.parseInt(parts[1]),
                      Integer.parseInt(parts[2]));
                })
            .toArray(Point3D[]::new);

    Map<Point3D[], Integer> distances = new HashMap<>();

    for (int i = 0; i < junctionBoxes.length - 1; i++) {
      for (int j = i + 1; j < junctionBoxes.length; j++) {
        distances.put(
            new Point3D[] {junctionBoxes[i], junctionBoxes[j]},
            junctionBoxes[i].getDistanceSquared(junctionBoxes[j]));
      }
    }
    PriorityQueue<Map.Entry<Point3D[], Integer>> queue =
        new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

    distances.entrySet().forEach(queue::add);
    return queue;
  }
}
