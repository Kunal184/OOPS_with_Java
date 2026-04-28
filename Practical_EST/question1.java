class Shape {
    void area() {
        System.out.println("idwfwfw");
    }
}

class Circle extends Shape {
    void area() {
        double r = 5;
        double result = 3.14 * r * r;
        System.out.println("Area of Circle: " + result);
    }
}

class Rectangle extends Shape {
    void area() {
        double l = 4, b = 6;
        double result = l * b;
        System.out.println("Area of Rectangle: " + result);
    }
}

public class question1 {
    public static void main(String[] args) {
        Shape s;

        s = new Circle();
        s.area();

        s = new Rectangle();
        s.area();
    }
}