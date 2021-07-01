package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

import java.awt.Component;

class CellViewMese extends VertexView {

    protected static MyRenderer renderer = new MyRenderer();
    protected static CompMese componente = new CompMese();


    public CellViewMese(Object arg0) {
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
            CellMese cell = null;
            UserObjectMese uo = null;

            try { // prova ad eseguire il codice

                /* recupera la cella */
                obj = cellView.getCell();
                continua = (obj != null);
                if (continua) {
                    if (obj instanceof CellMese) {
                        cell = (CellMese)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera lo UserObject */
                if (continua) {
                    obj = cell.getUserObject();
                    continua = (obj != null);
                    if (obj instanceof UserObjectMese) {
                        uo = (UserObjectMese)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera i dati dallo UserObject e regola il componente */
                if (continua) {
                    componente.setMese(uo.getNome());
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return componente;
        }
    }



}