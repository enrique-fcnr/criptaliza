package entities;

import java.util.UUID;

public class InvestidorTrade {

    //Attributes//
    private final UUID id;
    private final Investidor investidor;
    private final Trade trade;

    //Constructors//
    public InvestidorTrade(Investidor investidor, Trade trade) {
        this.id = UUID.randomUUID();
        this.investidor = investidor;
        this.trade = trade;
    }

    //Getters and Setters//
    public UUID getId() {
        return id;
    }
    public Investidor getInvestidor() {
        return investidor;
    }
    public Trade getTrade() {
        return trade;
    }

    //Methods//
    public static InvestidorTrade associar(Investidor investidor, Trade trade){
        return new InvestidorTrade(investidor, trade);
    }
}
