/**
 * Title:     IndirizzoAlbergo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2007
 */
package it.algos.albergo.clientealbergo.indirizzoalbergo;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Viste;
import it.algos.gestione.indirizzo.Indirizzo;

/**
 * Modulo indirizzi del cliente albergo.
 * <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2007 ore 8.58.32
 */
public interface IndirizzoAlbergo {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "IndirizzoAlbergo";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = Indirizzo.NOME_TAVOLA;

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Indirizzi";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Indirizzi";


    /**
     * Campi del Modello.
     * <p/>
     */
    public enum Cam implements Campi {

        campo1("", "", "", "", false),
        campo2("", "", "", "", false);

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
            return IndirizzoAlbergo.NOME_MODULO + "." + nome;
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
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        nav1(),
        nav2();


        public String get() {
            return super.toString();
        }

    }// fine della classe


    /**
     * Codifica delle Viste del modulo.
     * <p/>
     */
    public enum Vis implements Viste {

        vista1(),
        vista2();


        public String toString() {
            return super.toString() + vista;
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
    public enum Est implements Estratti {

        indirizzoCliente(EstrattoBase.Tipo.pannello),
        indirizzoNotifica(EstrattoBase.Tipo.stringa),
        cittadinanza(EstrattoBase.Tipo.stringa),
        indirizzoRiga(EstrattoBase.Tipo.stringa);

        /**
         * modulo di riferimento
         */
        private static String nomeModulo = NOME_MODULO;

        /**
         * tipo di estratto utilizzato
         */
        private EstrattoBase.Tipo tipoEstratto;


        /**
         * Costruttore completo con parametri.
         *
         * @param tipoEstratto utilizzato
         */
        Est(EstrattoBase.Tipo tipoEstratto) {
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


}// fine della interfaccia
