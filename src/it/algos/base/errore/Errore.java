/**
 * Title:        Errore.java
 * Package:      it.algos.base.errore
 * Description:
 * Copyright:    Copyright (c) 2002-2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 14.08
 */
package it.algos.base.errore;

import it.algos.base.costante.CostanteBase;
import it.algos.base.costante.CostanteTesto;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioErrore;
import it.algos.base.wrapper.DueStringhe;

import javax.swing.*;

/**
 * Gestisce i messaggi di errore.
 * </p>
 * Questa classe gestisce i messaggi di errore: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 14.08
 */
public class Errore implements CostanteBase {

    /**
     * voce della finestra di dialogo
     */
    private static final String TITOLO = "Messaggio di errore";

    /**
     * Tipologia di azione in caso di errore
     */
    private static TipoAzione tipoAzione = null;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * (solo per compilazione in sviluppo)
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public Errore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     * ricava automaticamente il nome della classe e del metodo chiamante
     *
     * @param unErrore informazioni di errore passate dal sistema
     */
    public Errore(Exception unErrore) {
        /* rimanda al costruttore della superclasse */
        super();
        String classe = "";
        String metodo = "";
        DueStringhe nomeClasseMetodo;

        /* recupera il nome della classe e del metodo chiamante
         * usa il parametro 2 per risalire nello stack:
         * Classe chiamante (2) - Classe Errore (1) */
        nomeClasseMetodo = Libreria.nomeClasseMetodoChiamante(2);

        /* regola le variabili di istanza coi parametri */
        if (nomeClasseMetodo != null) {
            classe = nomeClasseMetodo.getPrima();
            metodo = nomeClasseMetodo.getSeconda();
        }// fine del blocco if

        try { // prova ad eseguire il codice
            this.iniziaErroreMetodo(classe, metodo, "");
        } catch (Exception errore) { // intercetta l'errore
            new MessaggioErrore(CostanteTesto.ERRORE_INIZIA, this);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo
     * ricava automaticamente il nome della classe e del metodo chiamante
     *
     * @param unErrore informazioni di errore, passate dal sistema
     * @param unDettaglioErrore informazioni di ulteriore dettaglio
     * dell'errore, specificate dal programmatore
     */
    public Errore(Exception unErrore, String unDettaglioErrore) {
        /* rimanda al costruttore della superclasse */
        super();
        String classe = "";
        String metodo = "";
        DueStringhe nomeClasseMetodo;

        /* recupera il nome della classe e del metodo chiamante
         * usa il parametro 2 per risalire nello stack:
         * Classe chiamante (2) - Classe Errore (1) */
        nomeClasseMetodo = Libreria.nomeClasseMetodoChiamante(2);

        /* regola le variabili di istanza coi parametri */
        if (nomeClasseMetodo != null) {
            classe = nomeClasseMetodo.getPrima();
            metodo = nomeClasseMetodo.getSeconda();
        }// fine del blocco if

        try { // prova ad eseguire il codice
            this.iniziaErroreMetodo(classe, metodo, unDettaglioErrore);
        } catch (Exception errore) { // intercetta l'errore
            new MessaggioErrore(CostanteTesto.ERRORE_INIZIA, this);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */

    /**
     * .
     * <p/>
     */
    static {
        /* tipologia di azione in caso di errore */
        Errore.setTipoAzione(TipoAzione.avviso);
    }


    /**
     * Mostra il messaggio
     */
    private void iniziaErroreMetodo(String classe, String metodo, String dettaglio) throws
            Exception {
        /* variabile locale */
        String unTesto = "";

        /* costruisce il testo */
        unTesto += "Classe:\t" + classe;
        unTesto += "\nMetodo:\t" + metodo;

        // Aggiunge ulteriore dettaglio errore se presente
        if (Lib.Testo.isValida(dettaglio)) {
            unTesto += "\nDettaglio:\t" + dettaglio;
        } /* fine del blocco if */

        /* bottone in basso */
        String[] valori = {"continua"};

        /** mostra il messaggio */
        JOptionPane.showOptionDialog(null,
                unTesto,
                TITOLO,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                valori,
                valori[0]);
    } /* fine del metodo iniziaErroreMetodo */


    /**
     * Mostra un messaggio di errore.
     *
     * @param unTesto da mostrare
     */
    public static void messaggio(String unTesto) {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            /* bottone in basso */
            String[] valori = {"continua"};

            /* mostra il messaggio */
            JOptionPane.showOptionDialog(null,
                    unTesto,
                    TITOLO,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    valori,
                    valori[0]);

        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch
    }


    /**
     * Invia un messaggio per e-mail.
     *
     * @param unTesto da inviare
     */
    public static void posta(String unTesto) {
        /* variabili e costanti locali di lavoro */
        boolean spedita;
        String oggetto;

        try { // prova ad eseguire il codice
            oggetto = "pippoz";
            spedita = Lib.Web.postMail(oggetto, unTesto);
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

    }


    /**
     * Gestisce l'errore.
     *
     * @param unErrore creato nel metodo chiamante
     * @param dettaglio informazioni di ulteriore dettaglio
     * dell'errore, specificate dal programmatore
     */
    public static void crea(Exception unErrore, String dettaglio) {
        /* variabili e costanti locali di lavoro */
        DueStringhe nomeClasseMetodo;
        String unTesto = "";
        String classe = "";
        String metodo = "";
        Exception errore;

        /* recupera il nome della classe e del metodo chiamante
         * usa il parametro 3 per risalire nello stack:
         * Classe chiamante (3) - Classe Errore (2) */
        nomeClasseMetodo = Libreria.nomeClasseMetodoChiamante(3);

        /* regola le variabili di istanza coi parametri */
        if (nomeClasseMetodo != null) {
            classe = nomeClasseMetodo.getPrima();
            metodo = nomeClasseMetodo.getSeconda();
        }// fine del blocco if
        errore = unErrore;

        /* costruisce il testo */
        unTesto += "Classe:\t" + classe;
        unTesto += "\nMetodo:\t" + metodo;
        unTesto += "\nErrore:\t" + errore.toString();
        if (Lib.Testo.isValida(dettaglio)) {
            unTesto += "\nDescrizione:\t" + dettaglio;
        }// fine del blocco if

        switch (getTipoAzione()) {
            case nulla:
                break;
            case avviso:
                Errore.messaggio(unTesto);
                break;
            case log:

                break;
            case posta:
                Errore.posta(unTesto);
                break;
            case logPosta:

                break;
            case stop:

                break;
            default: // caso non definito
                break;
        } // fine del blocco switch

    }


    /**
     * Gestisce l'errore.
     *
     * @param unErrore creato nel metodo chiamante
     */
    public static void crea(Exception unErrore) {
        Errore.crea(unErrore, "");
    }


    private static TipoAzione getTipoAzione() {
        return tipoAzione;
    }


    public static void setTipoAzione(TipoAzione tipoAzione) {
        Errore.tipoAzione = tipoAzione;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum TipoAzione {

        nulla("Non fa nulla"),
        avviso("Dialogo di avviso"),
        log("Registra su file"),
        posta("Invia un e-mail"),
        logPosta("Log più posta"),
        stop("Esce dal programma"),;

        /**
         * titolo da utilizzare
         */
        private String titolo;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         */
        TipoAzione(String titolo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }

    }// fine della classe

}// fine della classe