/**
 * Title:        MenuRendererData.java
 * Description:  Renderer specifico per la data nella lista del menu
 * Copyright:    Copyright (c) 2002, 2010
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 04-06-2010
 */
package it.algos.albergo.ristorante.menu;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.renderer.RendererData;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Renderer per la data nella lista del menu.
 * <p/>
 */
public class MenuRendererData extends RendererData {

    /* formato da utilizzare */
    private Format formatter = new SimpleDateFormat("EEE d MMM yy");

    /**
     * Costruttore completo
     */
    public MenuRendererData(Campo campo) {
        super(campo);
    } /* fine del metodo costruttore completo */

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
        Date data;
        Object objOut = "";

        try { // prova ad eseguire il codice

            /* se e' la data vuota, la rende nulla */
            if ((objIn != null) && (objIn instanceof Date)) {
                data = (Date)objIn;
                if (!Lib.Data.isVuota(data)) {
                    objOut = formatter.format(data);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return objOut;
    }

}