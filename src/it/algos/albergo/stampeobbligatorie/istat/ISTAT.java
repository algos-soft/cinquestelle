package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

/**
 * Interfaccia schede di notifica.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di
 * interfacce presenti nel package</li>
 * <li> Mantiene la codifica del nome-chiave interno del modulo (usato nel Modello) </li>
 * <li> Mantiene la codifica della tavola di archivio collegata (usato nel Modello) </li>
 * <li> Mantiene la codifica del voce della finestra (usato nel Navigatore) </li>
 * <li> Mantiene la codifica del nome del modulo come appare nel Menu Moduli
 * (usato nel Navigatore) </li>
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara (astratti) i metodi  utilizzati nel package che devono
 * essere visti all'esterno </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public interface ISTAT extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "ISTAT";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "istat";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Istat";

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Istat";

    /**
     * Codifica del voce della tabella come appare nella altre Liste
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

        linkTesta("linktesta", "", "", "", false),
        tipoRiga("tiporiga", "nazionalità", "", "", true),
        codResidenza("codresidenza", "residenza", "", "", true),
        numArrivati("numarrivati", "arr", "", "", true),
        numPartiti("numpartiti", "part", "", "", true),
        codArrivati("codArrivati", "", "", "", false),
        codPartiti("codPartiti", "", "", "", false),
        check("check", "ok", "ok", "", true);
        
        /**
         * nome interno del campo usato nel database.
         * <p/>
         * default il nome della Enumeration <br>
         */
        private String nome;

        /**
         * voce della colonna della lista.
         * <p/>
         * default il nome del campo <br>
         */
        private String titoloColonna;

        /**
         * voce della etichetta in scheda.
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
         * @param titoloColonna della lista
         * @param etichettaScheda visibile
         * @param legenda del campo nella scheda
         * @param visibileLista flag per la visibilità nella lista
         */
        Cam(
                String nome,
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
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Costruttore completo con parametri.
         * <p/>
         * Crea un elemento da un elemento di un'altra Enumerazione
         *
         * @param unCampo oggetto di un'altra Enum Campi
         */
        Cam(Campi unCampo) {
            this(
                    unCampo.get(),
                    unCampo.getTitoloColonna(),
                    unCampo.getEtichettaScheda(),
                    unCampo.getLegenda(),
                    unCampo.isVisibileLista());
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
     * Codifica dei tipi di riga per il modulo ISTAT
     * <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    enum TipoRiga implements Campo.ElementiCombo {

        italiano("italiano", 1, "I"),
        straniero("straniero", 2, "S"),
        nonspecificato("non specificato", 3, "X");

        /**
         * nome descrittivo dell'elemento.
         * <p/>
         */
        private String nome;

        /**
         * codice registrato sul database.
         * <p/>
         */
        private int codice;

        /**
         * chiave da usare nelle HashMap di lavoro
         * <p/>
         */
        private String chiaveMappa;



        /**
         * Costruttore completo con parametri.
         *
         * @param nome nome descrittivo dell'elemento
         * @param codice codice registrato sul database
         * @param chiaveMappa chiave da usare nelle HashMap di lavoro
         */
        TipoRiga(String nome, int codice, String chiaveMappa) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
                this.setCodice(codice);
                this.setChiaveMappa(chiaveMappa);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }



        public String toString() {
            return this.getNome();
        }

        /**
         * Ritorna l'elemento della Enum dal codice di database
         * <p/>
         *
         * @param codice il codice di database
         * @return l'elemento corrispondente
         */
        static TipoRiga getTipoRiga(int codice) {
            /* variabili e costanti locali di lavoro */
            TipoRiga[] tipi;
            TipoRiga tipoOut = null;

            try { // prova ad eseguire il codice
                tipi = TipoRiga.values();
                for (TipoRiga tipo : tipi) {
                    if (tipo.getCodice() == codice) {
                        tipoOut = tipo;
                        break;
                    }// fine del blocco if
                }
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return tipoOut;
        }


        /**
         * Ritorna il codice di database.
         * <p/>
         *
         * @return il codice di database
         */
        public int getCodice() {
            return codice;
        }


        /**
         * Assegna il codice di database.
         * <p/>
         *
         * @param codice per il database
         */
        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String get() {
            return nome;
        }


        public String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        String getChiaveMappa() {
            return chiaveMappa;
        }


        private void setChiaveMappa(String chiaveMappa) {
            this.chiaveMappa = chiaveMappa;
        }
    }// fine della classe


} // fine della classe