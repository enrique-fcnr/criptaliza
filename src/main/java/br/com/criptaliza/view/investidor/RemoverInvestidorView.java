package br.com.criptaliza.view.investidor;

import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Investidor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RemoverInvestidorView {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            InvestidorDao investidorDao = new InvestidorDao(connection);

            // 1. Listamos para encontrar um ID real e testar
            List<Investidor> lista = investidorDao.listarTodos();

            if (!lista.isEmpty()) {
                // Pegamos o ID do primeiro investidor da lista
                long idParaRemover = lista.get(0).getId();

                System.out.println("Tentando remover investidor ID: " + idParaRemover);

                investidorDao.deletar(idParaRemover);

                System.out.println("Sucesso: Investidor removido com sucesso!");
            } else {
                System.out.println("Não há investidores no banco para deletar.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        }
    }
}
