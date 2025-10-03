package services;

import models.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Banco {

    private final List<Cliente> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public void adicionarCliente(Cliente novoCliente) {
        this.clientes.add(novoCliente);
    }

    public Optional<Cliente> autenticarCliente(String email, String senha) {
        return clientes.stream()
                .filter(cliente -> cliente.getEmail().equalsIgnoreCase(email) && cliente.verificarSenha(senha))
                .findFirst();
    }

    public Optional<Cliente> buscarClientePorEmail(String email) {
        return clientes.stream()
                .filter(cliente -> cliente.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean emailJaCadastrado(String email) {
        return clientes.stream().anyMatch(cliente -> cliente.getEmail().equalsIgnoreCase(email));
    }
}