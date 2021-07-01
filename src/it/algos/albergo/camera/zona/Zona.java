package it.algos.albergo.camera.zona;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

/**
 * Divisione delle camere di Albergo in zone o piani.
 * </p>
 * Sezioni che compongono l'interfaccia (default) (scorciatoia): <ul>
 * <li> Costanti generali (default true) (cg) </li>
 * <li> Enumeration Cam per i campi (default true) (ec) </li>
 * <li> Enumeration Vis per le viste (default false) (ev) </li>
 * <li> Enumeration Set per i campi (default false) (es) </li>
 * <li> Enumeration Nav per i navigatori (default false) (en) </li>
 * <li> Enumeration Pop per i filtri (default false) (ep) </li>
 * <li> Enumeration Est per gli estratti (default false) (ee) </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 7-apr-2009 ore 16:39
 */
public interface Zona {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Zona";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "zona";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Zone";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Zone dell'albergo";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;
   
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

        sigla("sigla","zona","zona","",true),
        ;

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
}// fine della classe