/**
 * Title:        SequenzialeTest.java
 * Package:      it.algos.base.sequenziale
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 novembre 2002 alle 16.19
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.sequenziale;

import it.algos.base.costante.CostanteBase;
import it.algos.base.messaggio.MessaggioErrore;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Classe di test per provare le classi del pacchetto <br>
 * Contiene il metodo iniziale "main".
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 novembre 2002 ore 16.19
 */
public class SequenzialeTest extends Object implements CostanteBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOMECLASSE = "SequenzialeTest";


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
     * Costruttore
     */
    public SequenzialeTest() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new MessaggioErrore(ERRORE_INIZIA, NOMECLASSE);
        } /* fine del blocco try-catch */

        /** Operazioni di prova */
        this.prova();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo */


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     */
    private void prova() {
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * Print some object to stderr
     *
     * @param unTesto descrizione dell'oggetto
     */
    private void print(Object unTesto) {
        System.err.println(unTesto);
    } /* fine del metodo */


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
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodo principale di partenza del programma                       (main)
    //-------------------------------------------------------------------------
    /**
     * Main method
     *
     * @param ignora nessun parametro in ingresso
     */
    public static void main(String ignora[]) throws Exception {
        /** crea una istanza di se stessa */
        new SequenzialeTest();
    } /* fine del metodo main */
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.sequenziale.SequenzialeTest.java
//-----------------------------------------------------------------------------
