/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      10-mag-2006
 */
package it.algos.base.wrapper;

import java.util.Date;

/**
 * Contenitore per le informazioni di un listino.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 10-mag-2006 ore 6.18.49
 */
public final class WrapListino {

//    /**
//     * sigla identificativa del listino (solo risposta)
//     */
//    private String sigla = "";

    /**
     * data iniziale (domanda e risposta)
     */
    private Date primaData = null;

    /**
     * data finale (domanda e risposta)
     */
    private Date secondaData = null;

//    /**
//     * sottoconto di riferimento (solo domanda)
//     */
//    private int codSottoconto = 0;

    /**
     * codice del record di listino (solo risposta)
     */
    private int codListino = 0;

    /**
     * prezzo del listino (solo risposta)
     */
    private double prezzo = 0;

    /**
     * codice di listino se listino fisso o
     * della riga di listino di riferimento se listino variabile
     */
    private int codRiga = 0;


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Solo per risposta   <br>
     *
     * @param primaData inizio del periodo
     * @param secondaData fine del periodo
     * @param codListino del record di listino
     * @param prezzo del listino
     */
    public WrapListino(Date primaData,
                       Date secondaData,
                       int codListino,
                       double prezzo,
                       int codRiga) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPrimaData(primaData);
        this.setSecondaData(secondaData);
        this.setCodListino(codListino);
//        this.setSigla(sigla);
        this.setPrezzo(prezzo);
        this.setCodRiga(codRiga);
    }// fine del metodo costruttore completo

//    public String getSigla() {
//        return sigla;
//    }
//
//
//    private void setSigla(String sigla) {
//        this.sigla = sigla;
//    }


    public Date getPrimaData() {
        return primaData;
    }


    public void setPrimaData(Date primaData) {
        this.primaData = primaData;
    }


    public Date getSecondaData() {
        return secondaData;
    }


    public void setSecondaData(Date secondaData) {
        this.secondaData = secondaData;
    }


    public int getCodListino() {
        return codListino;
    }


    private void setCodListino(int codListino) {
        this.codListino = codListino;
    }


    public double getPrezzo() {
        return prezzo;
    }


    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }


    public int getCodRiga() {
        return codRiga;
    }


    private void setCodRiga(int codRiga) {
        this.codRiga = codRiga;
    }
}// fine della classe
