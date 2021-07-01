/**
 * Title:     SviluppoLogica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      11-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodo;
import it.algos.albergo.prenotazione.periodo.periodoaddebito.AddebitoPeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.DueDate;

import java.util.Date;

/**
 * Business Logic della funzionalità Sviluppo Presenze
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 11-feb-2009 ore 22.04.29
 */
class SviluppoLogica {


    /**
     * Recupera le date estreme di un insieme di periodi.
     * <p/>
     *
     * @param filtroPeriodi filtro per isolare l'insieme di periodi
     *
     * @return il wrapper con le date estreme (dal primo arrivo all'ultima partenza)
     */
    static DueDate getEstremi(Filtro filtroPeriodi) {
        /* variabili e costanti locali di lavoro */
        DueDate estremi = null;
        Ordine ordine;
        Campo campo;
        Object valore;
        Date data1, data2;

        try {    // prova ad eseguire il codice
            Modulo modPeriodo;
            modPeriodo = PeriodoModulo.get();

            campo = modPeriodo.getCampo(Periodo.Cam.arrivoPrevisto);
            ordine = new Ordine(campo);
            valore = modPeriodo.query()
                    .valorePrimoRecord(campo.getNomeInterno(), filtroPeriodi, ordine);
            data1 = Libreria.getDate(valore);

            campo = modPeriodo.getCampo(Periodo.Cam.partenzaPrevista);
            ordine = new Ordine(campo);
            valore = modPeriodo.query()
                    .valoreUltimoRecord(campo.getNomeInterno(), filtroPeriodi, ordine);
            data2 = Libreria.getDate(valore);

            estremi = new DueDate(data1, data2);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return estremi;
    }


    /**
     * Crea la mappa delle righe di dettaglio.
     * <p/>
     * La mappa viene creata con tutti gli elementi
     * La colonna delle descrizioni di suddivisione è già riempita
     * Le altre celle sono vuote
     *
     * @param estremi date estreme di analisi
     * @param sudd tipo di suddivisione
     *
     * @return la mappa creata
     */
    static MappaDettagli creaMappaDettagli(DueDate estremi, Suddivisione sudd) {
        /* variabili e costanti locali di lavoro */
        MappaDettagli mappa = null;
        WrapSuddivisione elemento;

        try {    // prova ad eseguire il codice

            mappa = new MappaDettagli();

            /* crea le righe di dettaglio in base ai valori di suddivisione
            * e le aggiunge alla mappa con la chiave di suddivisione */
            WrapSuddivisione[] elementi = sudd.creaSuddivisione(estremi);
            for (int k = 0; k < elementi.length; k++) {
                elemento = elementi[k];
                int chiave = elemento.getChiave();
                String sigla = elemento.getSigla();
                String descrizione = elemento.getDescrizione();
                RigaDettaglio riga = new RigaDettaglio(sigla, descrizione);
                mappa.put(chiave, riga);
            } // fine del ciclo for

            /* aggiunge in fondo alla lista la riga "Altro" con chiave = 0 */
            RigaDettaglio riga = new RigaDettaglio("Altro","Non classificato");
            mappa.put(0, riga);


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mappa;

    }




    /**
     * Incasella i valori di un periodo nella mappa.
     * <p/>
     *
     * @param mappa mappa dati da riempire
     * @param codPeriodo codice del periodo da esaminare
     * @param sudd il tipo di suddivisione
     * @param estremi date estreme dell'intervallo di analisi
     */
    static void incasellaPeriodo(
            MappaDettagli mappa, int codPeriodo, Suddivisione sudd, DueDate estremi) {

        try {    // prova ad eseguire il codice

            if (sudd.isTemporale()) {
                incasellaDistribuito(mappa, codPeriodo, sudd, estremi);
            } else {
                incasellaUnico(mappa, codPeriodo, sudd, estremi);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Incasella un periodo in una unica casella.
     * <p/>
     * usato per suddivisioni non temporali
     *
     * @param mappa mappa dati da riempire
     * @param codPeriodo codice del periodo da esaminare
     * @param sudd il tipo di suddivisione
     * @param estremi date estreme dell'intervallo di analisi
     */
    private static void incasellaUnico(
            MappaDettagli mappa, int codPeriodo, Suddivisione sudd, DueDate estremi) {
        /* variabili e costanti locali di lavoro */
        int chiave;
        RigaDettaglio riga;

        try {    // prova ad eseguire il codice

            /* determina la chiave sulla quale incasellare il periodo */
            chiave = sudd.getChiavePeriodo(codPeriodo);

            /* recupera la riga */
            riga = mappa.get(chiave);

            /* determina i valori del periodo */
            ValoriPeriodo valori = getValoriPeriodo(codPeriodo, estremi);

            /* incrementa i valori della riga */
            riga.addPresAdulti(valori.getPresenzeAdulti());
            riga.addPresBambini(valori.getPresenzeBambini());
            riga.addValore(valori.getValore());

            /* aggiunge il codice periodo alla riga */
            riga.addCodPeriodo(codPeriodo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Incasella un periodo in una o più caselle della mappa.
     * <p/>
     * usato per suddivisioni di tipo temporale
     *
     * @param mappa mappa dati da riempire
     * @param codPeriodo codice del periodo da esaminare
     * @param sudd il tipo di suddivisione
     * @param estremi date estreme dell'intervallo di analisi
     */
    private static void incasellaDistribuito(
            MappaDettagli mappa, int codPeriodo, Suddivisione sudd, DueDate estremi) {
        try {    // prova ad eseguire il codice

            /* recupera i dati necessari dal periodo */
            Modulo modPeriodo = PeriodoModulo.get();
            Query query = new QuerySelezione(modPeriodo);
            Campo campoInizio = modPeriodo.getCampo(Periodo.Cam.arrivoPrevisto);
            Campo campoFine = modPeriodo.getCampo(Periodo.Cam.partenzaPrevista);
            Filtro filtro = FiltroFactory.codice(modPeriodo, codPeriodo);

            query.addCampo(campoInizio);
            query.addCampo(campoFine);
            query.setFiltro(filtro);

            Dati dati = modPeriodo.query().querySelezione(query);
            Date p1 = dati.getDataAt(campoInizio);
            Date p2 = dati.getDataAt(campoFine);
            dati.close();

            /* calcola l'intersezione tra il periodo in esame e gli estremi di analisi
            * d'ora in avanti analizza questo periodo */
            DueDate periodo = new DueDate(p1, p2);
            DueDate intersezione = Lib.Data.getIntersezionePeriodi(periodo, estremi);

            /**
             * Spazzola tutti i giorni del periodo da analizzare
             * per ogni giorno determina i valori e li aggiunge alla mappa
             * all'elemento di competenza
             */
            Date d1, d2;
            ValoriPeriodo valori;
            int chiave;
            d1 = intersezione.getData1();
            d2 = intersezione.getData2();
            RigaDettaglio riga;
            Date data = d1;
            while (Lib.Data.isPrecedenteUguale(d2, data)) {

                /**
                 * trova la posizione del giorno nella mappa e
                 * aggiunge i valori del periodo alla riga
                 */
                chiave = sudd.getChiaveGiorno(data);
                if (chiave>0) {
                    if (mappa.containsKey(chiave)) {
                        riga = mappa.get(chiave);
                    } else {
                        riga = mappa.get(0); //altro
                    }// fine del blocco if-else

                    valori = getValoriPeriodo(codPeriodo, data);
                    riga.addPresAdulti(valori.getPresenzeAdulti());
                    riga.addPresBambini(valori.getPresenzeBambini());
                    riga.addValore(valori.getValore());

                    /* aggiunge il codice periodo alla riga */
                    riga.addCodPeriodo(codPeriodo);

                }// fine del blocco if-else

                data = Lib.Data.add(data, 1);
            }// fine del blocco while

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Calcola i valori di sviluppo di un periodo in un dato giorno.
     * <p/>
     * In termini di presenze adulti, presenze bambini, valore economico
     *
     * @param codPeriodo il codice del periodo
     * @param data di analisi
     *
     * @return wrapper che incapsula i valori
     */
    private static ValoriPeriodo getValoriPeriodo(int codPeriodo, Date data) {
        return getValoriPeriodo(codPeriodo, new DueDate(data, data));
    }


    /**
     * Calcola i valori di sviluppo di un periodo in un dato intervallo di giorni.
     * <p/>
     * In termini di presenze adulti, presenze bambini, valore economico
     *
     * @param codPeriodo il codice del periodo
     * @param estremi gli estremi di analisi
     *
     * @return wrapper che incapsula i valori
     */
    private static ValoriPeriodo getValoriPeriodo(int codPeriodo, DueDate estremi) {
        /* variabili e costanti locali di lavoro */
        ValoriPeriodo valori = null;
        int pAdulti;
        int pBambini;
        double valore;

        try {    // prova ad eseguire il codice

            valori = new ValoriPeriodo();

            /* recupera i dati necessari dal periodo */
            Modulo modPeriodo = PeriodoModulo.get();
            Query query = new QuerySelezione(modPeriodo);
            Campo campoInizio = modPeriodo.getCampo(Periodo.Cam.arrivoPrevisto);
            Campo campoFine = modPeriodo.getCampo(Periodo.Cam.partenzaPrevista);
            Campo campoAdulti = modPeriodo.getCampo(Periodo.Cam.adulti);
            Campo campoBambini = modPeriodo.getCampo(Periodo.Cam.bambini);
            Filtro filtro = FiltroFactory.codice(modPeriodo, codPeriodo);

            query.addCampo(campoInizio);
            query.addCampo(campoFine);
            query.addCampo(campoAdulti);
            query.addCampo(campoBambini);
            query.setFiltro(filtro);

            Dati dati = modPeriodo.query().querySelezione(query);
            Date d1 = dati.getDataAt(campoInizio);
            Date d2 = dati.getDataAt(campoFine);
            int adulti = dati.getIntAt(campoAdulti);
            int bambini = dati.getIntAt(campoBambini);
            dati.close();

            /**
             * calcola gli estremi significativi del periodo
             * toglie il giorno di partenza alla data di fine
             * (il giorno di partenza non fa presenza e non vale per gli addebiti)
             */
            d2 = Lib.Data.add(d2, -1);
            DueDate periodo = new DueDate(d1, d2);

            /**
             * calcola il numero di giorni di intersezione tra
             * le date di inizio e fine periodo e le estremità di analisi
             */
            DueDate intersezione = Lib.Data.getIntersezionePeriodi(estremi, periodo);
            if (intersezione != null) {

                /* numero di giorni buoni per le presenze */
                int giorni = intersezione.getQuantiGiorni() + 1;

                /* numero di presenze */
                pAdulti = adulti * giorni;
                pBambini = bambini * giorni;
                valore = getValoreEconomicoPeriodo(codPeriodo, intersezione);

                /* incapsula i risultati */
                valori = new ValoriPeriodo(pAdulti, pBambini, valore);

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valori;
    }


    /**
     * Ritorna il valore economico che un periodo sviluppa
     * in un dato intervallo di giorni.
     * <p/>
     * Si basa sugli addebiti previsti
     *
     * @param codPeriodo il codice del periodo
     * @param intervallo l'intervallo di giorni
     *
     * @return il valore economico
     */
    private static double getValoreEconomicoPeriodo(int codPeriodo, DueDate intervallo) {
        /* variabili e costanti locali di lavoro */
        double valore = 0;
        Modulo mod;
        Query query;
        Filtro filtro;
        Dati dati;

        try {    // prova ad eseguire il codice

            /**
             * Recupera le date estreme del periodo.
             * Serviranno per controllare che gli addebiti previsti
             * non si estendano per errore fuori dal periodo di competenza.
             * In tal caso verranno ritagliati in modo da non farli
             * uscire dagli estremi del periodo.
             * Un addebito previsto ha senso dal giorno dell'arrivo fino
             * al giorno prima della partenza.
             * Per esempio, Quindi se un periodo inizia il giorno 4 e termina
             * il giorno 20, ogni suo addebito previsto non può
             * iniziare prima del 4 o terminare dopo il 19.
             */
            mod = PeriodoModulo.get();
            query = new QuerySelezione(mod);
            Campo cIniPer = mod.getCampo(Periodo.Cam.arrivoPrevisto);
            Campo cEndPer = mod.getCampo(Periodo.Cam.partenzaPrevista);
            query.addCampo(cIniPer);
            query.addCampo(cEndPer);
            filtro = FiltroFactory.codice(mod, codPeriodo);
            query.setFiltro(filtro);
            dati = mod.query().querySelezione(query);
            Date dIniPer = dati.getDataAt(cIniPer);
            Date dEndPer = dati.getDataAt(cEndPer);
            dati.close();

            /* recupera l'elenco degli addebiti previsti */
            mod = AddebitoPeriodoModulo.get();
            query = new QuerySelezione(mod);
            Campo cD1 = mod.getCampo(AddebitoFisso.Cam.dataInizioValidita);
            Campo cD2 = mod.getCampo(AddebitoFisso.Cam.dataFineValidita);
            Campo cQta = mod.getCampo(Addebito.Cam.quantita);
            Campo cPrezzo = mod.getCampo(Addebito.Cam.prezzo);
            query.addCampo(cD1);
            query.addCampo(cD2);
            query.addCampo(cQta);
            query.addCampo(cPrezzo);
            filtro = FiltroFactory.crea(AddebitoPeriodo.Cam.periodo.get(), codPeriodo);
            query.setFiltro(filtro);
            dati = mod.query().querySelezione(query);

            /* esamina ogni riga e incrementa il valore */
            DueDate dateAddebito = new DueDate();
            for (int row = 0; row < dati.getRowCount(); row++) {
                Date d1 = dati.getDataAt(row, cD1);
                Date d2 = dati.getDataAt(row, cD2);
                int qta = dati.getIntAt(row, cQta);
                double prezzo = dati.getDoubleAt(row, cPrezzo);

                /**
                 * Ritaglia l'intervallo di addebito se per errore si estende
                 * fuori dagli estremi del periodo.
                 * Vedi discussione precedente.
                 */
                if (Lib.Data.isPrecedente(dIniPer, d1)) {
                    d1 = dIniPer;
                }// fine del blocco if
                if (Lib.Data.isPosterioreUguale(dEndPer, d2)) {
                    d2 = Lib.Data.add(dEndPer, -1);
                }// fine del blocco if

                /* controlla che il periodo sia valido (date in sequenza) */
                if (Lib.Data.isPosterioreUguale(d1, d2)) {
                    dateAddebito.setData1(d1);
                    dateAddebito.setData2(d2);
                    valore += getValoreAddebito(dateAddebito, qta, prezzo, intervallo);
                }// fine del blocco if

            } // fine del ciclo for
            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore economico che un addebito previsto sviluppa
     * in un dato intervallo di giorni.
     * <p/>
     *
     * @param dateAddebito le estremità dell'addebito previsto
     * @param qta la quantità
     * @param prezzo il prezzo
     * @param intervallo le estremità del periodo analizzato
     *
     * @return il valore economico dell'addebito nel periodo analizzato
     */
    private static double getValoreAddebito(
            DueDate dateAddebito, int qta, double prezzo, DueDate intervallo) {
        /* variabili e costanti locali di lavoro */
        double valore = 0;

        try { // prova ad eseguire il codice

            /* trova l'intersezione tra i due periodi*/
            DueDate intersezione = Lib.Data.getIntersezionePeriodi(dateAddebito, intervallo);

            /* esegue solo se i due periodi hanno date in comune */
            if (intersezione != null) {

                /**
                 * calcola i giorni che verranno addebitati
                 * tanti quanti sono i giorni nell'adebito, estremi compresi
                 */
                int giorni = intersezione.getQuantiGiorni() + 1;

                /* valore totale dell'addebito nell'intervallo interessato */
                valore = qta * prezzo * giorni;
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;

    }



    


}// fine della classe
