/**
 * Title:     AlberoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-feb-2004
 */
package it.algos.base.albero;

import it.algos.base.errore.Errore;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;

/**
 * Modello dei dati per una struttura ad albero.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> Estende le funzionalita' della classe standard
 * <code>DefaultTreeModel</code> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-feb-2004 ore 5.50.32
 */
public class AlberoModello extends DefaultTreeModel {

    /**
     * Determines how the <code>isLeaf</code> method figures
     * out if a node is a leaf node. If true, a node is a leaf
     * node if it does not allow children. (If it allows
     * children, it is not a leaf node, even if no children
     * are present.) That lets you distinguish between <i>folder</i>
     * nodes and <i>file</i> nodes in a file system, for example.
     * <p/>
     * If this value is false, then any node which has no
     * children is a leaf node, and any node may acquire
     * children.
     *
     * @see javax.swing.tree.TreeNode#getAllowsChildren
     * @see javax.swing.tree.TreeModel#isLeaf
     * @see javax.swing.tree.DefaultTreeModel#setAsksAllowsChildren
     *      Metodo di attraversamento Preorder (depth-first, dall'alto)
     */

    /** Metodo di attraversamento Preorder (depth-first, dall'alto) */
    public static final int PREORDER = 1;

    /**
     * Metodo di attraversamento Postorder (depth-first, dal basso)
     */
    public static final int POSTORDER = 2;

    /**
     * Metodo di attraversamento Breadt First (livello per livello)
     */
    public static final int BREADTH_FIRST = 3;

    /**
     * Metodo di attraversamento di default
     */
    public static final int METODO_DEFAULT = PREORDER;

    private static boolean DEFAULT_ASK_ALLOWS_CHILDREN = false;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public AlberoModello() {
        /* rimanda al costruttore di questa classe */
        this(new AlberoNodo());
    }// fine del metodo costruttore base


