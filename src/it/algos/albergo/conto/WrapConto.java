package it.algos.albergo.conto;

import it.algos.albergo.pensioni.WrapAddebitiConto;

import java.util.Date;

/**
 * Wrapper per i dati di creazione di un conto.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 19-feb-2008 ore  15:48
 */
public final class WrapConto {


    /**
     * Codice del conto
     * (solo per modificare un conto esistente)
     */
    private int codConto;

    /**
     * variabile di tipo data
     * data di apertura del conto
     */
    private Date dataApertura;

    /**
     * variabile di tipo intero
     * codice della camera
     */
    private int codCamera;

    /**
     * variabile di tipo intero
     * codice del cliente
     */
    private int codCliente;

    /**
     * variabile di tipo intero
     * codice dell'azienda
     */
    private int codAzienda;

    /**
     * data inizio validità
     */
    private Date dataInizioValidita;

    /**
     * data fine validità
     */
    private Date dataFineValidita;

    /**
     * arrivo con
     */
    private int arrivoCon;

    /**
     * importo della eventuale caparra
     */
    private double caparra;

    /**
     * codice mezzo di pagamento della caparra
     */
    private int mezzoCaparra;

    /**
     * numero di ricevuta della caparra
     */
    private int ricevutaCaparra;

    /**
     * data di pagamento della caparra
     */
    private Date dataCaparra;

    /**
     * codice del periodo di riferimento (che ha originato il conto)
     */
    private int codPeriodo;

    /**
     * contenitore dei dati per gli addebiti continuativi del conto
     */
    private WrapAddebitiConto wrapAddebiti;


    /**
     * Costruttore senza parametri.
     */
    public WrapConto() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param dataApertura - data di apertura del conto
     * @param codCamera - codice della camera
     * @param codCliente - codice del cliente
     * @param codAzienda - codice dell'azienda
     */
    public WrapConto(Date dataApertura, int codCamera, int codCliente, int codAzienda) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setDataApertura(dataApertura);
        this.setCodCamera(codCamera);
        this.setCodCliente(codCliente);
        this.setCodAzienda(codAzienda);

    }// fine del metodo costruttore completo


    public int getCodConto() {
        return codConto;
    }


    public void setCodConto(int codConto) {
        this.codConto = codConto;
    }


    /**
     * metodo getter
     *
     * @return dataApertura
     */
    public Date getDataApertura() {
        return dataApertura;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param dataApertura - data di apertura del conto
     */
    private void setDataApertura(Date dataApertura) {
        this.dataApertura = dataApertura;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return codCamera
     */
    public int getCodCamera() {
        return codCamera;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param codCamera - codice della camera
     */
    private void setCodCamera(int codCamera) {
        this.codCamera = codCamera;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return codCliente
     */
    public int getCodCliente() {
        return codCliente;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param codCliente - codice del cliente
     */
    private void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return il codice azienda
     */
    public int getCodAzienda() {
        return codAzienda;
    }


    /**
     * metodo setter
     *
     * @param codAzienda - codice dell'azienda
     */
    private void setCodAzienda(int codAzienda) {
        this.codAzienda = codAzienda;
    }


    /**
     * metodo getter
     *
     * @return il wrapper addebiti
     */
    public WrapAddebitiConto getWrapAddebiti() {
        return wrapAddebiti;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param wrapAddebiti - contenitore dei dati per gli addebiti continuativi
     */
    public void setWrapAddebiti(WrapAddebitiConto wrapAddebiti) {
        this.wrapAddebiti = wrapAddebiti;
    } // fine del metodo setter


    public Date getDataInizioValidita() {
        return dataInizioValidita;
    }


    public void setDataInizioValidita(Date data) {
        this.dataInizioValidita = data;
    }


    public Date getDataFineValidita() {
        return dataFineValidita;
    }


    public void setDataFineValidita(Date data) {
        this.dataFineValidita = data;
    }


    public int getArrivoCon() {
        return arrivoCon;
    }


    public void setArrivoCon(int arrivoCon) {
        this.arrivoCon = arrivoCon;
    }

    public double getCaparra() {
        return caparra;
    }


    public void setCaparra(double caparra) {
        this.caparra = caparra;
    }


    public int getMezzoCaparra() {
        return mezzoCaparra;
    }


    public void setMezzoCaparra(int mezzoCaparra) {
        this.mezzoCaparra = mezzoCaparra;
    }


    public int getRicevutaCaparra() {
        return ricevutaCaparra;
    }


    public void setRicevutaCaparra(int ricevutaCaparra) {
        this.ricevutaCaparra = ricevutaCaparra;
    }


    public Date getDataCaparra() {
        return dataCaparra;
    }


    public void setDataCaparra(Date dataCaparra) {
        this.dataCaparra = dataCaparra;
    }


    /* codice del periodo di riferimento (che ha originato il conto) */
    public int getCodPeriodo() {
        return codPeriodo;
    }


    public void setCodPeriodo(int codPeriodo) {
        this.codPeriodo = codPeriodo;
    }


}// fine della classe