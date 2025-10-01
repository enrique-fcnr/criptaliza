package entities;

import java.util.UUID;

public class Investidor{

    //Attributes//
    private final UUID id;
    private String persona;
    private Integer pontuacaoRisco;
    private String resumoObjetivo;

    //Constructors//
    public Investidor(UUID id, String persona, Integer pontuacaoRisco, String resumoObjetivo) {
        this.id = UUID.randomUUID();
        this.persona = persona;
        this.pontuacaoRisco = pontuacaoRisco;
        this.resumoObjetivo = resumoObjetivo;
    }

    //Getters and Setters//
    public UUID getId() {
        return id;
    }
    public String getPersona() {
        return persona;
    }
    public void setPersona(String persona) {
        this.persona = persona;
    }
    public Integer getPontuacaoRisco() {
        return pontuacaoRisco;
    }
    public void setPontuacaoRisco(Integer pontuacaoRisco) {
        this.pontuacaoRisco = pontuacaoRisco;
    }
    public String getResumoObjetivo() {
        return resumoObjetivo;
    }
    public void setResumoObjetivo(String resumoObjetivo) {
        this.resumoObjetivo = resumoObjetivo;
    }

    //Methods//
    public void atualizarPerfil(String novaPersona, Integer novaPontuacaoRisco, String novoResumoObjetivo){
        this.persona = novaPersona;
        this.pontuacaoRisco = novaPontuacaoRisco;
        this.resumoObjetivo = novoResumoObjetivo;
        System.out.println("Perfil do investidor atualizado!");
    }
    public void criarCarteira(){
        System.out.println("Carteira de investimentos criada para investidor " + id);
        //criação da carteira aqui...
    }
    public Float simularInvestimento(Float valor){
        return null;
        //simulação de investimento aqui...
    }
}
