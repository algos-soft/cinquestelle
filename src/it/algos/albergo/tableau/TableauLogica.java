/**
 * Title:     SviluppoLogica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      11-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.JGraph;
import org.jgraph.graph.GraphConstants;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;

/**
 * Business Logic della funzionalità Tableau Prenotazioni
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-feb-2009 ore 17.04.29
 */
class TableauLogica {

    /**
     * Determina se una cella è stata spostata o ridimensionata
     * <p/>
     *
     * @param lineAnte linea prima dello spostamento
     * @param linePost linea dopo lo spostamento
     *
     * @return true se è stata spostata, false se è stata ridimansionata
     */
    static boolean isSpostata(Line2D lineAnte, Line2D linePost) {
        /* variabili e costanti locali di lavoro */
        boolean spostata = false;

        try { // prova ad eseguire il codice

            if ((lineAnte != null) && (linePost != null)) {
                int xAnte1 = (int)lineAnte.getX1();
                int xAnte2 = (int)lineAnte.getX2();

                int xPost1 = (int)linePost.getX1();
                int xPost2 = (int)linePost.getX2();

                /* se la lunghezza è invariata la cella è stata spostata */
                int lenAnte = xAnte2 - xAnte1;
                int lenPost = xPost2 - xPost1;
                if (lenAnte == lenPost) {
                    spostata = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spostata;

    }


    /**
     * Determina quale dei due estremi di una cella è stato ridimensinato
     * <p/>
     *
     * @param lineAnte linea prima dello spostamento
     * @param linePost linea dopo lo spostamento
     *
     * @return false se è stato ridimensionato l'estremo sinistro, true quello destro
     */
    static boolean qualeEstremoRidimensionato(Line2D lineAnte, Line2D linePost) {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;

        try { // prova ad eseguire il codice

            if ((lineAnte != null) && (linePost != null)) {
                int xAnte1 = (int)lineAnte.getX1();
                int xPost1 = (int)linePost.getX1();

                /**
                 * se la x di sinistra è cambiata, è stato ridimensionato
                 * a sinistra, se no è stato ridimensionato a destra
                 */
                if (xAnte1 != xPost1) {
                    flag = false;
                } else {
                    flag = true;
                }// fine del blocco if-else
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return flag;

    }


    /**
     * Crea una linea mediana di cella dai bounds di una cella
     * <p/>
     *
     * @param bounds i bounds della cella
     *
     * @return la linea mediana di cella
     */
    static LineaPeriodo getLineaCella(Rectangle2D bounds) {
        /* variabili e costanti locali di lavoro */
        LineaPeriodo linea = null;

        try { // prova ad eseguire il codice
            double x1 = bounds.getMinX();
            double y1 = bounds.getMinY();
            double x2 = bounds.getMaxX();
            double y2 = bounds.getMaxY();
            double yHalf = y1+((y2-y1)/2);
            linea = new LineaPeriodo(x1, x2, yHalf);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return linea;
    }

    /**
     * Recupera i bounds di una cella
     * dalla linea di cella.
     * <p/>
     *
     * @param lineaCella la linea di cella
     *
     * @return i bounds della cella
     */
    static Rectangle2D getBoundsCella(LineaPeriodo lineaCella) {
        /* variabili e costanti locali di lavoro */
        Rectangle2D bounds = null;
        int hCell = Tableau.H_CELLE_PERIODO;

        try { // prova ad eseguire il codice
            double x1 = lineaCella.getX1();
            double x2 = lineaCella.getX2();
            double yMedium = lineaCella.getY1();
            double y1 = yMedium-(hCell/2);
            double y2 = y1+ hCell;
            bounds = new Rectangle2D.Double(x1,y1,x2-x1,y2-y1);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bounds;
    }



    /**
     * Effettua lo snap al modulo del grafo di una linea
     * che è stata spostata o ridimensionata
     * <p/>
     *
     * @param linea linea che è stata spostata o ridimensionata
     * @param spostata true se è stata spostata, false se è stata ridimensionata
     * @param estremo se ridimensionata, l'estremo ridimensionato (false = sx, true = dx)
     *
     * @return La linea dopo lo snap al modulo del grafo
     */
    static LineaPeriodo snapModuloGrafo(LineaPeriodo linea, boolean spostata, boolean estremo) {
        /* variabili e costanti locali di lavoro */
        LineaPeriodo lineaSnap = null;
        Point2D p1, p2;
        Point2D pSnap1,pSnap2;

        try { // prova ad eseguire il codice

            if (linea != null) {

                /**
                 * Se è stata spostata snappa il punto sinistro e calcola
                 * il destro in base alla lunghezza originale.
                 * Se è stata ridimensionata, snappa l'estremo che è stato spostato
                 * e lascia l'altro estremo come era in origine.
                 */

                p1=linea.getP1();
                p2=linea.getP2();

                if (spostata) {
                    pSnap1 = snapPoint(p1);
                    double len = linea.getX2()-linea.getX1();
                    pSnap2 = new Point2D.Double(pSnap1.getX()+len,pSnap1.getY());
                } else {
                    if (!estremo) { // sinistro
                        pSnap1 = snapPoint(p1);
                        pSnap2 = new Point2D.Double(p2.getX(),pSnap1.getY());
                    } else {   // destro
                        pSnap2 = snapPoint(p2);
                        pSnap1 = new Point2D.Double(p1.getX(),pSnap2.getY());
                    }// fine del blocco if-else
                }// fine del blocco if-else

                /* costruisce la nuova linea di cella */
                lineaSnap = new LineaPeriodo(pSnap1,pSnap2);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lineaSnap;

    }


    /**
     * Effettua lo snap al modulo del grafo di un punto
     * <p/>
     *
     * @param punto da allineare
     *
     * @return Il punto dopo lo snap al modulo del grafo
     */
    public static Point2D snapPoint(Point2D punto) {
        /* variabili e costanti locali di lavoro */
        Point2D pointSnap = null;
        int stepX = Tableau.H_MODULE;
        int stepY = Tableau.V_MODULE;

        /* per spostare lo snap a metà della cella */
        int xOffset = Tableau.H_MODULE/2;
        int yOffset = Tableau.V_MODULE/2;

        int xSnap, ySnap;
        double poscell;
        int numCella;
        double fPart;

        try { // prova ad eseguire il codice

            if (punto != null) {

                /* snap X */
                double x = punto.getX();
                x-=xOffset;
                poscell = x / stepX;
                numCella = (int)poscell;
                fPart = poscell - numCella;
                if (fPart > 0.5) {
                    xSnap = stepX * (numCella + 1);
                } else {
                    xSnap = stepX * numCella;
                }// fine del blocco if-else
                xSnap+=xOffset;

                /* snap Y */
                double y = punto.getY();
                y-=yOffset;
                poscell = y / stepY;
                numCella = (int)poscell;
                fPart = poscell - numCella;
                if (fPart > 0.5) {
                    ySnap = stepY * (numCella + 1);
                } else {
                    ySnap = stepY * numCella;
                }// fine del blocco if-else
                ySnap+=yOffset;

                /* punto finale */
                pointSnap = new Point2D.Double(xSnap, ySnap);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pointSnap;

    }



    /**
     * Ritorna l'elenco delle celle di periodo intersecate da una cella.
     * <p/>
     * L'elenco non comprende la cella stessa.
     * @param graph grafo di riferimento
     * @param cella la cella da controllare
     * @return l'elenco delle altre celle intersecate
     */
    public static CellPeriodoIF[] getCelleIntersecate(JGraph graph, CellPeriodoIF cella) {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF[] celle  = null;
        ArrayList<CellPeriodoIF> listaCelle = new ArrayList<CellPeriodoIF>();

        try {    // prova ad eseguire il codice

            Map map = cella.getAttributes();
            Rectangle2D r = GraphConstants.getBounds(map);
            celle = TableauLogica.getCelleIntersecate(graph, r);

            /* trasforma l'array in lista */
            for (int k = 0; k < celle.length; k++) {
                listaCelle.add(celle[k]);
            } // fine del ciclo for

            /* rimuove eventualmente la cella stessa */
            listaCelle.remove(cella);

            /* ri-trasforma la lista in array */
            celle = new CellPeriodoIF[listaCelle.size()];
            for (int k = 0; k < listaCelle.size(); k++) {
                 celle[k] = listaCelle.get(k);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return celle;
}

    /**
     * Ritorna l'elenco delle celle di periodo intersecate da un rettangolo.
     * <p/>
     * @param graph grafo di riferimento
     * @param rect rettangolo da controllare
     * @return l'elenco delle celle di periodo intersecate
     */
    public static CellPeriodoIF[] getCelleIntersecate(JGraph graph, Rectangle2D rect) {
        /* variabili e costanti locali di lavoro */
        Rectangle rect2;
        CellPeriodoIF[] celle=null;

        try { // prova ad eseguire il codice
            rect2 = new Rectangle((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
            celle = TableauLogica.getCelleIntersecate(graph, rect2);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return celle;
    }

    /**
     * Ritorna l'elenco delle celle di periodo intersecate da un rettangolo.
     * <p/>
     * @param graph grafo di riferimento
     * @param rect rettangolo da controllare
     * @return l'elenco delle celle di periodo intersecate
     */
    private static CellPeriodoIF[] getCelleIntersecate(JGraph graph, Rectangle rect) {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF[] celle  = null;
        ArrayList<CellPeriodoIF> listaCelle = new ArrayList<CellPeriodoIF>();
        CellPeriodoIF cella;

        try {    // prova ad eseguire il codice
            Object[] intersez = graph.getRoots(rect);
            if (intersez!=null) {
                if (intersez.length>0) {
                    for(Object ogg : intersez){
                        if (ogg instanceof CellPeriodoIF) {
                            cella = (CellPeriodoIF)ogg;
                            listaCelle.add(cella);
                        }// fine del blocco if
                    }
                }// fine del blocco if
            }// fine del blocco if

            /* trasforma in array */
            celle = new CellPeriodoIF[listaCelle.size()];
            for (int k = 0; k < listaCelle.size(); k++) {
                 celle[k] = listaCelle.get(k);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return celle;
    }


    /**
     * Controlla se un rettangolo interseca dei periodi nel grafo.
     * <p/>
     * @param graph grafo di riferimento
     * @param rect il rettangolo da controllare
     * @return true se li interseca
     */
    private static boolean isIntersecaPeriodi (JGraph graph, Rectangle rect) {
        /* variabili e costanti locali di lavoro */
        boolean interseca  = false;

        try {    // prova ad eseguire il codice
            Object[] intersez = graph.getRoots(rect);
            if (intersez!=null) {
                if (intersez.length>0) {
                    for(Object ogg : intersez){
                        if (ogg instanceof CellPeriodoIF) {
                            interseca=true;
                            break;
                        }// fine del blocco if
                    }
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return interseca;
    }


    /**
     * Controlla se un rettangolo interseca dei periodi nel grafo.
     * <p/>
     * @param graph grafo di riferimento
     * @param rect il rettangolo da controllare
     * @return true se li interseca
     */
    public static boolean isIntersecaPeriodi (JGraph graph, Rectangle2D rect) {
        /* variabili e costanti locali di lavoro */
        boolean interseca  = false;

        try {    // prova ad eseguire il codice
            Rectangle clip = new Rectangle((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
            interseca = TableauLogica.isIntersecaPeriodi(graph, clip);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return interseca;
    }



}// fine della classe