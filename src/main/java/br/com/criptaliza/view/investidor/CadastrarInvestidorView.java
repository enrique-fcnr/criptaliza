package br.com.criptaliza.view.investidor;

import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;

public class CadastrarInvestidorView {
    public static void main(String[] args) {

        // 1. Tentar estabelecer a conexão com o banco
        try (Connection connection = ConnectionFactory.getConnection()) {

            // 2. Instanciar o DAO passando a conexão
            InvestidorDao dao = new InvestidorDao(connection);

            // 3. Criar o objeto Investidor (os dados que serão salvos)
            // Lembre-se: o ID do cliente 1 deve existir na tabela T_CLIENTE!
            Investidor novoInvestidor = new Investidor(1L, "Moderado", 58);

            // 4. Chamar o método de cadastro
            dao.cadastrar(novoInvestidor);

            // Se chegou aqui sem erro, deu certo!
            System.out.println("Investidor cadastrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

}
