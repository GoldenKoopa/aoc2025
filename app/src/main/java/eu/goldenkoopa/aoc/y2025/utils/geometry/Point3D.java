package eu.goldenkoopa.aoc.y2025.utils.geometry;

public class Point3D {

  private int x;
  private int y;
  private int z;

  public Point3D(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double getDistance(Point3D other) {
    return Math.sqrt(
        Math.pow(Math.abs(this.x - other.x), 2)
            + Math.pow(Math.abs(this.y - other.y), 2)
            + Math.pow(Math.abs(this.z - other.z), 2));
  }

  public double getDistanceSquared(Point3D other) {
    return Math.pow(Math.abs(this.x - other.x), 2)
        + Math.pow(Math.abs(this.y - other.y), 2)
        + Math.pow(Math.abs(this.z - other.z), 2);
  }

  @Override
  public String toString() {
    return "Point[" + this.x + "," + this.y + "," + this.z + "]";
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }
}
