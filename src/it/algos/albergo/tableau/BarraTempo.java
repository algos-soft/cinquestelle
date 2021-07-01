/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-giu-2010
 */
package it.algos.albergo.tableau;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.pannello.PannelloFlusso;

/**
 * Barra dei giorni.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-feb-2009 ore 9.11.17
 */
class BarraTempo extends PannelloFlusso {

    /* contenitore di riferimento */
    private PanGrafi panGrafi;

    /* grafo dei mesi */
    private TbBarMesi grafoMesi;

    /* grafo dei giorni */
    private TbBarGiorni grafoGiorni;

    /* flag per mettere la barra dei mesi sotto anziche' sopra*/
    private boolean mesiSotto;

    /**
     * Costruttore completo.
     * <p/>
     * @param panGrafi contenitore di riferimento
     */
    public BarraTempo(PanGrafi panGrafi) {
        this(panGrafi,false);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     * @param panGrafi contenitore di riferimento
     * @param mesiSotto flag per mettere la barra dei mesi sotto anziche' sopra ai giorni
     */
    public BarraTempo(PanGrafi panGrafi, boolean mesiSotto) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

        this.setPanGrafi(panGrafi);
        this.setMesiSotto(mesiSotto);

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

        /* regola il pannello */
        this.setUsaGapFisso(true);
        this.setGapFisso(0);

        /* crea i grafi */
        this.setGrafoMesi(new TbBarMesi(this.getPanGrafi()));
        this.setGrafoGiorni(new TbBarGiorni(this.getPanGrafi()));

        /* li aggiunge a questa barra */
        if (this.isMesiSotto()) {
            this.add(this.getGrafoGiorni());
            this.add(this.getGrafoMesi());
        } else {
            this.add(this.getGrafoMesi());
            this.add(this.getGrafoGiorni());
        }// fine del blocco if-else

        /* popola i grafi */
        this.getGrafoMesi().popola();
        this.getGrafoGiorni().popola();

    }// fine del metodo inizia


    /**
     * Ricarica i grafi della barra.
     * <p/>
     */
    public void reload () {
        this.getGrafoGiorni().reload();
        this.getGrafoMesi().reload();
    }

    /**
     * Scala la barra.
     * <p/>
     * @param scale il fattore di scala
     */
    public void setScale (double scale) {
        this.getGrafoGiorni().setScale(scale);
        this.getGrafoMesi().setScale(scale);
    }

    
    private PanGrafi getPanGrafi() {
        return panGrafi;
    }


    private void setPanGrafi(PanGrafi panGrafi) {
        this.panGrafi = panGrafi;
    }

    private TbBarMesi getGrafoMesi() {
        return grafoMesi;
    }

    private void setGrafoMesi(TbBarMesi grafoMesi) {
        this.grafoMesi = grafoMesi;
    }

    TbBarGiorni getGrafoGiorni() {
        return grafoGiorni;
    }

    private void setGrafoGiorni(TbBarGiorni grafoGiorni) {
        this.grafoGiorni = grafoGiorni;
    }

    private boolean isMesiSotto() {
        return mesiSotto;
    }

    private void setMesiSotto(boolean mesiSotto) {
        this.mesiSotto = mesiSotto;
    }
}// fine della classe