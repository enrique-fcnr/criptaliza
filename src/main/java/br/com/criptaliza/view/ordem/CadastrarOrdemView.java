package br.com.criptaliza.view.ordem;

import br.com.criptaliza.dao.OrdemDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Ordem;
import br.com.criptaliza.model.enums.TipoOrdem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class CadastrarOrdemView {
    public static void main(String[] args) {

        // 1. Tentar estabelecer a conexão
        try (Connection connection = ConnectionFactory.getConnection()) {

            // 2. Instanciar o DAO
            Ordem novaOrdem = getOrdem(connection);

            // Se não disparar exception, sucesso!
            System.out.println("Ordem de " + novaOrdem.getTipo() + " de " + novaOrdem.getAtivo() + " cadastrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Ordem getOrdem(Connection connection) throws SQLException {
        OrdemDao dao = new OrdemDao(connection);

        // 3. Criar o objeto "Pai" (Carteira) apenas com o ID
        // IMPORTANTE: O ID 1 deve existir na tabela T_CARTEIRA!
        Carteira carteiraRef = new Carteira();
        carteiraRef.setId(1L);

        // 4. Criar o objeto Ordem
        // Parametros: Carteira, Ativo, Quantidade, Preço, Tipo
        Ordem novaOrdem = new Ordem(
                carteiraRef,
                "BTC",
                new BigDecimal("0.0015"),
                new BigDecimal("350000.00"),
                TipoOrdem.COMPRA
        );

        // 5. Chamar o método de cadastro do DAO
        dao.cadastrar(novaOrdem);
        return novaOrdem;
    }
}