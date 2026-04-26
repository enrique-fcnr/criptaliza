package br.com.criptaliza.dao;

import br.com.criptaliza.model.entities.Ordem;
import br.com.criptaliza.model.entities.Trade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
            stm.setFloat(2, trade.getPrecoExec());
            stm.setFloat(3, trade.getQuantidade());
            stm.setTimestamp(4, new Timestamp(trade.getDataExec().getTime()));

            stm.executeUpdate();
            System.out.println("Trade registrado com sucesso!");
        }
    }

    // Método para buscar um trade pelo ID da ordem, caso precise verificar a execução
    public Trade buscarPorOrdem(Long ordemId) throws SQLException {
        String sql = "SELECT * FROM t_trade WHERE cd_ordem = ?";
        
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, ordemId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    // Aqui você retornaria o Trade montado. 
                    // Certifique-se de ter um construtor na classe Trade que aceite esses campos.
                    return new Trade(null, rs.getFloat("vl_preco_exec"), rs.getFloat("nr_quantidade"), rs.getTimestamp("dt_exec"));
                }
            }
        }
        return null;
    }
}
