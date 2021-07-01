/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.utenti;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;

import java.util.ArrayList;

/**
 * Interfaccia Utenti.
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
public interface Utenti extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Utenti";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "utenti";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Utenti";

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Utenti";

    /**
     * Codifica del voce della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     */
    public static final String VISTA_xxx = "";

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    public static final String SET_yyy = "";

    /**
     * Codifica del campo Nome Utente
     */
    public static final String CAMPO_NOME = "nome";

    /**
     * Codifica del campo Gruppo
     */
    public static final String CAMPO_GRUPPO = "linkgruppo";

    /**
     * Codifica del campo Livello
     */
    public static final String CAMPO_LIVELLO = "livello";

    /**
     * Codifica del campo Utente Abilitato
     */
    public static final String CAMPO_ABILITATO = "abilitato";


    /**
     * codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see it.algos.base.wrapper.EstrattoBase
     */
    public static final String ESTRATTO_kkk = "";


    /**
     * codifica del navigatore slave inserito nel navigatore NAV_UG di Gruppi
     */
    public static final String NAV_UG_SLAVE = "nav_ug_slave";


    /**
     * Codifica delle azioni specifiche di questo package
     */
    public static final String AZIONE_yyy = "";


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Tipi di fattura disponibili
     */
    public enum TipoUte {

        prog("Programmatore", "Prog", 1),
        admin("Amministratore", "Admin", 2),
        utente("Utente", "User", 3),
        ospite("Ospite", "Host", 4);

        /**
         * voce da utilizzare
         */
        private String titolo;

        /**
         * sigla di default
         */
        private String sigla;

        /**
         * livello @todo da eliminare perché le Enumeration sono ordinate
         *
         * @deprecated
         */
        private int livello;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo del tipo di utente
         * @param sigla di default
         * @param livello di utilizzo del programma
         */
        TipoUte(String titolo, String sigla, int livello) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
                this.setSigla(sigla);
                this.setLivello(livello);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Elenco di tutti i titoli.
         * <p/>
         *
         * @return lista dei titoli
         */
        public static ArrayList<String> getTitoli() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try {    // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (TipoUte tipo : TipoUte.values()) {
                    lista.add(tipo.getTitolo());
                } // fine del ciclo for-each

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<String> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (TipoUte tipo : TipoUte.values()) {
                    lista.add(tipo.getSigla());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Restituisce il tipo dal livello numerico.
         * <p/>
         *
         * @param livello numerico registrato nel database
         *
         * @return oggetto della Enumeration
         */
        public static TipoUte getTipo(int livello) {
            /* variabili e costanti locali di lavoro */
            TipoUte tipoUtente = null;

            try {    // prova ad eseguire il codice

                /* traverso tutta la collezione */
                for (TipoUte tipo : TipoUte.values()) {
                    if (tipo.ordinal() == livello) {
                        tipoUtente = tipo;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return tipoUtente;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        public int getLivello() {
            return livello;
        }


        private void setLivello(int livello) {
            this.livello = livello;
        }
    }// fine della classe

} // fine della classe
