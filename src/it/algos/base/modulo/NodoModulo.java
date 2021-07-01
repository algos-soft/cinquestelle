/**
 * Title:     NodoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      07-giu-2004
 */
package it.algos.base.modulo;

import it.algos.base.albero.AlberoNodo;

/**
 * Nodo di un albero Moduli.
 * </p>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 7-giu-2004 ore 14.04.00
 */
public class NodoModulo extends AlberoNodo {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * <br>
     * Creates a tree node that has no parent and no children, but which
     * allows children.
     */
    public NodoModulo() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo. <br>
     * <br>
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * @param unOggetto an Object provided by the user that constitutes
     * the node's data
     */
    public NodoModulo(String unOggetto) {
        /* rimanda al costruttore di questa classe */
        this(unOggetto, true);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo. <br>
     * <br>
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * @param unOggetto an Object provided by the user that constitutes
     * the node's data
     */
    public NodoModulo(Object unOggetto) {
        /* rimanda al costruttore della superclasse */
        super(unOggetto);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo. <br>
     * <br>
     * Creates a tree node with no parent, no children, initialized with
     * the specified user object, and that allows children only if
     * specified.
     *
     * @param unOggetto an Object provided by the user that constitutes
     * the node's data
     * @param accettaFigli if true, the node is allowed to have child
     * nodes -- otherwise, it is always a leaf node
     */
    public NodoModulo(String unOggetto, boolean accettaFigli) {
        /* rimanda al costruttore della superclasse */
//        super(new NodoModuloOggetto(unOggetto), accettaFigli);
    }// fine del metodo costruttore completo


    public void setPercorso(String percorso) {
        ((NodoModuloOggetto)super.getUserObject()).setPercorso(percorso);
    }


    public void setNomeChiave(String nomeChiave) {
        ((NodoModuloOggetto)super.getUserObject()).setNomeChiave(nomeChiave);
    }


    public void setModulo(Modulo modulo) {
        ((NodoModuloOggetto)super.getUserObject()).setModulo(modulo);
    }


    public String getPercorso() {
        return ((NodoModuloOggetto)super.getUserObject()).getPercorso();
    }


    public String getNomeChiave() {
        return ((NodoModuloOggetto)super.getUserObject()).getNomeChiave();
    }


    public Modulo getModulo() {
        return ((NodoModuloOggetto)super.getUserObject()).getModulo();
    }

}// fine della classe
