/**
 * Title:     PanCondizioni
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.pannello.PannelloFlusso;

/**
 * Pannello superclasse comune a tutti i pannelli del dialogo
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-feb-2009 ore 12.38.23
 */
class PanDialogo extends PannelloFlusso {

    /**
     * dialogo di riferimento
     */
    private SviluppoDialogo dialogoMain;

    /**
     * Costruttore completo con parametri.
     * <p>
     * Orientamento di default verticale
     *
     * @param dialogo dialogo di riferimento
     */
    public PanDialogo(SviluppoDialogo dialogo) {
        this(dialogo, Layout.ORIENTAMENTO_VERTICALE);
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param dialogo dialogo di riferimento
     * @param orientamento del layout
     */
    public PanDialogo(SviluppoDialogo dialogo, int orientamento) {
        /* rimanda al costruttore della superclasse */
        super(orientamento);

        /* regola le variabili di istanza coi parametri */
        this.setDialogoMain(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo



    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setOpaque(false);
    }// fine del metodo inizia


    protected SviluppoDialogo getDialogoMain() {
        return dialogoMain;
    }


    private void setDialogoMain(SviluppoDialogo dialogoMain) {
        this.dialogoMain = dialogoMain;
    }

}// fine della classe