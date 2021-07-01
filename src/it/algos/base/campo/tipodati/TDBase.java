/**
 * Title:        TDBase.java
 * Package:      it.algos.base.campo.tipodati
 * Description:  Tipo Dati Astratto
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 ottobre 2005 alle 12.57
 */
package it.algos.base.campo.tipodati;

import it.algos.base.errore.Errore;


/**
 * Tipo di dati astratto da associare al campo <br>
 */
public abstract class TDBase implements TipoDati {

    /**
     * classe Java che rappresenta questo tipo
     */
    protected Class classe = null;

    /**
     * valore considerato vuoto per questo tipo
     */
    protected Object valoreVuoto = null;


    /**
     * Costruttore base senza parametri
     */
    protected TDBase() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Ritorna la classe Java che rappresenta questo tipo
     *
     * @return la classe che rappresenta questo tipo
     */
    public Class getClasse() {
        return this.classe;
    }


    /**
     * Assegna la classe Java che rappresenta questo tipo
     *
     * @param classe la classe che rappresenta questo tipo
     */
    protected void setClasse(Class classe) {
        this.classe = classe;
    }


    /**
     * Recupera il valore vuoto per questo tipo
     *
     * @return il valore vuoto per questo tipo
     */
    public Object getValoreVuoto() {
        return this.valoreVuoto;
    }


    /**
     * Assegna il valore vuoto per questo tipo
     *
     * @param valore il valore vuoto per questo tipo
     */
    protected void setValoreVuoto(Object valore) {
        this.valoreVuoto = valore;
    }


}// fine della classe

