/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-feb-2007
 */
package it.algos.base.help;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.help.DefaultHelpModel;
import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.*;
import java.awt.*;

/**
 * Finestra di aiuto.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-feb-2007 ore 7.29.58
 */
public final class HelpFin extends JFrame {

    // The initial width and height of the frame
    private final static int WIDTH = 645;

    private final static int HEIGHT = 495;

    private JHelp help;

    private Help.Tipo tipo;


    /**
     * Costruttore senza parametri.
     */
    public HelpFin() {
        this(Help.Tipo.utente);
    }


    /**
     * Costruttore completo.
     *
     * @param tipo di aiuto (utente/programmatore)
     */
    public HelpFin(Help.Tipo tipo) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setTipo(tipo);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.regolaHelp();
            this.regolaFinestra();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni del visore help.
     */
    private void regolaHelp() {
        /* variabili e costanti locali di lavoro */
        JHelp help;
        HelpSet helpSet = null;
        DefaultHelpModel model;

        try { // prova ad eseguire il codice
            this.setHelp(new JHelp());
            help = this.getHelp();

            switch (this.getTipo()) {
                case utente:
                    helpSet = Help.getHelpSet();
                    break;
                case programmatore:
                    helpSet = Help.getHelpSetProg();
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            if (helpSet != null) {
                //@todo non funziona la compilazione (esterna ad Idea)
//                present = helpSet.getDefaultPresentation();
//                help.setHelpSetPresentation(present);
                //@todo fine del buco di compilazione
                model = new DefaultHelpModel(helpSet);
                help.setModel(model);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni della finestra.
     */
    private void regolaFinestra() {
        /* variabili e costanti locali di lavoro */
        JHelp help;
        String titolo;

        try { // prova ad eseguire il codice
            help = this.getHelp();
            titolo = Help.getHelpSet().getTitle();

            this.setTitle(titolo);
            this.setSize(WIDTH, HEIGHT);
            this.setForeground(Color.black);
            this.setBackground(Color.lightGray);
            this.getContentPane().add(help);    // the JH panel

            /* regola la risposta alla chiusura della finestra (la chiude) */
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            /* impacchetta, centra e rende visibile la finestra */
            Lib.Gui.centraFinestra(this);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private Help.Tipo getTipo() {
        return tipo;
    }


    private void setTipo(Help.Tipo tipo) {
        this.tipo = tipo;
    }


    private JHelp getHelp() {
        return help;
    }


    private void setHelp(JHelp help) {
        this.help = help;
    }
}// fine della classe
