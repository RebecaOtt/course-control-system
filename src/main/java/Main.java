import model.Course;
import model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Student student = new Student();
//        System.out.println("0- Cadastrar aluno, 1- Listar aluno, 2- Buscar aluno");
//        int studentOption = scanner.nextInt();
//
//        if (studentOption < 0 || studentOption > 2){
//            System.out.println("Opção inválida");
//        } else {
//            switch (studentOption) {
//                case 0:
//                    System.out.println("Digite seu nome:");
//                    scanner.nextLine();
//                    String studentName = scanner.nextLine();
//                    student.setName(studentName);
//
//                    System.out.println("Digite seu email:");
//                    String studentEmail = scanner.next();
//                    student.setEmail(studentEmail);
//
//                    System.out.println("Digite sua data de nascimento:");
//                    String date = scanner.next();
//                    try {
//                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                        LocalDate dateOfBirth = LocalDate.parse(date, formatter);
//                        student.setDateOfBirth(dateOfBirth);
//                    } catch (Exception e) {
//                        System.out.println("Data inválida, use o formato dd/MM/yyyy");
//                        break;
//                    }
//
//                    entityManager.getTransaction().begin(); //inicia transição
//                    entityManager.persist(student); //marca o objeto para salvar
//                    entityManager.getTransaction().commit(); //insere o aluno no bd
//
//                    System.out.println("Aluno cadastrado com sucesso!");
//                    break;
//                case 1:
//
//                    entityManager.getTransaction().begin();
//                    List<Student> studentsList = entityManager.createQuery("SELECT students FROM Student students", Student.class).getResultList();
//
//                    for (Student students : studentsList) {
//                        System.out.println("ID: " + students.getId());
//                        System.out.println("Nome: " + students.getName());
//                        System.out.println("Email: " + students.getEmail());
//                        System.out.println("Data de nascimento: " + students.getDateOfBirth());
//                        System.out.println("------------------------");
//                    }
//                    entityManager.getTransaction().commit();
//                    break;
//                default:
//                    System.out.println("Digite seu email:");
//                    String searchEmail = scanner.next();
//
//                    entityManager.getTransaction().begin();
//
//                    List<Student> students = entityManager
//                            .createQuery("SELECT searchStudents FROM Student searchStudents WHERE searchStudents.email = :email", Student.class)
//                            .setParameter("email", searchEmail)
//                            .getResultList();
//
//                    if (students.isEmpty()) {
//                        System.out.println("Aluno não encontrado.");
//                    } else {
//                        for (Student searchStudents : students) {
//                            System.out.println("Nome: " + searchStudents.getName());
//                            System.out.println("Email: " + searchStudents.getEmail());
//                            System.out.println("Data: " + searchStudents.getDateOfBirth());
//                        }
//                    }
//                    entityManager.getTransaction().commit();
//            }
//
//            entityManager.close();
//            entityManagerFactory.close();
//        }

        Course course = new Course();
        System.out.println("0- Cadastrar curso, 1- Listar curso, 2- Buscar curso");
        int courseOption = scanner.nextInt();

        if (courseOption < 0 || courseOption > 2){
            System.out.println("Opção inválida");
        } else {
            switch (courseOption) {
                case 0:
                    System.out.println("Digite o nome do seu curso:");
                    scanner.nextLine();
                    String courseName = scanner.nextLine();
                    course.setName(courseName);

                    System.out.println("Digite a descrição de seu curso:");
                    String courseDescription = scanner.nextLine();
                    course.setDescription(courseDescription);

                    System.out.println("Digite a carga horária do curso:");
                    int courseWorkload = scanner.nextInt();
                    course.setWorkload(courseWorkload);

                    entityManager.getTransaction().begin(); //inicia transição
                    entityManager.persist(course); //marca o objeto para salvar
                    entityManager.getTransaction().commit(); //insere o aluno no bd

                    System.out.println("Curso cadastrado com sucesso!");
                    break;
                case 1:
                    entityManager.getTransaction().begin();
                    List<Course> courseList = entityManager.createQuery("SELECT courses FROM Course courses", Course.class).getResultList();

                    for (Course courses : courseList) {
                        System.out.println("ID: " + courses.getId());
                        System.out.println("Nome: " + courses.getName());
                        System.out.println("Descrição: " + courses.getDescription());
                        System.out.println("Carga horária: " + courses.getWorkload());
                        System.out.println("------------------------");
                    }
                    entityManager.getTransaction().commit();
                    break;
                default:
                    System.out.println("Digite o nome do curso:");
                    String searchName = scanner.next();

                    entityManager.getTransaction().begin();

                    List<Course> courses = entityManager
                            .createQuery("SELECT searchCourse FROM Course searchCourse WHERE searchCourse.name = :name", Course.class)
                            .setParameter("name", searchName)
                            .getResultList();

                    if (courses.isEmpty()) {
                        System.out.println("Curso não encontrado.");
                    } else {
                        for (Course searchCourse : courses) {
                            System.out.println("Nome: " + searchCourse.getName());
                            System.out.println("Descrição: " + searchCourse.getDescription());
                            System.out.println("Carga horária: " + searchCourse.getWorkload());
                        }
                    }
                    entityManager.getTransaction().commit();
            }

            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
