package eu.goldenkoopa.aoc.y2025.utils.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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

  public List<Set<Vertex<T>>> getGroups() {
    Queue<Vertex<T>> remainingVertices = new LinkedList<>(adjacencyList.keySet());

    List<Set<Vertex<T>>> groups = new ArrayList<>();
    while (!remainingVertices.isEmpty()) {
      // System.out.println(remainingVertices);
      Vertex<T> vertex = remainingVertices.remove();
      Set<Vertex<T>> group = new HashSet<>(adjacencyList.get(vertex));
      List<Vertex<T>> verticesToCheck = new ArrayList<>(adjacencyList.get(vertex));

      int i = 0;
      while (i < verticesToCheck.size()) {
        Set<Vertex<T>> c = adjacencyList.get(verticesToCheck.get(i));
        group.addAll(c);
        for (Vertex<T> elem : c) {
          if (!verticesToCheck.contains(elem)) {
            verticesToCheck.add(elem);
          }
        }
        remainingVertices.remove(verticesToCheck.get(i));
        i++;
      }

      groups.add(group);
    }

    return groups;
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
