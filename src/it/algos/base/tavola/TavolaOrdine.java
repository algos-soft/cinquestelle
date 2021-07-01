/**
 * Title:        TavolaOrdine.java
 * Package:      it.algos.base.tavola
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 17 dicembre 2002 alle 18.34
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.tavola;

import it.algos.base.errore.ErroreInizia;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Vector;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di:<br>
 * A - Ordinare una lista secondo i valori di una colonna <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  17 dicembre 2002 ore 18.34
 */
public class TavolaOrdine extends TavolaModelloAstratta {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "TavolaOrdine";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    private int indexes[];

    private Vector sortingColumns = new Vector();

    private boolean ascending = true;

    private int compares;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public TavolaOrdine() {
        indexes = new int[0]; // for consistency
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unModello modello di dati
     */
    public TavolaOrdine(TableModel unModello) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.setModel(unModello);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new ErroreInizia(NOME_CLASSE, unErrore);
        } /* fine del blocco try-catch */

        /** Operazioni alla partenza ed eventuale interfaccia utente */
        this.partenza();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     */
    private void partenza() {
    } /* fine del metodo partenza */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    public int compareRowsByColumn(int row1, int row2, int column) {
        Class type = unModello.getColumnClass(column);
        TableModel data = unModello;

        // Check for nulls.

        Object o1 = data.getValueAt(row1, column);
        Object o2 = data.getValueAt(row2, column);

        // If both values are null, return 0.
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) { // Define null less than everything.
            return -1;
        } else if (o2 == null) {
            return 1;
        }

        /*
         * We copy all returned values from the getValue call in case
         * an optimised model is reusing one object to return many
         * values.  The Number subclasses in the JDK are immutable and
         * so will not be used in this way but other subclasses of
         * Number might want to do this to save space and avoid
         * unnecessary heap allocation.
         */

