/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-apr-2005
 */
package it.algos.base.layout;

import it.algos.base.errore.Errore;

import java.awt.*;

/**
 * Layout del pannello bottoni in un Dialogo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Organizza i vari bottoni che appaiono nel pannello inferiore di un dialogo </li>
 * <li> I bottoni sono sempre su una ed una sola riga </li>
 * <li> Mantiene un'attributo per la distanza dal bordo destro del pannello </li>
 * <li> Mantiene un'attributo per la distanza tra i bottoni </li>
 * <li> Mantiene un flag per allinare i bottoni da destra o al centro </li>
 * <li> I bottoni vengono inseriti da destra verso sinistra (default) </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-apr-2005 ore 12.34.04
 */
public final class LayoutBottoni extends LayoutBase {

    /**
     * dimensione minima del pannello bottoni (default)
     */
    private static final Dimension DIM = new Dimension(600, 100);

    /**
     * flag - posizionamento da destra (default)
     */
    private static final boolean ALLINEAMENTO_DESTRO = true;

    /**
     * distanza del primo bottone dal bordo destro (default)
     */
    private static final int DX_DESTRA = -200;

    /**
     * distanza tra ogni bottone (default)
     */
    private static final int DX_CORRENTE = 20;

    /**
     * flag - posizionamento da destra dei bottoni
     */
    private boolean allineamentoDestro = false;

    /**
     * distanza del primo bottone dal bordo destro
     */
    private int dxDestra = 0;

    /**
     * distanza tra ogni bottone
     */
    private int dxCorrente = 0;


    /**
     * Costruttore completo senza parametri.
     */
    public LayoutBottoni() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regolazioni di default */
        this.setAllineamentoDestro(ALLINEAMENTO_DESTRO);
        this.setDxDestra(DX_DESTRA);
        this.setDxCorrente(DX_CORRENTE);
    }// fine del metodo inizia


    /**
     * Sovrascrive il metodo addLayoutComponent, da LayoutManager2.
     * <p/>
     * Viene chiamato dal metodo add del Container, quando si aggiungono gli
     * oggetti al contenitore <br>
     * <b>Attenzione:</b> il <code>Layout</code> del pannelloCampo del CampoVideo
     * deve essere regolato (con setLayout) <b>prima</b> di iniziare ad aggiungere
     * gli oggetti (altrimenti questo metodo non viene invocato) <br>
     *
     * @param unComponente oggetto da aggiungere al pannelloCampo
     * @param constraints posizione nel pannelloCampo (stringa codificata in Layout)
     *
     * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, Object)
     */
    public void addLayoutComponent(Component unComponente, Object constraints) {
        try {    // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.addLayoutComponent(unComponente, null);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * preferredLayoutSize, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore da organizzare (disegnare)
     *
     * @return dimensione preferita
     *
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        return this.getDimensione();
    }


    /**
     * minimumLayoutSize, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore di cui calcolare le dimensioni
     *
     * @return dimensione minima
     *
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }


    /**
     * maximumLayoutSize, da LayoutManager2.
     * <p/>
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore di cui calcolare le dimensioni
     *
     * @return dimensione massima
     *
     * @see java.awt.Component#getMaximumSize
     * @see LayoutManager
     */
    public Dimension maximumLayoutSize(Container target) {
        return preferredLayoutSize(target);
    }


    /**
     * Sovrascrive il metodo layoutContainer, da LayoutManager.
     * <br>
     * Posiziona i componenti <br>
     *
     * @param unContenitore pannelloCampo del CampoVideo <br>
     *
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    public void layoutContainer(Container unContenitore) {
        /* variabili e costanti locali di lavoro */
        Component comp = null;
        int tot = 0;
        int x = 0;
        int y = 0;
        int lar = 0;

        try { // prova ad eseguire il codice
            tot = unContenitore.getComponentCount();
            tot--;
            x = unContenitore.getSize().width;
            x -= this.getDxDestra();
            y = 20;

            comp = unContenitore.getComponent(tot);
            tot--;
            lar = comp.getWidth();
            x -= lar;
            comp.setLocation(x, y);

            for (int k = tot; k >= 0; k--) {
                comp = unContenitore.getComponent(k);
                lar = comp.getWidth();
                x -= this.getDxCorrente();
                x -= lar;
                comp.setLocation(x, y);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */
    }


    /**
     * Calcola la dimensione interna del pannello.
     * <p/>
     * La dimensione viene calcolata in base alla dimensione dei bottoni.
     *
     * @return la dimensione interna
     */
    private Dimension getDimensione() {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;

        try { // prova ad eseguire il codice
            /* diemnsione minima */
            dim = DIM;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * Restituisce la larghezza del pannello.
     * <p/>
     *
     * @return larghezza
     */
    private int getLarghezza() {
        /* valore di ritorno */
        return getDimensione().width;
    }


    private boolean isAllineamentoDestro() {
        return allineamentoDestro;
    }


    private void setAllineamentoDestro(boolean allineamentoDestro) {
        this.allineamentoDestro = allineamentoDestro;
    }


    private int getDxDestra() {
        return dxDestra;
    }


    private void setDxDestra(int dxDestra) {
        this.dxDestra = dxDestra;
    }


    private int getDxCorrente() {
        return dxCorrente;
    }


    private void setDxCorrente(int dxCorrente) {
        this.dxCorrente = dxCorrente;
    }

}// fine della classe
