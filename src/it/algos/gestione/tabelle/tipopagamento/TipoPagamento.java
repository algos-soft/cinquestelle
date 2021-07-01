/**
 * Title:     Pagamento
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2004
 */
package it.algos.gestione.tabelle.tipopagamento;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

/**
 * Interfaccia Tipo di Pagamento.
 * </p>
 * es. Ricevuta bancaria, bonifico, rimessa diretta etc..
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2004 ore 8.08.42
 */
public interface TipoPagamento extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "tipopagamento";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "tipopagamento";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Codici pagamento";

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Pagamenti";

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

        sigla(ModelloAlgos.NOME_CAMPO_SIGLA, "", "", "", true),
        descrizione(ModelloAlgos.NOME_CAMPO_DESCRIZIONE, "", "", "", true),
        scadGiorni("scadgiorni", "gg", "giorni", "scadenza in giorni", true),
        fineMese("finemese", "f.m.", "fine mese", "sposta scadenza a fine mese", true),
        usaBanca("usabanca", "b.", "utilizza banca", "pagamento tramite banca", true),
        qualeBanca("qualebanca", "quale banca", "quale banca", "banca da utilizzare", true);

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
         * @param nome del campo
         * @param titoloColonna visibile in lista
         * @param etichettaScheda visibile in scheda
         * @param legenda visibile in scheda
         * @param visibileLista flag
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
    public enum Est implements Estratti {

        descrizione(EstrattoBase.Tipo.stringa);

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


    /**
     * Classe interna Enumerazione.
     */
    public enum Setup {

        cash("cash", "contanti", 0, false, false, 0),
        gg30("30gg", "30 giorni", 30, false, false, 0),
        gg30fm("30gg", "30 giorni f.m.", 30, true, false, 0),
        rd("rm", "rimessa diretta", 0, false, false, 0),
        bonifico("bonifico", "bonifico bancario", 0, false, true, 1);

        private String sigla;

        private String descrizione;

        private int giorni;

        private boolean fineMese;

        private boolean banca;

        private int qualeBanca;


        /**
         * Costruttore completo con parametri.
         *
         * @param sigla breve del pagamento
         * @param descrizione completa del pagamento
         * @param giorni di decorrenza
         * @param fineMese slittamento a fine mese
         * @param banca utilizzo di una banca
         * @param qualeBanca nostra o del cliente
         */
        Setup(String sigla,
              String descrizione,
              int giorni,
              boolean fineMese,
              boolean banca,
              int qualeBanca) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setSigla(sigla);
                this.setDescrizione(descrizione);
                this.setGiorni(giorni);
                this.setFineMese(fineMese);
                this.setBanca(banca);
                this.setQualeBanca(qualeBanca);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        public int getGiorni() {
            return giorni;
        }


        private void setGiorni(int giorni) {
            this.giorni = giorni;
        }


        public boolean isFineMese() {
            return fineMese;
        }


        private void setFineMese(boolean fineMese) {
            this.fineMese = fineMese;
        }


        public boolean isBanca() {
            return banca;
        }


        private void setBanca(boolean banca) {
            this.banca = banca;
        }


        public int getQualeBanca() {
            return qualeBanca;
        }


        public void setQualeBanca(int qualeBanca) {
            this.qualeBanca = qualeBanca;
        }
    }// fine della classe


}// fine della interfaccia