        if (type.getSuperclass() == java.lang.Number.class) {
            Number n1 = (Number)data.getValueAt(row1, column);
            double d1 = n1.doubleValue();
            Number n2 = (Number)data.getValueAt(row2, column);
            double d2 = n2.doubleValue();

            if (d1 < d2) {
                return -1;
            } else if (d1 > d2) {
                return 1;
            } else {
                return 0;
            }
        } else if (type == Date.class) {
            Date d1 = (Date)data.getValueAt(row1, column);
            long n1 = d1.getTime();
            Date d2 = (Date)data.getValueAt(row2, column);
            long n2 = d2.getTime();

            if (n1 < n2) {
                return -1;
            } else if (n1 > n2) {
                return 1;
            } else {
                return 0;
            }
        } else if (type == String.class) {
            String s1 = (String)data.getValueAt(row1, column);
            String s2 = (String)data.getValueAt(row2, column);
            int result = s1.compareTo(s2);

            if (result < 0) {
                return -1;
            } else if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        } else if (type == Boolean.class) {
            Boolean bool1 = (Boolean)data.getValueAt(row1, column);
            boolean b1 = bool1.booleanValue();
            Boolean bool2 = (Boolean)data.getValueAt(row2, column);
            boolean b2 = bool2.booleanValue();

            if (b1 == b2) {
                return 0;
            } else if (b1) { // Define false < true
                return 1;
            } else {
                return -1;
            }
        } else {
            Object v1 = data.getValueAt(row1, column);
            String s1 = v1.toString();
            Object v2 = data.getValueAt(row2, column);
            String s2 = v2.toString();
            int result = s1.compareTo(s2);

            if (result < 0) {
                return -1;
            } else if (result > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    public int compare(int row1, int row2) {
        compares++;
        for (int level = 0; level < sortingColumns.size(); level++) {
            Integer column = (Integer)sortingColumns.elementAt(level);
            int result = compareRowsByColumn(row1, row2, column.intValue());
            if (result != 0) {
                return ascending ? result : -result;
            }
        }
        return 0;
    }


    public void reallocateIndexes() {
        int rowCount = unModello.getRowCount();

        // Set up a new array of indexes with the right number of elements
        // for the new data model.
        indexes = new int[rowCount];

        // Initialise with the identity mapping.
        for (int row = 0; row < rowCount; row++) {
            indexes[row] = row;
        }
    }


    public void tableChanged(TableModelEvent e) {
        //System.out.println("Sorter: tableChanged");
        reallocateIndexes();

        super.tableChanged(e);
    }


    public void checkModel() {
        if (indexes.length != unModello.getRowCount()) {
            System.err.println("Sorter not informed of a change in model.");
        }
    }


    public void sort(Object sender) {
        checkModel();

        compares = 0;
        // n2sort();
        // qsort(0, indexes.length-1);
        shuttlesort((int[])indexes.clone(), indexes, 0, indexes.length);
        //System.out.println("Compares: "+compares);
    }


    public void n2sort() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = i + 1; j < getRowCount(); j++) {
                if (compare(indexes[i], indexes[j]) == -1) {
                    swap(i, j);
                }
            }
        }
    }


    // This is a home-grown implementation which we have not had time
    // to research - it may perform poorly in some circumstances. It
    // requires twice the space of an in-place algorithm and makes
    // NlogN assigments shuttling the values between the two
    // arrays. The number of compares appears to vary between N-1 and
    // NlogN depending on the initial order but the main reason for
    // using it here is that, unlike qsort, it is stable.
    public void shuttlesort(int from[], int to[], int low, int high) {
        if (high - low < 2) {
            return;
        }
        int middle = (low + high) / 2;
        shuttlesort(to, from, low, middle);
        shuttlesort(to, from, middle, high);

        int p = low;
        int q = middle;

        /* This is an optional short-cut; at each recursive call,
        check to see if the elements in this subset are already
        ordered.  If so, no further comparisons are needed; the
        sub-array can just be copied.  The array must be copied rather
        than assigned otherwise sister calls in the recursion might
        get out of sinc.  When the number of elements is three they
        are partitioned so that the first set, [low, mid), has one
        element and and the second, [mid, high), has two. We skip the
        optimisation when the number of elements is three or less as
        the first compare in the normal merge will produce the same
        sequence of steps. This optimisation seems to be worthwhile
        for partially ordered lists but some analysis is needed to
        find out how the performance drops to Nlog(N) as the initial
        order diminishes - it may drop very quickly.  */

        if (high - low >= 4 && compare(from[middle - 1], from[middle]) <= 0) {
            for (int i = low; i < high; i++) {
                to[i] = from[i];
            }
            return;
        }

        // A normal merge.

        for (int i = low; i < high; i++) {
            if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
                to[i] = from[p++];
            } else {
                to[i] = from[q++];
            }
        }
    }


    public void swap(int i, int j) {
        int tmp = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = tmp;
    }


    public void sortByColumn(int column) {
        sortByColumn(column, true);
    }


    public void sortByColumn(int column, boolean ascending) {
        this.ascending = ascending;
        sortingColumns.removeAllElements();
        sortingColumns.addElement(new Integer(column));
        sort(this);
        super.tableChanged(new TableModelEvent(this));
    }

    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    // The mapping only affects the contents of the data rows.
    // Pass all requests to these rows through the mapping array: "indexes".


    public Object getValueAt(int aRow, int aColumn) {
        checkModel();
        return unModello.getValueAt(indexes[aRow], aColumn);
    }


    public void setValueAt(Object aValue, int aRow, int aColumn) {
        checkModel();
        unModello.setValueAt(aValue, indexes[aRow], aColumn);
    }


    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setModel(TableModel unModello) {
        super.setModel(unModello);
        reallocateIndexes();
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    // There is no-where else to put this.
    // Add a mouse listener to the Table to trigger a table sort
    // when a column heading is clicked in the JTable.
    public void addMouseListenerToHeaderInTable(JTable table) {
        final TavolaOrdine unaTavolaOrdine = this;
        final JTable tableView = table;
        tableView.setColumnSelectionAllowed(false);
        MouseAdapter listMouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TableColumnModel columnModel = tableView.getColumnModel();
                int viewColumn = columnModel.getColumnIndexAtX(e.getX());
                int column = tableView.convertColumnIndexToModel(viewColumn);
                if (e.getClickCount() == 1 && column != -1) {
                    //System.out.println("Sorting ...");
                    int shiftPressed = e.getModifiers() & InputEvent.SHIFT_MASK;
                    boolean ascending = (shiftPressed == 0);
//                    unaTavolaOrdine.sortByColumn(column, ascending);
                    Object pippo = tableView;
//        ListaBase alfa = (ListaBase)pippo;
//        alfa.ordina(viewColumn);
                }
            }
        };  // fine di MouseAdapter
        JTableHeader th = tableView.getTableHeader();
        th.addMouseListener(listMouseListener);
    }
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.tavola.TavolaOrdine.java
//-----------------------------------------------------------------------------

