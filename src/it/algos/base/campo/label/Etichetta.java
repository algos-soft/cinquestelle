/**
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * Author:       alex
 * Date:         9-ago-2005
 */
package it.algos.base.campo.label;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.wrapper.TestoAlgos;

import java.awt.*;

/**
 * Etichetta di contorno del campo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-ago-2005 ore 14.05
 */
public final class Etichetta extends LabelBase {

    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public Etichetta() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            TestoAlgos.setEtichetta(this);

            /* colore del testo quando abilitato */
            this.setColoreTestoAbilitato(this.getForeground());

            if (Campo.DEBUG) {
                this.setOpaque(true);
                this.setBackground(Color.ORANGE);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

}// fine della classe
