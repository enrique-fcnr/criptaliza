package br.com.criptaliza.view.carteira;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PesquisarCarteiraView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){
            CarteiraDao dao = new CarteiraDao(connection);
            List<Carteira> listaCarteira = dao.listar();
            System.out.println("\nPESQUISAR CARTEIRA POR ID:");

            if(!listaCarteira.isEmpty()) {
                long idParaPesquisar = listaCarteira.get(0).getId();
                Carteira encontrada = dao.pesquisar(idParaPesquisar);
                System.out.println("Encontrada carteira com ID: " + idParaPesquisar + " | Nome da carteira: " + encontrada.getNome() + ". " );
            }else {
                System.out.println("O banco está vazio. Não há o que pesquisar.");
            }

        }catch (EntidadeNaoEncontradaException e) {
            // Agora o Java aceita este catch, pois o dao.pesquisar() pode lançar este erro
            System.err.println("Erro de Negócio: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }

    }
}
