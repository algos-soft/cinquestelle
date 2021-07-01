/**
 * Title:        BottoneBase.java
 * Package:      it.algos.base.componente.bottone
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 31 ottobre 2003 alle 16.39
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------

package it.algos.base.componente.bottone;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Gestire un bottone <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  31 ottobre 2003 ore 16.39
 */
public class BottoneBase extends JButton implements Bottone {

    /**
     * larghezza standard del bottone
     */
    private static final int LARGHEZZA = 120;

    /**
     * altezza standard del bottone
     */
    private static final int ALTEZZA = 30;

    /**
     * colore standard del testo
     */
    private static final Color COLORE = new Color(0, 0, 128);

    /**
     * carattere standard del testo
     */
    private static final String CARATTERE = "Arial";

    /**
     * stile standard del testo
     */
    private static final int STILE = Font.BOLD;

    /**
     * dimensione standard del testo
     */
    private static final int CORPO = 12;

    /**
     * font standard del testo (carattere + stile + dimensione)
     */
    private static final Font FONT = new Font(CARATTERE, STILE, CORPO);


    /**
     * Costruttore base senza parametri
     * <p/>
     */
    public BottoneBase() {
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


    /**
     * Creates a button where properties are taken from the Action supplied
     * <p/>
     *
     * @param a azione con la quale costruire il bottone
     */
    public BottoneBase(Action a) {
        /** rimanda al costruttore della superclasse */
        super(a);
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Creates a button with an icon.
     * <p/>
     *
     * @param icon icona per il bottone
     */
    public BottoneBase(Icon icon) {
        /** rimanda al costruttore della superclasse */
        super(icon);
        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Creates a button with text.
     * <p/>
     *
     * @param text testo per il bottone
     */
    public BottoneBase(String text) {
        /** rimanda al costruttore della superclasse */
        super(text);
        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Creates a button with initial text and an icon.
     * <p/>
     *
     * @param text testo per il bottone
     * @param icon icona per il bottone
     */
    public BottoneBase(String text, Icon icon) {
        /** rimanda al costruttore della superclasse */
        super(text, icon);
        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.regolaDefault();
    } /* fine del metodo inizia */


    /**
     * Regolazione delle variabili coi valori di default <br>
     */
    private void regolaDefault() {

//        /** dimensiona il bottone */
//        super.setSize(LARGHEZZA, ALTEZZA);
//
//        /** colore del testo */
//        this.setForeground(COLORE);
//
//        /** font del testo */
//        this.setFont(FONT);

        /* opacità di default */
        this.setOpaque(false);

    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setAction(Azione unAzione) {
        try { // prova ad eseguire il codice
            if (unAzione != null) {
                if (unAzione.getAzione() != null) {
                    super.setAction(unAzione.getAzione());
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setLarghezza(int larghezza) {
        super.setSize(larghezza, super.getHeight());
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setSize(int larghezza, int altezza) {
        super.setSize(larghezza, altezza);
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setLocation(int ascissa, int ordinata) {
        super.setLocation(ascissa, ordinata);
    } /* fine del metodo setter */


    /**
     * Adds an <code>ActionListener</code> to the button.
     *
     * @param l the <code>ActionListener</code> to be added
     */
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public BottoneBase getBottone() {
        return this;
    } /* fine del metodo getter */

}// fine della classe it.algos.base.componente.bottone.BottoneBase.java


