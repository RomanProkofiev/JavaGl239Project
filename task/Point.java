package task;


import static java.lang.Math.*;



public class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final double lengthToNextPoint(Point p2) {
        return sqrt(pow(this.x - p2.x, 2) + pow(this.y - p2.y, 2));
    }
}
