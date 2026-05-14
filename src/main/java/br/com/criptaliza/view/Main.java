package br.com.criptaliza.view;

import br.com.criptaliza.dao.*;
import br.com.criptaliza.exception.EntidadeNaoEncontradaException;
import br.com.criptaliza.factory.ConnectionFactory;
import br.com.criptaliza.model.entities.*;
import br.com.criptaliza.model.enums.*;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static Scanner leitor = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection conexao = ConnectionFactory.getConnection()) {
            int modulo = -1;
            while (modulo != 0) {
                System.out.println("\n========== SISTEMA CRIPTALIZA 2026 ==========");
                System.out.println("1. CLIENTES             2. INVESTIDORES");
                System.out.println("3. PERFIL INVESTIDOR    4. CARTEIRAS");
                System.out.println("5. ORDENS               6. TRADES");
                System.out.println("7. VÍNCULO INV/TRADE    0. SAIR");
                System.out.print("Selecione o módulo: ");
                modulo = lerInteiro();

                switch (modulo) {
                    case 1: menuClientes(conexao); break;
                    case 2: menuInvestidores(conexao); break;
                    case 3: menuPerfilInvestidor(conexao); break;
                    case 4: menuCarteiras(conexao); break;
                    case 5: menuOrdens(conexao); break;
                    case 6: menuTrades(conexao); break;
                    case 7: menuVinculo(conexao); break;
                    case 0: System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro de Conexão: " + e.getMessage());
        }
    }

    private static void menuPerfil(Connection conexao) {
    }

    // --- MÓDULO 1: CLIENTES ---
    private static void menuClientes(Connection conn) throws SQLException {
        ClienteDao dao = new ClienteDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU CLIENTES] 1.Incluir 2.Listar 3.Excluir 4.Pesquisar 5.Atualizar 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    // INCLUIR
                    System.out.print("Nome: "); String nome = leitor.nextLine();
                    System.out.print("Email: "); String email = leitor.nextLine();
                    System.out.print("Telefone: "); String telefone = leitor.nextLine();
                    System.out.print("Idioma: "); String idioma = leitor.nextLine();
                    dao.cadastrar(new Cliente(nome, email, telefone, idioma));


                } else if (op == 2) {
                    // LISTAR
                    System.out.println("\n--- LISTA DE CLIENTES ---");
                    dao.listar().forEach(c -> exibirDetalhesCliente(c));

                } else if (op == 3) {
                    // EXCLUIR
                    System.out.print("ID para excluir: ");
                    dao.remover(lerLongo());
                    System.out.println("Cliente removido.");

                } else if (op == 4) {
                    // PESQUISAR
                    System.out.print("ID para busca: ");
                    Cliente c = dao.pesquisar(lerLongo());
                    System.out.println("\n--- RESULTADO DA BUSCA ---");
                    exibirDetalhesCliente(c);

                } else if (op == 5) {
                    // ATUALIZAR
                    System.out.print("Digite o ID do cliente que deseja alterar: ");
                    long idAlt = lerLongo();

                    // Buscamos os dados atuais para mostrar ao usuário:
                    Cliente clienteAtual = dao.pesquisar(idAlt);
                    System.out.println("Dados atuais: " + clienteAtual.getNome() + " (" + clienteAtual.getEmail() + ")");

                    System.out.println("\n--- Digite os NOVOS dados ---");
                    System.out.print("Novo Nome: "); String nome = leitor.nextLine();
                    System.out.print("Novo Email: "); String email = leitor.nextLine();
                    System.out.print("Novo Telefone: "); String telefone = leitor.nextLine();
                    System.out.print("Novo Idioma: "); String idioma = leitor.nextLine();

                    // Criamos o objeto com os novos dados, mantendo o mesmo ID:
                    Cliente clienteNovo = new Cliente(nome, email, telefone, idioma);
                    clienteNovo.setId(idAlt);

                    dao.atualizar(clienteNovo);

                }
            } catch (Exception e) {
                System.out.println("Atenção: " + e.getMessage());
            }
        }
    }

    // Método auxiliar para exibir os dados de forma bonitinha
    private static void exibirDetalhesCliente(Cliente c) {
        if (c != null) {
            System.out.println("ID: " + c.getId() +
                    " | Nome: " + c.getNome() +
                    " | Email: " + c.getEmail() +
                    " | Tel: " + c.getTelefone() +
                    " | Idioma: " + c.getIdioma());
        }
    }


    // --- MÓDULO 2: INVESTIDORES ---
    private static void menuInvestidores(Connection conn) throws SQLException {
        InvestidorDao dao = new InvestidorDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU INVESTIDORES] 1.Incluir 2.Listar 3.Excluir 4.Pesquisar 5.Atualizar 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    System.out.print("ID do Cliente: "); long idC = lerLongo();
                    System.out.println("Selecione o novo Perfil: 1.Conservador 2.Moderado 3.Arrojado");
                    int escolha = lerInteiro();
                    Investidor novo = getInvestidor(escolha, idC);
                    if (novo != null) dao.cadastrar(novo);

                } else if (op == 2) {
                    dao.listarTodos().forEach(i -> exibirDadosInvestidor(i));

                } else if (op == 3) {
                    System.out.print("ID para excluir: "); dao.deletar(lerLongo());

                } else if (op == 4) {
                    System.out.print("ID para pesquisar: ");
                    Investidor i = dao.buscarPorId(lerLongo());
                    exibirDadosInvestidor(i);

                } else if (op == 5) {
                    // --- FUNCIONALIDADE: ATUALIZAR ---
                    System.out.print("Digite o ID do Investidor que deseja atualizar: ");
                    long idAlt = lerLongo();

                    // Primeiro buscamos se ele existe:
                    Investidor invExistente = dao.buscarPorId(idAlt);

                    System.out.println("Perfil atual: " + invExistente.getPersona());
                    System.out.println("Escolha o NOVO Perfil: 1.Conservador 2.Moderado 3.Arrojado");
                    int escolha = lerInteiro();


                    Investidor atualizado = getInvestidor(escolha, invExistente.getIdCliente());
                    atualizado.setId(idAlt);

                    dao.atualizar(atualizado);

                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    // Método auxiliar para não repetir código de exibição - Investidor.
    private static void exibirDadosInvestidor(Investidor i) {
        System.out.println("ID: " + i.getId() + " | ID Cliente: " + i.getIdCliente() + " | Persona: " + i.getPersona() + " | Risco: " + i.getPontuacaoRisco());
    }
    private static Investidor getInvestidor(int escolha, long idC) {
        // Retornamos o objeto diretamente conforme a escolha
        switch (escolha) {
            case 1:
                return new Investidor(idC, "Conservador", 90);
            case 2:
                return new Investidor(idC, "Moderado", 60);
            case 3:
                return new Investidor(idC, "Arrojado", 35);
            default:
                return null; // Retorna null se a opção não existir
        }
    }

    // --- MÓDULO 3: PERFIL DO INVESTIDOR ---
    private static void menuPerfilInvestidor(Connection conn) throws SQLException {
        PerfilInvestidorDao dao = new PerfilInvestidorDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU PERFIL INVESTIDOR] 1.Incluir 2.Listar 3.Excluir 4.Pesquisar 5.Atualizar 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    // INCLUIR
                    System.out.print("ID do Investidor: "); long idInvestidor = lerLongo();
                    System.out.print("Objetivo: "); String objetivo = leitor.nextLine();
                    System.out.print("Tolerância ao Risco (Ex: Baixa, Média, Alta): "); String risco = leitor.nextLine();
                    System.out.print("Horizonte (Ex: 5 anos): "); String horizonte = leitor.nextLine();
                    System.out.print("Experiência (Ex: Iniciante, Avançado): "); String experiencia = leitor.nextLine();

                    dao.cadastrar(new PerfilInvestidor(idInvestidor, objetivo, risco, horizonte, experiencia));


                } else if (op == 2) {
                    // LISTAR
                    System.out.println("\n--- LISTA DE PERFIS DETALHADOS ---");
                    dao.listar().forEach(p -> exibirDadosPerfil(p));

                } else if (op == 3) {
                    // EXCLUIR
                    System.out.print("ID do Perfil para excluir: ");
                    dao.remover(lerLongo());


                } else if (op == 4) {
                    // PESQUISAR
                    System.out.print("ID do Perfil para busca: ");
                    PerfilInvestidor p = dao.pesquisar(lerLongo());
                    if (p != null) {
                        exibirDadosPerfil(p);
                    } else {
                        System.out.println("Perfil não encontrado.");
                    }

                } else if (op == 5) {
                    // ATUALIZAR
                    System.out.print("Digite o ID do Perfil que deseja atualizar: ");
                    long idAlt = lerLongo();

                    // Busca os dados atuais
                    PerfilInvestidor pAtual = dao.pesquisar(idAlt);
                    System.out.println("Atual: " + pAtual.getObjetivo() + " | " + pAtual.getToleranciaRisco());

                    System.out.println("\n--- Digite os NOVOS Dados ---");
                    System.out.print("Novo Objetivo: "); String objetivo = leitor.nextLine();
                    System.out.print("Nova Tolerância ao Risco (Ex: Baixa, Média, Alta):"); String risco = leitor.nextLine();
                    System.out.print("Novo Horizonte (Ex: 5 anos): "); String horizonte = leitor.nextLine();
                    System.out.print("Nova Experiência (Ex: Iniciante, Avançado): "); String experiencia = leitor.nextLine();

                    // Criamos o novo objeto mantendo o ID do Perfil e do Investidor original
                    PerfilInvestidor pNovo = new PerfilInvestidor(pAtual.getIdInvestidor(), objetivo, risco, horizonte, experiencia);
                    pNovo.setId(idAlt);

                    dao.atualizar(pNovo);

                }
            } catch (Exception e) {
                System.out.println("Atenção: " + e.getMessage());
            }
        }
    }

    // Método auxiliar para exibição organizada - Perfil Investidor.
    private static void exibirDadosPerfil(PerfilInvestidor p) {
        if (p != null) {
            System.out.println("ID Perfil: " + p.getId() +
                    " | ID Investidor: " + p.getIdInvestidor() +
                    " | Objetivo: " + p.getObjetivo() +
                    " | Risco: " + p.getToleranciaRisco() +
                    " | Experiência: " + p.getExperiencia());
        }
    }

    // --- MÓDULO 4: CARTEIRAS ---
    private static void menuCarteiras(Connection conn) throws SQLException {
        CarteiraDao dao = new CarteiraDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU CARTEIRAS] 1.Incluir 2.Listar 3.Excluir 4.Pesquisar 5.Atualizar 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    // 1. Coleta de dados básicos
                    System.out.print("ID do Investidor: ");
                    long idI = lerLongo();
                    System.out.print("Nome da Carteira: ");
                    String nome = leitor.nextLine();

                    // 2. Definição automática de valores iniciais
                    // Uma carteira nova nasce com saldo 0 e data de agora
                    BigDecimal investimentoInicial = BigDecimal.valueOf(0.0);
                    BigDecimal valorAtual = BigDecimal.valueOf(0.0);
                    LocalDateTime dataCriacao = LocalDateTime.now();

                    // 3. Montagem dos objetos
                    Investidor inv = new Investidor();
                    inv.setId(idI);

                    // Criando o objeto com todos os parâmetros (Certifique-se que o construtor existe na classe Carteira)
                    Carteira novaCarteira = new Carteira(
                            inv,
                            nome,
                            dataCriacao,
                            investimentoInicial,
                            valorAtual
                    );

                    dao.cadastrar(novaCarteira);

                } else if (op == 2) {
                    // LISTAR
                    System.out.println("\n--- LISTA DE CARTEIRAS ---");
                    dao.listar().forEach(c -> exibirDadosCarteira(c));

                } else if (op == 3) {
                    // EXCLUIR
                    System.out.print("ID da Carteira para excluir: ");
                    dao.deletar(lerLongo());


                } else if (op == 4) {
                    // PESQUISAR
                    System.out.print("ID da Carteira para busca: ");
                    Carteira c = dao.pesquisar(lerLongo()); // Verifique se o DAO tem o método pesquisar
                    if (c != null) {
                        exibirDadosCarteira(c);
                    } else {
                        System.out.println("Carteira não encontrada.");
                    }

                } else if (op == 5) {
                    // ATUALIZAR
                    System.out.print("Digite o ID da Carteira que deseja alterar: ");
                    long idAlt = lerLongo();

                    // Buscamos a carteira atual
                    Carteira carteiraAtual = dao.pesquisar(idAlt);
                    System.out.println("Nome atual: " + carteiraAtual.getNome());

                    System.out.print("Novo Nome para a Carteira: ");
                    String novoNome = leitor.nextLine();

                    // Mantemos o investidor original e atualizamos apenas o nome
                    carteiraAtual.setNome(novoNome);

                    dao.atualizar(carteiraAtual); // Verifique se o DAO tem o método atualizar

                }
            } catch (Exception e) {
                System.out.println("Atenção: " + e.getMessage());
            }
        }
    }

    // Método auxiliar para exibição - Carteira.
    private static void exibirDadosCarteira(Carteira c) {
        if (c != null) {
            // Definindo o formato: Dia/Mês/Ano Hora:Minuto
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            // Formatando a data (ajuste para c.getDataCriacao() se for LocalDateTime)
            String dataFormatada = (c.getDataCriacao() != null) ? c.getDataCriacao().format(fmt) : "[N/A]";

            String dono = (c.getInvestidor() != null) ? " | Investidor ID: " + c.getInvestidor().getId() : "";

            System.out.println("ID: " + c.getId() +
                    " | Nome: " + c.getNome() + dono +
                    "\n   -> Criada em: " + dataFormatada +
                    " | Total: R$ " + String.format("%.2f", c.getTotalInvestido()) +
                    " | Atual: R$ " + String.format("%.2f", c.getValorAtual())
            );
        }
    }
    // --- MÓDULO 5: ORDENS ---
    // --- MÓDULO 5: ORDENS (VERSÃO FINAL ATUALIZADA) ---
    private static void menuOrdens(Connection conn) throws SQLException {
        OrdemDao dao = new OrdemDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU ORDENS]");
            System.out.println("1. Nova Ordem (Compra/Venda)");
            System.out.println("2. Ver Histórico Completo");
            System.out.println("3. VER MINHA CARTEIRA (Soma de Executados)");
            System.out.println("4. EXECUTAR Ordem (Dar Baixa)"); // <-- NOVO: Essencial para o saldo
            System.out.println("5. Buscar Ordem por ID");         // <-- NOVO: Para conferência
            System.out.println("6. Cancelar Ordem");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            op = lerInteiro();

            try {
                switch (op) {
                    case 1:
                        System.out.print("ID Carteira: "); long idC = lerLongo();
                        System.out.print("Ativo (ex: PETR4): "); String at = leitor.nextLine();
                        System.out.print("Qtd: "); BigDecimal qtd = new BigDecimal(leitor.nextLine().replace(",", "."));
                        System.out.print("Preço: "); BigDecimal pr = new BigDecimal(leitor.nextLine().replace(",", "."));
                        System.out.print("Tipo (1-COMPRA, 2-VENDA): ");
                        TipoOrdem tipo = (lerInteiro() == 2) ? TipoOrdem.VENDA : TipoOrdem.COMPRA;

                        Carteira cart = new Carteira(); cart.setId(idC);
                        dao.cadastrar(new Ordem(cart, at, qtd, pr, tipo));
                        break;
                    case 2:
                        System.out.println("\n--- Histórico de Todas as Ordens ---");
                        dao.listar().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Digite o ID da Carteira para ver a custódia: ");
                        dao.exibirPosicaoConsolidada(lerLongo());
                        break;
                    case 4:
                        System.out.print("ID da Ordem para confirmar EXECUÇÃO: ");
                        dao.executarOrdem(lerLongo());
                        break;
                    case 5:
                        System.out.print("ID da Ordem para busca detalhada: ");
                        Ordem busca = dao.buscarPorId(lerLongo());
                        System.out.println(busca);
                        break;
                    case 6:
                        System.out.print("ID da Ordem para CANCELAR: ");
                        dao.cancelar(lerLongo());
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // --- MÓDULO 6: TRADES ---
    private static void menuTrades(Connection conn) throws SQLException {
        TradeDao tradeDao = new TradeDao(conn); // Nome aqui é tradeDao
        OrdemDao ordemDao = new OrdemDao(conn);

        int op = -1;
        while (op != 0) {
            System.out.println("\n========= MÓDULO DE TRADES ===========");
            System.out.println("1. Registrar Execução (Gera Trade)");
            System.out.println("2. Listar Histórico de Trades");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            op = lerInteiro();

            try {
                if (op == 1) {
                    System.out.println("\n--- Registro de Execução ---");
                    System.out.print("Digite o ID da Ordem a ser executada: ");
                    long idOrdem = lerLongo();

                    // 1. Buscamos a ordem no banco
                    Ordem ordem = ordemDao.buscarPorId(idOrdem);

                    System.out.print("Preço Real de Execução (ex: 25.50): ");
                    String precoStr = leitor.nextLine().replace(",", ".");
                    BigDecimal precoExec = new BigDecimal(precoStr);

                    // 2. Criamos o objeto Trade usando a quantidade da Ordem encontrada
                    Trade novoTrade = new Trade(ordem, precoExec, ordem.getQuantidade(), LocalDateTime.now());

                    // 3. Salvamos o Trade (Usando a variável correta: tradeDao)
                    tradeDao.cadastrar(novoTrade);

                    // 4. Atualizamos o status da ordem
                    ordemDao.executarOrdem(idOrdem);

                    System.out.println("Sucesso! Trade salvo e saldo liberado.");

                } else if (op == 2) {
                    System.out.println("\n--- HISTÓRICO DE TRADES ---");
                    // CORREÇÃO AQUI: Mudamos de 'dao.listar()' para 'tradeDao.listar()'
                    tradeDao.listar().forEach(t -> {
                        System.out.println("ID Trade: " + t.getId() +
                                " | ID Ordem: " + t.getOrdem().getId() +
                                " | Preço Exec: R$ " + t.getPrecoExec() +
                                " | Qtd: " + t.getQuantidade());
                    });
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
    // --- MÓDULO 7: VÍNCULO INV/TRADE ---
    /*
    Este módulo foi incluído para permitir escalabilidade,
    suportando cenários onde múltiplos investidores dividem a execução
    de um mesmo trade.
     */
    private static void menuVinculo(Connection conn) throws SQLException {
        InvestidorTradeDao dao = new InvestidorTradeDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n========= MÓDULO DE VÍNCULOS =========");
            System.out.println("1. Vincular Investidor a um Trade");
            System.out.println("2. Listar todos os Vínculos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            op = lerInteiro();

            try {
                if (op == 1) {
                    System.out.print("ID do Investidor: "); long idI = lerLongo();
                    System.out.print("ID do Trade (Execução): "); long idT = lerLongo();

                    // Criando objetos simples apenas para carregar os IDs para o DAO
                    Investidor inv = new Investidor(); inv.setId(idI);
                    Trade tr = new Trade(); tr.setId(idT);

                    dao.cadastrar(new InvestidorTrade(inv, tr));
                    System.out.println("Vínculo realizado com sucesso!");

                } else if (op == 2) {
                    System.out.println("\n--- RELAÇÃO INVESTIDOR X TRADE ---");
                    dao.listar().forEach(v -> {
                        System.out.println("Vínculo ID: " + v.getId() +
                                " | Investidor ID: " + v.getInvestidor().getId() +
                                " | Trade ID: " + v.getTrade().getId());
                    });
                }
            } catch (Exception e) {
                System.out.println("Erro ao vincular: " + e.getMessage());
            }
        }
    }



    // --- AUXILIARES ---
    private static int lerInteiro() {
        try {
            int i = leitor.nextInt(); leitor.nextLine(); return i;
        } catch (Exception e) { leitor.nextLine(); return -1; }
    }
    private static long lerLongo() {
        try {
            long l = leitor.nextLong(); leitor.nextLine(); return l;
        } catch (Exception e) { leitor.nextLine(); return -1; }
    }

}