package br.com.criptaliza.view.carteira;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;

public class CadastroCarteiraView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){
            CarteiraDao dao = new CarteiraDao(connection);
            System.out.println("\nCADASTRAR CARTEIRA:");
            Investidor investidorResponsavel = new Investidor();
            investidorResponsavel.setId(1L);

            Carteira carteiraNova = new Carteira(investidorResponsavel, "Minha Carteira de Criptos Righ Risk");
            dao.cadastrar(carteiraNova);


        }catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }

    }
}
