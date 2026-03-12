package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;
import com.course.exception.DuplicateEnrollmentException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CourseService {
    private Course[] courses;
    private int courseCount;
    private final String FILE_NAME = "courses.txt";

    public CourseService() {
        courses = new Course[100];
        courseCount = 0;
        loadFromFile();
    }

    public void addCourse(Course c) {
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId() == c.getCourseId()) {
                return;
            }
        }
        if (courseCount < courses.length) {
            courses[courseCount] = c;
            courseCount++;
            saveToFile();
        }
    }

    public void enrollStudent(int courseId, Student s) throws CourseNotFoundException, CourseFullException, DuplicateEnrollmentException {
        Course foundCourse = null;
        for (int i = 0; i < courseCount; i++) {
            if (courses[i].getCourseId() == courseId) {
                foundCourse = courses[i];
                break;
            }
        }

        if (foundCourse == null) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
        }

        if (foundCourse.getEnrolledStudents() >= foundCourse.getMaxSeats()) {
            throw new CourseFullException("Course " + foundCourse.getCourseName() + " is already full.");
        }

        for (int i = 0; i < foundCourse.getEnrolledStudents(); i++) {
            if (foundCourse.getStudents()[i].getStudentId() == s.getStudentId()) {
                throw new DuplicateEnrollmentException("Student " + s.getStudentName() + " is already enrolled.");
            }
        }

        foundCourse.getStudents()[foundCourse.getEnrolledStudents()] = s;
        foundCourse.setEnrolledStudents(foundCourse.getEnrolledStudents() + 1);
        saveToFile();
    }

    public void viewCourses() {
        for (int i = 0; i < courseCount; i++) {
            courses[i].display();
        }
    }

    private void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));
            for (int i = 0; i < courseCount; i++) {
                Course c = courses[i];
                bw.write("COURSE," + c.getCourseId() + "," + c.getCourseName() + "," + c.getMaxSeats() + "," + c.getEnrolledStudents() + "\n");
                for (int j = 0; j < c.getEnrolledStudents(); j++) {
                    Student s = c.getStudents()[j];
                    bw.write("STUDENT," + c.getCourseId() + "," + s.getStudentId() + "," + s.getStudentName() + "\n");
                }
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    private void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
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
                    
                    courses[courseCount] = c;
                    courseCount++;
                } else if (parts[0].equals("STUDENT")) {
                    int courseId = Integer.parseInt(parts[1]);
                    int studentId = Integer.parseInt(parts[2]);
                    String studentName = parts[3];
                    Student s = new Student(studentId, studentName);
                    
                    for (int i = 0; i < courseCount; i++) {
                        if (courses[i].getCourseId() == courseId) {
                            Course c = courses[i];
                            int idx = 0;
                            while (idx < c.getMaxSeats() && c.getStudents()[idx] != null) {
                                idx++;
                            }
                            if (idx < c.getMaxSeats()) {
                                c.getStudents()[idx] = s;
                            }
                            break;
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
        }
    }
}
