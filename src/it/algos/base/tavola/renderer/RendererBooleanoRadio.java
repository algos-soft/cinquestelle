/**
 * Title:        RendererBooleanoRadio.java
 * Package:      it.algos.base.tavola.renderer
 * Description:  Renderer specifico
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 11 gennaio 2003 alle 10.25
 */
package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - ... <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  11 gennaio 2003 ore 10.25
 */
public class RendererBooleanoRadio extends RendererBase {

    /**
     * lista dei valori
     */
    private ArrayList unaLista = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public RendererBooleanoRadio() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampo
     */
    public RendererBooleanoRadio(Campo unCampo) {
        /** rimanda al costruttore della superclasse */
        super(unCampo);

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
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Effettua il rendering di un valore.
     * <p/>
     *
     * @param objIn valore in ingresso
     *
     * @return valore in uscita
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;
        Boolean bool;

        try {    // prova ad eseguire il codice

            objOut = objIn;

            unaLista = campo.getCampoDati().getListaValori(); //todo mettere in inizializza
            if ((objIn != null) && (objIn instanceof Boolean)) {
                bool = (Boolean)objIn;
                if (bool) {
                    objOut = BORDO + unaLista.get(1);
                } else {
                    objOut = BORDO + unaLista.get(0);
                } /* fine del blocco if/else */
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }

    //-------------------------------------------------------------------------
}// fine della classe

//-----------------------------------------------------------------------------

