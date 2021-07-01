/**
 * Title:     LibGui
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      16-ago-2004
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;

import javax.swing.*;
import java.awt.*;

/**
 * Repository di funzionalita' per l'interfaccia. </p> Classe astratta con soli metodi statici <br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 16-ago-2004 ore 10.49.53
 */
public abstract class LibGui {

    /**
     * Centra una finestra nello schermo.
     * <p/>
     * La finestra viene impacchettata <br> La finestra viene centrata <br> La finestra viene resa
     * visibile <br>
     *
     * @param fin finestra da centrare
     * @param finEsterna finestra contenitore esterno (di defalt lo schermo)
     */
    static void centraFinestra(Window fin, Window finEsterna) {

        try { // prova ad eseguire il codice

            /* La finestra viene impacchettata */
            fin.pack();

            /* La finestra viene centrata sullo schermo */
            centraWindow(fin, finEsterna);

            /* La finestra viene resa visibile */
            fin.setVisible(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Centra una finestra nello schermo.
     * <p/>
     * La finestra non viene impacchettata <br> La finestra non viene resa visibile <br>
     *
     * @param fin finestra da centrare
     * @param finEsterna finestra contenitore esterno (di default l'area utilizzabile dello
     * schermo)
     */
    static void centraWindow(Window fin, Window finEsterna) {
        /* variabili e costanti locali di lavoro */
        Dimension dimEsterna;
        Dimension dimFinestra;
        GraphicsEnvironment env;
        Rectangle bounds;
        int xBase = 0;
        int yBase = 0;
        int xFinestra;
        int yFinestra;

        try { // prova ad eseguire il codice

            /* recupera e calcola la dimensione e la posizione del contenitore */
            if (finEsterna == null) {

                // recupera le dimensioni dell'area disponibile dello schermo
                // sul sistema operativo correntemente in uso
                env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                bounds = env.getMaximumWindowBounds();
                dimEsterna = new Dimension(bounds.width, bounds.height);

            } else {
                /* recupera le dimensioni della finestra 'proprietaria' */
                dimEsterna = finEsterna.getSize();

                /* regola la base da cui calcolare la posizione */
                xBase = finEsterna.getX();
                yBase = finEsterna.getY();
            } /* fine del blocco if/else */

            /* recupera la dimensione della finestra */
            dimFinestra = fin.getSize();

            /* regola l'ascissa */
            xFinestra = dimEsterna.width - dimFinestra.width;
            xFinestra /= 2;
            xFinestra += xBase;

            /* regola l'ordinata */
            yFinestra = dimEsterna.height - dimFinestra.height;
            yFinestra /= 2;
            yFinestra += yBase;

            /* sposta la finestra */
            fin.setLocation(xFinestra, yFinestra);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Forza una finestra a starci nell'area disponibile corrente.
     * <p/>
     * Se non ci sta ne riduce le dimensioni.
     *
     * @param window finestra da controllare
     */
    static void fitWindowToScreen(Window window) {
        /* variabili e costanti locali di lavoro */
        Dimension dimMax;
        Dimension dimCurr;
        GraphicsEnvironment env;
        Rectangle bounds;
        int width;
        int heigth;
        int maxWidth;
        int maxHeigth;

        try {    // prova ad eseguire il codice

            /* dimensione massima per una finestra nell'ambiente corrente */
            env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            bounds = env.getMaximumWindowBounds();
            dimMax = new Dimension(bounds.width, bounds.height);
            maxWidth = dimMax.width;
            maxHeigth = dimMax.height;

            /* dimensioni correnti della finestra da controllare */
            dimCurr = window.getPreferredSize();

            if (!Lib.Mat.isContenuta(dimMax, dimCurr)) {
                width = dimCurr.width;
                heigth = dimCurr.height;

                if (width > maxWidth) {
                    width = maxWidth;
                }// fine del blocco if

                if (heigth > maxHeigth) {
                    heigth = maxHeigth;
                }// fine del blocco if

                window.setSize(new Dimension(width, heigth));
                Lib.Gui.centraWindow(window, null);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Forza una finestra alle dimensioni dello schermo intero corrente.
     * <p/>
     * Se non ci sta ne riduce le dimensioni.
     * Se è più piccola la ingrandisce
     *
     * @param window finestra da controllare
     */
    static void fitWindowToFullScreen(Window window) {
        /* variabili e costanti locali di lavoro */
        try {    // prova ad eseguire il codice

            /* dimensione dello schermo corrente */
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            window.setSize(dim);
            window.setLocation(0,0);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }



    /**
     * Centra una finestra nello schermo.
     * <p/>
     * La finestra viene impacchettata <br> La finestra viene centrata <br> La finestra viene resa
     * visibile <br>
     *
     * @param fin finestra da centrare
     */
    static void centraFinestra(Window fin) {
        /* invoca il metodo sovrascritto (quasi) */
        LibGui.centraFinestra(fin, null);
    }


    /**
     * Centra una finestra nello schermo.
     *
     * @param finestra da centrare
     */
    static void centraFinestra(Finestra finestra) {
        /* invoca il metodo sovrascritto (quasi) */
        LibGui.centraFinestra((Window)finestra.getFinestraBase());
    }


    /**
     * Centra una finestra in un'altra finestra. <br>
     *
     * @param fin finestra da centrare
     * @param finEsterna finestra contenitore esterno (di defalt lo schermo)
     */
    static void centraFinestra(Finestra fin, Finestra finEsterna) {
        /* invoca il metodo sovrascritto (quasi) */
        LibGui.centraFinestra((Window)fin.getFinestraBase(), finEsterna.getFinestraBase());
    }


    /**
     * Centra un dialogo nello schermo.
     *
     * @param dialogo da centrare
     */
    static void centraFinestra(JDialog dialogo) {
        /* invoca il metodo sovrascritto (quasi) */
        LibGui.centraFinestra((Window)dialogo);
    }


    /**
     * Controlla se il look è in uso.
     *
     * @param look nome del Look da testare
     *
     * @return vero se il look è in uso
     */
    static boolean usaLook(String look) {
        /* variabili e costanti locali di lavoro */
        boolean usato = false;

        try { // prova ad eseguire il codice
            if (UIManager.getLookAndFeel().getClass().getName().equals(look)) {
                usato = true;
            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return usato;
    }


}// fine della classe
