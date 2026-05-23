package br.com.criptaliza.view.ordem;

import br.com.criptaliza.dao.OrdemDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Ordem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RemoverOrdemView {
    public static void main(String[] args) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            OrdemDao dao = new OrdemDao(connection);

            // 1. Listar para encontrar uma ordem que possa ser removida
            List<Ordem> lista = dao.listar();

            if (!lista.isEmpty()) {
                // Pegamos o ID da primeira ordem para o teste de exclusão
                long idParaRemover = lista.get(0).getId();

                System.out.println("\n--- REMOVER ORDEM ---");
                System.out.println("Tentando remover a Ordem ID: " + idParaRemover);

                // 2. Chama o método de remoção do DAO
                dao.remover(idParaRemover);

                System.out.println("Ordem removida com sucesso do banco de dados!");
            } else {
                System.out.println("Não existem ordens no banco para testar a remoção.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (SQLException e) {
            // Caso haja uma FK prendendo essa ordem (ex: em T_TRADE), o erro cairá aqui
            System.err.println("Erro de Integridade/Banco: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}