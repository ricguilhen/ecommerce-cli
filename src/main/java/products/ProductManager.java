package products;

import entities.Product;
import repository.ProductRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

public class ProductManager {

    public void createProductsTable(Connection connection) {
        String sql = """
                CREATE TABLE IF NOT EXISTS products (
                    uuid TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    price REAL NOT NULL
                )
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.execute();
        } catch (Exception e) {
            System.err.println("Erro ao criar tabela de produtos: " + e.getMessage());
        }
    }

    public void ProductMenuMethodsADM() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite")) {
            createProductsTable(connection);

            ProductRepository productRepo = new ProductRepository(connection);
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== Menu de Produtos (ADM) ===");
                System.out.println("1. Cadastrar produto");
                System.out.println("2. Listar produtos");
                System.out.println("3. Buscar produto por UUID");
                System.out.println("4. Deletar produto por UUID");
                System.out.println("0. Sair do programa");
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
                        System.out.print("Nome do produto: ");
                        String name = scanner.nextLine();
                        System.out.print("Preço do produto: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();

                        Product newProduct = new Product(UUID.randomUUID(), name, price);
                        productRepo.save(newProduct);
                        System.out.println("Produto salvo com sucesso!");
                    }
                    case 2 -> {
                        List<Product> products = productRepo.findAll();
                        if (products.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            System.out.println("Lista de produtos:");
                            for (Product p : products) {
                                System.out.printf("%s - %s - R$ %.2f%n", p.getUuid(), p.getName(), p.getPrice());
                            }
                        }
                    }
                    case 3 -> {
                        System.out.print("Digite o UUID do produto: ");
                        String uuidStr = scanner.nextLine();
                        try {
                            UUID uuid = UUID.fromString(uuidStr);
                            productRepo.findById(uuid)
                                    .ifPresentOrElse(
                                            p -> System.out.printf("Produto: %s - R$ %.2f%n", p.getName(), p.getPrice()),
                                            () -> System.out.println("Produto não encontrado.")
                                    );
                        } catch (IllegalArgumentException e) {
                            System.out.println("UUID inválido.");
                        }
                    }
                    case 4 -> {
                        System.out.print("Digite o UUID do produto a ser deletado: ");
                        String uuidStr = scanner.nextLine();
                        try {
                            UUID uuid = UUID.fromString(uuidStr);
                            productRepo.deleteById(uuid);
                            System.out.println("Produto deletado (se existia).");
                        } catch (IllegalArgumentException e) {
                            System.out.println("UUID inválido.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Saindo do programa...");
                        System.exit(0);
                    }
                    default -> System.out.println("Opção inválida.");
                }

            } while (true);

        } catch (Exception e) {
            System.err.println("Erro na aplicação de produtos: " + e.getMessage());
        }
    }

    public ArrayList<Product> productCart() {

        ArrayList<Product> products = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite")) {
            ProductRepository productRepo = new ProductRepository(connection);
            Scanner scanner = new Scanner(System.in);
            products = new ArrayList<>();

            while (true) {
                System.out.println("Digite o UUID do produto a ser comprado: ");
                try {
                    UUID uuid = UUID.fromString(scanner.next());
                    Optional<Product> product = productRepo.findById(uuid);

                    if (product.isPresent()) {
                        products.add(product.get());
                        System.out.println("Produto adicionado com sucesso!");
                    } else {
                        System.out.println("Esse produto não existe!");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("UUID inválido. Tente novamente.");
                    continue;
                }

                System.out.println("Deseja adicionar mais um produto? (s/n)");
                String resposta = scanner.next().trim().toLowerCase();
                if (!resposta.equals("s")) {
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("Erro na aplicação de produtos: " + e.getMessage());
        }
        return products;
    }

    public double calcularTotal(ArrayList<Product> carrinho) {
        double total = 0.0;

        for (Product produto : carrinho) {
            total += produto.getPrice();
        }

        return total;
    }

}