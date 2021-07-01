package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

import java.awt.Component;
import java.util.Date;

class CellViewGiorno extends VertexView {

    protected static MyRenderer renderer = new MyRenderer();
    protected static CompGiorno componente = new CompGiorno();


    public CellViewGiorno(Object arg0) {
        super(arg0);
    }


    public CellViewRenderer getRenderer() {
        return renderer;
    }


    public static class MyRenderer implements CellViewRenderer {

        public Component getRendererComponent(
                JGraph jGraph, CellView cellView, boolean b, boolean b1, boolean b2) {
            /* variabili e costanti locali di lavoro */
            boolean continua;
            Object obj;
            CellGiorno cell = null;
            UserObjectGiorno uo = null;
            Date data;

            try { // prova ad eseguire il codice

                /* recupera la cella */
                obj = cellView.getCell();
                continua = (obj != null);
                if (continua) {
                    if (obj instanceof CellGiorno) {
                        cell = (CellGiorno)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera lo UserObject */
                if (continua) {
                    obj = cell.getUserObject();
                    continua = (obj != null);
                    if (obj instanceof UserObjectGiorno) {
                        uo = (UserObjectGiorno)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera i dati dallo UserObject e regola il componente */
                if (continua) {
                    data = uo.getData();
                    componente.setData(data);
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return componente;
        }
    }








}