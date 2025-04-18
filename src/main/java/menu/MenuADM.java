package menu;

import products.ProductManager;
import users.UserManager;

import java.util.Scanner;

public class MenuADM {

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Menu Principal (Administração) ===");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Produtos");
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
                    UserManager userManager = new UserManager();
                    userManager.UserMenuMethodsADM();
                }
                case 2 -> {
                    ProductManager productManager = new ProductManager();
                    productManager.ProductMenuMethodsADM();
                }
                case 0 -> System.out.println("Encerrando sistema de administração...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
}