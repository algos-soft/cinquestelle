/**
 * Title:     CompCamera
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * JPanel con fondo sfumato ed eventuale sfondo arrotondato
 * <p>
 * L'arrotondamento dello sfondo si controlla con setRoundRadius()
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2009 ore 15.09.49
 */
class PanGradientRounded extends JPanel {

    /* colore iniziale */
    private Color color1;

    /* colore finale */
    private Color color2;

    /* orientamento orizzontale o verticale */
    private int orientamento;

    /* raggio dei rounded corners (0 = no roundded corners) */
    private int roundRadius=0;

    /* valori di default della classe */
    private static final Color C1_DEFAULT=Color.lightGray;
    private static final Color C2_DEFAULT=Color.gray;
    private static final int ORIENT_DEFAULT=SwingConstants.HORIZONTAL;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param c1 colore iniziale
     * @param c2 colore finale
     * @param orientamento della sfumatura
     * (SwingConstants.HORIZONTAL o SwingConstants.VERTICAL)
     */
    public PanGradientRounded(Color c1, Color c2, int orientamento) {
        super();
        this.setOpaque(true);
        this.setLayout(new BorderLayout());
        this.setColor1(c1);
        this.setColor2(c2);
        this.setOrientamento(orientamento);
    }


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param c1 colore iniziale
     * @param c2 colore finale
     * (SwingConstants.HORIZONTAL o SwingConstants.VERTICAL)
     */
    public PanGradientRounded(Color c1, Color c2) {
        this(c1,c2,ORIENT_DEFAULT);
    }

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param c1 colore iniziale
     * @param orientamento della sfumatura
     * (SwingConstants.HORIZONTAL o SwingConstants.VERTICAL)
     */
    public PanGradientRounded(Color c1, int orientamento) {
        this(c1,c1.darker(),orientamento);
    }

    
    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param orientamento della sfumatura
     * (SwingConstants.HORIZONTAL o SwingConstants.VERTICAL)
     */
    public PanGradientRounded(int orientamento) {
        this(C1_DEFAULT, C2_DEFAULT, orientamento);
    }


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public PanGradientRounded() {
        this(ORIENT_DEFAULT);
    }


    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        Graphics2D g2d = (Graphics2D)g;

        int w = getWidth();
        int h = getHeight();
        int o = this.getOrientamento();
        GradientPaint gp;

        if (o == SwingConstants.VERTICAL) {
            gp = new GradientPaint(
                    0, 0, getColor1(),
                    0, h, getColor2());
        } else {
            gp = new GradientPaint(
                    0, 0, getColor1(),
                    w, 0, getColor2());
        }// fine del blocco if-else
        g2d.setPaint(gp);

        /**
         * se c'è un raggio usa fillRoundRect, se no usa fillRect
         * (sono leggerissimamaente diversi!)
         */
        int rad = this.getRoundRadius();
        if (rad!=0) {
            g2d.fillRoundRect(0, 0, w, h,rad,rad);
        } else {
            g2d.fillRect(0, 0, w, h);
        }// fine del blocco if-else

        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }


    /**
     * Assegna un colore alla sfulatura.
     * <p/>
     * @param color colore da assegnare
     * lo usa come primo colore e il secondo lo fa
     * automaticamente un po' più scuro
     */
    public void setColor(Color color) {
        try {    // prova ad eseguire il codice
            this.setColor1(color);
            this.setColor2(color.darker());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }





    private Color getColor1() {
        return color1;
    }


    public void setColor1(Color color1) {
        this.color1 = color1;
    }


    private Color getColor2() {
        return color2;
    }


    private void setColor2(Color color2) {
        this.color2 = color2;
    }


    private int getOrientamento() {
        return orientamento;
    }


    public void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


    private int getRoundRadius() {
        return roundRadius;
    }


    public void setRoundRadius(int roundRadius) {
        this.roundRadius = roundRadius;
    }
}// fine della classe