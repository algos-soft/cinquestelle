/**
 * Title:        EFactory.java
 * Package:      it.algos.base.campo.elemento
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 novembre 2003 alle 10.22
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.elemento;

import it.algos.base.errore.Errore;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe abstract factory e' responsabile di: <br>
 * A - Costruire le istanze delle classi che implementano l'interfaccia
 * Elemento <br>
 *
 * @author Guido Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  14 novembre 2003 ore 10.22
 */
public abstract class EFactory extends Object {

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
    public EFactory() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    /**
     * Crea un'istanza della classe EVuoto
     */
    public static Elemento creaVuoto(int larghezzaPopup) {
        /** variabili e costanti locali di lavoro */
        Elemento unElemento = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza della classe da restituire */
            unElemento = new EVuoto(larghezzaPopup);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElemento;
    } /* fine del metodo */


    /**
     * Crea un'istanza della classe ESeparatore
     */
    public static Elemento creaSeparatore(int numeroCaratteri) {
        /** variabili e costanti locali di lavoro */
        Elemento unElemento = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza della classe da restituire */
            unElemento = new ESeparatore(numeroCaratteri);

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElemento;
    } /* fine del metodo */


    /**
     * Crea un'istanza della classe ESeparatore
     */
    public static Elemento creaSeparatore() {
        /** variabili e costanti locali di lavoro */
        Elemento unElemento = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza della classe da restituire */
            unElemento = new ESeparatore();

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElemento;
    } /* fine del metodo */


    /**
     * Crea un'istanza della classe ENuovo
     */
    public static Elemento creaNuovo() {
        /** variabili e costanti locali di lavoro */
        Elemento unElemento = null;

        try {    // prova ad eseguire il codice

            /** crea un'istanza della classe da restituire */
            unElemento = new ENuovo();

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unElemento;
    } /* fine del metodo */
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.campo.elemento.EFactory.java
//-----------------------------------------------------------------------------
