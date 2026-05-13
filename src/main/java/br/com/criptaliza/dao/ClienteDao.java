package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private final Connection connection;

    // 1. CONSTRUTOR ATUALIZADO: Recebe a conexão por parâmetro
    public ClienteDao(Connection connection) {
        this.connection = connection;
    }

    // 2. CADASTRAR:
    public void cadastrar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO t_cliente (cd_cliente, nm_cliente, email, telefone, idioma) VALUES (seq_cliente.nextval, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getTelefone());
            stm.setString(4, cliente.getIdioma());
            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cliente cadastrado com sucesso!");
            }
        }
    }

    // 3. PESQUISAR:
    public Cliente pesquisar(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_cliente WHERE cd_cliente = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, codigo);
            try (ResultSet result = stm.executeQuery()) {
                if (!result.next()) {
                    throw new EntidadeNaoEncontradaException("Cliente não encontrado!");
                }
                return parseCliente(result);
            }
        }
    }

    // 4. LISTAR
    public List<Cliente> listar() throws SQLException {
        String sql = "SELECT * FROM t_cliente";
        List<Cliente> lista = new ArrayList<>();

        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {
                lista.add(parseCliente(result));
            }
        }
        return lista;
    }

    // 5. ATUALIZAR
    public void atualizar(Cliente cliente) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE t_cliente SET nm_cliente = ?, email = ?, telefone = ?, idioma = ? WHERE cd_cliente = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getEmail());
            stm.setString(3, cliente.getTelefone());
            stm.setString(4, cliente.getIdioma());
            stm.setLong(5, cliente.getId());

            int linhas = stm.executeUpdate();
            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Não foi possível atualizar o cliente ID: " + cliente.getId());
            }
            System.out.println("O cliente " + cliente.getId() + " foi atualizado.");
        }

    }

    // 6. REMOVER
    public void remover(long codigo) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_cliente WHERE cd_cliente = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, codigo);
            int linha = stm.executeUpdate();
            if (linha == 0) {
                throw new EntidadeNaoEncontradaException("Cliente não encontrado para ser removido.");
            }
        }
    }

    // Método Auxiliar
    private Cliente parseCliente(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_cliente");
        String nome = result.getString("nm_cliente");
        String email = result.getString("email");
        String telefone = result.getString("telefone");
        String idioma = result.getString("idioma");
        return new Cliente(id, nome, email, telefone, idioma);
    }
}