    /**
     * Costruttore completo. <br>
     * <br>
     * Creates a tree in which any node can have children.
     *
     * @param root primo nodo della struttura
     */
    public AlberoModello(AlberoNodo root) {
        /* rimanda al costruttore di questa classe */
        this(root, DEFAULT_ASK_ALLOWS_CHILDREN);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo. <br>
     * <br>
     * Creates a tree specifying whether any node can have children,
     * or whether only certain nodes can have children.
     *
     * @param root a TreeNode object that is the root of the tree
     * @param asksAllowsChildren a boolean, false if any node can
     * have children, true if each node is asked to see if
     * it can have children
     *
     * @see javax.swing.tree.DefaultTreeModel#asksAllowsChildren
     */
    public AlberoModello(AlberoNodo root, boolean asksAllowsChildren) {
        /* rimanda al costruttore della superclasse */
        super(root, asksAllowsChildren);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Aggiunge un nuovo nodo ad uno esistente.
     * </p>
     * Aggiunge un nodo figlio ad un nodo parente (gia' esistente) <br>
     * Se il nodo parente e' nullo, il nodo figlio diventa root <br>
     * Se il nodo parente non esiste nell'albero, non fa nulla <br>
     *
     * @param unNodoFiglio nuovo nodo da aggiungere
     * @param unNodo nodo esistente
     */
    public void addNodo(AlberoNodo unNodoFiglio, AlberoNodo unNodo) {
        /* variabili e costanti locali di lavoro */
        int pos = 0;

        try {    // prova ad eseguire il codice

            if (unNodo != null) {
                /* posizione in cui inserire il nuovo figlio */
                pos = unNodo.getChildCount();
                this.insertNodeInto(unNodoFiglio, unNodo, pos);
            } else {
                this.setRoot(unNodoFiglio);
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Traversa l'intero albero e ritorna una lista dei nodi.<br>
     *
     * @param metodo il metodo di attraversamento dell'albero.
     * (PREORDER, POSTORDER o BREADTH_FIRST)
     *
     * @return una lista di oggetti AlberoNodo
     *
     * @see AlberoModello
     *      PREORDER e POSTORDER sono entrambi di tipo depth-first.
     *      - PREORDER visita il nodo root per primo,
     *      - POSTORDER visita il nodo root per ultimo.
     *      BREADTH_FIRST traversa il sottoalbero in ordine di profondita'
     *      (prima tutti i nodi di livello 0, poi tutti i nodi di livello 1 e cosi' via)
     */
    public ArrayList<AlberoNodo> getNodi(int metodo) {

        /* valore di ritorno */
        return (this.getNodoRoot()).getNodi(metodo);

    } // fine del metodo


    /**
     * Traversa l'intero albero usando il metodo di default<br>
     * e ritorna una lista dei nodi<br>
     *
     * @return una lista di oggetti AlberoNodo
     */
    public ArrayList<AlberoNodo> getNodi() {
        /* valore di ritorno */
        return getNodi(AlberoModello.METODO_DEFAULT);
    } // fine del metodo


    /**
     * Ritorna il primo nodo contenente un dato oggetto
     * <p/>
     *
     * @param oggetto da cercare
     *
     * @return il primo nodo contenente l'oggetto richiesto
     *         null se non trovato
     */
    public AlberoNodo getNodoOggetto(Object oggetto) {
        /* variabili e costanti locali di lavoro */
        AlberoNodo nodo = null;
        Object unOggetto;
        ArrayList<AlberoNodo> nodi;

        try { // prova ad eseguire il codice
            nodi = this.getNodi();
            for (AlberoNodo unNodo : nodi) {
                unOggetto = unNodo.getUserObject();
                if (unOggetto.equals(oggetto)) {
                    nodo = unNodo;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nodo;
    } // fine del metodo


    /**
     * Traversa l'intero albero e ritorna una<br>
     * lista degli oggetti contenuti nei nodi. <br>
     *
     * @param metodo il metodo di attraversamento dell'albero.
     * (vedi metodo getNodi)
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggetti(int metodo) {

        /* valore di ritorno */
        return (this.getNodoRoot()).getOggetti(metodo);

    } // fine del metodo


    /**
     * Ritorna il numero totale di nodi
     * <p/>
     *
     * @return il numero totale di nodi
     */
    public int getSize() {
        int quanti = 0;
        ArrayList<AlberoNodo> nodi;

        try { // prova ad eseguire il codice
            nodi = this.getNodi();
            if (nodi != null) {
                quanti = nodi.size();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    } // fine del metodo


    /**
     * Traversa l'intero albero usando il metodo Postorder<br>
     * e ritorna una lista degli oggetti contenuti nei nodi.
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggettiPostorder() {
        /* valore di ritorno */
        return (this.getNodoRoot()).getOggettiPostorder();
    } // fine del metodo


    /**
     * Traversa l'intero albero usando il metodo Preorder<br>
     * e ritorna una lista degli oggetti contenuti nei nodi.
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggettiPreorder() {
        /* valore di ritorno */
        return (this.getNodoRoot()).getOggettiPreorder();
    } // fine del metodo


    /**
     * Traversa l'intero albero usando il metodo Breadth First<br>
     * e ritorna una lista degli oggetti contenuti nei nodi.
     *
     * @return una lista di oggetti
     */
    public ArrayList getOggettiBreadthFirst() {
        /* valore di ritorno */
        return (this.getNodoRoot()).getOggettiBreadthFirst();
    } // fine del metodo


    /**
     * Ritorna il nodo Root di questo albero.<br>
     *
     * @return il nodo Root di questo albero (oggetto AlberoNodo)
     *         null se il nodo Root non esiste o non e' di tipo AlberoNodo.
     */
    public AlberoNodo getNodoRoot() {
        /* variabili e costanti locali di lavoro */
        AlberoNodo nodoRoot = null;
        Object unOggetto;

        try {    // prova ad eseguire il codice
            unOggetto = this.getRoot();
            /* controllo di congruita' */
            if (unOggetto != null) {
                if (unOggetto instanceof AlberoNodo) {
                    nodoRoot = (AlberoNodo)unOggetto;
                } else {
                    throw new Exception("Il nodo Root non e' un oggetto di tipo AlberoNodo.");
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodoRoot;
    } // fine del metodo


    /**
     * Visualizza questo albero in una finestra con tutti i nodi espansi.<br>
     */
    public void mostraAlbero() {
        /* variabili e costanti locali di lavoro */
        JTree alberoVideo = null;
        JFrame finestra = null;
        JScrollPane scorrevole = null;
        ArrayList listaNodi = null;
        AlberoNodo unNodo = null;
        TreePath unPercorso = null;

        try {    // prova ad eseguire il codice
            /* prepara una finestra */
            alberoVideo = new JTree();
            finestra = new JFrame("Albero");
            finestra.setSize(600, 400);
            scorrevole = new JScrollPane(alberoVideo);
            finestra.getContentPane().add(scorrevole);
            alberoVideo.setModel(this);
//            alberoVideo.setShowsRootHandles(true);
            /* verifica che l'albero abbia almeno il nodo Root */
            if (this.getNodoRoot() != null) {

                /* espande tutti i nodi dell'albero */
                listaNodi = this.getNodi();
                for (int k = 0; k < listaNodi.size(); k++) {
                    unNodo = (AlberoNodo)listaNodi.get(k);
                    unPercorso = new TreePath(unNodo.getPath());
                    alberoVideo.expandPath(unPercorso);
                } // fine del ciclo for

                /* visualizza la finestra */
                finestra.setVisible(true);
//                new MessaggioAvviso("OK");

            } else {
                throw new Exception("L'albero non puo' essere visualizzato " +
                        "perche' non ha nodo Root.");
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


}// fine della classe