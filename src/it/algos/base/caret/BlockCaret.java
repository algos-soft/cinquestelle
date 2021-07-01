/**
 * Title:     BlockCaret
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      29-mag-2006
 */
package it.algos.base.caret;

import it.algos.base.errore.Errore;

import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Cursore a blocchetto.
 * </p>
 * Visualizza ul blocchetto grande come il carattere invece che il trattino basso.
 * Utilizzabile su qualsiasi JTextComponent
 * tramite JTextComponent.setCaret()
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 23.53
 */
public final class BlockCaret extends DefaultCaret {

    /**
     * larghezza del cursore (mantenuto per la successiva pulizia)
     */
    private int lar;


    /**
     * Disegna il caret
     * <p/>
     *
     * @param g il contesto grafico nel quale disegnare
     */
    public void paint(Graphics g) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp = null;
        String stringa;
        int pos = 0;
        char[] dst;
        char car = ' ';
        Font font;
        FontMetrics metrica;
        Rectangle rett;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* verifico che il caret sia visibile */
            if (continua) {
                continua = this.isVisible();
            }// fine del blocco if

            /* recupero la posizione del caret */
            if (continua) {
                pos = this.getDot();
            }// fine del blocco if

            /* recupero il componente di testo */
            if (continua) {
                comp = this.getComponent();
                continua = comp != null;
            }// fine del blocco if

            /* recupero il carattere alla posizione corrente */
            if (continua) {
                stringa = comp.getText();
                if (stringa.length() > 0) {
                    pos = this.getDot();
                    if (pos < stringa.length()) {
                        dst = new char[1];
                        stringa.getChars(pos, pos + 1, dst, 0);
                        if (dst.length > 0) {
                            car = dst[0];
                        } else {
                            continua = false;
                        }// fine del blocco if-else
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* recupero la larghezza del carattere */
            if (continua) {
                font = comp.getFont();
                metrica = comp.getFontMetrics(font);
                lar = metrica.charWidth(car);
            }// fine del blocco if

            /* disegno il cursore rettangolare */
            if (continua) {
                rett = comp.modelToView(pos);
                g.setColor(comp.getBackground());
                g.setXORMode(comp.getSelectionColor());
                g.fillRect(rett.x, rett.y, lar, rett.height);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * specify the size of the caret for redrawing
     * and do repaint() -- this is called when the
     * caret moves
     */
    protected synchronized void damage(Rectangle r) {

        if (r != null) {
            this.x = r.x;
            this.y = r.y;
            this.width = lar;
            this.height = r.height;
            repaint();
        }// fine del blocco if

    }


}// fine della classe
