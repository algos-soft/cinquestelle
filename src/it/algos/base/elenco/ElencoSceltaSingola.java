/**
 * Title:        ElencoSceltaSingola.java
 * Package:      it.algos.base.elenco
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 5 novembre 2003 alle 18.05
 */
package it.algos.base.elenco;

import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceDoppia;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire un elenco di dati a selezione singola <br>
 * B - Mantiene un singolo valore intero di posizionamento <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  5 novembre 2003 ore 18.05
 */
public final class ElencoSceltaSingola extends ElencoBase {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public ElencoSceltaSingola() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unaMatriceDoppia matrice di codici e valori
     */
    public ElencoSceltaSingola(MatriceDoppia unaMatriceDoppia) {
        /** rimanda al costruttore della superclasse */
        super(unaMatriceDoppia);

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
     * regola il valore di posizione <br>
     * riceve in ingresso il codice <br>
     * ricerca nell'array dei codici il valore e restituisce la posizione <br>
     * regola l'attributo interno <br>
     */
    public void regolaPosizione(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int posizione = -1;

        try {    // prova ad eseguire il codice

            continua = (this.getMatriceDoppia() != null);

            if (continua) {
                posizione = this.getMatriceDoppia().getListaCodici().indexOf(new Integer(codice));

                /* l'indice interno parte da zero, mentre io voglio che
                 * posizione parta da 1 */
                posizione++;

                if (posizione > 0) {
                    this.setPosizione(posizione);
                } else {
                    this.setPosizione(-1);
                }// fine del blocco if-else


            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * regola il valore di posizione <br>
     * <p/>
     * ricerca nell'array dei codici il valore e restituisce la posizione <br>
     * se non trova il valore, usa quello di default ricevuto come parametro <br>
     * regola l'attributo interno <br>
     *
     * @param codice valore da ricercare nella lista codici
     * @param valoreDefault valore da usare per l'attributo posizione,
     * se il codice non viene trovato nella lista
     */
    public void regolaPosizione(Object codice, Object valoreDefault) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int posizione = -1;
        int posizioneDefault = -1;

        try {    // prova ad eseguire il codice
            continua = (this.getMatriceDoppia() != null);

            /* recupera il valore di default dal parametro */
            if (valoreDefault instanceof Integer) {
                posizioneDefault = ((Integer)valoreDefault).intValue();
            } /* fine del blocco if/else */

            /* ricerca la posizione del codice nella lista mantenuta da
             *  unaMatriceDoppia */
            if (continua) {
                posizione = this.unaMatriceDoppia.getPosizioneCodice(codice);
            }// fine del blocco if

//            posizione = this.unaMatriceDoppia.getListaCodici().indexOf(codice);

            /* Se il valore e' stato trovato nella lista, regola l'attributo
             *  interno con la relativa posizione; altrimenti usa il valore
             *  di default ricevuto */
            if (posizione >= 0) {
                this.setPosizione(posizione);
            } else {
                this.setPosizione(posizioneDefault);
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setPosizione(Integer posizione) {
        this.setPosizione(posizione.intValue());
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    } /* fine del metodo setter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public int getPosizione() {
        return this.posizione;
    } /* fine del metodo getter */

}// fine della classe