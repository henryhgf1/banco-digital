package terminal;

import models.*;
import services.Banco;

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class BancoTerminal {

    private final Scanner scanner = new Scanner(System.in);
    private final Banco banco = new Banco();

    public static void main(String[] args) {
        BancoTerminal terminal = new BancoTerminal();
        terminal.inicializarBancoComDados();
        terminal.executar();
    }

    private void inicializarBancoComDados() {
        Cliente clienteTeste = new Cliente("Henry Gabriel", "123.456.789-10", "henry@email.com", "senha321");
        Conta contaTeste = new ContaCorrente(clienteTeste);
        clienteTeste.setConta(contaTeste);
        contaTeste.depositar(1000);
        banco.adicionarCliente(clienteTeste);
    }

    public void executar() {
        while (true) {
            exibirMenuPrincipal();
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine(); 

                switch (escolha) {
                    case 1:
                        processarLogin();
                        break;
                    case 2:
                        processarCriacaoDeConta();
                        break;
                    case 3:
                        System.out.println("\nObrigado por usar o Banco Digital. Até logo!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }
    
    private void exibirMenuPrincipal() {
        System.out.println("\n--- BEM-VINDO AO BANCO DIGITAL ---");
        System.out.println("1 - Fazer Login");
        System.out.println("2 - Criar Nova Conta");
        System.out.println("3 - Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private void processarLogin() {
        System.out.print("\nDigite seu email: ");
        String email = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        Optional<Cliente> clienteAutenticado = banco.autenticarCliente(email, senha);

        if (clienteAutenticado.isPresent()) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo(a), " + clienteAutenticado.get().getNome() + ".");
            exibirMenuDaConta(clienteAutenticado.get());
        } else {
            System.out.println("\nLogin falhou. Email ou senha incorretos.");
        }
    }

    private void processarCriacaoDeConta() {
        System.out.println("\n--- CRIAÇÃO DE NOVA CONTA ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        if (banco.emailJaCadastrado(email)) {
            System.out.println("\nERRO: Este e-mail já está cadastrado.");
            return;
        }

        System.out.print("Crie uma senha: ");
        String senha = scanner.nextLine();

        Cliente novoCliente = new Cliente(nome, cpf, email, senha);
        Conta novaConta = escolherTipoDeConta(novoCliente);
        
        if (novaConta != null) {
            novoCliente.setConta(novaConta);
            banco.adicionarCliente(novoCliente);
            System.out.println("\nConta criada com sucesso!");
            novaConta.imprimirExtrato();
        }
    }

    private Conta escolherTipoDeConta(Cliente cliente) {
        System.out.println("Qual tipo de conta deseja criar?");
        System.out.println("1 - Conta Corrente");
        System.out.println("2 - Conta Poupança");
        System.out.print("Escolha uma opção: ");
        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        switch (tipoConta) {
            case 1:
                return new ContaCorrente(cliente);
            case 2:
                return new ContaPoupanca(cliente);
            default:
                System.out.println("Opção inválida. Criação de conta cancelada.");
                return null;
        }
    }
    
    private void exibirMenuDaConta(Cliente cliente) {
        Conta conta = cliente.getConta();
        
        while (true) {
            System.out.println("\n--- Menu de Operações ---");
            System.out.println("1 - Ver Extrato");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Fazer Logout");
            System.out.print("Escolha uma opção: ");
            
            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1: conta.imprimirExtrato(); break;
                    case 2: processarDeposito(conta); break;
                    case 3: processarSaque(conta); break;
                    case 4: processarTransferencia(conta); break;
                    case 5:
                        System.out.println("Fazendo logout...");
                        return;
                    default: System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nERRO: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }

    private void processarDeposito(Conta conta) {
        System.out.print("Qual o valor do depósito? R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        conta.depositar(valor);
    }
    
    private void processarSaque(Conta conta) {
        System.out.print("Qual o valor do saque? R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        try {
            conta.sacar(valor);
        } catch (Conta.SaldoInsuficienteException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private void processarTransferencia(Conta contaOrigem) {
        System.out.print("Digite o e-mail do destinatário: ");
        String emailDestino = scanner.nextLine();
        
        Optional<Cliente> clienteDestinoOpt = banco.buscarClientePorEmail(emailDestino);
        
        if (clienteDestinoOpt.isEmpty()) {
            System.out.println("ERRO: Conta de destino não encontrada.");
            return;
        }

        System.out.print("Qual o valor a transferir? R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        
        try {
            contaOrigem.transferir(valor, clienteDestinoOpt.get().getConta());
        } catch (Conta.SaldoInsuficienteException e) {
            System.out.println("ERRO na transferência: " + e.getMessage());
        }
    }
}