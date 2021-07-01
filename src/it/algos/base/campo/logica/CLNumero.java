/**
 * Title:     CLNumero
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-ago-2007
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

/**
 * campo logica per campi numerici.
 * <p/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  3-ago-2007
 */
public final class CLNumero extends CLBase {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CLNumero() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLNumero(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Allinea le variabili del Campo: da GUI verso video.
     * <p/>
     * Sovrascrive il metodo della superclasse
     * Se è una stringa nulla o vuota, usa "0" come valore video
     * Altrimenti rinvia alla superclasse
     */
    public void guiVideo() {
        /* variabili e costanti locali di lavoro */
        Object unValore;

        try { // prova ad eseguire il codice

            /* recupera il valore */
            unValore = this.getCampoVideoNonDecorato().recuperaGUI();

            if (Lib.Testo.isValida(unValore)) {
                super.guiVideo();
            } else {
                /* regola il valore */
                this.getCampoDati().setVideo("0");
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
