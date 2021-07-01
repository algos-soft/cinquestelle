/**
 * Title:     MenuBase
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-mar-2004
 */
package it.algos.base.menu.menu;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.pref.Pref;

import javax.swing.*;
import java.util.HashMap;

/**
 * Menu astratto.
 * </p>
 * Questa classe: <ul>
 * <li> Estende le funzionalit&agrave </li>
 * <li> Mantiene la variabile di istanza <i>azioni</i> usata
 * nelle sottoclassi </li>
 * <li> Mantiene la variabile di istanza <i>azioni</i> usata
 * nelle sottoclassi </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-mar-2004 ore 8.03.54
 */
public abstract class MenuBase extends JMenu implements Menu {

    public enum MenuTipo {

        ARCHIVIO,
        COMPOSIZIONE,
        MODULI,
        TABELLE,
        SERVIZIO,
        SPECIFICO,
        HELP
    }


    /**
     * riferimento alla collezione chiave-valore delle azioni del pannello
     * la finestre fanno riferimento a queste azioni per
     * costruire la propria barra menu
     */
    private HashMap azioni = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuBase() {
        /* rimanda al costruttore di questa classe */
        this(null, "");
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     * @param titolo voce del menu come viene visualizzato nella finestra
     */
    public MenuBase(HashMap azioni, String titolo) {
        /* rimanda al costruttore della superclasse */
        super(titolo);

        /* regola le variabili di istanza coi parametri */
        this.azioni = azioni;

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Aggiunge la riga di menu al menu.
     * <p/>
     * Controlla il flag, per accertarsi che l'azione sia attiva
     * (cioe' funzionante) <br>
     * Regola il tasto acceleratore <br>
     *
     * @param unAzione da aggiungere
     * @param unMenu al quale aggiungere un menu
     *
     * @return vero se l'azione e' stata aggiunta
     */
    protected boolean aggiunge(Azione unAzione, JMenu unMenu) {
        /* variabili e costanti locali di lavoro */
        boolean mostraAzione = false;
        JMenuItem unMenuItem;
        Pref.Utente livelloAzione;
        Pref.Utente livelloUtente;
        int pos;

        try {    // prova ad eseguire il codice

            pos = unMenu.getItemCount();
            mostraAzione = this.add(unAzione, unMenu, pos);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mostraAzione;
    }


    /**
     * Aggiunge la riga di menu al menu, alla posizione.
     * <p/>
     * Controlla il flag, per accertarsi che l'azione sia attiva
     * (cioe' funzionante) <br>
     * Regola il tasto acceleratore <br>
     *
     * @param unAzione da aggiungere
     * @param unMenu al quale aggiungere un menu
     *
     * @return vero se l'azione e' stata aggiunta
     */
    protected boolean add(Azione unAzione, JMenu unMenu, int pos) {
        /* variabili e costanti locali di lavoro */
        boolean mostraAzione = false;
        JMenuItem unMenuItem;
        Pref.Utente livelloAzione;
        Pref.Utente livelloUtente;

        try {    // prova ad eseguire il codice

            /* controllo di congruita */
            if (unAzione == null) {
                return false;
            }// fine del blocco if

            /* Controlla il flag */
            if (Pref.GUI.attive.is()) {
                mostraAzione = unAzione.isAttiva();
            } else {
                mostraAzione = true;
            } /* fine del blocco if/else */

            /* controllo della tipologia di utente abilitato all'azione */
            if (mostraAzione) {
                livelloAzione = unAzione.getUtente();
                if (livelloAzione != null) {
                    mostraAzione = (livelloAzione.ordinal() + 1 >= Pref.Gen.tipoUtente.comboInt());
                }// fine del blocco if
            }// fine del blocco if

            /* prosegue */
            if (mostraAzione) {
                /* costruisce la riga di menu partendo dall'azione */
                unMenuItem = new JMenuItem(unAzione.getAzione());

                /* aggiunge la riga di menu al menu */
                unMenu.insert(unMenuItem, pos);

            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mostraAzione;
    }


    /**
     * Aggiunge la riga di menu al menu.
     * <p/>
     * Controlla il flag, per accertarsi che l'azione sia attiva
     * (cioe' funzionante) <br>
     * Regola il tasto acceleratore <br>
     *
     * @param unaChiaveAzione codice della collezione di azioni
     * @param unMenu al quale aggiungere un menu
     *
     * @return vero se l'azione e' stata aggiunta
     */
    protected boolean aggiunge(String unaChiaveAzione, JMenu unMenu) {
        /* variabili e costanti locali di lavoro */
        boolean mostraAzione = false;
        Azione unAzione;

        try {    // prova ad eseguire il codice
            /* recupera l'azione dalla collezione */
            unAzione = this.getAzione(unaChiaveAzione);

            mostraAzione = this.aggiunge(unAzione, unMenu);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* aggiunge il menu di avviso per l'errore */
            this.add(new JMenuItem(unaChiaveAzione + " non trovata"));
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mostraAzione;
    }


    /**
     * Aggiunge la riga di menu al menu.
     * <p/>
     * Controlla il flag, per accertarsi che l'azione sia attiva
     * (cioe' funzionante) <br>
     * Regola il tasto acceleratore <br>
     *
     * @param unaChiaveAzione codice della collezione di azioni
     *
     * @return vero se l'azione e' stata aggiunta
     */
    protected boolean aggiunge(String unaChiaveAzione) {
        return aggiunge(unaChiaveAzione, this);
    }


    /**
     * Aggiunge la riga di menu al menu.
     * <p/>
     * Controlla il flag, per accertarsi che l'azione sia attiva
     * (cioe' funzionante) <br>
     * Regola il tasto acceleratore <br>
     *
     * @param unAzione da aggiungere
     *
     * @return vero se l'azione e' stata aggiunta
     */
    public boolean aggiunge(Azione unAzione) {
        return aggiunge(unAzione, this);
    }


    /**
     * Aggiunge la riga di menu al menu.
     * <p/>
     * Controlla il flag, per accertarsi che l'azione sia attiva
     * (cioe' funzionante) <br>
     * Regola il tasto acceleratore <br>
     *
     * @param unAzione da aggiungere
     *
     * @return vero se l'azione e' stata aggiunta
     */
    public boolean add(Azione unAzione, int pos) {
        return add(unAzione, this, pos);
    }


    /**
     * Recupera l'azione dalla collezione interna.
     * <p/>
     * Metodo wrapper per evitare il casting di ritorno <br>
     *
     * @param chiave nome chiave per recuperare l'azione
     *
     * @return azione richiesta della collezione interna
     */
    protected Azione getAzione(String chiave) {
        /* variabili e costanti locali di lavoro */
        Azione unAzione = null;
        Object unOggetto;

        try {    // prova ad eseguire il codice
            unOggetto = azioni.get(chiave);
            if (unOggetto != null) {
                if (unOggetto instanceof Azione) {
                    unAzione = (Azione)unOggetto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unAzione;
    }


    /**
     * restituisce una istanza concreta.
     *
     * @return istanza di <code>MenuBase</code>
     */
    public MenuBase getMenu() {
        return this;
    } // fine del metodo

}// fine della classe
