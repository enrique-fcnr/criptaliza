package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trade {

    //Attributes//
    private final Ordem ordem;
    private final Float precoExec;
    private final Float quantidade;
    private final Date dataExec;
    private final List<InvestidorTrade> investidoresAssociados;

    //Constructors//
    public Trade(Ordem ordem, Float precoExec, Float quantidade, Date dataExec) {
        this.ordem = ordem;
        this.precoExec = precoExec;
        this.quantidade = quantidade;
        this.dataExec = dataExec;

        this.investidoresAssociados = new ArrayList<>();
    }

    //Getters and Setters//
    public Ordem getOrdem() { // NOVO: Getter para o objeto Ordem
        return ordem;
    }
    public Float getPrecoExec() {
        return precoExec;
    }
    public Float getQuantidade() {
        return quantidade;
    }
    public Date getDataExec() {
        return dataExec;
    }
    public List<InvestidorTrade> getInvestidoresAssociados() {
        return investidoresAssociados;
    }

    //Methods//
    public void registrar(){}
    public Float calcularLucro(Float precoCompra){
        return null;
    }
}
