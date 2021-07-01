/**
 * Title:        GestoreStato.java
 * Package:      it.algos.base.gestore.gestorestato
 * Description:  Controlli di stato
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 15 febbraio 2003 alle 12.32
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.gestore.gestorestato;

import it.algos.base.azione.Azione;
import it.algos.base.costante.CostanteAzione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.HashMap;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Mantenere alcuni attributi delle sottoclassi <br>
 * B - Viene estesa da GestoreStatoLista e GestoreStatoScheda <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 febbraio 2003 ore 12.32
 */
public abstract class GestoreStato extends Object implements CostanteAzione {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    /**
     * Abstract Data Types per le informazioni di un modulo
     */
    protected Modulo unModulo = null;

//    /** finestra 'proprietaria' di questa classe */
//    protected FinestraOld unaFinestra;

    /**
     * riferimento alla collezione di azioni del contenitore (finestra o campo)
     */
    protected HashMap linkAzioni = null;


    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public GestoreStato() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    public void inizializzaGestoreStato(Modulo unModulo) {
        /** regola le variabili di istanza coi parametri */
        this.unModulo = unModulo;
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     *
     * @param linkAzioni
     */
    public void avviaGestoreStato(HashMap linkAzioni) {
        /** regola le variabili di istanza coi parametri */
        this.linkAzioni = linkAzioni;
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * Abilita o disabilita una azione specifica
     */
    private void abilitaDisabilita(String unaChiaveAzione, boolean abilita) {
        /** prova ad eseguire il codice */
        try {
            Azione unaAzioneOld = (Azione)linkAzioni.get(unaChiaveAzione);
            /** controllo di congruita' */
            if (unaAzioneOld != null) {
                if (unaAzioneOld.isAttiva()) {
                    unaAzioneOld.getAzione().setEnabled(abilita);
                } else {
                    unaAzioneOld.getAzione().setEnabled(false);
                } /* fine del blocco if/else */
            } /* fine del blocco if */

        } catch (Exception unErrore) {
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    /**
     * Abilita una azione specifica
     */
    protected void abilita(String unaChiaveAzione) {
        this.abilitaDisabilita(unaChiaveAzione, true);
    } /* fine del metodo */


    /**
     * Disabilita una azione specifica
     */
    protected void disabilita(String unaChiaveAzione) {
        this.abilitaDisabilita(unaChiaveAzione, false);
    } /* fine del metodo */

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
}// fine della classe it.algos.base.gestore.gestorestato.GestoreStato.java
//-----------------------------------------------------------------------------
