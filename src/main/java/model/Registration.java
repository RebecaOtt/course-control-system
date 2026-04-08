package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //muitas matriculas podem estar associadas a um aluno
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    //muitas matriculas podem estar associadas a um curso
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate registrationDate;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
