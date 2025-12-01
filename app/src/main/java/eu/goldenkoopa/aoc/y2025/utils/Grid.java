package eu.goldenkoopa.aoc.y2025.utils;

import java.util.Set;
import java.util.stream.Stream;

public interface Grid<E> {

  int rows();

  int cols();

  boolean isValid(Location p);

  Stream<Location> locations();

  E get(Location p);

  /**
   * Sets element at location
   *
   * @param p the location
   * @param e the element to set
   * @return old element
   */
  E put(Location p, E e);

  Set<Location> mainNeighbours(Location p);

  Set<Location> sameNeighbours(Location p);

  Set<Location> allNeighbours(Location p);

  Set<Direction> mainNeighbourDirections(Location p);

  Set<Direction> allNeighbourDirections(Location p);

  /** Gets valid locations given distance away */
  Set<Location> manhattanDistance(Location p, int distance);

  Location findFirst(E e);

  Stream<Location> findAll(E e);

  String toString();
}
