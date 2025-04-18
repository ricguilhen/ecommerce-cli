package strategy;

import java.util.Scanner;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número do cartão de crédito: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Pagamento de R$" + amount + " realizado com sucesso no cartão " + maskCardNumber(cardNumber) + "!\n");
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() >= 4) {
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        }
        return "Número de cartão inválido";
    }
}