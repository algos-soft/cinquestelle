package it.algos.albergo.presenza;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Popup;

/**
 * Interfaccia del pacchetto - @todo Manca la descrizione dell'interfaccia.
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
 * @author gac
 * @version 1.0 / 22-gen-2008 ore  16:00
 */
public interface Presenza {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Presenza";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "presenza";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Presenze";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Presenze";

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

        ps("ps", "ps", "progressivo PS", "progressivo Pubblica Sicurezza ", true),
        arrivo("primoarrivo", "arrivo", "arrivo", "data di arrivo", false),
        entrata("entrata", "dal", "dal", "data di inizio periodo", true),
        uscita("uscita", "al", "al", "data fine periodo", true),
        presenze("presenze", "", "", "", false),
        chiuso("chiuso", "chiuso", "", "", false),
        cliente("linkcliente", "cliente", "cliente", "", true),
        camera("linkcamera", "camera", "camera", "camera occupata", true),
        pensione("pensione", "pens", "", "", true),
        pasto("pasto", "", "", "", true),
        tavolo("linktavolo", "tavolo", "tavolo", "", true),
        bambino("bambino", "bam", "", "", true),
        conto("linkconto", "conto", "conto", "conto di riferimento", true),
        arrivoCon("arrivoCon", "arrivo con", "arrivo con", "", false),
        periodo("linkperiodo", "periodo", "periodo", "", false),
        cambioEntrata("cambioEntrata", "tipo", "cambio entrata", "cambio camera in arrivo", true),
        cambioUscita("cambioUscita", "tipo", "cambio uscita", "cambio camera in uscita", true),
        provvisoria("partenzaoggi", "", "", "", true),
        azienda("azienda", "", "", "", true);

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
         * @param nomeInterno interno del campo usato nel database
         * @param titoloColonna titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda legenda del campo nella scheda
         * @param visibileLista flag per la visibilità nella lista
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

        conto(), // vista usata nella scheda conto
        clienteParentela(), // vista con cliente e parentela
        partenzaCambioManuale(), // vista con data arrivo, camera, cliente e parentela
        annullaArrivo(), // vista per navigatore annullamento arrivo
        annullaPartenza(), // vista per navigatore annullamento partenze
        confermaCambio(), // vista per navigatore conferma cambio
        gestioneps(), // vista per il dialogo di gestione PS
        storico(); // vista utilizzata dallo Storico Presenze


        public String get() {
            return toString();
        }
    }// fine della classe


    /**
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        conto(),
        // navigatore usato nella scheda conto
        clienteparentela(),
        // navigatore solo lista con nome cliente e parentela
        arrivoCameraClienteParentela(),
        // navigatore solo lista con data arrivo, camera, nome cliente e parentela
        annullaArrivo(),
        // navigatore solo lista visualizzato in annullamento arrivo
        annullaPartenza(),
        // navigatore solo lista visualizzato in conferma cambio
        confermaCambio(),

        // navigatore utilizzato dallo Storico Presenze
        storico();


        public String get() {
            return toString();
        }
    }// fine della classe


    /**
     * Codifica dei popup (fltri) del modulo.
     */
    public enum Pop implements Popup {

        presenti("Selettore"), // presenti, arrivati oggi, partiti oggi etc e storici
        pensioni("Pensione"), // tipo pensione
        pasti("Pasti"), // tipo pasto
        adultibambini("Adulti/Bambini"), // adulti o bambini
        registrati("Pubblica Sicurezza"); // registrati e non registrati

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
     * Codifica dei tipi di pasto.
     * <p/>
     */
    enum TipiPasto implements Campo.ElementiCombo {

        breakfast(1, "breakfast", "breakfast"),
        lunch(2, "lunch", "lunch"),
        dinner(3, "dinner", "dinner");

        private int codice;

        private String descrizione;

        private String sigla;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice per il database
         * @param descrizione dell'elemento
         * @param sigla dell'elemento
         */
        TipiPasto(int codice, String descrizione, String sigla) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setDescrizione(descrizione);
                this.setSigla(sigla);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Ritorna la descrizione dal codice
         * <p/>
         *
         * @param codice il codice
         *
         * @return la descrizione
         */
        public static String getDescrizione(int codice) {
            /* variabili e costanti locali di lavoro */
            String descrizione = "";
            TipiPasto[] valori;
            int unCodice;

            try { // prova ad eseguire il codice
                valori = TipiPasto.values();
                for (TipiPasto tipo : valori) {
                    unCodice = tipo.getCodice();
                    if (unCodice == codice) {
                        descrizione = tipo.getDescrizione();
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return descrizione;
        }

        /**
         * Ritorna l'elemento dal codice di database
         * <p/>
         *
         * @param codice il codice di database
         *
         * @return il corrispondente elemento della Enum
         */
        public static TipiPasto get(int codice) {
            /* variabili e costanti locali di lavoro */
            TipiPasto[] elementi;
            TipiPasto elemento = null;

            try { // prova ad eseguire il codice
                elementi = TipiPasto.values();
                for (TipiPasto elem : elementi) {
                    if (elem.getCodice() == codice) {
                        elemento = elem;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return elemento;
        }



        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public String toString() {
            return this.getDescrizione();
        }
    }
}// fine della classe