package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Ordem;
import br.com.criptaliza.model.enums.StatusOrdem;
import br.com.criptaliza.model.enums.TipoOrdem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// ... (mantenha os seus imports)

public class OrdemDao {
    private Connection connection;

    public OrdemDao(Connection connection) {
        this.connection = connection;
    }

    // 1. CADASTRAR (Está correto!)
    public void cadastrar(Ordem ordem) throws SQLException {
        String ativo = ordem.getAtivo().toUpperCase().trim();
        ordem.setAtivo(ativo);

        if (ordem.getTipo() == TipoOrdem.VENDA) {
            BigDecimal saldo = consultarSaldoDisponivel(ordem.getCarteira().getId(), ativo);
            if (saldo.compareTo(ordem.getQuantidade()) < 0) {
                throw new SQLException("Saldo insuficiente! Você possui apenas " + saldo + " de " + ativo);
            }
        }

        String sql = "INSERT INTO t_ordem (cd_ordem, cd_carteira, nm_ativo, nr_quantidade, vl_preco, tp_ordem, st_ordem) " +
                "VALUES (seq_ordem.nextval, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, ordem.getCarteira().getId());
            stm.setString(2, ativo);
            stm.setBigDecimal(3, ordem.getQuantidade());
            stm.setBigDecimal(4, ordem.getPreco());
            stm.setString(5, ordem.getTipo().name());
            stm.setString(6, (ordem.getStatus() == null ? StatusOrdem.CRIADA.name() : ordem.getStatus().name()));
            stm.executeUpdate();
        }
    }

    // 2. EXECUTAR ORDEM (Está correto!)
    public void executarOrdem(long idOrdem) throws SQLException {
        String sql = "UPDATE t_ordem SET st_ordem = 'EXECUTADA' WHERE cd_ordem = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, idOrdem);
            int rows = stm.executeUpdate();
            if (rows > 0) System.out.println("Ordem #" + idOrdem + " executada! Saldo atualizado.");
            else System.out.println("Ordem não encontrada.");
        }
    }

    // 3. BUSCAR POR ID (Ajustado para usar o mapear)
    public Ordem buscarPorId(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "SELECT * FROM t_ordem WHERE cd_ordem = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        throw new EntidadeNaoEncontradaException("Ordem não encontrada.");
    }

    // 4. EXIBIR POSIÇÃO (AJUSTADO: Filtro para EXECUTADA para manter coerência)
    public void exibirPosicaoConsolidada(long idCarteira) throws SQLException {
        String sql = "SELECT UPPER(TRIM(nm_ativo)) AS ativo, " +
                "SUM(CASE WHEN tp_ordem = 'COMPRA' THEN nr_quantidade ELSE -nr_quantidade END) AS total " +
                "FROM t_ordem " +
                "WHERE cd_carteira = ? AND UPPER(TRIM(st_ordem)) = 'EXECUTADA' " + // MUDANÇA AQUI
                "GROUP BY UPPER(TRIM(nm_ativo)) " +
                "HAVING SUM(CASE WHEN tp_ordem = 'COMPRA' THEN nr_quantidade ELSE -nr_quantidade END) > 0";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, idCarteira);
            try (ResultSet rs = stm.executeQuery()) {
                System.out.println("\n--- SUA CARTEIRA (ATIVOS EXECUTADOS) ---");
                boolean temAtivos = false;
                while (rs.next()) {
                    temAtivos = true;
                    System.out.printf("Ativo: %-10s | Quantidade Total: %s%n",
                            rs.getString("ativo"), rs.getBigDecimal("total"));
                }
                if (!temAtivos) System.out.println("Sua carteira está vazia (sem ordens executadas).");
            }
        }
    }

    // 5. CONSULTAR SALDO (Está correto!)
    public BigDecimal consultarSaldoDisponivel(long idCarteira, String ativo) throws SQLException {
        String sql = "SELECT SUM(CASE WHEN UPPER(TRIM(tp_ordem)) = 'COMPRA' THEN nr_quantidade ELSE -nr_quantidade END) AS saldo " +
                "FROM t_ordem " +
                "WHERE cd_carteira = ? AND UPPER(TRIM(nm_ativo)) = ? AND st_ordem = 'EXECUTADA'";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, idCarteira);
            stm.setString(2, ativo.toUpperCase().trim());
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    BigDecimal res = rs.getBigDecimal("saldo");
                    return (res != null) ? res : BigDecimal.ZERO;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // 6. LISTAR (Ajustado para usar o mapear)
    public List<Ordem> listar() throws SQLException {
        List<Ordem> lista = new ArrayList<>();
        String sql = "SELECT * FROM t_ordem ORDER BY cd_ordem DESC";
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs)); // Usa o mapeador
            }
        }
        return lista;
    }

    // 7. CANCELAR
    public void cancelar(long idOrdem) throws SQLException {
        String sql = "UPDATE t_ordem SET st_ordem = 'CANCELADA' WHERE cd_ordem = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, idOrdem);
            stm.executeUpdate();
            System.out.println("Ordem cancelada.");
        }
    }

    // 8. MAPEADOR (Helper)
    private Ordem mapear(ResultSet rs) throws SQLException {
        Ordem ordem = new Ordem();
        ordem.setId(rs.getLong("cd_ordem"));
        ordem.setAtivo(rs.getString("nm_ativo"));
        ordem.setQuantidade(rs.getBigDecimal("nr_quantidade"));
        ordem.setPreco(rs.getBigDecimal("vl_preco"));
        ordem.setTipo(TipoOrdem.valueOf(rs.getString("tp_ordem")));
        ordem.setStatus(StatusOrdem.valueOf(rs.getString("st_ordem")));

        Carteira carteira = new Carteira();
        carteira.setId(rs.getLong("cd_carteira"));
        ordem.setCarteira(carteira);
        return ordem;
    }
}