/**
 * Title:     CalcFactory
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-lug-2009
 */
package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.albergo.AlbergoLib;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;

import java.util.Date;

/**
 * Factory per la creazione di oggetti Calcolatori di movimenti
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-lug-2009 ore 8.48.24
 */
public class CalcFactory {

    /**
     * Crea un calcolatore in base a un intervallo di date.
     * <p/>
     *
     * @param data1 data di inizio periodo
     * @param data2 data di fine periodo
     *
     * @return il calcolatore creato
     */
    public static CalcMovimenti creaCalcolatore(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        CalcMovimenti calcolatore = null;
        CalcMovimenti.Tempi tempo;

        try { // prova ad eseguire il codice

            if (isDateValide(data1, data2)) {

                tempo = getTempo(data1, data2);
                switch (tempo) {
                    case passato:
                        calcolatore = new CalcPassato(data1, data2);
                        break;
                    case oggi:
                        calcolatore = new CalcOggi();
                        break;
                    case futuro:
                        calcolatore = new CalcFuturo(data1, data2);
                        break;
                    case cavallo:
                        calcolatore = new CalcCavallo(data1, data2);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            } else {
                new MessaggioAvviso("Date non valide o non in sequenza!");
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return calcolatore;

    }


    /**
     * controlla che le date fornite siano valide e in sequenza (anche stesso giorno)
     * se non lo sono ritorna false
     * <p/>
     *
     * @param d1 la data iniziale
     * @param d2 la data finale
     *
     * @return true se le date sono valide e in sequenza
     */
    private static boolean isDateValide(Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;

        try {    // prova ad eseguire il codice

            if (!Lib.Data.isValida(d1)) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (!Lib.Data.isValida(d2)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                continua = Lib.Data.isSequenza(d1, d2);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Ritorna l'indicatore del tempo analizzato.
     * <p/>
     *
     * @param data1 data di inizio periodo
     * @param data2 data di fine periodo
     *
     * @return l'indicatore del tempo analizzato
     */
    private static CalcMovimenti.Tempi getTempo(Date data1, Date data2) {
        /* variabili e costanti locali di lavoro */
        CalcMovimenti.Tempi tempo = null;
        Date d1, d2, d0;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            d1 = data1;
            d2 = data2;
            d0 = AlbergoLib.getDataProgramma();

            if (Lib.Data.isPrecedente(d0, d2)) {
                tempo = CalcMovimenti.Tempi.passato;
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (d1.equals(d2) && d1.equals(d0)) {
                    tempo = CalcMovimenti.Tempi.oggi;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (Lib.Data.isPosteriore(d0, d1)) {
                    tempo = CalcMovimenti.Tempi.futuro;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (Lib.Data.isCompresaUguale(d1, d2, d0)) {
                    tempo = CalcMovimenti.Tempi.cavallo;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tempo;

    }


}// fine della classe
