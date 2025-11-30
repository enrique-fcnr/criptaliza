package integrations;

import entities.Ordem;
import entities.Trade;

public class ExchangeIntegration {

    //Attributes//
    private final String nome;
    private final String endPoint;
    private final String apiKey;
    private final String secretKey;

    //Constructors//
    public ExchangeIntegration(String nome, String endPoint, String apiKey, String secretKey) {
        this.nome = nome;
        this.endPoint = endPoint;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    //Getters and Setters//
    public String getNome() {
        return nome;
    }
    public String getEndPoint() {
        return endPoint;
    }

    //Methods//
    public boolean conectar(){
        return false;
    }
    public String enviarOrdem(Ordem ordem){
        // Lógica: Formata a Ordem e faz a chamada POST para a API
        return null; // Placeholder: Retorna o ID da ordem na corretora
    }
    public Float consultarPreco(String ativo){
        // Lógica: Consulta o preço de mercado via API
        return null; // Placeholder
    }
    public Trade consultarTrade(String idOrdemCorretora){
        // Lógica: Consulta o status da ordem e, se executada,
        // retorna um objeto Trade.
        return null; // Placeholder
    }
}
