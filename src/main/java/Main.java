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
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        int menu;
        do {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            System.out.println("1- Gerenciar alunos, 2- Gerenciar cursos, 3- Gerenciar matriculas, 4- Relatório avançado, 5- Sair");
            menu = scanner.nextInt();

            if (menu < 1 || menu > 5) {
                System.out.println("Opção inválida");
            } else if (menu == 1) {
                sueStudent(scanner, entityManager);
            } else if (menu == 2) {
                sueCourse(scanner, entityManager);
            } else if (menu == 3) {
                sueRegistration(scanner, entityManager);
            } else if (menu == 4) {
                advancedReport(scanner, entityManager);
            }
        } while (menu != 5);

        System.out.println("Sistema encerrado");
        entityManager.close();
        entityManagerFactory.close();
    }

    public static void sueStudent(Scanner scanner, EntityManager entityManager) {
        System.out.println("0- Cadastrar aluno, 1- Listar aluno, 2- Buscar aluno");
        int studentOption = scanner.nextInt();
        scanner.nextLine();

        if (studentOption < 0 || studentOption > 2) {
            System.out.println("Opção inválida");
        } else {
            Student student = new Student();
            if (studentOption == 0) {
                System.out.println("Digite seu nome:");
                student.setName(scanner.nextLine());

                System.out.println("Digite seu email:");
                student.setEmail(scanner.next());

                System.out.println("Digite sua data de nascimento (dd/MM/yyyy):");
                try {
                    student.setDateOfBirth(LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                    executeTransaction(entityManager, student, "Aluno cadastrado!");
                } catch (Exception e) {
                    System.out.println("Data inválida");
                }
            } else if (studentOption == 1) {
                List<Student> studentsList = entityManager
                        .createQuery("SELECT students FROM Student students", Student.class).getResultList();

                checkAndPrint(studentsList, "Nenhum aluno cadastrado",item ->
                        System.out.printf("Id: %d, Nome: %s, Email: %s, Data de nascimento: %s\n", item.getId(), item.getName(), item.getEmail(), item.getDateOfBirth()));
            } else {
                System.out.println("Digite seu email:");
                String searchEmail = scanner.next();

                List<Student> students = entityManager
                        .createQuery("SELECT searchStudents FROM Student searchStudents WHERE searchStudents.email = :email", Student.class)
                        .setParameter("email", searchEmail)
                        .getResultList();
                checkAndPrint(students, "Aluno não encontrado", item ->
                        System.out.printf("Id: %d, Nome: %s, Email: %s, Data de nascimento: %s\n", item.getId(), item.getName(), item.getEmail(), item.getDateOfBirth()));
            }
        }
    }

    public static void sueCourse(Scanner scanner, EntityManager entityManager) {
        System.out.println("0- Cadastrar curso, 1- Listar curso, 2- Buscar curso");
        int courseOption = scanner.nextInt();
        scanner.nextLine();

        if (courseOption < 0 || courseOption > 2) {
            System.out.println("Opção inválida");
        } else {
            Course course = new Course();
            if (courseOption == 0) {
                System.out.println("Digite o nome do seu curso:");
                course.setName(scanner.nextLine());

                System.out.println("Digite a descrição de seu curso:");
                course.setDescription(scanner.nextLine());

                System.out.println("Digite a carga horária do curso:");
                course.setWorkload(scanner.nextInt());

                executeTransaction(entityManager, course, "Curso cadastrado!");
            } else if (courseOption == 1) {
                List<Course> courseList = entityManager
                        .createQuery("SELECT courses FROM Course courses", Course.class).getResultList();

                checkAndPrint(courseList, "Nenhum curso cadastrado", item ->
                        System.out.printf("Id: %d, Nome: %s, Descrição: %s, Carga horária: %d\n",item.getId(), item.getName(), item.getDescription(), item.getWorkload()));
            } else {
                System.out.println("Digite o nome do curso:");
                String searchName = scanner.next();

                List<Course> courses = entityManager
                        .createQuery("SELECT searchCourse FROM Course searchCourse WHERE searchCourse.name = :name", Course.class)
                        .setParameter("name", searchName)
                        .getResultList();
                checkAndPrint(courses, "Curso não encontrado", item ->
                        System.out.printf("Id: %d, Nome: %s, Descrição: %s, Carga horária: %d\n", item.getId(), item.getName(), item.getDescription(), item.getWorkload()));
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
                Student studentId = entityManager.find(Student.class, scanner.nextLong());

                System.out.println("Digite o id do curso:");
                Course courseId = entityManager.find(Course.class, scanner.nextLong());

                if (studentId == null || courseId == null) {
                    System.out.println("Aluno ou curso não encontrado");
                } else {
                    System.out.println("Digite a data de matrícula:");
                    try {
                        Registration registration = new Registration();
                        registration.setStudent(studentId);
                        registration.setCourse(courseId);
                        registration.setRegistrationDate(LocalDate.parse(scanner.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                        executeTransaction(entityManager, registration, "Matricula cadastrada!");
                    } catch (Exception e) {
                        System.out.println("Data inválida");
                    }
                }
            } else {
                List<Registration> registrationList = entityManager
                        .createQuery("SELECT registration FROM Registration registration", Registration.class).getResultList();
                checkAndPrint(registrationList, "Nenhuma matricula encontrada", item -> {
                    String nameStudent = (item.getStudent() != null) ? item.getStudent().getName() : "Aluno Excluído";
                    String nameCourse = (item.getCourse() != null) ? item.getCourse().getName() : "Curso Excluído";
                    System.out.printf("Nome: %s, Curso: %s\n", nameStudent, nameCourse);
                });
            }
        }
    }

    public static void executeTransaction(EntityManager entityManager, Object entity, String messageSuccess) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            System.out.println(messageSuccess);
        } catch (Exception e) {
            System.err.println("Erro ao processar transação: " + e.getMessage());
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public static <T> void checkAndPrint(List<T> list, String errorMessage, Consumer<T> sucessMessage) {
        if (list.isEmpty()) {
            System.out.println(errorMessage);
        } else {
            list.forEach(sucessMessage);
        }
    }

    public static void advancedReport(Scanner scanner, EntityManager entityManager) {
        System.out.println("1- Total alunos matriculados 2- Ver média de idade dos alunos por curso 3- Quantidade de alunos matriculados nos últimos 30 dias");
        int option = scanner.nextInt();
        if (option < 1 || option > 3) {
            System.out.println("Opção invalida");
        } else {
            if (option == 1){
                List<Object[]> enrolledStudents = entityManager
                        .createQuery("SELECT regis.course.name, COUNT(regis) FROM Registration regis GROUP BY regis.course.name", Object[].class)
                        .getResultList();

                enrolledStudents.forEach(item -> System.out.printf("Curso: %s | Quantidade de matrículas: %d\n", item));
            } else if (option == 2) {
                String calculation = "SELECT regis.course.name, AVG(YEAR(CURRENT_DATE) - YEAR(regis.student.dateOfBirth)) FROM Registration regis GROUP BY regis.course.name";

                List<Object[]> result = entityManager
                        .createQuery(calculation, Object[].class)
                        .getResultList();

                result.forEach(item -> System.out.printf("Curso: %s | Média de idade: %.1f\n", item));
            } else {
                LocalDate deadline = LocalDate.now().minusDays(30);
                String list = "SELECT regis.course.name, COUNT(regis.id) FROM Registration regis WHERE regis.registrationDate >= :deadline GROUP BY regis.course.name";
                List<Object[]> studentsDeadline =  entityManager
                        .createQuery(list, Object[].class).setParameter("deadline", deadline).getResultList();
                studentsDeadline.forEach(item -> System.out.printf("Curso: %s | %d alunos \n", item));
            }
        }
    }
}