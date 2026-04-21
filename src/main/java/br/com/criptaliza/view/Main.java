package br.com.criptaliza.view;

import br.com.criptaliza.dao.CarteiraDao;
import br.com.criptaliza.dao.ClienteDao;
import br.com.criptaliza.dao.InvestidorDao;
import br.com.criptaliza.dao.OrdemDao; // Novo Import
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.Carteira;
import br.com.criptaliza.model.entities.Cliente;
import br.com.criptaliza.model.entities.Investidor;
import br.com.criptaliza.model.entities.Ordem; // Novo Import
import br.com.criptaliza.model.enums.StatusOrdem;
import br.com.criptaliza.model.enums.TipoOrdem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("\n===========================================");
        System.out.println("   INICIANDO TESTE DE INTEGRAÇÃO ORACLE    ");
        System.out.println("===========================================");

        try (Connection connection = ConnectionFactory.getConnection()) {
            System.out.println("--- CONNECTION: Conexão realizada com sucesso! ---");

            // 1. Instanciar todos os DAOs necessários
            ClienteDao clienteDao = new ClienteDao(connection);
            InvestidorDao investidorDao = new InvestidorDao(connection);
            CarteiraDao carteiraDao = new CarteiraDao(connection);
            OrdemDao ordemDao = new OrdemDao(connection); // Novo DAO

            // ---------------------------------------------------------
            // SEÇÃO 1: CLIENTE
            // ---------------------------------------------------------
            System.out.println("\n--- [ TESTES CLIENTE ] ---");
            Cliente novoCliente = new Cliente("Pedro Silva", "pedro_silva@gmail.com", "98563547", "Francês");
            clienteDao.cadastrar(novoCliente);

            List<Cliente> listaClientes = clienteDao.listar();
            if (!listaClientes.isEmpty()) {
                Cliente clienteAtual = listaClientes.get(listaClientes.size() - 1);
                System.out.println("Listagem: Encontrado Cliente ID " + clienteAtual.getId());

                // ---------------------------------------------------------
                // SEÇÃO 2: INVESTIDOR
                // ---------------------------------------------------------
                System.out.println("\n--- [ TESTES INVESTIDOR ] ---");
                Investidor novoInv = new Investidor(clienteAtual.getId(), "Holder", 45);
                investidorDao.cadastrar(novoInv);

                List<Investidor> listaInvs = investidorDao.listarTodos();
                if (!listaInvs.isEmpty()) {
                    Investidor invAtual = listaInvs.get(listaInvs.size() - 1);
                    System.out.println("Listagem: Encontrado Investidor ID " + invAtual.getId());

                    // ---------------------------------------------------------
                    // SEÇÃO 3: CARTEIRA
                    // ---------------------------------------------------------
                    System.out.println("\n--- [ TESTES CARTEIRA ] ---");
                    Carteira novaCar = new Carteira(invAtual, "Reserva de Cripto Ativos");
                    carteiraDao.cadastrar(novaCar);

                    List<Carteira> listaCars = carteiraDao.listar();
                    if (!listaCars.isEmpty()) {
                        Carteira carParaTeste = listaCars.get(listaCars.size() - 1);
                        System.out.println("Listagem: Carteira '" + carParaTeste.getNome() + "' recuperada.");

                        // ---------------------------------------------------------
                        // SEÇÃO 4: ORDEM (Depende de Carteira)
                        // ---------------------------------------------------------
                        System.out.println("\n--- [ TESTES ORDEM ] ---");

                        // TESTE: Cadastrar Ordem
                        Ordem novaOrdem = new Ordem(
                                carParaTeste,
                                "BTC",
                                new BigDecimal("0.005"),
                                new BigDecimal("320000.00"),
                                TipoOrdem.COMPRA
                        );
                        ordemDao.cadastrar(novaOrdem);
                        System.out.println("Cadastro: Ordem de compra de BTC enviada ao banco.");

                        // TESTE: Listar e Pesquisar Ordem
                        List<Ordem> listaOrdens = ordemDao.listar();
                        if (!listaOrdens.isEmpty()) {
                            Ordem ordemTeste = listaOrdens.get(listaOrdens.size() - 1);
                            System.out.println("Listagem: Ordem ID " + ordemTeste.getId() + " de " + ordemTeste.getAtivo() + " recuperada.");

                            try {
                                // TESTE: Pesquisar por ID
                                Ordem encontrada = ordemDao.pesquisar(ordemTeste.getId());

                                // TESTE: Atualizar Status da Ordem
                                encontrada.setStatus(StatusOrdem.EXECUTADA);
                                ordemDao.atualizar(encontrada);
                                System.out.println("Update: Ordem ID " + encontrada.getId() + " marcada como EXECUTADA.");

                            } catch (EntidadeNaoEncontradaException e) {
                                System.err.println("Erro de Negócio na Ordem: " + e.getMessage());
                            }
                        }
                    }
                }
            } else {
                System.out.println("Atenção: Nenhum cliente na lista. Testes abortados.");
            }

            System.out.println("\n===========================================");
            System.out.println("       TODOS OS TESTES FINALIZADOS         ");
            System.out.println("===========================================");

        } catch (SQLException e) {
            System.err.println("\n ERRO DE BANCO DE DADOS: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n ERRO INESPERADO: ");
            e.printStackTrace();
        }
    }
}