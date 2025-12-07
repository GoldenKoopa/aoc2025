package eu.goldenkoopa.aoc.y2025.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ObjectGrid<T> implements Grid<T> {

  protected T[][] grid;

  protected ObjectGrid() {}

  @SuppressWarnings("unchecked")
  public ObjectGrid(int rows, int columns, T value) {
    grid = (T[][]) new Object[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        grid[i][j] = value;
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> ObjectGrid<T> parse(
      String[] input, Function<String, T> elementParser, char delimiter) {
    ObjectGrid<T> grid = new ObjectGrid<>();
    String delimiterRegex = Pattern.quote(String.valueOf(delimiter)) + "+";
    grid.grid =
        Arrays.stream(input)
            .map(
                line ->
                    Arrays.stream(line.split(delimiterRegex))
                        .filter(line1 -> !line1.isEmpty())
                        .map(elementParser)
                        .toArray(size -> (T[]) new Object[size]))
            .toArray(size -> (T[][]) new Object[size][]);
    return grid;
  }

  @SuppressWarnings("unchecked")
  public static <T> ObjectGrid<T> parseChars(String[] input, Function<Character, T> charParser) {
    ObjectGrid<T> grid = new ObjectGrid<>();
    grid.grid =
        Arrays.stream(input)
            .map(
                line ->
                    line.chars()
                        .mapToObj(c -> charParser.apply((char) c))
                        .toArray(size -> (T[]) new Object[size]))
            .toArray(size -> (T[][]) new Object[size][]);
    return grid;
  }

  @Override
  public int rows() {
    return grid.length;
  }

  @Override
  public int cols() {
    return grid[0].length;
  }

  @Override
  public boolean isValid(Location p) {
    return p.row() >= 0 && p.row() < grid.length && p.column() >= 0 && p.column() < grid[0].length;
  }

  @Override
  public Stream<Location> locations() {
    return Stream.iterate(
        new Location(0, 0),
        this::isValid,
        loc ->
            loc.column() < this.cols() - 1
                ? new Location(loc.row(), loc.column() + 1)
                : new Location(loc.row() + 1, 0));
  }

  @Override
  public Stream<Location> locationsRow(int row) {
    return Stream.iterate(
        new Location(row, 0), this::isValid, loc -> new Location(loc.row(), loc.column() + 1));
  }

  @Override
  public Stream<Location> locationsColumn(int column) {
    return Stream.iterate(
        new Location(0, column), this::isValid, loc -> new Location(loc.row() + 1, loc.column()));
  }

  @Override
  public T get(Location p) {
    return grid[p.row()][p.column()];
  }

  @Override
  public T put(Location p, T e) {
    T old = get(p);
    grid[p.row()][p.column()] = e;
    return old;
  }

  @Override
  public Set<Location> mainNeighbours(Location loc) {
    Set<Location> result = new HashSet<>();
    for (Direction d : Direction.MAIN_DIRECTIONS) {
      Location newLoc = loc.moved(d);
      if (isValid(newLoc)) result.add(newLoc);
    }
    return result;
  }

  @Override
  public Set<Location> sameNeighbours(Location loc) {
    T value = get(loc);
    Set<Location> res = new HashSet<>();
    for (Direction dir : Direction.MAIN_DIRECTIONS) {
      Location newLoc = loc.moved(dir);
      if (isValid(newLoc) && Objects.equals(get(newLoc), value)) {
        res.add(newLoc);
      }
    }
    return res;
  }

  @Override
  public Set<Location> allNeighbours(Location loc) {
    Set<Location> result = new HashSet<>();
    for (Direction d : Direction.values()) {
      Location newLoc = loc.moved(d);
      if (isValid(newLoc)) result.add(newLoc);
    }
    return result;
  }

  @Override
  public Set<Direction> mainNeighbourDirections(Location loc) {
    Set<Direction> result = new HashSet<>();
    for (Direction d : Direction.MAIN_DIRECTIONS) {
      Location newLoc = loc.moved(d);
      if (isValid(newLoc)) result.add(d);
    }
    return result;
  }

  @Override
  public Set<Direction> allNeighbourDirections(Location loc) {
    Set<Direction> result = new HashSet<>();
    for (Direction d : Direction.values()) {
      Location newLoc = loc.moved(d);
      if (isValid(newLoc)) result.add(d);
    }
    return result;
  }

  @Override
  public Set<Location> manhattanDistance(Location p, int distance) {
    Set<Location> res = new HashSet<>();
    for (int i = -distance; i <= distance; i++) {
      for (int j = -(distance - Math.abs(i)); j <= distance - Math.abs(i); j++) {
        Location loc = p.movedBy(i, j);
        if (isValid(loc)) res.add(loc);
      }
    }
    return res;
  }

  @Override
  public String toString() {
    if (grid == null || grid.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        sb.append(String.valueOf(grid[i][j]));
        if (j < grid[i].length - 1) {
          sb.append(' ');
        }
      }
      if (i < grid.length - 1) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  public String toString(Predicate<Location> predicate) {
    if (grid == null || grid.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (predicate.test(new Location(i, j))) {
          sb.append(ColorFormat.RED_BOLD + String.valueOf(grid[i][j]) + ColorFormat.RESET);
        } else {
          sb.append(String.valueOf(grid[i][j]));
        }
        if (j < grid[i].length - 1) {
          sb.append(' ');
        }
      }
      if (i < grid.length - 1) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  public String toString(Predicate<Location> predicate, Predicate<Location> predicate2) {
    if (grid == null || grid.length == 0) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (predicate.test(new Location(i, j))) {
          sb.append(ColorFormat.RED_BOLD + String.valueOf(grid[i][j]) + ColorFormat.RESET);
        } else if (predicate2.test(new Location(i, j))) {
          sb.append(ColorFormat.BLUE_BOLD + String.valueOf(grid[i][j]) + ColorFormat.RESET);
        } else {
          sb.append(String.valueOf(grid[i][j]));
        }
        if (j < grid[i].length - 1) {
          sb.append(' ');
        }
      }
      if (i < grid.length - 1) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  @Override
  public Location findFirst(T e) {
    return findAll(e).findFirst().orElse(null);
  }

  @Override
  public Stream<Location> findAll(T e) {
    return locations().filter(f -> Objects.equals(get(f), e));
  }

  @Override
  public Location findFirstRow(T e, int row) {
    return findAllRow(e, row).findFirst().orElse(null);
  }

  @Override
  public Location findFirstColumn(T e, int column) {
    return findAllColumn(e, column).findFirst().orElse(null);
  }

  @Override
  public Stream<Location> findAllRow(T e, int row) {
    return locationsRow(row).filter(f -> Objects.equals(get(f), e));
  }

  @Override
  public Stream<Location> findAllColumn(T e, int column) {
    return locationsColumn(column).filter(f -> Objects.equals(get(f), e));
  }
}
