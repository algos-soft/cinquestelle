/**
 * Copyright:    Copyright (c) 2004
 * Company:      Algos s.r.l.
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 29 novembre 2003 alle 17.35
 */
package it.algos.base.layout;

import java.awt.*;

/**
 * Adattatore astratto per l'interfaccia <code>LayoutManager2</code>.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Adapter</b> <br>
 * <li> I metodi di questa classe sono vuoti <br>
 * <li> Questa classe esiste come utilita' per creare oggetti che
 * utilizzano l'interfaccia <code>LayoutManager2</code> <br>
 * <li> Implementa tutti i metodi della interfaccia <code>LayoutManager2</code> <br>
 * </ul>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @version 1.0  /  21 dicembre 2003 ore 17.08
 */
public abstract class LayoutAdapter extends Object {

    /**
     * addLayoutComponent, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unNome stringa da associare al componente
     * @param unComponente componente da aggiungere
     *
     * @see #addLayoutComponent
     */
    public void addLayoutComponent(String unNome, Component unComponente) {
    }


    /**
     * removeLayoutComponent, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param comp componente da rimuovere
     */
    public void removeLayoutComponent(Component comp) {
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
        return null;
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
        return null;
    }


    /**
     * layoutContainer, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore da organizzare (disegnare)
     */
    public void layoutContainer(Container parent) {
    }


    /**
     * addLayoutComponent, da LayoutManager2.
     * <p/>
     * Adds the specified component to the layout, using the specified
     * constraint object <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param comp the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints) {
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
        return null;
    }


    /**
     * getLayoutAlignmentX, da LayoutManager2.
     * <p/>
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore di cui calcolare l'allineamento
     *
     * @return valore dell'allineamento
     */
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }


    /**
     * getLayoutAlignmentY, da LayoutManager2.
     * <p/>
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore di cui calcolare l'allineamento
     *
     * @return valore dell'allineamento
     */
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }


    /**
     * getLayoutAlignmentY, da LayoutManager2.
     * <p/>
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore da invalidare
     */
    public void invalidateLayout(Container target) {
    }


} // fine della classe
