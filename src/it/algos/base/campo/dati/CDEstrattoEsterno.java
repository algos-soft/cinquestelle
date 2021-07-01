/**
 * Title:        CDIntero.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2007
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       23 nov 2007 ore 14.23
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Campo dati Estratto;
 * <p/>
 *
 * @author Guido Andrea Ceresa
 * @author alex
 * @version 1.0  /  23 nov 2007 ore 14.23
 */
public final class CDEstrattoEsterno extends CDEstratto {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDEstrattoEsterno() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDEstrattoEsterno(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


}// fine della classe
