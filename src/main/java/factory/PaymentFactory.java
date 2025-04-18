package factory;

import strategy.*;

public class PaymentFactory {
    public static PaymentStrategy createPaymentMethod(String type) {
        return switch (type.toLowerCase()) {
            case "pix" -> new PixPayment();
            case "cartao" -> new CreditCardPayment();
            case "boleto" -> new BoletoPayment();
            default -> throw new IllegalArgumentException("Método de pagamento inválido");
        };
    }
}