/**
 * Title:     OpAnalisi
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.progressbar.OperazioneMonitorabile;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.wrapper.DueDate;
import it.algos.base.wrapper.SetValori;

/**
 * Operazione Sviluppo delle prenotazioni
 * </p>
 * Monitorata da ProgressBar
 */
class OpAnalisi extends OperazioneMonitorabile {

    private SviluppoDialogo dialogo;

    private Filtro filtroPeriodi;

    private Suddivisione suddivisione;

    private DueDate intervallo;

    private int quanti;

    private int max;

    private int[] codiciPeriodo;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param dialogo SviluppoDialogo di riferimento
     * @param filtroPeriodi il filtro che seleziona i periodi interessati
     * se è nullo usa tutti i periodi
     * @param suddivisione il tipo di suddivisione del risultato - obbligatorio
     * @param intervallo l'intervallo di sviluppo delle presenze
     * se è nullo, lo ricava automaticamente dalle date estreme del set di periodi
     * @param pb la progress bar di monitoraggio
     */
    public OpAnalisi(
            SviluppoDialogo dialogo,
            Filtro filtroPeriodi,
            Suddivisione suddivisione,
            DueDate intervallo,
            ProgressBar pb) {
        /* rimanda al costruttore della superclasse */
        super(pb);

        this.setDialogo(dialogo);
        this.setFiltroPeriodi(filtroPeriodi);
        this.setSuddivisione(suddivisione);
        this.setIntervallo(intervallo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            this.setMessaggio("Analisi in corso");
            this.setBreakAbilitato(true);

            /**
             * Recupera e registra i codici dei periodi da elaborare.
             * Va fatto prima di iniziare la elaborazione
             * perché la chiamata setMax() va fatta in fase statica
             * se no non funziona la progress bar
             */
            Modulo modPeriodo = PeriodoModulo.get();
            int[] codici = modPeriodo.query().valoriChiave(this.getFiltroPeriodi());
            this.setCodiciPeriodo(codici);
            this.setMax(codici.length);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void start() {

        try {    // prova ad eseguire il codice

            MappaDettagli mappa = this.creaMappa();
            this.riempiRisultati(mappa);
            this.getDialogo().setMappaDettagli(mappa);
            this.aggiornaGUI();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea una mappa con i dati di dettaglio.
     * </p>
     *
     * @return la mappa con i dati di dettaglio - null se i dati di impostazione non sono congrui
     */
    public MappaDettagli creaMappa() {
        MappaDettagli mappa = null;
        boolean continua;
        DueDate unPeriodo = null;

        try { // prova ad eseguire il codice

            Filtro filtroPeriodi = this.getFiltroPeriodi();
            Suddivisione suddivisione = this.getSuddivisione();
            DueDate periodo = this.getIntervallo();

            /* verifica che esista la suddivisione */
            continua = (suddivisione != null);

            /**
             * verifica che esista un periodo valido
             * se non esiste lo ricava dagli estremi dei periodi
             */
            if (continua) {
                boolean ok = false;
                if (periodo != null) {
                    if (periodo.isPieno() && periodo.isSequenza()) {
                        unPeriodo = periodo;
                        ok = true;
                    }// fine del blocco if
                }// fine del blocco if
                if (!ok) {
                    unPeriodo = SviluppoLogica.getEstremi(filtroPeriodi);
                    continua = (unPeriodo.isPieno() && unPeriodo.isSequenza());
                }// fine del blocco if
            }// fine del blocco if

            /**
             * crea la mappa dettagli suddivisa - una riga per ogni
             * elemento della suddivisione più la riga Altro
             */
            if (continua) {
                mappa = SviluppoLogica.creaMappaDettagli(unPeriodo, suddivisione);
                continua = (mappa != null);
            }// fine del blocco if

            /* riempie la mappa dettagli analizzando i periodi */
            if (continua) {
                this.riempiMappaDettagli(mappa, suddivisione, unPeriodo);
            }// fine del blocco if

            /**
             * - Se la la suddivisione è temporale cancella
             * l'elemento "Altro" se è senza valore.
             * - Se la la suddivisione non è temporale
             * pulisce la mappa dettagli dagli elementi senza valore.
             */
            if (continua) {
                if (suddivisione.isTemporale()) {
                    mappa.rimuoviAltroSeVuoto();
                } else {
                    mappa.rimuoviElementiVuoti();
                }// fine del blocco if-else
            }// fine del blocco if

            /**
             * Consolida le percentuali in ogni riga della mappa
             */
            if (continua) {
                mappa.calcPercentuali();
            }// fine del blocco if



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    }// fine del metodo inizia


    /**
     * Riempie la mappa analizzando i periodi.
     * <p/>
     *
     * @param mappa mappa dati da riempire
     * @param sudd tipo di suddivisione
     * @param estremi date estreme dell'intervallo di analisi -
     * considera solo le parti di periodo che ricadono in questo intervallo
     */
    private void riempiMappaDettagli(
            MappaDettagli mappa,
            Suddivisione sudd,
            DueDate estremi) {

        try {    // prova ad eseguire il codice

            /* spazzola i periodi e incasella ognuno al suo posto / ai suoi posti */
            int[] codici = this.getCodiciPeriodo();
            for (int k = 0; k < codici.length; k++) {
                this.quanti++;
                SviluppoLogica.incasellaPeriodo(mappa, codici[k], sudd, estremi);

                /* eventuale interruzione nella superclasse */
                if (super.isInterrompi()) {
                    break;
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * riempie i dati del modulo risultati.
     * <p/>
     *
     * @param mappa con i dati da utilizzare
     */
    private void riempiRisultati(MappaDettagli mappa) {
        try {    // prova ad eseguire il codice

            ModuloRisultati mod = this.getModRisultati();
            mod.svuotaRisultati();

            SetValori sv;

            if (mappa != null) {

                int key;
                RigaDettaglio riga;
                for (java.util.Map.Entry<Integer, RigaDettaglio> elem : mappa.entrySet()) {

                    key = elem.getKey();
                    riga = elem.getValue();

                    sv = new SetValori(mod);

                    sv.add(Nomi.chiave.get(), key);
                    sv.add(Nomi.sigla.get(), riga.getSigla());
                    sv.add(Nomi.descrizione.get(), riga.getDescrizione());
                    sv.add(Nomi.pres_ad.get(), riga.getPresAdulti());
                    sv.add(Nomi.pres_ba.get(), riga.getPresBambini());
                    sv.add(Nomi.pres_tot.get(), riga.getPresTotali());
                    sv.add(Nomi.percent_pres.get(), riga.getPercPresenze());
                    sv.add(Nomi.valore.get(), riga.getValore());
                    sv.add(Nomi.percent_val.get(), riga.getPercValore());

                    mod.query().nuovoRecord(sv.getListaValori());

                }

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * aggiorna la GUI.
     * <p/>
     */
    private void aggiornaGUI() {

        try { // prova ad eseguire il codice

            ModuloRisultati mod = this.getModRisultati();
            Lista lista = mod.getLista();

            /* regola il titolo della colonna di suddivisione */
            Campo campo = mod.getCampo(Nomi.sigla.get());
            String titolo = this.getSuddivisione().getLabel();
            lista.setTitoloColonna(campo, titolo);

            /* ricarica i dati */
            mod.getLista().caricaTutti();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private ModuloRisultati getModRisultati() {
        return this.getDialogo().getModuloRisultati();
    }



    public int getMax() {
        return max;
    }


    private void setMax(int max) {
        this.max = max;
    }


    public int getCurrent() {
        return quanti;
    }

    private SviluppoDialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(SviluppoDialogo dialogo) {
        this.dialogo = dialogo;
    }



    private Filtro getFiltroPeriodi() {
        return filtroPeriodi;
    }


    private void setFiltroPeriodi(Filtro filtroPeriodi) {
        this.filtroPeriodi = filtroPeriodi;
    }


    private Suddivisione getSuddivisione() {
        return suddivisione;
    }


    private void setSuddivisione(Suddivisione suddivisione) {
        this.suddivisione = suddivisione;
    }


    private DueDate getIntervallo() {
        return intervallo;
    }


    private void setIntervallo(DueDate estremi) {
        this.intervallo = estremi;
    }



    private int[] getCodiciPeriodo() {
        return codiciPeriodo;
    }


    private void setCodiciPeriodo(int[] codiciPeriodo) {
        this.codiciPeriodo = codiciPeriodo;
    }
} // fine della classe 'interna'

