/**
 * Title:     ValidatoreCF
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
 * Validatore di codice fiscale.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-lug-2006
 */

public class ValidatoreCF extends ValidatoreTesto {


    /**
     * Costruttore completo senza parametri.
     */
    public ValidatoreCF() {
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
        this.setLunghezzaMinima(16);
        this.setLunghezzaMassima(16);    // non limitata
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
        String messaggio;

        try { // prova ad eseguire il codice

            valido = super.validate(testo, comp);

            if (valido) {
                messaggio = this.controllaCF(testo);
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
     * Valida un testo come codice fiscale.
     * <p/>
     *
     * @return il motivo dell'errore, testo vuoto se non c'è errore
     */
    private String controllaCF(String cf) {
        /* variabili e costanti locali di lavoro */
        String errore = "";
        int i, s, c;
        String cf2 = "";
        int setdisp[] =
                {1,
                        0,
                        5,
                        7,
                        9,
                        13,
                        15,
                        17,
                        19,
                        21,
                        2,
                        4,
                        18,
                        20,
                        11,
                        3,
                        6,
                        8,
                        12,
                        14,
                        16,
                        10,
                        22,
                        25,
                        24,
                        23};
        boolean continua = true;


        try {    // prova ad eseguire il codice

            /* controllo lunghezza = 0, in tal caso lo considera valido */
            if (continua) {
                if (cf.length() == 0) {
                    errore = "";
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controllo lunghezza esatta */
            if (continua) {
                if (cf.length() > 0) {
                    if (cf.length() != 16) {
                        errore = "Lunghezza non corretta: deve essere 16 caratteri";
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* converte in maiuscole */
            if (continua) {
                cf2 = cf.toUpperCase();
            }// fine del blocco if

            if (continua) {
                for (i = 0; i < 16; i++) {
                    c = cf2.charAt(i);
                    if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'Z')) {
                        errore = "Caratteri non validi: deve contenere solo lettere e numeri";
                        continua = false;
                        break;
                    }// fine del blocco if
                }
            }// fine del blocco if

            /* controllo checksum */
            if (continua) {

                s = 0;
                for (i = 1; i <= 13; i += 2) {
                    c = cf2.charAt(i);
                    if (c >= '0' && c <= '9') {
                        s = s + c - '0';
                    } else {
                        s = s + c - 'A';
                    }
                }

                for (i = 0; i <= 14; i += 2) {
                    c = cf2.charAt(i);
                    if (c >= '0' && c <= '9') {
                        c = c - '0' + 'A';
                    }
                    s = s + setdisp[c - 'A'];
                }

                if (s % 26 + 'A' != cf2.charAt(15)) {
                    errore = "Codice fiscale errato: il codice di controllo non corrisponde";
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
