/**
 * Title:        RendererTesto.java
 * Package:      it.algos.base.tavola.renderer
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 20 dicembre 2002 alle 20.55
 */

package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Renderer di colonna per i campi testo<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  20 dicembre 2002 ore 20.55
 */
public class RendererTesto extends RendererFormat {


    /**
     * Costruttore completo senza parametri <br>
     */
    public RendererTesto(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
        /* regolazioni iniziali di riferimenti e variabili */
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
    } /* fine del metodo */


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
        String stringa = "";

        try {    // prova ad eseguire il codice
            if ((objIn != null) && (objIn instanceof String)) {
                stringa = (String)objIn;
                stringa = BORDO + stringa;
            } /* fine del blocco if */

            objOut = stringa;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}