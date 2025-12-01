package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cliente {
    //Attributes//
    private final UUID id;
    private String nome;
    private String email;
    private String telefone;
    private String idioma;

    private final List<Investidor> investidores; //associação à classe de investidores

    //Constructors//
    public Cliente(String nome, String email, String telefone, String idioma) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.idioma = idioma;

        this.investidores = new ArrayList<>();
    }

    //Getters and Setters//
    public UUID getId() {
        return id;
    }
    public void setNome(String nome) {this.nome = nome;}
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    //Methods//
    public void cadastrar(){
        System.out.println("Cliente " + nome + " cadastrado com sucesso!");
    }
    public boolean autenticar(String email){
        return this.email.equalsIgnoreCase(email);
    }

    //Association//
    public void adicionarInvestidor(Investidor investidor){
        this.investidores.add(investidor);
    }
    public void removerInvestidor(Investidor investidor){
        this.investidores.remove(investidor);
    }
    public void listarInvestidores(){
        for(Investidor inv : investidores){
            System.out.println("Investidor " + inv.getId() + " - Persona" + inv.getPersona());
        }
    }
}
