package model;

import javax.persistence.*;
import java.time.LocalDate;

/* Classe matricula, representa a matrícula de um aluno em um curso.
Tabela intermediária onde faz ligação entre Student e Course
tambem armazena a data que a matricula foi feita
* */
@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //muitas matriculas podem estar associadas a um aluno
    //@JoinColumn especifica que student_id é a chave estrangeira
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    //muitas matriculas podem estar associadas a um curso
    //@JoinColumn especifica que course_id é a chave estrangeira
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate registrationDate;

    //Getters e Setters

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
