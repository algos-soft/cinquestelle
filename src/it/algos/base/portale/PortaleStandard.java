/**
 * Title:     PortaleStandard
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2006
 */
package it.algos.base.portale;

import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.toolbar.ToolBar;

import javax.swing.*;
import java.awt.*;

/**
 * Portale standard.
 * </p>
 * Implementa un pannello che puo' contenere fino a quattro componenti:
 * - un pannello titoli
 * - un componente principale
 * - una toolbar
 * - un pannello comandi
 * Il layout � BorderLayout.
 * Il componente principale e' nela pannello centrale
 * Gli altri componenti possono essere disposti a piacere nei pannelli laterali.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2006 ore 14.02.18
 */
public class PortaleStandard extends PannelloBase {

    /**
     * usata in fase di sviluppo per vedere l'oggetto facilmente
     */
    protected static final boolean DEBUG = false;

    /**
     * Riferimento al componente principale
     */
    private JComponent compMain;

    /**
     * Riferimento alla toolbar
     */
    private ToolBar toolbar;

    /**
     * Riferimento al pannello titoli
     */
    private Pannello titoli;

    /**
     * Riferimento al pannello comandi
     */
    private Pannello comandi;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param comp il componente principale
     */
    public PortaleStandard(JComponent comp) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCompMain(comp);

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
        try {    // prova ad eseguire il codice

            /* crea e assegna il layout */
            this.setLayout(new BorderLayout());

            /* aggiunge il componente principale al centro */
            this.setComponente(this.getCompMain());

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge il componente principale al centro del portale.
     * <p/>
     * Se esiste gi� un componente principale lo sostituisce.
     *
     * @param comp il componente principale da aggiungere.
     */
    public void setComponente(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        JComponent oldComp;

        try {    // prova ad eseguire il codice

            /* rimuove l'eventuale componente preesistente */
            oldComp = this.getCompMain();
            if (oldComp != null) {
                this.remove(oldComp);
            }// fine del blocco if

            /* registra il nuovo componente e lo aggiunge al centro */
            this.setCompMain(comp);
            this.add(comp);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge il pannello titoli in una posizione data.
     * <p/>
     * Se esiste gi� un pannello titoli lo sostituisce.
     *
     * @param pannello da aggiungere.
     * @param constraint la posizione.
     *
     * @see BorderLayout
     */
    public void setTitolo(Pannello pannello, String constraint) {
        /* variabili e costanti locali di lavoro */
        Pannello oldPan;

        try {    // prova ad eseguire il codice

            /* rimuove l'eventuale componente preesistente */
            oldPan = this.getTitoli();
            if (oldPan != null) {
                this.remove(oldPan.getPanFisso());
            }// fine del blocco if

            /* registra il nuovo componente e lo aggiunge al centro */
            this.setTitoli(pannello);
            this.add(pannello.getPanFisso(), constraint);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge il pannello titoli in alto.
     * <p/>
     *
     * @param pannello da aggiungere.
     */
    public void setTitolo(Pannello pannello) {
        this.setTitolo(pannello, BorderLayout.PAGE_START);
    }


    /**
     * Aggiunge una stringa di voce.
     * <p/>
     *
     * @param stringa da aggiungere.
     */
    public void setTitolo(String stringa) {
        /* variabili e costanti locali di lavoro */
        Pannello panTitoli;

        try { // prova ad eseguire il codice

            /* se non c'e il pannello titoli lo crea adesso in alto */
            panTitoli = this.getTitoli();
            if (panTitoli == null) {
                panTitoli = this.getPanTitoliDefault();
                this.setTitolo(panTitoli);
            }// fine del blocco if

            /* svuota il pannello e vi inserice una JLabel col voce */
            panTitoli.getPanFisso().removeAll();
            panTitoli.add(new JLabel(stringa));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce un pannello Titoli vuoto di default.
     * <p/>
     *
     * @return il pannello titoli vuoto.
     */
    private Pannello getPanTitoliDefault() {
        /* variabili e costanti locali di lavoro */
        Pannello panTitoli = null;

        try { // prova ad eseguire il codice
            panTitoli = new PannelloBase();
            panTitoli.getPanFisso().setLayout(new BorderLayout());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panTitoli;
    }


    /**
     * Aggiunge il pannello comandi in una posizione data.
     * <p/>
     * Se esiste gi� un pannello comandi lo sostituisce.
     *
     * @param pannello il pannello da aggiungere.
     * @param constraint la posizione.
     *
     * @see BorderLayout
     */
    public void setPanComandi(Pannello pannello, String constraint) {
        /* variabili e costanti locali di lavoro */
        Pannello oldPan;

        try {    // prova ad eseguire il codice

            /* rimuove l'eventuale componente preesistente */
            oldPan = this.getComandi();
            if (oldPan != null) {
                this.remove(oldPan.getPanFisso());
            }// fine del blocco if

            /* registra il nuovo componente e lo aggiunge al centro */
            this.setComandi(pannello);
            this.add(pannello.getPanFisso(), constraint);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge il pannello comandi in basso.
     * <p/>
     *
     * @param pannello il pannello da aggiungere.
     */
    public void setPanComandi(Pannello pannello) {
        this.setPanComandi(pannello, BorderLayout.PAGE_END);
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
    }// fine del metodo avvia


    private JComponent getCompMain() {
        return compMain;
    }


    private void setCompMain(JComponent compMain) {
        this.compMain = compMain;
    }


    private ToolBar getToolbar() {
        return toolbar;
    }


    private void setToolbar(ToolBar toolbar) {
        this.toolbar = toolbar;
    }


    private Pannello getTitoli() {
        return titoli;
    }


    private void setTitoli(Pannello titoli) {
        this.titoli = titoli;
    }


    private Pannello getComandi() {
        return comandi;
    }


    private void setComandi(Pannello comandi) {
        this.comandi = comandi;
    }


}// fine della classe
