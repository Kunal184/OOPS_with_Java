import java.util.Scanner;

public class StudentGradeEvaluator {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter marks (out of 100): ");
            int marks = sc.nextInt();

            if (marks < 0 || marks > 100) {
                System.out.println("Marks must be between 0 and 100.");
            }
            else {
                String grade;

                if (marks >= 90)
                    grade = "A";
                else if (marks >= 75)
                    grade = "B";
                else if (marks >= 60)
                    grade = "C";
                else if (marks >= 40)
                    grade = "D";
                else
                    grade = "Fail";

                System.out.println("\nStudent Name: " + name);
                System.out.println("Marks Entered: " + marks);
                System.out.println("Grade Obtained: " + grade);
            }
        }
        catch (Exception e) {
            System.out.println("Invalid input. Please enter numeric marks only.");
        }
        finally {
            System.out.println("Marks evaluation completed.");
            sc.close();
        }
    }
}
