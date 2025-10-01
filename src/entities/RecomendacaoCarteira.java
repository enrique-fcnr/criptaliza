package entities;

public class RecomendacaoCarteira {

    //Attributes//
    private final String tipo;
    private final String ativo;
    private final Float valorSugerido;
    private final Float percAlvo;

    //Constructors//
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
    public String getAtivo() {
        return ativo;
    }
    public Float getValorSugerido() {
        return valorSugerido;
    }
    public Float getPercAlvo() {
        return percAlvo;
    }

    //Methods//
    public String justificar(){
        return null;
    }
    public void gerarSugestaoComBaseNoPerfil(PerfilInvestidor perfil){

    }
}
