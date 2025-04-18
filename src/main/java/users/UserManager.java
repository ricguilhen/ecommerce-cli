package users;

import entities.User;
import repository.UserRepository;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserManager {

    public void createUsersTable(Connection connection) {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    uuid TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL,
                    password TEXT NOT NULL
                )
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public void UserMenuMethodsADM() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite")) {
            createUsersTable(connection);

            UserRepository userRepo = new UserRepository(connection);
            Scanner scanner = new Scanner(System.in);

            int opcao;
            do {
                System.out.println("\n=== Menu de Usuários (ADM) ===");
                System.out.println("1. Cadastrar usuário");
                System.out.println("2. Listar usuários");
                System.out.println("3. Buscar usuário por UUID");
                System.out.println("4. Deletar usuário por UUID");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Digite um número válido.");
                    scanner.next();
                    System.out.print("Escolha uma opção: ");
                }
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Senha: ");
                        String password = scanner.nextLine();

                        User newUser = new User(UUID.randomUUID(), name, email, password);
                        userRepo.save(newUser);
                        System.out.println("Usuário salvo com sucesso!");
                    }
                    case 2 -> {
                        List<User> users = userRepo.findAll();
                        if (users.isEmpty()) {
                            System.out.println("Nenhum usuário cadastrado.");
                        } else {
                            System.out.println("Lista de usuários:");
                            for (User u : users) {
                                System.out.println(u.getUuid() + " - " + u.getName() + " - " + u.getEmail());
                            }
                        }
                    }
                    case 3 -> {
                        System.out.print("Digite o UUID do usuário: ");
                        String uuidStr = scanner.nextLine();
                        try {
                            UUID uuid = UUID.fromString(uuidStr);
                            userRepo.findById(uuid)
                                    .ifPresentOrElse(
                                            u -> System.out.println("Usuário: " + u.getName() + " - " + u.getEmail()),
                                            () -> System.out.println("Usuário não encontrado.")
                                    );
                        } catch (IllegalArgumentException e) {
                            System.out.println("UUID inválido.");
                        }
                    }
                    case 4 -> {
                        System.out.print("Digite o UUID do usuário a ser deletado: ");
                        String deleteUuidStr = scanner.nextLine();
                        try {
                            UUID deleteUuid = UUID.fromString(deleteUuidStr);
                            userRepo.deleteById(deleteUuid);
                            System.out.println("Usuário deletado (se existia).");
                        } catch (IllegalArgumentException e) {
                            System.out.println("UUID inválido.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Encerrando aplicação...");
                        System.exit(0);
                    }
                    default -> System.out.println("Opção inválida.");
                }

            } while (true);

        } catch (Exception e) {
            System.err.println("Erro na aplicação: " + e.getMessage());
        }
    }

    public boolean emailExists(Connection connection, String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar e-mail: " + e.getMessage());
            return false;
        }
    }

    public void findByEmail(Connection connection, String email) {
        String sql = "SELECT name, email, password FROM users WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("name");
                String emailString = rs.getString("email");

                System.out.println("Nome: " + nome);
                System.out.println("Email: " + emailString);
            } else {
                System.out.println("Nenhum usuário encontrado com esse email.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
    }


}
