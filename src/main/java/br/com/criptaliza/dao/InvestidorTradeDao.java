package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.Investidor;
import br.com.criptaliza.model.entities.InvestidorTrade;
import br.com.criptaliza.model.entities.Trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvestidorTradeDao {

    private final Connection connection;

    public InvestidorTradeDao(Connection connection) {
        this.connection = connection;
    }

    // 2. CADASTRAR:
    public void cadastrar(InvestidorTrade it) throws SQLException {
        String sql = "INSERT INTO t_investidor_trade (cd_investidor_trade, cd_investidor, cd_trade) " +
                "VALUES (seq_investidor_trade.nextval, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, it.getInvestidor().getId());
            stm.setLong(2, it.getTrade().getId());

            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Associação Investidor-Trade cadastrada com sucesso!");
            }
        }
    }

    // 3. PESQUISAR:
    public InvestidorTrade pesquisar(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_investidor_trade WHERE cd_investidor_trade = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet result = stm.executeQuery()) {
                if (!result.next()) {
                    throw new EntidadeNaoEncontradaException("Associação Investidor-Trade não encontrada!");
                }
                return parseInvestidorTrade(result);
            }
        }
    }

    // 4. LISTAR:
    public List<InvestidorTrade> listar() throws SQLException {
        String sql = "SELECT * FROM t_investidor_trade";
        List<InvestidorTrade> listaIT = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {
                listaIT.add(parseInvestidorTrade(result));
            }
        }
        return listaIT;
    }

    // 5. ATUALIZAR:
    public void atualizar(InvestidorTrade it) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "UPDATE t_investidor_trade SET cd_investidor = ?, cd_trade = ? WHERE cd_investidor_trade = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, it.getInvestidor().getId());
            stm.setLong(2, it.getTrade().getId());
            stm.setLong(3, it.getId());

            int linhasAlteradas = stm.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Associação Investidor-Trade ID " + it.getId() + " não encontrada para atualização.");
            }
            System.out.println("A associação Investidor-Trade " + it.getId() + " foi atualizada com sucesso!");
        }
    }

    // 6. REMOVER:
    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_investidor_trade WHERE cd_investidor_trade = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);

            int linhasAlteradas = stm.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Associação Investidor-Trade ID " + id + " não encontrada para exclusão.");
            }
            System.out.println("Associação Investidor-Trade ID " + id + " removida com sucesso!");
        }
    }

    private InvestidorTrade parseInvestidorTrade(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_investidor_trade");

        long idInvestidor = result.getLong("cd_investidor");
        Investidor invDummy = new Investidor();
        invDummy.setId(idInvestidor);

        long idTrade = result.getLong("cd_trade");
        Trade tradeDummy = new Trade();
        tradeDummy.setId(idTrade);

        return new InvestidorTrade(id, invDummy, tradeDummy);
    }
}
