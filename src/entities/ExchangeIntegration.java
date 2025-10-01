package entities;

public class ExchangeIntegration {

    //Attributes//
    private String nome;
    private String endPoint;

    //Constructors//
    public ExchangeIntegration(){}
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
