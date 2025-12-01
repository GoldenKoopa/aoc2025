package eu.goldenkoopa.aoc.y2025;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.goldenkoopa.aoc.y2025.utils.graph.Graph;
import eu.goldenkoopa.aoc.y2025.utils.graph.Vertex;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphTest {

  private Graph<String> graph;
  private Vertex<String> a, b, c, d;

  @BeforeEach
  void setup() {
    graph = new Graph<>();
    a = new Vertex<>("a");
    b = new Vertex<>("b");
    c = new Vertex<>("c");
    d = new Vertex<>("d");
  }

  @Test
  void testAddVertexAndGetAdjacent() {
    graph.addVertex(a);
    assertNotNull(graph.getAdjacentVertices(a));
    assertTrue(graph.getAdjacentVertices(a).isEmpty());
  }

  @Test
  void testAddVerticesCollection() {
    List<Vertex<String>> vs = List.of(a, b, c);
    graph.addVertices(vs);
    assertNotNull(graph.getAdjacentVertices(a));
    assertNotNull(graph.getAdjacentVertices(b));
    assertFalse(graph.getAdjacentVertices(a) == null);
    assertNull(graph.getAdjacentVertices(d));
  }

  @Test
  void testAddEdge() {
    graph.addEdge(a, b);
    assertTrue(graph.getAdjacentVertices(a).contains(b));
    assertTrue(graph.getAdjacentVertices(b).contains(a));
    // Should not throw if repeated
    graph.addEdge(a, b);
    assertEquals(1, graph.getAdjacentVertices(a).size());
  }

  @Test
  void testRemoveEdge() {
    graph.addEdge(a, b);
    graph.removeEdge(a, b);
    assertFalse(graph.getAdjacentVertices(a).contains(b));
    assertFalse(graph.getAdjacentVertices(b).contains(a));
  }

  @Test
  void testRemoveVertex() {
    graph.addEdge(a, b);
    graph.addEdge(a, c);
    graph.removeVertex(a);
    assertNull(graph.getAdjacentVertices(a));
    assertFalse(graph.getAdjacentVertices(b).contains(a));
    assertFalse(graph.getAdjacentVertices(c).contains(a));
  }
}
