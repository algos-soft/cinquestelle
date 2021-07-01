/**
 * Title:        RendererData.java
 * Package:      it.algos.base.tavola.renderer
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 22 dicembre 2002 alle 17.24
 */
package it.algos.base.tavola.renderer;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.SwingConstants;
import java.util.Date;

/**
 * Renderer di colonna per le date<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  22 dicembre 2002 ore 17.24
 */
public class RendererData extends RendererFormat {


    /**
     * Costruttore completo senza parametri <br>
     * @param campo di riferimento
     */
    public RendererData(Campo campo) {
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
        setHorizontalAlignment(SwingConstants.CENTER);
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
        Date data = null;

        try { // prova ad eseguire il codice

            /* se e' la data vuota, la rende nulla */
            if ((objIn != null) && (objIn instanceof Date)) {
                data = (Date)objIn;
                if (Lib.Data.isVuota(data)) {
                    data = null;
                }// fine del blocco if-else
            }// fine del blocco if

            /* valore in uscita */
            objOut = super.rendValue(data);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }


}