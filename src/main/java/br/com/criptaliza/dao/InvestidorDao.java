package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvestidorDao {

    private final Connection connection;

    // CONSTRUTOR CORRIGIDO: Atribui a conexão recebida
    public InvestidorDao(Connection conexao) {
        this.connection = conexao;
    }

    public void cadastrar(Investidor investidor) throws SQLException {
        String sql = "INSERT INTO t_investidor (cd_investidor, cd_cliente, nm_persona, nr_pontuacao) " +
                "VALUES (seq_investidor.nextval, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, investidor.getIdCliente());
            stm.setString(2, investidor.getPersona());
            stm.setInt(3, investidor.getPontuacaoRisco());

            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Investidor cadastrado com sucesso!");
            }
        }
    }

    public List<Investidor> listarTodos() throws SQLException {
        List<Investidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM t_investidor";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(parseInvestidor(rs));
            }
        }
        return lista;
    }

    public Investidor buscarPorId(Long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_investidor WHERE cd_investidor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new EntidadeNaoEncontradaException("Investidor não encontrado!");
                }
                return parseInvestidor(rs);
            }
        }
    }

    public void atualizar(Investidor investidor) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE t_investidor SET nm_persona = ?, nr_pontuacao = ? WHERE cd_investidor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, investidor.getPersona());
            stmt.setInt(2, investidor.getPontuacaoRisco());
            stmt.setLong(3, investidor.getId());

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new EntidadeNaoEncontradaException("Não foi possível atualizar o investidor ID: " + investidor.getId());
            }
            System.out.println("O investidor " + investidor.getId() + " foi atualizado.");
        }
    }


    public void deletar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_investidor WHERE cd_investidor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int linhasApagadas = stmt.executeUpdate();
            if (linhasApagadas == 0) {
                throw new EntidadeNaoEncontradaException("Não foi possível deletar o investidor ID: " + id);
            }
            System.out.println("Investidor removido com sucesso!");
        }
    }

    private Investidor parseInvestidor(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_investidor");
        Long clienteID = result.getLong("cd_cliente");
        String persona = result.getString("nm_persona");
        int pontuacao = result.getInt("nr_pontuacao");
        return new Investidor(id, clienteID, persona, pontuacao);
    }

}