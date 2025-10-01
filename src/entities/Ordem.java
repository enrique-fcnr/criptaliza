package entities;

import enums.StatusOrdem;
import enums.TipoOrdem;

public class Ordem {

    //Attributes//
    private String ativo;
    private Float quantidade;
    private Float preco;
    private TipoOrdem tipo;
    private StatusOrdem status;

    //Constructors//
    public Ordem(){

    }
    public Ordem(String ativo, Float quantidade, Float preco, TipoOrdem tipo, StatusOrdem status) {
        this.ativo = ativo;
        this.quantidade = quantidade;
        this.preco = preco;
        this.tipo = tipo;
        this.status = status;
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
    public void setTipo(TipoOrdem tipo) {
        this.tipo = tipo;
    }
    public StatusOrdem getStatus() {
        return status;
    }

    //Methods//
    public void aplicar(){}
    public void cancelar(){}
    //public void enviarParaCorretora(API_Corretora api)
}
