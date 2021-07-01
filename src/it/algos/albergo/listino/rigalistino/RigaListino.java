/**
 * Title:     RigaListino
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-apr-2007
 */
package it.algos.albergo.listino.rigalistino;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Viste;

/**
 * Righe del Listino Prezzi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 24-apr-2007
 */
public interface RigaListino extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "RigaListino";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "rigalistino";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Righe Listino";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Righe Listino";


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        listino("linklistino", "linklistino", "linklistino", "linklistino", false),
        dataInizio("datainizio", "dal", "dal", "", true),
        dataFine("datafine", "al", "al", "", false),
        prezzo("prezzo", "prezzo", "prezzo", "", false),;

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
         * @param titoloColonna della lista
         * @param etichettaScheda etichetta nella scheda
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
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String get() {
            return nome;
        }


        public String getNomeCompleto() {
            return RigaListino.NOME_MODULO + "." + nome;
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
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     * (ATTENZIONE - il nome della Vista DEVE essere tutto minuscolo)
     */
    public enum Vis implements Viste {

        sigla(),
        descrizione(),
        listino();


        @Override public String toString() {
            return super.toString() + vista;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei set di campi per la scheda.
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    public enum Set {

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei Navigatori di questo modulo
     */
    public enum Nav {

        navinlistino(),;

    }// fine della classe


} // fine della classe
