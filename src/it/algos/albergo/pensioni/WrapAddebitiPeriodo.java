/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      10-mag-2006
 */
package it.algos.albergo.pensioni;

import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.base.database.connessione.Connessione;

import java.util.Date;

/**
 * Contenitore per le informazioni relative a
 * un pacchetto di addebiti da listino relative a un periodo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-feb-2008 ore 12.18.49
 */
public final class WrapAddebitiPeriodo extends WrapAddebiti {


    /**
     * Costruttore completo.
     * <p/>
     * Crea l'oggetto e lo riempie con i dati indicati
     *
     * @param codice del periodo
     * @param conn connessione da utilizzare per le letture dal database
     */
    public WrapAddebitiPeriodo(int codice, Connessione conn) {
        /* rimanda al costruttore della superclasse */
        this(null, null, 0, codice, conn);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * Crea l'oggetto e lo riempie con i dati indicati
     *
     * @param dataIni data di inizio del periodo (giorno di arrivo)
     * @param dataEnd data di fine del periodo (giorno di partenza)
     * @param persone numero persone previste
     * @param codPeriodo del periodo
     * @param conn connessione da utilizzare per le letture dal database
     */
    public WrapAddebitiPeriodo(Date dataIni,
                               Date dataEnd,
                               int persone,
                               int codPeriodo,
                               Connessione conn) {
        /* rimanda al costruttore della superclasse */
        super(dataIni,
                dataEnd,
                persone,
                codPeriodo,
                conn,
                PeriodoModulo.get(),
                AddebitoPeriodoModulo.get(),
                AddebitoPeriodoModulo.get().getCampo(AddebitoPeriodo.Cam.periodo),
                AddebitoPeriodoModulo.get().getCampo(Addebito.Cam.listino),
                AddebitoPeriodoModulo.get().getCampo(Addebito.Cam.codRigaListino),
                AddebitoPeriodoModulo.get().getCampo(AddebitoFisso.Cam.dataInizioValidita),
                AddebitoPeriodoModulo.get().getCampo(AddebitoFisso.Cam.dataFineValidita),
                AddebitoPeriodoModulo.get().getCampo(Addebito.Cam.quantita),
                AddebitoPeriodoModulo.get().getCampo(Addebito.Cam.prezzo),
                PeriodoModulo.get().getCampo(Periodo.Cam.arrivoPrevisto),
                PeriodoModulo.get().getCampo(Periodo.Cam.dataFineAddebiti),
                PeriodoModulo.get().getCampo(Periodo.Cam.persone));

    }// fine del metodo costruttore completo

//    /**
//     * Ritorna la data di fine addebiti in funzione della data di fine del record di testa.
//     * <p/>
//     * Per il Periodo, essendo la data di fine del record di testa spostata in avanti di 1 giorno
//     * rispetto alla data di fine addebiti, sposta indietro di un giorno la data.
//     * @param dataFineTesta la data di fine del record di testa
//     * @return la data di fine addebiti
//     */
//    protected Date getDataFineAddebiti (Date dataFineTesta) {
//        /* variabili e costanti locali di lavoro */
//        Date data=null;
//
//        try { // prova ad eseguire il codice
//            if (dataFineTesta!=null) {
//                data = Lib.Data.add(dataFineTesta, -1);
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        return data;
//    }

}// fine della classe