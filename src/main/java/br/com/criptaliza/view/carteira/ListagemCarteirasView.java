package br.com.criptaliza.view.carteira;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListagemCarteirasView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){
            CarteiraDao carteiraDao = new CarteiraDao(connection);
            System.out.println("\nLISTAR TODAS AS CARTEIRAS: ");
            List<Carteira> listaCarteiras = carteiraDao.listar();
            for (Carteira carteira : listaCarteiras) {
                System.out.println("ID: " + carteira.getId() +
                        " | Nome: " + carteira.getNome() +
                        " | Investidor ID: " + carteira.getInvestidor().getId() +
                        " | Criada em: " + carteira.getDataCriacao());
            }

        }catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}
