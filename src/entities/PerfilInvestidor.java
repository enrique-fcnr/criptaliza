package entities;

public class PerfilInvestidor {

    //Attributes//
    private String objetivo;
    private String toleranciaRisco;
    private String horizonte;
    private String experiencia;
    private String preferencias;
    private String prevencoes;

    //Constructors//
    public PerfilInvestidor(String objetivo, String toleranciaRisco, String horizonte, String experiencia, String preferencias, String prevencoes) {
        this.objetivo = objetivo;
        this.toleranciaRisco = toleranciaRisco;
        this.horizonte = horizonte;
        this.experiencia = experiencia;
        this.preferencias = preferencias;
        this.prevencoes = prevencoes;
    }

    //Getters and Setters//
    public String getObjetivo() {
        return objetivo;
    }
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    public String getToleranciaRisco() {
        return toleranciaRisco;
    }
    public void setToleranciaRisco(String toleranciaRisco) {
        this.toleranciaRisco = toleranciaRisco;
    }
    public String getHorizonte() {
        return horizonte;
    }
    public void setHorizonte(String horizonte) {
        this.horizonte = horizonte;
    }
    public String getExperiencia() {
        return experiencia;
    }
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
    public String getPreferencias() {
        return preferencias;
    }
    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
    public String getPrevencoes() {
        return prevencoes;
    }
    public void setPrevencoes(String prevencoes) {
        this.prevencoes = prevencoes;
    }

    //Methods//
    public String avaliarPerfil(){
        return null;
    }
}
