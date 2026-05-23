package br.com.criptaliza.view.carteira;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AtualizarCarteiraView {
    public static void main(String[] args) {
        try(Connection connection = ConnectionFactory.getConnection()){
            CarteiraDao dao = new CarteiraDao(connection);
            List<Carteira> listaCarteira = dao.listar();

           if(!listaCarteira.isEmpty()){
               long idBusca = listaCarteira.get(0).getId();
               Carteira carteira = dao.pesquisar(idBusca);
               System.out.println("\nATUALIZAR CARTEIRA:");
               carteira.setNome("Carteira Low Risk Secundária");
               dao.atualizar(carteira);
               System.out.println("Carteira ID " + idBusca + " atualizada.");
           }else {
               System.out.println("Não existem carteiras cadastrados para testar a atualização.");
           }

        }catch (EntidadeNaoEncontradaException e) {
            System.err.println("Carteira não encontrada!");
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
