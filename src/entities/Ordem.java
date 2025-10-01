package entities;

import enums.StatusOrdem;
import enums.TipoOrdem;

public class Ordem {

    //Attributes//
    private final String ativo;
    private final Float quantidade;
    private Float preco;
    private final TipoOrdem tipo;
    private StatusOrdem status;

    //Constructors//
    public Ordem(String ativo, Float quantidade, Float preco, TipoOrdem tipo) {
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.preco = preco;
        this.tipo = tipo;
        this.status = StatusOrdem.CRIADA; // Define o status inicial internamente
    }

    //Getters and Setters//
    public String getAtivo() {
        return ativo;
    }
    public Float getQuantidade() {
        return quantidade;
    }
    public Float getPreco() {
        return preco;
    }
    public void setPreco(Float preco) {
        this.preco = preco;
    }
    public TipoOrdem getTipo() {
        return tipo;
    }
    public StatusOrdem getStatus() {
        return status;
    }
    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    //Methods//
    public void aplicar(){}
    public void cancelar(){}
    public void enviarParaCorretora(API_Corretora api){}
}
