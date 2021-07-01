package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import java.awt.Component;
import java.util.HashMap;

import org.jgraph.JGraph;
import org.jgraph.graph.CellHandle;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

class CellViewPeriodo extends VertexView {

    protected static CellViewRenderer renderer = new MyRenderer();

    public CellViewPeriodo(Object arg0) {
        super(arg0);
    }


    public CellViewRenderer getRenderer() {
        return renderer;
    }


    /**
     * Mostra solo gli handles di ridimensionamento orizzontale.
     * Sovrascrive getHandle (il metodo che ritorna l'handle della cella).
     * Altera la mappa interna degli handles per attivare solo quelli desiderati.
     */
    public CellHandle getHandle(GraphContext graphContext) {
        /* variabili e costanti locali di lavoro */
        CellHandle handle = super.getHandle(graphContext);
        if (handle instanceof SizeHandle) {
            SizeHandle sHandle = (SizeHandle)handle;
            int[] crs = sHandle.cursors;
            crs[0] = 0;
            crs[1] = 0;
            crs[2] = 0;
            crs[3] = 10;
            crs[4] = 11;
            crs[5] = 0;
            crs[6] = 0;
            crs[7] = 0;
        }// fine del blocco if

        /* valore di ritorno */
        return handle;
    }


    private static class MyRenderer extends VertexRenderer {

        public Component getRendererComponent(
                JGraph jGraph, CellView cellView, boolean b, boolean b1, boolean b2) {
            /* variabili e costanti locali di lavoro */
            boolean continua = false;
        	CompPeriodoIF comp=null;
            Component compOut = null;
            Object obj;
            CellPeriodoIF cell = null;
            UserObjectPeriodo uo = null;
            GrafoPrenotazioni graph = null;

            try { // prova ad eseguire il codice

                /* recupera la cella */
                obj = cellView.getCell();
                continua = (obj != null);
                if (continua) {
                    if (obj instanceof CellPeriodoIF) {
                        cell = (CellPeriodoIF)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera lo UserObject */
                if (continua) {
                    obj = cell.getUserObject();
                    continua = (obj != null);
                    if (obj instanceof UserObjectPeriodo) {
                        uo = (UserObjectPeriodo)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                // recupera il componente UI
                if (continua) {
                	comp = cell.getComponente();
				}

                /* recupera i dati dallo UserObject e li mette nel componente UI */
                if (continua) {
                	HashMap<String, Object> values = uo.getValues();
                	for(String key : values.keySet()){
                		comp.put(key, values.get(key));
                	}
                	
                	// finalizza il componente UI dopo che ha tutti i valori
                    if (jGraph != null) {
                        if (jGraph instanceof GrafoPrenotazioni) {
                            graph = (GrafoPrenotazioni)jGraph;
        					comp.pack(graph, cell.getUO());
                        }// fine del blocco if
                    }// fine del blocco if
					
                }// fine del blocco if
                
                // se non ha riconosciuto il tipo di cella rimanda alla superclasse 
                // questo succede per esempio per le celle della griglia
                if (continua) {
                    compOut = comp.getComponent();
                } else {
                    compOut = super.getRendererComponent(jGraph, cellView, b, b1, b2);
                }// fine del blocco if-else

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return compOut;

        }
    }




}