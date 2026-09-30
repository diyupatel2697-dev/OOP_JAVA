abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double radius;

    Circle(double r) {
        radius = r;
    }

    @Override
    double area() {
        return 3.14 * radius * radius;
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(double l, double w) {
        length = l;
        width = w;
    }

    @Override
    double area() {
        return length * width;
    }
}

class Triangle extends Shape {
    double base, height;

    Triangle(double b, double h) {
        base = b;
        height = h;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }
}

public class ShapeTest {
    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(10),
            new Rectangle(20, 10),
            new Triangle(15, 8)
        };

        double total = 0;
        double largest = Double.NEGATIVE_INFINITY;
        Shape largestShape = null;

        for (Shape s : shapes) {

            double a = s.area();

            System.out.println(
                s.getClass().getSimpleName() + " Area = " + a
            );

            total += a;

            if (a > largest) {
                largest = a;
                largestShape = s;
            }
        }

        System.out.println("Total Area = " + total);

        if (largestShape != null) {
            System.out.println(
                "Largest Area = " + largest +
                " (" + largestShape.getClass().getSimpleName() + ")"
            );
        } else {
            System.out.println("No shapes available.");
        }
    }
}