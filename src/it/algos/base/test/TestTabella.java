/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.test;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;

/**
 * Interfaccia TestTabella.
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
public interface TestTabella extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "TestTabella";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "prova";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Test";

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Test";

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
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String CAMPO_NUMERO = "numero";

    public static final String CAMPO_NUMERO_DUE = "numero2";

    public static final String CAMPO_IMPORTO = "importo";

    public static final String CAMPO_DATA = "data";

    public static final String CAMPO_DATA_NASCITA = "datanascita";

    public static final String CAMPO_CHECK = "campocheck";

    public static final String CAMPO_RADIO_SINGLE = "radiosingolo";

    public static final String CAMPO_RADIO_GROUP = "radiogruppo";

    public static final String CAMPO_COMBO = "campocombo";

    public static final String CAMPO_COLORI = "colori";

    public static final String CAMPO_NOTE = "note";

    /**
     * codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see it.algos.base.wrapper.EstrattoBase
     */
    public static final String ESTRATTO_kkk = "";


    /**
     * Classe interna Enumerazione.
     */
    public enum Colori {

        rosso("rosso"),
        giallo("giallo"),
        verde("verde"),
        marrone("marrone"),
        nero("nero"),
        bianco("bianco"),
        arancione("arancione"),
        indaco("indaco");

        /**
         * valore del'istanza
         */
        private String valore;


        /**
         * Costruttore completo con parametri.
         *
         * @param valore utilizzato nei popup
         */
        Colori(String valore) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setValore(valore);
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        public String getValore() {
            return valore;
        }


        private void setValore(String valore) {
            this.valore = valore;
        }


        public static String getStringaValori() {
            /* variabili e costanti locali di lavoro */
            String stringa = "";

            try { // prova ad eseguire il codice
                for (Colori colore : Colori.values()) {
                    stringa += "," + colore.getValore();
                } // fine del ciclo for-each

                if (Lib.Testo.isValida(stringa)) {
                    stringa = stringa.substring(1);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return stringa;
        }


    }// fine della classe


} // fine della classe
