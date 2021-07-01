/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.camera;

import java.awt.Color;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

/**
 * Interfaccia Camera.
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
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public interface Camera extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Camera";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "camera";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Camere";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Camere";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Colore di default per le celle della barra delle camere nel tableau
     */
	public static Color COLORE_DEFAULT_CELLE = Color.lightGray;


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        linkZona("zona", "zona", "zona", "piano o zona di raggruppamento", true),
        camera(Modello.NOME_CAMPO_SIGLA, "", "", "", true),
        composizione("linkcomposizione", "composizione", "composizione standard", "", true),
        righeCompo("righecompo", "righecompo", "composizioni possibili", "", false),
        escludiplanning("escludiplanning", "escludi dal planning", "escludi dal planning", "", false);

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
         * @param nome            interno del campo usato nel database
         * @param titoloColonna   titolo della colonna della lista
         * @param etichettaScheda titolo dell'etichetta nella scheda
         * @param legenda         legenda del campo nella scheda
         * @param visibileLista   flag per la visibilità nella lista
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
     * Codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see it.algos.base.wrapper.EstrattoBase
     * @see it.algos.base.wrapper.EstrattoBase.Tipo
     * @see it.algos.base.wrapper.Estratti
     */
    public enum Estratto implements Estratti {

        sigla(EstrattoBase.Tipo.stringa),
        letti(EstrattoBase.Tipo.stringa);


        /**
         * tipo di estratto utilizzato
         */
        private EstrattoBase.Tipo tipoEstratto;

        /**
         * modulo di riferimento
         */
        private static String nomeModulo = NOME_MODULO;


        /**
         * Costruttore completo con parametri.
         *
         * @param tipoEstratto utilizzato
         */
        Estratto(EstrattoBase.Tipo tipoEstratto) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTipo(tipoEstratto);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public EstrattoBase.Tipo getTipo() {
            return tipoEstratto;
        }


        private void setTipo(EstrattoBase.Tipo tipoEstratto) {
            this.tipoEstratto = tipoEstratto;
        }


        public String getNomeModulo() {
            return nomeModulo;
        }

    }// fine della classe

} // fine della classe
