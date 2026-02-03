class Area {

    double length;
    double breadth;
    double side;

    void area(double length, double breadth) {
        this.length = length;
        this.breadth = breadth;
        System.out.println("Area of Rectangle: " + (length * breadth));
    }

    void area(double side) {
        this.side = side;
        System.out.println("Area of Square: " + (side * side));
    }

    public static void main(String[] args) {
        Area obj = new Area();

        obj.area(5.0, 4.0);
        obj.area(6.0);
    }
}
