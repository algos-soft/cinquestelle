/**
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * Author:       alex
 * Date:         9-ago-2005
 */
package it.algos.base.campo.label;

import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;

/**
 * Elemento di contorno del campo.
 * <p/>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  9-ago-2005
 */
public abstract class LabelBase extends JLabel implements Labbel {

    /**
     * Colore del testo quando abilitato.
     */
    private Color ColoreTestoAbilitato = null;

    /**
     * Colore del testo quando disabilitato.
     */
    private Color ColoreTestoDisabilitato = null;


    /**
     * Costruttore base senza parametri
     * <p/>
     */
    public LabelBase() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione <br>
     */
    private void inizia() throws Exception {

        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* colori di default */
            this.setColoreTestoAbilitato(Color.BLACK);
            this.setColoreTestoDisabilitato(Color.LIGHT_GRAY);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Abilita o disabilita il componente.
     * <p/>
     * Sovrascrive il metodo della superclasse JComponent.<br>
     * Evita di disabilitare il componente che cambierebbe colore.<br>
     *
     * @param flag per abilitare o disabilitare
     */
    public void setEnabled(boolean flag) {

        if (flag) {
            this.setForeground(this.getColoreTestoAbilitato());
        } else {
            this.setForeground(this.getColoreTestoDisabilitato());
        }// fine del blocco if-else
    }


    private Color getColoreTestoAbilitato() {
        return ColoreTestoAbilitato;
    }


    protected void setColoreTestoAbilitato(Color coloreTestoAbilitato) {
        ColoreTestoAbilitato = coloreTestoAbilitato;
    }


    private Color getColoreTestoDisabilitato() {
        return ColoreTestoDisabilitato;
    }


    protected void setColoreTestoDisabilitato(Color coloreTestoDisabilitato) {
        ColoreTestoDisabilitato = coloreTestoDisabilitato;
    }


    public LabelBase getLabel() {
        return this;
    }


}// fine della classe
