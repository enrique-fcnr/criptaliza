package application;

import entities.Carteira;
import entities.Cliente;
import entities.Investidor;
import entities.PerfilInvestidor;

import java.util.ArrayList;
import java.util.HashMap;

public class program {
    public static void main(String[] args){
        // 1. Criar listas
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Carteira> carteiras = new ArrayList<>();

        // 2. Criar clientes
        Cliente c1 = new Cliente("João", "joao@email.com", "11999999999", "pt-BR");
        Cliente c2 = new Cliente("Maria", "maria@email.com", "11888888888", "pt-BR");
        clientes.add(c1);
        clientes.add(c2);

        // 3. Criar perfis de investidor
        PerfilInvestidor perfil1 = new PerfilInvestidor(
                "Aposentadoria", "Baixa", "Longo prazo", "Iniciante", "Renda fixa", "Evitar riscos"
        );

        PerfilInvestidor perfil2 = new PerfilInvestidor(
                "Multiplicação de capital", "Alta", "Curto prazo", "Avançado", "Criptomoedas", "Evitar renda fixa"
        );

        // 4. Criar investidores
        Investidor inv1 = new Investidor(perfil1, "Conservador", 30, "Investimentos de baixo risco");
        Investidor inv2 = new Investidor(perfil2, "Moderado", 60, "Equilíbrio entre risco e retorno");

        // 5. Criar carteiras
        Carteira cart1 = new Carteira(inv1, "Carteira de Renda Fixa");
        Carteira cart2 = new Carteira(inv2, "Carteira de Cripto Ativos");
        carteiras.add(cart1);
        carteiras.add(cart2);

        // 6. Associar investidores aos clientes
        c1.adicionarInvestidor(inv1);
        c2.adicionarInvestidor(inv2);

        // ==== Implementação do HashMap ====
        // Mapeando cada cliente para suas carteiras
        HashMap<Cliente, ArrayList<Carteira>> mapaClienteCarteira = new HashMap<>();

        // Inicializa listas de carteiras para cada cliente
        mapaClienteCarteira.put(c1, new ArrayList<>());
        mapaClienteCarteira.put(c2, new ArrayList<>());

        // Adiciona carteiras correspondentes aos clientes
        mapaClienteCarteira.get(c1).add(cart1);
        mapaClienteCarteira.get(c2).add(cart2);

        // 7. Listar clientes
        System.out.println("=== CLIENTES CADASTRADOS ===");
        for (Cliente cli : clientes) {
            System.out.println(cli.getNome() + " - " + cli.getEmail());
        }

        // 8. Listar carteiras usando HashMap
        System.out.println("\n=== CARTEIRAS POR CLIENTE ===");
        for (Cliente cli : mapaClienteCarteira.keySet()) {
            System.out.println("Cliente: " + cli.getNome());
            for (Carteira car : mapaClienteCarteira.get(cli)) {
                System.out.println("  Carteira: " + car.getNome());
            }
        }

        // 9. Adicionar ativos nas carteiras
        cart2.adicionarAtivo("BTC", 0.5f);
        cart2.adicionarAtivo("ETH", 2.0f);

        // 10. Mostrar ativos da carteira cripto
        System.out.println("\nAtivos da carteira '" + cart2.getNome() + "':");
        System.out.println(cart2.getAtivos());
    }
}
