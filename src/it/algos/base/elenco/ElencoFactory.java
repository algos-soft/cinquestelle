/**
 * Title:        ElencoFactory.java
 * Package:      it.algos.base.elenco
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 6 novembre 2003 alle 19.51
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.elenco;

import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceDoppia;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta factory e' responsabile di: <br>
 * A - ... <br>
 * B - ... <br>
 *
 * @author __AUTOREA__
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  6 novembre 2003 ore 19.51
 */
public abstract class ElencoFactory extends Object {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public ElencoFactory() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    /**
     * Crea una istanza per un elenco a selezione singola coi codici visibili
     */
    public static Elenco creaSingolo() {
        /* invoca il metodo sovrascritto di questa classe */
        return creaSingolo(null);
    } /* fine del metodo */


    /**
     * Crea una istanza per un elenco a selezione singola coi codici visibili
     */
    public static Elenco creaSingolo(MatriceDoppia unaMatriceDoppia) {
        /** variabili e costanti locali di lavoro */
        Elenco unElenco = null;

        try {    // prova ad eseguire il codice

            /* crea un'istanza della classe da ritornare */
            unElenco = new ElencoSceltaSingola(unaMatriceDoppia);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElenco;
    } /* fine del metodo */


    /**
     * Crea una istanza per un elenco a selezione multipla
     */
    public static Elenco creaMultiplo(MatriceDoppia unaMatriceDoppia) {
        /** variabili e costanti locali di lavoro */
        Elenco unElenco = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza della classe da ritornare */
            unElenco = new ElencoSceltaMultipla(unaMatriceDoppia);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElenco;
    } /* fine del metodo */

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.elenco.ElencoFactory.java
//-----------------------------------------------------------------------------
