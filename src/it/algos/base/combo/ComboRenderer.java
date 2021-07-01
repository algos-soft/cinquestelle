/**
 * Title:        ComboRenderer.java
 * Package:      it.algos.base.combo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 17 novembre 2003 alle 16.33
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.combo;

import it.algos.base.campo.elemento.EVuoto;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.costante.CostanteFont;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibFont;
import it.algos.base.messaggio.MessaggioAvviso;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - ... <br>
 * B - ... <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  17 novembre 2003 ore 16.33
 */
public final class ComboRenderer extends JLabel implements ListCellRenderer, Serializable {

    /* caratteri da inserire a sinistra per avere distanza dal bordo */
    private static final String ZERO = ""; // nessuno

    private static final String UNO = " "; // uno spazio vuoto

    private Component componente = null;

    private JLabel label = null;

    private Font fontNormale = null;

    private Font fontSpeciale = null;

    /* caratteri da inserire a sinistra per avere distanza dal bordo */
    private String bordo = "";


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public ComboRenderer() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.fontNormale = CostanteFont.FONT_COMBO;
        this.fontSpeciale = CostanteFont.FONT_CAMPI_EDIT_BOLD_SCHEDA;

        this.componente = new TextField();

        this.label = this.crealabel();
        this.label.setFont(fontNormale);
        this.label.setOpaque(true);
    } /* fine del metodo inizia */


    /**
     * ...
     */
    private JLabel crealabel() {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;

        try { // prova ad eseguire il codice
            label = new JLabel(EVuoto.TESTO_LUNGO, SwingConstants.LEFT);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return label;
    }


    /**
     * ...
     */
    public Component getListCellRendererComponent(JList unaLista,
                                                  Object unValore,
                                                  int unIndice,
                                                  boolean isSelezionato,
                                                  boolean laCellaHaIlFuoco) {

        /** variabili e costanti locali di lavoro */
        ComboData unDato = null;
        Elemento elemento;
        boolean continua;
        int hRiga;
        Font unFont;
        double fattoreAria = .4d;
        int hReale;
        int lar;
        Dimension dim;

        try {    // prova ad eseguire il codice

            if (unValore == null) {
                continua = false;
                this.label.setText("");
                this.componente = this.label;
            } else {
                continua = true;
            }// fine del blocco if-else

            if (continua) {

                /* Se il parametro e' ok, effettua il casting, altrimenti avvisa */
                if (unValore instanceof ComboData) {
                    unDato = (ComboData)unValore;
                } else {
                    new MessaggioAvviso("Qualcosa non funziona");
                } /* fine del blocco if/else */

                /* pulisce la label da una eventuale precedente icona */
                this.label.setIcon(null);

                /* la riga del campo edit, oppure la riga del popup */
                if (-1 == unIndice) {

                    /* aggiunge un piccolo bordo a sinistra, ma solo per il campo edit */
                    bordo = ZERO;

                    /* riga speciale */
                    if (unDato.isElementoSpeciale()) {
                        /* regola l'icona della label interna di questa classe */
                        this.label.setIcon(unDato.getIcona());

                        /* regola il testo della label interna di questa classe */
                        this.label.setText(unDato.toString());

                        /* restituisce come componente al popup, la label interna */
                        this.componente = this.label;
                    } /* fine del blocco if */

                } else {

                    /* aggiunge un piccolo bordo a sinistra, ma solo per il popup */
                    bordo = UNO;

                    /* riga speciale */
                    if (unDato.isElementoSpeciale()) {
                        /* restituisce direttamente il componente speciale, che mantiene
                          * tutte le sue caratteristiche */
                        elemento = (Elemento)unDato.getValore();
                        this.componente = elemento.getComponente();
                    } /* fine del blocco if */

                } /* fine del blocco if/else */

                /* riga normale */
                if (unDato.isElementoSpeciale() == false) {
                    /* regola il testo della label interna di questa classe */
                    this.label.setText(bordo + unDato.toString());

                    /* restituisce come componente al popup, la label interna */
                    this.componente = this.label;
                } /* fine del blocco if */

                if (unDato.isSeparatore() == false) {
                    /* determina l'altezza della riga in funzione del font utilizzato */
                    unFont = this.componente.getFont();
                    hRiga = LibFont.getAltezzaFont(unFont);

                    /* determina l'altezza reale aumentando l'altezza
                     * teorica di un po' per lasciare aria
                     * l'aumento e' proporzionale all'altezza della riga */
                    hReale = hRiga + (int)(hRiga * fattoreAria);

                    /* assegna la nuova altezza al componente */
                    lar = this.componente.getPreferredSize().width;
                    dim = new Dimension(lar, hReale);
                    ((JComponent)this.componente).setPreferredSize(dim);

                    if (isSelezionato) {
                        this.componente.setBackground(unaLista.getSelectionBackground());
                    } else {
                        this.componente.setBackground(unaLista.getBackground());
                    } /* fine del blocco if/else */
                } /* fine del blocco if */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return this.componente;

    } /* fine del metodo */

}// fine della classe