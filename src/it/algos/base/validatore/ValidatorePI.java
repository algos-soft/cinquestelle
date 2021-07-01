/**
 * Title:     ValidatorePI
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      5-lug-2006
 */
package it.algos.base.validatore;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.*;

/**
 * Validatore di partita IVA.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-lug-2006
 */

public class ValidatorePI extends ValidatoreTesto {

    /**
     * Costruttore completo senza parametri.
     */
    public ValidatorePI() {
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
        this.setAccettaTestoVuoto(true);
        this.setLunghezzaMinima(11);
        this.setLunghezzaMassima(11);    // non limitata
    }


    /**
     * Implementazione della logica di validazione.
     * <p/>
     * Implementare la logica di validazione in questo metodo.
     * Ritorna true se il dato e' valido, false se non e' valido.
     * All'interno della validazione e'anche possibile modificare
     * il messaggio in caso di dato non valido usando setMessaggio().
     *
     * @param testo da validare.
     * @param comp componente di riferimento
     *
     * @return true se valido, false se non valido.
     */
    protected boolean validate(String testo, JComponent comp) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        String messaggio;

        try { // prova ad eseguire il codice

            valido = super.validate(testo, comp);

            if (valido) {
                messaggio = this.controllaPI(testo);
                if (Lib.Testo.isValida(messaggio)) {
                    this.setMessaggio(messaggio);
                    valido = false;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Valida un testo come partita IVA.
     * <p/>
     *
     * @return il motivo dell'errore, testo vuoto se non c'è errore
     */
    private String controllaPI(String pi) {
        /* variabili e costanti locali di lavoro */
        String errore = "";
        int i, s, c;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* controllo lunghezza = 0, in tal caso lo considera valido */
            if (continua) {
                if (pi.length() == 0) {
                    errore = "";
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controllo lunghezza esatta */
            if (continua) {
                if (pi.length() > 0) {
                    if (pi.length() != 11) {
                        errore = "Lunghezza non corretta: deve essere 11 caratteri";
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* controllo solo numeri */
            if (continua) {
                for (i = 0; i < 11; i++) {
                    c = pi.charAt(i);
                    if (c < '0' || c > '9') {
                        errore = "Caratteri non validi: deve contenere solo numeri";
                        continua = false;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* controllo checksum */
            if (continua) {

                s = 0;
                for (i = 0; i <= 9; i += 2) {
                    s += pi.charAt(i) - '0';
                }
                for (i = 1; i <= 9; i += 2) {
                    c = 2 * (pi.charAt(i) - '0');
                    if (c > 9) {
                        c = c - 9;
                    }
                    s += c;
                }
                if ((10 - s % 10) % 10 != pi.charAt(10) - '0') {
                    errore = "Partita IVA errata: il codice di controllo non corrisponde";
                    continua = false;
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errore;
    }


}// fine della classe
