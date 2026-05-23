package br.com.criptaliza.model.entities;

import br.com.criptaliza.model.enums.StatusOrdem;
import br.com.criptaliza.model.enums.TipoOrdem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ordem {

    private Long id; // cd_ordem no banco
    private Carteira carteira; // Associação necessária (cd_carteira)
    private String ativo;
    private BigDecimal quantidade; // BigDecimal para precisão cripto
    private BigDecimal preco;
    private TipoOrdem tipo;
    private StatusOrdem status;
    private List<Trade> trades = new ArrayList<>();

    // Construtor Padrão (Importante para o DAO/Frameworks)
    public Ordem() {}

    // Construtor Customizado (Para usar na View)
    public Ordem(Carteira carteira, String ativo, BigDecimal quantidade, BigDecimal preco, TipoOrdem tipo) {
        this.carteira = carteira;
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.preco = preco;
        this.tipo = tipo;
        this.status = StatusOrdem.CRIADA;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public TipoOrdem getTipo() {
        return tipo;
    }

    public void setTipo(TipoOrdem tipo) {
        this.tipo = tipo;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    // Métodos de Negócio
    public void aplicar() {
        // Lógica para executar a ordem
    }

    public void cancelar() {
        this.status = StatusOrdem.CANCELADA;
    }

    // Exemplo de toString para facilitar suas Views de listagem
    @Override
    public String toString() {
        return "Ordem [ID=" + id + ", Ativo=" + ativo + ", Qtd=" + quantidade +
                ", Tipo=" + tipo + ", Status=" + status + "]";
    }
}