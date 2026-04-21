package br.com.criptaliza.view.ordem;

import br.com.criptaliza.dao.OrdemDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Ordem;
import br.com.criptaliza.model.enums.StatusOrdem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AtualizarOrdemView {
    public static void main(String[] args) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            OrdemDao dao = new OrdemDao(connection);

            // 1. Listar para garantir que temos um ID válido para teste
            List<Ordem> lista = dao.listar();

            if (!lista.isEmpty()) {
                // Pegamos a primeira ordem da lista
                long idBusca = lista.get(0).getId();

                // 2. Pesquisamos a ordem completa no banco
                Ordem ordem = dao.pesquisar(idBusca);

                System.out.println("\n--- ATUALIZAR ORDEM ---");
                System.out.println("Ativo: " + ordem.getAtivo());
                System.out.println("Status anterior: " + ordem.getStatus());

                // 3. Alteramos o Status (Simulando que a ordem foi executada na corretora)
                ordem.setStatus(StatusOrdem.EXECUTADA);

                // 4. Chamamos o DAO para fazer o UPDATE no Oracle
                dao.atualizar(ordem);

                System.out.println("Ordem ID " + idBusca + " atualizada para o status: " + ordem.getStatus());
            } else {
                System.out.println("Não existem ordens cadastradas para testar a atualização.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}