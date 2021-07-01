/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-nov-2006
 */
package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

/**
 * Renderer di colonna per i numeri<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  20 dicembre 2002 ore 19.08
 */
public class RendererPercentuale extends RendererNumero {


    /**
     * Costruttore completo senza parametri <br>
     */
    public RendererPercentuale(Campo campo) {
        /** rimanda al costruttore della superclasse */
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
        double doppio = 0;
        Campo campo = null;
        String stringa = "";

        try {    // prova ad eseguire il codice

            /* calcola la percentuale e arrotonda */
            if ((objIn != null) && (objIn instanceof Double)) {
                doppio = (Double)objIn;
                doppio *= 100;
                doppio = Lib.Mat.arrotonda(doppio, 2);
            }// fine del blocco if

            /* costruisce la stringa (solo se != 0) */
            if (doppio != 0) {
                campo = this.getCampo();
                stringa = campo.format(doppio);
                stringa += "%";
            }// fine del blocco if-else

            /* rinvia alla superclasse */
            objOut = stringa;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}
