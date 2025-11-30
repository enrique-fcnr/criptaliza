package entities;

import integrations.ExchangeIntegration;

public class API_Corretora {

    //Attributes//
    private final ExchangeIntegration exchangeIntegration;
    private final String nome;
    private final String endPoint;
    private Boolean ativo;

    //Constructors//
    public API_Corretora(String nome, String endPoint, ExchangeIntegration exchangeIntegration, Boolean ativo) {
        this.nome = nome;
        this.endPoint = endPoint;
        this.exchangeIntegration = exchangeIntegration;
        this.ativo = ativo;
    }

    //Getters and Setters//
    public String getNome() {
        return nome;
    }
    public String getEndPoint() {
        return endPoint;
    }
    public ExchangeIntegration getExchangeIntegration() { // Getter para a associação
        return exchangeIntegration;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    //Methods//
    public void enviarOrdem(Ordem ordem){
        // this.exchangeIntegration.enviarOrdem(ordem);
    }
    public Float consultarPreco(String ativo){
        // return this.exchangeIntegration.consultarPreco(ativo);
        return null;
    }
    public boolean consultarPrecoOrdem(){
        // Lógica: Consulta detalhes de uma ordem (preço, status, etc.)
        return false; // Placeholder
    }
}
