/**
 * Title:        MatriceBase.java
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

import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibResultSet;
import it.algos.base.wrapper.IntBool;
import it.algos.base.wrapper.IntBoolBool;

import java.sql.ResultSet;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire un modello dati in memoria <br>
 * B - Mantiene una matrice bidimensionale di oggetti <br>
 * C - La matrice 'riempie' tutte le celle; anche con valori null;
 * questo vuol dire che tutte le righe hanno lo stesso numero di elementi
 * e, ovviamente, tutte le colonne hanno lo stesso numero di righe;
 * naturalmente il numero di colonne ed il numero di righe sono
 * indipendenti tra di loro e possono (tipicamente) esseri diversi <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @author gac
 * @version 1.0  /  17 ottobre 2003 ore 15.35
 */
public class MatriceBase extends MatriceAstratta implements Matrice {

    /**
     * dati interni della matrice
     * per convenzione, prima le righe e poi le colonne
     */
    protected Object[][] unaMatriceDati = null;

    /**
     * titoli delle colonne
     */
    private String[] titoliColonne = null;

    private int righe = 0;

    private int colonne = 0;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public MatriceBase() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore */


    /**
     * Costruttore completo <br>
     */
    public MatriceBase(ResultSet unResultSet) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia(unResultSet);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore completo <br>
     */
    public MatriceBase(int righe, int colonne) {
        /* rimanda al costruttore della superclasse */
        super();

        this.righe = righe;
        this.colonne = colonne;

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia(null);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia(ResultSet unResultSet) throws Exception {
        if (unResultSet != null) {
            /* Crea la matrice coi dati del ResultSet */
            this.creaMatriceDaResultSet(unResultSet);
        } else {
            /* Crea la matrice vuota */
            this.unaMatriceDati = new Object[righe][colonne];
        } /* fine del blocco if/else */

    } /* fine del metodo inizia */


    /**
     * Crea la matrice coi dati del ResultSet
     */
    private void creaMatriceDaResultSet(ResultSet unResultSet) {

        try {    // prova ad eseguire il codice

            /* calcola il numero di righe del ResultSet */
            this.righe = LibResultSet.quanteRighe(unResultSet);

            /* recupera il numero di colonne */
            this.colonne = LibResultSet.quanteColonne(unResultSet);

            /* crea la matrice */
            this.unaMatriceDati = new Object[righe][colonne];

            /* regola la matrice coi dati del resultSet */
            for (int k = 0; k < righe; k++) {

                unResultSet.absolute(k + 1);

                for (int j = 0; j < colonne; j++) {
                    this.unaMatriceDati[k][j] = unResultSet.getObject(j + 1);
                } /* fine del blocco for */

            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Aggiunge una riga alla matrice esistente <br>
     * <p/>
     * calcola la dimensione attuale <br>
     * crea una matrice vuota dimensionata con una riga in piu' (colonne uguali) <br>
     * ricopia la matrice vecchia nella nuova <br>
     * aggiunge la nuova riga <br>
     * regola il riferimento della variabile alla nuova matrice  <br>
     * la lunghezza della riga in ingresso DEVE essere uguale al numero di
     * colonne della matrice; se e' piu' lunga, i valori eccedenti vengono tralasciati;
     * se e' piu' corta i valori mancanti restano a null <br>
     */
    private boolean aggiungeRiga(Object[] unaRigaDati) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        int dimRigheNew = 0;
        Object[][] nuovaMatrice = null;

        try { // prova ad eseguire il codice

            /* controlla che la riga di dati abbia un numero di elementi
             * pari al numero di colonne della matrice */
            if (unaRigaDati.length != this.colonne) {
                String testo = "";
                testo += "Numero di elementi (" + unaRigaDati.length + ") ";
                testo += "diverso dal numero di colonne (" + this.colonne + ")";
                throw new Exception(testo);
            }// fine del blocco if

            /* crea una matrice vuota dimensionata con una riga in piu' (colonne uguali) */
            dimRigheNew = this.righe + 1;
            nuovaMatrice = new Object[dimRigheNew][this.colonne];

            /* ricopia la matrice vecchia nella nuova */
            for (int k = 0; k < righe; k++) {
                for (int j = 0; j < colonne; j++) {
                    nuovaMatrice[k][j] = this.unaMatriceDati[k][j];
                } /* fine del blocco for */
            } /* fine del blocco for */

            /* aggiunge la nuova riga */
            for (int j = 0; j < unaRigaDati.length; j++) {

                nuovaMatrice[dimRigheNew - 1][j] = unaRigaDati[j];
            } /* fine del blocco for */

            this.righe++;

            /* regola il riferimento della variabile alla nuova matrice */
            this.setDati(nuovaMatrice);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Aggiunge una colonna alla matrice esistente <br>
     * <p/>
     * agiunge la colonna solo in testa //@todo provvisorio
     * calcola la dimensione attuale <br>
     * crea una matrice vuota dimensionata con una colonna in piu' <br>
     * ricopia la matrice vecchia nella nuova <br>
     * aggiunge la nuova colonna <br>
     * regola il riferimento della variabile alla nuova matrice  <br>
     */
    public boolean addColonna(Object[] unaColonnaDati, int posizione) {
        /* variabili e costanti locali di lavoro */
        boolean eseguito = false;
        int dimColonneNew = 0;
        Object[][] nuovaMatrice = null;

        try { // prova ad eseguire il codice
            /* crea una matrice vuota dimensionata con una colonna in piu' */
            dimColonneNew = this.colonne + 1;
            nuovaMatrice = new Object[this.righe][dimColonneNew];

            /* ricopia la matrice vecchia nella nuova */
            for (int k = 1; k < colonne + 1; k++) {
                for (int j = 0; j < righe; j++) {
                    nuovaMatrice[j][k] = this.unaMatriceDati[j][k - 1];
                } /* fine del blocco for */
            } /* fine del blocco for */

            /* aggiunge la nuova riga */
            for (int k = 0; k < unaColonnaDati.length; k++) {

                nuovaMatrice[k][0] = unaColonnaDati[k];
            } /* fine del blocco for */

            this.colonne++;

            /* regola il riferimento della variabile alla nuova matrice */
            this.setDati(nuovaMatrice);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return eseguito;
    } /* fine del metodo */


    /**
     * Aggiunge una colonna alla matrice esistente <br>
     * <p/>
     * agiunge la colonna come prima colonna di sinistra
     */
    public boolean addColonna(Object[] unaColonnaDati) {

        /* valore di ritorno */
        return addColonna(unaColonnaDati, 0);
    } /* fine del metodo */


    /**
     * Aggiunge una colonna vuota alla matrice esistente <br>
     * <p/>
     * agiunge la colonna come ultima colonna
     */
    public void addColonna() {
        /* variabili e costanti locali di lavoro */
        int dimColonneNew = 0;
        Object[][] nuovaMatrice = null;

        try { // prova ad eseguire il codice
            /* crea una matrice vuota dimensionata con una colonna in piu' */
            dimColonneNew = this.colonne + 1;
            nuovaMatrice = new Object[this.righe][dimColonneNew];

            /* ricopia la matrice vecchia nella nuova */
            for (int k = 1; k < colonne + 1; k++) {
                for (int j = 0; j < righe; j++) {
                    nuovaMatrice[j][k] = this.unaMatriceDati[j][k - 1];
                } /* fine del blocco for */
            } /* fine del blocco for */

            /* regola il riferimento della variabile alla nuova matrice */
            this.setDati(nuovaMatrice);

            /* incrementa di 1 il numero delle colonne */
            this.colonne = dimColonneNew;


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Inserisce una colonna vuota nella matrice esistente <br>
     * La colonna viene inserita alla posizione specificata (0 per la prima)<br>
     * Le eventuali colonne successive vengono fatte scorrere.<br>
     *
     * @param posizione la posizione di inserimento
     *
     * @return true se riuscito
     */
    public boolean inserisciColonna(int posizione) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        int dimColonneNew = 0;
        Object[][] nuovaMatrice = null;

        try { // prova ad eseguire il codice

            /* controlla che la posizione sia accettabile */
            if (riuscito) {
                if (posizione > this.getNumeroColonne()) {
                    riuscito = false;
                    throw new Exception("Posizione " + posizione + " non accettabile");
                }// fine del blocco if
            }// fine del blocco if

            /* crea una matrice vuota dimensionata con una colonna in piu' */
            if (riuscito) {
                dimColonneNew = this.colonne + 1;
                nuovaMatrice = new Object[this.righe][dimColonneNew];
            }// fine del blocco if

            /*
             * ricopia la matrice vecchia nella nuova
             * saltando la colonna da inserire
             */
            int colTarget = 0;
            if (riuscito) {
                for (int k = 0; k < colonne; k++) {
                    colTarget = k;
                    if (colTarget >= posizione) {
                        colTarget++;
                    }// fine del blocco if
                    for (int j = 0; j < righe; j++) {
                        nuovaMatrice[j][colTarget] = this.unaMatriceDati[j][k];
                    } /* fine del blocco for */

                } /* fine del blocco for */
            }// fine del blocco if

            /* regola il riferimento della variabile alla nuova matrice */
            this.setDati(nuovaMatrice);

            /* regola il nuovo numero delle colonne */
            this.colonne = dimColonneNew;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    /**
     * recupera il valore intero di una posizione della matrice
     */
    public int getInt(int riga, int colonna) {
        /** variabili e costanti locali di lavoro */
        int intero = 0;
        Object valore = null;

        try {    // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);

            if (valore != null) {
                if (valore instanceof Integer) {
                    intero = ((Integer)valore).intValue();
                } /* fine del blocco if */
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return intero;

    } /* fine del metodo */


    /**
     * recupera il valore booleano di una posizione della matrice
     */
    public boolean getBool(int riga, int colonna) {
        /** variabili e costanti locali di lavoro */
        boolean booleano = false;
        Object valore = null;

        try {    // prova ad eseguire il codice
            valore = this.getValueAt(riga, colonna);

            if (valore != null) {
                if (valore instanceof Boolean) {
                    booleano = ((Boolean)valore).booleanValue();
                } /* fine del blocco if */
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return booleano;

    } /* fine del metodo */


    /**
     * recupera il numero di occorrenze di un valore, in una colonna
     */
    protected int recupera(int valore, int colonna) {
        /** variabili e costanti locali di lavoro */
        int occorrenze = 0;
        int numero = 0;

        try {    // prova ad eseguire il codice

            for (int k = 0; k < this.getNumeroRighe(); k++) {

                numero = this.getInt(k, colonna);

                if (numero == valore) {
                    occorrenze++;
                } /* fine del blocco if */

            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return occorrenze;

    } /* fine del metodo */


    /**
     * Aggiunge una riga di dati alla matrice
     */
    public void addRiga(Object[] unaRigaDati) {
        this.aggiungeRiga(unaRigaDati);
    } /* fine del metodo */


    /**
     * Aggiunge una riga di dati alla matrice
     */
    public void addRiga(AbstractList unaRigaDati) {
        this.aggiungeRiga(super.listaVersoMatrice(unaRigaDati));
    } /* fine del metodo */

    /**
     * Elimina tutte le righe
     */
    public void removeRighe() {
        this.unaMatriceDati = new Object[0][colonne];
    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setDati(Object[][] unaMatriceDati) {

        try { // prova ad eseguire il codice
            if (unaMatriceDati != null) {
                righe = 0;
                colonne = 0;
                this.unaMatriceDati = unaMatriceDati;
                if (unaMatriceDati.length > 0) {
                    righe = unaMatriceDati.length;
                    colonne = unaMatriceDati[0].length;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo setter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public int getNumeroRighe() {
        return this.righe;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public int getNumeroColonne() {
        return this.colonne;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Object[][] getDati() {
        return this.unaMatriceDati;
    } /* fine del metodo getter */


    /**
     * Ritorna il valore di una cella.<br>
     *
     * @param riga numero di riga dell'oggetto richiesto
     * @param colonna numero di colonna dell'oggetto richiesto
     *
     * @return l'oggetto della cella specificata
     */
    public Object getValueAt(int riga, int colonna) {
        return this.unaMatriceDati[riga][colonna];
    } /* fine del metodo getter */


    /**
     * Regola il valore di una cella.<br>
     *
     * @param riga numero di riga dell'oggetto richiesto
     * @param colonna numero di colonna dell'oggetto richiesto
     * @param valore il valore da assegnare
     */
    public void setValueAt(int riga, int colonna, Object valore) {
        this.unaMatriceDati[riga][colonna] = valore;
    } /* fine del metodo getter */


    /**
     * Restituisce una colonna della matrice
     *
     * @param indice l'indice della colonna (0 per la prima)
     *
     * @return una lista di valori Object
     */
    public ArrayList getColonna(int indice) {
        /* variabili e costanti locali di lavoro */
        ArrayList valori = null;
        Object unValore = null;

        try {    // prova ad eseguire il codice
            valori = new ArrayList();
            for (int k = 0; k < unaMatriceDati.length; k++) {
                unValore = unaMatriceDati[k][indice];
                valori.add(unValore);
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        return valori;
    } /* fine del metodo getter */


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
    public void sort(IntBoolBool[] colonne) {
        Comparator comp = null;
        comp = new Comparatore2D(colonne);
        this.sort(comp);
    } // fine del metodo


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
    public void sort(IntBool[] colonne) {
        Comparator comp = null;
        comp = new Comparatore2D(colonne);
        this.sort(comp);
    }// fine del metodo costruttore completo


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
    public void sort(int[] colonne) {
        Comparator comp = null;
        comp = new Comparatore2D(colonne);
        this.sort(comp);
    }// fine del metodo costruttore completo


    /**
     * Ordina la Matrice su una colonna in ordine ascendente. <br>
     *
     * @param colonna l'indice della colonna sulla quale ordinare.
     */
    public void sort(int colonna) {
        this.sort(colonna, true);
    } // fine del metodo


    /**
     * Ordina la Matrice su una colonna con un verso. <p>
     *
     * @param colonna l'indice della colonna sulla quale ordinare (0 la prima)
     * @param verso true per ascendente, false per discendente
     */
    public void sort(int colonna, boolean verso) {
        Comparator comp = null;
        try {    // prova ad eseguire il codice
            comp = new Comparatore2D(colonna, verso);
            Arrays.sort(unaMatriceDati, comp);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Ordina la Matrice usando un Comparatore. <p>
     *
     * @param comp il comparatore da utilizzare
     */
    private void sort(Comparator comp) {
        Arrays.sort(unaMatriceDati, comp);
    } // fine del metodo


    /**
     * Pulisce la matrice.
     */
    public void clear() {
        this.unaMatriceDati = new Object[0][0];
    } // fine del metodo


    public String[] getTitoliColonne() {
        return titoliColonne;
    }


    public void setTitoliColonne(String[] titoliColonne) {
        this.titoliColonne = titoliColonne;
    }
}// fine della classe
