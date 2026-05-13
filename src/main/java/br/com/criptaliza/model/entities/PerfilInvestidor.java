package br.com.criptaliza.model.entities;

public class PerfilInvestidor {

    //Attributes//
    private Long id;
    private Long idInvestidor;
    private String objetivo;
    private String toleranciaRisco;
    private String horizonte;
    private String experiencia;
    private String preferencias;
    private String prevencoes;

    //Constructors//
    public PerfilInvestidor() {}

    public PerfilInvestidor(String objetivo, String toleranciaRisco, String horizonte, String experiencia, String preferencias, String prevencoes) {
        this.objetivo = objetivo;
        this.toleranciaRisco = toleranciaRisco;
        this.horizonte = horizonte;
        this.experiencia = experiencia;
        this.preferencias = preferencias;
        this.prevencoes = prevencoes;
    }

    public PerfilInvestidor(Long id, Long idInvestidor, String objetivo, String toleranciaRisco, String horizonte, String experiencia, String preferencias, String prevencoes) {
        this.id = id;
        this.idInvestidor = idInvestidor;
        this.objetivo = objetivo;
        this.toleranciaRisco = toleranciaRisco;
        this.horizonte = horizonte;
        this.experiencia = experiencia;
        this.preferencias = preferencias;
        this.prevencoes = prevencoes;
    }

    public PerfilInvestidor(long idInvestidor, String objetivo, String risco, String horizonte, String experiencia) {
        this.idInvestidor = idInvestidor;
        this.objetivo = objetivo;
        this.toleranciaRisco = risco;
        this.horizonte = horizonte;
        this.experiencia = experiencia;
    }


    //Getters and Setters//
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdInvestidor() {
        return idInvestidor;
    }
    public void setIdInvestidor(Long idInvestidor) {
        this.idInvestidor = idInvestidor;
    }
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
