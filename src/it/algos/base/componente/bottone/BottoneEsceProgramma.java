/**
 * Title:        BottoneEsceProgramma.java
 * Package:      it.algos.base.componente.bottone
 * Description:  Singolo bottone
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 7 gennaio 2003 alle 9.50
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.componente.bottone;

import it.algos.base.azione.archivio.AzChiudeProgramma;
import it.algos.base.errore.Errore;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di:<br>
 * A - Costruire un bottone con comando di uscita dal programma <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  7 gennaio 2003 ore 9.50
 */
public class BottoneEsceProgramma extends BottoneAstratto {

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
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore completo senza parametri <br>
     */
    public BottoneEsceProgramma() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** Operazioni alla partenza ed eventuale interfaccia utente */
        this.partenza();
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
        /** crea l'azione */
        unAzione = new AzChiudeProgramma();

        /** aggiunge il legame all'azione */
        this.addActionListener(unAzione.getAzione());
    } /* fine del metodo inizia */


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     */
    private void partenza() {
        super.avviaBottone();
    } /* fine del metodo partenza */
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
}// fine della classe it.algos.base.componente.bottone.BottoneEsceProgramma.java
//-----------------------------------------------------------------------------

