package br.com.criptaliza.model.entities;

import java.util.ArrayList;
import java.util.List;


public class Investidor{

    //Attributes//
    private long  id;
    private long idCliente;
    private String persona;
    private Integer pontuacaoRisco;


    // Associações (Relacionamentos)
    private  PerfilInvestidor perfil; // Associação 1:1 ao perfil
    private  List<Carteira> carteiras; // Associação 1:* a Carteira
    private  List<InvestidorTrade> tradesRegistrados; // Associação M:M a Trade

    //Constructors//

    // 1. Construtor para criar novo (sem ID):
    // Removido as listas dos parâmetros!
    public Investidor(long idCliente, String persona, Integer pontuacaoRisco) {
        this.idCliente = idCliente;
        this.persona = persona;
        this.pontuacaoRisco = pontuacaoRisco;
        // Inicializamos as listas aqui dentro.
        // Assim, o objeto nasce pronto para receber carteiras futuramente.
        this.carteiras = new ArrayList<>();
        this.tradesRegistrados = new ArrayList<>();
    }

    // 2. Construtor para o Dao (com ID do banco de dados):
    // Removido as listas dos parâmetros também!
    public Investidor(long id, long idCliente, String persona, Integer pontuacaoRisco) {
        this.id = id;
        this.idCliente = idCliente;
        this.persona = persona;
        this.pontuacaoRisco = pontuacaoRisco;
        this.carteiras = new ArrayList<>();
        this.tradesRegistrados = new ArrayList<>();
    }

    public Investidor() {

    }

    //Getters and Setters//
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Integer getPontuacaoRisco() {
        return pontuacaoRisco;
    }

    public void setPontuacaoRisco(Integer pontuacaoRisco) {
        this.pontuacaoRisco = pontuacaoRisco;
    }

    public PerfilInvestidor getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilInvestidor perfil) {
        this.perfil = perfil;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public List<InvestidorTrade> getTradesRegistrados() {
        return tradesRegistrados;
    }


    // --- Métodos de Regra de Negócio ---
    public void atualizarClassificacao(String novaPersona, Integer novaPontuacaoRisco) {
        this.persona = novaPersona;
        this.pontuacaoRisco = novaPontuacaoRisco;
        // Aqui futuramente você chamaria o InvestidorDAO para salvar no banco
        System.out.println("Status de investidor atualizado para: " + novaPersona);
    }

    public void vincularCarteira(Carteira carteira) {
        this.carteiras.add(carteira);
        System.out.println("Nova carteira adicionada à lista do investidor.");
    }

    public Double simularRendimento(Double valorSimulado) {
        // Exemplo: se o risco é 100, rende 20%. Se é 10, rende 2%.
        double taxa = this.pontuacaoRisco / 500.0;
        return valorSimulado * (1 + taxa);
    }
    @Override
    public String toString() {
        return "Perfil[Objetivo=" + perfil.getObjetivo() + ", Risco=" + perfil.getToleranciaRisco() + "]";
    }

    public void definirPersonaPorPontuacao(int pontuacao) {
        this.pontuacaoRisco = pontuacao;

        if (pontuacao >= 80) {
            this.persona = "Iniciante";
        } else if (pontuacao >= 65) {
            this.persona = "Moderado";
        } else {
            this.persona = "Confiante";
        }
    }
}
