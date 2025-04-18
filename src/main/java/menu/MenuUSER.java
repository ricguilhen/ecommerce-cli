package menu;

import entities.Product;
import products.ProductManager;
import strategy.PaymentMethod;
import users.UserManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuUSER {

    public void userMenuMethodsUSER() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite")) {
            ProductManager pm = new ProductManager();
            UserManager um = new UserManager();
            PaymentMethod payMethod = new PaymentMethod();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite o seu email: ");
            String email = scanner.nextLine();

            if (um.emailExists(connection, email)) {
                System.out.println("\n\n");
                ArrayList<Product> carrinho = pm.productCart();
                String paymentType = payMethod.showPaymentMenu(pm, carrinho);


                System.out.println("\n\nProcessando...\n\n");
                Thread.sleep(1000);

                System.out.println("Resumo da venda:\n");
                um.findByEmail(connection, email);
                System.out.println("Valor da compra: " + pm.calcularTotal(carrinho));
                System.out.println("Tipo de pagamento: " + paymentType);
                System.out.println("\n\nCompra realizada com sucesso!");
            }

        } catch (Exception e) {
            System.err.println("Erro na aplicação: " + e.getMessage());
        }
    }
}
