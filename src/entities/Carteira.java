package entities;

import java.util.UUID;

public class Carteira {
    //Attributes//
    private UUID id;
    private String nome;
    private String faixaRisco;
    private Float totalInvestido;
    private Float valorAtual;

    //Constructors//
    public Carteira(){
    }
    public Carteira(UUID id, String nome, String faixaRisco, Float totalInvestido, Float valorAtual) {
        this.id = id;
        this.nome = nome;
        this.faixaRisco = faixaRisco;
        this.totalInvestido = totalInvestido;
        this.valorAtual = valorAtual;
    }

    //Getters and Setters//
    public UUID getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getFaixaRisco() {
        return faixaRisco;
    }
    public Float getTotalInvestido() {
        return totalInvestido;
    }
    public Float getValorAtual() {
        return valorAtual;
    }

    //Methods//
    public void adicionarAtivo(String ativo, Float valor){

    }
    public void removerAtivo(String ativo){

    }
    public Float calcularRentabilidade(){
        return null;
    }
    public void rebalancear(){

    }

}
