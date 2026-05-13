package br.com.criptaliza.view;

import br.com.criptaliza.dao.*;
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
    private static void menuOrdens(Connection conn) throws SQLException {
        OrdemDao dao = new OrdemDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU ORDENS] 1.Nova Ordem 2.Listar 3.Excluir 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    System.out.print("ID Carteira: "); long idC = lerLongo();
                    System.out.print("Ativo: "); String at = leitor.nextLine();
                    System.out.print("Preço: "); BigDecimal pr = new BigDecimal(leitor.nextLine());
                    Carteira cart = new Carteira(); cart.setId(idC);
                    dao.cadastrar(new Ordem(cart, at, BigDecimal.ONE, pr, TipoOrdem.COMPRA));
                } else if (op == 2) {
                    dao.listar().forEach(System.out::println);
                } else if (op == 3) {
                    System.out.print("ID Ordem para excluir: "); dao.remover(lerLongo());
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    // --- MÓDULO 6: TRADES ---
    private static void menuTrades(Connection conn) throws SQLException {
        TradeDao dao = new TradeDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU TRADES] 1.Registrar Execução 2.Listar 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    System.out.print("ID Ordem: "); long idO = lerLongo();
                    System.out.print("Preço Executado: "); BigDecimal px = new BigDecimal(leitor.nextLine());
                    Ordem ord = new Ordem(); ord.setId(idO);
                    dao.cadastrar(new Trade(ord, px, BigDecimal.ONE, LocalDateTime.now()));
                } else if (op == 2) {
                    dao.listar().forEach(t -> System.out.println("Trade ID: " + t.getId() + " | Preço: " + t.getPrecoExec()));
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    // --- MÓDULO 7: VÍNCULO INV/TRADE ---
    private static void menuVinculo(Connection conn) throws SQLException {
        InvestidorTradeDao dao = new InvestidorTradeDao(conn);
        int op = -1;
        while (op != 0) {
            System.out.println("\n[MENU VÍNCULOS] 1.Vincular Investidor ao Trade 2.Listar 0.Voltar");
            op = lerInteiro();
            try {
                if (op == 1) {
                    System.out.print("ID Investidor: "); long idI = lerLongo();
                    System.out.print("ID Trade: "); long idT = lerLongo();
                    Investidor inv = new Investidor(); inv.setId(idI);
                    Trade tr = new Trade(); tr.setId(idT);
                    dao.cadastrar(new InvestidorTrade(inv, tr));
                } else if (op == 2) {
                    dao.listar().forEach(v -> System.out.println("Vínculo ID: " + v.getId() + " | Inv: " + v.getInvestidor().getId() + " | Trade: " + v.getTrade().getId()));
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
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