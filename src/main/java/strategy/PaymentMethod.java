package strategy;

import entities.Product;
import factory.PaymentFactory;
import products.ProductManager;

import java.util.ArrayList;
import java.util.Scanner;

public class PaymentMethod {

    public String showPaymentMenu(ProductManager pm, ArrayList<Product> products) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Escolha o método de pagamento:");
            System.out.println("1: PIX");
            System.out.println("2: Cartão de Crédito");
            System.out.println("3: Boleto");

            System.out.print("Opção: ");
            int choice = scanner.nextInt();

            double amount = pm.calcularTotal(products);

            String paymentType = switch (choice) {
                case 1 -> "pix";
                case 2 -> "cartao";
                case 3 -> "boleto";
                default -> null;
            };

            if (paymentType == null) {
                System.out.println("Opção inválida, tente novamente.\n");
                continue;
            }

            PaymentStrategy strategy = PaymentFactory.createPaymentMethod(paymentType);
            PaymentProcessor processor = new PaymentProcessor(strategy);
            processor.executePayment(amount);

            return paymentType;
        }
    }

}