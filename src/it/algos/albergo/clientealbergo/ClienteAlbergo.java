/**
 * Title:     ClienteAlbergo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.clientealbergo;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Popup;
import it.algos.base.wrapper.Viste;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.ArrayList;

/**
 * Mantiene tutte le costanti pubbliche e le codifiche di questo package.
 * <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Estende l'interfaccia <i>Cliente</i> </li>
 * <li> Mantiene il percorso della cartella </li>
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica degli estratti (per gli altri moduli) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara i metodi astratti utilizzati in tutto il package </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 7.57.03
 */
public interface ClienteAlbergo {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = Anagrafica.NOME_MODULO;

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = Anagrafica.NOME_TAVOLA;

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Clienti";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Clienti";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;


    /**
     * Codifica dei nomi dei Moduli (interni e come appaiono nel menu)
     */
    public static final String MODULO_AUTORITA = "Autorita";

    public static final String MODULO_PARENTE = "Parentela";

    public static final String MODULO_TIPO_DOCUMENTO = "Documento";


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda) <br>
     *
     * @see it.algos.base.wrapper.Campi
     */
    public enum Cam implements Campi {

//        checkCapo("checkcapo", "*", "capogruppo", "", true),
        capogruppo(Modello.NOME_CAMPO_PREFERITO, "", "", "", false),
        linkCapo("linkcapo", "", "", "", false),
        indirizzoInterno("indirizzoInterno", "", "indirizzo", "", false),
        parentela("parentela", "", "", "rispetto al capogruppo", true),

        luogoNato("luogoNato", "", "luogo di nascita", "", false),
        dataNato("dataNato", "", "data di nascita", "", false),
        indiceGiornoNato("indiceGiornoNato", "", "", "", false),
        annoNato("annoNato", "nato", "anno di nascita", "", false),

        dataDoc("dataDoc", "", "data rilascio", "", false),
        tipoDoc("codiceDoc", "", "tipo documento", "", false),
        autoritaDoc("autoritaDoc", "", "rilasciato da", "", false),
        numDoc("numDoc", "", "numero", "", false),
        scadenzaDoc("scadenzaDoc", "", "scadenza", "", false),

        checkPosta("checkPosta", "", "", "", false),
        lingua("lingua", "", "", "", false),
        checkEvidenza("checkEvidenza", "ev", "", "", false),
        checkFamiglia("checkFamiglia", "", "", "", false),
        telUfficio("ufficio", "", "", "", false),

        noteprep("noteprep", "note prep", "note preparazione", "esigenze particolari di preparazione della camera", false),
        notePersonali("notePersonali", "", "note riservate", "", false),
        ultSoggiorno("ultsoggiorno", "ult. sogg.", "ult. soggiorno", "", false);


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
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        gruppo(), // navigatore dei gruppi usato nella scheda cliente
        gruppoStorico(); // navigatore del gruppo usato nello Storico


        public String get() {
            return toString();
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

        standardAlbergo(),
        gruppoParentela(),
        ps(),
        gruppoStorico(); // vista per il navigatore del gruppo usato nello Storico



        @Override
        public String toString() {
            return super.toString() + vista;
        }

        public String get() {
            return toString();
        }

    }// fine della classe


    /**
     * Codifica dei popup (fltri) del modulo.
     */
    public enum Pop implements Popup {

        evidenza("Evidenza"), // clienti in evidenza
        parentela("Parentela"); // rapporto di parentela col capogruppo

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

        pubblicaSicurezza(EstrattoBase.Tipo.stringa),
        ps(EstrattoBase.Tipo.lista),;


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
     * Codifica dei possibili tipi di errore nei dati anagrafici.
     */
    public enum ErrDatiAnag {

        cognomeMancante("Manca il cognome"),
        nomeMancante("Manca il nome"),
        luogoNascitaMancante("Manca il luogo di nascita"),
        dataNascitaMancante("Manca la data di nascita"),
        indirizzoResidenzaMancante("Manca l'indirizzo di residenza"),
        localitaResidenzaMancante("Manca la località di residenza"),
        nazioneResidenzaMancante("Manca la nazione di residenza"),
        tipoDocMancante("Manca il tipo di documento"),
        numeroDocMancante("Manca il numero del documento"),
        dataRilDocDocMancante("Manca la data di rilascio del documento"),
        autoritaRilDocDocMancante("Manca l'autorità di rilascio del documento"),
        docScaduto("Il documento è scaduto"),;

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
        ErrDatiAnag(String titolo) {
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


        /**
         * Ritorna un blocco di testo con stinghe separate da return
         * contenente le descrizione dell'elenco di errori passato
         * <p/>
         *
         * @param errori elenco degli errori
         *
         * @return il blocco di testo descrittivo
         */
        public static String getTesto(ArrayList<ErrDatiAnag> errori) {
            /* variabili e costanti locali di lavoro */
            String testo = "";
            ErrDatiAnag errore;
            String[] stringhe;

            try { // prova ad eseguire il codice
                stringhe = new String[errori.size()];
                for (int k = 0; k < errori.size(); k++) {
                    errore = errori.get(k);
                    stringhe[k] = "- " + errore.get();
                } // fine del ciclo for
                testo = Lib.Testo.concatReturn(stringhe);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return testo;
        }
    }// fine della classe


}// fine della interfaccia
