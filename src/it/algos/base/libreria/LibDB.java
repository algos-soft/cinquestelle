/**
 * Title:     LibDB
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-gen-2004
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;

/**
 * Repository di funzionalità per la gestione del database. </p> Tutti i metodi sono statici <br> I
 * metodi non hanno modificatore così sono visibili all'esterno del package solo utilizzando
 * l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-gen-2004 ore 11.10.32
 */
public final class LibDB {


    /**
     * Raddoppia i caratteri riservati del DB (single quote e backslash) per poterli registrare
     *
     * @param stringaInput la stringa da proteggere
     *
     * @return una stringa con i caratteri riservati protetti
     */
    static String proteggiCaratteriRiservati(String stringaInput) {
        /* variabili e costanti locali di lavoro */
        String stringaOutput = null;
        String stringaRiservata;
        String stringaSostitutiva;

        /*
        * Il singolo backslash e' scritto nella regex come 4 backslashes!
        * Vedi spiega seguente...
        * The same backslash-mess occurs when providing replacement strings
        * for methods like String.replaceAll() as literal Java strings in
        * your Java code. In the replacement text, a dollar sign must be
        * encoded as \$ and a backslash as \\ when you want to replace the
        * regex match with an actual dollar sign or backslash. However,
        * backslashes must also be escaped in literal Java strings. So a
        * single dollar sign in the replacement text becomes "\\$" when
        * written as a literal Java string. The single backslash becomes "\\\\".
        * Right again: 4 backslashes to insert a single one.
        */
        final String[] caratteriRiservati = {"'", "\\\\"};


        try { // prova ad eseguire il codice

            /* ciclo for per tutti i caratteri riservati da proteggere */
            stringaOutput = stringaInput;
            for (int k = 0; k <= caratteriRiservati.length - 1; k++) {

                /* recupera la singola stringa riservata */
                stringaRiservata = caratteriRiservati[k];

                /* crea una stringa sostitutiva (la stringa riservata raddoppiata)*/
                stringaSostitutiva = stringaRiservata + stringaRiservata;

                /* sostituisce tutte le ricorrenze della stringa riservata */
                stringaOutput = stringaOutput.replaceAll(stringaRiservata, stringaSostitutiva);

            } /* fine del blocco for */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaOutput;
    }

}// fine della classe
