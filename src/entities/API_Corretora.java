package entities;

public class API_Corretora {

    //Attributes//
    private String nome;
    private String endPoint;
    private Boolean ativo;

    //Constructors//
    public API_Corretora(){}
    public API_Corretora(String nome, String endPoint, Boolean ativo) {
        this.nome = nome;
        this.endPoint = endPoint;
        this.ativo = ativo;
    }

    //Getters and Setters//
    public String getNome() {
        return nome;
    }
    public String getEndPoint() {
        return endPoint;
    }
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    //Methods//
    public void enviarOrdem(Ordem ordem){

    }
    public Float consultarPreco(String ativo){
        return null;
    }
}
