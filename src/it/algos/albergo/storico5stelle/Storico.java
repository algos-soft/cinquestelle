/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.storico5stelle;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

/**
 * Interfaccia Camera.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per un insieme di
 * interfacce presenti nel package</li>
 * <li> Mantiene la codifica del nome-chiave interno del modulo (usato nel Modello) </li>
 * <li> Mantiene la codifica della tavola di archivio collegata (usato nel Modello) </li>
 * <li> Mantiene la codifica del titolo della finestra (usato nel Navigatore) </li>
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
 * @version 1.0 / 2 feb 2006
 */
public interface Storico extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Storico";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "storico";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Storico";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Storico";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     */
    //Non ce ne sono

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    //Non ce ne sono


    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     * <p/>
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi
     *
     * @see it.algos.base.wrapper.Campi
     *      <p/>
     *      Classe interna Enumerazione.
     *      <p/>
     *      Codifica dei nomi dei campi
     * @see it.algos.base.wrapper.Campi
     */
//    public static final String CAMPO_CAMERA = Generale.CAMPO_SIGLA;

//    public static final String CAMPO_LETTI = "letti";

    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        cliente("linkcliente","cliente","cliente","",true),
        cognome("cognome","","","",false),
        nome("nome","","","",false),
        datanascita("datanascita","data di nascita","data di nascita","",false),
        entrata("entrata","","","",true),
        uscita("uscita","","","",true),
        camera("linkcamera","camera","camera","",true),
        stringacamera("nomecamera","nome camera","","",false),
        bambino("bambino","","","",true),
        pensione("pensione", "pens", "pens", "", true),
        stringapensione("nomepensione", "pens", "pens", "", false),
        ps("ps", "ps", "", "", true),
        cambioEntrata("cambioEntrata", "CE", "cambio entrata", "", true),
        cambioUscita("cambioUscita", "CU", "cambio uscita", "", true),
        azienda("azienda", "", "", "", true);



        /**
         * nome interno del campo usato nel database.
         * <p/>
         * default il nome della Enumeration <br>
         */
        private String nomecampo;

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
         * @param nomecampo interno del campo usato nel database
         * @param titoloColonna titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda legenda del campo nella scheda
         * @param visibileLista flag per la visibilità nella lista
         */
        Cam(String nomecampo,
            String titoloColonna,
            String etichettaScheda,
            String legenda,
            boolean visibileLista) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nomecampo);
                this.setTitoloColonna(titoloColonna);
                this.setEtichettaScheda(etichettaScheda);
                this.setLegenda(legenda);
                this.setVisibileLista(visibileLista);

                /* controllo automatico che ci sia il nome interno */
                if (Lib.Testo.isVuota(nomecampo)) {
                    this.setNome(this.toString());
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return nomecampo;
        }


        public String getNomeCompleto() {
            return NOME_MODULO + "." + nomecampo;
        }


        public String getNome() {
            return nomecampo;
        }


        private void setNome(String nome) {
            this.nomecampo = nome;
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



} // fine della classe