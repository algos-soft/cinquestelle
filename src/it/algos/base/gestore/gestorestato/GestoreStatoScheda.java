/**
 * Title:        GestoreStatoScheda.java
 * Package:      it.algos.base.gestore.gestorestato
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 15 febbraio 2003 alle 12.43
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.gestore.gestorestato;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import javax.swing.text.JTextComponent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Controlli dello stato generale della scheda e conseguenti regolazioni<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 febbraio 2003 ore 12.43
 */
public abstract class GestoreStatoScheda extends GestoreStato {
    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------

    //    /** scheda attiva al momento del controllo */
//    private SchedaOld unaScheda = null;
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    protected boolean isNuovoRecord = false;

    protected boolean isPrimoRecord = false;

    protected boolean isUltimoRecord = false;

    protected boolean isRecordModificato = false;

    protected boolean isRecordRegistrabile = false;

    protected boolean isTestoSelezionato = false;

    protected boolean isBufferAppuntiPieno = false;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public GestoreStatoScheda() {
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
        /** invoca il metodo sovrascritto della superclasse */
        super.inizializzaGestoreStato(unModulo);
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
//	/** invoca il metodo sovrascritto della superclasse */
//        super.avviaGestoreStato(linkAzioni);
//
//        /** regola le variabili di istanza coi parametri */
//        this.unaScheda = super.unModulo.getGestoreOld().getScheda();
//
//        this.inizializzaFlags();
//        this.controllaStato();
//        this.disabilitaTutteAzioni();
//        this.abilitaAzioni();
//        this.controllaStatoSub();

    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * ...
     */
    private void inizializzaFlags() {
        isNuovoRecord = false;
        isPrimoRecord = false;
        isUltimoRecord = false;
        isRecordModificato = false;
        isRecordRegistrabile = false;

        isTestoSelezionato = false;
        isBufferAppuntiPieno = false;
    } /* fine del metodo */

//   /** Controlla lo stato nella Scheda */
//    public void controllaStato() {
//        // Controlla se siamo in un nuovo record
//        if (super.unModulo.getGestoreOld().isNuovoRecord()) {
//            this.isNuovoRecord = true;
//        } /* fine del blocco if */
//
//        /** il nuovo record non visuializza i bottoni di spostamento */
//        if (this.isNuovoRecord == false) {
//            // Controlla se siamo nel primo record
//            try {	// prova ad eseguire il codice
//                if (unaScheda.getCursore() == 1) {
//                    this.isPrimoRecord = true;
//                } /* fine del blocco if */
//            } catch (Exception unErrore) {	// intercetta l'errore
//                /** mostra il messaggio di errore */
//                new Errore(unErrore, "isFirst");
//            } /* fine del blocco try-catch */
//
//            // Controlla se siamo nell'ultimo record
//            try {	// prova ad eseguire il codice
//                if (unaScheda.getCursore() == unaScheda.getQuantiRecords()) {
//                    this.isUltimoRecord = true;
//                } /* fine del blocco if/else */
//            } catch (Exception unErrore) {	// intercetta l'errore
//                /** mostra il messaggio di errore */
//                new Errore(unErrore, "isLast");
//            } /* fine del blocco try-catch */
//        } /* fine del blocco if */
//
//        // Controlla se il record e' stato modificato
//        if (this.unaScheda.isRecordModificato()) {
//            this.isRecordModificato = true;
//        } /* fine del blocco if */
//
//        // Controlla se il record e' registrabile
//        if (this.unaScheda.isRecordRegistrabile()) {
//            this.isRecordRegistrabile = true;
//        } /* fine del blocco if */
//
//        // Controlla se il buffer Appunti contiene qualcosa
//        Toolkit unToolkit = Toolkit.getDefaultToolkit();
//        Clipboard unaClipboard = unToolkit.getSystemClipboard();
//        if ( unaClipboard.getContents(null) != null ) {
//            isBufferAppuntiPieno = true;
//        } /* fine del blocco if */
//
//    } /* fine del metodo */


    /**
     * Controlla lo stato dei comandi di taglia, copia ed incolla
     */
    public void controllaCopiaIncolla(MouseEvent unEvento) {
        /** variabili e costanti locali di lavoro */
        String unTestoSelezionato = "";
        JTextComponent unComponenteTesto = null;

        // Controlla se c'e' del testo selezionato
        if (true) {
//        if ((unEvento.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
            /** recupera il campo */
            unComponenteTesto = (JTextComponent)unEvento.getSource();

            /** recupera il testo selezionato */
            unTestoSelezionato = unComponenteTesto.getSelectedText();

            /** regola il flag */
            if ((unTestoSelezionato != null) && (unTestoSelezionato.length() > 0)) {
                isTestoSelezionato = true;
            } /* fine del blocco if */

        } /* fine del blocco if */

    } /* fine del metodo */


    /**
     * Abilita o disabilita una azione specifica
     */
    private void abilitaDisabilita(String unaChiaveAzione, boolean abilita) {
        /** prova ad eseguire il codice */
        try {
//            AzioneOld unaAzioneOld = unaScheda.getFinestra().getAzione(unaChiaveAzione);
//            if (unaAzioneOld.isAbilitata()) {
//                unaAzioneOld.getAzione().setEnabled(abilita);
//            } else {
//                unaAzioneOld.getAzione().setEnabled(false);
//            } /* fine del blocco if/else */
        } catch (Exception unErrore) {
            /** mostra il messaggio di errore */
            new Errore(unErrore, unaChiaveAzione);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


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
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    /**
     * Disabilita tutte le azioni nella Scheda
     * (quelle necessarie verranno poi riabilitate una per una nel metodo abilitaAzioni)
     */
    public void disabilitaTutteAzioni() {
        disabilita(ANNULLA_MODIFICHE);
        disabilita(REGISTRA_RECORD);
        disabilita(CHIUDE_SCHEDA);
//        disabilita(STAMPA);
//        disabilita(ESCE_PROGRAMMA);

//        disabilita(RECORD_PRIMO);
        disabilita(RECORD_PRECEDENTE);
        disabilita(RECORD_SUCCESSIVO);
//        disabilita(RECORD_ULTIMO);
    } /* fine del metodo */


    /**
     * Riabilita le azioni nella Scheda
     */
    public void abilitaAzioni() {

        // Azioni che abilita sempre
        abilita(CHIUDE_SCHEDA);
//        abilita(STAMPA);
//        abilita(ESCE_PROGRAMMA);

        // Controlli abilitazione frecce
        if (isNuovoRecord) {
//            abilita(REGISTRA_RECORD);
        } else {
            /** frecce indietro */
            if (isPrimoRecord == false) {
//                abilita(RECORD_PRIMO);
                abilita(RECORD_PRECEDENTE);
            } /* fine del blocco if */
            /** frecce avanti */
            if (isUltimoRecord == false) {
                abilita(RECORD_SUCCESSIVO);
//                abilita(RECORD_ULTIMO);
            } /* fine del blocco if */
        } /* fine del blocco if */

        // Controllo abilitazione pulsante Registra
        if (isRecordModificato) {
            abilita(REGISTRA_RECORD);
        } /* fine del blocco if */

        // Controllo abilitazione pulsante Ripristina
        if (isRecordModificato) {
            abilita(ANNULLA_MODIFICHE);
        } /* fine del blocco if */
    } /* fine del metodo */


    /**
     * Disabilita nella Scheda le azioni di taglia, copia ed incolla
     * (quelle necessarie verranno poi riabilitate una per una nel metodo abilitaAzioni)
     */
    public void disabilitaAzioniCopiaIncolla() {
        disabilita(TAGLIA_TESTO);
        disabilita(COPIA_TESTO);
        disabilita(INCOLLA_TESTO);
    } /* fine del metodo */


    /**
     * Riabilita nella Scheda le azioni di taglia, copia ed incolla
     */
    public void abilitaAzioniCopiaIncolla() {
        if (true) {
//        if (isTestoSelezionato) {
            abilita(TAGLIA_TESTO);
            abilita(COPIA_TESTO);
        } /* fine del blocco if */
    } /* fine del metodo */


    /**
     * Esegue il controllo stato lista su tutti gli eventuali campi
     * sublista presenti nella scheda
     */
    public void controllaStatoSub() {

//        /** Spazzola i campi della scheda */
//        try {                                   // prova ad eseguire il codice
//            /** recupero la collezione dei campi presenti nella scheda */
//            HashMap unaCollezioneCampi = unaFinestraScheda.getCampoScheda();
//
//            /** traverso tutta la collezione */
//            Campo unCampo = null;
//            Iterator unGruppo = unaCollezioneCampi.values().iterator();
//            while (unGruppo.hasNext()) {
//                unCampo = (Campo) unGruppo.next();
//                // controllo se il campo è di tipo sublista,
//                // in tal caso eseguo il controllo stato lista
//                // DA  RICONTROLLARE
////                if (unCampo instanceof CampoSubLista) {
////                    CampoSubLista unCampoSub = (CampoSubLista)unCampo;
////                    unCampoSub.getModulo().getGestore().controllaStatoLista();
////                } /* fine del blocco if */
//
//            } // fine di while
//
//        } catch (Exception unErrore) {           // intercetta l'errore
//            /** messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */

    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    /**
     * Esegue la sequenza di controllo e abilitazione
     */
    public void regolaCopiaIncolla(MouseEvent unEvento) {
        this.inizializzaFlags();
        this.controllaCopiaIncolla(unEvento);
        this.disabilitaAzioniCopiaIncolla();
        this.abilitaAzioniCopiaIncolla();
    } /* fine del metodo */
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
}// fine della classe it.algos.base.gestore.gestorestato.GestoreStatoScheda.java
//-----------------------------------------------------------------------------

