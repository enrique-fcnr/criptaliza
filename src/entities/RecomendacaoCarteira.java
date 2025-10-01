package entities;

public class RecomendacaoCarteira {

    //Attributes//
    private String tipo;
    private String ativo;
    private Float valorSugerido;
    private Float percAlvo;

    //Constructors//
    public RecomendacaoCarteira(){
    }
    public RecomendacaoCarteira(String tipo, String ativo, Float valorSugerido, Float percAlvo) {
        this.tipo = tipo;
        this.ativo = ativo;
        this.valorSugerido = valorSugerido;
        this.percAlvo = percAlvo;
    }

    //Getters and Setters//
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getAtivo() {
        return ativo;
    }
    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
    public Float getValorSugerido() {
        return valorSugerido;
    }
    public void setValorSugerido(Float valorSugerido) {
        this.valorSugerido = valorSugerido;
    }
    public Float getPercAlvo() {
        return percAlvo;
    }
    public void setPercAlvo(Float percAlvo) {
        this.percAlvo = percAlvo;
    }

    //Methods//
    public String justificar(){
        return null;
    }
    public void gerarSugestaoComBaseNoPerfil(PerfilInvestidor perfil){

    }
}
