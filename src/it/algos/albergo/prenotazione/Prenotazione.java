/**
 * Title:     ClienteAlbergo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.prenotazione;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Popup;

/**
 * Mantiene tutte le costanti pubbliche e le codifiche di questo package.
 * <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Estende l'interfaccia <i>Cliente</i> </li>
 * <li> Mantiene il percorso della cartella </li>
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica degli estratti (per gli altri moduli) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara i metodi astratti utilizzati in tutto il package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 7.57.03
 */
public interface Prenotazione {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Prenotazione";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "prenotazioni";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Prenotazioni";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Prenotazioni";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        azienda("azienda", "", "", "", false),
        cliente("linkcliente", "cliente", "cliente", "", true),
        dataPrenotazione("dataprenotazione", "prenotaz.", "data prenotazione", "", true),
        dataScadenza("datascadenza", "scadenza", "data scadenza", "", true),
        opzione("opzione", "", "", "", true),
        confermata("confermata", "conf", "", "", true),
        disdetta("annullata", "disd", "disdetta", "", true),
        chiusa("chiusa", "ch", "", "", true),
        caparra("caparra", "", "", "", true),
        dataCaparra("datacaparra", "", "ricevuta", "data di ricevimento", false),
        mezzoCaparra("mezzoCaparra", "", "a mezzo", "", false),
        numRF("rf", "", "n. ricevuta", "", false),
        caparraAccreditata("capaccreditata", "", "caparra accreditata", "", false),
        nostraConferma("nostraconferma", "", "conferma", "data nostra conferma", false),
        canale("linkCanale", "", "canale", "", false),
        periodi("", "", "", "", false),
        note("note", "", "", "", false);

        /**
         * nome interno del campo usato nel database.
         * <p/>
         * default il nome della Enumeration <br>
         */
        private String nome;

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
         * @param nome interno del campo usato nel database
         * @param titoloColonna titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda legenda del campo nella scheda
         * @param visibileLista flag per la visibilità nella lista
         */
        Cam(String nome,
            String titoloColonna,
            String etichettaScheda,
            String legenda,
            boolean visibileLista) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
                this.setTitoloColonna(titoloColonna);
                this.setEtichettaScheda(etichettaScheda);
                this.setLegenda(legenda);
                this.setVisibileLista(visibileLista);

                /* controllo automatico che ci sia il nome interno */
                if (Lib.Testo.isVuota(nome)) {
                    this.setNome(this.toString());
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return nome;
        }


        public String getNomeCompleto() {
            return NOME_MODULO + "." + nome;
        }


        public String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
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
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei popup (fltri) del modulo
     */
    public enum Pop implements Popup {

        aperte("Aperte/chiuse"),
        anni("Anni");

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
    }// fine della classe


    
    /**
     * Codifica delle Viste del modulo.
     */
    public enum Vis {

        storico(); // vista utilizzata dallo Storico


        public String get() {
            return toString();
        }
    }// fine della classe

    /**
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        // navigatore utilizzato dallo Storico Cliente
        storico();


        public String get() {
            return toString();
        }
    }// fine della classe

}// fine della interfaccia
