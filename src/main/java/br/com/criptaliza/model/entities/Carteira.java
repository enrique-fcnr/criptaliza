package br.com.criptaliza.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode; // Importante para operações financeiras
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entidade que representa a Carteira de investimentos do usuário.
 */
public class Carteira {

    // Identificadores e Associações
    private Long id;
    private Investidor investidor;
    private LocalDateTime dataCriacao;

    // Atributos de Negócio
    private String nome;
    private String faixaRisco;
    private BigDecimal totalInvestido;
    private BigDecimal valorAtual;

    // --- CORREÇÃO: Inicialização das Coleções que faltavam ---
    private Map<String, BigDecimal> ativos = new HashMap<>();
    private List<Ordem> ordens = new ArrayList<>();
    // Certifique-se que a classe RecomendacaoCarteira existe no seu projeto
    private List<RecomendacaoCarteira> recomendacoes = new ArrayList<>();

    // --- CONSTRUTORES ---

    public Carteira() {
    }

    // Construtor para criação de nova carteira (Cadastro Inicial via Menu)
    public Carteira(Investidor investidor, String nome) {
        this.investidor = investidor;
        this.nome = nome;
        this.faixaRisco = "N/A";
        this.totalInvestido = BigDecimal.ZERO;
        this.valorAtual = BigDecimal.ZERO;
        this.dataCriacao = LocalDateTime.now();
    }

    // Construtor Completo (Usado pelo DAO ao carregar do banco)
    public Carteira(Investidor investidor, String nome, LocalDateTime dataCriacao, BigDecimal totalInvestido, BigDecimal valorAtual) {
        this.investidor = investidor;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.totalInvestido = totalInvestido;
        this.valorAtual = valorAtual;
        this.faixaRisco = "N/A";
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public void setInvestidor(Investidor investidor) {
        this.investidor = investidor;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFaixaRisco() {
        return faixaRisco;
    }

    public void setFaixaRisco(String faixaRisco) {
        this.faixaRisco = faixaRisco;
    }

    public BigDecimal getTotalInvestido() {
        return totalInvestido;
    }

    public void setTotalInvestido(BigDecimal totalInvestido) {
        this.totalInvestido = totalInvestido;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    // Getters e Setters das Coleções
    public Map<String, BigDecimal> getAtivos() {
        return ativos;
    }

    public void setAtivos(Map<String, BigDecimal> ativos) {
        this.ativos = ativos;
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<Ordem> ordens) {
        this.ordens = ordens;
    }

    public List<RecomendacaoCarteira> getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(List<RecomendacaoCarteira> recomendacoes) {
        this.recomendacoes = recomendacoes;
    }

    // --- MÉTODOS DE GESTÃO (Lógica de Negócio) ---

    public void adicionarAtivo(String ticker, BigDecimal quantidade) {
        if (this.ativos == null) this.ativos = new HashMap<>();
        this.ativos.merge(ticker, quantidade, BigDecimal::add);
    }

    public void removerAtivo(String ticker) {
        this.ativos.remove(ticker);
    }

    public BigDecimal calcularRentabilidade() {
        if (totalInvestido == null || totalInvestido.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        // Cálculo: ((Atual - Investido) / Investido) * 100
        return valorAtual.subtract(totalInvestido)
                .divide(totalInvestido, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }
}