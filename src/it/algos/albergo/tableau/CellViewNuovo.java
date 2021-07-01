package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import org.jgraph.JGraph;
import org.jgraph.graph.CellHandle;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import java.awt.Component;
import java.util.Date;

class CellViewNuovo extends VertexView {

    protected static CellViewRenderer renderer = new MyRenderer();

    protected static CompNuovo componente = new CompNuovo();


    public CellViewNuovo(Object arg0) {
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
            boolean continua;
            Component compOut = null;
            Object obj;
            CellNuovo cell = null;
            UserObjectNuovo uo = null;
            GrafoPrenotazioni graph = null;

            try { // prova ad eseguire il codice

                /* recupera il componente di riferimento */
                if (jGraph != null) {
                    if (jGraph instanceof GrafoPrenotazioni) {
                        graph = (GrafoPrenotazioni)jGraph;
                    }// fine del blocco if
                }// fine del blocco if

                /* recupera la cella */
                obj = cellView.getCell();
                continua = (obj != null);
                if (continua) {
                    if (obj instanceof CellNuovo) {
                        cell = (CellNuovo)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* recupera lo UserObject */
                if (continua) {
                    obj = cell.getUserObject();
                    continua = (obj != null);
                    if (obj instanceof UserObjectNuovo) {
                        uo = (UserObjectNuovo)obj;
                    } else {
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if

                /* regola il componente */
                if (continua) {

                    /* nome cliente */
                    componente.setTesto(creaTesto(uo, graph));

                }// fine del blocco if

                /* se non ha riconosciuto il tipo di cella rimanda alla superclasse */
                if (continua) {
                    compOut = componente;
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


    /**
     * Crea il testo da visualizzare nella cella.
     * <p/>
     * @param uo lo user-object
     * @param graph il grafo prenotazioni
     * @return il testo da visualizzare
     */
    private static String creaTesto(UserObjectNuovo uo, GrafoPrenotazioni graph) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Date data1, data2;
        int codCamera;
        String strCamera;

        try {    // prova ad eseguire il codice
            data1 = uo.getDataInizio();
            data2 = uo.getDataFine();
            codCamera = uo.getCodCamera();
            strCamera = graph.getPanGrafi().getNomeRisorsa(codCamera);

            int n1 = Lib.Data.getNumeroGiorno(data1);
            int n2 = Lib.Data.getNumeroGiorno(data2);

            testo = ""+n1+"-"+n2+ " ("+strCamera+")";

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


}