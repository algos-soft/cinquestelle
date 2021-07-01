/**
 * Title:        PassaggioObbligato.java
 * Package:      it.algos.base.query
 * Description:  Abstract Data Type per un passaggio obbligato della QuerySelezione
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 26 giugno 2003 alle 12.13
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.query.selezione;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.relazione.Relazione;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire un modello di dati che descrive un passaggio obbligato <br>
 * per le relazioni della QuerySelezione.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  26 giugno 2003 ore 12.13
 */
public final class PassaggioObbligato extends Object {

    /**
     * tavola di destinazione finale della relazione
     */
    private String unaTavolaDestinazione = "";

    /**
     * relazione da attraversare obbligatoriamente
     */
    private Relazione unaRelazioneObbligata = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public PassaggioObbligato() {
        /** rimanda al costruttore di questa classe */
        this(null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unaTavolaDestinazione la tavola destinazione
     * @param unCampoPassaggio un campo dal quale passare obbligatoriamente
     */
    public PassaggioObbligato(String unaTavolaDestinazione, Campo unCampoPassaggio) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia(unaTavolaDestinazione, unCampoPassaggio);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia(String unaTavolaDestinazione, Campo unCampoPassaggio) throws Exception {

        /** controllo errore */
        try {                                   // prova ad eseguire il codice
            /** regola la tavola destinazione */
            this.unaTavolaDestinazione = unaTavolaDestinazione;

            /** regola la relazione obbligata */
            this.unaRelazioneObbligata = new Relazione(unCampoPassaggio);

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo inizia */


    /**
     * ritorna la tavola destinazione del Passaggio Obbligato.
     *
     * @return la tavola destinazione
     */
    public String getTavolaDestinazione() {
        return this.unaTavolaDestinazione;
    }


    /**
     * ritorna la relazione obbligata.
     *
     * @return la relazione obbligata
     */
    public Relazione getRelazioneObbligata() {
        return this.unaRelazioneObbligata;
    }

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.query.QuerySelezionePassaggioObbligato.java
//-----------------------------------------------------------------------------

