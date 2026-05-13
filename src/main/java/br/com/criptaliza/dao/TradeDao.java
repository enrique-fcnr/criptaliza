package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.Ordem;
import br.com.criptaliza.model.entities.Trade;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TradeDao {

    private final Connection connection;

    public TradeDao(Connection connection) {
        this.connection = connection;
    }

    // 2. CADASTRAR:
    public void cadastrar(Trade trade) throws SQLException {
        String sql = "INSERT INTO t_trade (cd_trade, cd_ordem, vl_preco_exec, nr_quantidade, dt_exec) " +
                "VALUES (seq_trade.nextval, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, trade.getOrdem().getId());
            stm.setBigDecimal(2, trade.getPrecoExec());
            stm.setBigDecimal(3, trade.getQuantidade());
            stm.setTimestamp(4, Timestamp.valueOf(trade.getDataExec()));

            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Trade cadastrado com sucesso!");
            }
        }
    }

    // 3. PESQUISAR:
    public Trade pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_trade WHERE cd_trade = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet result = stm.executeQuery()) {
                if (!result.next()) {
                    throw new EntidadeNaoEncontradaException("Trade não encontrado!");
                }
                return parseTrade(result);
            }
        }
    }

    // 4. LISTAR:
    public List<Trade> listar() throws SQLException {
        String sql = "SELECT * FROM t_trade";
        List<Trade> listaTrades = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {
                listaTrades.add(parseTrade(result));
            }
        }
        return listaTrades;
    }

    // 5. ATUALIZAR:
    public void atualizar(Trade trade) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE t_trade SET vl_preco_exec = ?, nr_quantidade = ?, dt_exec = ? WHERE cd_trade = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setBigDecimal(1, trade.getPrecoExec());
            stm.setBigDecimal(2, trade.getQuantidade());
            stm.setTimestamp(3, Timestamp.valueOf(trade.getDataExec()));
            stm.setLong(4, trade.getId());

            int linhasAlteradas = stm.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Trade ID " + trade.getId() + " não encontrado para atualização.");
            }
            System.out.println("O trade " + trade.getId() + " foi atualizado com sucesso!");
        }
    }

    // 6. REMOVER:
    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_trade WHERE cd_trade = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);

            int linhasAlteradas = stm.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Trade ID " + id + " não encontrado para exclusão.");
            }
            System.out.println("Trade ID " + id + " removido com sucesso!");
        }
    }

    private Trade parseTrade(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_trade");
        BigDecimal precoExec = result.getBigDecimal("vl_preco_exec");
        BigDecimal quantidade = result.getBigDecimal("nr_quantidade");

        Timestamp ts = result.getTimestamp("dt_exec");
        LocalDateTime dataExec = (ts != null) ? ts.toLocalDateTime() : null;

        long idOrdem = result.getLong("cd_ordem");
        Ordem ordemDummy = new Ordem();
        ordemDummy.setId(idOrdem);

        return new Trade(id, ordemDummy, precoExec, quantidade, dataExec);
    }
}
