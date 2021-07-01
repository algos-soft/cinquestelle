package it.algos.gestione.anagrafica.categoria;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

import java.util.ArrayList;

/**
 * Descrizione Categorizzazione dei records di anagrafica.
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
 * @version 1.0 / 20-mar-2009
 */
public interface CatAnagrafica {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "CatAnagrafica";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "catanagrafica";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = NOME_MODULO;

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Categorie";

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

        sigla("sigla", "sigla", "sigla", "", true),
        descrizione("descrizione", "descrizione", "descrizione", "", true);

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
     * Codifica dei Navigatori del modulo
     * (oltre a quelli standard della superclasse)
     */
    public enum Tipo {

        cliente("clienti"),
        fornitore("fornitori"),
        agenzia("agenzie");

        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla del tipo
         */
        Tipo(String sigla) {
            /* regola le variabili di istanza coi parametri */
            this.setSigla(sigla);
        }// fine del metodo costruttore completo


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public String get() {
            return toString();
        }


        /**
         * Ritorna la lista delle sigle degli elementi.
         * <p/>
         */
        public static ArrayList<String> getSigle() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = new ArrayList<String>();

            try {    // prova ad eseguire il codice
                for (Tipo tipo : Tipo.values()) {
                    lista.add(tipo.getSigla());
                } // fine del ciclo for-each
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna la lista dei codici degli elementi.
         * <p/>
         */
        public static ArrayList<Integer> getCodici() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Integer> lista = new ArrayList<Integer>();

            try {    // prova ad eseguire il codice
                for (Tipo tipo : Tipo.values()) {
                    lista.add(tipo.getCodice());
                } // fine del ciclo for-each
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }




        /**
         * Ritorna il codice record dell'elemento.
         * <p/>
         * @return il codice record dell'elemento
         */
        public int getCodice() {
            return ordinal() + 1;
        }


    }// fine della Enumeration Tipo

} // fine dell'interfaccia
