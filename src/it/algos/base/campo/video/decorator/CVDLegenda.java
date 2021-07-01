/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 agosto 2003 alle 21.04
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.wrapper.TestoAlgos;

/**
 * Decoratore legenda della classe CampoVideo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna una legenda (descrizione) nel pannello campo del CampoVideo,
 * dopo il pannello componenti </li>
 * <li> La legenda può essere posizionata sotto o a destra </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 agosto 2003 ore 21.04
 */
public final class CVDLegenda extends CVDLabel {


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideoDecorato oggetto da decorare
     */
    public CVDLegenda(CampoVideo campoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(campoVideoDecorato);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** di default la legenda viene posizionata
         *  nella parte bassa del pannelloCampo */
        this.setPos(Pos.SOTTO);
    } /* fine del metodo inizia */


    /**
     * Regola le caratteristiche grafiche degli elementi GUI.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Regola font, colori, bordi e sfondi di tutti gli elementi GUI
     * del pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.video.CVBase#inizializza()
     */
    protected void regolaFontColori() {
        try {    // prova ad eseguire il codice
            /* regola colore e font dell'etichetta */
            TestoAlgos.setLegenda(this.getLabel());

            /* regola il colore di fondo della label */
            super.regolaFontColori();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


}// fine della classe