/**
 * Title:        RendererTesto.java
 * Package:      it.algos.base.tavola.renderer
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 20 dicembre 2002 alle 19.08
 */

package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import javax.swing.*;

/**
 * Renderer di colonna per i numeri<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  20 dicembre 2002 ore 19.08
 */
public class RendererNumero extends RendererFormat {


    /**
     * Costruttore completo
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererNumero(Campo campo) {
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
        setHorizontalAlignment(SwingConstants.RIGHT);
    } /* fine del metodo */


    /**
     * Effettua il rendering di un valore numerico.
     * <p/>
     * Se il valore in ingresso è zero ritorna stringa vuota
     * altrimenti rimanda alla superclasse
     *
     * @param objIn valore in ingresso
     *
     * @return valore in uscita
     */
    public Object rendValue(Object objIn) {
        /* variabili e costanti locali di lavoro */
        Object objOut = null;
        Number numero;

        try { // prova ad eseguire il codice

            if (objIn != null) {
                if (objIn instanceof Number) {
                    numero = (Number)objIn;
                    if (numero.doubleValue() == 0) {
                        objOut = "";
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if-else

            if (objOut == null) {
                objOut = super.rendValue(objIn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}


