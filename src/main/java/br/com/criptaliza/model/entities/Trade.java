package br.com.criptaliza.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Trade {

    //Attributes//
    private Long id;
    private Ordem ordem;
    private BigDecimal precoExec;
    private BigDecimal quantidade;
    private LocalDateTime dataExec;
    private List<InvestidorTrade> investidoresAssociados = new ArrayList<>();

    //Constructors//
    public Trade() {}

    public Trade(Ordem ordem, BigDecimal precoExec, BigDecimal quantidade, LocalDateTime dataExec) {
        this.ordem = ordem;
        this.precoExec = precoExec;
        this.quantidade = quantidade;
        this.dataExec = dataExec;
        this.investidoresAssociados = new ArrayList<>();
    }

    public Trade(Long id, Ordem ordem, BigDecimal precoExec, BigDecimal quantidade, LocalDateTime dataExec) {
        this.id = id;
        this.ordem = ordem;
        this.precoExec = precoExec;
        this.quantidade = quantidade;
        this.dataExec = dataExec;
        this.investidoresAssociados = new ArrayList<>();
    }

    //Getters and Setters//
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Ordem getOrdem() {
        return ordem;
    }
    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }
    public BigDecimal getPrecoExec() {
        return precoExec;
    }
    public void setPrecoExec(BigDecimal precoExec) {
        this.precoExec = precoExec;
    }
    public BigDecimal getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
    public LocalDateTime getDataExec() {
        return dataExec;
    }
    public void setDataExec(LocalDateTime dataExec) {
        this.dataExec = dataExec;
    }
    public List<InvestidorTrade> getInvestidoresAssociados() {
        return investidoresAssociados;
    }

    //Methods//
    public void registrar(){}
    public BigDecimal calcularLucro(BigDecimal precoCompra){
        return null;
    }
}
