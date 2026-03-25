package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/*Classe que representa um aluno
armazena informações sobre ele, como nome, email e data de nascimento
* */
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;

    //um aluno pode ter se matriculado em varios cursos
    @OneToMany(mappedBy = "student")
    private List<Registration> registrationList;

    //Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
