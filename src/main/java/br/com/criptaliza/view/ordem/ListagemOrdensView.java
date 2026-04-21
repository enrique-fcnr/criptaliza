package br.com.criptaliza.view.ordem;

import br.com.criptaliza.dao.OrdemDao;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Ordem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListagemOrdensView {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {

            // 1. Instanciar o DAO
            OrdemDao ordemDao = new OrdemDao(connection);

            System.out.println("\nLISTAGEM GERAL DE ORDENS: ");

            // 2. Chamar o método listar do DAO
            List<Ordem> listaOrdens = ordemDao.listar();

            if (!listaOrdens.isEmpty()) {
                for (Ordem o : listaOrdens) {
                    // Exibição formatada dos dados da Ordem
                    System.out.println(
                            "ID: " + o.getId() +
                                    " | Carteira: " + o.getCarteira().getId() +
                                    " | Ativo: " + o.getAtivo() +
                                    " | Qtd: " + o.getQuantidade() +
                                    " | Preço: R$ " + o.getPreco() +
                                    " | Tipo: " + o.getTipo() +
                                    " | Status: " + o.getStatus()
                    );
                }
                System.out.println("----------------------------------------");
                System.out.println("Total de ordens encontradas: " + listaOrdens.size());
            } else {
                System.out.println("Nenhuma ordem encontrada no banco de dados.");
            }

        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}