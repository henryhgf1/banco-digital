package models;

public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("\n=== Extrato da Conta Corrente ===");
        System.out.println("Titular: " + super.cliente.getNome());
        System.out.println("Agência: " + super.agencia);
        System.out.println("Número:  " + super.numero);
        System.out.printf("Saldo:   R$ %.2f\n", super.saldo);
        System.out.println("===============================");
    }
}