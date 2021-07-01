/**
 * Title:        MessaggioTest.java
 * Package:      it.algos.base.messaggio
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 settembre 2002 alle 12.32
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.messaggio;

import it.algos.base.costante.CostanteBase;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe e' responsabile di:<br>
 * A - ... <br>
 * B - ... <br>
 * Contiene il metodo iniziale "main".
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  30 settembre 2002 ore 12.32
 */
public class MessaggioTest extends Object implements CostanteBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOMECLASSE = "MessaggioTest";


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
    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    /**
     * Print some object to stderr
     *
     * @param unTesto descrizione dell'oggetto
     */
    private static void print(Object unTesto) {
        System.err.println(unTesto);
    } /* fine del metodo */


    /**
     * Operazioni alla partenza e eventuale interfaccia utente
     */
    private static void prova() {
        /** test con un parametro di testo valido */
        new MessaggioAvviso(MESS_CANCELLA_MULTIPLO_INIZIO, 45, MESS_CANCELLA_MULTIPLO_FINE);

        /** test con un parametro di testo valido */
        new MessaggioDialogo("Sei sicuro di voler cancellare il record selezionato ?");

        /** test con un parametro di testo valido */
        new MessaggioAvviso(TESTO_CHIUDE_FINESTRA_TIP);

        /** test con un parametro di testo valido */
        new MessaggioAvviso(TESTO_CHIUDE_FINESTRA_TIP, "ulteriore testo");

        /** test con un parametro di testo valido */
        new MessaggioErrore(ERRORE_GENERICO);

        /** test con un parametro di testo valido */
        new MessaggioErrore(ERRORE_INIZIA, "TestMessaggio");

        /** test con un parametro di testo non valido */
        new MessaggioAvviso("Prova di testo passato direttamente");

        /** termina il test */
        System.exit(0);
    } // partenza


    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
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
        prova();
    } /* fine del metodo main */
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.messaggio.MessaggioTest.java
//-----------------------------------------------------------------------------
