package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import java.awt.Component;
import java.util.HashMap;

import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexView;

/**
 * Cell view per le celle di tipo Risorsa
 */
public class CellViewRisorsa extends VertexView {


    protected static MyRenderer renderer = new MyRenderer();



    public CellViewRisorsa(Object arg0) {
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
        	CompRisorsaIF comp=null;
            Object obj;
            CellRisorsaIF cell = null;
            UserObjectRisorsa uo = null;

            try { // prova ad eseguire il codice

                /* recupera la cella */
                obj = cellView.getCell();
                continua = (obj != null);
                if (continua) {
                    if (obj instanceof CellRisorsaIF) {
                        cell = (CellRisorsaIF)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if
                
                /* recupera lo UserObject */
                if (continua) {
                    obj = cell.getUserObject();
                    continua = (obj != null);
                    if (obj instanceof UserObjectRisorsa) {
                        uo = (UserObjectRisorsa)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                // recupera il componente UI
                if (continua) {
                	comp = cell.getComponente();
				}

                /* recupera i dati dallo UserObject e li mette nel il componente UI */
                if (continua) {
                	HashMap<String, Object> values = uo.getValues();
                	for(String key : values.keySet()){
                		comp.set(key, values.get(key));
                	}
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp.getComponent();
        }
    }

}
