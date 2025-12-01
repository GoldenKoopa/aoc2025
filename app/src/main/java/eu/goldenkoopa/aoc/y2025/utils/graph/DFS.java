package eu.goldenkoopa.aoc.y2025.utils.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

class DFS {

  static <T> List<List<Vertex<T>>> run(
      Graph<T> graph, Predicate<T> filter, Consumer<T> consumer, Vertex<T> start, Vertex<T> end) {

    List<List<Vertex<T>>> result = new ArrayList<>();

    dfs(graph, filter, consumer, start, end, new ArrayList<>(), result);

    return result;
  }

  private static <T> void dfs(
      Graph<T> graph,
      Predicate<T> filter,
      Consumer<T> consumer, 
      Vertex<T> position,
      Vertex<T> end,
      List<Vertex<T>> currentPath,
      List<List<Vertex<T>>> validPaths) {

    Set<Vertex<T>> neighbours = graph.getAdjacentVertices(position);
    List<Vertex<T>> validNeighbours =
        neighbours.stream().filter(e -> filter.test(e.label())).toList();
    for (Vertex<T> vertex : validNeighbours) {
      currentPath.add(vertex);
      if (vertex.equals(end)) {
        validPaths.add(new ArrayList<>(currentPath));
        currentPath.removeLast();
        continue;
      }

      consumer.accept(vertex.label());
      dfs(graph, filter, consumer, vertex, end, new ArrayList<>(currentPath), validPaths);
      consumer.accept(vertex.label());
    }
  }
}
