package entities;

public class ExchangeIntegration {

    //Attributes//
    private final String nome;
    private final String endPoint;

    //Constructors//
    public ExchangeIntegration(String nome, String endPoint) {
        this.nome = nome;
        this.endPoint = endPoint;
    }

    //Getters and Setters//
    public String getNome() {
        return nome;
    }
    public String getEndPoint() {
        return endPoint;
    }

    //Methods//
    public void conectar(){}
}
