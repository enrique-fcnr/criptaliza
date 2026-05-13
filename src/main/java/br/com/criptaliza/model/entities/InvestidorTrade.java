package br.com.criptaliza.model.entities;

public class InvestidorTrade {

    //Attributes//
    private Long id;
    private Investidor investidor;
    private Trade trade;

    //Constructors//
    public InvestidorTrade() {}

    public InvestidorTrade(Investidor investidor, Trade trade) {
        this.investidor = investidor;
        this.trade = trade;
    }

    public InvestidorTrade(Long id, Investidor investidor, Trade trade) {
        this.id = id;
        this.investidor = investidor;
        this.trade = trade;
    }

    //Getters and Setters//
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Investidor getInvestidor() {
        return investidor;
    }
    public void setInvestidor(Investidor investidor) {
        this.investidor = investidor;
    }
    public Trade getTrade() {
        return trade;
    }
    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    //Methods//
    public static InvestidorTrade associar(Investidor investidor, Trade trade){
        return new InvestidorTrade(investidor, trade);
    }
}
