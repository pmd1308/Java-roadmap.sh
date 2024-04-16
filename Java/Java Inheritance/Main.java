import java.util.Scanner;
import repository.*;
import model.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentRepository studentRepository = new StudentRepositoryImpl();

        System.out.println("Bem-vindo ao Sistema de Gerenciamento de Estudantes!");

        while (true) {
            System.out.println("\nSelecione uma opção:");
            System.out.println("1. Adicionar Estudante");
            System.out.println("2. Atualizar Estudante");
            System.out.println("3. Deletar Estudante");
            System.out.println("4. Exibir Todos os Estudantes");
            System.out.println("5. Sair");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (choice) {
                case 1:
                    addStudent(scanner, studentRepository);
                    break;
                case 2:
                    updateStudent(scanner, studentRepository);
                    break;
                case 3:
                    deleteStudent(scanner, studentRepository);
                    break;
                case 4:
                    displayAllStudents(studentRepository);
                    break;
                case 5:
                    System.out.println("Obrigado por usar o Sistema de Gerenciamento de Estudantes. Até logo!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Por favor, selecione novamente.");
            }
        }
    }

    private static void addStudent(Scanner scanner, StudentRepository studentRepository) {
        System.out.println("Digite o nome do estudante:");
        String name = scanner.nextLine();

        System.out.println("Digite a idade do estudante:");
        byte age = scanner.nextByte();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("Digite o email do estudante:");
        String email = scanner.nextLine();

        System.out.println("Digite o ID do estudante:");
        String studentID = scanner.nextLine();

        System.out.println("Digite a série do estudante:");
        String grade = scanner.nextLine();

        Student newStudent = new Student(name, age, email, studentID, grade);
        studentRepository.addStudent(newStudent);
        System.out.println("Estudante adicionado com sucesso!");
    }

    private static void updateStudent(Scanner scanner, StudentRepository studentRepository) {
        System.out.println("Digite o ID do estudante que deseja atualizar:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("Digite o novo nome do estudante:");
        String name = scanner.nextLine();

        System.out.println("Digite a nova idade do estudante:");
        byte age = scanner.nextByte();
        scanner.nextLine(); // Limpar o buffer

        System.out.println("Digite o novo email do estudante:");
        String email = scanner.nextLine();

        System.out.println("Digite o novo ID do estudante:");
        String studentID = scanner.nextLine();

        System.out.println("Digite a nova série do estudante:");
        String grade = scanner.nextLine();

        Student updatedStudent = new Student(name, age, email, studentID, grade);
        studentRepository.updateStudent(id, updatedStudent);
        System.out.println("Estudante atualizado com sucesso!");
    }

    private static void deleteStudent(Scanner scanner, StudentRepository studentRepository) {
        System.out.println("Digite o ID do estudante que deseja excluir:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        studentRepository.deleteStudent(id);
        System.out.println("Estudante excluído com sucesso!");
    }

    private static void displayAllStudents(StudentRepository studentRepository) {
        System.out.println("Todos os estudantes:");
        studentRepository.getAllStudents().forEach(System.out::println);
    }
}

