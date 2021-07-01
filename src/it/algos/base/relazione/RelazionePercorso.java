/**
 * Title:        RelazionePercorso.java
 * Package:      it.algos.base.relazione
 * Description:  Abstract Data Types per la struttura dei dati di un Percorso di Relazioni
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author uido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 22 giugno 2003 alle 11.26
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.relazione;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire un modello di dati che descrive un percorso tra due tavole. <br>
 * Un PercorsoRelazione e' una lista ordinata di oggetti Relazione <br>
 * che descrive il percorso per andare da una tavola a un'altra tavola.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  22 giugno 2003 ore 11.26
 */
public final class RelazionePercorso extends Object {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    private String tavolaPartenza;  // la tavola di partenza

    private String tavolaArrivo;    // la tavola di arrivo

    private ArrayList listaRelazioni =
            null;    // la lista ordinata di relazioni che descrive il percorso


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
    public RelazionePercorso() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore con lista di relazioni
     * crea un nuovo percorso contenente la lista di Relazioni passata
     */
    public RelazionePercorso(ArrayList unaListaRelazioni) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia(unaListaRelazioni);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Costruttore con una relazione
     * crea un nuovo percorso contenente la Relazione passata
     */
    public RelazionePercorso(Relazione unaRelazione) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia(unaRelazione);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
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
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /** crea un nuovo percorso vuoto */
        listaRelazioni = new ArrayList();

    } /* fine del metodo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia(ArrayList unaListaRelazioni) throws Exception {

        /** crea un nuovo percorso vuoto */
        this.inizia();

        /** aggiunge la lista di relazioni al percorso */
        for (int k = 0; k < unaListaRelazioni.size(); k++) {
            this.listaRelazioni.add(unaListaRelazioni.get(k));
        } /* fine del blocco for */

        /** regola le tavole di Arrivo e Partenza del Percorso*/
        this.regolaTavolePartenzaArrivo();

    } /* fine del metodo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia(Relazione unaRelazione) throws Exception {

        /** crea un nuovo percorso vuoto */
        this.inizia();

        /** aggiunge la unica relazione al percorso */
        this.listaRelazioni.add(unaRelazione);

        /** regola le tavole di Arrivo e Partenza del Percorso*/
        this.regolaTavolePartenzaArrivo();

    } /* fine del metodo */
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------


    /**
     * Imposta le tavole di partenza e di arrivo
     * per questo percorso
     */
    private void regolaTavolePartenzaArrivo() {
        this.regolaTavolaPartenza();
        this.regolaTavolaArrivo();
    } /* fine del metodo */


    /**
     * Imposta la tavola di partenza
     * per questo percorso
     */
    private void regolaTavolaPartenza() {
        /** recupera il pacchetto di relazioni del percorso */
        ArrayList unPacchettoRelazioni = this.getRelazioni();
        /** se il pacchetto non e' vuoto, assegna la tavola al percorso */
        if (unPacchettoRelazioni.size() > 0) {
            /** recupera la relazione dal percorso */
            Relazione unaRelazione =
                    (Relazione)unPacchettoRelazioni.get(unPacchettoRelazioni.size() - 1);
            String unaTavola = unaRelazione.getTavolaPartenza();
            /** assegna le tavole al percorso */
            this.setTavolaPartenza(unaTavola);
        } /* fine del blocco if */
    } /* fine del metodo */


    /**
     * Imposta la tavola di arrivo
     * per questo percorso
     */
    private void regolaTavolaArrivo() {
        /** recupera il pacchetto di relazioni del percorso */
        ArrayList unPacchettoRelazioni = this.getRelazioni();
        /** se il pacchetto non e' vuoto, assegna la tavola al percorso */
        if (unPacchettoRelazioni.size() > 0) {
            /** recupera la relazione dal percorso */
            Relazione unaRelazione = (Relazione)unPacchettoRelazioni.get(0);
            String unaTavola = unaRelazione.getTavolaArrivo();
            /** assegna le tavole al percorso */
            this.setTavolaArrivo(unaTavola);
        } /* fine del blocco if */
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
    /**
     * Aggiunge una relazione al Percorso
     *
     * @param la Relazione da aggiungere
     */
    public void addRelazione(Relazione unaRelazione) {

        /** aggiunge la relazione alla lista */
        listaRelazioni.add(unaRelazione);

        /** regola la tavola di partenza del percorso */
        this.regolaTavolaPartenza();

    } /* fine del metodo */


    /**
     * Verifica se un Percorso passa attraverso le stesse tavole
     * di un altro Percorso
     *
     * @param il Percorso da verificare rispetto a questo (this)
     *
     * @return true se i due Percorsi passano attraverso le stesse tavole
     */
    public boolean equalsTavole(Object altroOggetto) {

        /** variabili e costanti locali di lavoro */
        Relazione unaRelazioneQuesto = null;
        Relazione unaRelazioneAltro = null;

        //* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof RelazionePercorso)) {
            return false; // tipi non comparabili
        }

        /** procedo alla comparazione */
        boolean uguali = true;
        RelazionePercorso questo = this;
        RelazionePercorso altro = (RelazionePercorso)altroOggetto;
        ArrayList unElencoRelazioniQuesto = questo.getRelazioni();
        ArrayList unElencoRelazioniAltro = altro.getRelazioni();

        /** controllo che i due percorsi abbiano lo stesso numero di passaggi*/
        if (unElencoRelazioniQuesto.size() != unElencoRelazioniAltro.size()) {
            return false;
        }

        /** spazzola tutte le relazioni di un percorso e le
         *  confronta con le corrispondenti relazioni dell'altro percorso
         *  per vedere se uniscono le stesse tavole */
        for (int k = 0; k < unElencoRelazioniQuesto.size(); k++) {

            // estrae le due relazioni da confrontare
            unaRelazioneQuesto = (Relazione)unElencoRelazioniQuesto.get(k);
            unaRelazioneAltro = (Relazione)unElencoRelazioniAltro.get(k);

            // confronta le due relazioni
            if (unaRelazioneQuesto.equalsTavole(unaRelazioneAltro) == false) {
                uguali = false;
                break;
            } /* fine del blocco if */

        } /* fine del blocco for */

        return uguali;
    } /* fine del metodo */


    /**
     * Verifica se un percorso e' esattamente uguale a un altro percorso
     * (stesso numero di passaggi, ogni passaggio passa dalle stesse tavole
     * e dagli stessi campi)
     *
     * @param il Percorso da verificare rispetto a questo (this)
     *
     * @return true se i due percorsi sono uguali
     */
    public boolean equals(Object altroOggetto) {

        /** variabili e costanti locali di lavoro */
        boolean uguali = true;
        RelazionePercorso percorsoQuesto = null;
        RelazionePercorso percorsoAltro = null;
        ArrayList unaListaRelazioniQuesto = new ArrayList();
        ArrayList unaListaRelazioniAltro = new ArrayList();
        Relazione unaRelazioneQuesto = null;
        Relazione unaRelazioneAltro = null;

        //* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof RelazionePercorso)) {
            return false; // tipi non comparabili
        }

        /** procedo alla comparazione */
        percorsoQuesto = this;
        percorsoAltro = (RelazionePercorso)altroOggetto;
        unaListaRelazioniQuesto = percorsoQuesto.getRelazioni();
        unaListaRelazioniAltro = percorsoAltro.getRelazioni();

        // verifico se hanno lo stesso numero di passaggi
        if (unaListaRelazioniQuesto.size() != unaListaRelazioniAltro.size()) {
            return false;
        } /* fine del blocco if */

        // comparo ogni singola relazione
        for (int k = 0; k < unaListaRelazioniQuesto.size(); k++) {

            // estraggo le relazioni da comparare
            unaRelazioneQuesto = (Relazione)unaListaRelazioniQuesto.get(k);
            unaRelazioneAltro = (Relazione)unaListaRelazioniAltro.get(k);

            // comparo le relazioni
            if (unaRelazioneQuesto.equals(unaRelazioneAltro) == false) {
                uguali = false;
                break;  // non occorre procedere oltre
            } /* fine del blocco if */

        } /* fine del blocco for */

        return uguali;

    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * Setter for property tavolaPartenza.
     *
     * @param tavolaPartenza la tavola dalla quale il percorso parte (many table)
     */
    public void setTavolaPartenza(String tavolaPartenza) {
        this.tavolaPartenza = tavolaPartenza;
    } /* fine del metodo setter */


    /**
     * Setter for property tavolaArrivo.
     *
     * @param tavolaArrivo la tavola alla quale il percorso arriva (one table)
     */
    public void setTavolaArrivo(String tavolaArrivo) {
        this.tavolaArrivo = tavolaArrivo;
    } /* fine del metodo setter */


    /**
     * Imposta una specifica Relazione nel Percorso
     *
     * @param la relazione da inserire nel percorso
     * @param posizione in cui inserire la relazione (0 per la prima)
     */
    public void setRelazione(Relazione unaRelazione, int indiceRelazione) {
        if (listaRelazioni.size() > indiceRelazione) {
            listaRelazioni.set(indiceRelazione, unaRelazione);
        } /* fine del blocco if */
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * Getter for property tavolaPartenza.
     *
     * @return tavolaPartenza la tavola dalla quale il percorso parte (many table)
     */
    public String getTavolaPartenza() {
        return this.tavolaPartenza;
    } /* fine del metodo getter */


    /**
     * Getter for property tavolaArrivo.
     *
     * @return tavolaArrivo la tavola alla quale il percorso arriva (one table)
     */
    public String getTavolaArrivo() {
        return this.tavolaArrivo;
    } /* fine del metodo getter */


    /**
     * Restituisce il percorso
     */
    public ArrayList getPercorso() {
        return this.listaRelazioni;
    } /* fine del metodo getter */


    /**
     * Restituisce l'ultima Relazione del Percorso
     */
    public Relazione getUltimaRelazione() {
        return (Relazione)this.listaRelazioni.get(listaRelazioni.size() - 1);
    } /* fine del metodo getter */


    /**
     * Restituisce tutte le Relazioni del Percorso
     */
    public ArrayList getRelazioni() {
        return this.listaRelazioni;
    } /* fine del metodo getter */


    /**
     * Restituisce una specifica Relazione del Percorso
     *
     * @param indiceRelazione indice della relazione da recuperare (0 per la prima)
     *
     * @return l'oggetto Relazione recuperato (null se l'indice e' fuori dal range)
     */
    public Relazione getRelazione(int indiceRelazione) {
        Relazione unaRelazione = null;

        if (listaRelazioni.size() > indiceRelazione) {
            unaRelazione = (Relazione)listaRelazioni.get(indiceRelazione);
        } /* fine del blocco if */

        return unaRelazione;
    } /* fine del metodo getter */


    /* Restituisce la lunghezza del percorso
* @return la lunghezza del percorso (il numero di relazioni di cui è composto) */
    public int getLunghezza() {
        return this.getRelazioni().size();
    } /* fine del metodo getter */

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
}// fine della classe it.algos.base.relazione.RelazionePercorso.java
//-----------------------------------------------------------------------------

