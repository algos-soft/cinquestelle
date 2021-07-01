/**
 * Title:     Fattura
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2004
 */
package it.algos.gestione.fattura;

import it.algos.base.documento.Documento;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Viste;

import java.util.ArrayList;

/**
 * Interfaccia fattura.
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
 * @version 1.0    / 9-feb-2004 ore 7.13.25
 */
public interface FattBase extends Documento {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Fattura";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "fattura";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = NOME_MODULO;

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Fatture";

    /**
     * Codifica del voce della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;


    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     * //Non ce ne sono
     * <p/>
     * /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

        numeroDoc(Documento.Cam.numeroDoc),
        dataDoc(Documento.Cam.dataDoc),
        cliente("link_cliente", "cliente", "cliente", "", true),
        picf("picf", "p.i. / c.f.", "p.i. / c.f.", "", false),
        destinatario("destinatario", "destinatario", "destinatario", "", false),
        destinazione("destinazione", "destinazione", "destinazione", "", false),
        rifNostri("rifnostri", "ns. rif.", "riferimenti nostri", "", false),
        rifCliente("rifcliente", "rif. cliente", "riferimenti cliente", "", false),
        tipodoc("tipodoc", "tipodoc", "tipo documento", "", false),
        estero("estero", "estero", "estero", "", false),
        pagamento("link_pagamento", "pagamento", "pagamento", "", false),
        usaBanca("usabanca", "usa banca", "usa banca", "", false),
        contoBanca("link_contobanca", "banca", "banca", "", false),
        coordBanca("coordbanca", "coordinate bancarie", "coordinate bancarie", "", false),
        pagamentofix("pagamentofix", "pagamento", "pagamento", "", false),
        dataScadenza("data_scadenza", "data scad", "data scadenza", "", true),
        codIva("link_iva", "codIva", "codice iva", "codice iva di default", false),
        percIva("perc_iva", "%iva", "% iva", "percentuale iva", false),
        percSconto("perc_sconto", "%", "sconto", "sconto di default", false),
        applicaRivalsa("applica_rivalsa", "riv", "", "", false),
        applicaRA("applica_ra", "ra", "", "", false),
        percRivalsa("perc_rivalsa", "%", "% riv. inps", "", false),
        percRA("perc_ra", "%", "% ra", "", false),
        righe("righe", "righe", "elementi della fattura", "", false),
        pagato("pagato", "pagato", "pagato", "", true),
        imponibileLordo("imponibile_lordo", "imponibile lordo", "imponibile lordo", "", false),
        importoRivalsa("rivalsa_inps", "riv. inps", "riv. inps", "", false),
        imponibileNetto("imponibile_netto", "imponibile", "imponibile", "", true),
        importoIva("importo_iva", "iva", "iva", "", true),
        totaleLordo("totale_lordo", "totale lordo", "totale lordo", "", false),
        importoRA("importo_ra", "r.a.", "rit. acconto", "", false),
        totaleNetto("totale_netto", "totale netto", "totale netto", "", true),
        confermato(Documento.Cam.confermato),
        dataConferma(Documento.Cam.dataConferma);

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


        /**
         * Costruttore completo con parametri.
         * <p/>
         * Crea un elemento da un elemento di un'altra Enumerazione
         *
         * @param unCampo oggetto di un'altra Enum Campi
         */
        Cam(Campi unCampo) {
            this(unCampo.get(),
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
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     * (ATTENZIONE - il nome della Vista DEVE essere tutto minuscolo)
     */
    public enum Vis implements Viste {

        standard();


        @Override public String toString() {
            return super.toString() + vista;
        }
    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Tipi di fattura disponibili
     */
    public enum TipoDoc {

        fattura("Fattura", 1),
        ricevutaFiscale("Ricevuta fiscale", 2),
        docTrasporto("Documento di trasporto", 3);

        /**
         * voce da utilizzare
         */
        private String titolo;

        /**
         * valore per il database
         */
        private int valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         * @param codice utilizzato nel database
         */
        TipoDoc(String titolo, int codice) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
                this.setValore(codice);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<TipoDoc> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<TipoDoc> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<TipoDoc>();

                /* traverso tutta la collezione */
                for (TipoDoc doc : values()) {
                    lista.add(doc);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public String toString() {
            return this.getTitolo();
        }


        private String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public int getValore() {
            return valore;
        }


        private void setValore(int valore) {
            this.valore = valore;
        }


        /**
         * Ritorna il voce per un dato codice
         * <p/>
         *
         * @param codice da cercare
         *
         * @return il voce dell'elemento con tale codice
         */
        public static String getTitolo(int codice) {
            /* variabili e costanti locali di lavoro */
            String titolo = "";
            int unCodice;

            for (TipoDoc tipo : values()) {
                unCodice = tipo.getValore();
                if (unCodice == codice) {
                    titolo = tipo.getTitolo();
                    break;
                }// fine del blocco if

            }

            /* valore di ritorno */
            return titolo;
        }

    }// fine della classe


}// fine della interfaccia
