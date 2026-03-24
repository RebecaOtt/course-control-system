package model;

import javax.persistence.*;
import java.util.List;

/*Classe curso que representa os cursos oferecidos, tendo dados como nome, descrição
e sua carga horária
* */
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private int workload;

    //um curso pode ter muitos alunos matriculados
    @OneToMany(mappedBy = "course")
    private List<Registration> registrationList;
}
