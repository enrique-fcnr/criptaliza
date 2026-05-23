package br.com.criptaliza.view.carteira;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RemoverCarteiraView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){
            CarteiraDao dao = new CarteiraDao(connection);

            List<Carteira> listaCarteiras = dao.listar();

            if(!listaCarteiras.isEmpty()){
                long idParaRemover = listaCarteiras.get(0).getId();
                String nomeCarteira = listaCarteiras.get(0).getNome();
                dao.deletar(idParaRemover);
                System.out.println("Sucesso: Carteira ID: " + idParaRemover + " | Nome: "+ nomeCarteira + " removida com sucesso! ");

            }else {
                System.out.println("Não há carteiras no banco de dados para deletar!");
            }
        }catch (EntidadeNaoEncontradaException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro de Banco de Dados: " + e.getMessage());
        }
    }
}
