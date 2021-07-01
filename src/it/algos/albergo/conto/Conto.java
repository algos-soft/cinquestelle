/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.conto;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.Navigatori;
import it.algos.base.wrapper.Popup;

import java.util.ArrayList;

/**
 * Interfaccia Conto.
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
public interface Conto extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Conto";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "conto";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Conti";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Conti";

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

        sigla(Modello.NOME_CAMPO_SIGLA, "sigla", "sigla del conto", "", true),
        azienda("azienda", "", "", "azienda di competenza", false),
        dataApertura("apertura", "", "", "apertura del conto", true),
        dataChiusura("chiusura", "", "data di chiusura", "", false),
        validoDal("arrivo", "dal", "dal", "inizio validità", false),
        numPersone("numpersone", "persone", "persone", "attualmente in carico", true),
        arrivoCon("arrivoCon", "arrivo con", "arrivo con", "", false),
        validoAl("partenzaprevista", "al", "al", "fine validità", false),
        partEffettiva("partenzaeffettiva", "partenza", "partenza effettiva", "", false),
        camera("camerainiziale", "", "camera", "camera occupata", false),
        pagante("linkpagante", "", "cliente", "", false),
        periodo("linkeriodo", "", "periodo", "", false),
        caparra("caparra", "", "", "importo versato", false),
        ricFiscale("ricevuta", "", "", "", false),
        chiuso("chiuso", "chiuso", "", "", true),
        note(Modello.NOME_CAMPO_NOTE, "", "", "", false),
        documento("linkfattura", "", "", "", false),
        totImporto("totimporto", "importo", "", "", true),
        totSconto("totsconto", "sconto", "", "", true),
        totNetto("totnetto", "netto a pagare", "", "", true),
        totPagato("totpagato", "pagato", "", "", true),
        totNonPagato("totnonpagato", "non pagato", "", "", true),
        totSospeso("totsospeso", "sospeso", "", "", true),
        addebiti("addebiti", "", "Addebiti", "", false),
        addebitifissi("addebitifissi", "", "", "", false),
        pagamenti("pagamenti", "", "Pagamenti", "", false),
        sconti("sconti", "", "Sconti", "", false),
        sospesi("sospesi", "", "Sospesi", "", false),
        presenze("presenze", "", "Presenze", "", false),;

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
     * Codifica delle Viste del modulo.
     */
    public enum Vis {

        partenza(), // vista usata usato nel dialogo di partenze
        storico(); // vista usata usato nello Storico Cliente


        public String get() {
            return toString();
        }
    }// fine della classe


    /**
     * Codifica dei Navigatori del modulo.
     */
    public enum Nav implements Navigatori {

        partenza(), // navigatore usato nel dialogo di partenze
        storico(); // navigatore usato nello Storico Cliente


        public String get() {
            return toString();
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

        popCamera(EstrattoBase.Tipo.matrice),
        popCliente(EstrattoBase.Tipo.matrice),
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


        /**
         * Restituisce un Estratto base.
         */
        public EstrattoBase get() {
            /* variabili e costanti locali di lavoro */
            EstrattoBase estratto = null;
            boolean continua;
            String nomeModulo;
            Modulo modulo = null;
            Modello modello = null;

            try { // prova ad eseguire il codice
                nomeModulo = this.getNomeModulo();
                continua = (Lib.Testo.isValida(nomeModulo));

                if (continua) {
                    modulo = Progetto.getModulo(nomeModulo);
                    continua = (modulo != null);
                }// fine del blocco if

                if (continua) {
                    modello = modulo.getModello();
                    continua = (modello != null);
                }// fine del blocco if

                if (continua) {
                    estratto = modello.getEstratto(this);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return estratto;
        }

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei popup (fltri) del modulo
     */
    public enum Pop implements Popup {

        tipi("Aperti/chiusi"),
        anni("Anni");

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
     * Codifica dei tipi di stampa del conto
     */
    public enum TipiStampa {

        completo("Completo"),
        pensione("Solo pensione"),
        extra("Solo extra");


        /**
         * descrizione del tipo di stampa
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         */
        TipiStampa(String descrizione) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setDescrizione(descrizione);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        /**
         * Ritorna una lista contenente tutti gli elementi della Enumerazione.
         * <p/>
         */
        public static ArrayList<TipiStampa> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<TipiStampa> lista = null;

            try { // prova ad eseguire il codice

                /* crea la lista */
                lista = new ArrayList<TipiStampa>();

                /* spazzola tutta la Enum */
                for (TipiStampa unTipo : values()) {
                    lista.add(unTipo);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Sovrascrive toString per tornare la descrizione.
         * <p/>
         */
        public String toString() {
            return this.getDescrizione();
        }


    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Coifica dei tipi di selezione del conto
     */
    public enum Selezione {

        conto(1, "per conto"),
        camera(2, "per camera"),
        cliente(3, "per cliente");

        /**
         * codice
         */
        private int codice;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param codice dell'elemento
         * @param titolo utilizzato nei popup
         */
        Selezione(int codice, String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setCodice(codice);
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<String> getElenco() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (Selezione tipo : Selezione.values()) {
                    lista.add(tipo.getTitolo());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public static Selezione getTipoCodice(int codice) {
            /* variabili e costanti locali di lavoro */
            Selezione tipo = null;

            try { // prova ad eseguire il codice

                /* traverso tutta la collezione */
                for (Selezione selezione : Selezione.values()) {
                    if (selezione.getCodice() == codice) {
                        tipo = selezione;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return tipo;
        }


        public int getCodice() {
            return codice;
        }


        private void setCodice(int codice) {
            this.codice = codice;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


    }// fine della classe


} // fine della classe
