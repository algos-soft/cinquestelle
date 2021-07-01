/**
 * Title:     TbBar
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

/**
 * Superclasse astratta - Barra delle camere o dei giorni.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
abstract class TbBar extends JGraph {

    /**
     * Costruttore completo senza parametri.<br>
     */
    public TbBar() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* regola il Graph */
            this.setEnabled(false);
            this.setAntiAliased(true);

            this.createModelAndView();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    protected void createModelAndView() {
        GraphModel model = new DefaultGraphModel();
        GraphLayoutCache view = new GraphLayoutCache(model, new DefaultCellViewFactory());
        this.setModel(model);
        this.setGraphLayoutCache(view);

    }
    
    

}// fine della classe