/**
 * Title:        LibResultSet.java
 * Package:      it.algos.base.libreria
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2003 alle 15.54
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.libreria;

import it.algos.base.errore.Errore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe astratta e' responsabile di:<br> A - Raccogliere metodi di utilita' per i ResultSet
 * <br> B - Usa solo metodi statici
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2003 ore 15.54
 */
public abstract class LibResultSet {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br> Indispensabile anche se non viene utilizzato (anche
     * solo per compilazione in sviluppo) <br> Rimanda al costruttore completo utilizzando eventuali
     * valori di default
     */
    public LibResultSet() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore base */
    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------


    /**
     * Ritorna il numero di righe presenti in un ResultSet
     *
     * @param rs il ResultSet
     *
     * @return il numero di righe presenti
     */
    public static int quanteRighe(ResultSet rs) {
        /** variabili e costanti locali di lavoro */
        int quantiRecords = 0;
        int oldCursore = 0;

        try {    // prova ad eseguire il codice
            oldCursore = rs.getRow();  // memorizza la posizione del cursore
            rs.last();             // si sposta alla ultima riga
            quantiRecords = rs.getRow();   // legge quante righe ci sono

            // Se il ResultSet non è vuoto, si riposiziona nella posizione originaria
            if (oldCursore > 0) {
                rs.absolute(oldCursore);
            } /* fine del blocco if */

        } catch (SQLException unErrore) {    // intercetta l'errore

            /**
             * fino al 21-03-08 l'errore era disabilitato
             * il commento alla riga era:
             * // niente errore, il RS potrebbe essere chiuso
             * questo nascondeva eventuali problemi
             * lo riabilito oggi, vediamo come va
             */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return quantiRecords;
    } /* fine del metodo */


    /**
     * Ritorna il numero di colonne presenti in un ResultSet
     *
     * @param rs il ResultSet
     *
     * @return il numero di colonne presenti
     */
    public static int quanteColonne(ResultSet rs) {
        /** variabili di lavoro */
        int unNumeroColonne = 0;

        try {                                   // prova ad eseguire il codice
            unNumeroColonne = rs.getMetaData().getColumnCount();
        } catch (Exception unErrore) {           // intercetta l'errore
            /**
             * fino al 21-03-08 l'errore era disabilitato
             * il commento alla riga era:
             * // niente errore, il RS potrebbe essere chiuso
             * questo nascondeva eventuali problemi
             * lo riabilito oggi, vediamo come va
             */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return unNumeroColonne;
    } /* fine del metodo */


    /**
     * Ritorna il nome di una colonna in un ResultSet
     *
     * @param rs il ResultSet
     * @param numCol il numero della colonna (1 per la prima)
     *
     * @return il nome della colonna
     */
    public static String nomeColonna(ResultSet rs, int numCol) {
        /** variabili di lavoro */
        String unNomeColonna = "";

        try {                                   // prova ad eseguire il codice
            unNomeColonna = rs.getMetaData().getColumnName(numCol);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return unNomeColonna;
    } /* fine del metodo */


    /**
     * Restituisce il valore di una cella del ResultSet
     *
     * @param rs il ResultSet
     * @param row l'indice della riga della quale recuperare il valore (prima riga = 1)
     * @param col l'indice della colonna della quale recuperare il valore (prima colonna = 1)
     *
     * @return il valore della cella (null se la cella non esiste)
     */
    public static Object getValoreCella(ResultSet rs, int row, int col) {

        /** variabili e costanti locali di lavoro */
        boolean continua = true;
        Object unValore = null;
        int quanteRighe = 0;
        int unNumeroColonne = 0;

        // verifica se la riga richiesta esiste nel ResultSet
        if (continua) {
            quanteRighe = LibResultSet.quanteRighe(rs);
            if (quanteRighe < row) {
                continua = false;
            } /* fine del blocco if */
        } /* fine del blocco if */

        // verifica se la colonna richiesta esiste nel ResultSet
        if (continua) {
            try {                                   // prova ad eseguire il codice
                unNumeroColonne = rs.getMetaData().getColumnCount();
                if (col > unNumeroColonne) {
                    continua = false;
                } /* fine del blocco if */
            } catch (Exception unErrore) {           // intercetta l'errore
                new Errore(unErrore, "verifica esistenza colonna");
                continua = false;
            } /* fine del blocco try-catch */

        } /* fine del blocco if */

        /** Posiziona il cursore sulla riga richiesta e recupera
         *  il valore della colonna richiesta */
        if (continua) {
            try {                                   // prova ad eseguire il codice
                rs.absolute(row);
                unValore = rs.getObject(col);
            } catch (Exception unErrore) {           // intercetta l'errore
                /** messaggio di errore */
                new Errore(unErrore, "posizionamento cursore");
            } /* fine del blocco try-catch */

        } /* fine del blocco if */

        return unValore;
    } /* fine del metodo */


    /**
     * Restituisce i valori di alcune colonne per una riga del ResultSet.
     *
     * @param rs il ResultSet
     * @param row il numero di riga per la quale recuperare i valori (1 per la prima)
     * @param colonne un array di indici colonne per i quali restituire i valori
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public static ArrayList getValoriRiga(ResultSet rs, int row, int[] colonne) {

        /** variabili e costanti locali di lavoro */
        ArrayList valori = new ArrayList();
        int idxCol = 0;
        Object valore = null;

        /** spazzola l'elenco colonne e aggiunge i valori */
        for (int k = 0; k < colonne.length; k++) {
            idxCol = colonne[k];
            valore = getValoreCella(rs, row, idxCol);
            valori.add(valore);
        } /* fine del blocco for */

        return valori;
    } /* fine del metodo */


    /**
     * Restituisce i valori di tutte le colonne per una riga del ResultSet.
     *
     * @param rs il ResultSet
     * @param row il numero di riga per la quale recuperare i valori (1 per la prima)
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public static ArrayList getValoriRiga(ResultSet rs, int row) {

        /** variabili e costanti locali di lavoro */
        ArrayList valori = new ArrayList();
        Object unValore;
        int quante = 0;

        /** recupera il numero di colonne del ResultSet */
        quante = quanteColonne(rs);

        /** spazzola tutte le colonne della riga e
         *  recupera i valori dei campi */
        for (int k = 1; k <= quante; k++) {
            unValore = getValoreCella(rs, row, k);
            valori.add(unValore);
        } /* fine del blocco for */

        return valori;
    } /* fine del metodo */


    /**
     * Restituisce i valori di tutte le colonne per tutte le righe del ResultSet.
     *
     * @param rs il ResultSet
     *
     * @return una ArrayList contenente gli oggetti valore
     */
    public static ArrayList getValoriRighe(ResultSet rs) {

        /** variabili e costanti locali di lavoro */
        ArrayList unElencoValori = new ArrayList();
        int unNumeroRighe = 0;

        /** recupera il numero di righe del ResultSet */
        unNumeroRighe = quanteRighe(rs);

        /** spazzola tutte le righe
         *  recupera i valori dei campi per ogni riga */
        for (int k = 1; k <= unNumeroRighe; k++) {
            unElencoValori.add(getValoriRiga(rs, k));
        } /* fine del blocco for */

        return unElencoValori;

    } /* fine del metodo */

}// fine della classe


