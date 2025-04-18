package strategy;

import java.util.Random;

public class BoletoPayment implements PaymentStrategy {

    @Override
    public void processPayment(double amount) {
        String boletoCode = "BOLETO-" + new Random().nextInt(100000);
        System.out.println("\n\n\nPagamento via Boleto sendo gerado...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n\nCódigo do Boleto: " + boletoCode);
        System.out.println("Pagamento de R$" + amount + " realizado com sucesso!\n\n");
    }
}