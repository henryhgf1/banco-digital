package models;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private Conta conta;

    public Cliente(String nome, String cpf, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public Conta getConta() { return conta; }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public boolean verificarSenha(String senha) {
        return this.senha.equals(senha);
    }
}