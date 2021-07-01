package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

/**
 * Abstract class with functionalities common to all 
 * the different Resource bars 
 */
@SuppressWarnings("serial")
public class TbBarRisorse extends TbBar {

	/* Pannello dei grafi di riferimento */
    private PanGrafi panGrafi;
    
    /* mappa ordinata delle celle di risorsa presenti nella barra (codice, cella) */
    private LinkedHashMap<Integer,CellRisorsaIF> mappaRisorse;

    /**
     * Costruttore
     * <p/>
     *
     * @param panGrafi pannello dei grafi di riferimento
     * @param cellViewClass classe per le viste delle celle (la classe deve estendere VertexView)
     */
	public TbBarRisorse(PanGrafi panGrafi) {
        /* rimanda al costruttore della superclasse */
        super();
        this.setPanGrafi(panGrafi);
        this.inizia();

    }// fine del metodo costruttore completo

    
    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     */
    private void inizia() {
    	
        /* mappa delle celle di risorsa per trovare la cella e la posizione */
        LinkedHashMap<Integer,CellRisorsaIF> mappa = new LinkedHashMap<Integer,CellRisorsaIF>();
        this.setMappaRisorse(mappa);

        /* riempie il grafo con le celle di risorsa */
        this.popola();

    }

    
    @Override
    protected void createModelAndView() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            GraphModel model = new DefaultGraphModel();
            GraphLayoutCache view = new GraphLayoutCache(model, new RisorsaViewFactory());
            this.setModel(model);
            this.setGraphLayoutCache(view);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    
    public void reload(){
    	
        /* svuota grafo */
    	GraphLayoutCache cache = this.getGraphLayoutCache();
        Object[] cells = cache.getCells(true,true,true,true);
        cache.remove(cells);

    	popola();
    }
    
    /**
     * Popola la barra delle risorse.
     * <p/>
     */
    public void popola() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CellRisorsaIF> celle;
        LinkedHashMap<Integer,CellRisorsaIF> mappaRisorse;
        CellRisorsaIF cella;

        try { // prova ad eseguire il codice

            /* svuota la mappa risorse */
            mappaRisorse = this.getMappaRisorse();
            mappaRisorse.clear();

            /* inserisce le nuove celle */
            Tableau tableau = getPanGrafi().getTableau();
            celle = tableau.getCelleRisorsa();
            for (int k = 0; k < celle.size(); k++) {
                cella = celle.get(k);
                cella.setPosizione(k);
                getGraphLayoutCache().insert(cella);
                mappaRisorse.put(cella.getCodice(),cella);
            }


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    
    /**
     * Ritorna il codice della risorsa corrispondente ad una coordinata.
     * <p/>
     * @param y la coordinata
     * @return il codice della risorsa corrispondente
     */
    public int getRisorsaAt(int y) {
        /* variabili e costanti locali di lavoro */
        int codRisorsa=0;
        CellRisorsaIF cella;
        Rectangle clip;
        Object[] celle;

        try { // prova ad eseguire il codice
            clip = new Rectangle(1,y,1,1);
            celle = this.getRoots(clip);
            if (celle!=null) {
                if (celle.length==1) {
                    Object ogg = celle[0];
                    if (ogg instanceof CellRisorsaIF) {
                        cella = (CellRisorsaIF)ogg;
                        codRisorsa = cella.getCodice();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codRisorsa;
    }


    /**
     * Recupera il nome di una risorsa dal codice.
     * <p/>
     *
     * @param idRisorsa codice della risorsa
     *
     * @return il nome della risorsa
     */
    public String getNomeRisorsa(int idRisorsa) {
        /* variabili e costanti locali di lavoro */
        String nomeRisorsa="";
        CellRisorsaIF cella;

        try { // prova ad eseguire il codice

            cella = this.getMappaRisorse().get(idRisorsa);
            if (cella!=null) {
            	nomeRisorsa = cella.getUO().getNome();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeRisorsa;

    }





    /**
     * Ritorna il numero di risorse.
     * <p/>
     *
     * @return il numero di risorse
     */
    public int getNumRisorse() {
        return this.getMappaRisorse().size();
    }



    /**
     * Recupera la posizione di una risorsa nella barra.
     * <p/>
     *
     * @param codRisorsa codice della risorsa
     *
     * @return la posizione nella barra (0 per la prima, -1 se non trovata)
     */
    public int getPos(int codRisorsa) {
        /* variabili e costanti locali di lavoro */
        int pos=-1;

        try { // prova ad eseguire il codice
            Collection<Integer> coll = this.getMappaRisorse().keySet();
            ArrayList<Integer> lista = new  ArrayList<Integer>(coll);
            pos = lista.indexOf(codRisorsa);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pos;

    }

    
    protected PanGrafi getPanGrafi() {
        return panGrafi;
    }


    private void setPanGrafi(PanGrafi panGrafi) {
        this.panGrafi = panGrafi;
    }

    protected LinkedHashMap<Integer, CellRisorsaIF> getMappaRisorse() {
        return mappaRisorse;
    }


    protected void setMappaRisorse(LinkedHashMap<Integer, CellRisorsaIF> mappaRisorse) {
        this.mappaRisorse = mappaRisorse;
    }

    /**
     * ViewFactory per le celle di tipo Risorsa.
     * </p>
     */
    private class RisorsaViewFactory extends DefaultCellViewFactory {

		protected VertexView createVertexView(Object cell) {
            return new CellViewRisorsa(cell);
        }
		
    } // fine della classe


}
