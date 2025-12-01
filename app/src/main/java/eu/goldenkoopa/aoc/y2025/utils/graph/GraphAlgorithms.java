package eu.goldenkoopa.aoc.y2025.utils.graph;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GraphAlgorithms {

  public static <T> List<List<Vertex<T>>> dfs(
      Graph<T> graph, Predicate<T> filter, Consumer<T> consumer, Vertex<T> start, Vertex<T> end) {
    return DFS.run(graph, filter, consumer, start, end);
  }
}
