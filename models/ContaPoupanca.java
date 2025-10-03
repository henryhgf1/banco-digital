package models;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("\n=== Extrato da Conta Poupança ===");
        System.out.println("Titular: " + super.cliente.getNome());
        System.out.println("Agência: " + super.agencia);
        System.out.println("Número:  " + super.numero);
        System.out.printf("Saldo:   R$ %.2f\n", super.saldo);
        System.out.println("===============================");
    }
}