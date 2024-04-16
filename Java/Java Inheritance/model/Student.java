package model;

public class Student {
    private String studentID;
    private String grade;

    public Student(String name, byte age, String email, String studentID, String grade) {
        super(name, age, email);
        this.studentID = studentID;
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", grade='" + grade + '\'' +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                '}';
    }
    
}
