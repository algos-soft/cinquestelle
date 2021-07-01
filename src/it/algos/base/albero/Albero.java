/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-feb-2006
 */
package it.algos.base.albero;

import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.albero.*;
import it.algos.base.libreria.Lib;
import it.algos.base.pannello.PannelloBase;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Implementazione di un pannello grafico contenente un albero
 * inserito in uno scorrevole.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-feb-2006 ore 22.56.29
 */
public class Albero extends PannelloBase {

    /* modello dati dell'albero */
    private AlberoModello modello;

    /* albero di tipo JTree */
    private JTree tree;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * voce della finestra che visualizza l'albero
     */
    private String titoloFinestra;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public Albero() {

        /* rimanda al costruttore della classe */
        this(new AlberoModello());

    }// fine del metodo costruttore completo


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modello il modello dati per l'albero
     */
    public Albero(AlberoModello modello) {

        /* rimanda al costruttore della superclasse */
        super();

        this.setModelloAlbero(modello);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
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
        /* variabili e costanti locali di lavoro */
        JTree tree;

        try { // prova ad eseguire il codice

            /* lista dei propri eventi */
            this.setListaListener(new EventListenerList());

            /* assegnazione layout */
            this.setLayout(new BorderLayout());

            /* crea e registra il JTree */
            tree = new JTree();
            this.setTree(tree);

            /* aggiunge un Selection Listener al JTree */
            this.getTree().addTreeSelectionListener(new SelectionListener());

            /* regola il JTree */
            /* se non lo faccio opaco si vede la differenza tra sfondo e testi */
            tree.setOpaque(true);
            tree.setShowsRootHandles(true);

            /* assegna il modello dati al JTree */
            tree.setModel(this.getModelloAlbero());

            /* inserisce il JTree in uno scorrevole */
            JScrollPane scorrevole = new JScrollPane(tree);

            /* inserisce lo scorrevole in questo pannello */
            this.add(scorrevole);

            /* voce della finestra che mostra l'albero */
            this.setTitoloFinestra("Albero");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Mostra l'albero in una finestra.
     * <p/>
     */
    public void mostra() {
        /* variabili e costanti locali di lavoro */
        JFrame finestra;
        JScrollPane scorrevole;
        ArrayList listaNodi;
        AlberoNodo unNodo;
        TreePath unPercorso;

        try {    // prova ad eseguire il codice

            /* aggiorna il modello prima di mostrarlo
             * (serve se è stata modificata la struttura dei nodi) */
            this.getModelloAlbero().reload();

            finestra = new JFrame(this.getTitoloFinestra());
            scorrevole = new JScrollPane(this);
            finestra.getContentPane().add(scorrevole);
            if (this.getNodoRoot() != null) {

                /* espande tutti i nodi dell'albero */
                listaNodi = this.getNodi();
                /* traverso tutta la collezione */
                for (int k = 0; k < listaNodi.size(); k++) {
                    unNodo = (AlberoNodo)listaNodi.get(k);
                    unPercorso = new TreePath(unNodo.getPath());
                    this.getTree().expandPath(unPercorso);
                } // fine del ciclo for

            } else {
                throw new Exception("L'albero non puo' essere visualizzato " +
                        "perche' non ha nodo Root.");
            }// fine del blocco if-else

            finestra.pack();
            finestra.setVisible(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il nodo Root di questo albero.<br>
     *
     * @return il nodo Root di questo albero (oggetto AlberoNodo)
     *         null se il nodo Root non esiste o non e' di tipo AlberoNodo.
     */
    public AlberoNodo getNodoRoot() {
        /* variabili e costanti locali di lavoro */
        AlberoModello modello;
        AlberoNodo nodoRoot = null;

        try {    // prova ad eseguire il codice
            modello = this.getModelloAlbero();
            if (modello != null) {
                nodoRoot = modello.getNodoRoot();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodoRoot;
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
        AlberoModello modello;

        try {    // prova ad eseguire il codice
            modello = this.getModelloAlbero();
            if (modello != null) {
                nodo = modello.getNodoOggetto(oggetto);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodo;
    } // fine del metodo


    /**
     * Traversa l'intero albero e ritorna una lista dei nodi.<br>
     *
     * @param metodo il metodo di attraversamento dell'albero.
     * (PREORDER, POSTORDER o BREADTH_FIRST)
     * <p/>
     * PREORDER e POSTORDER sono entrambi di tipo depth-first.
     * - PREORDER visita il nodo root per primo,
     * - POSTORDER visita il nodo root per ultimo.
     * BREADTH_FIRST traversa il sottoalbero in ordine di profondita'
     * (prima tutti i nodi di livello 0, poi tutti i nodi di livello 1 e cosi' via)
     *
     * @return una lista di oggetti AlberoNodo
     */
    public ArrayList<AlberoNodo> getNodi(int metodo) {
        /* variabili e costanti locali di lavoro */
        AlberoModello modello;
        ArrayList<AlberoNodo> nodi = null;

        try { // prova ad eseguire il codice
            modello = this.getModelloAlbero();
            if (modello != null) {
                nodi = modello.getNodi(metodo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nodi;
    } // fine del metodo


    /**
     * Traversa l'intero albero usando il metodo di default<br>
     * e ritorna una lista dei nodi<br>
     *
     * @return una lista di oggetti AlberoNodo
     */
    public ArrayList<AlberoNodo> getNodi() {
        /* variabili e costanti locali di lavoro */
        AlberoModello modello;
        ArrayList<AlberoNodo> nodi = null;

        try { // prova ad eseguire il codice
            modello = this.getModelloAlbero();
            if (modello != null) {
                nodi = modello.getNodi();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nodi;
    } // fine del metodo


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
        AlberoModello modello;

        try { // prova ad eseguire il codice
            modello = this.getModelloAlbero();
            if (modello != null) {
                modello.addNodo(unNodoFiglio, unNodo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Espande completamente l'albero.
     * <p/>
     */
    public void expandAll() {
        this.expandAll(true);
    }


    /**
     * Collassa completamente l'albero.
     * <p/>
     */
    public void collapseAll() {
        this.expandAll(false);
    }


    /**
     * Espande o collassa completamente l'albero
     * <p/>
     *
     * @param expand true per espandere false per collassare
     */
    private void expandAll(boolean expand) {
        /* variabili e costanti locali di lavoro */
        TreeNode root;

        try { // prova ad eseguire il codice

            // recupera il nodo root
            root = this.getModelloAlbero().getNodoRoot();

            // Traverse tree from root
            this.expandAll(new TreePath(root), expand);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Espande o collassa completamente un path dell'albero
     * <p/>
     *
     * @param parent TreePath da espandere / collassare
     * @param expand true per espandere false per collassare
     */
    private void expandAll(TreePath parent, boolean expand) {

        try { // prova ad eseguire il codice
            JTree tree = this.getTree();

            // Traverse children
            TreeNode node = (TreeNode)parent.getLastPathComponent();
            if (node.getChildCount() >= 0) {
                for (Enumeration e = node.children(); e.hasMoreElements();) {
                    TreeNode n = (TreeNode)e.nextElement();
                    TreePath path = parent.pathByAddingChild(n);
                    this.expandAll(path, expand);
                }
            }

            // Expansion or collapse must be done bottom-up
            if (expand) {
                tree.expandPath(parent);
            } else {
                tree.collapsePath(parent);
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Espande i nodi fino al livello specificato.
     * <p/>
     *
     * @param level livello di espansione
     * 0 = mostra solo il nodo root
     * 1 = mostra i nodi di primo livello (sotto a root)
     * 2 = etc...
     */
    public void setExpansionLevel(int level) {
        /* variabili e costanti locali di lavoro */
        AlberoNodo nodoRoot;
        ArrayList<AlberoNodo> nodi;
        int livelloNodo;
        TreePath path;

        try {    // prova ad eseguire il codice
            if (level >= 0) {
                this.expandAll();
                nodoRoot = this.getModelloAlbero().getNodoRoot();
                nodi = nodoRoot.getNodi(AlberoModello.BREADTH_FIRST);
                for (AlberoNodo nodo : nodi) {
                    livelloNodo = nodo.getLevel();
                    if (livelloNodo == level + 1) {
                        path = nodo.getTreePath().getParentPath();
                        this.expandAll(path, false);
                    }// fine del blocco if
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @param listener specifico
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.addLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * E' responsabilita' della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @param unEvento da lanciare
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, Albero.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il renderer per i nodi dell'albero.
     * <p/>
     *
     * @param renderer da assegnare
     */
    public void setCellRenderer(TreeCellRenderer renderer) {
        this.getTree().setCellRenderer(renderer);
    }


    /**
     * Ritorna un array contenente tutti
     * i nodi selezionati.
     * <p/>
     *
     * @return un array contenente i nodi selezionati.
     */
    public ArrayList<AlberoNodo> getNodiSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<AlberoNodo> nodiSelezionati = null;
        JTree tree;
        TreeSelectionModel tsm;
        AlberoNodo nodo;
        TreePath[] paths;
        Object comp;

        try {    // prova ad eseguire il codice

            nodiSelezionati = new ArrayList<AlberoNodo>();
            tree = this.getTree();
            tsm = tree.getSelectionModel();
            paths = tsm.getSelectionPaths();

            if (paths != null) {
                for (TreePath path : paths) {
                    comp = path.getLastPathComponent();
                    if (comp != null) {
                        if (comp instanceof AlberoNodo) {
                            nodo = (AlberoNodo)comp;
                            nodiSelezionati.add(nodo);
                        }// fine del blocco if
                    }// fine del blocco if
                }
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nodiSelezionati;
    }


    /**
     * Ritorna un array contenente gli User Objects di tutti
     * i nodi selezionati.
     * <p/>
     *
     * @return un array contenente gli User Objects selezionati.
     */
    public ArrayList<Object> getOggettiSelezionati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> oggetti = null;
        ArrayList<AlberoNodo> listaNodi;
        Object oggetto;

        try {    // prova ad eseguire il codice
            oggetti = new ArrayList<Object>();
            listaNodi = this.getNodiSelezionati();
            for (AlberoNodo nodo : listaNodi) {
                oggetto = nodo.getUserObject();
                oggetti.add(oggetto);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggetti;
    }


    /**
     * Recupera lo UserObject dell'ultimo componente di un dato path.
     * <p/>
     *
     * @param path da elaborare
     *
     * @return lo User Object all'estremita' del path
     */
    public Object getOggetto(TreePath path) {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;
        Object pathComp;
        AlberoNodo nodo;

        try {    // prova ad eseguire il codice
            pathComp = path.getLastPathComponent();
            if (pathComp != null) {
                if (pathComp instanceof AlberoNodo) {
                    nodo = (AlberoNodo)pathComp;
                    oggetto = nodo.getUserObject();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return oggetto;
    }


    /**
     * Seleziona il nodo successivo all'ultimo selezionato.
     * <p/>
     */
    public void selectNextNode() {
        /* variabili e costanti locali di lavoro */
        ArrayList<AlberoNodo> nodiSelezionati;
        AlberoNodo ultimoNodo;
        DefaultMutableTreeNode nodoNext;
        TreeNode[] pathNext;
        TreePath treePathNext;

        try {    // prova ad eseguire il codice
            nodiSelezionati = this.getNodiSelezionati();
            if (nodiSelezionati.size() > 0) {
                ultimoNodo = nodiSelezionati.get(nodiSelezionati.size() - 1);
                nodoNext = ultimoNodo.getNextNode();
                if (nodoNext != null) {
                    pathNext = nodoNext.getPath();
                    treePathNext = new TreePath(pathNext);
                    this.getTree().setSelectionPath(treePathNext);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Seleziona il nodo precedente al primo selezionato.
     * <p/>
     */
    public void selectPrevNode() {
        /* variabili e costanti locali di lavoro */
        ArrayList<AlberoNodo> nodiSelezionati;
        AlberoNodo primoNodo;
        DefaultMutableTreeNode nodoPrev;
        TreeNode[] pathPrev;
        TreePath treePathPrev;

        try {    // prova ad eseguire il codice
            nodiSelezionati = this.getNodiSelezionati();
            if (nodiSelezionati.size() > 0) {
                primoNodo = nodiSelezionati.get(0);
                nodoPrev = primoNodo.getPreviousNode();
                if (nodoPrev != null) {
                    pathPrev = nodoPrev.getPath();
                    treePathPrev = new TreePath(pathPrev);
                    this.getTree().setSelectionPath(treePathPrev);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Listener per la modifica dei dati nel modello.
     * </p>
     */
    private final class DataListener implements TreeModelListener {

        public void treeNodesChanged(TreeModelEvent e) {
            fire(Albero.Evento.datiModificati);
        }


        public void treeNodesInserted(TreeModelEvent e) {
            fire(Albero.Evento.datiModificati);
        }


        public void treeNodesRemoved(TreeModelEvent e) {
            fire(Albero.Evento.datiModificati);
        }


        public void treeStructureChanged(TreeModelEvent e) {
            fire(Albero.Evento.datiModificati);
        }

    } // fine della classe 'interna'


    /**
     * Listener per la modifica della selezione nell'albero.
     * </p>
     */
    private final class SelectionListener implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent e) {
            fire(Albero.Evento.selezioneModificata);
        }

    } // fine della classe 'interna'


    /**
     * Recupera il modello dati all'albero
     * <p/>
     *
     * @return il modello dati dell'albero
     */
    public AlberoModello getModelloAlbero() {
        return modello;
    }


    /**
     * Assegna il modello dati all'albero
     * <p/>
     *
     * @param modello il modello dati per l'albero
     */
    public void setModelloAlbero(AlberoModello modello) {
        /* variabili e costanti locali di lavoro */
        JTree tree;

        try { // prova ad eseguire il codice

            /* assegna il modello */
            this.modello = modello;

            /* aggiunge il listener dei dati al modello */
            if (this.modello != null) {
                this.modello.addTreeModelListener(new DataListener());
            }// fine del blocco if

            /**
             * Se esiste gia' il componente grafico JTree
             * gli assegna il nuovo modello
             */
            tree = this.getTree();
            if (tree != null) {
                tree.setModel(modello);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    public JTree getTree() {
        return tree;
    }


    private void setTree(JTree tree) {
        this.tree = tree;
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    private void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    private String getTitoloFinestra() {
        return titoloFinestra;
    }


    protected void setTitoloFinestra(String titoloFinestra) {
        this.titoloFinestra = titoloFinestra;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che possono essere lanciati dall'albero<br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        datiModificati(AlberoDatiModLis.class,
                AlberoDatiModEve.class,
                AlberoDatiModAz.class,
                "alberoDatiModAz"),
        selezioneModificata(AlberoSelModLis.class,
                AlberoSelModEve.class,
                AlberoSelModAz.class,
                "alberoSelModAz");

        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento classe
         * @param azione classe
         * @param metodo nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe


}// fine della classe
