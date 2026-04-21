package br.com.criptaliza.view.ordem;

import br.com.criptaliza.dao.OrdemDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Ordem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PesquisarOrdemView {
    public static void main(String[] args) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            OrdemDao ordemDao = new OrdemDao(connection);
            System.out.println("\nPESQUISAR ORDEM PELA ID: ");

            // 1. Listamos para obter um ID válido para o teste
            List<Ordem> listaOrdens = ordemDao.listar();

            if (!listaOrdens.isEmpty()) {
                // Pegamos o ID da primeira ordem da lista para pesquisar
                long idBusca = listaOrdens.get(0).getId();

                System.out.println("\n--- PESQUISANDO ORDEM ID: " + idBusca + " ---");

                // 2. Tenta buscar a ordem pelo ID
                // Se o seu DAO lança a Exception quando não encontra, o catch tratará.
                Ordem encontrada = ordemDao.pesquisar(idBusca);

                // 3. Exibição dos dados recuperados
                System.out.println("Ativo: " + encontrada.getAtivo());
                System.out.println("Quantidade: " + encontrada.getQuantidade());
                System.out.println("Tipo: " + encontrada.getTipo());
                System.out.println("Status Atual: " + encontrada.getStatus());
                System.out.println("ID da Carteira Vinculada: " + encontrada.getCarteira().getId());

            } else {
                System.out.println("O banco não possui ordens para realizar a pesquisa de teste.");
            }

        } catch (EntidadeNaoEncontradaException e) {
            // Este bloco captura se o pesquisar() não encontrar o registro
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}