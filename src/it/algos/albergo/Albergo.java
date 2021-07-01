/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2004
 */
package it.algos.albergo;

import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.addebitofisso.AddebitoFissoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.pianodeicontialbergo.conto.AlbConto;
import it.algos.albergo.pianodeicontialbergo.conto.AlbContoModulo;
import it.algos.albergo.pianodeicontialbergo.mastro.AlbMastro;
import it.algos.albergo.pianodeicontialbergo.mastro.AlbMastroModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottoconto;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.Generale;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.gestione.anagrafica.AnagraficaModulo;

/**
 * Interfaccia per il package Albergo.
 * </p>
 * Questa interfaccia: <ul>
 * <li>
 * <li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2004 ore 14.44.30
 */
public interface Albergo extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Albergo";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Albergo by Algos©";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Albergo";

    /**
     * Percorso completo della cartella che contiene il programma
     */
    public static final String PATH_PROGETTO = CostanteModulo.PATH_PROGRAMMI + "albergo/";

    /**
     * Codifica interna dei varii moduli del programma
     * (non sono ne i titoli delle Finestre, ne i titoli dei Menu)
     */
    public static final String MODULO_RISTORANTE = "MenuRistorante";

    public static final String MODULO_CONTO = "Conti";

    public static final String MODULO_ADDEBITO = "Addebiti";

    public static final String MODULO_CLIENTE = "Clienti";

    public static final String MODULO_PRESENZA = "Presenze";

    public static final String MODULO_PRENOTAZIONE = "Prenotazioni";

    public static final String MODULO_CAMERA = "Camere";

    public static final String MODULO_LISTINO = "Listini camere";

    public static final String MODULO_OPERATORE = "Operatori";

    public static final String MODULO_AZIENDA = "Aziende";

    public static final String MODULO_RCP = "Rcp";


    /* chiave della preferenza specifica "codice azienda attiva" */
    public static final String PREFERENZA_AZIENDA = "azienda";

    /* chiavi per le hotkeys */
    public static final int HOTKEY_1 = 1;

    public static final int HOTKEY_2 = 2;

    public static final int HOTKEY_0 = 0;

    public static final int HOTKEY_X = 99;
    public static final int HOTKEY_PROMEMORIA = 77;
    public static final int HOTKEY_DATA = 88;


    /**
     * Classe interna Enumerazione.
     */
    public enum Moduli {

        conto(Conto.NOME_MODULO),
        cliente(ClienteAlbergo.NOME_MODULO),
        albmastro(AlbMastro.NOME_MODULO),
        albconto(AlbConto.NOME_MODULO),
        albsottoconto(AlbSottoconto.NOME_MODULO),
        addebito(Addebito.NOME_MODULO),
        addebitoFisso(AddebitoFisso.NOME_MODULO),
        listinoBase(Listino.NOME_MODULO),
        camera(Camera.NOME_MODULO);

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


        public static ContoModulo Conto() {
            return (ContoModulo)conto.getModulo();
        }


        public static AnagraficaModulo Cliente() {
            return (AnagraficaModulo)cliente.getModulo();
        }


        public static AlbMastroModulo AlbMastro() {
            return (AlbMastroModulo)albmastro.getModulo();
        }


        public static AlbContoModulo AlbConto() {
            return (AlbContoModulo)albconto.getModulo();
        }


        public static AlbSottocontoModulo AlbSottoconto() {
            return (AlbSottocontoModulo)albsottoconto.getModulo();
        }


        public static AddebitoModulo Addebito() {
            return (AddebitoModulo)addebito.getModulo();
        }


        public static AddebitoFissoModulo AddebitoFisso() {
            return (AddebitoFissoModulo)addebitoFisso.getModulo();
        }


        public static ListinoModulo ListinoBase() {
            return (ListinoModulo)listinoBase.getModulo();
        }


        public static CameraModulo Camera() {
            return (CameraModulo)camera.getModulo();
        }


    }// fine della classe

}// fine della interfaccia
