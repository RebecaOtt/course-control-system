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

    //getters e setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }
}
