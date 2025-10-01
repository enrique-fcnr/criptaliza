package entities;

import java.util.Date;

public class Trade {

    //Attributes//
    private Float precoExec;
    private Float quantidade;
    private Date dataExec;

    //Constructors//
    public Trade(){}
    public Trade(Float precoExec, Float quantidade, Date dataExec) {
        this.precoExec = precoExec;
        this.quantidade = quantidade;
        this.dataExec = dataExec;
    }

    //Getters and Setters//
    public Float getPrecoExec() {
        return precoExec;
    }
    public Float getQuantidade() {
        return quantidade;
    }
    public Date getDataExec() {
        return dataExec;
    }

    //Methods//
    public void registrar(){}
    public Float calcularLucro(Float precoCompra){
        return null;
    }
}
