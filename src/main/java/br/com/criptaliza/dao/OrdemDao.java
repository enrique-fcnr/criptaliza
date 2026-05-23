package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Ordem;
import br.com.criptaliza.model.enums.StatusOrdem;
import br.com.criptaliza.model.enums.TipoOrdem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemDao {

    private final Connection connection;

    // 1. CONSTRUTOR ATUALIZADO: Recebe a conexão por parâmetro
    public OrdemDao(Connection connection) {
        this.connection = connection;
    }

    // 2. CADASTRAR:
    public void cadastrar(Ordem ordem) throws SQLException {
        // SQL baseado no seu diagrama:
        // cd_ordem (sequence), cd_carteira (FK), nm_ativo, nr_quantidade, vl_preco, tp_ordem, st_ordem
        String sql = "INSERT INTO t_ordem (cd_ordem, cd_carteira, nm_ativo, nr_quantidade, vl_preco, tp_ordem, st_ordem) " +
                "VALUES (seq_ordem.nextval, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            // 1. Foreign Key: Pegamos o ID do objeto Carteira que está dentro da Ordem
            stm.setLong(1, ordem.getCarteira().getId());

            // 2. Nome do Ativo (ex: "BTC")
            stm.setString(2, ordem.getAtivo());

            // 3. Quantidade (BigDecimal garante precisão de 8 casas decimais ou mais)
            stm.setBigDecimal(3, ordem.getQuantidade());

            // 4. Preço do Ativo
            stm.setBigDecimal(4, ordem.getPreco());

            // 5. Tipo da Ordem: Convertendo o Enum para String (.name())
            // Salva como "COMPRA" ou "VENDA" no banco
            stm.setString(5, ordem.getTipo().name());

            // 6. Status da Ordem: Convertendo o Enum para String
            // Salva como "CRIADA", "EXECUTADA", etc.
            stm.setString(6, ordem.getStatus().name());

            // Executa a inserção
            stm.executeUpdate();
        }
    }

    // 3. PESQUISAR:
    public Ordem pesquisar(long codigo) throws SQLException, EntidadeNaoEncontradaException{
        String sql = "SELECT * FROM t_ordem WHERE cd_ordem = ?";

        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setLong(1, codigo);
            try (ResultSet resultSet = stm.executeQuery()){
                if (!resultSet.next()){
                    throw new EntidadeNaoEncontradaException("Ordem não encontrada!");
                }
                return parseOrdem(resultSet);
            }
        }


    }

    // 4. LISTAR:
    public List<Ordem> listar()throws SQLException{
        String sql = "SELECT * FROM t_ordem";
        List<Ordem> listaOrdem = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {
                listaOrdem.add(parseOrdem(result));
            }
        }
        return listaOrdem;
    }

    // 5. ATUALIZAR:
    public void atualizar(Ordem ordem) throws SQLException, EntidadeNaoEncontradaException {
        // Atualizamos o preço, a quantidade e o status (o ativo e a carteira geralmente são imutáveis)
        String sql = "UPDATE t_ordem SET nr_quantidade = ?, vl_preco = ?, st_ordem = ? WHERE cd_ordem = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setBigDecimal(1, ordem.getQuantidade());
            stm.setBigDecimal(2, ordem.getPreco());

            // Convertendo o Enum StatusOrdem para String para o Banco
            stm.setString(3, ordem.getStatus().name());

            stm.setLong(4, ordem.getId());

            int linhasAlteradas = stm.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Ordem ID " + ordem.getId() + " não encontrada para atualização.");
            }

            // Lembrete: Remova o println após testar para manter o DAO "limpo" (silencioso)
            System.out.println("A ordem do ativo " + ordem.getAtivo() + " foi atualizada com sucesso!");
        }
    }

    // 6. REMOVER:
    public void remover(long id) throws SQLException, EntidadeNaoEncontradaException {
        String sql = "DELETE FROM t_ordem WHERE cd_ordem = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setLong(1, id);

            int linhasAlteradas = stm.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Ordem ID " + id + " não encontrada para exclusão.");
            }

            System.out.println("Ordem ID " + id + " removida com sucesso!");
        }
    }


    private Ordem parseOrdem(ResultSet result) throws SQLException {
        // 1. Recuperar os dados básicos das colunas do banco
        Long id = result.getLong("cd_ordem");
        String ativo = result.getString("nm_ativo");
        java.math.BigDecimal quantidade = result.getBigDecimal("nr_quantidade");
        java.math.BigDecimal preco = result.getBigDecimal("vl_preco");

        // 2. Recuperar as Strings dos Enums e converter de volta para os tipos Java
        String tipoStr = result.getString("tp_ordem");
        TipoOrdem tipo = (tipoStr != null) ? TipoOrdem.valueOf(tipoStr) : null;

        String statusStr = result.getString("st_ordem");
        StatusOrdem status = (statusStr != null) ? StatusOrdem.valueOf(statusStr) : null;

        // 3. Recuperar a FK da Carteira e criar o objeto "dummy"
        long idCarteira = result.getLong("cd_carteira");
        Carteira carteira = new Carteira();
        carteira.setId(idCarteira);

        // 4. Montar o objeto Ordem completo
        Ordem ordem = new Ordem();
        ordem.setId(id);
        ordem.setAtivo(ativo);
        ordem.setQuantidade(quantidade);
        ordem.setPreco(preco);
        ordem.setTipo(tipo);
        ordem.setStatus(status);
        ordem.setCarteira(carteira); // Associa a carteira dona desta ordem

        return ordem;
    }
}