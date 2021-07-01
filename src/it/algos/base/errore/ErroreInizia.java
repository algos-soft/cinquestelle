/**
 * Title:        ErroreInizia.java
 * Package:      it.algos.base.errore
 * Description:
 * Copyright:    Copyright (c) 2002-2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 14.09
 */
package it.algos.base.errore;

import it.algos.base.messaggio.MessaggioErrore;

import javax.swing.*;

/**
 * Questa classe e' responsabile di:<br>
 * A - gestire gli errori nel metodo iniziale <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 14.09
 */
public final class ErroreInizia extends Errore {

    /**
     * nome della classe che lancia l'errore
     */
    private String classe = "";


    /**
     * oggetto della classe che lancia l'errore
     */
    private Object oggetto = null;

    /**
     * oggetto con le informazioni di sistema
     */
    private Exception errore = null;


    /**
     * Costruttore base senza parametri <br>
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public ErroreInizia() {
        /** rimanda al costruttore di questa classe */
        this(null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unaClasse classe che ha generato l'errore
     * @param unErrore informazioni di errore passate dal sistema
     */
    public ErroreInizia(String unaClasse, Exception unErrore) {
        /** rimanda al costruttore della superclasse */
        super(unErrore);

        classe = unaClasse;
        errore = unErrore;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.iniziaClasse();
        } catch (Exception unErroreLocale) {           // intercetta l'errore
            new MessaggioErrore(ERRORE_INIZIA, this);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo
     *
     * @param unOggetto oggetto della classe che ha generato l'errore
     * @param unErrore informazioni di errore passate dal sistema
     */
    public ErroreInizia(Object unOggetto, Exception unErrore) {
        /** rimanda al costruttore della superclasse */
        super(unErrore);

        oggetto = unOggetto;
        errore = unErrore;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.iniziaOggetto();
        } catch (Exception unErroreLocale) {           // intercetta l'errore
            new MessaggioErrore(ERRORE_INIZIA, this);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     */
    private void iniziaClasse() throws Exception {
        new MessaggioErrore(ERRORE_INIZIA, classe);
        if (errore.getMessage() != null) {
//	    JOptionPane.showMessageDialog(null, unErrore.getMessage());
//	    JOptionPane.showMessageDialog(null, unErrore.getLocalizedMessage());
        } /* fine del blocco if */
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     */
    private void iniziaOggetto() throws Exception {
        new MessaggioErrore(ERRORE_INIZIA, oggetto);
        if (errore.getMessage() != null) {
            JOptionPane.showMessageDialog(null, errore.getMessage());
            JOptionPane.showMessageDialog(null, errore.getLocalizedMessage());
        } /* fine del blocco if */
    } /* fine del metodo inizia */
}// fine della classe