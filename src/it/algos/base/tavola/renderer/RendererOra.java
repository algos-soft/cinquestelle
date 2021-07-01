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

import javax.swing.*;

/**
 * Renderer di colonna per le ore<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  22 dicembre 2002 ore 17.24
 */
public class RendererOra extends RendererFormat {


    /**
     * Costruttore completo senza parametri <br>
     */
    public RendererOra(Campo campo) {
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




}