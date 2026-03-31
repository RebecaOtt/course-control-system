import model.Course;
import model.Registration;
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

        int menu;
        do {
            //se alguma transação foi iniciada e não completa o rollback cancela a transação e volta do 0
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            System.out.println("1- Gerenciar alunos, 2- Gerenciar cursos, 3- Gerenciar matriculas, 4- Sair");
            menu = scanner.nextInt();

            if (menu < 1 || menu > 4) {
                System.out.println("Opção inválida");
            } else if (menu == 1) {
                sueStudent(scanner, entityManager);
            } else if (menu == 2) {
                sueCourse(scanner, entityManager);
            } else if (menu == 3) {
                sueRegistration(scanner, entityManager);
            }
        } while (menu != 4);

        entityManager.close();
        entityManagerFactory.close();
        System.out.println("Sistema encerrado");

    }

    public static void sueStudent(Scanner scanner, EntityManager entityManager) {
        Student student = new Student();
        System.out.println("0- Cadastrar aluno, 1- Listar aluno, 2- Buscar aluno");
        int studentOption = scanner.nextInt();
        scanner.nextLine();

        if (studentOption < 0 || studentOption > 2) {
            System.out.println("Opção inválida");
        } else {
            if (studentOption == 0) {
                System.out.println("Digite seu nome:");
                student.setName(scanner.nextLine());

                System.out.println("Digite seu email:");
                student.setEmail(scanner.next());

                System.out.println("Digite sua data de nascimento (dd/MM/yyyy):");
                try {
                    entityManager.getTransaction().begin();

                    student.setDateOfBirth(LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    entityManager.persist(student);

                    entityManager.getTransaction().commit();
                    System.out.println("Aluno cadastrado");
                } catch (Exception e) {
                    System.out.println("Data inválida");

                    if (entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().rollback();
                    }
                }
            } else if (studentOption == 1) {
                List<Student> studentsList = entityManager
                        .createQuery("SELECT students FROM Student students", Student.class).getResultList();

                if (studentsList.isEmpty()) {
                    System.out.println("Nenhum aluno cadastrado.");
                } else {
                    studentsList.forEach(item ->
                            System.out.printf("Id: %d, Nome: %s, Email: %s, Data de nascimento: %s\n", item.getId(), item.getName(), item.getEmail(), item.getDateOfBirth()));
                }
            } else {
                System.out.println("Digite seu email:");
                String searchEmail = scanner.next();

                List<Student> students = entityManager
                        .createQuery("SELECT searchStudents FROM Student searchStudents WHERE searchStudents.email = :email", Student.class)
                        .setParameter("email", searchEmail)
                        .getResultList();

                if (students.isEmpty()) {
                    System.out.println("Aluno não encontrado.");
                } else {
                    students.forEach(item ->
                            System.out.printf("Id: %d, Nome: %s, Email: %s, Data de nascimento: %s\n", item.getId(), item.getName(), item.getEmail(), item.getDateOfBirth()));
                }
            }
        }
    }

    public static void sueCourse(Scanner scanner, EntityManager entityManager) {
        Course course = new Course();
        System.out.println("0- Cadastrar curso, 1- Listar curso, 2- Buscar curso");
        int courseOption = scanner.nextInt();
        scanner.nextLine();

        if (courseOption < 0 || courseOption > 2) {
            System.out.println("Opção inválida");
        } else {
            if (courseOption == 0) {
                System.out.println("Digite o nome do seu curso:");
                course.setName(scanner.nextLine());

                System.out.println("Digite a descrição de seu curso:");
                course.setDescription(scanner.nextLine());

                System.out.println("Digite a carga horária do curso:");
                course.setWorkload(scanner.nextInt());

                try {
                    entityManager.getTransaction().begin();
                    entityManager.persist(course);

                    entityManager.getTransaction().commit();
                    System.out.println("Curso cadastrado!");
                } catch (Exception e) {
                    if (entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().rollback();
                    }
                    System.out.println("Erro: Problema no banco.");
                }
            } else if (courseOption == 1) {
                List<Course> courseList = entityManager
                        .createQuery("SELECT courses FROM Course courses", Course.class).getResultList();

                if (courseList.isEmpty()) {
                    System.out.println("Nenhum curso cadastrado.");
                } else {
                    courseList.forEach(item ->
                            System.out.printf("Id: %d, Nome: %s, Descrição: %s, Carga horária: %d\n",item.getId(), item.getName(), item.getDescription(), item.getWorkload()));
                }
            } else {
                System.out.println("Digite o nome do curso:");
                String searchName = scanner.next();

                List<Course> courses = entityManager
                        .createQuery("SELECT searchCourse FROM Course searchCourse WHERE searchCourse.name = :name", Course.class)
                        .setParameter("name", searchName)
                        .getResultList();

                if (courses.isEmpty()) {
                    System.out.println("Curso não encontrado.");
                } else {
                    courses.forEach(item ->
                            System.out.printf("Id: %d, Nome: %s, Descrição: %s, Carga horária: %d\n", item.getId(), item.getName(), item.getDescription(), item.getWorkload()));
                }
            }
        }
    }

    public static void sueRegistration(Scanner scanner, EntityManager entityManager) {
        System.out.println("0- Cadastrar matrícula, 1- Listar matrícula");
        int registationOption = scanner.nextInt();
        scanner.nextLine();

        if (registationOption < 0 || registationOption > 1) {
            System.out.println("Opção inválida");
        } else {
            if (registationOption == 0) {
                System.out.println("Digite o id do aluno:");

                //.find vai ao banco buscar todas as informações daquele ID
                Student studentId = entityManager.find(Student.class, scanner.nextLong());

                System.out.println("Digite o id do curso:");
                Course courseId = entityManager.find(Course.class, scanner.nextLong());

                if (studentId == null || courseId == null) {
                    System.out.println("Aluno ou curso não encontrado");
                } else {
                    System.out.println("Digite a data de matrícula:");
                    try {
                        entityManager.getTransaction().begin();

                        Registration registration = new Registration();
                        registration.setStudent(studentId);
                        registration.setCourse(courseId);
                        registration.setRegistrationDate(LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        entityManager.persist(registration);

                        entityManager.getTransaction().commit();
                        System.out.println("Matrícula cadastrado");
                    } catch (Exception e) {
                        System.out.println("Data inválida");

                        if (entityManager.getTransaction().isActive()) {
                            entityManager.getTransaction().rollback();
                        }
                    }
                }
            } else {
                List<Registration> registrationList = entityManager
                        .createQuery("SELECT registration FROM Registration registration", Registration.class).getResultList();
                if (registrationList.isEmpty()) {
                    System.out.println("Nenhuma matrícula encontrada.");
                } else {
                    registrationList.forEach(item -> {
                        String nameStudent = (item.getStudent() != null) ? item.getStudent().getName() : "Aluno Excluído";
                        String nameCourse = (item.getCourse() != null) ? item.getCourse().getName() : "Curso Excluído";

                        System.out.printf("Nome: %s, Curso: %s\n", nameStudent, nameCourse);
                    });
                }
            }
        }
    }
}
