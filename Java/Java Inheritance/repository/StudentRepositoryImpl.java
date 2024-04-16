package repository;

import model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl extends BaseRepository implements StudentRepository {
    @Override
    public void addStudent(Student student) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO students (name, age, email, studentID, grade) VALUES (?,?,?,?,?)")) {
            stmt.setString(1, student.getName());
            stmt.setByte(2, student.getAge());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getStudentID());
            stmt.setString(5, student.getGrade());
            stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(rs.getString("name"), rs.getByte("age"), rs.getString("email"), rs.getString("studentID"), rs.getString("grade"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}
