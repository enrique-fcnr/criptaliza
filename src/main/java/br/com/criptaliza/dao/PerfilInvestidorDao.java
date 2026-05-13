package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.PerfilInvestidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PerfilInvestidorDao {

    private final Connection connection;

    public PerfilInvestidorDao(Connection connection) {
        this.connection = connection;
    }

    // 2. CADASTRAR:
    public void cadastrar(PerfilInvestidor perfil) throws SQLException {
        String sql = "INSERT INTO t_perfil_investidor (cd_perfil_investidor, cd_investidor, tx_objetivo, tx_tolerancia_risco, tx_horizonte, tx_experiencia, tx_preferencias, tx_prevencoes) " +
                "VALUES (seq_perfil_investidor.nextval, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, perfil.getIdInvestidor());
            stm.setString(2, perfil.getObjetivo());
            stm.setString(3, perfil.getToleranciaRisco());
            stm.setString(4, perfil.getHorizonte());
            stm.setString(5, perfil.getExperiencia());
            stm.setString(6, perfil.getPreferencias());
            stm.setString(7, perfil.getPrevencoes());

            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Perfil de investidor cadastrado com sucesso!");
            }
        }
    }

    // 3. PESQUISAR:
    public PerfilInvestidor pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_perfil_investidor WHERE cd_perfil_investidor = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet result = stm.executeQuery()) {
                if (!result.next()) {
                    throw new EntidadeNaoEncontradaException("Perfil de investidor não encontrado!");
                }
                return parsePerfil(result);
            }
        }
    }

    // 4. LISTAR:
    public List<PerfilInvestidor> listar() throws SQLException {
        String sql = "SELECT * FROM t_perfil_investidor";
        List<PerfilInvestidor> listaPerfis = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {
                listaPerfis.add(parsePerfil(result));
            }
        }
        return listaPerfis;
    }

    // 5. ATUALIZAR:
    public void atualizar(PerfilInvestidor perfil) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE t_perfil_investidor SET tx_objetivo = ?, tx_tolerancia_risco = ?, tx_horizonte = ?, tx_experiencia = ?, tx_preferencias = ?, tx_prevencoes = ? WHERE cd_perfil_investidor = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, perfil.getObjetivo());
            stm.setString(2, perfil.getToleranciaRisco());
            stm.setString(3, perfil.getHorizonte());
            stm.setString(4, perfil.getExperiencia());
            stm.setString(5, perfil.getPreferencias());
            stm.setString(6, perfil.getPrevencoes());
            stm.setLong(7, perfil.getId());

            int linhasAlteradas = stm.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Perfil ID " + perfil.getId() + " não encontrado para atualização.");
            }
            System.out.println("O perfil de investidor " + perfil.getId() + " foi atualizado com sucesso!");
        }
    }

    // 6. REMOVER:
    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_perfil_investidor WHERE cd_perfil_investidor = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);

            int linhasAlteradas = stm.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Perfil ID " + id + " não encontrado para exclusão.");
            }
            System.out.println("Perfil ID " + id + " removido com sucesso!");
        }
    }

    private PerfilInvestidor parsePerfil(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_perfil_investidor");
        Long idInvestidor = result.getLong("cd_investidor");
        String objetivo = result.getString("tx_objetivo");
        String toleranciaRisco = result.getString("tx_tolerancia_risco");
        String horizonte = result.getString("tx_horizonte");
        String experiencia = result.getString("tx_experiencia");
        String preferencias = result.getString("tx_preferencias");
        String prevencoes = result.getString("tx_prevencoes");

        return new PerfilInvestidor(id, idInvestidor, objetivo, toleranciaRisco, horizonte, experiencia, preferencias, prevencoes);
    }
}
