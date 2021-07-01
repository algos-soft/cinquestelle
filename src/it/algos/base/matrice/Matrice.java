/**
 * Title:        Matrice.java
 * Package:      it.algos.base.libreria
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 17 ottobre 2003 alle 15.35
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.matrice;

import it.algos.base.wrapper.IntBool;
import it.algos.base.wrapper.IntBoolBool;

import java.util.AbstractList;
import java.util.ArrayList;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Gestire una matrice <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @author gac
 * @version 1.0  /  17 ottobre 2003 ore 15.35
 */
public interface Matrice {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    public abstract void addRiga(Object[] unaRigaDati);


    public abstract void addRiga(AbstractList unaRigaDati);


    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    public abstract void setDati(Object[][] unaMatriceDati);


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    public abstract int getNumeroRighe();


    public abstract int getNumeroColonne();


    public abstract Object[][] getDati();


    public abstract Object getValueAt(int riga, int colonna);


    /**
     * Regola il valore di una cella.<br>
     *
     * @param riga numero di riga dell'oggetto richiesto
     * @param colonna numero di colonna dell'oggetto richiesto
     * @param valore il valore da assegnare
     */
    public abstract void setValueAt(int riga, int colonna, Object valore);


    public abstract boolean addColonna(Object[] unaColonnaDati, int posizione);


    public abstract boolean addColonna(Object[] unaColonnaDati);


    /**
     * Aggiunge una colonna vuota alla matrice esistente <br>
     * <p/>
     * aggiunge la colonna come ultima colonna
     */
    public abstract void addColonna();


    /**
     * Inserisce una colonna vuota nella matrice esistente <br>
     * La colonna viene inserita alla posizione specificata (0 per la prima)<br>
     * Le colonne successive vengono fatte scorrere.<br>
     *
     * @param posizione la posizione di inserimento
     *
     * @return true se riuscito
     */
    public boolean inserisciColonna(int posizione);


    /**
     * Restituisce una colonna della matrice
     *
     * @param indice l'indice della colonna (0 per la prima)
     *
     * @return una lista di valori Object
     */
    public ArrayList getColonna(int indice);


    /**
     * Ordina la Matrice su una serie di colonne. <p>
     *
     * @param colonne elenco degli indici delle colonne di comparazione
     * Array di oggetti IntBoolBool, dove:
     * - l'int rappresenta l'indice della colonna da comparare
     * (0 per la prima)<br>
     * - il primo boolean il verso di ordinamento della colonna
     * (true = ascendente, false = discendente)
     * - il secondo boolean l'opzione case-sensitive (solo per le stringhe)
     * (true = case sensitive, false = case insensitive)<br>
     */
    public void sort(IntBoolBool[] colonne);


    /**
     * Ordina la Matrice su una serie di colonne con verso. <p>
     *
     * @param colonne elenco degli indici delle colonne di comparazione
     * Array di oggetti IntBool, dove:
     * - l'int rappresenta l'indice della colonna da comparare
     * (0 per la prima)<br>
     * - il boolean il verso di ordinamento della colonna
     * (true = ascendente, false = discendente)<br>
     * Il flag case-sensitive e' impostato di default a true.
     */
    public void sort(IntBool[] colonne);


    /**
     * Ordina la Matrice su una serie di colonne. <p>
     *
     * @param colonne elenco degli indici delle colonne di comparazione
     * Array di oggetti IntBool, dove:
     * - l'int rappresenta l'indice della colonna da comparare
     * (0 per la prima)<br>
     * Il verso delle colonne e' impostato di default a ascendente.<br>
     * Il flag case-sensitive e' impostato di default a true.<br>
     */
    public void sort(int[] colonne);


    /**
     * Ordina la Matrice su una colonna in ordine ascendente. <br>
     *
     * @param colonna l'indice della colonna sulla quale ordinare.
     */
    public void sort(int colonna);


    /**
     * Ordina la Matrice su una colonna con un verso. <br>
     *
     * @param colonna l'indice della colonna sulla quale ordinare (0 la prima)
     * @param verso true per ascendente, false per discendente
     */
    public void sort(int colonna, boolean verso);

    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.elenco.Matrice.java
//-----------------------------------------------------------------------------

