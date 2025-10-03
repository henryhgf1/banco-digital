package models;

public abstract class Conta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    public Conta(Cliente cliente) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("ERRO: O valor do depósito deve ser positivo.");
            return;
        }
        this.saldo += valor;
        System.out.printf("Depósito de R$ %.2f realizado com sucesso.\n", valor);
    }

    public void sacar(double valor) throws SaldoInsuficienteException {
        if (valor <= 0) {
            System.out.println("ERRO: O valor do saque deve ser positivo.");
            return;
        }
        if (this.saldo < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o saque.");
        }
        this.saldo -= valor;
        System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valor);
    }

    public void transferir(double valor, Conta contaDestino) throws SaldoInsuficienteException {
        this.sacar(valor);
        contaDestino.depositar(valor);
        System.out.printf("Transferência de R$ %.2f para %s realizada com sucesso.\n", valor, contaDestino.getCliente().getNome());
    }

    public void exibirSaldo() {
        System.out.printf("Saldo atual: R$ %.2f\n", this.saldo);
    }
    
    public abstract void imprimirExtrato();

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public static class SaldoInsuficienteException extends Exception {
        public SaldoInsuficienteException(String message) {
            super(message);
        }
    }
}