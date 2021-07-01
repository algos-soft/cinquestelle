/**
 * Title:        SequenzialeComandoBase.java
 * Package:      it.algos.base.sequenziale
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 17.43
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.sequenziale;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Classe comando - Superclasse delle classi comando di questo
 * pacchetto.<br>
 * Non uso le classi del pacchetto comando, perche il pacchetto
 * sequenziale è di basso livello
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 17.43
 */
public class SequenzialeComandoBase extends AbstractAction implements ActionListener {

    //-------------------------------------------------------------------------
    // Costanti statiche della  classe   (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOMECLASSE = "SequenzialeComandoBase";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    /**
     * gestore principale della logica e dei riferimenti (puntatori)
     */
    protected SequenzialeGestore unGestore = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * testo del bottone
     */
    private String unNome = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Rimanda al costruttore completo utilizzando i valori di default
     */
    public SequenzialeComandoBase() {
        /** rimanda al costruttore di questa classe */
        this(null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo col gestore dei comandi
     *
     * @param unGestore oggetto che gestisce i comandi
     * @param unNome proprieta' del comando
     */
    public SequenzialeComandoBase(SequenzialeGestore unGestore, String unNome) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.unGestore = unGestore;
        this.unNome = unNome;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            JOptionPane.showMessageDialog(null,
                    "Si è verificato un errore ",
                    "Non sono riuscito ad eseguire il comando",
                    JOptionPane.ERROR_MESSAGE);
        } /* fine del blocco try-catch */
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
        /**
         * regola la proprieta dell'azione (NAME),
         * con la variabile d'istanza (unNome).<br>
         * la proprieta dell'azione verra usata per mostrare
         * il testo nei menu e nei bottoni
         */
        this.putValue(NAME, unNome);
    } /* fine del metodo */


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
    // Metodi che verranno sovrascritti nella sottoclasse          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    /**
     * actionPerformed, da ActionListener.<br>
     * Esegue il comando - Va implementata dalle singole sottoclassi
     *
     * @param unEvento - L' evento che causa il comando da eseguire
     */
    public void actionPerformed(ActionEvent unEvento) {
    } // actionPerformed
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
}// fine della classe it.algos.base.sequenziale.SequenzialeComandoBase.java
//-----------------------------------------------------------------------------

