package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;
import com.course.exception.DuplicateEnrollmentException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses;
    private final String FILE_NAME = "courses.txt";

    public CourseService() {
        courses = new ArrayList<>();
        loadFromFile();
    }

    public void addCourse(Course c) {
        for (Course course : courses) {
            if (course.getCourseId() == c.getCourseId()) {
                return;
            }
        }
        courses.add(c);
        saveToFile();
    }

    public void enrollStudent(int courseId, Student s) throws CourseNotFoundException, CourseFullException, DuplicateEnrollmentException {
        Course foundCourse = null;
        for (Course c : courses) {
            if (c.getCourseId() == courseId) {
                foundCourse = c;
                break;
            }
        }

        if (foundCourse == null) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
        }

        if (foundCourse.getEnrolledStudents() >= foundCourse.getMaxSeats()) {
            throw new CourseFullException("Course " + foundCourse.getCourseName() + " is already full.");
        }

        for (Student enrolled : foundCourse.getStudentsList()) {
            if (enrolled.getStudentId() == s.getStudentId()) {
                throw new DuplicateEnrollmentException("Student " + s.getStudentName() + " is already enrolled.");
            }
        }

        foundCourse.getStudentsList().add(s);
        foundCourse.setEnrolledStudents(foundCourse.getEnrolledStudents() + 1);
        saveToFile();
    }

    public void viewCourses() {
        for (Course c : courses) {
            c.display();
        }
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courses) {
                bw.write("COURSE," + c.getCourseId() + "," + c.getCourseName() + "," + c.getMaxSeats() + "," + c.getEnrolledStudents());
                bw.newLine();
                for (Student s : c.getStudentsList()) {
                    bw.write("STUDENT," + c.getCourseId() + "," + s.getStudentId() + "," + s.getStudentName());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("COURSE")) {
                    int id = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    int maxSeats = Integer.parseInt(parts[3]);
                    int enrolled = Integer.parseInt(parts[4]);
                    Course c = new Course(id, name, maxSeats);
                    c.setEnrolledStudents(enrolled);
                    courses.add(c);
                } else if (parts[0].equals("STUDENT")) {
                    int courseId = Integer.parseInt(parts[1]);
                    int studentId = Integer.parseInt(parts[2]);
                    String studentName = parts[3];
                    Student s = new Student(studentId, studentName);
                    for (Course c : courses) {
                        if (c.getCourseId() == courseId) {
                            c.getStudentsList().add(s);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }
}
