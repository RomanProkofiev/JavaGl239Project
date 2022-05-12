package task;

public class Angle {
    public final Point top;
    public final Point versh1;
    public final Point versh2;

    // Конструктор, создающий острый угол по 3-м точкам, указанным в параметрах
    public Angle(Point p1, Point p2, Point p3) {
        this.top = p1;
        this.versh1 = p2;
        this.versh2 = p3;
    }
}
