/**
 * Title:        ElencoBase.java
 * Package:      it.algos.base.elenco
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 5 novembre 2003 alle 18.01
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.elenco;

import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceDoppia;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Gestire un elenco di dati ed i codici relativi <br>
 * B - Mantiene gli attributi (ed i relativi metodi) comuni a tutte le
 * sottoclassi: <il> <br>
 * <li> listaCodici <br>
 * <li> listaValori <br> </il> <br>
 * C - Delega alle sottoclassi gli attributi specializzati: <il> <br>
 * <li> <code>ElencoSceltaSingola</code>, che mantiene un singolo valore
 * intero di posizionamento <br>
 * <li> <code>ElencoSceltaMultipla</code>, che mantiene una lista di
 * valori booleani di selezione </il> <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  5 novembre 2003 ore 18.01
 */
public abstract class ElencoBase implements Elenco {

    /**
     * oggetto specializzato per mantenere due matrici: codici e valori
     */
    protected MatriceDoppia unaMatriceDoppia = null;

    /**
     * posizione nella lista valori
     */
    protected int posizione = 0;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public ElencoBase() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unaMatriceDoppia matrice di codici e valori
     */
    public ElencoBase(MatriceDoppia unaMatriceDoppia) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.setMatriceDoppia(unaMatriceDoppia);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
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
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * ...
     */
    public void regolaPosizione(int codice) {
    } /* fine del metodo */


    /**
     * ...
     */
    public void regolaPosizione(Object codice, Object posizioneDefault) {
    } /* fine del metodo */


    /**
     * Ritorna il codice corrispondente alla posizione selezionata
     *
     * @return il codice richiesto (-1 se non trovato)
     */
    public int getCodiceSelezionato() {
        /** variabili e costanti locali di lavoro */
        int codice = 0;
        int posizione = 0;

        try {                                   // prova ad eseguire il codice

            /* recupera il codice dalla posizione */
            posizione = this.getPosizione();
            codice = this.getMatriceDoppia().getCodiceAt(posizione);

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return codice;
    } /* fine del metodo */


    /**
     * Aggiunge alla MatriceDoppia un valore ed il corrispondente codice <br>
     */
    public void addValoreCodice(Object unValore, Object unCodice, int unaPosizione) {
        if (this.getMatriceDoppia() != null) {
            this.getMatriceDoppia().addValoreCodice(unValore, unCodice, unaPosizione);
        }// fine del blocco if
    }


    /**
     * Aggiunge alla MatriceDoppia un valore ed il corrispondente codice <br>
     */
    public void addValoreCodice(Object unValore, int unCodice, int unaPosizione) {
        if (this.getMatriceDoppia() != null) {
            this.getMatriceDoppia().addValoreCodice(unValore, unCodice, unaPosizione);
        }// fine del blocco if
    }


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setMatriceDoppia(MatriceDoppia unaMatriceDoppia) {
        this.unaMatriceDoppia = unaMatriceDoppia;
    } /* fine del metodo setter */


    /**
     * metodo setter che <b>deve</b> essere implementato nella sottoclasse
     */
    public void setPosizione(Integer posizione) {
    } /* fine del metodo setter */


    /**
     * metodo setter che <b>deve</b> essere implementato nella sottoclasse
     */
    public void setPosizione(int posizione) {
    } /* fine del metodo setter */


    /**
     * metodo setter che <b>deve</b> essere implementato nella sottoclasse
     */
    public void setSelezione(ArrayList selezione) {
    } /* fine del metodo setter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public MatriceDoppia getMatriceDoppia() {
        return this.unaMatriceDoppia;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public ArrayList getListaCodici() {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;

        try { // prova ad eseguire il codice
            if (this.getMatriceDoppia() != null) {
                lista = this.getMatriceDoppia().getListaCodici();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public ArrayList getListaValori() {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;

        try { // prova ad eseguire il codice
            if (this.getMatriceDoppia() != null) {
                lista = this.getMatriceDoppia().getListaValori();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Vector getVettoreValori() {
        return new Vector(getListaValori());
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Object[] getArrayValori() {
        return this.getListaValori().toArray();
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Object getValoreSelezionato() {
        return getValore(this.getPosizione());
    } /* fine del metodo getter */


    /**
     * Ritorna il valore alla posizione specificata.
     * <p/>
     *
     * @return il valore alla posizione specificata
     *         (1 per la prima posizione)
     */
    public Object getValore(int posizione) {
        /* variabili e costanti locali di lavoro */
        Object unValore = null;
        int max = 0;

        try { // prova ad eseguire il codice
            max = this.getListaValori().size();

            if ((posizione > 0) && (posizione <= max)) {
                unValore = this.getListaValori().get(posizione - 1);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unValore;
    }


    /**
     * metodo getter
     */
    public int getPosizione(int unCodice) {
        this.regolaPosizione(unCodice);
        return this.posizione;
    } /* fine del metodo getter */


    /**
     * metodo getter
     */
    public int getPosizione(Object unCodice) {
        if (unCodice != null) {
            return this.getPosizione(((Integer)unCodice).intValue());
        } else {
            return 0;
        }// fine del blocco if-else

    } /* fine del metodo getter */


    /**
     * metodo getter
     */
    public int getCodice(int unaPosizione) {
        this.setPosizione(unaPosizione);
        return this.getCodiceSelezionato();
    } /* fine del metodo getter */


    /**
     * metodo getter
     */
    public int getCodice(Object unaPosizione) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        int posizione;

        try { // prova ad eseguire il codice
            if (unaPosizione != null) {
                if (unaPosizione instanceof Integer) {
                    posizione = (Integer)unaPosizione;
                    codice = this.getCodice(posizione);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;

    } /* fine del metodo getter */


    /**
     * metodo getter che <b>deve</b> essere implementato nella sottoclasse
     */
    public int getPosizione() {
        return 0;
    } /* fine del metodo getter */


    /**
     * metodo getter che <b>deve</b> essere implementato nella sottoclasse
     */
    public ArrayList getSelezione() {
        return null;
    } /* fine del metodo getter */

}// fine della classe