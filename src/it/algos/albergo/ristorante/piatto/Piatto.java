/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      18-gen-2005
 */
package it.algos.albergo.ristorante.piatto;

import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;

import java.util.ArrayList;

/**
 * Interfaccia per la tabella Piatto.
 * <p/>
 * Costanti pubbliche, codifiche e metodi (astratti) di questo package <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un'interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
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
 * @version 1.0    / 18-gen-2005 ore 11.10.11
 */
public interface Piatto extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Piatto";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "piatto";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Piatti";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Piatti del menu";

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
    public static final String VISTA_NOME = "vistanome";

    public static final String VISTA_CATEGORIA_NOME = "categorianome";

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    public static final String SET_ITALIANO = "setitaliano";

    public static final String SET_TEDESCO = "settedesco";

    public static final String SET_INGLESE = "setinglese";

    public static final String SET_FRANCESE = "setfrancese";

    public static final String SET_RICETTA = "setricetta";

    /**
     * Lingue utilizzate per i nomi dei campi
     * da comporre coi prefissi NOME e SPIEGAZIONE
     */
    public static final String[] CAMPO_LINGUA = {"italiano", "deutsch", "english", "francais"};

    /**
     * etichette dei campi scheda per ogni lingua
     * utilizzate come default se non trova la tavola lingua
     */
    public static final String[] ETICHETTA_LINGUA = {"italiano", "tedesco", "inglese", "francese"};

    /**
     * Codici ad uso interno delle lingue utilizzate
     * (fanno riferimento a LINGUA ed ETICHETTA)
     */
    public static final int ITALIANO = 0;

    public static final int TEDESCO = 1;

    public static final int INGLESE = 2;

    public static final int FRANCESE = 3;

    /**
     * Prefisso dei nomi dei campi - ogni lingua ha entrambi i campi
     */
    public static final String NOME = "nome";

    public static final String SPIEGAZIONE = "descrizione";

    public static final String COPIA = "copia";

    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String LABEL_PIATTO = "nome del piatto";

    public static final String CAMPO_NOME_ITALIANO = NOME + CAMPO_LINGUA[ITALIANO];

    public static final String CAMPO_SPIEGAZIONE_ITALIANO = SPIEGAZIONE + CAMPO_LINGUA[ITALIANO];

    public static final String CAMPO_CATEGORIA = "linkcategoria";

    public static final String CAMPO_CARNE = "carne";

    public static final String CAMPO_INGREDIENTI = "ingredienti";

    public static final String CAMPO_RICETTA = "ricetta";

    public static final String CAMPO_COMANDA = "comanda";

    public static final String CAMPO_OFFERTO = "offerto";

    public static final String CAMPO_ORDINATO = "ordinato";

    public static final String CAMPO_CONGELATO = "congelato";


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Contenuti di piatto disponibili (carne, pesce, verdura...)
     */
    public enum Contenuti {

        carne("carne", 1),
        pesce("pesce", 2),
        altro("altro", 0);

        /**
         * titolo da utilizzare
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
        Contenuti(String titolo, int codice) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
                this.setValore(codice);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public static ArrayList<Contenuti> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Contenuti> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<Contenuti>();

                /* traverso tutta la collezione */
                for (Contenuti doc : values()) {
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
         * Ritorna il titolo per un dato codice
         * <p/>
         *
         * @param codice da cercare
         *
         * @return il titolo dell'elemento con tale codice
         */
        public static String getTitolo(int codice) {
            /* variabili e costanti locali di lavoro */
            String titolo = "";
            int unCodice;

            for (Contenuti tipo : values()) {
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
