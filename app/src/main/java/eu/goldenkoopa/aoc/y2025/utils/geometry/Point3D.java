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

  public int getDistanceSquared(Point3D other) {
    int dx = Math.abs(this.x - other.x);
    int dy = Math.abs(this.y - other.y);
    int dz = Math.abs(this.z - other.z);
    return dx * dx + dy * dy + dz * dz;
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
