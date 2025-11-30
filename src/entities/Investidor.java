package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Investidor{

    //Attributes//
    private final PerfilInvestidor perfil; // Associação 1:1 ao perfil
    private final UUID id;
    private String persona;
    private Integer pontuacaoRisco;
    private String resumoObjetivo;

    private final List<Carteira> carteiras; // Associação 1:* a Carteira
    private final List<InvestidorTrade> tradesRegistrados; // Associação M:M a Trade

    //Constructors//
    public Investidor(PerfilInvestidor perfil, String persona, Integer pontuacaoRisco, String resumoObjetivo) {
        this.id = UUID.randomUUID();
        this.perfil = perfil; // Inicializa a referência
        this.persona = persona;
        this.pontuacaoRisco = pontuacaoRisco;
        this.resumoObjetivo = resumoObjetivo;

        this.carteiras = new ArrayList<>(); // Inicializa a coleção
        this.tradesRegistrados = new ArrayList<>(); // Inicializa a coleção
    }

    //Getters and Setters//
    public PerfilInvestidor getPerfil() { // NOVO: Getter para o perfil
        return perfil;
    }
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
    public List<Carteira> getCarteiras() { // NOVO: Getter para a coleção
        return carteiras;
    }
    public List<InvestidorTrade> getTradesRegistrados() { // NOVO: Getter para a coleção
        return tradesRegistrados;
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
