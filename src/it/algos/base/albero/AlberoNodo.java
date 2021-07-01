/**
 * Title:     AlberoNodo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-feb-2004
 */
package it.algos.base.albero;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.NodoModuloOggetto;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Nodo di una struttura ad albero.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> Estende le funzionalita' della classe standard
 * <code>DefaultMutableTreeNode</code> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-feb-2004 ore 5.57.21
 */
public class AlberoNodo extends DefaultMutableTreeNode {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * <br>
     * Creates a tree node that has no parent and no children, but which
     * allows children.
     */
    public AlberoNodo() {
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
    public AlberoNodo(String unOggetto) {
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
    public AlberoNodo(Object unOggetto) {
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
    public AlberoNodo(String unOggetto, boolean accettaFigli) {
        /* rimanda al costruttore della superclasse */
        super(new NodoModuloOggetto(unOggetto), accettaFigli);
    }// fine del metodo costruttore completo


    /**
     * Traversa il sottoalbero di questo nodo (compreso) e ritorna una<br>
     * lista dei nodi. <br>
     *
     * @param metodo il metodo di attraversamento del sottoalbero.
     * (AlberoModello.PREORDER, POSTORDER o BREADTH_FIRST)
     * <p/>
     * PREORDER e POSTORDER sono entrambi di tipo depth-first.
     * - PREORDER visita il nodo di partenza per primo,
     * - POSTORDER visita il nodo di partenza per ultimo.
     * BREADTH_FIRST traversa il sottoalbero in ordine di profondita'
     * (prima tutti i nodi di livello 0, poi tutti i nodi di livello 1 e cosi' via)
     *
     * @return una lista di oggetti AlberoNodo
     */
    public ArrayList<AlberoNodo> getNodi(int metodo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<AlberoNodo> unaLista = null;
        Enumeration unaEnumerazione;
        AlberoNodo unNodo;

        try {    // prova ad eseguire il codice

            /* crea la lista da ritornare */
            unaLista = new ArrayList<AlberoNodo>();

            /** selezione del metodo di attraversamento */
            switch (metodo) {
                case AlberoModello.PREORDER:
                    unaEnumerazione = this.preorderEnumeration();
                    break;
                case AlberoModello.POSTORDER:
                    unaEnumerazione = this.postorderEnumeration();
                    break;
                case AlberoModello.BREADTH_FIRST:
                    unaEnumerazione = this.breadthFirstEnumeration();
                    break;
                default:
                    throw new Exception("Parametro non valido.");
            } /* fine del blocco switch */

            /* spazzola la enumerazione e riempie la lista */
            if (unaEnumerazione != null) {
                while (unaEnumerazione.hasMoreElements()) {
                    unNodo = (AlberoNodo)unaEnumerazione.nextElement();
                    unaLista.add(unNodo);
                }/* fine del blocco while */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unaLista;
    } // fine del metodo


    /**
     * Traversa il sottoalbero di questo nodo (compreso) usando il metodo
     * di attraversamento di default e ritorna una lista dei nodi<br>
     *
     * @return una lista di oggetti AlberoNodo
     */
    public ArrayList<AlberoNodo> getNodi() {
        /* valore di ritorno */
        return getNodi(AlberoModello.METODO_DEFAULT);
    } // fine del metodo


    /**
     * Traversa il sottoalbero di questo nodo (compreso) e ritorna una<br>
     * lista degli oggetti contenuti nei nodi. <br>
     * Ritorna solo gli oggetti che non sono nulli.<br>
     * La dimensione della lista ritornata puo' essere quindi inferiore
     * al numero di nodi dell'albero.<br>
     *
     * @param metodo il metodo di attraversamento del sottoalbero.
     * (vedi metodo getNodi)
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggetti(int metodo) {
        /* variabili e costanti locali di lavoro */
        ArrayList listaOggetti = null;
        ArrayList listaNodi;
        AlberoNodo unNodo;
        Object unOggetto;

        try {    // prova ad eseguire il codice

            /* crea la lista degli oggetti */
            listaOggetti = new ArrayList();

            /* recupera la lista dei nodi*/
            listaNodi = this.getNodi(metodo);

            /*
             * spazzola la lista dei nodi e recupera gli oggetti
             * aggiunge solo gli oggetti che non sono nulli
             */
            for (int k = 0; k < listaNodi.size(); k++) {
                unNodo = (AlberoNodo)listaNodi.get(k);
                unOggetto = unNodo.getUserObject();
                if (unOggetto != null) {
                    listaOggetti.add(unOggetto);
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaOggetti;
    } // fine del metodo


    /**
     * Traversa il sottoalbero di questo nodo (compreso) usando il
     * metodo POSTORDER e ritorna una lista degli oggetti contenuti
     * nei nodi. <br>
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggettiPostorder() {
        /* valore di ritorno */
        return getOggetti(AlberoModello.POSTORDER);
    } // fine del metodo


    /**
     * Traversa il sottoalbero di questo nodo (compreso) usando il
     * metodo PREORDER e ritorna una lista degli oggetti contenuti
     * nei nodi. <br>
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggettiPreorder() {
        /* valore di ritorno */
        return getOggetti(AlberoModello.PREORDER);
    } // fine del metodo


    /**
     * Traversa il sottoalbero di questo nodo (compreso) usando il
     * metodo Breadth First e ritorna una lista degli oggetti contenuti
     * nei nodi. <br>
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggettiBreadthFirst() {
        /* valore di ritorno */
        return getOggetti(AlberoModello.BREADTH_FIRST);
    } // fine del metodo


    /**
     * Ritorna l'elenco dei figli di questo nodo che sono foglie.
     * <p/>
     *
     * @return l'elenco dei figli di tipo foglia (oggetti TreeNode)
     */
    public ArrayList getLeafChildren() {
        /* variabili e costanti locali di lavoro */
        ArrayList nodi = null;
        TreeNode nodo;

        try {    // prova ad eseguire il codice
            nodi = new ArrayList();
            Enumeration e = this.children();
            while (e.hasMoreElements()) {
                nodo = (TreeNode)e.nextElement();
                if (nodo.isLeaf()) {
                    nodi.add(nodo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodi;
    }


    /**
     * Ritorna il numero dei figli di questo nodo che sono foglie.
     * <p/>
     *
     * @return il numero di figli di tipo foglia
     */
    public int getLeafChildrenCount() {
        return this.getLeafChildren().size();
    }


    /**
     * Ritorna l'elenco dei figli di questo nodo che non sono foglie.
     * <p/>
     *
     * @return l'elenco dei figli non-foglia (oggetti TreeNode)
     */
    public ArrayList getNonLeafChildren() {
        /* variabili e costanti locali di lavoro */
        ArrayList nodi = null;
        TreeNode nodo;

        try {    // prova ad eseguire il codice
            nodi = new ArrayList();
            Enumeration e = this.children();
            while (e.hasMoreElements()) {
                nodo = (TreeNode)e.nextElement();
                if (nodo.isLeaf() == false) {
                    nodi.add(nodo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodi;
    }


    /**
     * Ritorna il numero dei figli di questo nodo che non sono foglie.
     * <p/>
     *
     * @return il numero di figli non-foglia
     */
    public int getNonLeafChildrenCount() {
        return this.getNonLeafChildren().size();
    }


    /**
     * Ritorna tue se questo nodo e' una foglia finale.
     * <p/>
     * Una foglia finale e' un nodo che non ha figli e non permette figli.
     *
     * @return true se il nodo e' una foglia finale.
     */
    public boolean isFinalLeaf() {
        /* variabili e costanti locali di lavoro */
        boolean isFinale = false;

        if (this.isLeaf()) {
            if (this.getAllowsChildren() == false) {
                isFinale = true;
            }// fine del blocco if
        }// fine del blocco if

        /* valore di ritorno */
        return isFinale;
    }


    // Returns a TreePath containing this node.
    public TreePath getTreePath() {
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        TreeNode node = this;

        // Add all nodes to list
        while (node != null) {
            list.add(node);
            node = node.getParent();
        }
        Collections.reverse(list);

        // Convert array of nodes to TreePath
        return new TreePath(list.toArray());
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy).
     * <p/>
     * Clona questo nodo con tutti i suoi children.
     * I riferimenti agli User Object vengono mantenuti.
     * Il nodo originale non viene modificato.
     */
    public AlberoNodo clonaNodo() {
        /* variabili e costanti locali di lavoro */
        AlberoNodo nodoClone = null;
        AlberoNodo nodoChildClone;
        AlberoNodo nodo;

        try {    // prova ad eseguire il codice

            /* Clona questo nodo nella superclasse.
             * Viene creato un nodo nuovo, con riferimento
             * allo stesso User Object del nodo originale,
             * ma senza nodi Parent ne' Children*/
            nodoClone = (AlberoNodo)super.clone();

            /* Aggiunge al nodo clonato dei cloni dei propri children
             * Elabora ricorsivamente. */
            Enumeration e = this.children();
            while (e.hasMoreElements()) {
                nodo = (AlberoNodo)e.nextElement();
                nodoChildClone = nodo.clonaNodo();
                nodoClone.add(nodoChildClone);
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return nodoClone;

    } /* fine del metodo */


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
