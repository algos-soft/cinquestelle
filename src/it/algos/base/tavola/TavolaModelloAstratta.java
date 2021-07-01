/**
 * Title:        TavolaModelloAstratta.java
 * Package:      it.algos.base.tavola
 * Description:  Abstract Data Types per il modello della tavola
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 17 dicembre 2002 alle 10.30
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.tavola;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Gestire il modello dati per una tavola a video <br>
 * B - Di default, implementa tutti i metodi della interfaccia TableModel,
 * invocando il metodo sovrascritto del modello <br>
 * <p/>
 * In a chain of data manipulators some behaviour is common <br>
 * TavolaModelloAstratta provides most of this behavour and can be subclassed
 * by filters that only need to override a handful of specific methods <br>
 * TavolaModelloAstratta implements TableModel by routing all requests to its
 * model, and TableModelListener by routing all events to its listeners <br>
 * Inserting a TavolaModelloAstratta which has not been subclassed into a chain
 * sof table filters hould have no effect <br>
 * <p/>
 * original version 1.4 12/17/97
 * original author Philip Milne
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  17 dicembre 2002 ore 10.30
 */
public abstract class TavolaModelloAstratta extends AbstractTableModel implements TableModelListener {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "TavolaAstratta";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * modello dei dati
     */
    protected TableModel unModello = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore completo senza parametri <br>
     * Non serve a nulla, ma lo metto per completezza <br>
     */
    public TavolaModelloAstratta() {
        /** rimanda al costruttore della superclasse */
        super();

        /** se non viene modificato col metodo setter, tiene questo oggetto
         *  della sottoclasse come modello dei dati*/
        unModello = this;
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    /**
     * getRowCount, from TableModel
     *
     * @return il numero di righe del modello
     */
    public int getRowCount() {
        return (unModello == null) ? 0 : unModello.getRowCount();
    } /* fine del metodo */


    /**
     * getColumnCount, from TableModel
     *
     * @return il numero di colonne del modello
     */
    public int getColumnCount() {
        return (unModello == null) ? 0 : unModello.getColumnCount();
    } /* fine del metodo */


    /**
     * getValueAt, from TableModel
     *
     * @param riga numero di riga dell'oggetto richiesto
     * @param colonna numero di colonna dell'oggetto richiesto
     *
     * @return l'oggetto della cella specificata
     */
    public Object getValueAt(int riga, int colonna) {
        return unModello.getValueAt(riga, colonna);
    } /* fine del metodo */


    /**
     * setValueAt, from TableModel
     *
     * @param valore nuovo oggetto
     * @param riga numero di riga in cui posizionare il nuovo oggetto
     * @param colonna numero di colonna in cui posizionare il nuovo oggetto
     */
    public void setValueAt(Object unValore, int unaRiga, int unaColonna) {
        unModello.setValueAt(unValore, unaRiga, unaColonna);
    } /* fine del metodo */


    /**
     * getColumnName, from TableModel
     *
     * @param colonna indice della colonna di cui si vuole il nome
     *
     * @return nome della colonna specificata
     */
    public String getColumnName(int unaColonna) {
        return unModello.getColumnName(unaColonna);
    } /* fine del metodo */


    /**
     * getColumnClass, from TableModel
     *
     * @param colonna indice della colonna di cui si vuole l'oggetto
     *
     * @return classe dei dati della colonna specificata
     */
    public Class getColumnClass(int unaColonna) {
        return unModello.getColumnClass(unaColonna);
    } /* fine del metodo */


    /**
     * isCellEditable, from TableModel
     *
     * @param riga numero di riga di cui si chiede la modificabilita'
     * @param colonna numero di colonna di cui si chiede la modificabilita'
     *
     * @return vero se la cella e' modificabile, falso altrimenti
     */
    public boolean isCellEditable(int unaRiga, int unaColonna) {
        return unModello.isCellEditable(unaRiga, unaColonna);
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setModel(TableModel unModello) {
        this.unModello = unModello;
        unModello.addTableModelListener(this);
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public TableModel getModel() {
        return this.unModello;
    } /* fine del metodo getter */


    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    /**
     * implementazione della interfaccia TableModelListener
     */
    public void tableChanged(TableModelEvent unEvento) {
        /** di default passa avanti tutti gli eventi */
        super.fireTableChanged(unEvento);
    } /* fine del metodo */
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.tavola.TavolaModelloAstratta.java
//-----------------------------------------------------------------------------

