package task;


import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Shape {
    private Circle circle;
    private Angle angle;
    public Line line;

    public static List<Point> allShapesPoints;

    public Shape(Circle c, Angle a) {
        this.circle = c;
        this.angle = a;

        allShapesPoints = findPeaksOfShape();

        if (isPointInsideTheCircle(angle.top, circle)) {
            allShapesPoints.add(angle.top);
        }
        if (isPointInsideTheCircle(angle.versh1, circle)) {
            allShapesPoints.add(angle.versh1);
        }

        if (isPointInsideTheCircle(angle.versh2, circle)) {
            allShapesPoints.add(angle.versh2);
        }

        int count = 0;
        for (int i = 0; i < circle.points.size(); i++) {
            if (isInsideTheAngle(circle.points.get(i), angle)) {
                count++;
            }
        }

        if (count == circle.points.size()) {
            allShapesPoints.addAll(circle.points);
        }

        if (count == circle.points.size()) {
            allShapesPoints.addAll(circle.points);
        }
        try {
            line = new Line(allShapesPoints.get(0), allShapesPoints.get(allShapesPoints.size() - 1));
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }


    public Shape() {
    }

    private List<Point> findPeaksOfShape() {
        allShapesPoints = new ArrayList<>();

        double xc = this.circle.centre.x;
        double yc = this.circle.centre.y;

        assert this.angle.top.x != this.angle.versh1.x; // вставь меня!!!!

        double k = (this.angle.top.y - this.angle.versh1.y) / (this.angle.top.x - this.angle.versh1.x);
        double b = this.angle.top.y - k * this.angle.top.x;

        solveEquation(xc, yc, this.circle.radius, k, b);

        assert this.angle.top.x != this.angle.versh2.x; // вставь меня!!!

        k = (this.angle.top.y - this.angle.versh2.y) / (this.angle.top.x - this.angle.versh2.x);
        b = this.angle.top.y - k * this.angle.top.x;

        solveEquation(xc, yc, this.circle.radius, k, b);

        for (int i = 0; i < circle.points.size(); i++) {
            if (isInsideTheAngle(circle.points.get(i), angle)) {
                allShapesPoints.add(circle.points.get(i));
            }
        }

        return allShapesPoints;
    }

    private void solveEquation(double xc, double yc, double r, double k, double b) {
        double v = 1 + k * k;
        double w = 2 * xc - 2 * k * b + 2 * yc * k;
        double z = xc * xc + b * b + yc * yc - 2 * b * yc - r * r;

        double D = w * w - 4 * v * z;

        if (D >= 0) {
            double x1 = (w + Math.sqrt(D)) / (2 * v);
            double x2 = (w - Math.sqrt(D)) / (2 * v);

            double y1 = k * x1 + b;
            double y2 = k * x2 + b;

            Point f1 = new Point((int) x1, (int) y1);
            Point f2 = new Point((int) x2, (int) y2);

            if (isInsideTheAngle(f1, this.angle)
                    && isInsideTheAngle(f2, this.angle)
                    && isPointInsideTheCircle(f1, this.circle)
                    && isPointInsideTheCircle(f2, this.circle)) {
                allShapesPoints.add(f1);
                allShapesPoints.add(f2);
            }
        }
    }

    // проверяет лежит ли точка внутри круга
    public boolean isPointInsideTheCircle(Point p, Circle c) {
        return !(p.lengthToNextPoint(c.centre) > c.radius + 10);
    }

    // проверяет лежит ли точка внутри угла
    private static boolean isInsideTheAngle(Point p, Angle t) {
        double x1, x2, x3, x0;
        double y1, y2, y3, y0;

        x0 = p.x;
        y0 = p.y;

        x1 = t.top.x;
        x2 = t.versh1.x;
        x3 = t.versh2.x;

        y1 = t.top.y;
        y2 = t.versh1.y;
        y3 = t.versh2.y;

        double v = (x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0);
        double v1 = (x2 - x0) * (y3 - y2) - (x3 - x2) * (y2 - y0);
        double v2 = (x3 - x0) * (y1 - y3) - (x1 - x3) * (y3 - y0);

        return (v >= 10 && v1 >= 10 && v2 >= 10)
                || (v <= -10 && v1 <= -10 && v2 <= -10);
    }

    // Возвращает площадь текущей фигуры
    public static double square() {
        double square = 0;

        for (int i = 0; i < allShapesPoints.size() - 1; i++) {
            square = square + (allShapesPoints.get(i).x * allShapesPoints.get(i + 1).y);
            square = square - (allShapesPoints.get(i + 1).x * allShapesPoints.get(i).y);
        }

        return Math.abs(square);
    }
}
