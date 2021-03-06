package it.algos.albergo.odg.odgriga;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Popup;

/**
 * Interfaccia del pacchetto Righe Ordine del Giorno
 * </p>
 * Sezioni che compongono l'interfaccia (default) (scorciatoia): <ul>
 * <li> Costanti generali (default true) (cg) </li>
 * <li> Enumeration Cam per i campi (default true) (ec) </li>
 * <li> Enumeration Set per i campi (default false) (es) </li>
 * <li> Enumeration Vis per le viste (default false) (ev) </li>
 * <li> Enumeration Nav per i navigatori (default false) (en) </li>
 * <li> Enumeration Est per gli estratti (default false) (ee) </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public interface OdgRiga {

    /**
     * Codifica del nome-chiave interno del modulo
     */
    public static final String NOME_MODULO = "OdgRiga";

    /**
     * Codifica della tavola di archivio collegata
     */
    public static final String NOME_TAVOLA = "odgrighe";

    /**
     * Codifica del titolo della finestra
     */
    public static final String TITOLO_FINESTRA = "Righe Ordine del Giorno";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     */
    public static final String TITOLO_MENU = "Righe Ordine del Giorno";

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

        zona("linkzona", "", "", "", false),
        camera("linkcamera", "camera", "camera", "", true),
        periodo1("periodo1", "", "", "", false),
        periodo2("periodo2", "", "", "", false),

        fermata("fermata", "", "fermata", "", true),
        partenza("partenza", "", "partenza", "", true),
        arrivo("arrivo", "", "arrivo", "", true),
        cambio("cambio", "", "cambio", "", true),
        cambioDa("cambioda", "dalla camera", "cambio dalla", "", false),
        cambioA("cambioa", "alla camera", "cambio alla", "", false),
        parteDomani("partedomani", "parte domani", "parte domani", "", false),
        cambiaDomani("cambiadomani", "cambia domani", "cambia domani", "", false),
        chiudere("chiudere", "", "chiudere", "", true),
        dafare("dafare", "da preparare", "da preparare", "", true),
        composizione("preparazione", "", "composizione", "", true),
        compoprecedente("compoprec", "composizione precente", "preced.", "", false),
        note("note", "", "note", "", true),
        righeAccessori("righeaccesori", "righeaccesori", "lista accessori", "", false);

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
         * flag per la visibilit?? nella lista.
         * <p/>
         * nessun default <br>
         */
        private boolean visibileLista;


        /**
         * Costruttore completo con parametri.
         *
         * @param nomeInterno interno del campo usato nel database
         * @param titoloColonna titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda legenda del campo nella scheda
         * @param visibileLista flag per la visibilit?? nella lista
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
     * Codifica delle Viste del modulo.
     */
    public enum Vis {

        vistaRighe(); // vista usata nel Navigatore di Default delle Righe


        public String get() {
            return toString();
        }
    }// fine della classe


    /**
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        navRighe();


        public String get() {
            return toString();
        }
    }// fine della classe


    /**
     * Codifica dei popup (fltri) del modulo.
     */
    public enum Pop implements Popup {

        mioPop("Selettore"); // nuovo arrivo e cambio camera

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


}// fine della classe