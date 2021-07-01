/**
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      07-mag-2008
 */

package it.algos.albergo.booking;

import it.algos.albergo.prenotazione.periodo.PeriodoLogica;
import it.algos.base.errore.Errore;

import java.util.Date;

/**
 * Implementazione logiche di controllo booking.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 07-mag-2008
 */
public abstract class Booking {

    /**
     * Controlla se una data camera è libera per un dato periodo.
     * <p/>
     *
     * @param codCamera codice della camera da controllare
     * @param dataInizio data inizio periodo
     * @param dataFine data fine periodo
     * @param codPeriodoEscluso codice dell'eventuale periodo da escludere dalla ricerca
     * @param codContoEscluso codice dell'eventuale conto da escludere dalla ricerca
     *
     * @return true se la camera è libera
     */
    public static boolean isCameraLibera(int codCamera,
                                         Date dataInizio,
                                         Date dataFine,
                                         int codPeriodoEscluso,
                                         int codContoEscluso) {
        /* variabili e costanti locali di lavoro */
        boolean libera = false;

        try { // prova ad eseguire il codice

            /* controlla nelle prenotazioni */
            libera = Booking.isCameraLiberaPren(codCamera, dataInizio, dataFine, codPeriodoEscluso);

            /* se libera nelle prenotazioni, controlla anche nei conti */
            if (libera) {
                libera = Booking.isCameraLiberaConti(codCamera,
                        dataInizio,
                        dataFine,
                        codContoEscluso);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return libera;
    }


    /**
     * Controlla nelle prenotazioni se una data camera è libera per un dato periodo.
     * <p/>
     *
     * @param codCamera codice della camera da controllare
     * @param dataInizio data inizio periodo
     * @param dataFine data fine periodo
     * @param codEscludi codice dell'eventuale periodo da escludere dalla ricerca
     *
     * @return true se la camera è libera
     */
    private static boolean isCameraLiberaPren(int codCamera,
                                              Date dataInizio,
                                              Date dataFine,
                                              int codEscludi) {
        /* variabili e costanti locali di lavoro */
        boolean libera = false;
        int codSovrapposto;

        try { // prova ad eseguire il codice
            codSovrapposto = PeriodoLogica.getCodPeriodoSovrapposto(dataInizio,
                    dataFine,
                    codCamera,
                    codEscludi);
            libera = (codSovrapposto == 0);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return libera;
    }


    /**
     * Controlla nei conti se una data camera è libera per un dato periodo.
     * <p/>
     *
     * @param codCamera codice della camera da controllare
     * @param dataInizio data inizio periodo
     * @param dataFine data fine periodo
     * @param codEscludi codice dell'eventuale conto da escludere dalla ricerca
     *
     * @return true se la camera è libera
     */
    private static boolean isCameraLiberaConti(int codCamera,
                                               Date dataInizio,
                                               Date dataFine,
                                               int codEscludi) {
        /* variabili e costanti locali di lavoro */
        boolean libera = false;

        try { // prova ad eseguire il codice

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return libera;
    }


} // fine della classe