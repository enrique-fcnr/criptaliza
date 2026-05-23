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

    public void cadastrar(Trade trade) throws SQLException {
        // SQL para inserir o Trade na tabela T_TRADE
        String sql = "INSERT INTO t_trade (cd_trade, cd_ordem, vl_preco_exec, nr_quantidade, dt_exec) VALUES (seq_trade.nextval, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, trade.getOrdem().getId());
            stm.setBigDecimal(2, trade.getPrecoExec());
            stm.setBigDecimal(3, trade.getQuantidade());
            stm.setTimestamp(4, Timestamp.valueOf(trade.getDataExec()));

            stm.executeUpdate();
            System.out.println("Trade registrado com sucesso!");
        }
    }

    public Trade pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_trade WHERE cd_trade = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) throw new EntidadeNaoEncontradaException("Trade não encontrado!");
                return parseTrade(rs);
            }
        }
    }

    public List<Trade> listar() throws SQLException {
        String sql = "SELECT * FROM t_trade";
        List<Trade> lista = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) lista.add(parseTrade(rs));
        }
        return lista;
    }

    public void atualizar(Trade trade) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE t_trade SET vl_preco_exec = ?, nr_quantidade = ? WHERE cd_trade = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setBigDecimal(1, trade.getPrecoExec());
            stm.setBigDecimal(2, trade.getQuantidade());
            stm.setLong(3, trade.getId());
            int linhas = stm.executeUpdate();
            if (linhas == 0) throw new EntidadeNaoEncontradaException("Trade ID " + trade.getId() + " não encontrado para atualização.");
            System.out.println("Trade ID " + trade.getId() + " atualizado com sucesso!");
        }
    }

    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_trade WHERE cd_trade = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            int linhas = stm.executeUpdate();
            if (linhas == 0) throw new EntidadeNaoEncontradaException("Trade ID " + id + " não encontrado para exclusão.");
            System.out.println("Trade ID " + id + " removido com sucesso!");
        }
    }

    public Trade buscarPorOrdem(Long ordemId) throws SQLException {
        String sql = "SELECT * FROM t_trade WHERE cd_ordem = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, ordemId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) return parseTrade(rs);
            }
        }
        return null;
    }

    private Trade parseTrade(ResultSet rs) throws SQLException {
        Long id = rs.getLong("cd_trade");
        long idOrdem = rs.getLong("cd_ordem");
        BigDecimal precoExec = rs.getBigDecimal("vl_preco_exec");
        BigDecimal quantidade = rs.getBigDecimal("nr_quantidade");
        LocalDateTime dataExec = rs.getTimestamp("dt_exec").toLocalDateTime();
        Ordem ord = new Ordem();
        ord.setId(idOrdem);
        Trade t = new Trade(ord, precoExec, quantidade, dataExec);
        t.setId(id);
        return t;
    }
}
