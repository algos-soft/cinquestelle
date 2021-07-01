/**
 * Title:        Testo.java
 * Package:      it.algos.base.testo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.29
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.testo;

import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Classe per gestire in maniera unificata tutti i testi, le scritte,
 * i messaggi di avviso, i nomi dei bottoni e degli altri componenti.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.29
 */
public class Testo extends Object implements CostanteModulo {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "Testo";

    /**
     * nome del file
     */
    private static final String NOME_FILE = PATH_BASE + "testo.TestoDati";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    /**
     * collezione chiave-valore per contenere i testi
     */
    private static Properties unSet;


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
     * Costruttore completo senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public Testo() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore base */
    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    /**
     *	Crea la collezione
     */
    /**
     * Metodo statico.
     * <p/>
     * Invocato la prima volta che la classe statica viene richiamata nel programma <br>
     */
    static {
        /** crea la collezione per i testi */
        unSet = new Properties();
    } // static

    /**
     * Metodo statico.
     * <p/>
     * Invocato la prima volta che la classe statica viene richiamata nel programma <br>
     */
    static {
        /** variabili e costanti locali di lavoro */
        ResourceBundle unaRisorsa = null;
        Enumeration unaLista = null;
        String unaChiave = "";
        String unFile = NOME_FILE;

        /** recupera il file di preferenze */
        try {
            unaRisorsa = ResourceBundle.getBundle(unFile);
        } catch (Exception unErrore) {
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** recupera la lista delle chiavi */
        unaLista = unaRisorsa.getKeys();

        /** traverso tutta la collezione */
        while (unaLista.hasMoreElements()) {
            unaChiave = (String)unaLista.nextElement();
            aggiunge(unaChiave, unaRisorsa.getString(unaChiave));
        } /* fine del blocco while */
    } /* fine del metodo statico */


    /**
     * aggiunge una coppia chiave-valore alla collezione
     */
    public static void aggiunge(String unaChiave, Object unValore) {
        unSet.setProperty(unaChiave, (String)unValore);
    } /* fine del metodo statico */


    /**
     * elimina una coppia chiave-valore dalla collezione
     */
    public static void elimina(String unaChiave) {
        unSet.remove(unaChiave);
    } /* fine del metodo statico */


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
    /**
     * getter per la dimensione della collezione
     */
    public static int size() {
        return unSet.size();
    } // getSize


    /**
     * getter per l'intera collezione di testi
     */
    public static Properties getCollezioneTesti() {
        return unSet;
    } // fine del metodo getter


    /**
     * getter per l'esistenza di una particolare chiave
     */
    public static boolean isChiaveValida(String chiave) {
        return unSet.containsKey(chiave);
    } // fine del metodo getter


    /**
     * getter per il singolo testo
     */
    public static String get(String chiave) {
        /** valore di ritorno */
        return (String)unSet.getProperty(chiave);
    } // fine del metodo getter
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
}// fine della classe it.algos.base.testo.Testo.java
//-----------------------------------------------------------------------------

