package application;

import entities.Carteira;
import entities.Cliente;
import entities.Investidor;
import entities.PerfilInvestidor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Program {

    public static void main(String[] args) {

        // ===== 1. Criar clientes =====
        Cliente c1 = new Cliente("João", "joao@email.com", "11999999999", "pt-BR");
        Cliente c2 = new Cliente("Maria", "maria@email.com", "11888888888", "pt-BR");

        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(c1);
        clientes.add(c2);

        // ===== 2. Criar perfis de investidor =====
        PerfilInvestidor perfil1 = new PerfilInvestidor(
                "Aposentadoria", "Baixa", "Longo prazo", "Iniciante", "Renda fixa", "Evitar riscos"
        );

        PerfilInvestidor perfil2 = new PerfilInvestidor(
                "Multiplicação de capital", "Alta", "Curto prazo", "Avançado", "Criptomoedas", "Evitar renda fixa"
        );

        // ===== 3. Criar investidores =====
        Investidor inv1 = new Investidor(perfil1, "Conservador", 30, "Investimentos de baixo risco");
        Investidor inv2 = new Investidor(perfil2, "Moderado", 60, "Equilíbrio entre risco e retorno");

        // ===== 4. Criar carteiras =====
        Carteira cart1 = new Carteira(inv1, "Carteira de Renda Fixa");
        Carteira cart2 = new Carteira(inv2, "Carteira de Cripto Ativos");

        // Adicionar ativos à carteira de cripto
        cart2.adicionarAtivo("BTC", 0.5f);
        cart2.adicionarAtivo("ETH", 2.0f);

        // ===== 5. Associar investidores aos clientes =====
        c1.adicionarInvestidor(inv1);
        c2.adicionarInvestidor(inv2);

        // ===== 6. Criar HashMap Cliente -> Carteiras =====
        HashMap<Cliente, ArrayList<Carteira>> mapaClienteCarteira = new HashMap<>();
        mapaClienteCarteira.put(c1, new ArrayList<>());
        mapaClienteCarteira.put(c2, new ArrayList<>());

        mapaClienteCarteira.get(c1).add(cart1);
        mapaClienteCarteira.get(c2).add(cart2);

        // ===== 7. Listar clientes e carteiras no console =====
        System.out.println("=== CLIENTES E SUAS CARTEIRAS ===");
        for (Cliente cliente : mapaClienteCarteira.keySet()) {
            System.out.println("Cliente: " + cliente.getNome() + " | Email: " + cliente.getEmail());
            for (Carteira carteira : mapaClienteCarteira.get(cliente)) {
                System.out.println("  Carteira: " + carteira.getNome());
                if (carteira.getAtivos() != null && !carteira.getAtivos().isEmpty()) {
                    for (String ativo : carteira.getAtivos().keySet()) {
                        System.out.println("    Ativo: " + ativo + " | Quantidade: " + carteira.getAtivos().get(ativo));
                    }
                }
            }
        }

        // ===== 8. Salvar em arquivo =====
        salvarClientesEmArquivo(mapaClienteCarteira, "clientes.txt");
    }

    // ===== Método para salvar HashMap em arquivo =====
    public static void salvarClientesEmArquivo(HashMap<Cliente, ArrayList<Carteira>> mapa, String nomeArquivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) { // sobrescreve o arquivo
            for (Cliente cliente : mapa.keySet()) {
                bw.write("Cliente: " + cliente.getNome() + " | Email: " + cliente.getEmail());
                bw.newLine();

                for (Carteira carteira : mapa.get(cliente)) {
                    bw.write("  Carteira: " + carteira.getNome());
                    bw.newLine();

                    if (carteira.getAtivos() != null && !carteira.getAtivos().isEmpty()) {
                        for (String ativo : carteira.getAtivos().keySet()) {
                            bw.write("    Ativo: " + ativo + " | Quantidade: " + carteira.getAtivos().get(ativo));
                            bw.newLine();
                        }
                    }
                }
                bw.newLine();
            }
            System.out.println("Dados salvos em " + nomeArquivo + " com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
