/**
 * Title:     TbBarGiorni
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.VertexView;

import java.util.Date;

/**
 * Barra dei giorni.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
class TbBarGiorni extends TbBar {

    /* contenitore di riferimento */
    private PanGrafi panGrafi;

    /**
     * Costruttore completo senza parametri.
     * <p/>
     * @param panGrafi contenitore di riferimento
     */
    public TbBarGiorni(PanGrafi panGrafi) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setPanGrafi(panGrafi);

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
        this.popola();
    }// fine del metodo inizia


    @Override
    protected void createModelAndView() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            GraphModel model = new DefaultGraphModel();
            GraphLayoutCache view = new GraphLayoutCache(model, new GiornoViewFactory());
            this.setModel(model);
            this.setGraphLayoutCache(view);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }



    /**
     * Aggiunge un giorno
     * <p/>
     * Il giorno viene aggiunto alla lista ordinata e al grafo.
     * @param giorno data da aggiungere
     */
    private void addGiorno(Date giorno) {

        try {    // prova ad eseguire il codice

            /* aggiunge al grafo e alla lista */
            int pos = this.getPos(giorno);
            GraphCell cell = new CellGiorno(giorno,pos);
            this.getGraphLayoutCache().insert(cell);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera la posizione di un giorno nella barra dei giorni.
     * <p/>
     * Se il giorno non è compreso nella barra, ritorna comunque la sua posizione
     * dove sarebbe se la barra fosse abbastanza estesa. Se il giorno cade prima
     * del primo giorno, ritorna comunque la posizione negativa.
     * @param giorno la data da cercare
     * @return la posizione teorica nella barra (0 per il primo giorno)
     */
    public int getPos (Date giorno) {
        /* variabili e costanti locali di lavoro */
        int offset=0;
        Date dataIniziale;

        try { // prova ad eseguire il codice
            dataIniziale = this.getData1();
            offset = Lib.Data.diff(giorno, dataIniziale);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return offset;

    }

    /**
     * Ritorna la data corrispondente ad una coordinata.
     * <p/>
     * @param x la coordinata
     * @return la data corrispondente
     */
    public Date getDataAt(int x) {
        /* variabili e costanti locali di lavoro */
        Date data=null;
        Date dataInizio;


        try { // prova ad eseguire il codice
            dataInizio = this.getData1();
            int giorni = x/Tableau.H_MODULE;
            data = Lib.Data.add(dataInizio, giorni);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna il numero di camere.
     * <p/>
     * @return il numero di camere
     */
    public int getNumGiorni() {
        int ng =Lib.Data.diff(this.getData2(), this.getData1());
        ng++;
        return ng;
    }



    /**
     * Ricarica il grafo delle date.
     * <p/>
     */
    public void reload () {
        this.popola();
    }


    /**
     * Svuota e riempie nuovamente il grafo.
     * <p/>
     * La barra dei giorni viene riempita in base alle date correntemente impostate.
     */
    void popola() {
        /* variabili e costanti locali di lavoro */
        GraphLayoutCache cache;

        try {    // prova ad eseguire il codice


            /* svuota grafo */
            cache = this.getGraphLayoutCache();
            Object[] celle = cache.getCells(true,true,true,true);
            cache.remove(celle);


            /* riempie con le date correnti */
            Date d1 = this.getData1();
            Date d2 = this.getData2();
            Date data=d1;
            while (Lib.Data.isPrecedenteUguale(d2, data)) {
                this.addGiorno(data);
                data = Lib.Data.add(data, 1);
            }// fine del blocco while

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    private PanGrafi getPanGrafi() {
        return panGrafi;
    }


    private void setPanGrafi(PanGrafi panGrafi) {
        this.panGrafi = panGrafi;
    }


    private Date getData1() {
        return this.getPanGrafi().getData1();
    }

    private Date getData2() {
        return this.getPanGrafi().getData2();
    }

    /**
     * ViewFactory per le celle di tipo Giorno.
     * </p>
     */
    private class GiornoViewFactory extends DefaultCellViewFactory {

        protected VertexView createVertexView(Object cell) {
            return new CellViewGiorno(cell);
        }

    } // fine della classe CameraViewFactory


}// fine della classe