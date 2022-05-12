package task;


import java.util.ArrayList;
import java.util.List;


public class Circle {
    public final double radius;
    public final Point centre;

    public List<Point> points = new ArrayList<>();

    public Circle(Point point, Point point1) {
        this.centre = point;
        this.radius = point.lengthToNextPoint(point1);

        for (int i = 1; i < 2000; i++) {
            Point p;
            p = new Point((int) (Math.cos(2 * Math.PI * i / 2000) * this.radius + this.centre.x),
                    (int) (Math.sin(2 * Math.PI * i / 2000) * this.radius + this.centre.y));
            points.add(p);
        }
    }
}
