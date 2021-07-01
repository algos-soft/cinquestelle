/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      14-mag-2005
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;

import java.math.BigDecimal;

/**
 * LibJava. </p> Questa classe astratta: <ul> <li> </li> </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 14-mag-2005 ore 18.02.20
 */
public abstract class LibJava extends Object {

    /**
     * Controlla l'eguaglianza del valore di due oggetti stringa.
     * <p/>
     * Controlla che entrambi gli oggetti non siano nulli <br> Controlla che il primo oggetto sia
     * della classe corretta <br> Invoca il metodo equals del primo oggetto, passandogli il secondo
     * oggetto <br> Nel metodo equals viene fatto il controllo di classe per il secondo oggetto
     * <br>
     *
     * @param primo oggetto di classe String
     * @param secondo oggetto di classe String
     *
     * @return vero se il valore dei due oggetti è identico
     */
    private static boolean isUguale(String primo, String secondo) {
        /* variabili e costanti locali di lavoro */
        boolean uguale = false;
        boolean continua = false;

        try { // prova ad eseguire il codice
            continua = true;

            if (continua) {
                continua = (primo != null) ? true : false;
            }// fine del blocco if

            if (continua) {
                continua = (secondo != null) ? true : false;
            }// fine del blocco if

            if (continua) {
                continua = (primo instanceof String) ? true : false;
            }// fine del blocco if

            /* test di confronto */
            if (continua) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uguale;
    }


    /**
     * Controlla l'eguaglianza del valore di due oggetti booleani.
     * <p/>
     * Controlla che entrambi gli oggetti non siano nulli <br> Controlla che il primo oggetto sia
     * della classe corretta <br> Invoca il metodo equals del primo oggetto, passandogli il secondo
     * oggetto <br> Nel metodo equals viene fatto il controllo di classe per il secondo oggetto
     * <br>
     *
     * @param primo oggetto di classe Boolean
     * @param secondo oggetto di classe Boolean
     *
     * @return vero se il valore dei due oggetti è identico
     */
    private static boolean isUguale(Boolean primo, Boolean secondo) {
        /* variabili e costanti locali di lavoro */
        boolean uguale = false;
        boolean continua = false;

        try { // prova ad eseguire il codice
            continua = true;

            if (continua) {
                continua = (primo != null) ? true : false;
            }// fine del blocco if

            if (continua) {
                continua = (secondo != null) ? true : false;
            }// fine del blocco if

            if (continua) {
                continua = (primo instanceof Boolean) ? true : false;
            }// fine del blocco if

            /* test di confronto */
            if (continua) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uguale;
    }


    /**
     * Controlla l'eguaglianza del valore di due oggetti interi.
     * <p/>
     * Controlla che entrambi gli oggetti non siano nulli <br> Controlla che il primo oggetto sia
     * della classe corretta <br> Invoca il metodo equals del primo oggetto, passandogli il secondo
     * oggetto <br> Nel metodo equals viene fatto il controllo di classe per il secondo oggetto
     * <br>
     *
     * @param primo oggetto di classe Integer
     * @param secondo oggetto di classe Integer
     *
     * @return vero se il valore dei due oggetti è identico
     */
    private static boolean isUguale(Integer primo, Integer secondo) {
        /* variabili e costanti locali di lavoro */
        boolean uguale = false;
        boolean continua = false;

        try { // prova ad eseguire il codice
            continua = true;

            if (continua) {
                continua = (primo != null) ? true : false;
            }// fine del blocco if

            if (continua) {
                continua = (secondo != null) ? true : false;
            }// fine del blocco if

            if (continua) {
                continua = (primo instanceof Integer) ? true : false;
            }// fine del blocco if

            /* test di confronto */
            if (continua) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uguale;
    }


    /**
     * Controlla l'eguaglianza del valore di due oggetti.
     * <p/>
     * Controlla che entrambi gli oggetti siano della stessa classe <br> Invoca il metodo
     * sovrascritto per la classe specifica <br> Invoca il metodo equals della classe <br>
     *
     * @param primo oggetto di classe Object
     * @param secondo oggetto di classe Object
     *
     * @return vero se il valore dei due oggetti è identico
     */
    public static boolean isUguale(Object primo, Object secondo) {
        /* variabili e costanti locali di lavoro */
        boolean uguale = false;

        try { // prova ad eseguire il codice
            if ((primo instanceof String) && (secondo instanceof String)) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

            if ((primo instanceof Boolean) && (secondo instanceof Boolean)) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

            if ((primo instanceof Integer) && (secondo instanceof Integer)) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

            if ((primo instanceof BigDecimal) && (secondo instanceof BigDecimal)) {
                uguale = primo.equals(secondo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uguale;
    }

}// fine della classe
