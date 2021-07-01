/**
 * Title:     TableauGraphUI
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      11-mar-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.plaf.basic.BasicGraphUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * GraphUI del Grafo Prenotazioni
 * </p>
 * Assegna un MouseHandler specifico.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 11-mar-2009 ore 9.45.07
 */
class GPGraphUI extends BasicGraphUI {

    private GrafoPrenotazioni graph;

    private CellNuovo cellaTemp;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param graph grafo prenotazioni di riferimento
     */
    public GPGraphUI(GrafoPrenotazioni graph) {

        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setGraph(graph);

    }// fine del metodo costruttore completo


    @Override
    protected MouseListener createMouseListener() {
        return new GPMouseHandler();
    }




    /**
     * Ritorna la cella sulla quale il mouse si trova attualmente.
     * <p/>
     *
     * @param e l'evento del mouse
     *
     * @return la cella sulla quale è il mouse, null se non è su una cella
     */
    private CellPeriodoIF getCellaSottoMouse(MouseEvent e) {
        return getCellaAt(e.getX(), e.getY());
    }

    /**
     * Ritorna la cella correntemente selezionata.
     * <p/>
     *
     * @return la cella correntemente selezionata
     */
    private CellPeriodoIF getCellaSelezionata() {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF cella=null;
        Object ogg;

        try { // prova ad eseguire il codice
            ogg = this.getGraph().getSelectionCell();
            if ((ogg!=null) && (ogg instanceof CellPeriodoIF)) {
                cella = (CellPeriodoIF)ogg;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cella;
    }



    /**
     * Ritorna la cella eventualmente esistente alle date coordinate.
     * <p/>
     *
     * @param x la coordinata x
     * @param y la coordinata y
     *
     * @return la cella alle coordinate date, null se non sono coperte da una cella
     */
    private CellPeriodoIF getCellaAt(int x, int y) {
        /* variabili e costanti locali di lavoro */
    	CellPeriodoIF cella = null;
        JGraph graph;
        CellView focus = null;
        CellView cv;

        try {    // prova ad eseguire il codice

            graph = this.getGraph();
            int s = graph.getTolerance();
            Rectangle2D r = graph.fromScreen(new Rectangle2D.Double(x
                                    - s, y - s, 2 * s, 2 * s));
            focus = (focus != null && focus.intersects(graph, r)) ? focus: null;

//            Point2D punto = new Point2D.Double(x, y);
//            punto = graph.fromScreen(punto);
//            double xa = punto.getX();
//            double ya = punto.getY();

            cv = graph.getNextSelectableViewAt(focus, x, y);
            if (cv != null) {
                Object ogg = cv.getCell();
                if (ogg instanceof CellPeriodoIF) {
                    cella = (CellPeriodoIF)ogg;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cella;

    }



    /**
     * Crea e registra la cella temporanea per il nuovo periodo.
     * <p/>
     * @param e il mouseEvent
     */
    private void creaCellaTemp(MouseEvent e) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            /* trasforma da coordinate schermo a coordinate grafo */
            double x = e.getX();
            double y = e.getY();
            Point2D punto = new Point2D.Double(x, y);
            punto = getGraph().fromScreen(punto);

            /* snap al nodo più vicino */
            Point2D punto1 = TableauLogica.snapPoint(punto);

            /* recupera date e camera di inizio */
            Date data = this.getDataAt((int)punto1.getX());
            int codCamera = this.getCameraAt((int)punto1.getY());

            /* crea la cella provvisoria */
            cellaTemp = new CellNuovo();
            cellaTemp.getUO().setDataInizio(data);
            cellaTemp.getUO().setCodCamera(codCamera);

            /* dimensiona la cella con altezza standard e larghezza iniziale = 0 */
            LineaPeriodo linea = new LineaPeriodo(punto1, punto1);
            Rectangle2D bounds = TableauLogica.getBoundsCella(linea);
            AttributeMap map = cellaTemp.getAttributes();
            GraphConstants.setBounds(map, bounds);

            /* inserisce la cella nel grafo */
            getGraph().getGraphLayoutCache().insert(cellaTemp);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Trascina la cella temporanea.
     * <p/>
     * @param e il mouseEvent
     */
    private void dragCellaTemp(MouseEvent e) {

        try { // prova ad eseguire il codice

            int xMouse = e.getX();
            int yMouse = e.getY();
            Point2D puntoMouse = new Point2D.Double(xMouse, yMouse);
            puntoMouse = getGraph().fromScreen(puntoMouse);

            AttributeMap map = cellaTemp.getAttributes();
            Rectangle2D bounds = GraphConstants.getBounds(map);
            double x1Cell = bounds.getMinX();
            double y1Cell = bounds.getMinY();
            Point2D punto = new Point2D.Double(puntoMouse.getX(), y1Cell);
            Point2D puntoSnap = TableauLogica.snapPoint(punto);
            double xSnap = puntoSnap.getX();
            double w = xSnap - x1Cell;
            Rectangle2D.Double rect2 = new Rectangle2D.Double(
                    x1Cell, y1Cell, w, bounds.getHeight());

            /* modifica le dimensioni della cella finché non ne interseca altre */
            if (!TableauLogica.isIntersecaPeriodi(getGraph(),rect2)) {
                Map nested = new Hashtable();
                Map attributeMap1 = new Hashtable();
                GraphConstants.setBounds(attributeMap1, rect2);
                nested.put(cellaTemp, attributeMap1);
                getGraph().getGraphLayoutCache().edit(nested, null, null, null);

                Date data = this.getDataAt((int)(x1Cell+w));
                cellaTemp.getUO().setDataFine(data);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }




    /**
     * Ritorna la data corrispondente ad una coordinata.
     * <p/>
     * @param x la coordinata
     * @return la data corrispondente
     */
    private Date getDataAt(int x) {
        return getGraph().getPanGrafi().getBarTempo().getGrafoGiorni().getDataAt(x);
    }


    /**
     * Ritorna la camera corrispondente ad una coordinata.
     * <p/>
     * @param y la coordinata
     * @return il codice della camera corrispondente
     */
    private int getCameraAt(int y) {
        return getGraph().getPanGrafi().getBarRisorse().getRisorsaAt(y);
    }






    private GrafoPrenotazioni getGraph() {
        return graph;
    }


    private void setGraph(GrafoPrenotazioni graph) {
        this.graph = graph;
    }




    /**
     * Mouse Handler specializzato
     * </p>
     */
    public final class GPMouseHandler extends MouseHandler {

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            super.mousePressed(mouseEvent);

            if (cellaTemp == null) {
            	CellPeriodoIF unaCella = getCellaSottoMouse(mouseEvent);
                if (unaCella == null) {
                    creaCellaTemp(mouseEvent);
                }// fine del blocco if
            }

            /* segnala l'evento al grafo */
            getGraph().mousePressed(mouseEvent.getX(), mouseEvent.getY());

        }


        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            /* variabili e costanti locali di lavoro */
            int codCamera;
            Date dataInizio;
            Date dataFine;

            super.mouseReleased(mouseEvent);

            if (cellaTemp != null) {

                //recupera i dati dalla cella temporanea
                codCamera=cellaTemp.getUO().getCodCamera();
                dataInizio=cellaTemp.getUO().getDataInizio();
                dataFine=cellaTemp.getUO().getDataFine();

                //crea preventivo/prenotazione da tableau
                if (codCamera>0) {
                    if (Lib.Data.isValida(dataInizio)) {
                        if (Lib.Data.isValida(dataFine)) {
                            if (Lib.Data.isPosteriore(dataInizio, dataFine)) {
                                Tableau tableau = getGraph().getPanGrafi().getTableau();
                                tableau.creaPrevePren(codCamera, dataInizio, dataFine);
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                //elimina la cella temporanea
                Object[] oggetti = new Object[1];
                oggetti[0] = cellaTemp;
                getGraph().getGraphLayoutCache().remove(oggetti);
                cellaTemp = null;
                

            }// fine del blocco if

        }


        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

            super.mouseClicked(mouseEvent);

            /* deseleziona tutte le celle eventualmete selezionate */
            if (getCellaSottoMouse(mouseEvent) == null) {
                Object[] celle = getGraph().getSelectionCells();
                for (Object ogg : celle) {
                    getGraph().removeSelectionCell(ogg);
                }
            }// fine del blocco if

            /* su doppio clic apre la prenotazione */
            if (mouseEvent.getClickCount() == 2) {
            	CellPeriodoIF cella;
                cella = getCellaSottoMouse(mouseEvent);
                if (cella!=null) {
                    getGraph().apriPrenotazione(cella);
                }// fine del blocco if
            }// fine del blocco if

        }


        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

            // rimuove la croce di highlight quando si trascina
            getGraph().removeHighlight();

            // trascina la cella temporanea
            if (cellaTemp != null) {
                dragCellaTemp(mouseEvent);
            }// fine del blocco if

//            System.out.println(mouseEvent);
//            System.out.println(mouseEvent.getX()+","+mouseEvent.getY());
//            // todo da rivedere - bloccare il drag se interseca altre celle!!
//            CellPeriodo cella = getCellaSelezionata();
//            Map map = cella.getAttributes();
//            Rectangle2D r = GraphConstants.getBounds(map);
//            System.out.println(dim);

//            Point p = getInsertionLocation();
//            System.out.println(p);
//            getHandle()

//            Rectangle2D bounds = getGraph().getCellBounds(cella);
//            System.out.println(bounds);

//            PortView cv = getGraph().getPortViewAt(mouseEvent.getX(), mouseEvent.getY());
//            if (cv!=null) {
//                Rectangle2D rect = cv.getBounds();
//                System.out.println(rect);
//            }// fine del blocco if


//            if (cella!=null) {
//                Object[] oggetti = TableauLogica.getCelleIntersecate(getGraph(), cella);
//                if (oggetti.length>0) {
//                    int a = 87;
//                }// fine del blocco if
//            }// fine del blocco if-else

            super.mouseDragged(mouseEvent);


        }


        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            super.mouseMoved(mouseEvent);
        }


        @Override
        protected void postProcessSelection(MouseEvent mouseEvent, Object o, boolean b) {
            super.postProcessSelection(mouseEvent, o, b);
        }


    } // fine della classe 'interna'



}// fine della classe
