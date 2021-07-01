/**
 * Title:     AtaskNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-giu-2007
 */
package it.algos.gestione.task;

import it.algos.base.azione.AzioneBase;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.stampa.StampaLista;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Navigatore Tasks all'interno dell'Evento.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-giu-2007
 */
public final class GTaskNavigatore extends NavigatoreLS implements GTask {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public GTaskNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            this.setUsaPannelloUnico(true);

            this.addSchedaCorrente(new GTaskScheda(this.getModulo()));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    public void inizializza() {

        try { // prova ad eseguire il codice

            this.setUsaSelezione(true);

            super.inizializza();

            /* aggiunge il bottone Esporta */
            this.addAzione(new AzEsporta());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Esporta l'elenco degli iscritti attualmente visualizzato.
     */
    private void export() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        ArrayList<Campo> campi;
        Connessione conn;

        try {    // prova ad eseguire il codice

            campi = this.getCampiExport();
            filtro = this.getLista().getFiltro();
            conn = this.getConnessione();
            GTaskModulo.get().exportTask(campi, filtro, conn);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna la lista dei campi da esportare.
     * <p/>
     * Nella superclasse aggiunge i campi comuni
     * Nelle sottoclassi superclasse aggiunge i campi specifici
     *
     * @return la lista dei campi da esportare
     */
    private ArrayList<Campo> getCampiExport() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = new ArrayList<Campo>();

        try {    // prova ad eseguire il codice

            campi.add(this.getCampoScheda(Cam.sigla.get()));
            campi.add(this.getCampoScheda(Cam.giornilavorazione.get()));
            campi.add(this.getCampoScheda(Cam.dataInizio.get()));
            campi.add(this.getCampoScheda(Cam.dataUtile.get()));
            campi.add(this.getCampoScheda(Cam.evaso.get()));

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Stampa la lista.
     * <p/>
     */
    public void stampaLista() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        StampaLista stampa = null;
        Vista vista;
        VistaElemento elem;
        Filtro filtro;
        Ordine ordine;
        String titolo;

        try {    // prova ad eseguire il codice

            modulo = this.getModulo();
            vista = new Vista(modulo);
            elem = vista.addCampo(Cam.sigla.get());
            elem.setLarghezzaColonna(100);
            elem = vista.addCampo(Cam.descrizione.get());
            elem.setLarghezzaColonna(200);
            elem = vista.addCampo(Cam.dataUtile.get());
            elem = vista.addCampo(Cam.evaso.get());

            filtro = this.getLista().getFiltro();
            ordine = this.getLista().getOrdine();

            titolo = "Elenco dei task ";

            /* crea, regola ed esegue la stampa */
            stampa = this.getModulo().getStampaLista(vista, filtro, ordine);
            stampa.setTitolo(titolo);
            stampa.setConnessione(this.getConnessione());
            stampa.run();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione Esporta Task.
     * </p>
     */
    private final class AzEsporta extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzEsporta() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
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
                super.setUsoLista(true);
                super.setIconaMedia("Export24");
                super.setTooltip("Esporta i Task attualmente visualizzati");
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                export();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'interna'


}// fine della classe
