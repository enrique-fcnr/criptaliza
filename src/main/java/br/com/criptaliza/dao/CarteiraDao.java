package br.com.criptaliza.dao;

import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Cliente;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDao {

        private final Connection connection;

    // 1. CONSTRUTOR ATUALIZADO: Recebe a conexão por parâmetro
    public CarteiraDao(Connection connection) {
        this.connection = connection;
    }

    // 2. CADASTRAR: Usando try-with-resources:
    public void cadastrar(Carteira carteira) throws SQLException{
        String sql = "INSERT INTO t_carteira (cd_carteira, cd_investidor, nm_carteira, dt_criacao) VALUES(seq_carteira.nextval, ?, ?, ?)";

        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setLong(1, carteira.getInvestidor().getId());
            stm.setString(2, carteira.getNome());
            stm.setTimestamp(3, java.sql.Timestamp.valueOf(carteira.getDataCriacao()));
            int linhasAfetadas = stm.executeUpdate();
            if(linhasAfetadas > 0){
                System.out.println("Carteira (" + carteira.getNome() +  ") cadastrada com sucesso!");
            }
            }
    }

    // 3. PESQUISAR: Usando try-with-resources duplo (stmt e rs):
    public Carteira pesquisar(long codigo) throws SQLException, EntidadeNaoEncontradaException{
        String sql = "SELECT * FROM t_carteira WHERE cd_carteira = ?";

        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setLong(1, codigo);
            try(ResultSet result = stm.executeQuery()){
                if(!result.next()){
                    throw new EntidadeNaoEncontradaException("Carteira não encontrada!");
                }
                return parseCarteira(result);
            }
        }
    }

    // 4. LISTAR:
    public List<Carteira> listar() throws SQLException{
        String sql = "SELECT * FROM t_carteira";
        List<Carteira> listaCarteira = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);
             ResultSet result = stm.executeQuery()) {
            while (result.next()) {
                listaCarteira.add(parseCarteira(result));
            }
        }
        return listaCarteira;
    }

    // 5. ATUALIZAR:
    public void atualizar(Carteira carteira) throws SQLException, EntidadeNaoEncontradaException{
        String sql = "UPDATE t_carteira SET nm_carteira = ? WHERE cd_carteira = ?";

        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setString(1, carteira.getNome());
            stm.setLong(2, carteira.getId());

            int linhasAlteradas = stm.executeUpdate();
            if(linhasAlteradas == 0){
                throw new EntidadeNaoEncontradaException("Carteira ID" + carteira.getId() + "não encontrada para atualização.");

            }
            System.out.println("A carteira " + carteira.getNome() + " foi atualizada com sucesso!");
        }

    }

    // 6. DELETAR:
    public void deletar(Long id) throws SQLException, EntidadeNaoEncontradaException{
        String sql = "DELETE FROM t_carteira WHERE cd_carteira = ?";

        try(PreparedStatement stm = connection.prepareStatement(sql)){
            stm.setLong(1, id);
            int linhasAlteradas = stm.executeUpdate();

            if (linhasAlteradas == 0) {
                throw new EntidadeNaoEncontradaException("Carteira ID " + id + " não encontrado para exclusão.");
            }
            System.out.println("Carteira ID " + id + " removida com sucesso!");

        }
    }


    // Método Auxiliar Ajustado para a sua classe Investidor
    private Carteira parseCarteira(ResultSet result) throws SQLException {
        Long id = result.getLong("cd_carteira");
        String nome = result.getString("nm_carteira");

        java.sql.Timestamp ts = result.getTimestamp("dt_criacao");
        java.time.LocalDateTime dataCriacao = (ts != null) ? ts.toLocalDateTime() : null;

        // Recupera o ID do investidor vindo do banco
        long idInvestidor = result.getLong("cd_investidor");

        // Instancia o investidor usando o construtor vazio que você criou
        Investidor investidor = new Investidor();
        investidor.setId(idInvestidor);

        // Monta a carteira
        Carteira carteira = new Carteira();
        carteira.setId(id);
        carteira.setNome(nome);
        carteira.setDataCriacao(dataCriacao);
        carteira.setInvestidor(investidor); // Associa o objeto "dummy"

        return carteira;
    }
}
