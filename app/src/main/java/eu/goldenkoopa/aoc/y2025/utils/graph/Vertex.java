package eu.goldenkoopa.aoc.y2025.utils.graph;

import java.util.Objects;

public record Vertex<T>(T label) {

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Vertex<?>)) {
      return false;
    }

    Vertex<?> other = (Vertex<?>) obj;

    return Objects.equals(this.label, other.label);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.label);
  }

  @Override
  public String toString() {
    return label.toString();
  }
}
