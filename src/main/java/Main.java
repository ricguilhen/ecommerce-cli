import factory.PaymentFactory;
import menu.MenuADM;
import menu.MenuUSER;
import strategy.PaymentMethod;
import strategy.PaymentProcessor;
import strategy.PaymentStrategy;
import users.UserManager;

import java.awt.*;
import java.util.Scanner;

// Toda vez que quiser voltar pro menu, vai ter que sair e entrar do programa. Preguiça de arrumar.

// Se quiser tirar as mensagens de WARNING (se aparecerem):
// No IntelliJ:
//Vá até Run > Edit Configurations
// Adicione uma nova configuração -> Aplicação (caso não exista)
// Nome: Main
// Main Class: Main
// Modify options -> VM Options
//No campo VM Options, adicione:
//--enable-native-access=ALL-UNNAMED

public class Main {
    public static void main(String[] args) {

        MenuADM menuADM = new MenuADM();
        MenuUSER menuUSER = new MenuUSER();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do{
            System.out.println("\n-- Menu Principal --");
            System.out.println("\n\n1. Menu ADM");
            System.out.println("2. Menu USER");
            System.out.println("3. Sair");
            System.out.println("\nAcessar: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    menuADM.showMainMenu();
                case 2:
                    menuUSER.userMenuMethodsUSER();
                case 3:
                    System.out.println("Saindo do programa...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        } while (true);

    }
}