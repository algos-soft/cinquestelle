/**
 * Title:        MatriceDoppia.java
 * Package:      it.algos.base.matrice
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi
 * @version 1.0  /
 * Creato:       il 12 novembre 2003 alle 9.05
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.matrice;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;

import java.util.AbstractList;
import java.util.ArrayList;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Costruisce un tipo di dati (record in Pascal) <br>
 * B - Mantiene una matrice di interi ed una di oggetti (di solito stringhe) <br>
 *
 * @author __AUTOREA__
 * @author __AUTOREB__
 * @author __AUTOREC__
 * @author alex
 * @version 1.0  /  12 novembre 2003 ore 9.05
 */
public final class MatriceDoppia extends MatriceAstratta {

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
    /**
     * matrice di oggetti (di solito codici della tavola)
     */
    private Object[] codici = null;

    /**
     * matrice di oggetti (di solito valori di tipo stringa)
     */
    private Object[] valori = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public MatriceDoppia() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.regolaMatrici();
    } /* fine del metodo inizia */


    /**
     * Crea le matrici, se non esistono.
     */
    private void regolaMatrici() {
        try { // prova ad eseguire il codice
            /* forza l'esistenza delle matrici */
            if (this.getCodici() == null) {
                this.setCodici(new Object[0]);
                this.setValori(new Object[0]);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * ...
     */
    private ArrayList creaListaCodici() {
        /** variabili e costanti locali di lavoro */
        ArrayList lista = null;

        lista = new ArrayList();

        try {    // prova ad eseguire il codice
            if (codici != null) {
                /* ciclo for */
                for (int k = 0; k < codici.length; k++) {
                    lista.add(codici[k]);
                } /* fine del blocco for */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return lista;
    } /* fine del metodo */


    /**
     * ...
     */
    private ArrayList creaListaValori() {
        /** variabili e costanti locali di lavoro */
        ArrayList lista = null;

        lista = new ArrayList();

        try {    // prova ad eseguire il codice
            if (valori != null) {
                /* ciclo for */
                for (int k = 0; k < valori.length; k++) {
                    lista.add(valori[k]);
                } /* fine del blocco for */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return lista;
    } /* fine del metodo */


    /**
     * Aggiunge un oggetto ad una matrice <br>
     * <p/>
     * calcola la dimensione attuale <br>
     * crea una matrice vuota dimensionata con un valore in piu' <br>
     * ricopia la matrice vecchia nella nuova <br>
     * aggiunge il nuovo valore nella posizione indicata <br>
     *
     * @return la nuova matrice <br>
     */
    private Object[] addOggetto(Object[] matrice, Object unOggetto, int posizione) {
        /** variabili e costanti locali di lavoro */
        int dim = 0;
        Object[] nuovaMatrice = null;

        try {    // prova ad eseguire il codice

            /** calcola la dimensione della nuova matrice */
            dim = matrice.length;
            dim++;

            /** controllo di congruita' del valore di posizione */
            if ((posizione >= 0) && (posizione < dim)) {

                /** crea la nuova matrice */
                nuovaMatrice = new Object[dim];

                /** ricopia la matrice vecchia nella nuova fino alla posizione
                 *  di inserimento del nuovo valore */
                System.arraycopy(matrice, 0, nuovaMatrice, 0, posizione);

                /** regola il nuovo valore */
                nuovaMatrice[posizione] = unOggetto;

                /** ricopia la matrice vecchia nella nuova dalla posizione
                 *  di inserimento del nuovo valore */
                System.arraycopy(matrice,
                        posizione,
                        nuovaMatrice,
                        posizione + 1,
                        dim - posizione - 1);

            } else {
                nuovaMatrice = matrice;
            } /* fine del blocco if/else */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return nuovaMatrice;

    } /* fine del metodo */


    /**
     * Aggiunge un oggetto alla matrice dei valori <br>
     */
    private Object[] addValore(Object unOggetto, int posizione) {
        /** invoca il metodo delegato */
        return this.addOggetto(valori, unOggetto, posizione);
    } /* fine del metodo */


    /**
     * Aggiunge un oggetto alla matrice dei codici <br>
     */
    private Object[] addCodice(Object unOggetto, int posizione) {
        /** invoca il metodo delegato */
        return this.addOggetto(codici, unOggetto, posizione);
    } /* fine del metodo */


    /**
     * Ritorna il codice corrispondente a una posizione specificata
     *
     * @param posizione la posizione nella matrice dei codici (prima = 1)
     *
     * @return unCodice il codice richiesto, -1 se non esistente
     */
    public int getCodiceAt(int posizione) {
        /** variabili e costanti locali di lavoro */
        int unCodice = -1;

        try {                                   // prova ad eseguire il codice
            /* allinea il valore */
            posizione--;

            // Controllo di congruita'
            if ((posizione >= 0) && (posizione < codici.length)) {
                unCodice = ((Integer)codici[posizione]).intValue();
            } /* fine del blocco if */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return unCodice;
    } /* fine del metodo */


    /**
     * Ritorna il valore corrispondente a una posizione specificata
     *
     * @param posizione la posizione nella matrice dei codici (prima = 1)
     *
     * @return il valore richiesto, null se non esistente
     */
    public Object getValoreAt(int posizione) {
        /** variabili e costanti locali di lavoro */
        Object valore = null;

        try {                                   // prova ad eseguire il codice
            /* allinea il valore */
            posizione--;

            // Controllo di congruita'
            if ((posizione >= 0) && (posizione < valori.length)) {
                valore = (valori[posizione]);
            } /* fine del blocco if */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return valore;
    } /* fine del metodo */


    /**
     * Aggiunge un valore ed il corrispondente codice <br>
     * <p/>
     * Vengono incrementate entrambe le matrici <br>
     */
    public void addValoreCodice(Object unValore, Object unCodice, int unaPosizione) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Object[] codici;
        Object[] valori;
        int max;

        try {    // prova ad eseguire il codice
            codici = this.getCodici();
            continua = (codici != null);

            if (continua) {
                valori = this.getValori();

                /* recupera la dimensione della matrice */
                max = valori.length;

                /* Controllo di congruita' della posizione */
                unaPosizione = unaPosizione < 0 ? 0 : unaPosizione;

                /* Controllo di congruita' della posizione */
                unaPosizione = unaPosizione > max ? max : unaPosizione;

                /* Aggiunge un oggetto alla matrice dei valori */
                valori = this.addValore(unValore, unaPosizione);

                /* Aggiunge un nullo alla matrice dei codici */
                codici = this.addCodice(unCodice, unaPosizione);

                this.setCodici(codici);
                this.setValori(valori);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Aggiunge un valore ed il corrispondente codice <br>
     * <p/>
     * Vengono incrementate entrambe le matrici <br>
     */
    public void addValoreCodice(Object unValore, int unCodice, int unaPosizione) {
        /** invoca il metodo sovrascritto di questa classe */
        this.addValoreCodice(unValore, new Integer(unCodice), unaPosizione);
    } /* fine del metodo */


    /**
     * Aggiunge un codice ed il corrispondente valore
     * <p/>
     * L'aggiunta avviene in fondo alla matrice <br>
     * Vengono incrementate entrambe le matrici <br>
     * Se le matrici non esistono, vengono create <br>
     *
     * @param codice chiave del record
     * @param valore valore del campo
     *
     * @return dimensione delle matrici interne
     */
    public int add(int codice, String valore) {
        /* variabili e costanti locali di lavoro */
        int dim = 0;
        int pos;

        try { // prova ad eseguire il codice
            this.regolaMatrici();

            pos = this.getCodici().length;
            this.addValoreCodice(valore, codice, pos);
            dim = ++pos;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * Aggiunge un codice (non intero) ed il corrispondente valore
     * <p/>
     * L'aggiunta avviene in fondo alla matrice <br>
     * Vengono incrementate entrambe le matrici <br>
     * Se le matrici non esistono, vengono create <br>
     *
     * @param codice chiave del record
     * @param valore valore del campo
     *
     * @return dimensione delle matrici interne
     */
    public int add(Object codice, String valore) {
        /* variabili e costanti locali di lavoro */
        int dim = 0;
        int pos;

        try { // prova ad eseguire il codice
            this.regolaMatrici();

            pos = this.getCodici().length;
            this.addValoreCodice(valore, codice, pos);
            dim = ++pos;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * Ricerca la posizione di un codice nella collezione <br>
     * <p/>
     * La posizione (contrariamente agli array), parte da 1 <br>
     *
     * @param unCodice valore numerico da ricercare
     *
     * @return posizione del codice richiesto - 0 se non trovato
     */
    public int getPosizioneCodice(int unCodice) {
        /* variabili e costanti locali di lavoro */
        int unaPosizione = 0;

        try {    // prova ad eseguire il codice

            for (int k = 0; k < codici.length; k++) {
                if (unCodice == ((Integer)codici[k]).intValue()) {
                    unaPosizione = k;
                    break;
                } /* fine del blocco if */
            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaPosizione;
    } /* fine del metodo */


    /**
     * Ricerca la posizione di un codice nella collezione <br>
     * <p/>
     * La posizione (contrariamente agli array), parte da 1 <br>
     *
     * @param unCodice oggetto (di tipo Integer) da ricercare
     *
     * @return posizione del codice richiesto - 0 se non trovato
     */
    public int getPosizioneCodice(Object unCodice) {
        /* variabili e costanti locali di lavoro */
        int unaPosizione = 0;

        try {    // prova ad eseguire il codice
            if (unCodice != null) {
                /* invoca il metodo sovrascritto di questa classe */
                unaPosizione = getPosizioneCodice(Libreria.getInt(unCodice));
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaPosizione;

    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * ...
     */
    public void setCodici(Object[] codici) {
        this.codici = codici;
    } /* fine del metodo */


    /**
     * ...
     */
    public void setCodici(AbstractList codici) {
        this.codici = super.listaVersoMatrice(codici);
    } /* fine del metodo */


    /**
     * ...
     */
    public void setValori(Object[] valori) {
        this.valori = valori;
    } /* fine del metodo */


    /**
     * ...
     */
    public void setValori(AbstractList valori) {
        this.valori = super.listaVersoMatrice(valori);
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Object[] getCodici() {
        return this.codici;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public ArrayList getListaCodici() {
        return this.creaListaCodici();
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Object[] getValori() {
        return this.valori;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public ArrayList getListaValori() {
        return this.creaListaValori();
    } /* fine del metodo getter */


    public int size() {
        /* variabili e costanti locali di lavoro */
        int dimCodici = 0;
        int dimValori = 0;
        int dim = 0;
        boolean continua;
        Object[] codici;
        Object[] valori = null;


        try { // prova ad eseguire il codice
            codici = this.getCodici();
            continua = (codici != null);

            if (continua) {
                dimCodici = codici.length;
                continua = (dimCodici > 0);
            }// fine del blocco if

            if (continua) {
                valori = this.getValori();
                continua = (valori != null);
            }// fine del blocco if

            if (continua) {
                dimValori = valori.length;
                continua = (dimValori > 0);
            }// fine del blocco if

            if (continua) {
                continua = (dimCodici == dimValori);
            }// fine del blocco if

            if (continua) {
                dim = dimCodici;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }

}// fine della classe