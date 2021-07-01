package it.algos.albergo.promemoria;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Popup;

/**
 * Descrizione promemoria/avvisi.
 * </p>
 * Sezioni che compongono l'interfaccia (default) (scorciatoia): <ul>
 * <li> Costanti generali (default true) (kg) </li>
 * <li> Enumeration Cam per i campi (default true) (ec) </li>
 * <li> Enumeration Vis per le viste (default false) (ev) </li>
 * <li> Enumeration Set per i campi (default false) (es) </li>
 * <li> Enumeration Nav per i navigatori (default false) (en) </li>
 * <li> Enumeration Pop per i filtri (default false) (ep) </li>
 * <li> Enumeration Est per gli estratti (default false) (ee) </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @version 1.0 / 12-mar-2009
 */
public interface Promemoria {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Promemoria";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "promemoria";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = NOME_MODULO;

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = NOME_MODULO;

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica dell'ora di mezzanotte
     */
    public static final int MEZZANOTTE = 61;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     * Primo parametro: nome interno del campo usato nel database
     * -> se vuoto usa il tag dell'Enumeration
     * Secondo parametro: titolo della colonna della lista
     * -> se vuoto usa il nome interno
     * Terzo parametro: titolo dell'etichetta nella scheda
     * -> se vuoto usa il titolo della colonna della lista
     * Quarto parametro: legenda del campo nella scheda
     * -> se vuoto usa non visualizza nulla
     * Quinto parametro: flag per la visibilità nella lista
     * -> obbligatorio un valore booleano
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        eseguito("eseguito", "ok", "eseguito", "", true),
        linkTipo("tipopro", "tipo", "tipo", "", true),
        testo("testo", "testo", "descrizione", "", false),
        rifCamera("rifCamera", "cam", "camera", "riferimento facoltativo", true),
        rifCliente("rifCliente", "cl", "cliente", "riferimento facoltativo", true),
        oraScadenza("orascadenza", "ora", "", "", true),
        dataScadenza("datascadenza", "data", "", "", true),
        oraVisione("oravisione", "visione", "", "", false),
        dataVisione("datavisione", "visione", "", "", false),
        urgenza("urgenza", "urgenza", "urgenza", "livello di urgenza", false),
        idUtenteCrea("idUtenteCrea", "idUtenteCrea", "idUtenteCrea", "", false),
        idUtenteChiude("idUtenteChiude", "idUtenteChiude", "idUtenteChiude", "", false),;

        /**
         * nome interno del campo usato nel database.
         * <p/>
         * default il nome minuscolo della Enumeration <br>
         */
        private String nomeInterno;

        /**
         * titolo della colonna della lista.
         * <p/>
         * default il nome del campo <br>
         */
        private String titoloColonna;

        /**
         * titolo della etichetta in scheda.
         * <p/>
         * default il nome del campo <br>
         */
        private String etichettaScheda;

        /**
         * legenda del campo nella scheda.
         * <p/>
         * nessun default <br>
         */
        private String legenda;

        /**
         * flag per la visibilità nella lista.
         * <p/>
         * nessun default <br>
         */
        private boolean visibileLista;


        /**
         * Costruttore completo con parametri.
         *
         * @param nomeInterno     interno del campo usato nel database
         * @param titoloColonna   titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda         legenda del campo nella scheda
         * @param visibileLista   flag per la visibilità nella lista
         */
        Cam(String nomeInterno,
            String titoloColonna,
            String etichettaScheda,
            String legenda,
            boolean visibileLista) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nomeInterno);
                this.setTitoloColonna(titoloColonna);
                this.setEtichettaScheda(etichettaScheda);
                this.setLegenda(legenda);
                this.setVisibileLista(visibileLista);

                /* controllo automatico che ci sia il nome interno */
                if (Lib.Testo.isVuota(nomeInterno)) {
                    this.setNome(this.toString());
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return nomeInterno;
        }


        public String getNomeCompleto() {
            return NOME_MODULO + "." + nomeInterno;
        }


        public String getNome() {
            return nomeInterno;
        }


        private void setNome(String nomeInterno) {
            this.nomeInterno = nomeInterno;
        }


        public String getTitoloColonna() {
            return titoloColonna;
        }


        private void setTitoloColonna(String titoloColonna) {
            this.titoloColonna = titoloColonna;
        }


        public String getEtichettaScheda() {
            return etichettaScheda;
        }


        private void setEtichettaScheda(String etichettaScheda) {
            this.etichettaScheda = etichettaScheda;
        }


        public String getLegenda() {
            return legenda;
        }


        private void setLegenda(String legenda) {
            this.legenda = legenda;
        }


        public boolean isVisibileLista() {
            return visibileLista;
        }


        private void setVisibileLista(boolean visibileLista) {
            this.visibileLista = visibileLista;
        }
    }// fine della Enumeration Cam


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei popup (filtri) del modulo (regolati nel modello) <br>
     * Primo parametro: titolo del popup utilizzato per il tooltiptext
     * -> se vuoto usa il tag dell'Enumeration
     */
    public enum Pop implements Popup {

        eseguiti("Promemoria eseguiti"),
        tipi("Tipi di promemoria");

        /**
         * titolo del popup.
         * <p/>
         * utilizzato per il tooltiptext <br>
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo del popup utilizzato per il tooltiptext
         */
        Pop(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        private String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public String get() {
            return getTitolo();
        }
    }// fine della Enumeration Pop


} // fine dell'interfaccia
