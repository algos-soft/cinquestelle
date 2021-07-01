/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-gen-2005
 */
package it.algos.albergo.ristorante;

import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.fisso.Fisso;
import it.algos.albergo.ristorante.fisso.FissoModulo;
import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.menu.recupero.RTOOld;
import it.algos.albergo.ristorante.modifica.Modifica;
import it.algos.albergo.ristorante.modifica.ModificaModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.piatto.PiattoModulo;
import it.algos.albergo.ristorante.righemenuordini.RMO;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenupiatto.RMPModulo;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.ristorante.righetavoloordini.RTO;
import it.algos.albergo.ristorante.sala.Sala;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.albergo.ristorante.tavolo.TavoloModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

/**
 * Interfaccia per il package Ristorante.
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
 * @version 1.0    / 24-gen-2005 ore 18.31.09
 */
public interface Ristorante extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "MenuRistorante";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     * <p/>
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     * <p/>
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
//    public static final String NOME_TAVOLA = "Ristorante";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Ristorante";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Ristorante";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     * <p/>
     * Codifica interna dei varii moduli del programma
     * (non sono ne i titoli delle Finestre, ne i titoli dei Menu)
     * <p/>
     * Codifica interna dei varii moduli del programma
     * (non sono ne i titoli delle Finestre, ne i titoli dei Menu)
     */
//    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica interna dei varii moduli del programma
     * (non sono ne i titoli delle Finestre, ne i titoli dei Menu)
     */
    public static final String MODULO_CATEGORIA = Categoria.NOME_MODULO;

    public static final String MODULO_FISSO = Fisso.NOME_MODULO;

    public static final String MODULO_LINGUA = Lingua.NOME_MODULO;

    public static final String MODULO_MENU = Menu.NOME_MODULO;

    public static final String MODULO_MODIFICA = Modifica.NOME_MODULO;

    public static final String MODULO_PIATTO = Piatto.NOME_MODULO;

    public static final String MODULO_RIGHE_MENU_ORDINI = RMO.NOME_MODULO;

    public static final String MODULO_RIGHE_PIATTO = RMP.NOME_MODULO;

    public static final String MODULO_RIGHE_TAVOLO = RMT.NOME_MODULO;

    public static final String MODULO_RIGHE_ORDINI = RTO.NOME_MODULO;

    public static final String MODULO_SALA = Sala.NOME_MODULO;

    public static final String MODULO_TAVOLO = Tavolo.NOME_MODULO;

    public static final String MODULO_RTO_OLD = RTOOld.NOME_MODULO;

    /**
     * Prefisso del nome del campo del piatto
     */
    public static final String NOME = "nome";

    public static final String SPIEGAZIONE = "descrizione";

    /**
     * Campi per le tipologie di pasti nel menu
     */
    public static final String[] PASTI = {"colazione", "pranzo", "cena"};

    
    /**
     * Codici ad uso interno dei pasti utilizzati
     * (fanno riferimento alla posizione in PASTI[])
     */
    public static final int COLAZIONE = 0;

    public static final int PRANZO = 1;

    public static final int CENA = 2;


    /**
     * Codici per il database dei pasti utilizzati
     */
    public static final int COD_DB_PRANZO = 2;

    public static final int COD_DB_CENA = 3;


    /**
     * categoria di queste preferenze nell'albero XML
     */
    public static final String RIS = "ristorante/";

    /**
     * booleana - codice della categoria contorno
     */
    public static final String CODICE_CONTORNO = RIS + "codice contorno";

    /**
     * booleana - codice della categoria secondi
     */
    public static final String CODICE_SECONDO = RIS + "codice secondo";


    /**
     * Classe interna Enumerazione.
     */
    public enum Moduli {

        piatto(Piatto.NOME_MODULO),
        tavolo(Tavolo.NOME_MODULO),
        fisso(Fisso.NOME_MODULO),
        modifica(Modifica.NOME_MODULO),
        rmp(RMP.NOME_MODULO);

        /**
         * titolo da utilizzare
         */
        private String nomeModulo;


        /**
         * Costruttore completo con parametri.
         *
         * @param nomeModulo nome effettivo del modulo
         */
        Moduli(String nomeModulo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNomeModulo(nomeModulo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private String getNomeModulo() {
            return nomeModulo;
        }


        private void setNomeModulo(String nomeModulo) {
            this.nomeModulo = nomeModulo;
        }


        public Modulo getModulo() {
            /* variabili e costanti locali di lavoro */
            Modulo mod = null;
            String nome;

            try { // prova ad eseguire il codice
                nome = this.getNomeModulo();

                if (Lib.Testo.isValida(nome)) {
                    mod = Progetto.getModulo(nome);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);

            }// fine del blocco try-catch
            return mod;
        }


        public Campo getCampo(String nome) {
            /* variabili e costanti locali di lavoro */
            Campo campo = null;
            Modulo mod;

            try { // prova ad eseguire il codice
                mod = this.getModulo();

                if (mod != null) {
                    campo = mod.getCampo(nome);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);

            }// fine del blocco try-catch
            return campo;
        }


        public static PiattoModulo Piatto() {
            return (PiattoModulo)piatto.getModulo();
        }


        public static TavoloModulo Tavolo() {
            return (TavoloModulo)tavolo.getModulo();
        }


        public static FissoModulo Fisso() {
            return (FissoModulo)fisso.getModulo();
        }


        public static ModificaModulo Modifica() {
            return (ModificaModulo)modifica.getModulo();
        }


        public static RMPModulo Rmp() {
            return (RMPModulo)rmp.getModulo();
        }


    }// fine della classe


}// fine della interfaccia
