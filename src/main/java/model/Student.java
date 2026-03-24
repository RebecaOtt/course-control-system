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
}
