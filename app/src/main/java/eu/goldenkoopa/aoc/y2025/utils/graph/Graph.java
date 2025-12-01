package eu.goldenkoopa.aoc.y2025.utils.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph<T> {

  private Map<Vertex<T>, Set<Vertex<T>>> adjacencyList = new HashMap<>();

  public void addVertex(Vertex<T> vertex) {
    adjacencyList.putIfAbsent(vertex, new HashSet<>());
  }

  public void addVertices(Collection<Vertex<T>> vertices) {
    vertices.forEach(this::addVertex);
  }

  @SafeVarargs
  private final void addVertices(Vertex<T>... vertices) {
    Arrays.stream(vertices).forEach(this::addVertex);
  }

  public void removeVertex(Vertex<T> vertex) {
    adjacencyList.values().stream().forEach(e -> e.remove(vertex));
    adjacencyList.remove(vertex);
  }

  public void addEdge(Vertex<T> vertex1, Vertex<T> vertex2) {
    this.addVertices(vertex1, vertex2);
    adjacencyList.get(vertex1).add(vertex2);
    adjacencyList.get(vertex2).add(vertex1);
  }

  public void removeEdge(Vertex<T> vertex1, Vertex<T> vertex2) {
    this.adjacencyList.get(vertex1).remove(vertex2);
    this.adjacencyList.get(vertex2).remove(vertex1);
  }

  public Set<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
    return adjacencyList.getOrDefault(vertex, Set.of());
  }

  public Vertex<T> getVertex(T t) {
    for (Vertex<T> e : adjacencyList.keySet()) {
      if (e.label().equals(t)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Graph [\n");
    adjacencyList.forEach((k, v) -> sb.append("\t" + k + ":\t" + v.toString() + "\n"));

    sb.append("]");

    return sb.toString();
  }
}
