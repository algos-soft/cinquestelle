/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-set-2005
 */
package it.algos.base.validatore;

import static it.algos.base.costante.CostCaratteri.PAROLA;
import it.algos.base.errore.Errore;

import javax.swing.*;

/**
 * Validatore astratto specializzato per valori di testo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 27-set-2005 ore 10.36.10
 */

public class ValidatoreTesto extends ValidatoreRegexp {

    /**
     * Flag - controllo accettazione testo vuoto
     */
    private boolean accettaVuoto;

    /**
     * Lunghezza minima.
     */
    private int lunghezzaMinima;

    /**
     * Lunghezza massima (0 = non limitata).
     */
    private int lunghezzaMassima;


    /**
     * Costruttore completo senza parametri.
     */
    public ValidatoreTesto() {
        /* rimanda al costruttore della superclasse */
        super();

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
        this.setEspressione(PAROLA);
        this.setAccettaTestoVuoto(true);  // default
        this.setLunghezzaMinima(0);
        this.setLunghezzaMassima(0);    // non limitata
    }


    /**
     * Implementazione della logica di validazione.
     * <p/>
     * Implementare la logica di validazione in questo metodo.
     * Ritorna true se il dato e' valido, false se non e' valido.
     * All'interno della validazione e'anche possibile modificare
     * il messaggio in caso di dato non valido usando setMessaggio().
     *
     * @param testo da validare
     * @param comp componente di riferimento
     *
     * @return true se valido, false se non valido.
     */
    protected boolean validate(String testo, JComponent comp) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;

        try { // prova ad eseguire il codice

            /* controllo accettazione testo vuoto */
            valido = this.checkTestoVuoto(testo);

            /* controllo della lunghezza minima */
            if (valido) {
                valido = this.checkLunghezzaMinima(testo);
            }// fine del blocco if

            /* controllo della lunghezza massima */
            if (valido) {
                valido = this.checkLunghezzaMassima(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla l'accettazione del testo vuoto.
     * <p/>
     *
     * @param testo da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkTestoVuoto(String testo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;

        try {    // prova ad eseguire il codice

            if (!this.isAccettaVuoto()) {
                if (testo.length() == 0) {
                    valido = false;
                    this.setMessaggio("Il campo non può essere vuoto");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla che il valore sia uguale o superiore al minimo.
     * <p/>
     *
     * @param testo da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkLunghezzaMinima(String testo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        int min;
        int lun;

        try {    // prova ad eseguire il codice

            min = this.getLunghezzaMinima();

            /* se accetta il testo vuoto il min è zero */
            if (this.isAccettaVuoto()) {
                min = 0;
            }// fine del blocco if

            lun = testo.length();
            if (lun < min) {
                valido = false;
                this.setMessaggio("La lunghezza minima è " + min);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla che il valore sia inferiore o uguale al massimo.
     * <p/>
     *
     * @param testo da controllare
     *
     * @return true se valido, false se invalido
     */
    private boolean checkLunghezzaMassima(String testo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        int max;
        int lun;

        try {    // prova ad eseguire il codice

            max = this.getLunghezzaMassima();
            if (max > 0) {
                lun = testo.length();
                if (lun > max) {
                    valido = false;
                    this.setMessaggio("La lunghezza massima è " + max);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    private boolean isAccettaVuoto() {
        return accettaVuoto;
    }


    /**
     * Controllo accettazione testo vuoto.
     *
     * @param accettaVuoto true per accettare il testo vuoto
     */
    public void setAccettaTestoVuoto(boolean accettaVuoto) {
        this.accettaVuoto = accettaVuoto;
    }


    private int getLunghezzaMinima() {
        return lunghezzaMinima;
    }


    /**
     * Controllo lunghezza minima del testo.
     *
     * @param lunghezzaMinima del testo
     */
    public void setLunghezzaMinima(int lunghezzaMinima) {
        this.lunghezzaMinima = lunghezzaMinima;
    }


    private int getLunghezzaMassima() {
        return lunghezzaMassima;
    }


    /**
     * Controllo lunghezza massima del testo.
     *
     * @param lunghezzaMassima del testo
     */
    public void setLunghezzaMassima(int lunghezzaMassima) {
        this.lunghezzaMassima = lunghezzaMassima;
    }

}// fine della classe
