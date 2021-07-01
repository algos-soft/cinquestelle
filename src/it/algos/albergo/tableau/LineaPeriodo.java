/**
 * Title:     LineaPeriodo
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      4-mar-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Rappresentazione lineare di un periodo nel grafo periodi.
 * La linea è sempre orizzontale quindi ha una sola ordinata y.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 4-mar-2009 ore 16.36.22
 */
class LineaPeriodo extends Line2D.Double {

    /**
     * Costruttore completo con parametri. <br>
     *
     * @param x1 ascissa estrema sinistra
     * @param x2 ascissa estrema destra
     * @param y estremo destro
     */
    public LineaPeriodo(double x1, double x2, double y) {
        super(x1,y,x2,y);
    }// fine del metodo costruttore completo

    /**
     * Costruttore completo con parametri. <br>
     *
     * @param p1 punto sinistro
     * @param p2 punto destro
     */
    public LineaPeriodo(Point2D p1, Point2D p2) {
        super(p1,p2);
    }// fine del metodo costruttore completo


    /**
     * Ritorna il rettangolo corrispondente a questa linea
     * <p/>
     * @return il rettangolo corrispondente
     */
    public Rectangle2D getRectangle() {
        /* variabili e costanti locali di lavoro */
        Rectangle2D rect=null;

        try { // prova ad eseguire il codice
            double x1 = this.getX1();
            double y1 = this.getY1()-(Tableau.H_CELLE_PERIODO/2);
            double x2 = this.getX2();
            double y2 = y1+Tableau.H_CELLE_PERIODO;
            rect = new Rectangle2D.Double(x1,y1,x2-x1,y2-y1);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return rect;

    }

    /**
     * Ritorna il numero di giorni corrispondente a questa lines
     * <p/>
     * @return il numero di giorni
     */
    public int getQuantiGiorni() {
        /* variabili e costanti locali di lavoro */
        int giorni=0;

        try { // prova ad eseguire il codice
            double x1 = this.getX1();
            double x2 = this.getX2();
            double w = x2-x1;
            giorni = (int)Lib.Mat.arrotonda(w/Tableau.H_MODULE,0);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return giorni;

    }



}// fine della classe
