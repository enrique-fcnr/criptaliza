package br.com.criptaliza.model.enums;

public enum PerfilTipo {
    INICIANTE("Conservador", 90),
    MODERADO("Moderado", 60),
    CONFIANTE("Arrojado", 35);

    private final String nome;
    private final int pontuacao;

    PerfilTipo(String nome, int pontuacao) {
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public String getNome() { return nome; }
    public int getPontuacao() { return pontuacao; }
}