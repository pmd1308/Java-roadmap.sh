package repository;

import java.util.List;
import schoolmanagement.model.Student;

public interface StudentRepository {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    void addStudent(Student student);
    void updateStudent(int id, Student updatedStudent);
    void deleteStudent(int id);
}
