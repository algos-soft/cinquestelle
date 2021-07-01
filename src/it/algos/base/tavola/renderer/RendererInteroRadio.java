/**
 * Title:        RendererInteroRadio.java
 * Package:      it.algos.base.tavola.renderer
 * Description:  Renderer specifico
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 16 gennaio 2003 alle 9.09
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
 * @version 1.0  /  16 gennaio 2003 ore 9.09
 */
public class RendererInteroRadio extends RendererBase {


    /**
     * lista dei valori
     */
    protected ArrayList unaLista = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public RendererInteroRadio() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampo
     */
    public RendererInteroRadio(Campo unCampo) {
        /** rimanda al costruttore della superclasse */
        super(unCampo);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
        Object objOut = "";

        try {    // prova ad eseguire il codice

            /** controllo di congruita' */
            if (unaLista == null) {
                objOut = "";
                return objOut;
            } /* fine del blocco if */

            /** controllo di congruita' */
            if (objIn instanceof String) {
                objOut = objIn;
                return objOut;
            } /* fine del blocco if */

            /** controllo di congruita' */
            if ((objIn instanceof Integer) | (objIn instanceof Long)) {
                int intero = 0;
                boolean nascondi = false;

                if (objIn instanceof Integer) {
                    intero = (Integer)objIn;
                } /* fine del blocco if */

                if (objIn instanceof Long) {
                    Long unValoreLungo = (Long)objIn;
                    intero = unValoreLungo.intValue();
                } /* fine del blocco if */

                if ((intero == 0) || (nascondi && (intero == unaLista.size()))) {
                    objOut = "";
                } else {
                    objOut = BORDO + unaLista.get(intero - 1);
                } /* fine del blocco if/else */
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}// fine della classe

