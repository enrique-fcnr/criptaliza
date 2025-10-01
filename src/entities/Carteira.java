package entities;

import java.util.*;

public class Carteira {
    //Attributes//
    private final UUID id;
    private final Investidor investidor; //associação à classe investidor
    private String nome;
    private String faixaRisco;
    private Float totalInvestido;
    private Float valorAtual;

    private final Map<String, Float> ativos; // Coleção para armazenar a posição: Ex: <"BTC", 0.5>, <"ETH", 3.0>

    private final List<Ordem> ordens;
    private final List<RecomendacaoCarteira> recomendacoes;

    //Constructors//
    public Carteira(Investidor investidor, String nome) {
        this.id = UUID.randomUUID();
        this.investidor = investidor;
        this.nome = nome;

        this.faixaRisco = "N/A";
        this.totalInvestido = 0.0f;
        this.valorAtual = 0.0f;
        this.ativos = new HashMap<>();
        this.ordens = new ArrayList<>();
        this.recomendacoes = new ArrayList<>();
    }

    //Getters and Setters//
    public UUID getId() {
        return id;
    }
    public Investidor getInvestidor() { // Getter para a associação
        return investidor;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) { // Mutável
        this.nome = nome;
    }
    public String getFaixaRisco() {
        return faixaRisco;
    }
    public void setFaixaRisco(String faixaRisco) { // Mutável
        this.faixaRisco = faixaRisco;
    }
    public Float getTotalInvestido() {
        return totalInvestido;
    }
    public Float getValorAtual() {
        return valorAtual;
    }
    public List<Ordem> getOrdens() {
        return ordens;
    }
    public List<RecomendacaoCarteira> getRecomendacoes() {
        return recomendacoes;
    }

    // Métodos para gestão dos Ativos
    public Map<String, Float> getAtivos() {
        return ativos;
    }

    //Methods//
    public void adicionarAtivo(String ativo, Float quantidade){
        // Lógica de adição de ativo na Map 'ativos'
    }
    public void removerAtivo(String ativo){
        // Lógica de remoção de ativo da Map 'ativos'
    }
    public Float calcularRentabilidade(){
        return null; // Placeholder
    }
    public void rebalancear(){
        // Lógica de alocação inteligente
    }

}
