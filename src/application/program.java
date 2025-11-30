package application;

import entities.Carteira;
import entities.Cliente;
import entities.Investidor;
import entities.PerfilInvestidor;

import java.util.ArrayList;

public class program {
    public static void main(String[] args){
        // 1. Criar listas (ArrayList) ====
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Carteira> carteiras = new ArrayList<>();

        // 2. Criar clientes ====
        Cliente c1 = new Cliente("João", "joao@email.com", "11999999999", "pt-BR");
        Cliente c2 = new Cliente("Maria", "maria@email.com", "11888888888", "pt-BR");

        clientes.add(c1);
        clientes.add(c2);

        // 3. Criar perfis de investidor ====
        PerfilInvestidor perfil1 = new PerfilInvestidor(
                "Aposentadoria",
                "Baixa",
                "Longo prazo",
                "Iniciante",
                "Renda fixa",
                "Evitar riscos"
        );

        PerfilInvestidor perfil2 = new PerfilInvestidor(
                "Multiplicação de capital",
                "Alta",
                "Curto prazo",
                "Avançado",
                "Criptomoedas",
                "Evitar renda fixa"
        );


        // 4. Criar investidores ====
        Investidor inv1 = new Investidor(perfil1, "Conservador", 30, "Investimentos de baixo risco");
        Investidor inv2 = new Investidor(perfil2, "Moderado", 60, "Equilíbrio entre risco e retorno");

        // 5. Criar carteiras associadas aos investidores ====
        Carteira cart1 = new Carteira(inv1, "Carteira de Renda Fixa");
        Carteira cart2 = new Carteira(inv2, "Carteira de Cripto Ativos");

        carteiras.add(cart1);
        carteiras.add(cart2);

        // 6. Adicionar investidores aos clientes ====
        c1.adicionarInvestidor(inv1);
        c2.adicionarInvestidor(inv2);


        // 7. Listar clientes ====
        System.out.println("=== CLIENTES CADASTRADOS ===");
        for (Cliente cli : clientes) {
            System.out.println(cli.getNome() + " - " + cli.getEmail());
        }

        // 8. Listar carteiras ====
        System.out.println("\n=== CARTEIRAS CADASTRADAS ===");
        for (Carteira car : carteiras) {
            System.out.println(
                    car.getNome() +
                            " | Persona do investidor: " + car.getInvestidor().getPersona()
            );
        }

        // 9. Adicionar ativos nas carteiras ====
        cart2.adicionarAtivo("BTC", 0.5f);
        cart2.adicionarAtivo("ETH", 2.0f);




    }
}
