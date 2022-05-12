package task;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


public class MyFrame extends JFrame implements ActionListener {
    private double maxS = Integer.MIN_VALUE;

    private static Shape finalShape;

    private static final List<Point> mousePoints = new ArrayList<>();
    private static final List<Point> points = new ArrayList<>();
    private static final List<Circle> circles = new ArrayList<>();
    private static final List<Angle> angles = new ArrayList<>();

    private final JButton button_TO_ADD_CIRCLES;
    private final JButton button_TO_ADD_ANGLES;
    private final JButton button_TO_SOLVE;
    private final JButton button_TO_CLEAR;

    private final JButton button_TO_ADD_ANGLES_BY_MOUSE;
    private final JButton button_TO_ADD_CIRCLES_BY_MOUSE;

    private final JButton button_TO_ADD_ANGLES_BY_RANDOM;
    private final JButton button_TO_ADD_CIRCLES_BY_RANDOM;

    private final JTextField textField_FOR_ENTER_CIRCLES;
    private final JTextField textField_FOR_ENTER_ANGLES;

    private final JTextField textField_FOR_COUNT_RANDOM_SHAPES;

    private boolean isPressedAngle = true;

    public MyFrame(String t) {
        setTitle(t);

        button_TO_ADD_CIRCLES_BY_RANDOM = inizializeButton("Любая окружность", 500, 10, 150, 150);
        button_TO_ADD_ANGLES_BY_RANDOM = inizializeButton("Любой угол ", 900, 10, 150, 150);
        button_TO_ADD_CIRCLES_BY_MOUSE = inizializeButton(" Круг мышью" , 1700, 150, 150, 150);
        button_TO_ADD_ANGLES_BY_MOUSE = inizializeButton(" Угол мышью", 1700, 350, 150, 150);
        button_TO_SOLVE = inizializeButton("Решение", 1700, 590, 150, 150);
        button_TO_SOLVE.setBackground(new Color(255, 0, 255));
        button_TO_CLEAR = inizializeButton("Очистка", 10, 850, 200, 70);
        button_TO_CLEAR.setBackground(Color.red);
        button_TO_ADD_CIRCLES = inizializeButton("Окружность", 1100, 10, 250, 60);
        button_TO_ADD_CIRCLES.setBackground(new Color(0, 255, 255));
        button_TO_ADD_ANGLES = inizializeButton("Угол", 1400, 10, 250, 60);
        button_TO_ADD_ANGLES.setBackground(Color.green);

        textField_FOR_ENTER_CIRCLES = new JTextField("(200, 200) (300, 300)");
        textField_FOR_ENTER_CIRCLES.setBounds(1100, 100, 250, 50);

        textField_FOR_ENTER_ANGLES = new JTextField("(500, 500) (200, 200) (150, 400)");
        textField_FOR_ENTER_ANGLES.setBounds(1400, 100, 250, 50);

        textField_FOR_COUNT_RANDOM_SHAPES = new JTextField("Кол-во любых фигур ");
        textField_FOR_COUNT_RANDOM_SHAPES.setBounds(700, 10, 150, 150);

        addButtonsAndFields();

        setLayout(null);
        setSize(1920, 1080);
        addMouseListener(new MyListener());
        getContentPane().setBackground(new Color(105, 105, 105));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addButtonsAndFields() {
        add(textField_FOR_ENTER_CIRCLES);
        add(textField_FOR_COUNT_RANDOM_SHAPES);
        add(textField_FOR_ENTER_ANGLES);
        add(button_TO_SOLVE);
        add(button_TO_ADD_ANGLES_BY_MOUSE);
        add(button_TO_ADD_ANGLES_BY_RANDOM);
        add(button_TO_ADD_CIRCLES_BY_RANDOM);
        add(button_TO_ADD_CIRCLES_BY_MOUSE);
        add(button_TO_ADD_ANGLES);
        add(button_TO_CLEAR);
        add(button_TO_ADD_CIRCLES);
    }

    private JButton inizializeButton(String s, int x1, int x2, int x3, int x4) {
        final JButton button;
        button = new JButton(s);
        button.setBounds(x1, x2, x3, x4);
        button.addActionListener(this);
        return button;
    }

    private static void drawOval(Graphics g, Circle c) {
        g.setColor(new Color(0, 255, 255));
        g.drawOval((int) (c.centre.x - c.radius),
                (int) (c.centre.y - c.radius),
                (int) c.radius * 2,
                (int) c.radius * 2);
    }

    private static void drawAngle(Graphics g, Angle a) {
        g.setColor(Color.green);
        g.drawLine(a.top.x, a.top.y, a.versh1.x, a.versh1.y);
        g.drawLine(a.top.x, a.top.y, a.versh2.x, a.versh2.y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Point p : points) {
            g.drawOval(p.x, p.y, 5, 5);
        }

        for (Angle a : angles) {
            drawAngle(g, a);
        }

        for (Circle c : circles) {
            drawOval(g, c);
        }

        circles.forEach(c -> {
            angles.stream().map(a -> new Shape(c, a)).filter(s -> Shape.square() >= maxS).forEach(s -> {
                maxS = Shape.square();
                finalShape = s;
            });
        });
    }

    public static void showSolve(Graphics g) {
        // меняем ширину линии
        Graphics2D newGraphics = (Graphics2D) g;
        BasicStroke pen1 = new BasicStroke(10);
        newGraphics.setStroke(pen1);

        int bound = Shape.allShapesPoints.size();
        for (int i = 0; i < bound; i++) {
            for (int j = 0; j < bound; j++) {
                newGraphics.drawLine(Shape.allShapesPoints.get(i).x, Shape.allShapesPoints.get(i).y, Shape.allShapesPoints.get(j).x, Shape.allShapesPoints.get(j).y);
            }
            newGraphics.setColor(Color.magenta);
            newGraphics.drawOval(Shape.allShapesPoints.get(i).x, Shape.allShapesPoints.get(i).y, 4, 4);
            newGraphics.fillOval(Shape.allShapesPoints.get(i).x, Shape.allShapesPoints.get(i).y, 4, 4);
            try {
                newGraphics.drawLine(finalShape.line.p1.x, finalShape.line.p1.y, finalShape.line.p2.x, finalShape.line.p2.y);
            } catch (NullPointerException e) {
                e.getMessage();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher m;
        
        if (e.getSource() == button_TO_ADD_ANGLES_BY_RANDOM) {
            int count = Integer.parseInt(textField_FOR_COUNT_RANDOM_SHAPES.getText());
            addRandomAngles(count);
        }

        if (e.getSource() == button_TO_ADD_CIRCLES_BY_RANDOM) {
            int count = Integer.parseInt(textField_FOR_COUNT_RANDOM_SHAPES.getText());
            addRandomCirles(count);
        }

        if (e.getSource() == button_TO_ADD_ANGLES_BY_MOUSE) {
            isPressedAngle = true;
        }

        if (e.getSource() == button_TO_ADD_CIRCLES_BY_MOUSE) {
            isPressedAngle = false;
        }

        if (e.getSource() == button_TO_ADD_CIRCLES) {
            m = pat.matcher(textField_FOR_ENTER_CIRCLES.getText());

            var koordinati = new int[4];
            int temp = 0;

            while (m.find()) koordinati[temp++] = Integer.parseInt(m.group());

            circles.add(new Circle(
                    new Point(koordinati[0], koordinati[1]),
                    new Point(koordinati[2], koordinati[3])
            ));

            points.addAll(Arrays.asList(
                    new Point(koordinati[0], koordinati[1]),
                    new Point(koordinati[2], koordinati[3])
            ));
            repaint();
        }

        if (e.getSource() == button_TO_ADD_ANGLES) {
            m = pat.matcher(textField_FOR_ENTER_ANGLES.getText());

            var koordinati = new int[6];
            int temp = 0;

            while (m.find()) koordinati[temp++] = Integer.parseInt(m.group());

            angles.add(new Angle(
                    new Point(koordinati[0], koordinati[1]),
                    new Point(koordinati[2], koordinati[3]),
                    new Point(koordinati[4], koordinati[5])
            ));

            points.addAll(Arrays.asList(
                    new Point(koordinati[0], koordinati[1]),
                    new Point(koordinati[2], koordinati[3]),
                    new Point(koordinati[4], koordinati[5])));
            repaint();
        }

        if (e.getSource() == button_TO_SOLVE) {
            showSolve(getGraphics());
        }

        if (e.getSource() == button_TO_CLEAR) {
            repaint();
            points.clear();
            circles.clear();
            angles.clear();
            finalShape = new Shape();
        }
    }

    private void addRandomCirles(int count) {
        for (int i = 0; i < count; i++) {
            Point p1 = new Point((int) (Math.random() * 500 + 100), (int) (Math.random() * 500 + 100));
            Point p2 = new Point((int) (Math.random() * 500 + 100), (int) (Math.random() * 500 + 100));
            circles.add(new Circle(p1, p2));
            repaint();
        }
    }

    private void addRandomAngles(int count) {
        for (int i = 0; i < count; i++) {
            Point p1 = new Point((int) (Math.random() * 500 + 100), (int) (Math.random() * 500 + 100));
            Point p2 = new Point((int) (Math.random() * 500 + 100), (int) (Math.random() * 500 + 100));
            Point p3 = new Point((int) (Math.random() * 500 + 100), (int) (Math.random() * 500 + 100));
            angles.add(new Angle(p1, p2, p3));
            repaint();
        }
    }

    private class MyListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            if (isPressedAngle) {
                mousePoints.add(p);
                points.add(p);
                if (mousePoints.size() == 3) {
                    angles.add(new Angle(mousePoints.get(0), mousePoints.get(1), mousePoints.get(2)));
                    mousePoints.clear();
                }
            } else {
                mousePoints.add(p);
                points.add(p);
                if (mousePoints.size() == 2) {
                    circles.add(new Circle(mousePoints.get(0), mousePoints.get(1)));
                    mousePoints.clear();
                }
            }

            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public static void main(String[] args) {
        new MyFrame("Prokofiev");
    }
}
