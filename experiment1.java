class Student {
    int id;
    String name;
    int age;

    Student() {
        System.out.println("Object Initialized");
    }

    Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    void displayDetails() {
        System.out.println("Student ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.id = 112;
        s1.name = "Rahul";
        s1.age = 23;
        s1.displayDetails();

        Student s2 = new Student(101, "Drax", 20);
        s2.displayDetails();
    }
}
